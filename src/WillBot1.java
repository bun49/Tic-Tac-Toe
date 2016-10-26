public class WillBot1 {
	
	private static int size = 0;
	private static int turn = 0;
	private static int[][] board = new int[0][0];
	private static int p = 0;
	private static int np = 0;
	
	public WillBot1(){}
	
	public int[] move(int SIZE, int TURN, int[][] BOARDARRAY, int PLAYERNUM){	
		size = SIZE;
		turn = TURN;
		board = BOARDARRAY.clone();
		p = PLAYERNUM;
		np = 3 - p;
		
		int[] backup = {size/2, size/2};
		if(turn == 0){return backup;}
		
		int[] pointforwin = checkForWin();
		if(pointforwin != null){return pointforwin;}
		
		int[] pointforpin = checkForPin();
		if(pointforpin != null){return pointforpin;}
		
		backup = findAveragePoint();
		
		return backup;
	}
	
	private int[] checkForWin(){
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
	
	private int[] checkForPin(){//finds places to guarantee a win after next turn
		int[] point = new int[2];
		int[] backup = new int[2];
		boolean usebackup = false;
		//rewrite to consider blocking maximum pins
		
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
				if(npcount == 3){backup[0] = x+blank; backup[1] = y; usebackup = true;}
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
				if(npcount == 3){backup[0] = x; backup[1] = y+blank; usebackup = true;}
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
				if(npcount == 3){backup[0] = x+blank; backup[1] = y+blank; usebackup = true;}
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
				if(npcount == 3){backup[0] = x-blank; backup[1] = y+blank; usebackup = true;}
			}
		}
		
		if(usebackup){return backup;}
		
		return null;
	}
	
	private int[] findAveragePoint(){//finds the empty square closest to the average of all filled-in squares on the board
		int[] point = new int[2];
		double[] average = new double[2];
		
		//findAverage
		int filledin = 0;
		int xtotal = 0;
		int ytotal = 0;
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(board[x][y] != 0){
					filledin++;
					xtotal += x;
					ytotal += y;
				}
			}
		}
		average[0] = xtotal / filledin;
		average[1] = ytotal / filledin;
		
		//findPointDistances
		double shortest = size * size;
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(board[x][y] == 0){
					if(Math.sqrt(Math.pow(x - average[0], 2) + Math.pow(y - average[1], 2)) <= shortest){
						point[0] = x;
						point[1] = y;
						shortest = Math.sqrt(Math.pow(x - average[0], 2) + Math.pow(y - average[1], 2));
					}
				}
			}
		}
		return point;
	}
	
}
