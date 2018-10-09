package EpicVotingSystem;
/**
 * File Name :
 * author :Aljane Walsh
 * Date :
 * Description :
 */
import java.io.*;
import java.util.*;



public class VotingController
{
    //Create an Arraylist read & store staff & candidate data from file
    private ArrayList<Staff> staffs = new ArrayList<Staff>();
    private ArrayList<Candidate> candidates = new ArrayList<Candidate>();
    private ArrayList<Admin> administrator = new ArrayList<Admin>();

   //Type to access individual staff & candidate from array list
    private Staff theStaff;
    private Candidate theCandidate;
    private Admin theAdmin;

    //VotingController constructor
    public VotingController()
    {
        loadStaffData();
        loadCandidateData();
        loadAdminData();
    }

    //loads candidates from file. This method is complete and working ok.
    public void loadCandidateData()
    {
        try
        {
             String fileName = "candidates.txt";
             File theFile = new File(fileName);
             BufferedReader reader = new BufferedReader(new FileReader(theFile));

             String candidateData;
             
             while((candidateData = reader.readLine())!= null)
             {
                 String[] candidateDetails = candidateData.split(",");
                 int code = Integer.parseInt(candidateDetails[0]);
                 int votes = Integer.parseInt(candidateDetails[2]);
                 theCandidate = new Candidate(code, candidateDetails[1], votes);
                 candidates.add(theCandidate);
             }
             reader.close();
         }
         catch(IOException e)
         {
             System.out.println("Error! There was a problem with loading candidate names from file");
         }
    }
    
    public void loadAdminData()
    {
    	try
    	{
    		String fileName = "admin.txt";
    		File theFile = new File(fileName);
            BufferedReader reader = new BufferedReader(new FileReader(theFile));

            String adminData;
            

            while((adminData = reader.readLine())!= null)
            {
            	String[] adminDetails = adminData.split(",");
                int code = Integer.parseInt(adminDetails[0]);
                String nameAdmin = adminDetails[1];
                String usernameAdmin = adminDetails[2];
                String passwordAdmin = adminDetails[3];
                
                theAdmin = new Admin(code,nameAdmin,usernameAdmin,passwordAdmin);
                administrator.add(theAdmin);
            }
            reader.close();
        }
        catch(IOException e)
        {
            System.out.println("Error! There was a problem with loading candidate names from file");
        }    				
    }
    
    //=================================================================
	
	//=================================================================
	//loads staff names from file.
	public void loadStaffData()
	{
	    // Assignment 2 Note : use this method to read data from staff text file and store in arraylist .
		// Write the code for this method by using loadCandidateData() as sample syntax
		try
	    {
	         String fileName = "staff.txt";
	         File theFile = new File(fileName);
	         BufferedReader reader = new BufferedReader(new FileReader(theFile));
	
	         String staffData;
	         	
	         while((staffData = reader.readLine())!= null)
	         {
	        	 String[] staffDetails = staffData.split(",");
	             int id = Integer.parseInt(staffDetails[0]);
	             int voted = Integer.parseInt(staffDetails[2]);
	             theStaff = new Staff(id, staffDetails[1], staffDetails[3], voted, staffDetails[4]);
	             staffs.add(theStaff);
	         }
	         reader.close();
	     }
	     catch(IOException e)
	     {
	         System.out.println("Error! There was a problem with loading candidate names from file");
	     }
		catch(NumberFormatException e){
			System.out.println("Sorry, there was a problem converting a property to a number");
			System.out.println(e);
		}
	}

	//returns a staff if found in the staffs ArrayList
    public Staff getStaff(int id)
    {
        Iterator<Staff> it = staffs.iterator();
        while(it.hasNext())
        {
            theStaff = (Staff) it.next();
            if(theStaff.getId()== id)
            {
                return theStaff;
            }
        }
        return null;
    }

	
    //=================================================================
	
	//=================================================================
	public Candidate getCandidate(int candidateCode)
	{
	    // Assignment 2 Note : use this method to return the candidate if found in the candidates ArrayList
	    // Write the code for this method by using getStaff() as sample syntax
		Iterator<Candidate> it = candidates.iterator();
	    while(it.hasNext())
	    {
	        theCandidate = (Candidate) it.next();
	        if(theCandidate.getCandidateCode()== candidateCode)
	        {
	            return theCandidate;
	        }
	    }
	    return null;
	
	}

	//=================================================================
	
	
	
	//=================================================================
	
	public Admin getUsernameAdmin(String usernameAdmin)
	{
		Iterator<Admin> it = administrator.iterator();
		while(it.hasNext())
		{
			theAdmin = (Admin) it.next();
			if(theAdmin.getUsername()== usernameAdmin);
			{
				return theAdmin;
			}			
		}
		return null;		
	}

	//=================================================================
	
	public ArrayList<Admin> getAdministrator()
	{
		return administrator;
	}

	//returns the collection of candidates
    public ArrayList<Candidate> getCandidates()
    {
        return candidates;
    }
    
    public ArrayList<Staff> getStaff()
    {
    	return staffs;
    }

    //=================================================================
	
	//returns total number of staffs in the collection
    public int getTotalVoters()
    {
        return staffs.size();
    }

    public int getTotalCandidates()
    {
    	return candidates.size();
    }

    //=================================================================
	
	

	//=================================================================
	
	private void saveAdminData() {
		try
	    {
	        BufferedWriter writer = new  BufferedWriter(new FileWriter("admin.txt"));
	        Iterator<Admin> it = administrator.iterator();
	        String adminDetails;
	        while(it.hasNext())
	        {
	            theAdmin = (Admin) it.next();
	            adminDetails = theAdmin.getID() + "," + theAdmin.getName() + "," + theAdmin.getUsername() +"," +theAdmin.getPassword() +"\n"; //getting the admin details, putting it together as a string
	            writer.write(adminDetails); // writing this new admin detail string to the admin.txt file
	        }
	        writer.close();
	    }
	    catch(IOException e)
	    {
	        System.out.println(e);
	    }
		
	}

	//writes staffs back to file
    public void saveStaffData()
    {
        try
        {
            BufferedWriter writer = new  BufferedWriter(new FileWriter("staff.txt"));
            Iterator<Staff> it = staffs.iterator();
            String staffDetails;
            while(it.hasNext())
            {
                theStaff = (Staff) it.next();
                staffDetails = theStaff.getId() + "," +theStaff.getName() + "," + theStaff.hasVoted()+ "," +theStaff.getPassword() +"," +theStaff.getTimeStamp()+"\n";
                writer.write(staffDetails);
            }
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    //=================================================================
    //writes candidates date back to file
    public void saveCandidateData()
    {
        // Assignment 2 Note : use this method to write data back to candidate text file
        // Write the code for this method by using saveStaffData() as sample syntax
    	 try
         {
             BufferedWriter writer = new  BufferedWriter(new FileWriter("candidates.txt"));
             Iterator<Candidate> it = candidates.iterator();
             String candidateDetails;
             while(it.hasNext())
             {
                 theCandidate = (Candidate) it.next();
                 candidateDetails = theCandidate.getCandidateCode() + "," +theCandidate.getName() + "," + theCandidate.getVotes() +"\n";
                 writer.write(candidateDetails);
             }
             writer.close();
         }
         catch(IOException e)
         {
             System.out.println(e);
         }
    	
    }
    //=================================================================
	
    public void createAdmin(int id, String name, String username, String password) {
		theAdmin = new Admin(id, name,username, password);
		 administrator.add(theAdmin);
		 saveAdminData();
		
	}

	public void createStaff(int id, String name, String password, int voted, String timeStamp) {
		theStaff = new Staff(id, name,password, voted,timeStamp);
		 staffs.add(theStaff);
		 saveStaffData();
		
	}

	public void createCandidate(int id, String name, int votes) {
		theCandidate = new Candidate(id, name, votes);
		 candidates.add(theCandidate);
		 saveCandidateData();
		
	}

	public void deleteAdmin(int id) {
		Iterator<Admin> it = administrator.iterator();
        while(it.hasNext())
        {
            theAdmin = (Admin) it.next();
            if(theAdmin.getID()== id)
            {
                administrator.remove(theAdmin);
                break;
            }
        }
        saveAdminData();		
	}

	public void deleteStaff(int id) {
		Iterator<Staff> it = staffs.iterator();
        while(it.hasNext())
        {
            theStaff = (Staff) it.next();
            if(theStaff.getId()== id)
            {
                staffs.remove(theStaff);
                break;
            }
        }
        saveStaffData();
		
	}

	//=================================================================

	//=================================================================
	
	public void deleteCandidate(int candidateCode) {

		Iterator<Candidate> it = candidates.iterator();    
	    while(it.hasNext())
	    {
	        theCandidate = (Candidate) it.next();
	        if (theCandidate.getCandidateCode()==candidateCode) 
	        {
	        	candidates.remove(theCandidate);
	            break;
	        }
	    }
	    saveCandidateData();
	}

	//=================================================================
	
	
	//=================================================================
	
	//every staff vote must be saved to file
	public void recordVote()
	{
		String timestamp = new Date().toString();
		theStaff.setTimeStamp(timestamp);
	    theStaff.setVoted();
	    theCandidate.addVote();
	    saveStaffData();//save to file
	    saveCandidateData();//save to file
	}

	//=================================================================
	

	
	//=================================================================


	

}