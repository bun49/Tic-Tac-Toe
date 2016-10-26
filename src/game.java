import java.awt.*;

import javax.swing.*;

public class game extends JFrame {

	private static final long serialVersionUID = 1L;

	public game(String p1Choice, String p2Choice, int gridSize){
	
	//draw
		setLayout(new BorderLayout());
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		ImageIcon image1 = new ImageIcon();
		JLabel[][] labelArray = new JLabel[gridSize][gridSize];
        
		for(int x = 0; x<gridSize; x++)
		{
			for(int y = 0; y<gridSize; y++)
			{
				image1 = new ImageIcon(getClass().getResource("box.png"));
		        labelArray[x][y] = new JLabel(image1);
		        c.gridx = x;
		        c.gridy = y;
		        p.add(labelArray[x][y], c);
			}
		}
		add(p,BorderLayout.CENTER);
		pack();
		
	//actual game
		WillBot5 bot1 = new WillBot5();
		WillBot5 bot2 = new WillBot5();
		
		int[][] boardArray = new int[gridSize][gridSize]; 
		int[] addPoint = new int[2];
		int playernum = 0;
		boolean cats = true;
		
		for(int turn = 0; turn < gridSize*gridSize; turn++){
			
			playernum = (turn % 2) + 1;
			
			if(playernum == 1){addPoint = bot1.move(gridSize, turn, boardArray, 1);}
			else{addPoint = bot2.move(gridSize, turn, boardArray, 2);}
			
			if(boardArray[addPoint[0]][addPoint[1]] != 0){
				System.out.println("player " + playernum + " fucking sucks.");
			}else{
				boardArray[addPoint[0]][addPoint[1]] = playernum;
				
				if(playernum == 1){image1 = new ImageIcon(getClass().getResource("x.png"));}
				else{image1 = new ImageIcon(getClass().getResource("o.png"));}
				
				p.remove(labelArray[addPoint[0]][addPoint[1]]);
				JLabel xolabel = new JLabel(image1);
		        c.gridx = addPoint[0];
		        c.gridy = addPoint[1];
		        p.add(xolabel,c);
		        add(p,BorderLayout.CENTER);
				pack();
				
			}
			
			System.out.println(turn + ": (" + addPoint[0] + ", " + addPoint[1] + ")");
			
			if(checkForWin(boardArray, playernum, gridSize)){
				System.out.println("player " + playernum + " wins! Congrat.");
				cats = false;
				break;
			}
			
			try {Thread.sleep(0);}catch(InterruptedException e){e.printStackTrace();}
		}
		if(cats == true){System.out.println("Cats Game. Try Harder next Time.");}
		
	}
	
	private boolean checkForWin(int[][] board, int p, int size){
		boolean allsame = false;
		
		//checkHorizontal
		for(int x = 0; x < size - 4; x++){
			for(int y = 0; y < size; y++){
				allsame = true;
				for(int i = 0; i < 5; i++){
					if(board[x+i][y] != p){allsame = false; break;}
				}
				if(allsame){return true;}
			}
		}
		
		//checkVertical
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size - 4; y++){
				allsame = true;
				for(int i = 0; i < 5; i++){
					if(board[x][y+i] != p){allsame = false; break;}
				}
				if(allsame){return true;}
			}
		}
		
		//checkDiagonal+
		for(int x = 0; x < size - 4; x++){
			for(int y = 0; y < size - 4; y++){
				allsame = true;
				for(int i = 0; i < 5; i++){
					if(board[x+i][y+i] != p){allsame = false; break;}
				}
				if(allsame){return true;}
			}
		}
		
		//checkDiagonal-
		for(int x = 4; x < size; x++){
			for(int y = 0; y < size - 4; y++){
				allsame = true;
				for(int i = 0; i < 5; i++){
					if(board[x-i][y+i] != p){allsame = false; break;}
				}
				if(allsame){return true;}
			}
		}
		
		return false;
	}
	
}
