import javax.swing.*;
import java.awt.*;

public class PruefungAnlegenGUI {

    public PruefungAnlegenGUI() {

        JFrame frame = new JFrame("Prüfung anlegen");

        frame.setSize(700, 800);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Style.BACKGROUND);

        JLabel titleLabel = new JLabel("Neue Prüfung anlegen", SwingConstants.CENTER);
        titleLabel.setFont(Style.TITLE_FONT);
        titleLabel.setForeground(Style.PRIMARY);

        JPanel formPanel = new JPanel();
        formPanel.setBackground(Style.BACKGROUND);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Prüfungsname:");
        JTextField nameField = new JTextField();
        nameField.setMaximumSize(Style.FIELD_SIZE);

        JLabel dateLabel = new JLabel("Datum:");
        JTextField dateField = new JTextField();
        dateField.setMaximumSize(Style.FIELD_SIZE);

        JLabel gruppeLabel = new JLabel("Gruppe:");
        JTextField gruppeField = new JTextField();
        gruppeField.setMaximumSize(Style.FIELD_SIZE);

        JLabel dauerLabel = new JLabel("Dauer (Minuten):");
        JTextField dauerField = new JTextField();
        dauerField.setMaximumSize(Style.FIELD_SIZE);

        JLabel aufgabeLabel = new JLabel("Aufgabe:");
        JButton aufgabeButton = new JButton("Datei auswählen");
        JLabel aufgabeDateiLabel = new JLabel("Keine Datei ausgewählt");

        JLabel loesungLabel = new JLabel("Musterlösung:");
        JButton loesungButton = new JButton("Datei auswählen");
        JLabel loesungDateiLabel = new JLabel("Keine Datei ausgewählt");

        JLabel teilnehmerLabel = new JLabel("Teilnehmerliste:");
        JButton teilnehmerButton = new JButton("Datei auswählen");
        JLabel teilnehmerDateiLabel = new JLabel("Keine Datei ausgewählt");

        Style.styleButton(aufgabeButton);
        Style.styleButton(loesungButton);
        Style.styleButton(teilnehmerButton);

        aufgabeButton.setPreferredSize(Style.SMALL_BUTTON_SIZE);
        loesungButton.setPreferredSize(Style.SMALL_BUTTON_SIZE);
        teilnehmerButton.setPreferredSize(Style.SMALL_BUTTON_SIZE);

aufgabeButton.addActionListener(e -> {

    JFileChooser chooser = new JFileChooser();

    int result = chooser.showOpenDialog(frame);

    if (result == JFileChooser.APPROVE_OPTION) {

        aufgabeDateiLabel.setText(
                chooser.getSelectedFile().getName());
    }
});
loesungButton.addActionListener(e -> {

    JFileChooser chooser = new JFileChooser();

    int result = chooser.showOpenDialog(frame);

    if (result == JFileChooser.APPROVE_OPTION) {

        loesungDateiLabel.setText(
                chooser.getSelectedFile().getName());
    }
});

teilnehmerButton.addActionListener(e -> {

    JFileChooser chooser = new JFileChooser();

    int result = chooser.showOpenDialog(frame);

    if (result == JFileChooser.APPROVE_OPTION) {

        teilnehmerDateiLabel.setText(
                chooser.getSelectedFile().getName());
    }
});

        JButton speichernButton = new JButton("Speichern");
        JButton abbrechenButton = new JButton("Abbrechen");

        Style.styleButton(speichernButton);
        Style.styleButton(abbrechenButton);

        formPanel.add(nameLabel);
        formPanel.add(nameField);

        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(dateLabel);
        formPanel.add(dateField);

        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(gruppeLabel);
        formPanel.add(gruppeField);

        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(dauerLabel);
        formPanel.add(dauerField);

        formPanel.add(Box.createVerticalStrut(20));

        formPanel.add(aufgabeLabel);
        formPanel.add(aufgabeButton);
        formPanel.add(aufgabeDateiLabel);

        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(loesungLabel);
        formPanel.add(loesungButton);
        formPanel.add(loesungDateiLabel);

        formPanel.add(Box.createVerticalStrut(10));

        formPanel.add(teilnehmerLabel);
        formPanel.add(teilnehmerButton);
        formPanel.add(teilnehmerDateiLabel);

        formPanel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Style.BACKGROUND);

        buttonPanel.add(speichernButton);
        buttonPanel.add(abbrechenButton);

        formPanel.add(buttonPanel);

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(formPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}