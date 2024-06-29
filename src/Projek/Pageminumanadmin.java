package Projek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;

public class Pageminumanadmin extends JFrame {
    private JTable table;
    private JTextField txtfoodname;
    private JTextField txtbrand;
    private JTextField txtprice;
    private JSpinner spinnerqty;
    private JTextField textexpired;

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        String type = model.getValueAt(selectedRow, 0).toString();
        String brand = model.getValueAt(selectedRow, 1).toString();
        String expiredDate = model.getValueAt(selectedRow, 2).toString();

        String quantityToDeleteStr = JOptionPane.showInputDialog(this,
                "Enter the quantity to delete for " + type + " - " + brand);
        if (quantityToDeleteStr == null) {

            return;
        }

        try {
            double quantityToDelete = Double.parseDouble(quantityToDeleteStr);

            double currentQuantity = Double.parseDouble(model.getValueAt(selectedRow, 3).toString());
            if (quantityToDelete <= 0 || quantityToDelete > currentQuantity) {
                JOptionPane.showMessageDialog(this, "Please enter a valid quantity to delete.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (currentQuantity == quantityToDelete) {
                model.removeRow(selectedRow);
            } else {
                model.setValueAt(currentQuantity - quantityToDelete, selectedRow, 3);
            }

            removeEntryFromDatabase(type, brand, expiredDate, quantityToDelete);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numerical quantity to delete.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        updateTotalsInTable();
    }

    private void updateTotalsInTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            String quantityStr = model.getValueAt(i, 3) != null ? model.getValueAt(i, 3).toString() : "0";
            String priceStr = model.getValueAt(i, 4) != null ? model.getValueAt(i, 4).toString() : "0";

            double quantity = Double.parseDouble(quantityStr);
            double price = Double.parseDouble(priceStr);

            double total = calculateTotal(quantity, price);
            model.setValueAt(total, i, 5);
        }
    }

    private void updateQuantityInDatabase(String type, String brand, String expiredDate, double currentQuantity,
            double quantityToDelete) {
        String filePath = "Database/Datadrink.txt";
        String tempFilePath = "Database/TempDatadrink.txt";
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            writer = new BufferedWriter(new FileWriter(tempFilePath));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(";");
                String currentfoodType = rowData[0];
                String currentBrand = rowData[1];
                String currentExpired = rowData[2];

                if (currentfoodType.equals(type) && currentBrand.equals(brand) && currentExpired.equals(expiredDate)) {

                    double updatedQuantity = Double.parseDouble(rowData[3]) - quantityToDelete;

                    writer.write(type + ";" + brand + ";" + expiredDate + ";" + updatedQuantity + ";" + rowData[4] + ";"
                            + calculateTotal(updatedQuantity, Double.parseDouble(rowData[4])));
                } else {
                    writer.write(line);
                }

                writer.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error updating quantity in the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            try {

                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeEntryFromDatabase(String type, String brand, String expiredDate, double quantityToDelete) {
        String filePath = "Database/Datadrink.txt";
        String tempFilePath = "Database/TempDatadrink.txt";

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            writer = new BufferedWriter(new FileWriter(tempFilePath));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(";");
                String currentfoodType = rowData[0];
                String currentBrand = rowData[1];
                String currentExpired = rowData[2];
                double currentQuantity = Double.parseDouble(rowData[3]);

                if (currentfoodType.equals(type) && currentBrand.equals(brand) && currentExpired.equals(expiredDate)) {

                    double updatedQuantity = currentQuantity - quantityToDelete;

                    if (updatedQuantity < 0) {
                        JOptionPane.showMessageDialog(this, "Cannot delete more than the current quantity.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (updatedQuantity > 0) {
                        writer.write(type + ";" + brand + ";" + expiredDate + ";" + updatedQuantity + ";" + rowData[4]
                                + ";" + calculateTotal(updatedQuantity, Double.parseDouble(rowData[4])));
                        writer.newLine();
                    }
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error removing entry from the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            try {

                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File originalFile = new File(filePath);
        if (!originalFile.delete()) {
            JOptionPane.showMessageDialog(this, "Error deleting the original file.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        File tempFile = new File(tempFilePath);
        if (!tempFile.renameTo(originalFile)) {
            JOptionPane.showMessageDialog(this, "Error renaming the file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Pageminumanadmin() {
        getContentPane().setBackground(Color.CYAN);
        setTitle("ResQ Food");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(true);
        getContentPane().setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Current Drink Stock");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblNewLabel_1.setForeground(SystemColor.textText);
        lblNewLabel_1.setBounds(34, 38, 409, 72);
        getContentPane().add(lblNewLabel_1);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(34, 348, 668, 202);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Drink Name");
        lblNewLabel.setBounds(10, 11, 110, 26);
        panel.add(lblNewLabel);

        txtfoodname = new JTextField();
        txtfoodname.setBounds(10, 37, 144, 26);
        panel.add(txtfoodname);
        txtfoodname.setColumns(10);

        JLabel lblBrand = new JLabel("Brand");
        lblBrand.setBounds(10, 74, 110, 26);
        panel.add(lblBrand);

        txtbrand = new JTextField();
        txtbrand.setColumns(10);
        txtbrand.setBounds(10, 100, 144, 26);
        panel.add(txtbrand);

        JLabel lblQuantity = new JLabel("Quantity");
        lblQuantity.setBounds(250, 11, 110, 26);
        panel.add(lblQuantity);

        spinnerqty = new JSpinner();
        spinnerqty.setBounds(250, 37, 68, 26);
        panel.add(spinnerqty);

        JButton btnNewButton = new JButton("Add");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updatefood();
            }
        });
        btnNewButton.setBounds(477, 55, 138, 45);
        panel.add(btnNewButton);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedRow();
            }
        });
        btnDelete.setBounds(477, 128, 138, 45);
        panel.add(btnDelete);

        txtprice = new JTextField();
        txtprice.setColumns(10);
        txtprice.setBounds(10, 158, 260, 26);
        panel.add(txtprice);

        JLabel lblPrice = new JLabel("Price");
        lblPrice.setBounds(10, 137, 86, 26);
        panel.add(lblPrice);

        textexpired = new JTextField();
        textexpired.setBounds(250, 100, 138, 26);
        panel.add(textexpired);
        textexpired.setColumns(10);

        JLabel lblExpiredDate = new JLabel("Expired Date");
        lblExpiredDate.setBounds(247, 74, 110, 26);
        panel.add(lblExpiredDate);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 98, 764, 239);
        getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Type", "Brand", "Expired Date", "Quantity", "Price", "Total"
                }

        ));

        JButton btnNewButton_1 = new JButton("Back");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Homepageadmin back = new Homepageadmin();
                back.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setBounds(10, 11, 98, 28);
        getContentPane().add(btnNewButton_1);

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon("F:\\TugasBinus\\semester 3\\OopPkm\\Img\\drink1.jpg"));
        lblNewLabel_2.setBounds(-27, 11, 844, 711);
        getContentPane().add(lblNewLabel_2);
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        try (BufferedReader reader = new BufferedReader(new FileReader("Database/Datadrink.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(";");
                model.addRow(rowData);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading data from the file.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void updatefood() {
        String type = txtfoodname.getText();
        String brand = txtbrand.getText();
        String expiredDate = textexpired.getText();
        int quantity = (int) spinnerqty.getValue();
        double price = Double.parseDouble(txtprice.getText());
        double total = calculateTotal(quantity, price);

        String foodData = type + ";" + brand + ";" + expiredDate + ";" + quantity + ";" + price + ";" + total;
        String filePath = "Database/Datadrink.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(foodData);
            writer.newLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error updating stock.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[] { type, brand, expiredDate, quantity, price, total });

        updateTotalsInTable();

        updateQuantityInDatabase(type, brand, expiredDate, quantity, price);
    }

    private double calculateTotal(double quantity, double price) {
        return quantity * price;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pageminumanadmin window = new Pageminumanadmin();
            window.setVisible(true);
        });
    }
}