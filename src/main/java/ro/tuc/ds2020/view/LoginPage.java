package ro.tuc.ds2020.view;

import ro.tuc.ds2020.service.ApplianceService;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class LoginPage extends JFrame {

    private JTextField usernameText;
    private JPanel panel1;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JFrame frame;
    private ApplianceService applianceService;

    public LoginPage() {

        frame = new JFrame("Login Form");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 230));
        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            UUID id = applianceService.login(usernameText.getText(), String.valueOf(passwordText.getPassword()));
            System.out.println("User ID: " + id);
            if (id != null) {
                new ClientPage(applianceService, id);
            }
        });
    }

    public void setApplianceService(ApplianceService applianceService) {
        this.applianceService = applianceService;
    }
}
