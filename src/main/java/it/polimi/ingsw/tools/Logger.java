package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger			/* Used to print exceptions in console and text file */
{
	private FileWriter fr;

	public Logger() throws IOException
	{
		File logfile = new File("log.txt");
		FileWriter fr = new FileWriter(logfile, true);		/* if not true, appends at the beginning of file */
	}

	public void log(String logString) throws IOException
	{
		System.out.println(logString);
		fr.write(logString);
	}
}
