package com.CSCI300;

import java.sql.*;
import java.util.Scanner;

public class Borrowers {

    public static void setupClosingDBConnection() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try { Database.connection.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }, "Shutdown-thread"));
    }

    public static void borrowers(boolean admin) {
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
                        addBorrower();
                        break;
                    case 2:
                        getBorrower();
                        break;
                    case 3:
                        delBorrower();
                        break;
                    case 4:
                        updateBorrower();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again");
                }

            } while (choice != 0);
        } else {
            getBorrower();
        }
    }

    public static void addBorrower() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter borrower id: ");
        int borrowerID = input.nextInt();
        System.out.println("Enter name of borrower: ");
        input.nextLine();
        String borrowerName = input.nextLine();
        System.out.println("Enter major of borrower: ");
        String major = input.nextLine();
        System.out.println("Enter date of return: ");
        String returnDate = input.nextLine();

        try {
            Connection connection = Database.connection;
            String query = "INSERT INTO borrowers VALUES (?, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(query);

            stm.setInt(1, borrowerID);
            stm.setString(2, borrowerName);
            stm.setString(3, major);
            stm.setString(4, returnDate);
            stm.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getBorrower() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter borrower id: ");
        int borrowerId = input.nextInt();

        try {
            Connection connection = Database.connection;
            String query = "SELECT * FROM borrowers WHERE borrower_id = '" + borrowerId + "'";
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet result = stm.executeQuery(query);
            while (result.next()) {
                String borrowerName = result.getString("borrower_name");
                String major = result.getString("major");
                String returnDate = result.getString("return_date");

                System.out.println("Borrower name: " + borrowerName);
                System.out.println("Major: " + major);
                System.out.println("Return Date: " + returnDate);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void delBorrower() {

    }

    public static void updateBorrower() {

    }

}
