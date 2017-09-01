import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;

public class messageGUI extends JFrame

{
	public JTextArea message = new JTextArea(15,80);
	public JScrollPane ScrollPane;
	public String s = "";
	
	public messageGUI() throws IOException
	{
		this.setSize(700,800);
		this.setLocation(400,300);
		this.setTitle("");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.LayoutComponents();
		
		//showMessage(s);
	}
	
	public void LayoutComponents() throws IOException
	{
		ScrollPane = new JScrollPane(message);
		ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(ScrollPane, BorderLayout.CENTER);
		message.setText("Hello");
		
		FileReader file = new FileReader("QC-beetle_README.txt");
		Scanner in = new Scanner(file);
		
		String s = in.nextLine();
		message.setText(s);
		while(in.hasNextLine())
		{
			message.append("\n");
			String j = in.nextLine();
			message.append(j);
			
		}
		
		in.close();
		file.close();
		//message.append("\n");
		
	}
	
	public void showMessage(String s1)
	{
		message.append(s1);
		message.append("\n");
		
	}
	
	public void getMessage(String s2)
	{
		s = s2;
		
	}

}
