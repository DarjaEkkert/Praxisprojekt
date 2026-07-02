package gui;

import javax.swing.*;

import database.DatabaseManager;
import database.ErgebnisRepository;

import java.awt.*;
import java.util.List;
import model.AufgabeErgebnis;
import service.ManuelleKorrekturService;


public class ManuelleKorrekturGUI extends JFrame {

    
    private List<AufgabeErgebnis> ergebnisse;
    private int index = 0;

    private JLabel aufgabeLabel;
    private JLabel statusLabel;
    private JTextField punkteField;
    private AufgabeErgebnis aufgabe;
    private int ergebnisId;
    
    public ManuelleKorrekturGUI(
        int ergebnisId,
        String student,
        List<AufgabeErgebnis> ergebnisse){
                this.ergebnisId = ergebnisId;
                this.ergebnisse = ergebnisse;

//nur falsche aufgaben anzeigen
while (index < this.ergebnisse.size()
        && this.ergebnisse.get(index).getStatus()
            != model.AufgabenStatus.MANUELL_PRUEFEN) {

    index++;
}
if (index >= this.ergebnisse.size()) {

    JOptionPane.showMessageDialog(
            this,
            "Für diesen Studenten sind keine manuellen Korrekturen erforderlich.");

    dispose();
    return;
}
aufgabe = this.ergebnisse.get(index);

JPanel panel = new JPanel();
panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
panel.setBackground(Style.BACKGROUND);

JLabel studentLabel =
        new JLabel("Student: " + student);

Style.styleInfo(studentLabel);
        aufgabeLabel =
            new JLabel("Aufgabe: " + aufgabe.getNummer());
            Style.styleTitle(aufgabeLabel);

panel.add(studentLabel);
panel.add(Box.createVerticalStrut(15));

panel.add(aufgabeLabel);

        statusLabel =
            new JLabel("Status: " + aufgabe.getStatus());
            Style.styleInfo(statusLabel);

panel.add(statusLabel);

panel.add(Box.createVerticalStrut(20));

JLabel punkteLabel = new JLabel("Punkte:");
Style.styleInfo(punkteLabel);
punkteField = new JTextField();

punkteField.setText(String.valueOf(aufgabe.getPunkte()));
punkteField.setMaximumSize(Style.FIELD_SIZE);

panel.add(punkteLabel);
panel.add(punkteField);


JButton weiterButton = new JButton("Nächste Aufgabe");
Style.styleButton(weiterButton);

weiterButton.addActionListener(e -> {

    double punkte =
        Double.parseDouble(punkteField.getText());

    ManuelleKorrekturService.bewerteAufgabe(
        aufgabe,
        punkte);
    
        DatabaseManager db = new DatabaseManager();
db.connect();

ErgebnisRepository repository =
        new ErgebnisRepository(db);

repository.updateAufgabeErgebnis(
        ergebnisId,
        aufgabe);



    statusLabel.setText(
        "Status: " + aufgabe.getStatus());

    index++;
    while (index < ergebnisse.size()
        && ergebnisse.get(index).getStatus()
            != model.AufgabenStatus.MANUELL_PRUEFEN) {

    index++;
}

if (index >= ergebnisse.size()) {

    double gesamtpunkte =
            ManuelleKorrekturService.berechneGesamtpunkte(ergebnisse);

double maxPunkte =
        ManuelleKorrekturService
                .berechneMaxPunkte(ergebnisse);

double prozent =
        (gesamtpunkte / maxPunkte) * 100;

        repository.updateGesamtergebnis(
        ergebnisId,
        gesamtpunkte,
        prozent,
        prozent >= 70);

 db.disconnect();

    JOptionPane.showMessageDialog(
            this,
            "Korrektur abgeschlossen.\n\n"
            + "Gesamtpunkte: "
            + gesamtpunkte
            + "\nProzent: "
            + String.format("%.2f", prozent)
            + "%");

    dispose();
    return;
}

aufgabe = ergebnisse.get(index);

aufgabeLabel.setText(
        "Aufgabe: " + aufgabe.getNummer());

statusLabel.setText(
        "Status: " + aufgabe.getStatus());

punkteField.setText(
        String.valueOf(aufgabe.getPunkte()));

});



panel.add(Box.createVerticalStrut(20));

panel.add(Box.createVerticalStrut(10));
panel.add(weiterButton);

        setTitle("Manuelle Korrektur");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

add(panel);
        setVisible(true);

    }
}