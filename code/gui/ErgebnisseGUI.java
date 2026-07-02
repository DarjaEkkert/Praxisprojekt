package gui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import database.DatabaseManager;
import database.ErgebnisRepository;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.util.*;

import model.AufgabeErgebnis;
import model.Pruefung;

public class ErgebnisseGUI extends JFrame {

    public ErgebnisseGUI(Pruefung pruefung) {

getContentPane().setBackground(Style.BACKGROUND);
setLayout(new BorderLayout());

JPanel panel = new JPanel();
panel.setBackground(Style.BACKGROUND);
panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

JLabel titelLabel = new JLabel("Ergebnisse");
Style.styleTitle(titelLabel);

JLabel pruefungLabel =
        new JLabel("Prüfung: " + pruefung.getName());
Style.styleInfo(pruefungLabel);

JLabel datumLabel =
        new JLabel("Datum: " + pruefung.getDatum());
Style.styleInfo(datumLabel);

JLabel gruppeLabel =
        new JLabel("Gruppe: " + pruefung.getGruppe());
Style.styleInfo(gruppeLabel);

panel.add(titelLabel);
panel.add(Box.createVerticalStrut(20));
panel.add(pruefungLabel);
panel.add(datumLabel);
panel.add(gruppeLabel);

panel.add(Box.createVerticalStrut(20));

JLabel studentenLabel =
        new JLabel("Abgegebene Prüfungen");
Style.styleInfo(studentenLabel);

panel.add(studentenLabel);

DatabaseManager db = new DatabaseManager();
db.connect();

ErgebnisRepository repository =
        new ErgebnisRepository(db);

ArrayList<String> studenten =
        repository.getStudentenByPruefung(
                pruefung.getId());

db.disconnect();

JList<String> studentenListe =
        new JList<>(studenten.toArray(new String[0]));

JScrollPane scrollPane =
        new JScrollPane(studentenListe);

scrollPane.setPreferredSize(
        new Dimension(300, 200));

panel.add(scrollPane);

panel.add(Box.createVerticalStrut(20));

JButton oeffnenButton =
        new JButton("Korrektur öffnen");

Style.styleButton(oeffnenButton);

oeffnenButton.setAlignmentX(CENTER_ALIGNMENT);

oeffnenButton.addActionListener(e -> {

    String student =
            studentenListe.getSelectedValue();

    if (student == null) {

        JOptionPane.showMessageDialog(
                this,
                "Bitte wählen Sie einen Studenten aus.");

        return;
    }

    
    db.connect();

    ErgebnisRepository ergebnisRepository =
            new ErgebnisRepository(db);

    int ergebnisId =
            ergebnisRepository.getErgebnisId(
                    pruefung.getId(),
                    student);

    java.util.List<AufgabeErgebnis> aufgaben =
        ergebnisRepository.getAufgabeErgebnisse(
                ergebnisId);

    db.disconnect();
    new ManuelleKorrekturGUI(
            ergebnisId,
            student,
            aufgaben);

    
});

panel.add(oeffnenButton);

add(panel);

setTitle("Ergebnisse");
setSize(700, 500);
setLocationRelativeTo(null);
setDefaultCloseOperation(DISPOSE_ON_CLOSE);

setVisible(true);
    }

  
}