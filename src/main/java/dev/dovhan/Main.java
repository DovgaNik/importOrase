package dev.dovhan;

import com.opencsv.*;
import com.opencsv.exceptions.*;

import java.sql.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws SQLException {
		Connection connection = null;
		try {
			connection = ConnectionProvider.getConnection();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		List<String> addedATU = new ArrayList<>();
		try {
			CSVReader reader = new CSVReaderBuilder(new FileReader("orase-date.csv")).build();
			String[] nextLine;
			reader.readNext();
			while ((nextLine = reader.readNext()) != null) {

				if (!addedATU.contains(nextLine[2])) {
					System.out.println(nextLine[2] + " doesn't exist in db, adding...");
					String sql = "INSERT INTO atu (name, code_of_country, atu_type_id) VALUES (?, ?, ?)";
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setString(1, nextLine[2]);
					statement.setString(2, "RO");
					statement.setInt(3, 2);
					statement.execute();
					addedATU.add(nextLine[2]);
				}

				String sql = "SELECT id FROM atu WHERE name = ?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, nextLine[2]);
				statement.execute();
				ResultSet result = statement.getResultSet();
				result.next();
				int atuID = result.getInt("id");
//				System.out.println(nextLine[2] + " is " + atuID);
				System.out.println(nextLine[1] + atuID);


				sql = "INSERT INTO cities (name, atu_id, population) VALUES (?, ?, ?)";
				statement = connection.prepareStatement(sql);
				statement.setString(1, nextLine[1]);
				statement.setInt(2, atuID);
				try {
					statement.setInt(3, Integer.getInteger(nextLine[3]));
				} catch (Exception e){
					statement.setInt(3, 0);
				}
				statement.execute();

			}
		} catch (IOException | CsvValidationException | SQLException e){
			System.out.println(e.getMessage());
		}

		connection.close();
	}
}