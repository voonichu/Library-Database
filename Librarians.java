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

    public static void addLibrarian() {

    }

    public static void getLibrarian() {

    }

    public static void delLibrarian() {

    }

    public static void updateLibrarian() {

    }

}
