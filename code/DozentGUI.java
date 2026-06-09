import javax.swing.*;

import model.CurrentUser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DozentGUI {

    public DozentGUI() {

        JFrame frame = new JFrame("Dozentenbereich");

        frame.setSize(1000, 600);

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());

        frame.getContentPane().setBackground(
                Style.BACKGROUND);

       
        // Benutzeranzeige oben
        

        JLabel userLabel =
                new JLabel(
                        "Angemeldet als: "
                                + CurrentUser.getCurrentUser().getUsername()
                                + " ("
                                + CurrentUser.getCurrentUser().getRole()
                                + ")");
        JButton logoutButton = new JButton("Logout");
        Style.styleButton(logoutButton);
        logoutButton.setPreferredSize(Style.SMALL_BUTTON_SIZE);

        logoutButton.addActionListener(
                new ActionListener() {

                public void actionPerformed( ActionEvent e) {

                CurrentUser.setCurrentUser(null);

                frame.dispose();

                new LoginGUI();
        }
        });

        JPanel userPanel = new JPanel( new FlowLayout( FlowLayout.LEFT));

        userPanel.setBackground( Style.BACKGROUND);
        userPanel.add(userLabel);
        userPanel.add(logoutButton);

        userLabel.setFont(Style.BUTTON_FONT);
        userLabel.setForeground(Style.PRIMARY);

        JLabel logoLabel = Style.createLogo();

        JPanel headerPanel =new JPanel(new BorderLayout());

        headerPanel.setBackground( Style.BACKGROUND);
        headerPanel.add( userPanel, BorderLayout.WEST);
        headerPanel.add(logoLabel,  BorderLayout.EAST);

        
        // Linke Seite
        

        JPanel leftPanel = new JPanel();

        leftPanel.setLayout(
                new BoxLayout(
                        leftPanel,
                        BoxLayout.Y_AXIS));

        leftPanel.setBackground(
                Style.BACKGROUND);

        JLabel vergangeneLabel =
                new JLabel("Vergangene Prüfungen");

        DefaultListModel<String> vergangeneModel =
                new DefaultListModel<>();

        vergangeneModel.addElement(
                "Excel Test WS25");

        vergangeneModel.addElement(
                "Excel Test SS26");

        JList<String> vergangeneListe =
                new JList<>(vergangeneModel);

        JLabel geplanteLabel =
                new JLabel("Geplante Prüfungen");

        DefaultListModel<String> geplanteModel =
                new DefaultListModel<>();

        geplanteModel.addElement(
                "Excel Abschlussprüfung");

        geplanteModel.addElement(
                "Excel Nachprüfung");

        JList<String> geplanteListe =
                new JList<>(geplanteModel);

        leftPanel.add(vergangeneLabel);
        leftPanel.add(new JScrollPane(vergangeneListe));

        leftPanel.add(Box.createVerticalStrut(20));

        leftPanel.add(geplanteLabel);
        leftPanel.add(new JScrollPane(geplanteListe));

       
        // Rechte Seite
       

        JPanel rightPanel = new JPanel();

        rightPanel.setBackground(
                Style.BACKGROUND);

        rightPanel.setLayout(
                new BoxLayout(
                rightPanel,
                BoxLayout.Y_AXIS));


        JButton pruefungButton =
                new JButton("Prüfung anlegen");

        JButton ergebnisseButton =
                new JButton("Ergebnisse ansehen");

        JButton studentenButton =
                new JButton("Studenten verwalten");
                Dimension buttonSize =
        new Dimension(250, 60);

        JLabel actionLabel = new JLabel("Dozentenfunktionen");
        actionLabel.setFont(Style.TITLE_FONT);
        actionLabel.setForeground(Style.PRIMARY);
        actionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pruefungButton.setPreferredSize(buttonSize);
        ergebnisseButton.setPreferredSize(buttonSize);
        studentenButton.setPreferredSize(buttonSize);

        pruefungButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ergebnisseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        studentenButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        Style.styleButton(pruefungButton);
        Style.styleButton(ergebnisseButton);
        Style.styleButton(studentenButton);
        
        rightPanel.add(Box.createVerticalStrut(50));

        rightPanel.add(actionLabel);

        rightPanel.add(Box.createVerticalStrut(40));

        rightPanel.add(pruefungButton);

        rightPanel.add(Box.createVerticalStrut(20));

        rightPanel.add(ergebnisseButton);

        rightPanel.add(Box.createVerticalStrut(20));

        rightPanel.add(studentenButton);

        
        // SplitPane
        

        JSplitPane splitPane =
                new JSplitPane(
                        JSplitPane.HORIZONTAL_SPLIT,
                        leftPanel,
                        rightPanel);

        splitPane.setDividerLocation(330);

        frame.add(headerPanel,BorderLayout.NORTH);
        frame.add(splitPane,BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}