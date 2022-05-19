import java.sql.*;
import java.util.Scanner;


public class Program
{
	static Connection connection = null;
	static PreparedStatement preparedStatement = null;
	static String currentUser;
	static Scanner scanner = new Scanner(System.in);
	static String getUserQuery = "SELECT * FROM users WHERE login = ?";
	static String firstName;
	static String lastName;
	
	public static void main(String[] args)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/bank", "root", "root");
			showLogin();
			showMenu();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void showLogin(){
		String correctPassword = null;
		while(true)
		{
			System.out.println("Введите логин:");
			String login = scanner.nextLine();
			try
			{
				preparedStatement = connection.prepareStatement(getUserQuery);
				preparedStatement.setString(1, login);
				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					currentUser = login;
					correctPassword = resultSet.getString("password");
					firstName = resultSet.getString("firstName");
					lastName = resultSet.getString("lastName");
					resultSet.close();
					break;
				}
				else {
					System.out.println("Такого пользователя не существует, повторите ввод логина!");
					resultSet.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		while(true)
		{
			System.out.println("Введите пароль:");
			String password = scanner.nextLine();
			if(password.equals(correctPassword))
			{
				System.out.println("Добро пожаловать, " + firstName + " " + lastName);
				break;
			}
			else
			{
				System.out.println("Вы ввели неправильный пароль. Повторите ввод!");
			}
		}
	}
	
	public static void showMenu(){
		
	}
}
