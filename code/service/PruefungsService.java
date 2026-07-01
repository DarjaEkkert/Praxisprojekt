package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseManager;
import database.PruefungsRepository;
import model.Pruefung;
import model.AufgabeErgebnis;
import model.AufgabenStatus;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.CellType;


public class PruefungsService {

private static List<AufgabeErgebnis> vergleicheArbeitsmappe(
        XSSFWorkbook studentWorkbook,
        XSSFWorkbook musterWorkbook) {
        
        int aktuelleAufgabe = 0;
        int richtigeFormeln = 0;
        int falscheFormeln = 0;
        boolean aktuelleAufgabeFehler = false;
        boolean ersteAufgabe = true;
        List<AufgabeErgebnis> ergebnisse = new ArrayList<>();
        
        
    for (int i = 0;
         i < musterWorkbook.getNumberOfSheets();
         i++) {

        XSSFSheet studentSheet = studentWorkbook.getSheetAt(i);
        XSSFSheet musterSheet = musterWorkbook.getSheetAt(i);

        for (int zeile = 0;
             zeile <= musterSheet.getLastRowNum();
             zeile++) {

            XSSFRow musterRow = musterSheet.getRow(zeile);
            XSSFRow studentRow = studentSheet.getRow(zeile);

            if (musterRow == null) {
                continue;
            }

            if (studentRow == null) {
                continue;
            }

            for (int spalte = 0;
                 spalte < musterRow.getLastCellNum();
                 spalte++) {

                XSSFCell musterCell = musterRow.getCell( spalte,
                    Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                XSSFCell studentCell = studentRow == null
                    ? null
                    : studentRow.getCell(
                        spalte,
                    Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                if (musterCell.getCellType() == CellType.BLANK
                    && studentCell.getCellType() == CellType.BLANK) {

                    continue;
                }
                CellType typ =  musterCell.getCellType();

                if (typ == CellType.STRING) {

                    String text = musterCell.getStringCellValue().trim();

if (text.matches("Aufgabe\\s+\\d+.*")) {

    if (!ersteAufgabe) {

        ergebnisse.add(
            new AufgabeErgebnis(
                aktuelleAufgabe,
                aktuelleAufgabeFehler
                    ? AufgabenStatus.MANUELL_PRUEFEN
                    : AufgabenStatus.AUTOMATISCH_RICHTIG,
                aktuelleAufgabeFehler ? 0.0 : 1.0,
                1.0,
                "",
                null));
    }

    ersteAufgabe = false;
    aktuelleAufgabeFehler = false;
    aktuelleAufgabe++;

}
                }

                if (typ == CellType.FORMULA) {

                String musterFormel =  musterCell.getCellFormula();

                String studentFormel = studentCell.getCellFormula();

                if (musterFormel.equals(studentFormel)) {

                    richtigeFormeln++;

                } else {

                    falscheFormeln++;
                    aktuelleAufgabeFehler = true;
                    }

                }
            }
        }

    }
if (!ersteAufgabe) {

    ergebnisse.add(
        new AufgabeErgebnis(
            aktuelleAufgabe,
            aktuelleAufgabeFehler
                ? AufgabenStatus.MANUELL_PRUEFEN
                : AufgabenStatus.AUTOMATISCH_RICHTIG,
            aktuelleAufgabeFehler ? 0.0 : 1.0,
            1.0,
            "",
            null));
}


   return ergebnisse; 
}


public static void pruefeDatei(int pruefungId,String dateipfad) {

            DatabaseManager db = new DatabaseManager();

            db.connect();

            PruefungsRepository repository = new PruefungsRepository(db);
            Pruefung pruefung = repository.getPruefungById(pruefungId);

            db.disconnect();

        try {

            FileInputStream musterDatei =  new FileInputStream(  pruefung.getLoesungsPfad());
            XSSFWorkbook musterWorkbook = new XSSFWorkbook(musterDatei);
            FileInputStream file = new FileInputStream(dateipfad);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

  List<AufgabeErgebnis> ergebnisse =
        vergleicheArbeitsmappe(workbook, musterWorkbook);
double punkte = 0;

for (AufgabeErgebnis ergebnis : ergebnisse) {
    punkte += ergebnis.getPunkte();
}

    System.out.println("Gesamtpunkte: " + punkte);
    double prozent = (punkte / 11.0) * 100;
    System.out.println("Ergebnis: " + prozent + "%");

            if (prozent >= 70) {

                System.out.println("Prüfung bestanden");

            } else {

                System.out.println("Prüfung nicht bestanden");
            }

            musterWorkbook.close();
            musterDatei.close();
            workbook.close();
            file.close();

        } catch (Exception e) {
            System.out.println("Fehler beim Lesen der Excel-Datei");
            e.printStackTrace();
        }
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
