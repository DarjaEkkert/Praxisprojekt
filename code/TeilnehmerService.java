import java.io.FileInputStream;
import java.util.Random;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import database.DatabaseManager;
import database.UserRepository;
import model.User;
import model.Role;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class TeilnehmerService {

    public static void leseTeilnehmerliste(String dateipfad) {

        try {

            FileInputStream file =
                    new FileInputStream(dateipfad);

            XSSFWorkbook workbook =
                    new XSSFWorkbook(file);

            XSSFSheet sheet =
                    workbook.getSheetAt(0);

            System.out.println("\n--- Teilnehmerliste ---");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                var row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                String vorname =
                        row.getCell(0).getStringCellValue();

                String nachname =
                        row.getCell(1).getStringCellValue();

                String username =
                    vorname.toLowerCase()
                    + "."
                    + nachname.toLowerCase();

                String passwort =generierePasswort();

                DatabaseManager db =  new DatabaseManager();

                db.connect();

                UserRepository repository = new UserRepository(db);

                User user =
                    new User(
                        username,
                        passwort,
                        Role.STUDENT);

                repository.saveUser(user);

                db.disconnect();

                System.out.println(
                    "\n"
                    + vorname + " "
                    + nachname);

                System.out.println(
                    "Benutzername: "
                    + username);

                System.out.println(
                    "Passwort: "
                    + passwort);
            }

            workbook.close();
            file.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public static String generierePasswort() {

    String zeichen =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    Random random = new Random();

    StringBuilder passwort =
            new StringBuilder();

    for (int i = 0; i < 8; i++) {

        passwort.append(
                zeichen.charAt(
                        random.nextInt(
                                zeichen.length())));
    }

    return passwort.toString();
}
}