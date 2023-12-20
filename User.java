// Enum created for user types
enum UserType {
    ADMIN,
    REGULAR
}

// User class
class User{
	// Class attributes
    private int UserID;
    private String FirstName;
    private String LastName;
    private String email;
    private String Password;
    private int UserType;
    private String Address;
    private double grossIncome;
    private double taxCredits;
    private double taxOwed;

    // Constructor 
    public User(int UserID, String FirstName, String LastName, String Address, int UserType, String email, String Password) {
        this.UserID = UserID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.email = email;
        this.Password = Password;
        this.UserType = UserType;
        this.Address=Address;
    }
    
    // getter and setters
    public void setgrossIncome(double GrossIncome) {
    	this.grossIncome=GrossIncome;
    }
    
    public void settaxCredits(double taxCredits) {
    	this.taxCredits=taxCredits;
    }
    
    public Double getgrossIncome() {
    	return grossIncome;
    }
    
    public Double gettaxCredit() {
    	return taxCredits;
    }
    
    public int getUserID() {
        return UserID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getPassword() {
        return Password;
    }

    public String getemail() {
        return email;
    }

    public String getLastName() {
        return LastName;
    }
    
    public String getAddress() {
        return Address;
    }

    public int UserType() {
        return UserType;
    }
}


