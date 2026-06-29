package database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class PruefungsteilnehmerRepository {

    private DatabaseManager databaseManager;

    public PruefungsteilnehmerRepository(
            DatabaseManager databaseManager) {

        this.databaseManager = databaseManager;
    }

    public void saveTeilnehmer(
            int pruefungId,
            String username) {

        try {

            String sql =
                    "INSERT INTO pruefungsteilnehmer "
                    + "(pruefung_id, username) "
                    + "VALUES (?, ?)";

            PreparedStatement statement =
                    databaseManager
                            .getConnection()
                            .prepareStatement(sql);

            statement.setInt(1, pruefungId);
            statement.setString(2, username);

            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
public int getPruefungIdByUsername(
        String username) {

    try {

        String sql =
                "SELECT pruefung_id "
                + "FROM pruefungsteilnehmer "
                + "WHERE username = ?";

        PreparedStatement statement =
                databaseManager
                        .getConnection()
                        .prepareStatement(sql);

        statement.setString(
                1,
                username);

        ResultSet result =
                statement.executeQuery();

        if (result.next()) {

            return result.getInt(
                    "pruefung_id");
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return -1;
} 
public ArrayList<String> getTeilnehmerByPruefungId(int pruefungId) {

    ArrayList<String> teilnehmer = new ArrayList<>();

    try {

        String sql =
                "SELECT username FROM pruefungsteilnehmer "
                + "WHERE pruefung_id = ?";

        PreparedStatement statement =
                databaseManager
                        .getConnection()
                        .prepareStatement(sql);

        statement.setInt(1, pruefungId);

        ResultSet result =
                statement.executeQuery();

        while (result.next()) {

            teilnehmer.add(
                    result.getString("username"));
        }

    } catch (Exception e) {

        e.printStackTrace();
    }

    return teilnehmer;
}
}
