public class WillBot3 {
	
	private static int size = 0;
	private static int turn = 0;
	private static int p = 0;
	private static int np = 0;
	
	public WillBot3(){}
	
	public int[] move(int SIZE, int TURN, int[][] BOARDARRAY, int PLAYERNUM){
		size = SIZE;
		turn = TURN;
		p = PLAYERNUM;
		np = 3 - p;
		int[][] board = BOARDARRAY.clone();
		
		int[] point = {(int)(Math.random()*size), (int)(Math.random()*size)};
		if(turn == 0){return point;}
		
		int[] pointforwin = checkForWin(board);
		if(pointforwin != null){return pointforwin;}
		
		int[] pointforpin = checkForPin(board, true);
		if(pointforpin != null){return pointforpin;}
		
		int[] pointforsuperpin = checkForSuperPin(board);//a point that creates two pin possibilities
		if(pointforsuperpin != null){return pointforsuperpin;}
		
		int val = 0;
		int maxval = 0;
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(board[x][y] == 0){
					val = calcVal(x, y, board);
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
	
	private int calcVal(int x, int y, int[][] board){
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
	
	private int[] checkForWin(int[][] board){
		int[] point = new int[2]; //coordinates of point that would win the game for me
		int[] backup = new int[2]; //coordinates of point that would prevent josh from winning
		boolean usebackup = false;
		
		//checkHorizontal
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size - 4; x++){
				int pcount = 0;
				int npcount = 0;
				int blank = 0;
				for(int i = 0; i < 5; i++){
					if(board[x+i][y] == p){pcount++; npcount = -99;}
					else if(board[x+i][y] == 0){blank = i;}
					else if(board[x+i][y] == np){pcount = -99; npcount++;}
				}
				if(pcount == 4){point[0] = x+blank; point[1] = y; return point;}
				if(npcount == 4){backup[0] = x+blank; backup[1] = y; usebackup = true;}
			}
		}
		
		//checkVertical
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size - 4; y++){
				int pcount = 0;
				int npcount = 0;
				int blank = 0;
				for(int i = 0; i < 5; i++){
					if(board[x][y+i] == p){pcount++; npcount = -99;}
					else if(board[x][y+i] == 0){blank = i;}
					else if(board[x][y+i] == np){pcount = -99; npcount++;}
				}
				if(pcount == 4){point[0] = x; point[1] = y+blank; return point;}
				if(npcount == 4){backup[0] = x; backup[1] = y+blank; usebackup = true;}
			}
		}
		
		//checkDiagonal+
		for(int x = 0; x < size - 4; x++){
			for(int y = 0; y < size - 4; y++){
				int pcount = 0;
				int npcount = 0;
				int blank = 0;
				for(int i = 0; i < 5; i++){
					if(board[x+i][y+i] == p){pcount++; npcount = -99;}
					else if(board[x+i][y+i] == 0){blank = i;}
					else if(board[x+i][y+i] == np){pcount = -99; npcount++;}
				}
				if(pcount == 4){point[0] = x+blank; point[1] = y+blank; return point;}
				if(npcount == 4){backup[0] = x+blank; backup[1] = y+blank; usebackup = true;}
			}
		}
		
		//checkDiagonal-
		for(int x = 4; x < size; x++){
			for(int y = 0; y < size - 4; y++){
				int pcount = 0;
				int npcount = 0;
				int blank = 0;
				for(int i = 0; i < 5; i++){
					if(board[x-i][y+i] == p){pcount++; npcount = -99;}
					else if(board[x-i][y+i] == 0){blank = i;}
					else if(board[x-i][y+i] == np){pcount = -99; npcount++;}
				}
				if(pcount == 4){point[0] = x-blank; point[1] = y+blank; return point;}
				if(npcount == 4){backup[0] = x-blank; backup[1] = y+blank; usebackup = true;}
			}
		}
		
		if(usebackup){return backup;}
		
		return null;
	}
	
	private int[] checkForPin(int[][] board, boolean returnbackup){//finds places to guarantee a win after next turn
		int[] point = new int[2];
		int[] backup = new int[2];
		boolean usebackup = false;
		int[][] multipinblock = new int[size][size];
		
		//checkHorizontal
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size - 5; x++){
				int pcount = 0;
				int npcount = 0;
				int blank = 0;
				for(int i = 0; i < 6; i++){
					if(board[x+i][y] == np){pcount = -99;}
					if(board[x+i][y] == p){npcount = -99;}
					if(i > 0 && i < 5){
						if(board[x+i][y] == p){pcount++;}
						if(board[x+i][y] == 0){blank = i;}
						if(board[x+i][y] == np){npcount++;}
					}
				}
				if(pcount == 3){point[0] = x+blank; point[1] = y; return point;}
				if(npcount == 3){multipinblock[x+blank][y]++; usebackup = true;}
			}
		}
		
		//checkVertical
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size - 5; y++){
				int pcount = 0;
				int npcount = 0;
				int blank = 0;
				for(int i = 0; i < 6; i++){
					if(board[x][y+i] == np){pcount = -99;}
					if(board[x][y+i] == p){npcount = -99;}
					if(i > 0 && i < 5){
						if(board[x][y+i] == p){pcount++;}
						if(board[x][y+i] == 0){blank = i;}
						if(board[x][y+i] == np){npcount++;}
					}
				}
				if(pcount == 3){point[0] = x; point[1] = y+blank; return point;}
				if(npcount == 3){multipinblock[x][y+blank]++; usebackup = true;}
			}
		}
		
		//checkDiagonal+
		for(int x = 0; x < size - 5; x++){
			for(int y = 0; y < size - 5; y++){
				int pcount = 0;
				int npcount = 0;
				int blank = 0;
				for(int i = 0; i < 6; i++){
					if(board[x+i][y+i] == np){pcount = -99;}
					if(board[x+i][y+i] == p){npcount = -99;}
					if(i > 0 && i < 5){
						if(board[x+i][y+i] == p){pcount++;}
						if(board[x+i][y+i] == 0){blank = i;}
						if(board[x+i][y+i] == np){npcount++;}
					}
				}
				if(pcount == 3){point[0] = x+blank; point[1] = y+blank; return point;}
				if(npcount == 3){multipinblock[x+blank][y+blank]++; usebackup = true;}
			}
		}
		
		//checkDiagonal-
		for(int x = 5; x < size; x++){
			for(int y = 0; y < size - 5; y++){
				int pcount = 0;
				int npcount = 0;
				int blank = 0;
				for(int i = 0; i < 6; i++){
					if(board[x-i][y+i] == np){pcount = -99;}
					if(board[x-i][y+i] == p){npcount = -99;}
					if(i > 0 && i < 5){
						if(board[x-i][y+i] == p){pcount++;}
						if(board[x-i][y+i] == 0){blank = i;}
						if(board[x-i][y+i] == np){npcount++;}
					}
				}
				if(pcount == 3){point[0] = x-blank; point[1] = y+blank; return point;}
				if(npcount == 3){multipinblock[x-blank][y+blank]++; usebackup = true;}
			}
		}
		
		if(returnbackup){
			if(usebackup){
				int maxval = 0;
				for(int x = 0; x < size; x++){
					for(int y = 0; y < size; y++){
						if(multipinblock[x][y] > maxval){
							backup[0] = x;
							backup[1] = y;
							maxval = multipinblock[x][y];
						}
					}
				}
				return backup;
			}
		}
		
		return null;
	}	

	private int[] checkForSuperPin(int[][] board){//finds places to guarantee a win after next turn
		int[] point = new int[2];
		int[] pin = new int[2];
		
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(board[x][y] == 0){
					int[][] newboard = new int[size][size];
					for(int i = 0; i < size; i++){
						for(int j = 0; j < size; j++){
							newboard[i][j] = board[i][j];
						}	
					}
					newboard[x][y] = p;
					pin = checkForPin(newboard, false);
					if(pin != null){
						newboard[pin[0]][pin[1]] = np;
						pin = checkForPin(newboard, false);
						if(pin != null){
							point[0] = x;
							point[1] = y;
							return point;
						}
					}
				}
			}
		}
		
		return null;
	}
	
}
