package com.CSCI300;

import java.sql.*;
import java.util.Scanner;


public class Availability {

    public static void setupClosingDBConnection() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try { Database.connection.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }, "Shutdown-thread"));
    }

    public static void availability(boolean admin) {
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
                        addAvailability();
                        break;
                    case 2:
                        getAvailability();
                        break;
                    case 3:
                        delAvailability();
                        break;
                    case 4:
                        updateAvailability();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again");
                }

            } while (choice != 0);
        } else {
            getAvailability();
        }
    }


    public static void addAvailability() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter availability ID: ");
        int availabilityID = input.nextInt();
        System.out.println("Enter borrower ID: ");
        int borrowerid = input.nextInt();
        System.out.println("Enter availability of book (yes/no): ");
        input.nextLine();
        String availability = input.nextLine();
        System.out.println("Enter book ID: ");
        int bookid = input.nextInt();

        try {
            Connection connection = Database.connection;
            String query = "INSERT INTO availability VALUES (?, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(query);

            stm.setInt(1, availabilityID);
            stm.setInt(2, borrowerid);
            stm.setString(3, availability);
            stm.setInt(4, bookid);
            stm.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }

    }


    public static void getAvailability() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter book ID: ");
        int bookID = input.nextInt();

        try {
            Connection connection = Database.connection;
            String query = "SELECT availability_status FROM availability WHERE book_id = '" + bookID + "'";
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet result = stm.executeQuery(query);

            while (result.next()) {
                String availability = result.getString("availability_status");
                System.out.println("Available: " + availability);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void delAvailability() {

    }

    public static void updateAvailability() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter ID of book you wish to update: ");
        String bookID = input.nextLine();
        System.out.println("Enter availability status: ");
        String availabilityStatus = input.nextLine();

        try {
            Connection connection = Database.connection;
            String query = "UPDATE availability SET availability_status = ? WHERE book_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, availabilityStatus);
            stm.setString (2, bookID);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
