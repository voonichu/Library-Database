package com.CSCI300;

import java.sql.*;
import java.util.Scanner;


public class Librarians {

    public static void setupClosingDBConnection() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try { Database.connection.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }, "Shutdown-thread"));
    }

    public static void librarians(boolean admin) {
        Database.connect();
        setupClosingDBConnection();
        Scanner input = new Scanner(System.in);
        int choice;
        if (admin) {
            do {
                System.out.println("1. Add");
                System.out.println("2. View");
                System.out.println("3. Delete");
                System.out.println("4. Update");
                System.out.println("0. Exit");
                System.out.println("Enter what you want to do to the table:");
                choice = input.nextInt();

                switch (choice) {
                    case 1:
                        addLibrarian();
                        break;
                    case 2:
                        getLibrarian();
                        break;
                    case 3:
                        delLibrarian();
                        break;
                    case 4:
                        updateLibrarian();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again");
                }

            } while (choice != 0);
        } else {
            getLibrarian();
        }
    }

    public static void addLibrarian(){
        Scanner getInput = new Scanner(System.in);
        System.out.println("Enter Librarian ID: ");
        int librarianID = getInput.nextInt();
        System.out.println("Enter Librarian Name: ");
        getInput.nextLine(); // We need to have this before the first 'nextLine()' so it doesn't skip any of the later '.nextLine()'
        String librarianName = getInput.next();
        System.out.println("What time does the librarian start working?: ");
        getInput.nextLine();
        String shift = getInput.next();
        System.out.println("What days of the week does the librarian work?: ");
        getInput.nextLine();
        String workDays = getInput.next();
        System.out.println("Is the librarian a student, an admin, or a full-time employee?: ");
        getInput.next();
        String librarianStatus = getInput.next();

        try {
            Connection connection = Database.connection;
            String query = "INSERT INTO librarians VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, librarianID);
            stm.setString(2, librarianName);
            stm.setString(3, shift);
            stm.setString(4, workDays);
            stm.setString(5, librarianStatus);
            stm.executeUpdate();
            System.out.println("The new librarian is added to the database!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getLibrarian() {
        try {
            Connection connection = Database.connection; // Connect to database
            String query = "SELECT * FROM librarians"; // Enter the query
            Statement stm = connection.createStatement(); // Create statement
            ResultSet result = stm.executeQuery(query); // Execute the query

            while (result.next()) {
                System.out.println("Librarian ID: " + result.getInt("librarian_id"));
                System.out.println("Librarian Name: " + result.getString("librarian_name"));
                System.out.println("Shift: " + result.getString("shift"));
                System.out.println("Work Days: " + result.getString("work_days"));
                System.out.println("Librarian Status: " + result.getString("librarian_status"));
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void delLibrarian() {

    }

    public static void updateLibrarian() {

    }

}
