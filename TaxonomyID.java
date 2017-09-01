import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class TaxonomyID {
	
	public String taxonomyID;
	public String Accession;
	public String[] Source;
	public String[] taxonomy;
	public int[] SourceCount;//number of each source;
	public String[] SourcePrint;//the display version of source list
	public int NUM;//the size of count array;
	
	
	
	public TaxonomyID(String[] accessionNumber, String[] sourceName, int arraySize)
	{
		NUM = 0;
		//Accession = accessionNumbers;
		//searchTaxonomyID(Accession);
		sortAndcount(sourceName, arraySize, accessionNumber);
		
	}

	public void searchTaxonomyID(String accessionNumbers)
	{
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?"
				+ "db=sequences&id=" + accessionNumbers
				+ "&rettype=fasta&retmode=xml");
		HttpResponse response;
		PrintWriter writer = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			writer = new PrintWriter("test.fa");
			writer.println(EntityUtils.toString(entity));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writer.close();
		
		FileReader reader;
		try {
			reader = new FileReader("test.fa");
			Scanner in = new Scanner(reader);
			while(in.hasNextLine())
			{
				
				//System.out.println("hello");
				String s = in.nextLine();
				String query = "<TSeq_taxid>";
				String endQuery = "</TSeq_taxid>";
				int start = s.indexOf(query);
				int end = s.indexOf(endQuery);
				if(start > 0)
				{
					//System.out.println(s.substring(start+12, end));
					taxonomyID = s.substring(start+12, end);
					
				}
				//System.out.println(s.substring(start+12, end));
			}
			in.close();
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public void sortAndcount(String[] sourceName, int arraySize, String[] accessionNumber)
	{
		Source = new String[arraySize];
		for(int i = 0; i < arraySize; i ++)
			  if(sourceName[i] != null)
				  Source[i] = sourceName[i];

		Arrays.sort(Source);
		
		/*for(int i = 0; i < arraySize-1; i ++)
		{
			int minpos = i;
			for(int j = i + 1; j < arraySize; j ++)
				if(Source[i].compareTo(Source[j]) < 0)
					minpos = i;
			String temp = Source[i];
			String temp1 = accessionNumber[i];
			Source[i] = Source[minpos];
			accessionNumber[i] = accessionNumber[minpos];
			Source[minpos] = temp;
			accessionNumber[minpos] = temp1;
			
		}*/
		
		Set<String> set = new HashSet<String>();
		for(int i = 0; i < arraySize; i ++)
			set.add(Source[i]);
		 
		
		
		Iterator<String> it = set.iterator();
		Iterator<String> it1 = set.iterator();
		while(it.hasNext())
		{
			  //SourcePrint[NUM] = it.next();
			String s = it.next();
			  NUM ++;
		    //System.out.println(it.next());
		}
		
		SourceCount = new int [NUM];
		SourcePrint = new String [NUM];
		taxonomy = new String[NUM];
		
		
		int j = 0;
		while(it1.hasNext())
		{
			String s = it1.next();  
			SourcePrint[j] = s;
			j ++;
			 // NUM ++;
		    //System.out.println(it.next());
		}
		
		for(int i = 0; i < NUM; i ++)
			SourceCount[i] = 1;
		int num = 0; //start position of SourceCount;
		
		for(int i = 0; i < arraySize-1; i ++)
		  {
			 if(Source[i].compareTo(Source[i+1]) == 0)
			 {
				 SourceCount[num] += 1;
				 //Accession[num] = accessionNumber[i+1];
				 
			 }
			 if(Source[i].compareTo(Source[i+1]) < 0)
			 {
				 
				 num ++;
				 
			 }
			 //System.out.println(Source[i]);
		  }
		
		Arrays.sort(SourcePrint);
		
		for(int i = 0; i < NUM; i ++)
		{
		
			String access = searchAccession(SourcePrint[i], arraySize, accessionNumber, sourceName);
			searchTaxonomyID(access);
			taxonomy[i] = taxonomyID;
			
			
		}
	}
	
	public String searchAccession(String SourcePrint, int arraySize, String[] accessionNumber, 
			String[] sourceName)
	{
		
		for(int i = 0; i < sourceName.length; i ++)
		{
			if(sourceName[i] != null && sourceName[i].compareTo(SourcePrint) == 0)
			{
				Accession = accessionNumber[i];
				return Accession;
				
			}

		}
		return null;
		
	}
	
	
	public String getTaxonomy(int num)
	{
		return taxonomy[num];
		
	}
	
	public int getCount(int num)
	{
		
		return SourceCount[num];
		
	}
	
	public String getSource(int num)
	{
		return SourcePrint[num];
		
	}
	
	public int getCountSize()
	{
		return NUM;
	}
	
	
	
	
}
