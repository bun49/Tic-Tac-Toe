import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class menu extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private String[] player1Options = {"Human", "Bot1", "Bot2"};
	
	private String[] player2Options = {"Human", "Bot1", "Bot2"};
	
	public static String player1Choice = "";
	public static String player2Choice = "";
	public static int gridSize = 3;
	

	public menu()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		
		JLabel title = new JLabel("Welcome to Dic-Dac-Doe");
		title.setFont(new Font("Times", Font.BOLD, 30));
		c.insets = new Insets(20,30,20,30);
		c.gridx = 1;
		p.add(title, c);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridBagLayout());
		JLabel p1Label = new JLabel("Player One:");
		c.insets = new Insets(0,0,10,0);
		panel2.add(p1Label, c);
		JLabel p2Label = new JLabel("Player Two:");
		c.gridy = 2;
		c.insets = new Insets(0,0,0,0);
		panel2.add(p2Label, c);
		
		JComboBox<?> p1Choices = new JComboBox<Object>(player1Options);
		p1Choices.setSelectedIndex(0);
		p1Choices.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
            {
				JComboBox<?> cb = (JComboBox<?>)e.getSource();
		        player1Choice = (String)cb.getSelectedItem();
            }
		});
		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(0,10,10,0);
		panel2.add(p1Choices, c);
		
		JComboBox<?> p2Choices = new JComboBox<Object>(player2Options);
		p2Choices.setSelectedIndex(0);
		p2Choices.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
            {
				JComboBox<?> cb = (JComboBox<?>)e.getSource();
		        player2Choice = (String)cb.getSelectedItem();

            }
		});
		c.gridy = 2;
		panel2.add(p2Choices, c);
		
		JLabel gridSizeLabel = new JLabel("Grid Size (3-16):");
		JTextField gridSizeText = new JTextField("3",2);
		gridSizeText.setHorizontalAlignment(JTextField.RIGHT);
		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(0,0,10,0);
		panel2.add(gridSizeLabel,c);
		c.gridx = 2;
		c.insets = new Insets(0,10,10,0);
		panel2.add(gridSizeText,c);
		
		JButton start = new JButton("Play!");
		c.insets = new Insets(10,0,10,0);
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 4;
		panel2.add(start,c);
		start.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
            {
				gridSize = Integer.parseInt(gridSizeText.getText());
				//this is where it passes the info on to the program and starts the game
				setVisible(false);
				game g = new game("WillBot1.java","WillBot2.java",16);
				g.setVisible(true);
            }
		});
		
		add(p, BorderLayout.CENTER);
		add(panel2, BorderLayout.SOUTH);
		
		pack();
		
	}
	
}
