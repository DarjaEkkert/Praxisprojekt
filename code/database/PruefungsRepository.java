package database;

import java.sql.PreparedStatement;
import model.Pruefung;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class PruefungsRepository {

    private DatabaseManager databaseManager;

    public PruefungsRepository(DatabaseManager databaseManager) {

        this.databaseManager = databaseManager;
    }

public void savePruefung(Pruefung pruefung) {

    try {

        String sql =
                "INSERT INTO pruefungen "
                + "(name, datum, gruppe, dauer, aufgabenPfad, loesungsPfad, teilnehmerPfad,status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement =
                databaseManager
                        .getConnection()
                        .prepareStatement(sql);

        statement.setString(1, pruefung.getName());
        statement.setString(2, pruefung.getDatum());
        statement.setString(3, pruefung.getGruppe());
        statement.setInt(4, pruefung.getDauer());

        statement.setString(5, pruefung.getAufgabenPfad());
        statement.setString(6, pruefung.getLoesungsPfad());
        statement.setString(7, pruefung.getTeilnehmerPfad());
        statement.setString(8, pruefung.getStatus());

        statement.executeUpdate();

        System.out.println(
                "Prüfung gespeichert.");

    } catch (Exception e) {

        e.printStackTrace();
    }
}

public void updateStatus(
            int pruefungId,
            String status) {

        try {

            String sql =
                    "UPDATE pruefungen "
                    + "SET status = ? "
                    + "WHERE id = ?";

            PreparedStatement statement =
                    databaseManager
                            .getConnection()
                            .prepareStatement(sql);

            statement.setString(1, status);
            statement.setInt(2, pruefungId);
System.out.println(
        "Status ändern für Prüfung "
        + pruefungId
        + " auf "
        + status);
            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

public List<Pruefung> getAllPruefungen() {

    List<Pruefung> pruefungen =
            new ArrayList<>();

    try {

        String sql =
                "SELECT * FROM pruefungen";

        PreparedStatement statement =
                databaseManager
                        .getConnection()
                        .prepareStatement(sql);

        ResultSet result =
                statement.executeQuery();

while (result.next()) {

    Pruefung pruefung =
            new Pruefung(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("datum"),
                    result.getString("gruppe"),
                    result.getInt("dauer"),
                    result.getString("aufgabenPfad"),
                    result.getString("loesungsPfad"),
                    result.getString("teilnehmerPfad"),
                    result.getString("status"));

    pruefungen.add(pruefung);
}

result.close();
statement.close();

    } catch (Exception e) {

        e.printStackTrace();
    }

    return pruefungen;
}
public int getLetztePruefungId() {

    try {

        String sql =
                "SELECT MAX(id) AS id "
                + "FROM pruefungen";

        PreparedStatement statement =
                databaseManager
                        .getConnection()
                        .prepareStatement(sql);

        ResultSet result =
                statement.executeQuery();

        int id = -1;

        if (result.next()) {

            id = result.getInt("id");
        }

        result.close();
        statement.close();

        return id;

    } catch (Exception e) {

        e.printStackTrace();
    }

    return -1;
}

public Pruefung getPruefungById(int id) {

    try {

        String sql =
                "SELECT * FROM pruefungen "
                + "WHERE id = ?";

        PreparedStatement statement =
                databaseManager
                        .getConnection()
                        .prepareStatement(sql);

        statement.setInt(1, id);

        ResultSet result =
                statement.executeQuery();

        Pruefung pruefung = null;

        if (result.next()) {

            pruefung = new Pruefung(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("datum"),
                    result.getString("gruppe"),
                    result.getInt("dauer"),
                    result.getString("aufgabenPfad"),
                    result.getString("loesungsPfad"),
                    result.getString("teilnehmerPfad"),
                    result.getString("status"));
        }

        result.close();
        statement.close();

        return pruefung;

    } catch (Exception e) {

        e.printStackTrace();
    }

    return null;
}

}