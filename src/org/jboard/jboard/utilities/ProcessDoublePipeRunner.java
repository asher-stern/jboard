package org.jboard.jboard.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

/**
 * 
 * @author Asher Stern
 * Date: Jul 22, 2014
 *
 */
public abstract class ProcessDoublePipeRunner
{
	public ProcessDoublePipeRunner(List<String> command, String directory)
	{
		super();
		this.command = command;
		this.directory = directory;
	}

	public void sendInputLine(final String line)
	{
		System.out.println("Input: "+line);
		processInputWriter.println(line);
		processInputWriter.flush();
	}
	
	public abstract void processOutputLine(final String line);
	
	
	public void startProcess()
	{
		try
		{
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			File dir = new File(directory);
			if (!(dir.isDirectory()))
			{
				throw new RuntimeException("Given directory "+dir.getAbsolutePath()+" is not a directory.");
			}
			processBuilder.directory(dir);
			processBuilder.redirectErrorStream(true);
			process = processBuilder.start();
			processInputWriter = new PrintWriter(process.getOutputStream());
			
			Thread outputCollectorThread = new Thread(new OutputCollector());
			outputCollectorThread.start();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public void stopProcess()
	{
		try
		{
			stop = true;
			if (process!=null)
			{
				process.getInputStream().close();
				process.getOutputStream().close();
				process.destroy();
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	
	public Exception getException()
	{
		return exception;
	}


	private void internalProcessOutputLine(final String line)
	{
		System.out.println("output: "+line);
		processOutputLine(line);
	}

	private class OutputCollector implements Runnable
	{
		@Override
		public void run()
		{
			try
			{

				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while (!stop)
				{
					String line = reader.readLine();
					if (line==null)
					{
						stop = true;
					}
					else
					{
						internalProcessOutputLine(line);
					}
				}
			}
			catch(Exception e)
			{
				stop = true;
				exception = e;
				e.printStackTrace(System.out);
				System.out.println("OutputCollector stops now.");
			}
			
			System.out.println("OutputCollector thread is over.");
		}
	}

	 

	private final List<String> command;
	private final String directory;
	
	private Process process;
	private PrintWriter processInputWriter;
	private boolean stop = false;
	private Exception exception = null;
}
