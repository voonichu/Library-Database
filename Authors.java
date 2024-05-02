package com.CSCI300;

import java.sql.*;
import java.util.Scanner;

public class Authors {

    public static void setupClosingDBConnection() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try { Database.connection.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }, "Shutdown-thread"));
    }

    public static void authors(boolean admin) {
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
                        addAuthors();
                        break;
                    case 2:
                        getAuthors();
                        break;
                    case 3:
                        delAuthors();
                        break;
                    case 4:
                        updateAuthors();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again");
                }

            } while (choice != 0);
        } else {
            getAuthors();
        }
    }

    public static void addAuthors() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("What is the Author ID?");
            int author_id = input.nextInt();
            System.out.println("What is the Publisher ID?");
            int publisher_id = input.nextInt();
            System.out.println("What is the Author's name?");
            input.nextLine();
            String author_name = input.nextLine();
            System.out.println("What is the Genre?");
            String genre = input.nextLine();

            Connection connection = Database.connection;
            String query = "INSERT INTO Authors VALUES (?, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(query);

            stm.setInt(1, author_id);
            stm.setInt(2, publisher_id);
            stm.setString(3, author_name);
            stm.setString(4, genre);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getAuthors() {
        try {
            Connection connection = Database.connection;
            String query = "SELECT * FROM Authors";
            Statement stm = connection.createStatement();
            ResultSet result = stm.executeQuery(query);

            while (result.next()) {
                System.out.println("Author ID: " + result.getInt("author_id"));
                System.out.println("Publisher ID: " + result.getInt("publisher_id"));
                System.out.println("Author Name: " + result.getString("author_name"));
                System.out.println("Genre: " + result.getString("genre"));
                System.out.println("\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void delAuthors() {
        try {
            Connection connection = Database.connection;
            Scanner input = new Scanner(System.in);
            System.out.println("What is the ID of the author you want to delete?");
            int id = input.nextInt();
            String query = "DELETE FROM Authors WHERE author_id = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateAuthors() {
        try {
            Connection connection = Database.connection;
            Scanner input = new Scanner(System.in);
            System.out.println("What is the author_id you want to edit?");
            int author_id = input.nextInt();

            int choice;
            do {
                System.out.println("1. Edit publisher_id");
                System.out.println("2. Edit Author name");
                System.out.println("3. Edit genre");
                System.out.println("0. Exit");
                System.out.println("Enter choice: ");
                choice = input.nextInt();
                String query;
                switch (choice) {
                    case 1:
                        try {
                            int publisher_id;
                            System.out.println("What would you like to update the publisher_id to?");
                            input.nextLine();
                            publisher_id = input.nextInt();
                            query = "UPDATE Authors SET publisher_id = ? WHERE author_id = ?";
                            PreparedStatement stm = connection.prepareStatement(query);
                            stm.setInt(1, publisher_id);
                            stm.setInt(2, author_id);
                            stm.executeUpdate();
                            System.out.println("Update successful");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        break;
                    case 2:
                        try {
                            String author_name;
                            System.out.println("What would you like to update the Author's name to?");
                            author_name = input.nextLine();
                            query = "UPDATE Authors SET author_name = ? WHERE author_id = ?";
                            PreparedStatement stm = connection.prepareStatement(query);
                            stm.setString(1, author_name);
                            stm.setInt(2, author_id);
                            stm.executeUpdate();
                            System.out.println("Update successful");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        break;
                    case 3:
                        try {
                            String genre;
                            System.out.println("What would you like to update the genre to?");
                            input.nextLine();
                            genre = input.nextLine();
                            query = "UPDATE Books SET genre = ? WHERE author_id = ?";
                            PreparedStatement stm = connection.prepareStatement(query);
                            stm.setString(1, genre);
                            stm.setInt(2, author_id);
                            stm.executeUpdate();
                            System.out.println("Update Successful");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        break;
                    case 0:
                        System.out.println("Finished updating.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose again");
                }
            } while (choice != 0);


        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
