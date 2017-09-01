
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import org.jcvi.jillion.core.datastore.DataStoreException;
import org.jcvi.jillion.core.datastore.DataStoreProviderHint;
import org.jcvi.jillion.core.util.iter.StreamingIterator;
import org.jcvi.jillion.fasta.nt.NucleotideFastaWriter;
import org.jcvi.jillion.fasta.nt.NucleotideFastaWriterBuilder;
import org.jcvi.jillion.fasta.qual.QualityFastaWriter;
import org.jcvi.jillion.fasta.qual.QualityFastaWriterBuilder;
import org.jcvi.jillion.trace.fastq.FastqDataStore;
import org.jcvi.jillion.trace.fastq.FastqFileDataStoreBuilder;
import org.jcvi.jillion.trace.fastq.FastqRecord;



public class MainGUI extends JFrame
implements ActionListener
{
	//instance variables which are components
		private JPanel Parameter, Buttons, Visual, contentPanel;
		private JTable table = new JTable();
		private JRadioButton Rbutton1, Rbutton2;
		//private JTextArea textArea;
		private JLabel l1, l2, l3, l4, l9, statusLabel;
		//private JEditorPane editorPane;
		private JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8;
		private JButton b1, b2, b2_1, b3, b4, parameterButton;
		private JMenuBar menuBar;
		private JMenu menu1, detail, menu3, menu4;
		private JMenuItem menuItem1, menuItem2, menuItem3, menuItem4,
									  menuItem5, menuItem6, menuItem7, menuItem8, 
									  detailItem1, detailItem2, detailItem3, detailItem4, 
									  detailItem5, detailItem6;
	
		public File fasta_file, fastq_file; //Imported Fasta or Fastq file;
		public String file_directory = ""; //The directory of fasta or fastq file;
		
		public int arraySize = 0;
		public String[] source_name = new String[200000];
		public String[] accessionNumber = new String [200000];
		
		
		public int[] deCount = {1, 1, 1, 1, 1, 1}, visualCount = new int[6]; 
		public int []classicCount = {0, 0, 0, 0, 0, 0}; 
		public int[] conCount = { 0, 0, 0, 0, 0, 0};
		public int[] INDEX = new int [6];
		
		public int total_count = 0, count = 0;
		
		
		private String Evalue, alignment, outfmt, OutputName, bit_score, max_target, identity, Customize;
		
		private double identityScore, coverage;
		
		private int length;
		//private int col = 1; //number of data import
		
		private JComboBox option1, option2, option3, Detail;
		
		public boolean all_database = false, OpenDialog = false, OpenDatabase = false, fastq = false;
		
		public int index = 0;//database position;
		
		private String[] DatabaseOptions = {"All", "Bacteria", "Fungi",  "Mycoplasma", "Chlamydia", 
				"Parasite", "Virus"};
		private String[] Options = {"Result Details", "Bacteria", "Fungi",  "Mycoplasma", "Chlamydia", 
				"Parasite", "Virus"};
		private String[] AlignmentMode = {"Quick", "Sensitive", "More Sensitive"};
		
		private String[] Database = {"Diamond/Bacteria", "Diamond/Fungi", "Diamond/Mycoplasma", 
				"Diamond/Chlamydia", "Diamond/Parasite", "Diamond/viral"};
		
		//private String[] details = {"Result Detail", "Bacteria", "Chlamydia",  "Fungi", "Mycoplasma", 
		//		"Parasite", "Virus"};
		
		private Vector<String> ColumnName = new Vector<String>();
		private Vector<String> Data = new Vector<String>();
		private Vector<Vector<String>> dataVector = new Vector<Vector<String>>();
	
		private DefaultTableModel model;
		
		public JProgressBar bar = new JProgressBar();
		
		final JOptionPane optionPane = new JOptionPane("Hello world", JOptionPane.INFORMATION_MESSAGE, 
				JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
		
		final JDialog dialog = optionPane.createDialog(null, "test");
		
		
		
		parameterGUI parameter1 = new parameterGUI();
		CallBlast CreateDatabase = new CallBlast();
		//ImportGUI IGui = new ImportGUI();
		//messageGUI mgui = new messageGUI();
		
	public MainGUI()
	{
		
		this.setSize(1400,700);
		this.setLocation(300,100);
		this.setTitle("QC-Beetle");
		ImageIcon image = new ImageIcon("Img/beetle.png");
		this.setIconImage(image.getImage());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.LayoutComponents();
		
		

		
	}

	
	public void LayoutComponents()
	{
		MenuBar();
		this.setJMenuBar(menuBar);
		
		//the top of Parameter Panel
		TitledBorder border1 = new TitledBorder("Step1 : Database Selection");
		JPanel top = new JPanel();
		l1 = new JLabel("Database: ");
		l1.setFont(new Font("SansSerif",Font.ITALIC, 12));
		b1 = new JButton("Customize");
		b1.setFont(new Font("SansSerif",Font.ITALIC, 12));
		b1.addActionListener(this);
		option1 = new JComboBox(DatabaseOptions);
		option1.setSelectedIndex(0);
		top.setBorder(border1);
		top.add(l1);
		top.add(option1);
		top.add(b1);
		
		
		//the middle of Parameter Panel
		TitledBorder border2 = new TitledBorder("Step2 : Parameters Setting");
		JPanel middle = new JPanel(new GridLayout(2,1));
		JPanel middle1 = new JPanel(new GridLayout(3,1));
		l2 = new JLabel("Query Identity%: ");
		l2.setFont(new Font("SansSerif",Font.ITALIC, 12));
		middle1.add(l2);
		tf2 = new JTextField("Default 90");
		/*tf2.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        tf2.setText("");
		    }

		    public void focusLost(FocusEvent e) {
		        // nothing
		    }
		});*/
		middle1.add(tf2);
		l3 = new JLabel("Query Coverage%: ");
		l3.setFont(new Font("SansSerif",Font.ITALIC, 12));
		middle1.add(l3);
		tf3 = new JTextField("Default 95");
		/*tf3.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        tf3.setText("");
		    }

		    public void focusLost(FocusEvent e) {
		        // nothing
		    }
		});*/
		middle1.add(tf3);
		l4 = new JLabel("Matching Length: ");
		l4.setFont(new Font("SansSerif",Font.ITALIC, 12));
		middle1.add(l4);
		tf4 = new JTextField("Default 30");
		/*tf4.addFocusListener(new FocusListener() {
		    public void focusGained(FocusEvent e) {
		        tf4.setText("");
		    }

		    public void focusLost(FocusEvent e) {
		        // nothing
		    }
		});*/
		middle1.add(tf4);
		JPanel middle2 = new JPanel();
		parameterButton = new JButton("Change Parameters");
		parameterButton.setFont(new Font("SansSerif",Font.ITALIC, 12));
		parameterButton.addActionListener(this);
		middle2.add(parameterButton);
		middle.add(middle1);
		middle.add(middle2);

		middle.setBorder(border2);
		
		
		//the bottom of Parameter Panel
		TitledBorder border3 = new TitledBorder("Step3 : Search Contamination");
		JPanel bottom = new JPanel(new GridLayout(2,1));
		JPanel bottom_1 = new JPanel();
		//Rbutton1 = new JRadioButton("Standard Output");
		//Rbutton2 = new JRadioButton("Visualization Output");
		//Rbutton1.setSelected(true);
		l9 = new JLabel("Alignment Mode: ");
		l9.setFont(new Font("SansSerif",Font.ITALIC, 12));
		option2 = new JComboBox(AlignmentMode);
		option2.setSelectedIndex(0);
		JPanel bottom_2 = new JPanel();
		JPanel bottom_2_1 = new JPanel();
		JPanel bottom_2_2 = new JPanel();
		JLabel l10 = new JLabel("Step3 : ");
		JLabel l11 = new JLabel("Step4: ");
		b2 = new JButton("Search Contamination");
		b2.setFont(new Font("SansSerif",Font.ITALIC, 12));
		b2.addActionListener(this);
		b2_1 = new JButton("Show Summary Result");
		b2_1.setFont(new Font("SansSerif",Font.ITALIC, 12));
		b2_1.addActionListener(this);
		//bottom_2.add(l10);
		bottom_2.add(b2);
		bottom_2_1.add(b2);
		bottom_2_2.add(b2_1);
		//bottom_2.add(l11);
		bottom_2.add(bottom_2_1);
		TitledBorder bottom2 = new TitledBorder("Step4 : Show Summary Result");
		bottom_2_2.setBorder(bottom2);
		//bottom_2.add(bottom_2_2);
		bottom_1.add(l9);
		bottom_1.add(option2);

		bottom.add(bottom_1);
		bottom.add(bottom_2);
		bottom.setBorder(border3);
		
		//Overview of the Parameter Panel
		Parameter = new JPanel(new GridLayout(4,1));
		Parameter.add(top);
		Parameter.add(middle);
		Parameter.add(bottom);
		Parameter.add(bottom_2_2);
		option1.addActionListener(this);
		option2.addActionListener(this);
		//Rbutton1.setActionCommand("Standard");
		//Rbutton1.addActionListener(this);
		//Rbutton2.setActionCommand("Visualization");
		//Rbutton2.addActionListener(this);
		
		
		//JSplitPane with parameter panel and table panel
		ColumnName.addElement("Source");
		ColumnName.addElement("taxon% [Default parameter]");
		ColumnName.addElement("taxon% [Schmieder's parameter]");
		ColumnName.addElement("taxon% [Conservative parameter]");

		model = new DefaultTableModel(dataVector,ColumnName);
	    table.setModel(model);
		JScrollPane ScrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		TitledBorder title = new TitledBorder("Summary Result");
		ScrollPane.setBorder(title);
		
		TitledBorder border4 = new TitledBorder("Step5 : Visualization");
		Visual = new JPanel();
		Visual.setBorder(border4);
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,  ScrollPane, Visual);
		split.setOneTouchExpandable(true);
		split.setDividerLocation(150);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                Parameter, split);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(330);
		this.add(splitPane, BorderLayout.CENTER);
		
		//Bottom panel with buttons
		JPanel progress = new JPanel(new GridLayout(2,1));
		Buttons = new JPanel();
		contentPanel = new JPanel();
		
		JLabel l12 = new JLabel("Step5: ");
		JLabel l13 = new JLabel("Step6: ");
		b3 = new JButton("Visualization");
		b3.setFont(new Font("SansSerif",Font.ITALIC, 12));
		b4 = new JButton("Details");
		b4.setFont(new Font("SansSerif",Font.ITALIC, 12));
		option3 = new JComboBox(Options);
		option3.setSelectedIndex(0);
		option3.addActionListener(this);
		//b3.setPreferredSize(new Dimension(150,20));
		//b4.setPreferredSize(new Dimension(150,20));
		b3.addActionListener(this);
		b4.addActionListener(this);
		//Buttons.add(l12);
		Buttons.add(b3);
		//contentPanel.add(l13);
		Buttons.add(option3);
		
		Buttons.add(bar);
		//progress.add(contentPanel);

		this.add(Buttons, BorderLayout.SOUTH);
		

	}
	
	
	private void reloadData(int i, boolean all) throws ClassNotFoundException
	{
		ColumnName.clear();
	    Data.clear();
	    //Data.add(source);
	    if(all)
	    {
	    	for(i = 1; i < 7; i ++)
	    	{
	    		Data.add(DatabaseOptions[i]);
	    		Data.add(String.format("%5.3f", deCount[i-1] * 100.0/total_count));
	    		Data.add(String.format("%5.3f", classicCount[i-1] * 100.0/total_count));
	    		Data.add(String.format("%5.3f", conCount[i-1] * 100.0/total_count));
	    	}
	    	
	    }
	    else
	    {
	    	Data.add(DatabaseOptions[i]);
    		Data.add(String.format("%5.3f", deCount[i-1] * 100.0/total_count));
    		Data.add(String.format("%5.3f", classicCount[i-1] * 100.0/total_count));
    		Data.add(String.format("%5.3f", conCount[i-1] * 100.0/total_count));
	    	
	    }

	    dataVector.add(Data);
	    
	    
	    //ColumnName.addElement("Source");
		//ColumnName.addElement("Default Ratio %");
		//ColumnName.addElement("Classic Ratio %");
		//ColumnName.addElement("Conserved Ratio %");
	   
	    
	}


	
	public void actionPerformed(ActionEvent e)
	{
		//Import the Fasta file
		if(e.getSource() == menuItem1)
		{
			class FastaFile extends SwingWorker<String, Object>{

				@Override
				protected String doInBackground() throws Exception {
					b1.setEnabled(false);
					parameterButton.setEnabled(false);
					b2.setEnabled(false);
					b2_1.setEnabled(false);
					b3.setEnabled(false);
					bar.setIndeterminate(true);
					JFileChooser fastaChooser = new JFileChooser();
					fastaChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					int result = fastaChooser.showOpenDialog(null);
					if (result == JFileChooser.APPROVE_OPTION)
					{
						
						fasta_file = fastaChooser.getSelectedFile();
						file_directory = fasta_file.getAbsolutePath();
						try
						{
						FileReader reader = new FileReader(fasta_file);
						Scanner in = new Scanner(reader);
						
						while(in.hasNextLine())
						{
							///setCursor(Cursor.WAIT_CURSOR);
							
							String s = in.nextLine();
							String query = ">";
							int start = s.indexOf(query);
							if(start >= 0)
							{
								total_count ++;
							}
							
						}
						
						in.close();
						reader.close();
						}
						catch(IOException ex)
						{
							ex.printStackTrace();
						}
					}
					return "Fasta Done.";
				}
				
				protected void done()
				{
					b1.setEnabled(true);
					b2.setEnabled(true);
					parameterButton.setEnabled(true);
					b2_1.setEnabled(true);
					b3.setEnabled(true);
					bar.setIndeterminate(false);
				}
				
			}
			
			new FastaFile().execute();
			//readFastaFile(file_directory);
			//setCursor(Cursor.DEFAULT_CURSOR);
			//dialog.setVisible(false);
			//IGui.setVisible(false);
			//JOptionPane.showMessageDialog(null, "Imported confirmed", "File Import", JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		//Import the Fastq file
		if(e.getSource() == menuItem2)
		{
			class FastqFile extends SwingWorker<String, Object>{

				
				protected String doInBackground() throws Exception {
					b1.setEnabled(false);
					parameterButton.setEnabled(false);
					b2.setEnabled(false);
					b2_1.setEnabled(false);
					b3.setEnabled(false);
					bar.setIndeterminate(true);
					JFileChooser fastaChooser = new JFileChooser();
					fastaChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					int result = fastaChooser.showOpenDialog(null);
					if (result == JFileChooser.APPROVE_OPTION)
					{
						fastq_file = fastaChooser.getSelectedFile();
						file_directory = fastq_file.getAbsolutePath();
						
					}
					//readFastaFile(file_directory);
					try {
						ConvertFastq(file_directory);
						
					} catch (IOException | DataStoreException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return "Fastq Done.";
				}
				
				
				protected void done()
				{
					b1.setEnabled(true);
					b2.setEnabled(true);
					parameterButton.setEnabled(true);
					b2_1.setEnabled(true);
					b3.setEnabled(true);
					bar.setIndeterminate(false);
					fastq = true;
				}
				
			}
			
			new FastqFile().execute();
			
		}
		
		//get the help manual
		if(e.getSource() == menuItem7 || e.getSource() == menuItem8)
		{
			messageGUI help = null;
			try {
				help = new messageGUI();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			help.setVisible(true);
			
			
		}
		
		
		//Customize the database;
		if(e.getSource() == b1)
		{
			OpenDatabase = true;
			
			CreateDatabase.setVisible(true);
			
		}
		
		//Search the contamination
		if(e.getSource() == b2)
		{
			getParameters();
			
			if(file_directory.equals(""))
				JOptionPane.showMessageDialog(null, "Please Import the NGS file", "File Error",
						JOptionPane.ERROR_MESSAGE);
			
			class Search extends SwingWorker<String, Object>{

				@Override
				protected String doInBackground() throws Exception {
					
					b1.setEnabled(false);
					b2.setEnabled(false);
					parameterButton.setEnabled(false);
					b2_1.setEnabled(false);
					b3.setEnabled(false);
					bar.setIndeterminate(true);
					
					if(OpenDatabase)
					{
						callDiamond(Customize, file_directory, Evalue, alignment, outfmt, 
								OutputName, bit_score, max_target, identity);
						
						
					}
					else
					{
						if(all_database)
						{
							
							for(index = 1; index < 7; index ++)
							{
								callDiamond(Database[index-1], file_directory, Evalue, alignment, outfmt, 
										OutputName + "_" + DatabaseOptions[index],
										bit_score, max_target, identity);

							}
							
						

						}
						else
						{
							
							callDiamond(Database[index-1], file_directory, Evalue, alignment, outfmt, 
									OutputName + "_" + DatabaseOptions[index], 
									bit_score, max_target, identity);
			
						}
						
					}
					
					return "Search contamination completed!";
				}
				
				protected void done()
				{
					for(int i = 0; i < 6; i ++)
					{
						visualCount[i] = deCount[i];
						deCount[i] = 1;
						classicCount[i] = 0;
						conCount[i] = 0;
						
					}
					
					b1.setEnabled(true);
					b2.setEnabled(true);
					parameterButton.setEnabled(true);
					b2_1.setEnabled(true);
					b3.setEnabled(true);
					bar.setIndeterminate(false);
					
				}
				
				
				
			}
			
			new Search().execute();
			

			
			
			

		}
		
		//Show Result
		if(e.getSource() == b2_1)
		{
			getParameters();
			
			class Summary extends SwingWorker<String, Object>{
				
				@Override
				protected String doInBackground() throws Exception {
					
					b1.setEnabled(false);
					b2.setEnabled(false);
					parameterButton.setEnabled(false);
					b2_1.setEnabled(false);
					b3.setEnabled(false);
					bar.setIndeterminate(true);
					
					if(all_database)
					{
						int rowCount = model.getRowCount();
						//Remove rows one by one from the end of the table
						for (int i = rowCount - 1; i >= 0; i--)
						{
						    model.removeRow(i);
						}
						
						ColumnName.removeAllElements();
						
						ColumnName.addElement("Source");
						ColumnName.addElement("taxon% [Default parameter]");
						ColumnName.addElement("taxon% [Schmieder's parameter]");
						ColumnName.addElement("taxon% [Conservative parameter]");
						
						for(int j = 1; j < 7; j ++)
						{
							getQueryID(OutputName + "_" + DatabaseOptions[j], j);
							getClassicQueryID(OutputName + "_" + DatabaseOptions[j], j);
							getConservedQueryID(OutputName + "_" + DatabaseOptions[j], j);
			
						}
						
				
						
						loadData();

					}
					else
					{
						int rowCount = model.getRowCount();
						//Remove rows one by one from the end of the table
						for (int i = rowCount - 1; i >= 0; i--)
						{
						    model.removeRow(i);
						}
						ColumnName.removeAllElements();
						
						ColumnName.addElement("Source");
						ColumnName.addElement("taxon% [Default parameter]");
						ColumnName.addElement("taxon% [Schmieder's parameter]");
						ColumnName.addElement("taxon% [Conservative parameter]");
						
						
						INDEX[count] = index;
						count ++;
						
					
						
						getQueryID(OutputName + "_" + DatabaseOptions[index], index); //get query ID column

						getClassicQueryID(OutputName + "_" + DatabaseOptions[index], index);

						getConservedQueryID(OutputName + "_" + DatabaseOptions[index], index);

						String[] row = {DatabaseOptions[index], String.format("%5.3f", deCount[index-1] * 100.0/total_count), 
								String.format("%5.3f", classicCount[index-1] * 100.0/total_count), 
								String.format("%5.3f", conCount[index-1] * 100.0/total_count)};
						
						model.addRow(row);
						
						
						
					}
					
					return "Done.";
				}
				
				
				public void done()
				{
					for(int i = 0; i < 6; i ++)
					{
						visualCount[i] = deCount[i];
						deCount[i] = 1;
						classicCount[i] = 0;
						conCount[i] = 0;
						
					}
					
					b1.setEnabled(true);
					b2.setEnabled(true);
					parameterButton.setEnabled(true);
					b2_1.setEnabled(true);
					b3.setEnabled(true);
					bar.setIndeterminate(false);
					
				}
				
				
			}
			
			new Summary().execute();
			
			
			
		
		}
		
		//Change Diamond parameters
		if(e.getSource() == parameterButton)
		{
			OpenDialog = true;
			
			parameter1.setVisible(true);
			
		}
		
		
		//Visualization the result
		if(e.getSource() == b3)
		{
			getParameters();
			Visual.removeAll();
			
			class Visualization extends SwingWorker<String, Object>{

				@Override
				protected String doInBackground() throws Exception {
					
					b1.setEnabled(false);
					b2.setEnabled(false);
					parameterButton.setEnabled(false);
					b2_1.setEnabled(false);
					b3.setEnabled(false);
					bar.setIndeterminate(true);
					
					if(all_database)
					{
						DefaultPieDataset pieData = new DefaultPieDataset();
						DefaultPieDataset pieData1 = new DefaultPieDataset();
						DefaultPieDataset pieData2 = new DefaultPieDataset();
						double[] ratio = new double[6];
						for(int i = 0; i < 6; i ++)
							{
								ratio[i] = visualCount[i] * 100.0/total_count;
								pieData.setValue(DatabaseOptions[i+1], ratio[i]);
								pieData1.setValue(DatabaseOptions[i+1], ratio[i]);
							}
						//pieData2.setValue("Virus", 0.01);
						//pieData2.setValue("Bacteria", 0.0001);
						//pieData2.setValue("Parasite", 0.00002);
						pieData1.setValue("Unspecified", 100-ratio[0]-ratio[1]-ratio[2]-ratio[3]-ratio[4]-ratio[5]);
						//pieData1.setValue("Unspecified", 100-0.01-0.0001-0.00002);
						callVisualization(pieData1, pieData);
						

					}
					else
					{
						DefaultPieDataset pieData = new DefaultPieDataset();
						DefaultPieDataset pieData1 = new DefaultPieDataset();
						
						double[] ratio = new double[6];
						double num = 100;
						for(int i = 0; i < 6; i ++)
						{
							if(INDEX[i] > 0)
							{
								int j = INDEX[i];
								ratio[j-1] = visualCount[j-1] * 100.0/total_count;
								pieData.setValue(DatabaseOptions[j], ratio[j-1]);
								pieData1.setValue(DatabaseOptions[j], ratio[j-1]);
								num -= ratio[j-1];
							}
							
							
						}

						pieData1.setValue("Unspecified", num);
						callVisualization(pieData1, pieData);
						

					}
					
					
					return "Visualization Completed!";
				}
				
				public void done()
				{
					b1.setEnabled(true);
					b2.setEnabled(true);
					parameterButton.setEnabled(true);
					b2_1.setEnabled(true);
					b3.setEnabled(true);
					bar.setIndeterminate(false);
					
				}
				
				
			}
			
			new Visualization().execute();
			
			
			
		}
		
		//get the details of contaminants
		JComboBox box = (JComboBox) e.getSource();
		
		class Detail extends SwingWorker<String, Object>{

			@Override
			protected String doInBackground() throws Exception {
				
				bar.setIndeterminate(true);
				
				if(box.getSelectedIndex() == 0)
				{	bar.setIndeterminate(false);								}
				else if(box.getSelectedIndex() == 1)
					checkDetail(Options[1]);
				else if(box.getSelectedIndex() == 2)
					checkDetail(Options[2]);
				else if(box.getSelectedIndex() == 3)
					checkDetail(Options[3]);
				else if(box.getSelectedIndex() == 4)
					checkDetail(Options[4]);
				else if(box.getSelectedIndex() == 5)
					checkDetail(Options[5]);
				else if(box.getSelectedIndex() == 6)
					checkDetail(Options[6]); 
				return "Done.";
				
			}
			
			public void done()
			{
				
				bar.setIndeterminate(false);
				
			}
			
			
			
		}
		
		new Detail().execute();
		
		
		
		
	}
	
	

	
	
	public void loadData()
	{
		String[] row1 = {DatabaseOptions[1], String.format("%5.3f", deCount[0] * 1000.0/total_count), 
				String.format("%5.3f", classicCount[0] * 1000.0/total_count), 
				String.format("%5.3f", conCount[0] * 1000.0/total_count)};
		
		String[] row2 = {DatabaseOptions[2], String.format("%5.3f", deCount[1] * 1000.0/total_count), 
				String.format("%5.3f", classicCount[1] * 1000.0/total_count), 
				String.format("%5.3f", conCount[1] * 1000.0/total_count)};
		
		String[] row3 = {DatabaseOptions[3], String.format("%5.3f", deCount[2] * 1000.0/total_count), 
				String.format("%5.3f", classicCount[2] * 1000.0/total_count), 
				String.format("%5.3f", conCount[2] * 1000.0/total_count)};
		
		
		String[] row4 = {DatabaseOptions[4], String.format("%5.3f", deCount[3] * 1000.0/total_count), 
				String.format("%5.3f", (classicCount[3]) * 1000.0/total_count), 
				String.format("%5.3f", (conCount[3]) * 1000.0/total_count)};
		
		String[] row5 = {DatabaseOptions[5], String.format("%5.3f", deCount[4] * 1000.0/total_count), 
				String.format("%5.3f", classicCount[4] * 1000.0/total_count), 
				String.format("%5.3f", conCount[4] * 1000.0/total_count)};
		
		String[] row6 = {DatabaseOptions[6], String.format("%5.3f", deCount[5] * 1000.0/total_count), 
				String.format("%5.3f", classicCount[5] * 1000.0/total_count), 
				String.format("%5.3f", conCount[5] * 1000.0/total_count)};
		//dataVector.add(Data);
		
		
		model.addRow(row1);
		model.addRow(row2);
		model.addRow(row3);
		model.addRow(row4);
		
		model.addRow(row5);
		model.addRow(row6);
		model.fireTableDataChanged();
		
	}
	
	
	public void MenuBar()
	{
		menuBar = new JMenuBar();
		menu1 = new JMenu("File");

		menu4 = new JMenu("Help");
		menuItem1 = new JMenuItem("Import the Fasta file");
		menuItem2 = new JMenuItem("Import the Fastq file");
		menuItem3 = new JMenuItem("Export the blastx result");

		menuItem7 = new JMenuItem("Help");
		menuItem8 = new JMenuItem("About");

		menu1.add(menuItem1);
	    menu1.add(menuItem2);



		menu4.add(menuItem7);
		menu4.add(menuItem8);
		menuBar.add(menu1);
		//menuBar.add(menu2);
		//menuBar.add(menu3);
		menuBar.add(menu4);
		menuItem1.addActionListener(this);
		menuItem2.addActionListener(this);
		menuItem3.addActionListener(this);

		menuItem7.addActionListener(this);
		menuItem8.addActionListener(this);

		
	}

	public void ConvertFastq(String directory) throws IOException, DataStoreException
	{
		File fastqFile = new File(directory);
		File seqOutFile = new File("Fasta_file/output.seq.fasta");
		File qualOutFile = new File("Fasta_file/output.qual.fasta");
		 
		try(
		    FastqDataStore datastore = new FastqFileDataStoreBuilder(fastqFile)
								.hint(DataStoreProviderHint.ITERATION_ONLY)
								.build();
		 
		   StreamingIterator<FastqRecord> iter = datastore.iterator();
		 
		   NucleotideFastaWriter seqWriter = new NucleotideFastaWriterBuilder(seqOutFile)
		                                                                       .build();
		   QualityFastaWriter qualWriter = new QualityFastaWriterBuilder(qualOutFile)
		                                                                        .build();
		  ){
		    while(iter.hasNext()){
		        FastqRecord fastq = iter.next();
		 
		        String id = fastq.getId();
		        String comment = fastq.getComment();
		 
		        seqWriter.write(id, fastq.getNucleotideSequence() , comment);
		 
		        qualWriter.write(id, fastq.getQualitySequence() , comment);
		        
		    }
		}
		
	}
	
	public void callDiamond(String database, String directory, String evalue, String alignment, String outfmt,
			String outputname, String bit_score, String max_target, String identity)
	{
		String s = null;

		
		
		try
		{
			Process p;
			p = Runtime.getRuntime().exec("diamond blastx"
					+ " --db " + database + " "
					+ "--query "  + directory + " " 
					+ "--evalue " + Evalue + " "
					+ "--min-score " + bit_score + " "
					+ "-k 1 "
					+ "--id " + identity + " "
					+ "--outfmt " + outfmt + " "
					+ "--out Result_file/" + outputname + ".txt");
			
			
			
			
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			
			
			//timer.start();
			//read the output from the command
			while((s = stdInput.readLine()) != null)
			{
				//System.out.println(s);
				
				setCursor(Cursor.WAIT_CURSOR);
				b1.setEnabled(false); 
				b2.setEnabled(false); 
				b2_1.setEnabled(false);
				b3.setEnabled(false);
				b4.setEnabled(false);
				
				
			}
			
			//read any errors from the command
			//System.out.println("Here is the standard error of the command: \n");
			while((s = stdError.readLine()) != null)
			{
				//System.out.println(s);
			}
			
			
			
		}
		catch (IOException ex)
		{
			System.out.println("exception happened");
			
			
		}
		
		setCursor(Cursor.DEFAULT_CURSOR);
		b1.setEnabled(true); 
		b2.setEnabled(true); 
		b2_1.setEnabled(true);
		b3.setEnabled(true);
		b4.setEnabled(true);
		
	}

	public void checkDetail(String options)
	{
		
		int rowCount = model.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--)
		{
		    model.removeRow(i);
		}
		
	   
	   getDetail(OutputName + "_" + options);
	   
	   TaxonomyID taxo = new TaxonomyID(accessionNumber, source_name, arraySize);
	   
	   int num = taxo.getCountSize();
	   
	    
	   ColumnName.removeAllElements();
	   
		
		ColumnName.addElement("Taxonomy ID");
		
		ColumnName.addElement("Source");
		ColumnName.addElement("Ratio %");
		//String[] row = {"Citrobacter koseri", "0.106"};
		
		model = new DefaultTableModel(dataVector,ColumnName);
		
		String[][] row = new String[num][3];
		for(int i = 0; i < num; i ++)
		{
			row[i][0] = taxo.getTaxonomy(i);
			row[i][1] = taxo.getSource(i);
			row[i][2] = String.format("%5.3f", taxo.getCount(i)* 100.0/arraySize);
			model.addRow(row[i]);
			
		}
		
		
	    table.setModel(model);
	    
	    table.addMouseMotionListener(new MouseAdapter()
	    		{
	    			public void mouseMoved(MouseEvent e)
	    			{
	    				int col = table.columnAtPoint(e.getPoint());
	    				if(col == 0)
	    					setCursor(Cursor.HAND_CURSOR);
	    				else
	    					setCursor(Cursor.DEFAULT_CURSOR);
	    				
	    				
	    			}
	    		});
		
	    table.addMouseListener(new MouseAdapter()
 {
    public void mouseClicked(MouseEvent e)
    {
    	int row = table.rowAtPoint(e.getPoint());
    	int col = table.columnAtPoint(e.getPoint());
    	for(int i = 0; i < num; i ++)
    	{
    		if(row == i && col == 0)
    		{
    			String url = "https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?id=" + taxo.getTaxonomy(i);
    			try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    		}
    	}
    	
      
    }
 });
	    
	  
	}
	
	public void getParameters()
	{
		if(OpenDatabase)
		{
			Customize = CreateDatabase.getDatabaseName();
			
		}
		
		if(fastq)
			file_directory = "Fasta_file/default.fa";
		
		if(OpenDialog)
		{
			
			Evalue = parameter1.getEvalue();
			OutputName = parameter1.getOutputName();
			bit_score = parameter1.getBitScore();
			max_target = parameter1.getMaxTarget();
			identity = parameter1.getIdentity();
			outfmt = "6 qseqid stitle pident length mismatch gapopen qstart qend sstart send evalue bitscore";
		}
		else
		{
			//parameter.setVisible(false);
			Evalue = "0.001";
			OutputName = "diamond_blastx";
			bit_score = "50";
			max_target = "5";
			identity = "70";
			outfmt = "6 qseqid stitle pident length mismatch gapopen qstart qend sstart send evalue bitscore";
		}
		
		
		if(tf2.getText().equals("") || tf2.getText().equals("Default 90"))
			identityScore = 50.0;
		else
			identityScore = Double.parseDouble(tf2.getText());
		
		if(option1.getSelectedIndex() == 0)
			{
				all_database = true;
				
			}
		else if(option1.getSelectedIndex() == 1)
			{
				//Database = "Diamond/Bacteria";
				index = 1; 
			}
		else if(option1.getSelectedIndex() == 2)
			{
				//Database = "Diamond/Fungi";
				index = 2;
			}
		else if (option1.getSelectedIndex() == 3)
			{
				//Database = "Diamond/Mycoplasma";
				index = 3;
			}
		else if (option1.getSelectedIndex() == 4)
			{
				//Database = "Diamond/Chlamydia";
				index = 4;
			}
		else if (option1.getSelectedIndex() == 5)
			{
				//Database = "Diamond/Parasite";
				index = 5;
			}
		else if (option1.getSelectedIndex() == 6)
			{
				//Database = "Diamond/viral";
				index = 6;
			}
		
		
		if(option2.getSelectedIndex() == 0)
			alignment = "--quick";
		else if(option2.getSelectedIndex() == 1)
			alignment = "--sensitive";
		else if(option2.getSelectedIndex() == 2)
			alignment = "--more-sensitive";
		
		
		if(tf3.getText().equals("") || tf3.getText().equals("Default 95"))
			coverage = 70.0;
		else
			coverage = Double.parseDouble(tf3.getText());
		
		if(tf4.getText().equals("") || tf4.getText().equals("Default 30"))
			length = 0;
		else
			length = Integer.parseInt(tf4.getText());
		
		
	}

	
	public void callVisualization(DefaultPieDataset pieData1, DefaultPieDataset pieData)
	{
		JFreeChart chart1 = ChartFactory.createPieChart("Contamination Level", pieData1, true, true, true);
		//JFreeChart chart1 = createChart(pieData1, "Contamination Level");
		//JFreeChart chart = createChart(pieData, "Contamination Proportion");
		JFreeChart chart = ChartFactory.createPieChart("Contaminants Proportion", pieData, true, true, true);
		
		
		ChartPanel cp = new ChartPanel(chart);
		ChartPanel cp1 = new ChartPanel(chart1);
		cp.setPreferredSize(new Dimension(470,370));
		cp1.setPreferredSize(new Dimension(470,370));
		/*JScrollPane ScrollPane1 = new JScrollPane(cp1);
		JScrollPane ScrollPane2 = new JScrollPane(cp);
		ScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		ScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		ScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		ScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);*/
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                cp1, cp);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(470);
		
		//JScrollPane ScrollPane = new JScrollPane(splitPane);
		
		//ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//ScrollPane.setVerticalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		Visual.add(splitPane, BorderLayout.CENTER);
		
		Visual.validate();//present the pie chart
		
	}
	
	public JFreeChart createChart(DefaultPieDataset pieData, String title)
	{
		
		JFreeChart chart = ChartFactory.createPieChart3D(title, pieData, true, true, true);
		
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		
		
		//plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("No data to display");
        
		
        return chart;
		
	}
	
	
	public void eraseParameters()
	{
		identity = "";
		index = 0;
		max_target = "";
		bit_score = "";
		OutputName = "";
		outfmt = "";
		alignment = "";
		Evalue = "";
		all_database = false;
		
	}
	
	
	public void getDetail(String outputname)
	{
		
		try {
			FileReader reader = new FileReader("Result_file/" + outputname + ".txt");
			Scanner in = new Scanner(reader);
			//PrintWriter writer = new PrintWriter(outputname + "_queryID.txt");
			
			String s = "";
			int num = 0;
			
			while(in.hasNextLine())
			{
				s = in.nextLine();
				String startQuery = "[";
				String endQuery = "]";
				String startRef = "ref|";
				String endRef = "| ";
				int start1 =s.indexOf(startRef);
				int end1 = s.indexOf(endRef);
				int start = s.indexOf(startQuery);
				int end = s.indexOf(endQuery);
				if(start > 0)
					source_name[num] = s.substring(start+1, end);

				if(start1 > 0)
					accessionNumber[num] = s.substring(start1+4, end1);

				num ++;
				
				
			}

			arraySize = num;
			in.close();
			reader.close();
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public void readFastaFile(String directory)
	{
		bar.setIndeterminate(true);
		
		try {
			FileReader reader = new FileReader(directory);
			Scanner in = new Scanner(reader);
			while(in.hasNextLine())
			{
				setCursor(Cursor.WAIT_CURSOR);
				
				String s = in.nextLine();
				String query = ">";
				int start = s.indexOf(query);
				if(start >= 0)
				{
					total_count ++;
				}
				
			}
			
			in.close();
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getQueryID(String outputname, int i)
	{
		try {
			FileReader reader = new FileReader("Result_file/" + outputname + ".txt");
			Scanner in = new Scanner(reader);
			
			
			while(in.hasNextLine())
			{
				
				String s = in.nextLine();
				int start1 = s.indexOf("[");
				int start2 = 0;
				String sub1 = "";
				if(s.indexOf(" (") < 0 || start2 < start1)
				{
					start2 = s.indexOf("]");
					sub1 = s.substring(start1 + 1, start2);
				
				}
				else
				{
					start2 = s.indexOf(" (");
					sub1 = s.substring(start1 + 1, start2);
				}	
				
				if( !in.hasNextLine())
					break;
				
				String j = in.nextLine();
				
				//String [] j_id = j.split("\t");//next query ID
				
				int end1 = j.indexOf("[");
				int end2 = 0;
				String sub2 = "";
				if(j.indexOf(" (") < 0 || end2 < end1)
				{
					end2 = j.indexOf("]");
					sub2 = j.substring(end1 + 1, end2);
				
				}
				else
				{
					end2 = j.indexOf(" (");
					sub2 = j.substring(end1 + 1, end2);
					
				}
				
				if(sub1.equals(sub2))
				{
					s = j;//line switch
				}
				else
				{
					deCount[i-1] ++;
					s = j;//line switch
				}
				
				
				
			}

			in.close();
			reader.close();
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	public void getClassicQueryID(String outputname, int i)
	{
		try {
			FileReader reader = new FileReader("Result_file/" + outputname + ".txt");
			Scanner in = new Scanner(reader);
			//PrintWriter writer = new PrintWriter(outputname + "_queryID.txt");
			
			
			while(in.hasNextLine())
			{
				String s = in.nextLine();
				String [] s_id = s.split("\t");//first query ID 
				
				
				if( !in.hasNextLine())
					break;
				String j = in.nextLine();
				//String [] j_id = j.split("\t");//next query ID
				double queryCoverage = Double.parseDouble(s_id[2]);
				double identity = Double.parseDouble(s_id[3]);
				
				
					if( queryCoverage < coverage || identity < identityScore)
					{
						s = j;//line switch
					}
					else 
					{
						classicCount[i-1] ++;
						s = j;//line switch
					}
				
				
				
				
				
				
			}

			in.close();
			reader.close();
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void getConservedQueryID(String outputname, int i)
	{
		try {
			FileReader reader = new FileReader("Result_file/" + outputname + ".txt");
			Scanner in = new Scanner(reader);
			//PrintWriter writer = new PrintWriter(outputname + "_queryID.txt");
			
			
			while(in.hasNextLine())
			{
				String s = in.nextLine();
				String [] s_id = s.split("\t");//first query ID 
				
				
				if( !in.hasNextLine())
					break;
				String j = in.nextLine();
				//String [] j_id = j.split("\t");//next query ID
				double queryCoverage = Double.parseDouble(s_id[2]);
				double identity = Double.parseDouble(s_id[3]);
				int len = Integer.parseInt(s_id[4]);
				
				
					if( queryCoverage < coverage || identity < identityScore || len < length)
					{
						s = j;//line switch
					}
					else 
					{
						conCount[i-1] ++;
						s = j;//line switch
					}
				
				
			}

			in.close();
			reader.close();
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	

	
	
	
	
	
	
	
}