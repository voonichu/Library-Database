package com.CSCI300;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Database.connect();
        setupClosingDBConnection();
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Welcome to the Library Database");
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            System.out.println("0. Exit");
            System.out.println("Enter selection: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    signup();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }

        } while (choice != 0);

        input.close();
    }



    public static void loggedIn(boolean admin) {
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. Books");
            System.out.println("2. Authors");
            System.out.println("3. Availability");
            System.out.println("4. Borrowers");
            System.out.println("5. Librarians");
            System.out.println("6. Publishers");
            System.out.println("0. Sign Out");
            System.out.println("Enter the table you want to view/edit:");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    Books.books(admin);
                    break;
                case 2:
                    Authors.authors(admin);
                    break;
                case 3:
                    Availability.availability(admin);
                    break;
                case 4:
                    Borrowers.borrowers(admin);
                    break;
                case 5:
                    Librarians.librarians(admin);
                    break;
                case 6:
                    Publishers.publishers(admin);
                case 0:
                    System.out.println("Signing Out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again");
            }
        } while (choice != 0);
    }


    public static void setupClosingDBConnection() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try { Database.connection.close(); System.out.println("Application Closed - DB Connection Closed");
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }, "Shutdown-thread"));
    }

    public static void login() { // Login function
        try {
            String query = "SELECT Count(*) FROM Users"; // First query to find out how many users are in table
            Connection connection = Database.connection;
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet result = stm.executeQuery();
            result.next();
            int numUsers = result.getInt("COUNT(*)"); // Saves the number of users into numUsers


            String[] usernames = new String[numUsers]; // Array made the size of numUsers to store all usernames
            Scanner input = new Scanner(System.in);
            query = "SELECT username FROM Users"; // Second query to get all usernames in table
            stm = connection.prepareStatement(query);
            result = stm.executeQuery();
            int i = 0;
            while (result.next()) {
                usernames[i] = result.getString("username"); // Saves every username into usernames array
                i++;
            }
            boolean usernameMatch = false; // Boolean to check match logic
            boolean passwordMatch = false; // Boolean to check match logic
            boolean adminStatus = false; // Boolean to check admin status
            String decryptedPassword = "";
            System.out.println("Enter username:");
            String loginusername = input.nextLine(); // Get username from user
            System.out.println("Enter password:");
            String loginpassword = input.nextLine(); // Get password from user

            for (i = 0; i < numUsers; i++) {
                if (loginusername.equals(usernames[i])) {
                    usernameMatch = true; // sets usernameMatch boolean to true if the username the user entered is in the usernames array
                }
            }
            if (usernameMatch) { // Runs query only if a username was found that matches one in the database
                query = "SELECT password, admin FROM Users WHERE username = ?"; // Gets the password and admin status from specific username entered
                stm = connection.prepareStatement(query);
                stm.setString(1, loginusername);
                result = stm.executeQuery();
                while (result.next()) {
                        decryptedPassword = result.getString("password");
                        adminStatus = result.getBoolean("admin");
                    }
                }
            decryptedPassword = EncryptDecrypt.decrypt(decryptedPassword);
            if (decryptedPassword.equals(loginpassword)) {
                passwordMatch = true;
            }

            if (usernameMatch && passwordMatch) {
                System.out.println("Login Successful");
                System.out.println(adminStatus);
            } else {
                System.out.println("Incorrect username or password");
            }

           loggedIn(adminStatus);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void signup() {
        try {
            Scanner input = new Scanner(System.in);
            Connection connection = Database.connection;
            boolean invalidUser = false;

            System.out.println("Enter your desired username");
            String username = input.nextLine();
            System.out.println("Enter your desired password");
            String password = input.nextLine();

            String query = "SELECT Count(*) FROM Users"; // First query to find out how many users are in table
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet result = stm.executeQuery();
            result.next();
            int numUsers = result.getInt("COUNT(*)"); // Saves the number of users into numUsers


            String[] usernames = new String[numUsers]; // Array made the size of numUsers to store all usernames
            query = "SELECT username FROM Users"; // Second query to get all usernames in table
            stm = connection.prepareStatement(query);
            result = stm.executeQuery();
            int i = 0;
            while (result.next()) {
                usernames[i] = result.getString("username"); // Saves every username into usernames array
                i++;
            }

            for (i = 0; i < numUsers; i++) {
                if (username.equals(usernames[i])) {
                    invalidUser = true; // sets invalidUser boolean to true if the username the user entered is in the usernames array
                }
            }

            if (!invalidUser) {
                query = "INSERT INTO Users VALUES (?, ?, ?, ?)";
                stm = connection.prepareStatement(query);
                stm.setInt(1, 0);
                stm.setString(2, username);
                stm.setString(3, EncryptDecrypt.encrypt(password));
                stm.setBoolean(4, false);
                stm.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }



}
