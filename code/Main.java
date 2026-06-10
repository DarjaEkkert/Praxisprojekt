import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.CellType;
import java.io.FileOutputStream;
import java.io.File;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import database.DatabaseManager;
import database.UserRepository;

import model.User;

public class Main {
    
    public static void main(String[] args) {

       DatabaseManager db = new DatabaseManager();

        db.connect();
        

        UserRepository repository = new UserRepository(db);

        User user = repository.getUserByUsername( "student");

        System.out.println( user.getUsername());

        System.out.println( user.getRole());
                
        db.disconnect(); 

        String studentName = "Max Mustermann";
        String gruppenNummer = "18M";
        String pruefungsDatum = "03.02.2026";
        
    }

    public static void pruefeDatei(String dateipfad) {
        try {
            FileInputStream file = new FileInputStream(dateipfad);

            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet1 = workbook.getSheetAt(0);
            XSSFSheet sheet2 = workbook.getSheetAt(1);
            XSSFSheet sheet3 = workbook.getSheetAt(2);

            int aufgabe1 = 0;
            int aufgabe2 = 0;
            int aufgabe3 = 0;
            int aufgabe4 = 0;
            int aufgabe5 = 0;
            int punkte = 0;
//Blatt 1
System.out.println("\n--- Blatt 1 Prüfung ---");


if (pruefeZelle(sheet1, 1, 6, "SUM", 210, "Aufgabe 1")) {
    punkte++;
    aufgabe1 = 1;
}

if (pruefeZelle(sheet1, 3, 6, "MAX", 50, "Aufgabe 2")) {
    punkte++;
    aufgabe2 = 1;
}

if (pruefeZelle(sheet1, 5, 6, "COUNT", 30, "Aufgabe 3")) {
    punkte++;
    aufgabe3 = 1;
}


//Blatt2

if (pruefeBlatt2(sheet2)) {
    punkte++;
    aufgabe4 = 1;
    System.out.println("Blatt 2 Aufgabe richtig");
} else {
    System.out.println("Blatt 2 Aufgabe nicht vollständig korrekt");
}
//Blatt3

if (pruefeBlatt3(sheet3)) {

    punkte++;
    aufgabe5 = 1;
    
    System.out.println("Blatt 3 Aufgabe richtig");

} else {

    System.out.println("Blatt 3 Aufgabe fehlerhaft");
}

            System.out.println("Gesamtpunkte: " + punkte + "/5");
            double prozent = (punkte / 5.0) * 100;

            System.out.println("Ergebnis: " + prozent + "%");
            erstelleErgebnisExcel(
                punkte,
                prozent,
                aufgabe1,
                aufgabe2,
                aufgabe3,
                aufgabe4,
                aufgabe5
            );
            if (prozent >= 70) {

                System.out.println("Prüfung bestanden");

            } else {

                System.out.println("Prüfung nicht bestanden");
            }

            workbook.close();
            file.close();

        } catch (Exception e) {
            System.out.println("Fehler beim Lesen der Excel-Datei");
            e.printStackTrace();
        }
    }

    public static boolean pruefeZelle(XSSFSheet sheet, int rowIndex, int colIndex,
                                 String erwarteteFunktion, double erwarteterWert, String aufgabeName) {

    var row = sheet.getRow(rowIndex);
    if (row == null) {
        System.out.println(aufgabeName + " falsch - keine Eingabe");
        return false;
    }

    var cell = row.getCell(colIndex);
    if (cell == null) {
        System.out.println(aufgabeName + " falsch - keine Eingabe");
        return false;
    }

    if (cell.getCellType() != CellType.FORMULA) {
        System.out.println(aufgabeName + " falsch - keine Formel verwendet");
        return false;
    }

    String formula = cell.getCellFormula().toUpperCase();
    double value = cell.getNumericCellValue();

    if (formula.contains(erwarteteFunktion) && Math.abs(value - erwarteterWert) < 0.01) {
        System.out.println(aufgabeName + " richtig");
        return true;
    } else {
        System.out.println(aufgabeName + " falsch - (" + formula + " = " + value + ")");
        return false;
    }
}

public static boolean pruefeBlatt2(XSSFSheet sheet) {

    boolean aufgabe_ok = true;

    System.out.println("\n--- Blatt 2 Prüfung ---");

    for (int i = 1; i <= 10; i++) {

        var row = sheet.getRow(i);
        if (row == null) {
            aufgabe_ok = false;
            System.out.println("Zeile " + (i + 1) + ": falsch - keine Eingabe");
            continue;
        }

        var cell = row.getCell(2);

        if (cell == null) {
            aufgabe_ok = false;
            System.out.println("Zeile " + (i + 1) + ": falsch - keine Eingabe");
            continue;
        }

        if (cell.getCellType() != CellType.FORMULA) {
            aufgabe_ok = false;
            System.out.println("Zeile " + (i + 1) + ": falsch - keine Formel");
            continue;
        }

        String formula = cell.getCellFormula().toUpperCase();
        double value = cell.getNumericCellValue();

        boolean usesFix = formula.contains("$F$2") || formula.contains("F$2");

        double bValue = sheet.getRow(i).getCell(1).getNumericCellValue();
        double price = sheet.getRow(1).getCell(5).getNumericCellValue();
        double expected = bValue * price;

        if (Math.abs(value - expected) < 0.01) {

            if (usesFix) {
                System.out.println("Zeile " + (i + 1) + ": richtig");
            } else {
                aufgabe_ok = false;
                System.out.println("Zeile " + (i + 1) + ": Ergebnis korrekt, aber Zellbezug nicht fixiert");
            }

        } else {
            aufgabe_ok = false;
            System.out.println("Zeile " + (i + 1) + ": falsch - falsches Ergebnis");
        }
    }

    return aufgabe_ok;
}

public static boolean pruefeBlatt3(XSSFSheet sheet) {

    boolean aufgabe_ok = true;

    System.out.println("\n--- Blatt 3 Prüfung ---");

    for (int i = 3; i <= 7; i++) {

        var row = sheet.getRow(i);

        var cell = row.getCell(2);

        String formula = cell.getCellFormula().toUpperCase();
        
        if (formula.contains("WENN") || formula.contains("IF")) {

            if (formula.contains(">29.99")) {

                System.out.println(
                    "Zeile " + (i + 1) +
                    ": richtig, aber Bestellung 4 wird nicht korrekt behandelt"
                );

            } else if (formula.contains(">=29.99")) {

                System.out.println(
                    "Zeile " + (i + 1) + ": richtig"
                );

            } else {

                aufgabe_ok = false;

                System.out.println(
                    "Zeile " + (i + 1) + ": falsche Formel"
                );
            }

        } else {

            aufgabe_ok = false;

            System.out.println(
                "Zeile " + (i + 1) + ": keine WENN-Funktion"
            );
        }
    }

    return aufgabe_ok;
}

public static void erstelleErgebnisExcel(int punkte,
        double prozent,
        int aufgabe1,
        int aufgabe2,
        int aufgabe3,
        int aufgabe4,
        int aufgabe5) {
            String studentName = "Max Mustermann";
     try {

        FileInputStream file = new FileInputStream("uploads/vorlage.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook(file);

        XSSFSheet sheet = workbook.getSheetAt(0);
        workbook.setSheetName(0, studentName);
        sheet.getRow(4).getCell(1).setCellValue(aufgabe1);
        sheet.getRow(5).getCell(1).setCellValue(aufgabe2);
        sheet.getRow(6).getCell(1).setCellValue(aufgabe3);
        sheet.getRow(7).getCell(1).setCellValue(aufgabe4);
        sheet.getRow(8).getCell(1).setCellValue(aufgabe5);

        sheet.getRow(2).getCell(0).setCellValue(studentName);

        sheet.getRow(23).getCell(3).setCellValue(punkte);

        sheet.getRow(23).getCell(4).setCellValue(prozent/100);

        if (prozent >= 70) {

            sheet.getRow(2).createCell(3).setCellValue("BESTANDEN");

        } else {

            sheet.getRow(2).createCell(3).setCellValue("NICHT BESTANDEN");
        }

        File ordner = new File("Ergebnisse/18M");

        ordner.mkdirs();
        FileOutputStream out =
                new FileOutputStream("Ergebnisse/18M/Ergebnis_Max_Mustermann.xlsx");

        workbook.write(out);

        out.close();
        workbook.close();
        file.close();

        erstelleSammelErgebnis(studentName, prozent);
        
        System.out.println("Ergebnisdatei erstellt.");

    } catch (Exception e) {

        e.printStackTrace();
    }
}
public static void erstelleSammelErgebnis(
        String studentName,
        double prozent) {
            try {
                System.out.println("Sammelergebnis gestartet");
                FileInputStream file =
                    new FileInputStream("uploads/vorlage_ergebnisse.xlsx");

                XSSFWorkbook workbook = new XSSFWorkbook(file);

                XSSFSheet sheet = workbook.getSheetAt(0);
                System.out.println("Vorlage geöffnet");
                sheet.getRow(0).getCell(4).setCellValue("18M");

                sheet.getRow(1).getCell(3).setCellValue("03.02.2026");

                int freieZeile = 2;

                while (sheet.getRow(freieZeile) != null
                    && sheet.getRow(freieZeile).getCell(0) != null
                    && !sheet.getRow(freieZeile).getCell(0).toString().isEmpty()) {

                freieZeile++;
                }
                System.out.println("Freie Zeile: " + freieZeile);
                sheet.createRow(freieZeile);

                        sheet.getRow(freieZeile).createCell(0).setCellValue(studentName);

                        sheet.getRow(freieZeile).createCell(1).setCellValue(prozent);
                        System.out.println("Speichere Datei");
                        FileOutputStream out =
                            new FileOutputStream("Ergebnisse/18M/Prüfungsergebnisse_18M.xlsx");

                        System.out.println("Schreibe Sammeldatei");
                        workbook.write(out);

                        out.close();
                        workbook.close();
                        file.close();

                        System.out.println("Sammelergebnis gespeichert.");

            } catch (Exception e) {

                e.printStackTrace();
}

}
}
