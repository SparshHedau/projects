package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Studentmanagement {
    public static void main(String[] args) {


        String url = "jdbc:mysql://localhost:3306/Oriental";
        String username = "root";
        String password = "Sparsh@2002";


        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            Scanner sc = new Scanner(System.in);
            System.out.println("Press 1 to fetch the student data \n Press 2 to  update the student data \n Press 3 to insert batches of the Student data \n Press 4 to delete the Student data ");

            int choice = sc.nextInt();
            //int max_id=0;
            switch (choice) {
                case 1:
                    ResultSet fetchdata = statement.executeQuery("Select * from Students ");
                    operations.details(fetchdata);
                    break;
                case 2:
                    ResultSet MaxIdResultset = statement.executeQuery("select MAX(st_id)  as max_st_id from Students ");
                    int max_id = 0;
                    while (MaxIdResultset.next()) {
                        max_id = MaxIdResultset.getInt("max_st_id");
                        System.out.println("MAX id of Student is : " + max_id);
                    }
                    max_id++;
                    System.out.println("Enter the name ");
                    String name = sc.next();

                    System.out.println("Enter the email ");
                    String email = sc.next();

                    System.out.println("Enter the phone number ");
                    String phone = sc.next();
                    int rowCount = statement.executeUpdate("insert into Students values (" + max_id + ",'" + name + "','" + email + "','" + phone + "')");
                    if (rowCount > 0) {
                        System.out.println("Data Inserted .....");
                    } else {
                        System.out.println("Error give the right Information ");
                    }
                    break;

                case 3:

//int count = sc.nextInt();
                    System.out.print("Enter the number of students ");
                    int count = sc.nextInt();
                    ResultSet Max2IdResultset = statement.executeQuery("select MAX(st_id)  as max_st_id from Students ");
                    //int max_id2 = 0;
                    while (Max2IdResultset.next()) {
                        max_id = Max2IdResultset.getInt("max_st_id");
                        System.out.println("MAX id of Student is : " + max_id);
                    }



                    for (int i = 1; i <= count; i++) {
                        System.out.print("Enter the ID : ");
                        String I_id = sc.next();

                        System.out.print("Enter the name of the student : ");
                        String iname = sc.next();

                        System.out.print("Enter the email : ");
                        String i_email = sc.next();

                        System.out.print("Enter the phone number : ");
                        String i_phone = sc.next();


                        statement.addBatch("insert into Students values (" + I_id + ",'" + iname + "','" + i_email + "','" + i_phone + "')");
                    }
                    System.out.println("--------------------------");
                    int rowaffected[] = statement.executeBatch();
                    if (rowaffected.length == count) {
                        System.out.println("Data inserted");
                    } else {
                        System.out.println("No Excecution");
                    }
                    break;


                case 4:
                    System.out.println("Enter the id for delete record:");
                    int id = sc.nextInt();
                    int row = statement.executeUpdate("DELETE from Students where st_id = " + id);
                    if (row > 0) {
                        System.out.println("Data Deleted:" + id);
                    } else {
                        System.out.println("Data Deletion failed:");
                    }
                    break;
                default:
                    System.out.println("Enter valid input");
                    break;

            }
        }
        catch(Exception e ){
                e.printStackTrace();
            }

        }

}