Overview

This was the team-based final project for my CSC 202 course (level II Java Programming) that I took while at Virginia Western Community College. Teams of 3 or 4 were to
incorporate everything that the course covered over the 16-week semester - file I/O, stacks, queues, binary trees, database connectivity with JDBC, among other aspects of
object-oriented design.

The goal of this project was to create a virtual auction house in which a customer could select an auction from a provided list, and place a bid on and potentially win the 
item being auctioned off. Auctions could be created by admins, include an admin-chosen item from a list of items, and were to open and close at times that the administrator 
specified. User data was to be both readable and writeable, that is to say that the information that pertained to a specific user should be able to be loaded into the program 
and written to an external file. This file was expanded during the project's development to include information regarding every object used in the program. Included in the 
rhinosJavaSalesProject package is the input_file.txt file - this is a file that can be loaded upon starting up the program and populates the program's data structures with 
objects that can be used during the program's execution. The format of the file is of my own creation and somewhat unintuitive as this was my first time ever attempting such 
a task. I included a diagram of how my design for reading and writing information to and from files works in the rhinosJavaSalesProject package - ReadDocumentation.png depicts
how the text file is broken up into blocks and how those blocks are used to create the objects that are to be acted upon in the program.

User manual

Main menu

	- The main menu has two versions - one for when a user is logged into the application and another for when no user is present	
	
	No user present:
	- 1) Loads sample data into the items arraylist
	- 2) Prints the items in the items arraylist
	- 3) Initiates admin login process
	- 4) Initiates customer login process
	- 5) Loads information from an external file
	- 6) Writes the status of the program to text file
	- 7) Exit the application after asking for user's confirmation
	
	User Present:
	- 1) Loads items array list with sample data
	- 2) Prints the items present in the items array list
	- 3) Logs the user out of the application (input "yes" at the prompt to affirm)
	- 4) Advances the user to the next menu
	- 5) Exits the application
	
Logging in

	- When the user chooses to log in as a customer, a sub menu shows up. From here the user can choose
	  to create a new customer account, login as an existing customer account, or return to the previous menu.
	  
	- The breakdown of the options is as follows:
	  
	  Login sub menus
	  	- 1) Will compare your login credentials to those of the accounts that we have logged
	  	- 2) Initiates the creation process of a new account
	  	- 3) Returns to the previous menu
	  	
	  	Returning Account
	  		- Prompts you for login credentials and compares them to what the application has logged
	  		- Enter the username and then the password
	  	
	  	New Account
	  		- You are prompted to input the username you want to be associated with your account
	  		- After username input, you are prompted for the password you want to be associated with your account
	  		- You will be logged in as that account upon account creation
	  
	- Logging in as an admin is a bit different because only admins can create new admin accounts. 
	  A default admin account is already created, the credentials to which are as follows:
	  
		username: Admin
		password: password
	  	  		
Admin Menu
	- 1) Lists active auctions
	- 2) Lists future auctions
	- 3) Lists active auctions, displays bidding history of chosen one
	- 4) Lists completed auctions
	- 5) Lists winning bids
	- 6) Activates a new auction
	- 7) Creates a new admin account
	- 8) Returns to previous menu
	
Customer Menu
	- 1) Loads sample data
	- 2) Prints items arraylist
	- 3) Logs out
	- 4) Opens customer menu
	- 5) Exits program
	
Running the flat file (called input_file.txt)
	- The program is now capable of reading and writing information. We have a pre-written text file that has 
	  information about the state of various auctions.
	- Pressing 5 on the main menu will trigger reading in a file
	- Once this file is read in, it will create:
	  		- 1 Dynamic Active Auction - This auction begins when the file gets read in and ends 5 minutes after its creation
	  		- 1 Active Auction - This auction has been created already and some bids have already been submitted
	  		- 1 Completed Auction - This auction has already ended and all bids that were part of it are stored in the text file
	  		- 1 Dynamic Future Auction - This auction will open 2 minutes after the text file gets read in, and close 5 minutes
	  		  after that
	  		- 1 Future Auction - This auction is scheduled to open in October of 2021 and close in December
