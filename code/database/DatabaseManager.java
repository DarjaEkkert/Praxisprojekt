package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManager {

    private Connection connection;

    public void connect() {

        try {

            Class.forName("org.sqlite.JDBC");
            
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:pruefungssystem.db");

            System.out.println(
                    "Verbindung zur Datenbank hergestellt.");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void disconnect() {

        try {

            if (connection != null) {

                connection.close();

                System.out.println(
                        "Verbindung geschlossen.");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}