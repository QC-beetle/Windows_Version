import java.awt.Cursor;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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

public class Summer {

	
	public static void main(String[] arg)
	{
		
		MainGUI mgui = new MainGUI();
		mgui.setVisible(true);

		
		/*EventQueue.invokeLater(new Runnable(){
			public void run(){
				new MainGUI().setVisible(true);
			}
		});*/
		
		
		/*HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?"
				+ "db=sequences&id=1196970908&rettype=fasta&retmode=text");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		PrintWriter writer = new PrintWriter("test.fa");
		writer.println(EntityUtils.toString(entity));
		
		//System.out.println(EntityUtils.toString(entity));
		writer.close();*/
		
		
		
		
		
		
		/*String fonts[]
		        = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		for (int i = 0; i < fonts.length; i++) {
		    System.out.println(fonts[i]);
		}
		
		
		/*File fasta_file;
		String file_directory = "";
		
		int total_count = 0;
		JFileChooser fastaChooser = new JFileChooser();
		fastaChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int result = fastaChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			fasta_file = fastaChooser.getSelectedFile();
			file_directory = fasta_file.getAbsolutePath();
			FileReader reader = new FileReader(fasta_file);
			Scanner in = new Scanner(reader);
			while(in.hasNextLine())
			{
				//setCursor(Cursor.WAIT_CURSOR);
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
	
		System.out.println(file_directory);
		System.out.println(total_count); */
		
		/*File fastqFile = new File("E:/java-neon/workspace/Project/Fasta_file/ERR000916_1.fastq");
		 
		File seqOutFile = new File("E:/java-neon/workspace/Project/Fasta_file/output.seq.fasta");
		File qualOutFile = new File("E:/java-neon/workspace/Project/Fasta_file/output.qual.fasta");
		 
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
		
		System.out.println("Sucessful!!");
		*/
		
	}

	
	 


	
	
}