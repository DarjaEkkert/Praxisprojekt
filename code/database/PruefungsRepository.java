package database;

import java.sql.PreparedStatement;
import model.Pruefung;

public class PruefungsRepository {

    private DatabaseManager databaseManager;

    public PruefungsRepository(DatabaseManager databaseManager) {

        this.databaseManager = databaseManager;
    }

    public void savePruefung(Pruefung pruefung) {

    try {

        String sql =
                "INSERT INTO pruefungen "
                + "(name, datum, gruppe, dauer, aufgabenPfad, loesungsPfad, teilnehmerPfad) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

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

        statement.executeUpdate();

        System.out.println(
                "Prüfung gespeichert.");

    } catch (Exception e) {

        e.printStackTrace();
    }
}
}