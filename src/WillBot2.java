public class WillBot2 {
	
	private static int size = 0;
	private static int turn = 0;
	private static int[][] board = new int[0][0];
	
	public WillBot2(){}
	
	public int[] move(int SIZE, int TURN, int[][] BOARDARRAY, int PLAYERNUM){
		size = SIZE;
		turn = TURN;
		board = BOARDARRAY.clone();
		
		int[] point = {size/2, size/2};
		if(turn == 0){return point;}
		
		int val = 0;
		int maxval = 0;
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				if(board[x][y] == 0){
					val = calcVal(x,y);
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
	
	private int calcVal(int x, int y){
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
