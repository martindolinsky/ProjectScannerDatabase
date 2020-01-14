package sk.itsovy.dolinsky.scannerdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * @author Martin Dolinsky
 */
public class Database implements DatabaseMethods {
	private final static String ANSI_RESET = "\u001B[0m";
	private final static String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_YELLOW = "\033[0;33m";
	private static final String RED_BOLD = "\033[1;31m";
	private static final String GREEN_BRIGHT = "\033[0;92m";
	private final String JDBC = "com.mysql.cj.jdbc.Driver";
	private final String URL = "jdbc:mysql://localhost:8889/world_x?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private Connection connection;

	@Override
	public Connection getConnection() throws Exception {
		Class.forName(JDBC); // adding JDBC driver to Java
		connection = DriverManager.getConnection(URL, "root", "root");
		return connection;
	}

	@Override
	public void selectSlovakCities() throws Exception {
		PreparedStatement statement = getConnection().prepareStatement(
				"SELECT Name AS City, district,countrycode AS Country," +
				"json_extract(city.Info,'$.Population') AS Population FROM city WHERE " +
						"countryCode like 'SVK';");

		ResultSet rs = statement.executeQuery();
		System.out.println("\nList of SVK countries: ");
		while (rs.next()) {
			System.out.println(rs.getString("City") + "     "
					+ rs.getString("district") + "     "
					+ rs.getString("Country") + "     "
					+ rs.getString("Population"));
		}
		connection.close();
	}

	@Override
	public void updateSlovakCity() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.print(ANSI_BLUE + "Enter name of city you want to update: ");
		String city = sc.nextLine();
		System.out.print("Enter new population: " + ANSI_RESET);
		String population = sc.nextLine();
		PreparedStatement statement = getConnection().prepareStatement("" +
				"UPDATE City SET Info = ? WHERE Name LIKE ?");
		statement.setString(1, "{\"Population\": " + population + "}");
		statement.setString(2, city);
		statement.execute();
		System.out.println(GREEN_BRIGHT + "Population updated!" + ANSI_RESET);
		connection.close();
	}

	@Override
	public void deleteSlovakCity() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.print(ANSI_BLUE + "Enter name of City you want to delete: " + ANSI_RESET);
		String name = sc.nextLine();
		System.out.println(ANSI_YELLOW + "Are you sure? You want to delete this city?" + ANSI_RESET);
		String answer = sc.nextLine();
		int count = 0;
		if (answer.equals("yes") || answer.equals("y")) {
			PreparedStatement statement = getConnection().prepareStatement(
					"SELECT Name AS City,countrycode AS Country," +
					"json_extract(city.Info,'$.Population') AS Population FROM city WHERE " +
							"countryCode like 'SVK';");
			ResultSet rs = statement.executeQuery();
			if (!rs.next()) {
				System.out.println(RED_BOLD + "Incorrect input! Try again!" + ANSI_RESET);
			} else {
				count++;
				if (count > 0) {
					PreparedStatement delete = getConnection().prepareStatement("" +
							"DELETE FROM city WHERE Name LIKE ? AND countryCode LIKE 'SVK'");
					delete.setString(1, name);
					delete.execute();
					System.out.println(GREEN_BRIGHT + "\nSuccessfully removed." + ANSI_RESET);
				}
			}
		} else {
			System.out.println(RED_BOLD + "Not removed." + ANSI_RESET);
		}
		connection.close();
	}

	@Override
	public void insertSlovakCity() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.print(ANSI_BLUE + "Enter name of City you want to insert: ");
		String name = sc.nextLine();
		System.out.print("Enter District: ");
		String district = sc.nextLine();
		System.out.print("Enter country code: ");
		String countryCode = sc.nextLine();
		System.out.print("Enter population: " + ANSI_RESET);
		String population = sc.nextLine();
		PreparedStatement select = getConnection().prepareStatement(
				"SELECT * FROM city WHERE Name LIKE ?");
		select.setString(1, name);
		int count = 0;
		ResultSet rs = select.executeQuery();

		if (rs.next()) {
			count++;
			System.out.println(RED_BOLD + "\nCity already exists!" + ANSI_RESET);
		}
		if (count == 0) {
			PreparedStatement insert = getConnection().prepareStatement(
					"INSERT INTO city (Name, CountryCode, District, Info) VALUES (?, ?, ?, ?)");
			insert.setString(1, name);
			insert.setString(2, countryCode);
			insert.setString(3, district);
			insert.setString(4, "{\"Population\": " + population + "}");
			insert.execute();
			System.out.println(GREEN_BRIGHT + "\nSuccessfully inserted." + ANSI_RESET);
		}
		connection.close();
	}
}
