package service;

import java.io.FileOutputStream;

import org.openpdf.text.Document;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfWriter;

import database.DatabaseManager;
import database.PruefungsteilnehmerRepository;
import database.UserRepository;

import org.openpdf.text.Font;
import org.openpdf.text.Image;
import java.util.ArrayList;

import model.User;
import database.PruefungsRepository;
import model.Pruefung;

public class PdfService {

    public void createZugangsdatenPdf(
        String pruefung,
        String gruppe,
        String datum,
        String dozent,
        ArrayList<User> teilnehmer) {

        try {

            Document document = new Document();
            Image logo =Image.getInstance("assets/logo.png");
            logo.scaleToFit(120,60);

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream("Zugangsdaten_" + gruppe + ".pdf"));

            document.open();
            document.add(logo); 
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("AIT Excel-Prüfungssystem", titleFont);
            document.add(title);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(   "AIT Excel-Pruefungssystem"));
            document.add( new Paragraph("Prüfung: " + pruefung));
            document.add(new Paragraph("Datum: " + datum));
            document.add( new Paragraph("Gruppe: " + gruppe));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Dozent: " + dozent));
            document.add(new Paragraph("Anzahl Teilnehmer: " + teilnehmer.size()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("================================================"));
            document.add(new Paragraph("Teilnehmer"));
            document.add(new Paragraph(" "));

for (User user : teilnehmer) {
    document.add(new Paragraph(user.getVollerName()));
    document.add(new Paragraph("Benutzername: " + user.getUsername()));
    document.add(new Paragraph("Passwort: " + user.getPassword()));
    document.add(new Paragraph("----------------------------------------"));
}

            document.close();

            System.out.println(
                    "PDF erfolgreich erstellt.");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
public void createZugangsdatenPdf(int pruefungId) {

    DatabaseManager db = new DatabaseManager();
    db.connect();

    PruefungsRepository pruefungsRepository = new PruefungsRepository(db);

    Pruefung pruefung = pruefungsRepository.getPruefungById(pruefungId);
    PruefungsteilnehmerRepository teilnehmerRepository = new PruefungsteilnehmerRepository(db);

    UserRepository userRepository = new UserRepository(db);

    ArrayList<String> usernames = teilnehmerRepository.getTeilnehmerByPruefungId(pruefungId);

    ArrayList<User> teilnehmer =  new ArrayList<>();

    for (String username : usernames) {

        User user = userRepository.getUserByUsername(username);

        if (user != null) {

            teilnehmer.add(user);
        }
    }

    if (pruefung == null) {

        System.out.println("Prüfung nicht gefunden.");
        db.disconnect();
        return;
    }

    createZugangsdatenPdf(
            pruefung.getName(),
            pruefung.getGruppe(),
            pruefung.getDatum(),
            "Dozent",
        teilnehmer);

    db.disconnect();
}
}