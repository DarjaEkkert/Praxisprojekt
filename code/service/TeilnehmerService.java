package service;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import database.DatabaseManager;
import database.PruefungsteilnehmerRepository;
import database.UserRepository;
import model.User;
import model.Role;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class TeilnehmerService {

    public static void leseTeilnehmerliste(String dateipfad, int pruefungId) {

            ArrayList<User> teilnehmer = new ArrayList<>();
        try {

            FileInputStream file = new FileInputStream(dateipfad);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet =  workbook.getSheetAt(0);

            DatabaseManager db = new DatabaseManager();
            db.connect();
            UserRepository userRepository = new UserRepository(db);
            PruefungsteilnehmerRepository teilnehmerRepository =  new PruefungsteilnehmerRepository(db);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                var row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }
                if (row.getCell(0) == null
                    || row.getCell(1) == null) {

                    continue;
                }

                String vorname = row.getCell(0).getStringCellValue();
                String nachname = row.getCell(1).getStringCellValue();

                if (vorname.isBlank()
                    || nachname.isBlank()) {

                    continue;
                }
                String username =
                    vorname.toLowerCase()
                    + "."
                    + nachname.toLowerCase();
                String passwort =generierePasswort();
                
                User user =
                    new User(
                        username,
                        passwort,
                        Role.STUDENT,
                        vorname,
                        nachname);

                userRepository.saveUser(user);
                teilnehmer.add(user);

                teilnehmerRepository.saveTeilnehmer(pruefungId, username);
            }

            //PdfService pdfService = new PdfService();

            workbook.close();
            file.close();
            db.disconnect();

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