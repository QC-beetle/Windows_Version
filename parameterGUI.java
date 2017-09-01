import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

public class parameterGUI extends JFrame 
implements ActionListener
{
	private JPanel top, bottom;
	private JButton submit, clear;
	private JLabel l1, l2, l3, l4, l5;
	private JTextField tf1, tf2, tf3, tf4, tf5;
	public String Evalue, OutputName, bit_score, max_target, identity;
	public boolean change = false;
	
	
	
	public parameterGUI()
	{
		this.setSize(350,200);
		this.setLocation(400,300);
		this.setTitle("Quality Control Parameters");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.LayoutComponents();
		
		getParameters();
		
	}
	
	public void LayoutComponents()
	{
		top = new JPanel(new GridLayout(5,1));
		l1 = new JLabel("E-Value: ");
		l2 = new JLabel("Output Filename: ");
		l3 = new JLabel("Bit-Score: ");
		l4 = new JLabel("Number of Targets: ");
		l5 = new JLabel("Identity%: ");

		tf1 = new JTextField("Default 0.001");
		/*tf1.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        tf1.setText("");
		    }

		    public void focusLost(FocusEvent e) {
		        // nothing
		    }
		});*/
		tf2 = new JTextField("Default blastx_output.txt");
		/*tf2.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        tf2.setText("");
		    }

		    public void focusLost(FocusEvent e) {
		        // nothing
		    }
		});*/
		tf3 = new JTextField("Default 50");
		/*tf3.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        tf3.setText("");
		    }

		    public void focusLost(FocusEvent e) {
		        // nothing
		    }
		});*/
		tf4 = new JTextField("Default 1");
		/*tf4.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        tf4.setText("");
		    }

		    public void focusLost(FocusEvent e) {
		        // nothing
		    }
		});*/
		tf5 = new JTextField("Default 80");
		/*tf5.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        tf5.setText("");
		    }

		    public void focusLost(FocusEvent e) {
		        // nothing
		    }
		});*/
		top.add(l1);
		top.add(tf1);
		top.add(l2);
		top.add(tf2);
		top.add(l3);
		top.add(tf3);
		top.add(l4);
		top.add(tf4);
		top.add(l5);
		top.add(tf5);
		this.add(top, BorderLayout.NORTH);

		bottom = new JPanel();
		submit = new JButton("Submit");
		clear = new JButton("Clear");
		submit.addActionListener(this);
		clear.addActionListener(this);
		bottom.add(submit);
		bottom.add(clear);
		this.add(bottom, BorderLayout.SOUTH);
		
		
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() == submit)
		{
			
			getParameters();
			
			this.dispose();
			
			
		}
		
		if(e.getSource() == clear)
		{
			tf1.setText("");
			tf2.setText("");
			tf3.setText("");
			tf4.setText("");
			tf5.setText("");
			
			
		}
		
		
		
	}
	
	
	public void getParameters()
	{
		if(tf1.getText().equals("Default 0.001"))
			Evalue = "0.001";
		else
			Evalue = tf1.getText();
		
		
		if(tf2.getText().equals("Default blastx_output.txt"))
			OutputName = "diamond_blastx";
		else
			OutputName = tf2.getText();
		
		if(tf3.getText().equals("Default 50"))
			bit_score = "50";
		else
			bit_score = tf3.getText();
		
		if(tf4.getText().equals("Default 1"))
			max_target = "1";
		else 
			max_target = tf4.getText();
		
		if(tf5.getText().equals("Default 80"))
			identity = "80";
		else 
			identity = tf5.getText();
		
	}
	
	
	public String getEvalue()
	{
		return Evalue;
		
	}
	
	public String getOutputName()
	{
		return OutputName;
		
	}
	
	public String getBitScore()
	{
		return bit_score;
		
	}
	
	public String getMaxTarget()
	{
		return max_target;
		
	}
	
	public String getIdentity()
	{
		return identity;
		
	}

	public boolean getChange()
	{
		return change;
	}
}
