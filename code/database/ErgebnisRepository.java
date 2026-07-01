package database;

import java.sql.PreparedStatement;
import java.util.List;
import model.AufgabeErgebnis;
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
}