package com.testing.amazon.tests.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class DatabaseBaseTest {
	public static Connection connection;
	private static final String URL = "jdbc:mysql://localhost:3306/AmazonDB";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password12345";
	
	@BeforeClass
    public static void setUp() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle connection errors
        }
    }

    @AfterClass
    public static void tearDown() {
        if (connection != null) {
        	try (Statement statement = connection.createStatement()) { 
                // Drop tables in reverse order
        		System.out.println("Check the table manually before droping it");
        		Scanner sc = new Scanner(System.in);
        		sc.nextLine();
        		sc.close();
                statement.executeUpdate("DROP TABLE IF EXISTS OrderDetails");
                statement.executeUpdate("DROP TABLE IF EXISTS SalesTable");
                statement.executeUpdate("DROP TABLE IF EXISTS OrderTable");
                statement.executeUpdate("DROP TABLE IF EXISTS CustomerTable");
                statement.executeUpdate("DROP TABLE IF EXISTS Product");

                System.out.println("Tables dropped successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle errors in dropping tables
            }
            try {
                connection.close();
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle closing connection errors
            }
        }
    }
    
    public static int insertFromCSV(String filePath, String tableName) {
        try {
            List<String> columns = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                // Fetch column names from the header row
                String header = reader.readLine();
                if (header != null) {
                    String[] columnNames = header.split(",");
                    columns.addAll(Arrays.asList(columnNames));
                }
            }

            String insertQuery = generateInsertQuery(tableName, columns);
            int counter = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                 PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                String line;
                boolean headerSkipped = false;

                while ((line = reader.readLine()) != null) {
                    if (!headerSkipped) {
                        headerSkipped = true; // Skip header row
                        continue;
                    }
                    counter++;

                    String[] values = line.split(",");

                    for (int i = 0; i < values.length; i++) {
                        preparedStatement.setObject(i + 1, values[i]);
                    }

                    preparedStatement.addBatch();
                }

                preparedStatement.executeBatch();
            }
            return counter;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static String generateInsertQuery(String tableName, List<String> columns) {
        StringJoiner columnNames = new StringJoiner(", ", "(", ")");
        StringJoiner placeholders = new StringJoiner(", ", "(", ")");

        for (String column : columns) {
            columnNames.add(column);
            placeholders.add("?");
        }

        return "INSERT INTO " + tableName + columnNames.toString() + " VALUES " + placeholders.toString();
    }
    
    public int getRowCount(String tableName) {
        int count = 0;
        try (
            Statement statement = connection.createStatement()) {
            String countQuery = "SELECT COUNT(*) FROM " + tableName;
            ResultSet resultSet = statement.executeQuery(countQuery);
            if (resultSet.next()) {
                count = resultSet.getInt(1); // Retrieve the count from the first column of the result
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
	        e.printStackTrace();
	    }
        return count;
    }

    

}
