package com.testing.amazon.tests.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.testng.Assert;
import org.testng.annotations.Test;



public class CRUDTest extends DatabaseBaseTest{
	
	
	@Test
	public void createCustomerTableTest() {
	    try (Statement statement = connection.createStatement()) {
	        String createCustomerTableQuery = "CREATE TABLE CustomerTable (customerId INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), email VARCHAR(255))";
	        int updatedRows = statement.executeUpdate(createCustomerTableQuery);

	        Assert.assertEquals(updatedRows, 0, "CustomerTable creation failed");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        Assert.fail("Failed to create CustomerTable due to database exception");
	    } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
            Assert.fail("Failed to create OrderTable due to Exception");
		}
	}
	
	@Test(dependsOnMethods = "createCustomerTableTest")
	public void createProductTableTest() {
	    try (Statement statement = connection.createStatement()) {
	        String createProductTableQuery = "CREATE TABLE Product (productId INT AUTO_INCREMENT PRIMARY KEY, productName VARCHAR(255), price DECIMAL(8, 2))";
	        int updatedRows = statement.executeUpdate(createProductTableQuery);

	        Assert.assertEquals(updatedRows, 0, "ProductTable creation failed");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        Assert.fail("Failed to create ProductTable due to database exception");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Failed to create ProductTable due to Exception");
	    }
	}
	
	@Test(dependsOnMethods = "createProductTableTest")
	public void createOrderTableTest() {
	    try (Statement statement = connection.createStatement()) {
	        String createOrderTableQuery = "CREATE TABLE OrderTable (" +
	                "orderId INT AUTO_INCREMENT PRIMARY KEY, " +
	                "customerId INT, " +
	                "orderDate DATE, " +
	                "FOREIGN KEY (customerId) REFERENCES CustomerTable(customerId))";
	                
	        int updatedRows = statement.executeUpdate(createOrderTableQuery);

	        Assert.assertEquals(updatedRows, 0, "OrderTable creation failed");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        Assert.fail("Failed to create OrderTable due to database exception");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Failed to create OrderTable due to unhandled exception");
	    }
	}
	
	@Test(dependsOnMethods = "createOrderTableTest")
	public void createSalesTableTest() {
	    try (Statement statement = connection.createStatement()) {
	        String createSalesTableQuery = "CREATE TABLE SalesTable (saleId INT AUTO_INCREMENT PRIMARY KEY, orderId INT, customerId INT, region VARCHAR(255), FOREIGN KEY (orderId) REFERENCES OrderTable(orderId), FOREIGN KEY (customerId) REFERENCES CustomerTable(customerId))";
	        int updatedRows = statement.executeUpdate(createSalesTableQuery);

	        Assert.assertEquals(updatedRows, 0, "SalesTable creation failed");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        Assert.fail("Failed to create SalesTable due to database exception");
	    } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
            Assert.fail("Failed to create OrderTable due to Exception");
		}
	}
	
	@Test(dependsOnMethods = "createSalesTableTest")
	public void createOrderDetailsTableTest() {
	    try (Statement statement = connection.createStatement()) {
	        String createOrderDetailsTableQuery = "CREATE TABLE OrderDetails (" +
	                "orderDetailId INT AUTO_INCREMENT PRIMARY KEY, " +
	                "orderId INT, " +
	                "productId INT, " +
	                "quantity INT, " +
	                "FOREIGN KEY (orderId) REFERENCES OrderTable(orderId), " +
	                "FOREIGN KEY (productId) REFERENCES Product(productId))";

	        int updatedRows = statement.executeUpdate(createOrderDetailsTableQuery);

	        Assert.assertEquals(updatedRows, 0, "OrderDetails table creation failed");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        Assert.fail("Failed to create OrderDetails table due to database exception");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Failed to create OrderDetails table due to an exception");
	    }
	}
	
	@Test(dependsOnMethods = "createSalesTableTest")
	public void insertDataIntoCustomerTableTest() {
	    try {
	        String filePath = "src//test//resources//customer_data.csv";
	        String tableName = "CustomerTable";
	        int expectedCount = insertFromCSV(filePath, tableName);

	        int actualCount = getRowCount(tableName);
	        System.out.println(expectedCount+" : "+actualCount);
	        Assert.assertEquals(actualCount, expectedCount, "The INSERT operation is unsuccessful.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Failed to insert into CustomerTable due to an exception");
	    }
	}
	
	@Test(dependsOnMethods = "insertDataIntoCustomerTableTest")
	public void insertDataIntoProductTableTest() {
	    try {
	        String filePath = "src//test//resources//product_data.csv";
	        String tableName = "Product";
	        int expectedCount = insertFromCSV(filePath, tableName);

	        int actualCount = getRowCount(tableName);
	        System.out.println(expectedCount+" : "+actualCount);
	        Assert.assertEquals(actualCount, expectedCount, "The INSERT operation into ProductTable is unsuccessful.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Failed to insert into ProductTable due to an exception");
	    }
	}
	
	@Test(dependsOnMethods = "insertDataIntoProductTableTest")
	public void insertDataIntoOrderTableTest() {
	    try {
	        String filePath = "src//test//resources//order_data.csv";
	        String tableName = "OrderTable";
	        int expectedCount = insertFromCSV(filePath, tableName);

	        int actualCount = getRowCount(tableName);
	        System.out.println(expectedCount+" : "+actualCount);
	        Assert.assertEquals(actualCount, expectedCount, "The INSERT operation into OrderTable is unsuccessful.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Failed to insert into OrderTable due to an exception");
	    }
	}
	
	@Test(dependsOnMethods = "insertDataIntoOrderTableTest")
	public void insertDataIntoOrderDetailsTableTest() {
	    try {
	        String filePath = "src//test//resources//order_details_data.csv";
	        String tableName = "OrderDetails";
	        int expectedCount = insertFromCSV(filePath, tableName);

	        int actualCount = getRowCount(tableName);
	        System.out.println(expectedCount+" : "+actualCount);
	        Assert.assertEquals(actualCount, expectedCount, "The INSERT operation into OrderDetails table is unsuccessful.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Failed to insert into OrderDetails table due to an exception");
	    }
	}
	
	@Test(dependsOnMethods = "insertDataIntoOrderDetailsTableTest")
	public void insertDataIntoSalesTableTest() {
		try{
			String filePath = "src//test//resources//sales_data.csv"; // Path to your CSV file
		    String tableName = "SalesTable";
		    int expectedCount = insertFromCSV(filePath, tableName);
		    
		 // Fetch the actual count from the database
		    int actualCount = getRowCount(tableName);
		    System.out.println(expectedCount+" : "+actualCount);
		    Assert.assertEquals(actualCount, expectedCount, "The INSERT operation is unsuccessful.");
		} catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Failed to insert into OrderDetails table due to an exception");
	    }
	}
	
	@Test(dependsOnMethods = "insertDataIntoSalesTableTest")
	public void findDuplicatesTest() {
	    try {
	        int expectedCount = 2;
	        int actualCount=-1;
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) - COUNT(DISTINCT productName) AS duplicate_count FROM product");

	        if (resultSet.next()) {
	        	actualCount = resultSet.getInt("duplicate_count");
	        }

	        resultSet.close();
	        statement.close();

	        
	        System.out.println(expectedCount+" : "+actualCount);
	        Assert.assertEquals(actualCount, expectedCount, "Duplicates fetched was incorect.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Failed to duplicates from table due to an exception");
	    }
	}
	
	@Test(dependsOnMethods = "insertDataIntoSalesTableTest")
	public void findMaxPriceProductTest() {
	    try {
	        String expectedProductName = "Canon EOS R5 Mirrorless Camera";
	        String actualProductName = "";
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery("SELECT productName FROM product WHERE price = (SELECT MAX(price) FROM product)");

	        if (resultSet.next()) {
	            actualProductName = resultSet.getString("productName");
	        }

	        resultSet.close();
	        statement.close();

	        
	        System.out.println(actualProductName+" : "+expectedProductName);
	        Assert.assertEquals(actualProductName, expectedProductName, "Max priced product fetched was incorect.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Failed to Max priced product from table due to an exception");
	    }
	}
	
	@Test(dependsOnMethods = "insertDataIntoSalesTableTest")
	public void findMostSoldProductTest() {
	    try {
	        String expectedProductName = "Microsoft Surface Pro 8";
	        String actualProductName = "";
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery("SELECT p.productName " +
	        	    "FROM product p " +
	        	    "JOIN ( " +
	        	    "    SELECT productId, SUM(quantity) AS totalQuantity " +
	        	    "    FROM orderdetails " +
	        	    "    GROUP BY productId " +
	        	    "    ORDER BY totalQuantity DESC " +
	        	    "    LIMIT 1 " +
	        	    ") AS mostSold ON p.productId = mostSold.productId");

	        	if (resultSet.next()) {
	        	    actualProductName = resultSet.getString("productName");
	        	}

	        resultSet.close();
	        statement.close();

	        
	        System.out.println(actualProductName+" : "+expectedProductName);
	        Assert.assertEquals(actualProductName, expectedProductName, "Most sold product fetched was incorect.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Failed to Max priced product from table due to an exception");
	    }
	}
	
	@Test(dependsOnMethods = "insertDataIntoSalesTableTest")
	public void findMostSoldRegionWiseTest() {
		try {
	        String getMostProductIn = "India"; // Dynamically set the region here
	        String expectedProductName = "Microsoft Surface Pro 8";
	        String actualProductName = "";
	        
	        // SQL query with a parameterized region
	        String sqlQuery = "SELECT p.productName " +
	                "FROM salestable s " +
	                "JOIN orderdetails od ON s.orderId = od.orderId " +
	                "JOIN product p ON od.productId = p.productId " +
	                "WHERE s.region = ? " + // Parameterized region
	                "GROUP BY p.productName " +
	                "ORDER BY SUM(od.quantity) DESC " +
	                "LIMIT 1";

	        // PreparedStatement to set the region parameter
	        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
	        preparedStatement.setString(1, getMostProductIn); // Set the region parameter

	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            actualProductName = resultSet.getString("productName");
	        }

	        resultSet.close();
	        preparedStatement.close();

	        System.out.println(actualProductName + " : " + expectedProductName);
	        Assert.assertEquals(actualProductName, expectedProductName, "Most sold product in specified region fetched was incorrect.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        Assert.fail("Failed to fetch most sold product in specified region from table due to an exception");
	    }
	}


}
