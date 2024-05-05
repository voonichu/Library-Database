package com.CSCI300;

import java.sql.*;
import java.util.Scanner;


public class Publishers {

    public static void setupClosingDBConnection() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try { Database.connection.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }, "Shutdown-thread"));
    }

    public static void publishers(boolean admin) {
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
                        addPublisher();
                        break;
                    case 2:
                        getPublisher();
                        break;
                    case 3:
                        delPublisher();
                        break;
                    case 4:
                        updatePublisher();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again");
                }

            } while (choice != 0);
        } else {
            getPublisher();
        }
    }

    public static void addPublisher() {
        Scanner getInput = new Scanner(System.in);
        System.out.println("Enter Publisher ID: ");
        int publisherID = getInput.nextInt();
        System.out.println("Enter Publisher Name: ");
        String publisherName = getInput.next();
        System.out.println("What subject/genre does the publishing company work for?: ");
        getInput.next();
        String publisherFocus = getInput.next();
        System.out.println("When was the book published?: ");
        String publisherDate = getInput.next();
        System.out.println("Enter ISBN number: ");
        String isbn = getInput.next();
        try {
            Connection connection = Database.connection;
            String query = "INSERT INTO Publishers VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, publisherID);
            stm.setString(2, publisherName);
            stm.setString(3, publisherFocus);
            stm.setString(4, publisherDate);
            stm.setString(5,isbn);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getPublisher(){
        try {
            Connection connection = Database.connection; // Connect to database
            String query = "SELECT * FROM publishers"; // Enter the query
            Statement stm = connection.createStatement(); // Create statement
            ResultSet result = stm.executeQuery(query); // Execute the query

            while (result.next()) {
                System.out.println("Publisher ID: " + result.getInt("publisher_id"));
                System.out.println("Publisher Name: " + result.getString("publisher_name"));
                System.out.println("Publisher Focus: " + result.getString("publisher_focus"));
                System.out.println("Publish Date: " + result.getString("publish_date"));
                System.out.println("ISBN: " + result.getString("isbn"));
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void delPublisher() {

    }

    public static void updatePublisher() {

    }

}

