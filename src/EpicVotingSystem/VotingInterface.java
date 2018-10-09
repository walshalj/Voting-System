
package EpicVotingSystem;

import java.io.*;
import java.util.*;
//import java.io.BufferedReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * File Name :
 * author : Aljane Walsh
 * Date : 
 * Description :
 */

public class VotingInterface
{
    private VotingController vc;
    private Staff theStaff;
    private Candidate theCandidate;
    private Admin theAdmin;
	private final String USERNAME = "admin";
    private final String PASSWORD ="admin123";
    private int numberOfCandidates = 0;
    private SimpleDateFormat sdf = new SimpleDateFormat("d/M/y");
    private Date startDate;
    private Date endDate;
    private boolean hasDateSet = false;
	private BufferedReader in = new BufferedReader( new InputStreamReader( System.in ));
    private int loginCounter = 0;
    
	//Here is the start of your program. 
    public static void main(String[] args)
    {
        VotingInterface vi = new VotingInterface();
        vi.start();       
    }
   
    public int getNumberOfCandidates() 
   	{
		return numberOfCandidates;
	}
   
    public void setNumberOfCandidates(int numberOfCandidates) 
	{
		this.numberOfCandidates = numberOfCandidates;
	}
	
	//This methods is complete and does not require change.
    public void start()
    {
        vc = new VotingController();
        commenceVoting();
    }
	
	//=======================================================================
	
	
	//=======================================================================
	 public void displayVotingScreen()
	 {
	
		 //method to  welcome & display staff name after a successful login 
		 String staffname = theStaff.getName();
		 System.out.printf("\nWelcome %s", staffname+"!");
	     setNumberOfCandidates(0);
	
	     ArrayList<?> candidates = vc.getCandidates();
	
	     Iterator<?> it = candidates.iterator();
	     System.out.println("\n\n\tCode\t\tCandidate Name");
	     System.out.println("\t====\t\t==============\n");
	     while(it.hasNext())
	     {   theCandidate = (Candidate)it.next();
	         System.out.println("\t" + theCandidate.getCandidateCode() + "\t\t" + theCandidate.getName());
	         setNumberOfCandidates(getNumberOfCandidates() + 1);
	     }	
	 }

	//=======================================================================
	
	 //=======================================================================
	 public void commenceVoting()
	{
	    // get user input by using getInput() methods.       
	    boolean systemQuit = false;
	    while(!systemQuit)
	    {
	    	String input  = null;
	    	
	    	//menu to vote or login as admin.
	        System.out.println("\n ===================Epic Voting System===================\n");
	        System.out.println(" Enter \"V\" to vote as staff\n"
	        		+ "\t\"A\" to login as admin\n\t\"H\" for help");
	        
	        
	        input = getInput();
			if (input.equalsIgnoreCase("V"))
	        {   if(hasDateSet)
	        	{
		        	if(checkDateRange(new Date())){
						manageStaffLogin(); //call manageVote
					}
		        	else{
		        		System.out.println("Cannot vote today.");
		        		commenceVoting();
		        	}
	        	}
	        else{
	        	manageStaffLogin();
	        }
	        
	        	
				
	        }
	        
	        else if (input.equalsIgnoreCase("A"))
	        {
	        	//validateAdmin() then manageAdmin() based on user input
	        	validateAdminLogin();
	        	systemQuit = manageAdmin();
	        }
			
	        else if (input.equalsIgnoreCase("H"))
	        {
	        	System.out.print("\n\t>>>>>>> How to use the voting system <<<<<<<<<  \n\nYou must be eligible to vote to use the system."
	        			+ "\nYou are qualified to vote if:\n\n*you are 18 years or older AND\n*you are a staff of Epic Construction organisation."
	        			+ "\n\nYour username and password are required to vote.\n\n");
	        	manageHelpOption(); //managing help option
	        }
	        
	        else
	        {
	        	System.out.println("Your input is not recognized");
	        }
	    }        
	}

	//This method helps to manage admin session after a successfull login
	 private boolean manageAdmin()
	 {
	    boolean adminQuit = false;
	    boolean systemQuit = false;
	
	    while (!adminQuit){
	        System.out.println("\nTo continue managing the system,enter \"M\"\nTo continue voting enter\"C\".\nTo end voting enter \"Stop\" : ");
	        String input = getInput();
	        if (input.equalsIgnoreCase("M")){
	        	manageSystemAdmin();
	        }
	        else if (input.equalsIgnoreCase("C")){
	            //back to voting
	            adminQuit = true;
	        }
	        else if(input.equalsIgnoreCase("Stop")){
	            //stop system
	            adminQuit = true;
	            systemQuit = true;
	            System.out.println("Voting System Closed");
	        }
	        else{
	            System.out.println("Cannot understand your input, please re-enter : \n\n");
	        }
	    }
	    return systemQuit;
	 }

	//validates administrator username & password. This method is complete and does not require additional code.
     public boolean validateAdmin(String username, String password)
     {
        if(username.equalsIgnoreCase(USERNAME)&&(password.equals(PASSWORD))){
            return true;
        }
        else{
            return false;
        }
     }
     
     //validate admin login
     private void validateAdminLogin()
     {
    	 boolean adminQuit = false;
    	   	 
    	 while (!adminQuit)
         {
    		//welcome page and prompts for admin's username
             System.out.print("\n\t>>>>>>>> Welcome to Administration page <<<<<<<<"
             		+ "\n\nPlease enter your username for validation or \"Q\" to quit : " ); 
             String input = getInput();
                         
             for (int i = 0; i <vc.getAdministrator().size() ;i++)
             {         	
             	theAdmin = vc.getUsernameAdmin(input);
             	
             	if (theAdmin != null)
             	{
             		break;
             	}
             }           
                        
             try
             {
             	
             	 if (input.equalsIgnoreCase("q"))
                  { 
                      adminQuit = true;
                  }
             	 
             	 else
                  {
                      String password = null;
                      
                      System.out.print("\nPlease enter password : "); //prompts for password
                      password = getInput().trim();

                      if((theAdmin.getPassword()).equals(password))
                      { 
                          manageSystemAdmin(); //upon successful login, the admin is presented with options
                          adminQuit = true;
                      }
                      
                      else
                      {
                          System.out.println("Incorrect password. Please try again!");
                      }
                  }
             }
             catch(Exception e)
             {
             	System.out.println("Incorrect username. Username \"" + input + "\" does not exist!");
             }
         }             
     }
     	 
	 private void manageSystemAdmin() {
		 //admin's options
		 System.out.println("\n\nTo view voting statistics, enter \"stat\"\nTo manage the system, enter \"M\"\n "
		 		+ "To view overall system report, enter \"O\"\nor \"q\" to quit: ");
         String input = getInput();
        
         if (input.equalsIgnoreCase("stat")){             
        	 printVoteResults(); //prints vote results
         }
         else if(input.equalsIgnoreCase("M")){
             manageAdminOptions(); //manage system information
             
         }
         
         else if (input.equalsIgnoreCase("q"))
         { 
             commenceVoting(); //menu to vote         
         }
         
         else if (input.equalsIgnoreCase("O")){
        	 overallSystemReport(); //overall report
         }
         
         else{
             System.out.println("Cannot understand your input, please re-enter : \n\n");
         }		
	}
	 
	 private void manageAdminOptions() {
		
			//admins options to view, add and delete from the system(staff, admin or candidate)			
		 	System.out.println("To manage the system, enter \"add\" to add:\n\t\t\"delete\"to delete:\n\t\t\"view\" to view:");
		 	
	          String input = getInput();	          
	            if (input.equalsIgnoreCase("add")){	            	
	            	 System.out.println("To add, please enter \"staff\" :\n\t\t\"admin\":\n\t\t\"candidate\":\n\t\t\"voterange\"");
	            	  input = getInput();
	            	  
	 	            if (input.equalsIgnoreCase("staff")){
	 	            	
	  	            	//add staff
	 	            	addStaff();	 	            	
	 	            }
	 	            
	 	           else if (input.equalsIgnoreCase("admin")){
	 	        	   
	 	        	   //add admin
	 	        	  addAdminUser();		                
		            }
		            
		            else if(input.equalsIgnoreCase("candidate")){
		                
		            	//add candidate
		            	addCandidate();
		            }
		            else if(input.equalsIgnoreCase("voterange")){
		                
		            	//add candidate
		            	createVoteRange();
		            }
		            else{
		                System.out.println("Cannot understand your input, please re-enter : \n\n");
		            }
	            	
	            }
	            else if (input.equalsIgnoreCase("delete")){
	            	System.out.println("To delete, please enter \"staff\" :\n\t\t\"admin\":\n\t\t\"candidate\":");
	            	  input = getInput();
	            	  if (input.equalsIgnoreCase("staff")){
	 	            	
	 	            	//delete staff
	 	            	System.out.println("Enter the staff ID you wish to remove from the system:");
	 	        		 vc.deleteStaff(Integer.parseInt(getInput()));
	 	        		 System.out.print("\nThe selected staff has been successfully deleted!\n");	 	            	
	 	            }
	 	            
	 	           else if (input.equalsIgnoreCase("admin")){
	 	        	   
	 	        	   //deletes admin from the system 	        	 
	 	        		 System.out.println("Enter the admin user ID you wish to remove from the system:");
	 	        		 vc.deleteAdmin(Integer.parseInt(getInput()));
	 	        		 System.out.print("\nThe selected admin has been successfully deleted!\n");		                
		            }
		            
		            else if(input.equalsIgnoreCase("candidate")){
		                
		            	//delete candidate
		            	System.out.println("Enter the candidate code you wish to remove from the system:");
	 	        		 vc.deleteCandidate(Integer.parseInt(getInput()));
	 	        		 System.out.print("\nThe selected candidate has been successfully deleted!\n");
		            }
		            
		            
		            else{
		                System.out.println("Cannot understand your input, please re-enter : \n\n");
		            }	                
	            }
	            
	            else if(input.equalsIgnoreCase("view")){
	            	System.out.println("To view, please enter \"staff\" :\n\t\t\"admin\":\n\t\t\"candidate\":");
	            	  input = getInput();
	            	  
	 	            if (input.equalsIgnoreCase("staff")){
	 	            	
	 	            	//view staff
	 	            	showStaff();	 	            	
	 	            }
	 	            
	 	           else if (input.equalsIgnoreCase("admin")){
	 	        	   
	 	        	   //view admin
	 	        	   showAdmin();		                
		            }
		            
		            else if(input.equalsIgnoreCase("candidate")){
		                
		            	//view candidates
		            	showCandidatesList();
		            }
		            
		            
		            else{
		                System.out.println("Cannot understand your input, please re-enter : \n\n");
		            }	                
	            }
	            	            
	            else{
	                System.out.println("Cannot understand your input, please re-enter : \n\n");		
	            }	            
	}

	//manage help option	 
	 private boolean manageHelpOption()
	 {
	    boolean adminQuit = false;
	    boolean systemQuit = false;
	
	    while (!adminQuit){
	        System.out.println("\nTo view the list of candidates, please enter \"LC\" to exit or \"X\" to exit:");
	        String input = getInput();
	        
	       if(input.equalsIgnoreCase("X")){       	   
	                //exit help option 
	                adminQuit = true;                                   
	        }
	       
	       else if(input.equalsIgnoreCase("LC")){
	           helpOptionCandidateList();             	
	       	}
	       
	        else{
	            System.out.println("I cannot understand your input. Please re-enter: \n\n");
	        }
	    }
	    return systemQuit;
	 }

	private void helpOptionCandidateList() {
		
		//candidates list in help option
		 ArrayList<?> candidates = vc.getCandidates();   	 
		 Iterator<?> it = candidates.iterator();
		     	
	     System.out.println("\n\n\t\t>>>>>>>>>>>> List of Candidates <<<<<<<<<<<<<<<<<<\n\n");    
	     System.out.println("\t\tCode\t\t\tName");
	     System.out.println("\t\t____\t\t\t____\n");    	 
		 
		 while(it.hasNext())
		 {
			 theCandidate = (Candidate) it.next();    		 
		 }
		 
		 it = candidates.iterator();
		 while(it.hasNext())
		 {
			 theCandidate = (Candidate) it.next();             
	         System.out.println("\t\t"+theCandidate.getCandidateCode() + "\t\t\t" + theCandidate.getName());             
		 }		
	}

	private void addAdminUser()
	 {
		//create new admin profile
		String ID, name, username, password;
		System.out.println("\nTo create admin new profile, enter the details in the following prompts\n");
		
		try {
			System.out.println("\nAdmin ID: "); //prompts for admin ID
		     ID = getInput();
			
			// Enter new admin Name
			System.out.println("\nAdmin name: ");
			name = getInput();// get input
			
			// new admin username
			System.out.println("\nAdmin username:");
			username = getInput();// get input
			
			// new admin password
			System.out.println("\nAdmin password: ");
			password= getInput();
			vc.createAdmin(Integer.parseInt(ID), name,username, password);//Creates admin new profile
			System.out.println("\nAdmin created successfully!");
			manageSystemAdmin();
		} 
		
		catch (Exception e) {
			System.out.println(e);
		}		 
	}

	private void addCandidate() {
		String candidateCode, name, votes;
		System.out.println("\nTo create candidate new profile, enter the details in the following prompts\n");
		
		try {
			System.out.println("\nCandidate code: ");
		     candidateCode = getInput();
			 
			System.out.println("\nCandidate name: ");
			name = getInput();// get input
						
			System.out.println("\nVoteCode(0):");
			votes = getInput();// get input
						
			vc.createCandidate(Integer.parseInt(candidateCode), name, Integer.parseInt(votes));//Creates candidate new profile
			System.out.println("\nCandidate added successfully!");
			manageSystemAdmin();			
		} 
		catch (Exception e) {
			System.out.println("File cannot be written to. Please contact your administrator.");
		}	 
	}	
		
	 private void addStaff()
	 {
		String id, name, password,voted,timeStamp;
				
		System.out.println("\nTo create staff new profile, enter the details in the following prompts\n");
		
		try {
			System.out.println("\nStaff ID: ");
		     id = getInput();
			
			// Enter new staff Name
			System.out.println("\nStaff name: ");
			name = getInput();// get input
			
			
			System.out.println("\nStaff password:");
			password = getInput();// get input
			
			
			System.out.println("\nVote code(0): ");
			voted= getInput();
			
			System.out.println("\nTimestamp code(t): ");
			timeStamp= getInput();
			
			vc.createStaff(Integer.parseInt(id), name,password, Integer.parseInt(voted), timeStamp);// get input
			System.out.println("\nAdmin created successfully");
			manageSystemAdmin();
		} 
		
		catch (Exception e) {
			System.out.println("File cannot be written to. Please contact your administrator.");
		}		 
	}
	
	 private void showAdmin(){
		ArrayList<?> administrator = vc.getAdministrator();
	
	 	Iterator<?> it = administrator.iterator();
	
	
	    System.out.println("\n\n\t\t>>>>>>>>>>>> Administration Personnel <<<<<<<<<<<<<<<<<<\n\n");    
	    System.out.println("\tID\tName\t\t\tUsername\tPassword");
	    System.out.println("\t____\t____\t\t\t_____\t\t____\n");
	
	 	it = administrator.iterator();
	 	while(it.hasNext())
	 	{
	 		theAdmin = (Admin) it.next();           
	        System.out.println("\t"+theAdmin.getID() + "\t" + theAdmin.getName() + "\t\t" +
	        theAdmin.getUsername() +"\t\t"+ theAdmin.getPassword() );
	 	}
		
	}

	private void showCandidatesList() {
		ArrayList<?> candidates = vc.getCandidates();
		int candidateVotes = 0;  	 
   	 
   	 	Iterator<?> it = candidates.iterator();
   	 	
        System.out.println("\n\n\t\t>>>>>>>>>>>> List of Candidates <<<<<<<<<<<<<<<<<<\n\n");    
        System.out.println("\tCode\tName\t\t\tVotes");
        System.out.println("\t____\t____\t\t\t_____\t\t\n");
   	 
   	 	it = candidates.iterator();
   	 	while(it.hasNext())
   	 	{
   	 		theCandidate = (Candidate) it.next();
            candidateVotes = theCandidate.getVotes();
            System.out.println("\t"+theCandidate.getCandidateCode() + "\t" + theCandidate.getName() + "\t\t" +
            candidateVotes +"\t\t\n" );
   	 	}	
	}

	 private void showStaff(){
		ArrayList<?> staffs = vc.getStaff();
   	 
   	 	Iterator<?> it = staffs.iterator();
   	 
        System.out.println("\n\n\t\t>>>>>>>>>>>> List of Staff <<<<<<<<<<<<<<<<<<\n\n");    
        System.out.println("\tID\tName\t\t\tPassword\tVoted\t\tDate Voted");
        System.out.println("\t____\t____\t\t\t_____\t\t____\t\t__________\n");  	 
   	 
   	 	it = staffs.iterator();
   	 	while(it.hasNext())
   	 	{
   	 		theStaff = (Staff) it.next();
           
            System.out.println("\t"+theStaff.getId() + "\t" + theStaff.getName() + "\t\t" +
            theStaff.getPassword() +"\t\t"+ theStaff.hasVoted()+ "\t\t" + theStaff.getTimeStamp());
   	 	}		
	}
	
	 //screen input reader. This method is complete and working ok.
     private String getInput()
     {
        String theInput = "";

        try
        {
            theInput = in.readLine().trim();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        return theInput;
     }
	 
   //=======================================================================
	
		
	 //=======================================================================
	//manages a staff vote 
	 private void getStaffVote()
	 {
	    // Use this methods to  capture and confirm a vote for staff
		 int candidateCode;
		 Boolean tryAgain = true;
		 
		 displayVotingScreen();
		 
		 while (tryAgain)
		 {
			 // then display candidate names and instructions on how to place a vote.	
			 System.out.print("\nPlease enter your \"CANDIDATE's CODE\",\n \"Q\" to quit or \"C\" to cancel: \n");
			 
			 
			 try
			 {
				 String input = getInput();
				 
				 if (input.equalsIgnoreCase("Q"))
				 {
					 tryAgain = false;
				 }
				 
				 else  if (input.equalsIgnoreCase("C"))
				 {
					displayVotingScreen();
					tryAgain = true;
				 }
				  
				 
				 
				 else
				 {
					 candidateCode = Integer.parseInt(input);
					 theCandidate = vc.getCandidate(candidateCode);
					 System.out.print ("\nYou have voted for " + theCandidate.getName()+ ". "
					 		+ "\n\nEnter  Y to confirm or any other key to Cancel, then press ENTER : ");
					 					 
					 if (getInput().equalsIgnoreCase("Y"))
					 {
						 vc.recordVote();
						 System.out.println("\nThank you for voting.");						 
						 tryAgain = false;
					 }					 
				 }						 
			 }
			 
			catch(NumberFormatException e)
	     	{
	     		System.out.println("Invalid entry. Please try again");	
	     	}
			 
			catch(NullPointerException e)
	      	{
	      		System.out.println("Invalid entry. Please try again");	
	      	}
			 
			catch(Exception e)
	       	{
	       		System.out.println("Invalid entry. Please try again or contact the administrator");	
	       	}
			 
		 }
		 
		 
	 }

	//=======================================================================
	
	 //=======================================================================
	 public void manageStaffLogin()
    {
		 boolean next = false;

	        //loop for each voter
	        while (next == false)
	        {
	            try
	            {
		        	
		        		System.out.print("\nPlease enter your staff ID:");
		        		theStaff = vc.getStaff(Integer.parseInt(getInput()));

		                if(theStaff.hasVoted() == 1)
		                {
		                    System.out.println("\nYou have already voted!");
		                    next = true;
		                }
		                else if (theStaff.hasVoted() == 0)
		                {
		                	String password = null;
		                	System.out.print("\nPlease enter your password: ");
		                	password = getInput().trim();
		                	
		                	if ((theStaff.getPassword()).equals(password)){
		                		getStaffVote();
			                    next = true;
		                	}
		                	
		                	else{
		                		System.out.println("Incorrect password. Please try again");{	                		
		                		}
		                		++loginCounter;
		                		if (loginCounter == 3){
		                			next=true;
		                			System.out.println("\nYou have exceeded the number of login attempts. \nGoing back to main menu...\n");	
		                		}
		                	}	                    
		                }
		                else
		                {
		                    System.out.println("There seems to be a problem. Please contact your administrator");
		                    
		                }
		        	}
	            catch(NumberFormatException e)
	            {
	                 System.out.println("Invalid entry. \nPlease try again");	                 
	                 ++loginCounter;
	                 if (loginCounter == 3){
	                	 next=true;
	                	 System.out.println("\nYou have exceeded the number of login attempts. \nGoing back to main menu...\n");
	                 }
	            }
	             catch(NullPointerException e)
	             {
	                  System.out.println("Error! Staff ID not found.\nPress ENTER to try again or  \"q\" to QUIT :  ");
	                  	++loginCounter;
		                 if (loginCounter == 3){
		                	 next=true;
		                	 System.out.println("\nYou have exceeded the number of login attempts. \nPress ENTER to go back to main menu...\n");
		                 }
		                 
	                  
	                     if ("q".equalsIgnoreCase(getInput()))
	                     {
	                         System.out.println("Goodbye!");
	                         next = true;
	                     }
	             }

	                
	            }
    			
	        System.out.print("\nGoodbye!\n");
	            
	         }
	        
   //=======================================================================


    //=======================================================================
     //prints out the voting results after a successful admin login
     public void printVoteResults()
     {
    	 ArrayList<?> candidates = vc.getCandidates();
    	 int totalCandidates = vc.getTotalCandidates();
    	 int totalVoters = vc.getTotalVoters();
    	 double totalVoted = 0;
    	 double totalNotVoted = 0;
    	 int candidateVotes = 0;
    	
    	 DecimalFormat df = new DecimalFormat("###.##");
    	 
    	 Iterator<?> it = candidates.iterator();
    	 
         System.out.println("\n\n\n\t\t>>>>>>>>>>>> VOTING STATISTICS <<<<<<<<<<<<<<<<<<\n\n");
         
         if(hasDateSet){
        	 System.out.printf(" Dates have been set to:\n %s - %s\n", startDate.toString(), endDate.toString()+"\n");
         }
         else{
        	 System.out.println("No date set yet\n");
         }
         
         System.out.println("\tCode\tName\t\t\tVotes\t\t(%)");
         System.out.println("\t____\t____\t\t\t_____\t\t______\n");
    	 
    	 while(it.hasNext())
    	 {
    		 theCandidate = (Candidate) it.next();
    		 totalVoted += theCandidate.getVotes(); //counts total number of votes
    	 }
    	 
    	 totalNotVoted = totalVoters - totalVoted;
    	 
    	 it = candidates.iterator();
    	 while(it.hasNext())
    	 {
    		 theCandidate = (Candidate) it.next();
             candidateVotes = theCandidate.getVotes();
             System.out.println("\t"+theCandidate.getCandidateCode() + "\t" + theCandidate.getName() + "\t\t" +
             candidateVotes +"\t\t(" + df.format((candidateVotes/totalVoted)*100) +"%)");
    	 }
    	 
    	 System.out.println("\nNumber of candidates:" +totalCandidates);
    	 System.out.println("Number of voters:" +totalVoters);
    	 System.out.println("Numbers voted: " +totalVoted + "(" +df.format((totalVoted/totalVoters)*100)+ "%)");   
    	 System.out.println("Numbers not voted: " +totalNotVoted + "(" +df.format((totalNotVoted/totalVoters)*100)+ "%)\n\n");
    }
     
	 // new boolean method called checkDateRange
     
     private void createVoteRange()
	 {
		 try{
			 // prompt user to enter start date		 	
			System.out.println("\nPlease enter start date:\n");
			startDate = sdf.parse(getInput());
			
			System.out.println("\nPlease enter end date:\n");
			endDate = sdf.parse(getInput());
		    // set the end date as the input
			// ok dates have been set
			// print out the dates
			System.out.printf("Dates have been set to: %s - %s", startDate.toString(), endDate.toString());
			hasDateSet = true;
			commenceVoting();
		 }
		 catch(Exception e){
			 System.out.print(e);
		 }				 
	 }

	private boolean checkDateRange(Date testDate){
    	return !testDate.before(startDate) && !testDate.after(endDate);
    	
    	
        // takes a date as an input
        // checks if that date is within the date range
        // date range is checked by using testDate.before(startDate) and testDate.after(endDate)
        // if it is not within range, return false, otherwise return true (same)
        // OR if it is within range, return true, otherwise return false (same)
    	
     }
	
	

	//=======================================================================
	
	 private void overallSystemReport()
	 {
		 ArrayList<?> candidates = vc.getCandidates();
		 int totalVoters = vc.getTotalVoters();
		 double totalVoted = 0;
		 double totalNotVoted = 0;
		 int candidateVotes = 0;	
		 int totalCandidates = vc.getTotalCandidates();
		
	
		 DecimalFormat df = new DecimalFormat("###.##");
	
		 Iterator<?> it = candidates.iterator();
		
		 System.out.println("\n\n\n\t\t============= SYSTEM REPORT ================\n\n");
		 
		 if(hasDateSet){
        	 System.out.printf(" Dates have been set to:\n %s - %s\n", startDate.toString(), endDate.toString()+"\n");
         }
         else{
        	 System.out.println("No date set yet\n");
         }
		 
		 System.out.println("\n\n\n\t\t>>>>>>>>>>>> Candidates <<<<<<<<<<<<<<<<<<\n\n");	
		 System.out.println("\tCode\tName\t\t\tVotes\t\t(%)");
		 System.out.println("\t____\t____\t\t\t_____\t\t______\n");
	
	 while(it.hasNext())
	 {
		 theCandidate = (Candidate) it.next();
		 totalVoted += theCandidate.getVotes(); //counts total number of votes
	 }
	 totalNotVoted = totalVoters - totalVoted;
	
	 it = candidates.iterator();
	 while(it.hasNext())
	 {
		 theCandidate = (Candidate) it.next();
	     candidateVotes = theCandidate.getVotes();
	     System.out.println("\t"+theCandidate.getCandidateCode() + "\t" + theCandidate.getName() + "\t\t" +
	     candidateVotes +"\t\t(" + df.format((candidateVotes/totalVoted)*100) +"%)");
	 }
		
	 showStaff();
	
	 System.out.println("\nNumber of candidates:" +totalCandidates);
	 System.out.println("Numbers of voters:" +totalVoters);
	 System.out.println("Numbers voted: " +totalVoted + "(" +df.format((totalVoted/totalVoters)*100)+ "%)");
	 System.out.println("Numbers not voted: " +totalNotVoted + "(" +df.format((totalNotVoted/totalVoters)*100)+ "%)\n\n");
	
		
	 }


	
    //=======================================================================

	 
	 

}
