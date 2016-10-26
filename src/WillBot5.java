
public class WillBot5 {
	
	public WillBot5(){}
	
	public int[] move(int SIZE, int TURN, int[][] BOARDARRAY, int PLAYERNUM){
		int size = SIZE;
		int turn = TURN;
		int[] point = new int[2];
		int me = PLAYERNUM;
		int opp = 3 - me;
		int[][] board = BOARDARRAY.clone();
		
		int[] testpoint = {(int)(Math.random()*size), (int)(Math.random()*size)};
		if(turn == 0){return testpoint;}
		
		testpoint = checkForWin(board, size, me);
		if(testpoint != null){return testpoint;}
		
		testpoint = checkForWin(board, size, opp);
		if(testpoint != null){return testpoint;}
		
		testpoint = checkForPin1(board, size, me);
		if(testpoint != null){return testpoint;}
		
		testpoint = checkForPin1(board, size, opp);
		if(testpoint != null){return testpoint;}
		
		testpoint = checkForPin2(board, size, me);//a point that creates two pin possibilities or a pin and win
		if(testpoint != null){return testpoint;}
	
		testpoint = checkForPin2(board, size, opp);
		if(testpoint != null){return testpoint;}
	
		testpoint = checkForPin3(board, size, me);//a point that creates two superpin possibilities or...
		if(testpoint != null){return testpoint;}
		
		testpoint = checkForPin3(board, size, opp);
		if(testpoint != null){return testpoint;}
		
		int val = 0;
		int maxval = 0;
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(board[x][y] == 0){
					val = calcVal(board, size, x, y);
					if(val > maxval){
						point[0] = x;
						point[1] = y;
						maxval = val;
					}
				}
			}
		}
		return point;
	}
	
	private boolean isRelevant(int[][] board, int size, int x, int y){
		int dist = 2;
		if(board[x][y] == 0){
			for(int i = Math.max(0, x-dist); i <= Math.min(x+dist, size-1); i++){
				for(int j = Math.max(0, y-dist); j <= Math.min(y+dist, size-1); j++){
					if(board[i][j] != 0){return true;}
				}
			}
		}
		return false;
	}
	
	private int[] checkForWin(int[][] board, int size, int p){
		int[] point = new int[2]; //coordinates of point that would win the game for player p
		int np = 3-p;
		int[][] wincount = new int[size][size];
		boolean returnpoint = false;
		
		//checkHorizontal
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size - 4; x++){
				int pcount = 0;
				int blank = 0;
				for(int i = 0; i < 5; i++){
					if(board[x+i][y] == p){pcount++;}
					else if(board[x+i][y] == 0){blank = i;}
					else if(board[x+i][y] == np){pcount = -99; break;}
				}
				if(pcount == 4){wincount[x+blank][y]++; returnpoint = true;}
			}
		}
		
		//checkVertical
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size - 4; y++){
				int pcount = 0;
				int blank = 0;
				for(int i = 0; i < 5; i++){
					if(board[x][y+i] == p){pcount++;}
					else if(board[x][y+i] == 0){blank = i;}
					else if(board[x][y+i] == np){pcount = -99; break;}
				}
				if(pcount == 4){wincount[x][y+blank]++; returnpoint = true;}
			}
		}
		
		//checkDiagonal+
		for(int x = 0; x < size - 4; x++){
			for(int y = 0; y < size - 4; y++){
				int pcount = 0;
				int blank = 0;
				for(int i = 0; i < 5; i++){
					if(board[x+i][y+i] == p){pcount++;}
					else if(board[x+i][y+i] == 0){blank = i;}
					else if(board[x+i][y+i] == np){pcount = -99; break;}
				}
				if(pcount == 4){wincount[x+blank][y+blank]++; returnpoint = true;}
			}
		}
		
		//checkDiagonal-
		for(int x = 4; x < size; x++){
			for(int y = 0; y < size - 4; y++){
				int pcount = 0;
				int blank = 0;
				for(int i = 0; i < 5; i++){
					if(board[x-i][y+i] == p){pcount++;}
					else if(board[x-i][y+i] == 0){blank = i;}
					else if(board[x-i][y+i] == np){pcount = -99; break;}
				}
				if(pcount == 4){wincount[x-blank][y+blank]++; returnpoint = true;}
			}
		}
		
		if(returnpoint){
			int maxval = 0;
			for(int x = 0; x < size; x++){
				for(int y = 0; y < size; y++){
					if(wincount[x][y] > maxval){
						point[0] = x;
						point[1] = y;
						maxval = wincount[x][y];
					}
				}
			}
			return point;
		}
		
		return null;
	}
	
	private int[] checkForPin1(int[][] board, int size, int p){//finds places to guarantee a win after next turn
		int[] point = new int[2];
		int np = 3-p;
		int[][] pin1count = new int[size][size];
		int[] win = new int[2];
		int newsize = 9;
		int num = (newsize-1)/2;
		
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(isRelevant(board, size, x, y)){
					int[][] newboard = new int[newsize][newsize];
					for(int i = -num; i <= num; i++){
						for(int j = -num; j <= num; j++){
							if(x+i < 0 || x+i >= size || y+j < 0 || y+j >= size){
								newboard[i+num][j+num] = np;
							}else{newboard[i+num][j+num] = board[i+x][j+y];}
						}
					}
					newboard[num][num] = p;
					while(true){
						win = checkForWin(newboard, newsize, p);
						if(win != null){
							pin1count[x][y]++;
							newboard[win[0]][win[1]] = np;
						}else{break;}
					}
				}
			}
		}
		
		int maxval = 0;
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(pin1count[x][y] > maxval){
					point[0] = x;
					point[1] = y;
					maxval = pin1count[x][y];
				}
			}
		}
		if(maxval >= 2){return point;}
			
		return null;
	}	

	private int[] checkForPin2(int[][] board, int size, int p){//finds places to guarantee a win after next turn
		int[] point = new int[2];
		int np = 3-p;
		int[][] pin2count = new int[size][size];
		int[] win = new int[2];
		int[] pin1 = new int[2];
		int newsize = 17;
		int num = (newsize-1)/2;
		
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(isRelevant(board, size, x, y)){
					int[][] newboard = new int[newsize][newsize];
					for(int i = -num; i <= num; i++){
						for(int j = -num; j <= num; j++){
							if(x+i < 0 || x+i >= size || y+j < 0 || y+j >= size){
								newboard[i+num][j+num] = np;
							}else{newboard[i+num][j+num] = board[i+x][j+y];}
						}
					}
					newboard[num][num] = p;
					while(true){
						win = checkForWin(newboard, newsize, p);
						if(win != null){
							pin2count[x][y]++;
							newboard[win[0]][win[1]] = np;
						}else{
							pin1 = checkForPin1(newboard, newsize, p);
							if(pin1 != null){
								pin2count[x][y]++;
								newboard[pin1[0]][pin1[1]] = np;
							}else{break;}
						}
					}
				}
			}
		}
		
		int maxval = 0;
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(pin2count[x][y] > maxval){
					point[0] = x;
					point[1] = y;
					maxval = pin2count[x][y];
				}
			}
		}
		if(maxval >= 2){return point;}
			
		return null;	
	}
	
	private int[] checkForPin3(int[][] board, int size, int p){//finds places to guarantee a win after next turn
		int[] point = new int[2];
		int np = 3-p;
		int[][] pin3count = new int[size][size];
		int[] win = new int[2];
		int[] pin1 = new int[2];
		int[] pin2 = new int[2];
		int newsize = 25;
		int num = (newsize-1)/2;
		
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(isRelevant(board, size, x, y)){
					int[][] newboard = new int[newsize][newsize];
					for(int i = -num; i <= num; i++){
						for(int j = -num; j <= num; j++){
							if(x+i < 0 || x+i >= size || y+j < 0 || y+j >= size){
								newboard[i+num][j+num] = np;
							}else{newboard[i+num][j+num] = board[i+x][j+y];}
						}
					}
					newboard[num][num] = p;
					while(true){
						win = checkForWin(newboard, newsize, p);
						if(win != null){
							pin3count[x][y]++;
							newboard[win[0]][win[1]] = np;
						}else{
							pin1 = checkForPin1(newboard, newsize, p);
							if(pin1 != null){
								pin3count[x][y]++;
								newboard[pin1[0]][pin1[1]] = np;
							}else{			
								pin2 = checkForPin2(newboard, newsize, p);
								if(pin2 != null){
									pin3count[x][y]++;
									newboard[pin2[0]][pin2[1]] = np;
								}else{break;}
							}
						}
					}
				}
			}
		}
		
		int maxval = 0;
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(pin3count[x][y] > maxval){
					point[0] = x;
					point[1] = y;
					maxval = pin3count[x][y];
				}
			}
		}
		if(maxval >= 2){return point;}
			
		return null;	
	}
	
	private int calcVal(int[][] board, int size, int x, int y){
		int val = 0;
		int player = 0;
		
		//horizontal
		if(x >= 1 && board[x-1][y] != 0){
			val++;
			player = board[x-1][y];
			for(int i = x-2; i >= 0; i--){
				if(board[i][y] == player){val += x-i;}
				else{break;}
			}
		}
		if(x < size-1 && board[x+1][y] != 0){
			val++;
			player = board[x+1][y];
			for(int i = x+2; i < size; i++){
				if(board[i][y] == player){val += i-x;}
				else{break;}
			}
		}
		
		//vertical
		if(y >= 1 && board[x][y-1] != 0){
			val++;
			player = board[x][y-1];
			for(int i = y-2; i >= 0; i--){
				if(board[x][i] == player){val += y-i;}
				else{break;}
			}
		}
		if(y < size-1 && board[x][y+1] != 0){
			val++;
			player = board[x][y+1];
			for(int i = y+2; i < size; i++){
				if(board[x][i] == player){val += i-y;}
				else{break;}
			}
		}
		
		//diagonal+
		if(x >= 1 && y >= 1 && board[x-1][y-1] != 0){
			val++;
			player = board[x-1][y-1];
			for(int i = 2; i <= Math.min(x, y); i++){
				if(board[x-i][y-i] == player){val += i;}
				else{break;}
			}
		}
		if(x < size-1 && y < size-1 && board[x+1][y+1] != 0){
			val++;
			player = board[x+1][y+1];
			for(int i = 2; i < Math.min(size-x, size-y); i++){
				if(board[x+i][y+i] == player){val += i;}
				else{break;}
			}
		}
		
		//diagonal-
		if(x >= 1 && y < size-1 && board[x-1][y+1] != 0){
			val++;
			player = board[x-1][y+1];
			for(int i = 2; i <= Math.min(x, size-y-1); i++){
				if(board[x-i][y+i] == player){val += i;}
				else{break;}
			}
		}
		if(x < size-1 && y >= 1 && board[x+1][y-1] != 0){
			val++;
			player = board[x+1][y-1];
			for(int i = 2; i <= Math.min(size-x-1, y); i++){
				if(board[x+i][y-i] == player){val += i;}
				else{break;}
			}
		}
		
		//extras
		if(x >= 1 && x < size-1 && board[x-1][y] == board[x+1][y] && board[x-1][y] != 0){val++;}
		if(y >= 1 && y < size-1 && board[x][y-1] == board[x][y+1] && board[x][y-1] != 0){val++;}
		if(x >= 1 && y >= 1 && x < size-1 && y < size-1 && board[x-1][y-1] == board[x+1][y+1] && board[x-1][y-1] != 0){val++;}
		if(x >= 1 && y < size-1 && x < size-1 && y >= 1 && board[x-1][y+1] == board[x+1][y-1] && board[x-1][y+1] != 0){val++;}
		
		return val;
	}
	
}

