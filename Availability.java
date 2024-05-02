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

    }

    public static void getAvailability() {

    }

    public static void delAvailability() {

    }

    public static void updateAvailability() {

    }

}