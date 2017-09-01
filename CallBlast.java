import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class CallBlast  extends JFrame
implements ActionListener
{
	private JButton b1, upload;
	public JTextField tf1, tf2;
	public File GiFile;
	public String FASTA;
	public String numbers = "";; //Gi numbers from text file;
	public boolean UPLOAD; //whether users upload file or not;
	
	public CallBlast()
	{
		this.setSize(450,230);
		this.setLocation(400,300);
		this.setTitle("Customize Database");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.LayoutComponents();
		
	}
	
	
	public void LayoutComponents()
	{
		
		JPanel top = new JPanel(new GridLayout(2,1));
		JLabel l1 = new JLabel("GI /Accession Numbers: ");
		tf1 = new JTextField(500);
		JLabel l2 = new JLabel("Database Name: ");
		tf2 = new JTextField(20);
		upload = new JButton("Upload Txt");
		upload.addActionListener(this);
		top.add(l1);
		top.add(tf1);
		top.add(l2);
		top.add(tf2);
		this.add(top, BorderLayout.NORTH);
		
		
		JPanel bottom = new JPanel();
		b1 = new JButton("Create Database");
		b1.addActionListener(this);
		bottom.add(upload);
		bottom.add(b1);
		
		this.add(bottom, BorderLayout.SOUTH);
		
		
	}
	

	
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		//Create the database function
		if(e.getSource() == b1)
		{
			if(UPLOAD)
			{
				FASTA = tf2.getText();
				if (FASTA.equals(""))
				{
					JOptionPane.showMessageDialog(null,
					        "Error you did not enter database name..", 
					        "Error", JOptionPane.ERROR_MESSAGE);
					
				}
				
				callDatabase(numbers, FASTA);
				makeDatabase(FASTA);
				setCursor(Cursor.DEFAULT_CURSOR);
			}
			else
			{
				String ID = tf1.getText();
				FASTA = tf2.getText();
				if(ID.equals(""))
				{
					JOptionPane.showMessageDialog(null,
					        "Error you did not enter Gi Number.", 
					        "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if (FASTA.equals(""))
				{
					JOptionPane.showMessageDialog(null,
					        "Error you did not enter database name..", 
					        "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				callDatabase(ID, FASTA);
				makeDatabase(FASTA);
				setCursor(Cursor.DEFAULT_CURSOR);
			}
			
			
		}
		
		//upload the Gi number file
		if(e.getSource() == upload)
		{
			
			try {
				JFileChooser chooser = new JFileChooser();
				int result = chooser.showOpenDialog(null);
				
				if(result == JFileChooser.APPROVE_OPTION)
				{
					GiFile = chooser.getSelectedFile();
					FileReader reader = new FileReader(GiFile);
					Scanner in = new Scanner(reader);
					numbers = in.nextLine();
					
					while(in.hasNextLine())
					{
						numbers = numbers + "," + in.nextLine();
					}
					
					
					in.close();
					reader.close();
					
					
				}
				
				UPLOAD = true;
				
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
		}
		
	}
	
	public void callDatabase(String id, String fastaName)
	{
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?"
				+ "db=sequences&id=" + id
				+ "&rettype=fasta&retmode=text");
		HttpResponse response;
		PrintWriter writer = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			writer = new PrintWriter(fastaName + ".fa");
			writer.println(EntityUtils.toString(entity));
			JOptionPane.showMessageDialog(null, "Please check your fasta file", "Database Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writer.close();
		
		
	}
	
	public void makeDatabase(String fastaName)
	{
		String s = null;
		try
		{
			
			Process p1 = Runtime.getRuntime().exec("diamond makedb --in " + fastaName + ".fa "
					+ " -d " + fastaName);
			
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p1.getErrorStream()));
			
			//create a FASTA file for storing a list of transcript sequences
			
			
			//read the output from the command
			//print the output in the FASTA file
			//System.out.println("Here is the standard output of the command: \n");
			while((s = stdInput.readLine()) != null)
			{
				JOptionPane.showMessageDialog(null, "Database is customizing.....", "Info", JOptionPane.INFORMATION_MESSAGE);
				setCursor(Cursor.WAIT_CURSOR);
				//System.out.println(s);
			}
			
			//read any errors from the command
			//System.out.println("Here is the standard error of the command: \n");
			while((s = stdError.readLine()) != null)
			{
				JOptionPane.showMessageDialog(null, "Please check your fasta file", "Database Error",
						JOptionPane.ERROR_MESSAGE);
				//System.out.println(s);
			}
			
			
			
			
		}
		catch (IOException ex)
		{
			//System.out.println("exception happened");
			JOptionPane.showMessageDialog(null, "Please check your fasta file", "Database Error",
						JOptionPane.ERROR_MESSAGE);
			
		}
	}
	
	
	public String getDatabaseName()
	{
		return FASTA;
		
	}
	
	
}