//Note: This class is complete. Don’t change code in this class,
//unless you want to add more functionality  for Staff object

package EpicVotingSystem;



/**
 * File Name :
 * author :
 * Date :
 * Description :
 */
public class Staff
{
    private int id;
    private String name;
    private int voted; //has the staff voted
    //private int notVoted;
    private String password;
    private String timeStamp;

    public Staff(int id, String name, String password,int voted, String timeStamp)
    {
            this.id = id;
            this.name = name;
            this.voted = voted;
            //this.notVoted = notVoted;
            this.password = password;
            this.setTimeStamp(timeStamp);
            
    }

    public void setId(int id)
    {
       this.id = id;
    }

    public void setName(String name)
    {
            this.name = name;
    }

    public void setVoted()
    {
            this.voted = 1;
    }

    public int getId()
    {
       return id;
    }

    public String getName()
    {
            return name;
    }

    public int hasVoted()
    {
            return voted;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

	
}
