/*
 * Name: Anthony Dalia
 * Course: CNT 4714 Spring 2020
 * Assignment Title: Project 2 - Muli-threaded programming in Java
 * Data: Febuary 9th, 2020
 * 
 * Class: CNT4714-20Spring 001
 */



import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class conveyor 
{

	public String conveyorName;
	public Lock accessControl = new ReentrantLock();
	
	//Activation
	public void movePackages(String activator)
	{
		System.out.println(activator + ": moved packages on " + conveyorName);
	}
	
	//Constructor
	public conveyor(String name)
	{
		conveyorName = name;
	}
}
