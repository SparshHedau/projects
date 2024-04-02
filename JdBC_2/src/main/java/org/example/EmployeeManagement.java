package org.example;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class EmployeeManagement {

    private static final String JDBC_URl = "jdbc:mysql://localhost:3306/spark";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "Sparsh@2002";

    public static Connection connection;
    public static PreparedStatement preparedStatement;
    public static ResultSet resultSet;

    public static void main(String[] args) {
        try {


            connection = DriverManager.getConnection(JDBC_URl, USER_NAME, PASSWORD);
            createEmployeeTAble();

            insertDataintoDB("Sparsh",20,"Sparshhedau@gmail.com");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void insertDataintoDB(String name, int age, String email) throws SQLException {
        String insertSQl="INSERT INTO Employee(name,age,email) VALUES (?,?,?)";
        preparedStatement=connection.prepareStatement(insertSQl);
        preparedStatement.setString(1,name);
        preparedStatement.setInt(2,age);
        preparedStatement.setString(3,email);
        preparedStatement.executeUpdate();
        System.out.println("Data Inserted...");

    }

    private static void createEmployeeTAble() throws SQLException {

        String createTable="CREATE TABLE IF NOT EXISTS Employee ( id INT AUTO_INCREMENT PRIMARY KEY," +
                " name VARCHAR(100) ,"
                +"age INT ,"
                +"email VARCHAR(100)"
                +")";
       preparedStatement= connection.prepareStatement(createTable);
       preparedStatement.execute();
        System.out.println("Table Created  ");

    }

}