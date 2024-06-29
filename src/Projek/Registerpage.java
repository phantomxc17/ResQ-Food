package Projek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Registerpage extends JFrame {
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;
    private JTextField textNama;
    private JTextField textEmail;

    public Registerpage() {
        getContentPane().setBackground(Color.CYAN);
        setTitle("ResQ Food");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(true);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Nama");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel.setBounds(372, 144, 55, 33);
        getContentPane().add(lblNewLabel);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(361, 288, 328, 33);
        getContentPane().add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(241, 394, 295, 29);
        getContentPane().add(passwordField);

        JButton btnNewButton_1 = new JButton("Register");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton_1.setBackground(Color.GREEN);
        btnNewButton_1.setBounds(241, 453, 295, 59);
        getContentPane().add(btnNewButton_1);

        JLabel lblNewLabel_1 = new JLabel("Welcome To E-Warung");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblNewLabel_1.setForeground(Color.BLACK);
        lblNewLabel_1.setBounds(216, 28, 408, 72);
        getContentPane().add(lblNewLabel_1);

        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(241, 321, 295, 29);
        getContentPane().add(passwordField_1);

        JLabel lblConfirmPassword = new JLabel("Confirm Password");
        lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblConfirmPassword.setBounds(329, 361, 328, 33);
        getContentPane().add(lblConfirmPassword);

        JLabel lblNewLabel_2 = new JLabel("Email");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_2.setBounds(372, 216, 55, 33);
        getContentPane().add(lblNewLabel_2);

        textNama = new JTextField();
        textNama.setBounds(241, 171, 295, 33);
        getContentPane().add(textNama);
        textNama.setColumns(10);

        textEmail = new JTextField();
        textEmail.setColumns(10);
        textEmail.setBounds(241, 244, 295, 33);
        getContentPane().add(textEmail);
    }

    private void register() {
        String name = textNama.getText();
        String email = textEmail.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(passwordField_1.getPassword());

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userData = name + ";" + email + ";" + password;
        String filePath = "Database/Datauser.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(userData);
            writer.newLine();
            writer.close();
            JOptionPane.showMessageDialog(this, "Registration success!", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();

            Idpage idPage = new Idpage();
            idPage.setVisible(true);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Registerpage window = new Registerpage();
            window.setVisible(true);
        });
    }
}
