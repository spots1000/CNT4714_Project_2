/*
 * Name: Anthony Dalia
 * Course: CNT 4714 Spring 2020
 * Assignment Title: Project 2 - Muli-threaded programming in Java
 * Data: Febuary 9th, 2020
 * 
 * Class: CNT4714-20Spring 001
 */



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class threads 
{
	//Variables that manage all the parameter we will read from files
	static String file = "config.txt";
	static int numRoutingStations;
	static int numConveyors;
	static int[] routerWork; 
	
	private static void readFile()
	{
		BufferedReader fileIO = null;
		
		//Configure our file reader
		try 
		{
			fileIO = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) 
		{
			System.out.println("Config File Could not be read. Program will not exectue. (FILE LD ERR)");
		}
		
		//Read in the number of routing stations we will use
		try 
		{
			numRoutingStations = Integer.parseInt(fileIO.readLine());
		} catch (NumberFormatException e) 
		{
			System.out.println("Config File Could not be read. Program will not exectue. (INT CONV ERR)");
		} catch (IOException e) 
		{
			System.out.println("Config File Could not be read. Program will not exectue. (LINE RD ERR)");
		}
		
		//Read the config for each router into the array
		routerWork = new int[numRoutingStations];
		
		for(int i = 0; i < numRoutingStations; i++)
		{
			try 
			{
				routerWork[i] = Integer.parseInt(fileIO.readLine());
			} catch (NumberFormatException e) 
			{
				System.out.println("Config File Could not be read. Program will not exectue. (INT CONV ERR)");
			} catch (IOException e) 
			{
				System.out.println("Config File Could not be read. Program will not exectue. (LINE RD ERR)");
			}
		}
	}
	
	public static void main(String args[])
	{

		
		//Arrays that hold the conveyors and parameters so we can loop through them
		conveyor[] conveyors = new conveyor[10];
		routingStation[] routers = new routingStation[10];
		
		//Read through the file to gather our parameters.
		readFile();
		
		//Generate the number of conveyors based on our file variable.
		for(int i = 0; i < numRoutingStations; i++)
		{
			conveyors[i] = new conveyor("Conveyor C" + i);
		}
		
		//Generate the routing stations and initialize them properly
		for(int i = 0; i < numRoutingStations; i++)
		{
			if(i == numRoutingStations - 1 )
			{
				routers[i] = new routingStation(conveyors[i], conveyors[0], ("Routing Station " + i), routerWork[i]);
				
				//Make a report of our configuration
				System.out.println("Routing Station " + i + " In-Connnection set to conveyor " + i);
				System.out.println("Routing Station " + i + " Out-Connnection set to conveyor " + 0);
				System.out.println("Routing Station " + i + " Workload set. Station " + i + " has " + routerWork[i] + " packages groups to move.\n\n");
			}
			else
			{
				routers[i] = new routingStation(conveyors[i], conveyors[i+1], ("Routing Station " + i), routerWork[i]);
				
				//Make a report of our configuration
				System.out.println("Routing Station " + i + " In-Connnection set to conveyor " + i);
				System.out.println("Routing Station " + i + " Out-Connnection set to conveyor " + (i+1));
				System.out.println("Routing Station " + i + " Workload set. Station " + i + " has " + routerWork[i] + " packages groups to move. \n\n");
			}
		}
		
		//loop through and start each thread
		for(int i =  0; i < numRoutingStations; i++)
		{
			routers[i].start();
		}
	}
}
