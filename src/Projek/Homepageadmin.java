package Projek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Homepageadmin extends JFrame {

    public Homepageadmin() {
        getContentPane().setBackground(Color.CYAN);
        setTitle("ResQ Food");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(true);
        getContentPane().setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Don't Forget to Update Stock !!! ");
        lblNewLabel_1.setBounds(34, 45, 643, 72);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblNewLabel_1.setForeground(SystemColor.textText);
        getContentPane().add(lblNewLabel_1);

        JButton btnMakan = new JButton("Makanan");
        btnMakan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pagemakananadmin update = new Pagemakananadmin();
                update.setVisible(true);
            }
        });
        btnMakan.setBounds(67, 260, 242, 158);
        btnMakan.setFont(new Font("Tahoma", Font.PLAIN, 17));
        getContentPane().add(btnMakan);

        JButton btnMinuman = new JButton("Minuman");
        btnMinuman.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pageminumanadmin update1 = new Pageminumanadmin();
                update1.setVisible(true);
            }
        });
        btnMinuman.setBounds(363, 260, 230, 158);
        btnMinuman.setFont(new Font("Tahoma", Font.PLAIN, 17));
        getContentPane().add(btnMinuman);

        JButton btnNewButton = new JButton("Home");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                First update2 = new First();
                update2.setVisible(true);
                dispose();
            }
        });
        btnNewButton.setBounds(216, 452, 230, 61);
        getContentPane().add(btnNewButton);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon("F:\\TugasBinus\\semester 3\\OopPkm\\Img\\invent.png"));
        lblNewLabel.setBounds(-113, -66, 960, 675);
        getContentPane().add(lblNewLabel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Homepageadmin window = new Homepageadmin();
            window.setVisible(true);
        });
    }
}
