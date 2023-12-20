// Main driver class
import java.util.Scanner;

public class usermanagerApp {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// While loop to again display the main menu until user exit
		while (true) {
			System.out.println("Press 1 for Login: ");
			System.out.println("Press 2 for regular user singup: ");
			System.out.println("Press any other key for Exit: ");
			String LoginOptions = scanner.nextLine();

			// when user wants to login to the system
			if (LoginOptions.equals("1")) {

				System.out.print("Enter your UserName: ");
				String username = scanner.nextLine();

				System.out.print("Enter your password: ");
				String password = scanner.nextLine();

				User user = UserManager.loginUser(username, password);
				if (user != null) {
					// if user is admin
					if (user.UserType() == 0) {
						// setting another while loop which will show the Admin functionalities until admin logout
						boolean test=true;
						while(test) {
							System.out.println("************ Welcome Admin ************");
							System.out.println("Press 1 to update your profile");
							System.out.println("Press 2 to Display all system users");
							System.out.println("Press 3 to delete user from system");
							System.out.println("Press 4 to view all tax operations done by users");
							System.out.println("Press 5 to logout");
							String AdminOptions = scanner.nextLine();
							
							// Switch statement will execute the admin functions
							switch (AdminOptions) {

							case "1":
								UserManager.modifyProfile(user);
								break;
							case "2":
								UserManager.listAllUsers();
								break;
							case "3":
								UserManager.removeUser();
								break;
							case "4":
								UserManager.reviewOperations();
								break;
							case "5":
								test=false;
								break;	
							}
						}
					} else {
						// setting another while loop to show the options to regular user until user logout from the system
						boolean test1=true;
						while(test1) {
							System.out.println("Regular user logged in.");
							System.out.println("What you want to do : ");
							System.out.println("Press 1 to update your profile : ");
							System.out.println("Press 2 to use new tax calculation operations : ");
							System.out.println("Press 3 to display all your tax caclulation operations : ");
							System.out.println("Press 4 to logout : ");
							String options = scanner.nextLine();

							switch (options) {
							case "1":
								UserManager.modifyProfile(user);
								break;
							case "2":
								TaxCalculator taxCalculator = new TaxCalculatorImpl();
								UserManager.calculateTax(taxCalculator, user);
								System.out.println(UserManager.toString(user));
								UserManager.insertTaxData(user);
								break;
							case "3":
								UserManager.reviewSpecificOperations(user);
								break;
							case "4":
								test1=false;
								break;
							}
						}
					}
				} else {
					System.out.println("Invalid username or password.");
				}
			} else {
				if (LoginOptions.equals("2")) {
					UserManager.Signup();
				} else {
					System.exit(0);
				}
			}
		}
	}
}
