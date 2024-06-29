package Projek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Idpageadmin extends JFrame {
    private JTextField textMail;
    private JPasswordField passwordField;

    public Idpageadmin() {
        getContentPane().setBackground(new Color(255, 128, 128));
        setTitle("ResQ Food");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(true);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Email");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel.setBounds(372, 144, 55, 33);
        getContentPane().add(lblNewLabel);

        JLabel textPass = new JLabel("Password");
        textPass.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textPass.setBounds(357, 227, 328, 33);
        getContentPane().add(textPass);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Checkdata();
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton.setBackground(Color.YELLOW);
        btnNewButton.setBounds(299, 313, 194, 59);
        getContentPane().add(btnNewButton);

        JLabel lblNewLabel_1 = new JLabel("Welcome Admin");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblNewLabel_1.setForeground(Color.BLACK);
        lblNewLabel_1.setBounds(266, 44, 408, 72);
        getContentPane().add(lblNewLabel_1);

        textMail = new JTextField();
        textMail.setBounds(241, 183, 295, 39);
        getContentPane().add(textMail);
        textMail.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(241, 259, 295, 39);
        getContentPane().add(passwordField);

        this.dispose();
        return;
    }

    private void Checkdata() {
        String email = textMail.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        String filePath = "Database/Dataadmin.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");
                if (userData.length == 3 && userData[1].equals(email) && userData[2].equals(password)) {

                    JOptionPane.showMessageDialog(this, "Login successful!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    Homepageadmin homePage1 = new Homepageadmin();
                    homePage1.setVisible(true);

                    this.dispose();
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Invalid email or password. Please try again.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Idpageadmin window = new Idpageadmin();
            window.setVisible(true);
        });
    }
}
