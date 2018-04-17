import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatabaseWrite 
	{
	public static void main(String[] args) 
		{
		
		//Initialize input reader
		InputStreamReader cin = null;

		//Initialize input writer
		BufferedWriter writer = null;
		
		try 
			{
			//create a temporary file
			String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			
			//presents the file as a time- change to user input
			//based on collection name
			File logFile = new File(timeLog);
			
			// This will output the full path where the file will be written to...
			System.out.println(logFile.getCanonicalPath());
			
			//And now, we write...
			cin = new InputStreamReader(System.in);
			writer = new BufferedWriter(new FileWriter(logFile, true)); 
			System.out.println("Enter characters, 'q' to quit.");
	        char c;
	        do 
	        	{
	            c = (char) cin.read();
	            System.out.print(c);
	            
	            //Doesn't write q
	            if (c != 'q')
	            	{
	            	writer.write(c);
	            	} 
	        	}
	        while(c != 'q');		
	        	} 
		catch (Exception e) 
			{
			e.printStackTrace();
	        } 
		finally 
			{
		try 
			{
			// Close the writer regardless of what happens...
			writer.close();
	        } 
		catch (Exception e) 
			{
	        }
			}
		}
	}