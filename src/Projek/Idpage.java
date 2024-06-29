package Projek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Idpage extends JFrame {
    private JTextField textMail;
    private JPasswordField passwordField;

    public Idpage() {
        getContentPane().setBackground(Color.CYAN);
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

        JButton btnNewButton_1 = new JButton("Register");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Registerpage regist = new Registerpage();
                regist.setVisible(true);
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

        JLabel lblNewLabel_2 = new JLabel("Belum Punya Akun?");
        lblNewLabel_2.setBounds(241, 422, 123, 33);
        getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_2_1 = new JLabel("Daftar Yuk");
        lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_2_1.setForeground(Color.BLUE);
        lblNewLabel_2_1.setBounds(359, 422, 103, 33);
        getContentPane().add(lblNewLabel_2_1);

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

        String filePath = "Database/Datauser.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");
                if (userData.length == 3 && userData[1].equals(email) && userData[2].equals(password)) {

                    JOptionPane.showMessageDialog(this, "Login successful!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    Homepage homePage = new Homepage();
                    homePage.setVisible(true);

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
            Idpage window = new Idpage();
            window.setVisible(true);
        });
    }
}
