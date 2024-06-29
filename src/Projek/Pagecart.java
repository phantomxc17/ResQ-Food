package Projek;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.DecimalFormat;

public class Pagecart extends JFrame {
    private JTable table;
    private JPanel panel;
    private JLabel totalLabel;
    private DefaultTableModel tableModel;

    public Pagecart() {
        getContentPane().setBackground(Color.CYAN);
        setTitle("ResQ Food");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(true);
        getContentPane().setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Ayo segera checkout sebelum kehabisan :)");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblNewLabel_1.setForeground(SystemColor.textText);
        lblNewLabel_1.setBounds(33, 45, 691, 72);
        getContentPane().add(lblNewLabel_1);

        panel = new JPanel();
        panel.setBounds(10, 128, 288, 319);
        getContentPane().add(panel);
        panel.setLayout(null);

        JButton btnNewButton_1 = new JButton("Pay");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pagepay pay = new Pagepay();
                pay.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setBounds(68, 130, 137, 64);
        panel.add(btnNewButton_1);

        totalLabel = new JLabel("");
        totalLabel.setBounds(68, 75, 268, 30);
        panel.add(totalLabel);
        totalLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

        JButton btnNewButton_1_1_1 = new JButton("Page Makanan");
        btnNewButton_1_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pagemakanan back1 = new Pagemakanan();
                back1.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1_1_1.setBounds(10, 256, 126, 38);
        panel.add(btnNewButton_1_1_1);

        JButton btnNewButton_1_1_1_1 = new JButton("Page Minuman");
        btnNewButton_1_1_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pageminuman back2 = new Pageminuman();
                back2.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1_1_1_1.setBounds(146, 256, 132, 38);
        panel.add(btnNewButton_1_1_1_1);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(308, 128, 466, 319);
        getContentPane().add(scrollPane);

        tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "Type", "Brand", "Expired Date", "Price", "Total", "Total Price" });

        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        JButton btnNewButton_1_2 = new JButton("Clear Cart");
        btnNewButton_1_2.setBounds(532, 458, 192, 46);
        getContentPane().add(btnNewButton_1_2);

        JButton btnNewButton_1_1 = new JButton("Back");
        btnNewButton_1_1.setBounds(20, 11, 90, 34);
        getContentPane().add(btnNewButton_1_1);
        btnNewButton_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pagemakanan balik = new Pagemakanan();
                balik.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearSelectedRow();
                updateTotalBill();
                updateDataChartFile();
            }
        });

        showItem();
    }

    private void showItem() {
        String filePath = "Database/Datacart.txt";
        DecimalFormat decimalFormat = new DecimalFormat("Rp #,##0.00");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(";");
                if (rowData.length >= 5) {
                    double total = Double.parseDouble(rowData[4]);
                    double price = Double.parseDouble(rowData[3]);
                    double totalPrice = total * price;

                    Object[] rearrangedData = {
                            rowData[0],
                            rowData[1],
                            rowData[2],
                            String.format("%.2f", price),
                            decimalFormat.format(totalPrice)
                    };
                    tableModel.addRow(rearrangedData);
                }
            }
            updateTotalBill();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to clear.");
            return;
        }

        String quantityStr = JOptionPane.showInputDialog("Enter quantity to delete:");
        if (quantityStr != null && !quantityStr.isEmpty()) {
            int quantityToDelete = Integer.parseInt(quantityStr);
            deleteQuantityFromRow(selectedRow, quantityToDelete);
            updateTotalBill();
            updateDataChartFile();
        }
    }

    private void deleteQuantityFromRow(int row, int quantityToDelete) {
        DecimalFormat decimalFormat = new DecimalFormat("Rp #,##0.00");
        String totalStr = (String) tableModel.getValueAt(row, 4);
        int total = Integer.parseInt(totalStr);
        double price = Double.parseDouble((String) tableModel.getValueAt(row, 3));

        if (quantityToDelete >= total) {
            tableModel.removeRow(row);
        } else {
            int newTotal = total - quantityToDelete;
            tableModel.setValueAt(String.valueOf(newTotal), row, 4);

            double newTotalPrice = newTotal * price;
            tableModel.setValueAt(decimalFormat.format(newTotalPrice), row, 5);
        }
    }

    private void updateDataChartFile() {
        String filePath = "Database/Datacart.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String type = (String) tableModel.getValueAt(i, 0);
                String brand = (String) tableModel.getValueAt(i, 1);
                String expiredDate = (String) tableModel.getValueAt(i, 2);
                String price = (String) tableModel.getValueAt(i, 3);
                String total = (String) tableModel.getValueAt(i, 4);
                String totalPrice = (String) tableModel.getValueAt(i, 5);

                String line = type + ";" + brand + ";" + expiredDate + ";" + price + ";" + total + ";" + totalPrice;
                writer.write(line);
                writer.newLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTotalBill() {
        double totalBill = 0.0;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String totalPriceStr = (String) tableModel.getValueAt(i, 5);
            double totalPrice = Double.parseDouble(totalPriceStr.replace("Rp ", "").replace(",", ""));
            totalBill += totalPrice;
        }

        totalLabel.setText("Total Bill: Rp " + String.format("%.2f", totalBill));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pagecart window = new Pagecart();
            window.setVisible(true);
        });
    }
}