package database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.util.List;
import model.AufgabeErgebnis;
import model.AufgabenStatus;
import model.Ergebnis;
public class ErgebnisRepository {

    private DatabaseManager db;

    public ErgebnisRepository(DatabaseManager db) {

    this.db = db;
}

public int saveErgebnis(
        int pruefungId,
        String username,
        double gesamtpunkte,
        double prozent,
        boolean bestanden) {

    try {

        String sql =
                "INSERT INTO ergebnisse "
                + "(pruefung_id, username, gesamtpunkte, prozent, bestanden) "
                + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement =
                db.getConnection().prepareStatement(sql);

        statement.setInt(1, pruefungId);
        statement.setString(2, username);
        statement.setDouble(3, gesamtpunkte);
        statement.setDouble(4, prozent);
        statement.setInt(5, bestanden ? 1 : 0);

        statement.executeUpdate();

PreparedStatement idStatement =
        db.getConnection().prepareStatement(
                "SELECT last_insert_rowid()");

var result = idStatement.executeQuery();

if (result.next()) {

    return result.getInt(1);
}

    } catch (Exception e) {

        e.printStackTrace();
    }
    return -1;
}
public void saveAufgabeErgebnisse(
        int ergebnisId,
        List<AufgabeErgebnis> ergebnisse) {

    try {

        String sql =
                "INSERT INTO aufgabe_ergebnisse "
                + "(ergebnis_id, aufgabe_nummer, status, punkte, max_punkte, kommentar) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement =
                db.getConnection().prepareStatement(sql);

        for (AufgabeErgebnis aufgabe : ergebnisse) {

            statement.setInt(1, ergebnisId);
            statement.setInt(2, aufgabe.getNummer());
            statement.setString(3, aufgabe.getStatus().name());
            statement.setDouble(4, aufgabe.getPunkte());
            statement.setDouble(5, aufgabe.getMaxPunkte());
            statement.setString(6, aufgabe.getKommentar());

            statement.executeUpdate();
        }

    } catch (Exception e) {

        e.printStackTrace();
    }
}

public ArrayList<AufgabeErgebnis> getAufgabeErgebnisse(
        int ergebnisId) {

    ArrayList<AufgabeErgebnis> aufgaben =
            new ArrayList<>();

    try {

        String sql =
                "SELECT * FROM aufgabe_ergebnisse "
                + "WHERE ergebnis_id = ? "
                + "ORDER BY aufgabe_nummer";

        PreparedStatement statement =
                db.getConnection().prepareStatement(sql);

        statement.setInt(1, ergebnisId);

        ResultSet result =
                statement.executeQuery();

        while (result.next()) {

            AufgabeErgebnis aufgabe =
                    new AufgabeErgebnis(

                    result.getInt("aufgabe_nummer"),

                    AufgabenStatus.valueOf(
                            result.getString("status")),

                    result.getDouble("punkte"),

                    result.getDouble("max_punkte"),

                    "",

                    result.getString("kommentar"));

            aufgaben.add(aufgabe);
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return aufgaben;
}

public void updateAufgabeErgebnis(
        int ergebnisId,
        AufgabeErgebnis aufgabe) {

    try {

        String sql =
                "UPDATE aufgabe_ergebnisse "
                + "SET status = ?, "
                + "punkte = ?, "
                + "kommentar = ? "
                + "WHERE ergebnis_id = ? "
                + "AND aufgabe_nummer = ?";

        PreparedStatement statement =
                db.getConnection().prepareStatement(sql);

        statement.setString(
                1,
                aufgabe.getStatus().name());

        statement.setDouble(
                2,
                aufgabe.getPunkte());

        statement.setString(
                3,
                aufgabe.getKommentar());

        statement.setInt(
                4,
                ergebnisId);

        statement.setInt(
                5,
                aufgabe.getNummer());

        statement.executeUpdate();

    } catch (Exception e) {

    e.printStackTrace();
}
}

public ArrayList<String> getStudentenByPruefung(
        int pruefungId) {

    ArrayList<String> studenten =
            new ArrayList<>();

    try {

        String sql =
                "SELECT username FROM ergebnisse "
                + "WHERE pruefung_id = ?";

        PreparedStatement statement =
                db.getConnection()
                        .prepareStatement(sql);

        statement.setInt(1, pruefungId);

        ResultSet result =
                statement.executeQuery();

        while (result.next()) {

            studenten.add(
                    result.getString("username"));
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return studenten;
}

public ArrayList<Ergebnis> getErgebnisseByPruefung(
        int pruefungId) {

    ArrayList<Ergebnis> ergebnisse =
            new ArrayList<>();

    try {

        String sql =
                "SELECT username, prozent, bestanden "
                + "FROM ergebnisse "
                + "WHERE pruefung_id = ?";

        PreparedStatement statement =
                db.getConnection().prepareStatement(sql);

        statement.setInt(1, pruefungId);

        ResultSet result =
                statement.executeQuery();

        while (result.next()) {

            ergebnisse.add(
                    new Ergebnis(
                            result.getString("username"),
                            result.getDouble("prozent"),
                            result.getInt("bestanden") == 1));
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return ergebnisse;
}

public int getErgebnisId(
        int pruefungId,
        String username) {

    try {

        String sql =
                "SELECT id FROM ergebnisse "
                + "WHERE pruefung_id = ? "
                + "AND username = ?";

        PreparedStatement statement =
                db.getConnection()
                        .prepareStatement(sql);

        statement.setInt(1, pruefungId);
        statement.setString(2, username);

        ResultSet result =
                statement.executeQuery();

        if (result.next()) {

            return result.getInt("id");
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return -1;
}

public void updateGesamtergebnis(
        int ergebnisId,
        double gesamtpunkte,
        double prozent,
        boolean bestanden) {

    try {

        String sql =
                "UPDATE ergebnisse "
                + "SET gesamtpunkte = ?, "
                + "prozent = ?, "
                + "bestanden = ? "
                + "WHERE id = ?";

        PreparedStatement statement =
                db.getConnection().prepareStatement(sql);

        statement.setDouble(1, gesamtpunkte);
        statement.setDouble(2, prozent);
        statement.setInt(3, bestanden ? 1 : 0);
        statement.setInt(4, ergebnisId);

        statement.executeUpdate();

    } catch (Exception e) {

        e.printStackTrace();
    }
}
}