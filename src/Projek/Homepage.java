package Projek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Homepage extends JFrame {

    public Homepage() {
        getContentPane().setBackground(Color.CYAN);
        setTitle("ResQ Food");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(true);
        getContentPane().setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Welcome To ResQ food");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblNewLabel_1.setForeground(SystemColor.textText);
        lblNewLabel_1.setBounds(0, 45, 409, 72);
        getContentPane().add(lblNewLabel_1);

        JButton btnMakan = new JButton("Makanan");
        btnMakan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pagemakanan menu = new Pagemakanan();
                menu.setVisible(true);
                dispose();
            }
        });
        btnMakan.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnMakan.setBounds(121, 240, 513, 133);
        getContentPane().add(btnMakan);

        JButton btnMinuman = new JButton("Minuman");
        btnMinuman.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pageminuman menu1 = new Pageminuman();
                menu1.setVisible(true);
                dispose();
            }
        });
        btnMinuman.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnMinuman.setBounds(121, 384, 513, 133);
        getContentPane().add(btnMinuman);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon("F:\\TugasBinus\\semester 3\\OopPkm\\Img\\home.jpg"));
        lblNewLabel.setBounds(0, 0, 784, 561);
        getContentPane().add(lblNewLabel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Homepage window = new Homepage();
            window.setVisible(true);
        });
    }
}
