import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.*;

public class ImportGUI extends JFrame{
	
	
	public ImportGUI()
	{
		this.setSize(400,100);
		this.setLocation(400,300);
		this.setTitle("Importing Info");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.LayoutComponents();
		
		
	}

	
	public void LayoutComponents()
	{
		
		JPanel middle = new JPanel();
		
		JLabel l1 = new JLabel("File is importing.....");
		l1.setFont(new Font("SansSerif",Font.PLAIN, 20));
		middle.add(l1);
		
		this.add(middle, BorderLayout.CENTER);
		
		
	}
	
	
}
