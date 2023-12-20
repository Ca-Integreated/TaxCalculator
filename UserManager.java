// Major database attraction and functionality holder class
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class UserManager {
	// DB connection with MYSQL
	private static final String DB_URL = "jdbc:mysql://localhost:3306/TaxDB";
	// DB UserName and password 
	private static final String DB_USER = "ooc2023";
	private static final String DB_PASSWORD = "ooc2023";
	private static double taxOwed;

	// Login function 
	public static User loginUser(String username, String password) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String query = "SELECT * FROM users WHERE email = ? AND Password = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						int UserID = resultSet.getInt("UserID");
						String FirstName = resultSet.getString("FirstName");
						String LastName = resultSet.getString("LastName");
						int UserType = resultSet.getInt("UserType");
						String email = resultSet.getString("email");
						String Address = resultSet.getString("Address");

						return new User(UserID, FirstName, LastName, Address, UserType, email, password);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Signup Function
	public static void Signup() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter First Name: ");
		String firstName = scanner.nextLine();

		System.out.print("Enter Last Name: ");
		String lastName = scanner.nextLine();

		System.out.print("Enter Address: ");
		String address = scanner.nextLine();

		System.out.print("Enter User Type: ");
		Integer userType = scanner.nextInt();

		System.out.print("Enter Email: ");
		String email = scanner.next();

		System.out.print("Enter Password: ");
		String password = scanner.next();

		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String query = "INSERT INTO users (FirstName, LastName, Address, UserType, email, Password) VALUES (?, ?, ?, ?, ?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setString(1, firstName);
				preparedStatement.setString(2, lastName);
				preparedStatement.setString(3, address);
				preparedStatement.setInt(4, userType);
				preparedStatement.setString(5, email);
				preparedStatement.setString(6, password);

				int affectedRows = preparedStatement.executeUpdate();

				if (affectedRows > 0) {
					System.out.println("User registered successfully.");
				} else {
					System.out.println("Failed to register user.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Update user profile functions
	public static void modifyProfile(User user) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter your new First Name: ");
		String newFirstName = scanner.nextLine();
		System.out.print("Enter your new Last Name: ");
		String newLastname = scanner.nextLine();
		System.out.print("Enter your new Address: ");
		String newAddress = scanner.nextLine();
		System.out.print("Enter your new Password: ");
		String newPassword = scanner.nextLine();

		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String query = "UPDATE users SET FirstName = ?, LastName = ?, Password = ?, Address = ? WHERE UserID = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setString(1, newFirstName);
				preparedStatement.setString(2, newLastname);
				preparedStatement.setString(3, newPassword);
				preparedStatement.setString(4, newAddress);
				preparedStatement.setInt(5, user.getUserID());
				preparedStatement.executeUpdate();
				System.out.println("Your Profile updated successfully.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Display All users functions
	public static void listAllUsers() {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String query = "SELECT * FROM users";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					System.out.println("List of all users:");
					while (resultSet.next()) {
						int id = resultSet.getInt("UserID");
						String FirstName = resultSet.getString("FirstName");
						String LastName = resultSet.getString("LastName");
						String Address = resultSet.getString("Address");
						String email = resultSet.getString("email");
						String password = resultSet.getString("Password");
						int type = resultSet.getInt("Usertype");

						System.out.println("User ID: " + id + ", FirstName: " + FirstName + ", LastName: " + LastName + ", Address: "
								+ Address + ", Email: " + email+ ", Password: " + password+ ", UserType: " + type);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Remove user functions
	public static void removeUser() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the ID of the user you want to remove: ");
		int userIdToRemove = scanner.nextInt();

		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String query = "DELETE FROM operations WHERE UserID = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setInt(1, userIdToRemove);
				preparedStatement.executeUpdate();
			}
			String query1 = "DELETE FROM users WHERE UserID = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query1)) {
				preparedStatement.setInt(1, userIdToRemove);
				preparedStatement.executeUpdate();
				System.out.println("User removed successfully.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Review all operations functions
	public static void reviewOperations() {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String query = "SELECT\r\n"
					+ "    users.UserID,\r\n"
					+ "    users.FirstName,\r\n"
					+ "    operations.OperationID,\r\n"
					+ "    operations.TaxOwe\r\n"
					+ "FROM\r\n"
					+ "    users\r\n"
					+ "JOIN\r\n"
					+ "    operations ON users.UserID = operations.UserID;\r\n"
					+ "";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					System.out.println("All operations:");
					while (resultSet.next()) {
						int id = resultSet.getInt("UserID");
						String FirstName = resultSet.getString("FirstName");
						int opid = resultSet.getInt("OperationID");
						double Tax = resultSet.getDouble("TaxOwe");

						System.out.println("User ID: " + id + ", FirstName: " + FirstName + ", OperationID: " + opid + ", Tax owe: "
								+ Tax);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Review the specific operations by using the USER ID for regular users
	public static void reviewSpecificOperations(User user) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String query ="SELECT users.UserID, users.FirstName, operations.OperationID, operations.TaxOwe FROM users JOIN operations "
					+ "ON users.UserID = operations.UserID WHERE users.UserID="+user.getUserID();
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					System.out.println("All operations by you:");
					while (resultSet.next()) {
						int id = resultSet.getInt("UserID");
						String FirstName = resultSet.getString("FirstName");
						int opid = resultSet.getInt("OperationID");
						double Tax = resultSet.getDouble("TaxOwe");

						System.out.println("User ID: " + id + ", FirstName: " + FirstName + ", OperationID: " + opid + ", Tax owe: "
								+ Tax);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Insert the Tax Data into the database calculated by users
	public static void insertTaxData(User user) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String insertQuery = "INSERT INTO operations (GrossIncome, TaxCredit, TaxOwe, UserID) VALUES (?, ?, ?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
				preparedStatement.setDouble(1, user.getgrossIncome());
				preparedStatement.setDouble(2, user.gettaxCredit());
				preparedStatement.setDouble(3, taxOwed);
				preparedStatement.setDouble(4, user.getUserID());
				preparedStatement.executeUpdate();
				System.out.println("Data inserted Suucessfully !! ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Calculate the tax data
	public static void calculateTax(TaxCalculator taxCalculator, User user) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter your gross income: ");
		double grossIncome = scanner.nextDouble();
		user.setgrossIncome(grossIncome);

		System.out.print("Enter your tax credits: ");
		double taxCredits = scanner.nextDouble();
		user.settaxCredits(taxCredits);

		taxOwed = taxCalculator.calculateTax(grossIncome, taxCredits);
	}
	// Dispaly the calculated tax data to the user.
	 public static String toString(User user) {
	        return "Name: " + user.getFirstName() +
	               "\nGross Income: " + user.getgrossIncome() +
	               "\nTax Credits: " + user.gettaxCredit() +
	               "\nTax Owed: " + taxOwed;
	    }
}