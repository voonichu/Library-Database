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

    }

    public static void getPublisher() {

    }

    public static void delPublisher() {

    }

    public static void updatePublisher() {

    }

}

