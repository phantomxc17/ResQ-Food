package Projek;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Pagemakanan extends JFrame {
    private JTable table;

    public Pagemakanan() {
        getContentPane().setBackground(Color.CYAN);
        setTitle("ResQ Food");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(true);
        getContentPane().setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Selamat Berbelanja Makanan Favoritmu");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblNewLabel_1.setForeground(SystemColor.textText);
        lblNewLabel_1.setBounds(33, 45, 691, 72);
        getContentPane().add(lblNewLabel_1);

        JPanel panel = new JPanel();
        panel.setBounds(248, 431, 287, 101);
        getContentPane().add(panel);
        panel.setLayout(null);

        JButton btnNewButton_2 = new JButton("Cart");
        btnNewButton_2.setBounds(10, 34, 110, 45);
        panel.add(btnNewButton_2);

        JButton btnNewButton = new JButton("Add to Cart");
        btnNewButton.setBounds(162, 34, 110, 45);
        panel.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pagecart cart = new Pagecart();
                cart.setVisible(true);
                dispose();
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(33, 128, 723, 292);
        getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Type", "Brand", "Expired Date", "Stock", "Price"
                }));

        JButton btnNewButton_1_1 = new JButton("Back");
        btnNewButton_1_1.setBounds(33, 11, 118, 36);
        getContentPane().add(btnNewButton_1_1);
        btnNewButton_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Homepage balik = new Homepage();
                balik.setVisible(true);
                dispose();
            }
        });
        showItem();

    }

    private void showItem() {
        String filePath = "Database/Datafood.txt";
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(";");
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addToCart() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1 || table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please select an item to add to the cart.");
            return;
        }

        String quantityString = JOptionPane.showInputDialog(this, "Enter quantity:");
        if (quantityString == null || quantityString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Quantity cannot be empty.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityString);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity should be a positive integer.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        double currentStock = Double.parseDouble(model.getValueAt(selectedRow, 3).toString());

        if (quantity > currentStock) {
            JOptionPane.showMessageDialog(this, "Insufficient stock. Please enter a smaller quantity.");
            return;
        }

        double newStock = currentStock - quantity;

        model.setValueAt(newStock, selectedRow, 3);

        String brand = model.getValueAt(selectedRow, 1).toString();

        try (BufferedReader reader = new BufferedReader(new FileReader("Database/Datafood.txt"))) {
            StringBuilder newContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(brand)) {

                    String[] parts = line.split(";");
                    if (parts.length >= 5) {
                        try {
                            double currentStockInDatabase = Double.parseDouble(parts[3]);
                            double updatedStock = currentStockInDatabase - quantity;
                            if (updatedStock <= 0) {

                                continue;
                            }
                            parts[3] = String.valueOf(updatedStock);
                            line = String.join(";", parts);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
                newContent.append(line).append("\n");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Database/Datafood.txt"))) {
                writer.write(newContent.toString());
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (newStock <= 0) {

            model.removeRow(selectedRow);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Database/Datacart.txt", true))) {
            for (int i = 0; i < model.getColumnCount(); i++) {
                if (i != 3) {
                    writer.write(model.getValueAt(selectedRow, i).toString());
                    writer.write(";");
                }
            }
            writer.write(quantity + ";");
            writer.write("1");
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JOptionPane.showMessageDialog(this, "Item quantity updated in cart successfully!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pagemakanan window = new Pagemakanan();
            window.setVisible(true);
        });
    }
}
