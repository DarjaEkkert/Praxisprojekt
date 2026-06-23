package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;

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

    public Connection getConnection() {

        return connection;
    }

    public void createUserTable() {

    try {

        Statement statement =
                connection.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT NOT NULL,"
                + "password TEXT NOT NULL,"
                + "role TEXT NOT NULL"
                + ");";

        statement.execute(sql);

        System.out.println(
                "Tabelle users erstellt.");

    } catch (Exception e) {

        e.printStackTrace();
    }
}

public void insertDefaultUsers() {

    try {

        String sql =
                "INSERT INTO users(username, password, role) "
                + "VALUES (?, ?, ?)";

        PreparedStatement statement =
                connection.prepareStatement(sql);

        statement.setString(1, "student");
        statement.setString(2, "1234");
        statement.setString(3, "STUDENT");

        statement.executeUpdate();

        statement.setString(1, "dozent");
        statement.setString(2, "1234");
        statement.setString(3, "DOZENT");

        statement.executeUpdate();

        System.out.println(
                "Standardbenutzer eingefügt.");

    } catch (Exception e) {

        e.printStackTrace();
    }
}

public void createPruefungTable() {

    try {

        Statement statement =
                connection.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS pruefungen ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "datum TEXT NOT NULL,"
                + "gruppe TEXT NOT NULL,"
                + "dauer INTEGER NOT NULL,"
                + "aufgabenPfad TEXT NOT NULL,"
                + "loesungsPfad TEXT NOT NULL,"
                + "teilnehmerPfad TEXT NOT NULL"
                + ");";

        statement.execute(sql);

        System.out.println(
                "Tabelle pruefungen erstellt.");

    } catch (Exception e) {

        e.printStackTrace();
    }
}
public void createPruefungsteilnehmerTable() {

    try {

        Statement statement =
                connection.createStatement();

        String sql =
                "CREATE TABLE IF NOT EXISTS pruefungsteilnehmer ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "pruefung_id INTEGER NOT NULL,"
                + "username TEXT NOT NULL"
                + ");";

        statement.execute(sql);

        System.out.println(
                "Tabelle pruefungsteilnehmer erstellt.");

    } catch (Exception e) {

        e.printStackTrace();
    }
}
}