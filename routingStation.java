/*
 * Name: Anthony Dalia
 * Course: CNT 4714 Spring 2020
 * Assignment Title: Project 2 - Muli-threaded programming in Java
 * Data: Febuary 9th, 2020
 * 
 * Class: CNT4714-20Spring 001
 */



import java.util.Random;

public class routingStation extends Thread
{
	public conveyor input;
	public conveyor output;
	public String stationID;
	public int packagesLeftToMove;
	
	public void run()
	{
		//Attempt to access the first buffer
		while (packagesLeftToMove > 0)
		{
			//Attempt to access both buffers
			if(input.accessControl.tryLock())
			{
				//Input buffer locked out cuz it is avalible.
				System.out.println(stationID + ": holds lock on input " + input.conveyorName + ".");
				
				if(output.accessControl.tryLock())
				{
					//output buffer locked out cuz it is avalible too. 
					System.out.println(stationID + ": holds lock on output " + output.conveyorName + ".");
					
					//Perform some buffer actions
					input.movePackages(stationID);
					output.movePackages(stationID);
					
					//Release both buffers
					input.accessControl.unlock();
					output.accessControl.unlock();
					
					System.out.println(stationID + ": unlocks input " + input.conveyorName);
					System.out.println(stationID + ": unlocks output " + output.conveyorName);
					
					//Decrement our run counter and announce how many runs we have left
					packagesLeftToMove--;
					System.out.println(stationID + ": has " + packagesLeftToMove + " package groups left to move.");
				}
				else
				{
					System.out.println(stationID + ": unable to lock output conveyor - releasing lock on input " + input.conveyorName);
					input.accessControl.unlock(); //Unlock the first buffer that we locked in prep for the second buffer
					
					//Wait randomly
					try 
					{
						Random rand = new Random();
						int wait = rand.nextInt(50);
						sleep(wait);
						
					} catch (InterruptedException e) 
					{
						//We could not sleep for some reason
						System.out.println(stationID + " could not sleep for some reason.");
					}
				}
			}
			else
			{			
				//Wait randomly
				try 
				{
					Random rand = new Random();
					int wait = rand.nextInt(50);
					sleep(wait);
				} catch (InterruptedException e) 
				{
					//We could not sleep for some reason
					System.out.println(stationID + " could not sleep for some reason.");
				}
			}
		}
		
		//We have finsihed our run
		System.out.println("\n** " + stationID + ": workload successfully completed. ** \n\n\n");
	}
	
	public routingStation(conveyor inpt, conveyor oupt, String name, int runs)
	{
		input = inpt;
		output = oupt;
		stationID = name;
		packagesLeftToMove = runs;
	}
}
