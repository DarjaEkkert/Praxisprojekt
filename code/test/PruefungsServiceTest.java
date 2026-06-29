package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import service.PruefungsService;

public class PruefungsServiceTest {


@Test
void sollteTrueLiefernWennFormelGueltigIst() {

    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet();

    for (int i = 3; i <= 7; i++) {

        XSSFRow row = sheet.createRow(i);

        row.createCell(2)
           .setCellFormula("IF(A1>=29.99,0,4.99)");
    }

    boolean ergebnis = PruefungsService.pruefeBlatt3(sheet);

    assertTrue(ergebnis);
}


@Test
void sollteFalseLiefernWennKeineWennFormelVerwendetWird() {

    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet();

    for (int i = 3; i <= 7; i++) {

        XSSFRow row = sheet.createRow(i);

        row.createCell(2)
           .setCellFormula("SUM(A1:A10)");
    }

    boolean ergebnis = PruefungsService.pruefeBlatt3(sheet);

    assertFalse(ergebnis);
}

@Test
void sollteExceptionWerfenWennZelleKeineFormelEnthaelt() {

    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet();

    for (int i = 3; i <= 7; i++) {

        XSSFRow row = sheet.createRow(i);

        row.createCell(2).setCellValue("Ja");
    }

    assertThrows(
        IllegalStateException.class,
        () -> PruefungsService.pruefeBlatt3(sheet)
    );
}

@Test
void sollteNullPointerExceptionWerfenWennFormelInFalscherZelleSteht() {

    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet();

    for (int i = 3; i <= 7; i++) {

        XSSFRow row = sheet.createRow(i);

        // Formel absichtlich in Spalte D statt C
        row.createCell(3)
           .setCellFormula("IF(A1>=29.99,0,4.99)");
    }

    assertThrows(
        NullPointerException.class,
        () -> PruefungsService.pruefeBlatt3(sheet)
    );
}

@Test
void sollteAlternativeKorrekteFormelNichtErkennen() {

    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet();

    for (int i = 3; i <= 7; i++) {

        XSSFRow row = sheet.createRow(i);

        row.createCell(2)
           .setCellFormula("IF(A1<29.99,4.99,0)");
    }

    boolean ergebnis = PruefungsService.pruefeBlatt3(sheet);

    assertTrue(ergebnis);
}
}