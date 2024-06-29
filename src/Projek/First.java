package Projek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class First extends JFrame {

    public First() {

        setTitle("ResQ Food");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(true);

        JButton btnAdmin = new JButton("Admin");
        btnAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Idpageadmin login1 = new Idpageadmin();
                login1.setVisible(true);
                dispose();

            }
        });
        btnAdmin.setBackground(Color.LIGHT_GRAY);
        btnAdmin.setBounds(344, 427, 258, 82);

        JButton btnBuy = new JButton("Buy");
        btnBuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Idpage login = new Idpage();
                login.setVisible(true);
                dispose();
            }
        });
        btnBuy.setBackground(Color.LIGHT_GRAY);
        btnBuy.setBounds(344, 334, 258, 82);

        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(btnAdmin);
        contentPane.add(btnBuy);

        JLabel lblNewLabel = new JLabel("Welcome to ResQ Food");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 21));
        lblNewLabel.setBounds(336, 39, 300, 75);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("F:\\TugasBinus\\semester 3\\OopPkm\\Img\\warung1.png"));
        lblNewLabel_1.setBounds(0, 0, 784, 561);
        getContentPane().add(lblNewLabel_1);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            First window = new First();
            window.setVisible(true);
        });
    }
}
