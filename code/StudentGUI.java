import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class StudentGUI {

    public StudentGUI() {
        System.out.println(
        CurrentUser.getCurrentUser().getUsername()
);

JFrame frame = new JFrame("Excel-Prüfungssystem");

frame.setSize(700, 500);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setLayout(new BorderLayout());
frame.getContentPane().setBackground(Style.BACKGROUND);


// Logo

JLabel logoLabel = Style.createLogo();

JPanel logoPanel =
        new JPanel(new FlowLayout(FlowLayout.RIGHT));


logoPanel.setBackground(Style.BACKGROUND);
logoPanel.add(logoLabel);


// Titel

JLabel titleLabel =
        new JLabel("Excel-Prüfungssystem");
       
        JLabel userLabel =
        new JLabel(
            "Angemeldet als: "
            + CurrentUser.getCurrentUser().getUsername()
            + " ("
            + CurrentUser.getCurrentUser().getRole()
            + ")"
        );

userLabel.setFont(Style.BUTTON_FONT);
userLabel.setForeground(Style.PRIMARY);

titleLabel.setFont(Style.TITLE_FONT);
titleLabel.setForeground(Style.PRIMARY);


// Timer

JLabel timerLabel =
        new JLabel("Zeit: 00:00");

timerLabel.setFont(Style.TITLE_FONT);
timerLabel.setForeground(Style.PRIMARY);


// Statusmeldungen

JLabel infoLabel =
        new JLabel("");

infoLabel.setFont(Style.BUTTON_FONT);
infoLabel.setForeground(Style.PRIMARY);


// Buttons

JButton uploadButton = new JButton("Lösung hochladen");
Style.styleButton(uploadButton);
JButton startButton = new JButton("Prüfung starten");
Style.styleButton(startButton);


// Timer-Variable

final int[] sekunden = {0};

        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                sekunden[0]++;
                if (sekunden[0] == 10800) {

                    infoLabel.setText("Prüfungszeit vorbei! Bitte Lösung hochladen.");
                }

                if (sekunden[0] >= 11700) {


                    uploadButton.setEnabled(false);

                     ((Timer)e.getSource()).stop();

                    infoLabel.setText("Upload-Zeit abgelaufen!");
                }

                int minuten = sekunden[0] / 60;
                int restSekunden = sekunden[0] % 60;

                timerLabel.setText(
                    String.format("Zeit: %02d:%02d", minuten, restSekunden)
                 );
            }
        });
        

        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                timer.start();
                infoLabel.setText("Viel Erfolg!");
                uploadButton.setEnabled(true);
                try {

                    Desktop.getDesktop().open(new File("uploads/aufgabe.xlsx"));

                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }
        });

        uploadButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JFileChooser chooser = new JFileChooser();

                int result = chooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {

                File file = chooser.getSelectedFile();
                
                timer.stop();

                Main.pruefeDatei(file.getAbsolutePath());
                infoLabel.setText("Lösung wurde hochgeladen.");
                }
            }
        });

JPanel headerPanel = new JPanel(new BorderLayout());

headerPanel.setBackground(Style.BACKGROUND);
headerPanel.add(logoLabel, BorderLayout.EAST);


JPanel buttonPanel = new JPanel(new BorderLayout());
buttonPanel.setBackground(Style.BACKGROUND);


JPanel topButtonPanel = new JPanel();
topButtonPanel.setBackground(Style.BACKGROUND);

topButtonPanel.add(startButton);
topButtonPanel.add(uploadButton);


buttonPanel.add(topButtonPanel, BorderLayout.NORTH);
buttonPanel.add(timerLabel, BorderLayout.CENTER);
timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

JPanel centerPanel = new JPanel(new BorderLayout());

centerPanel.setBackground(Style.BACKGROUND);

centerPanel.add(userLabel, BorderLayout.NORTH);
centerPanel.add(buttonPanel, BorderLayout.CENTER);

frame.add(headerPanel, BorderLayout.NORTH);
frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}