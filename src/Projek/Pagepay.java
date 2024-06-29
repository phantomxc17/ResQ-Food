package Projek;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.DecimalFormat;

public class Pagepay extends JFrame {
    private JTable table;
    private JLabel totalLabel;
    private DefaultTableModel tableModel;
    private ButtonGroup addressGroup;
    private ButtonGroup paymentMethodGroup;
    private JRadioButton rdbtnNewRadioButton;
    private JRadioButton rdbtnOffice;
    private JRadioButton rdbtnVirtualAccount;
    private JRadioButton rdbtnCod;

    public Pagepay() {
        getContentPane().setBackground(Color.CYAN);
        setTitle("ResQ Food");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(true);
        getContentPane().setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Ayo segera checkout sebelum kehabisan :)");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblNewLabel_1.setForeground(SystemColor.textText);
        lblNewLabel_1.setBounds(31, 101, 691, 72);
        getContentPane().add(lblNewLabel_1);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(31, 164, 723, 186);
        getContentPane().add(scrollPane);

        tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "Type", "Brand", "Expired Date", "Price", "Total", "Total Price" });

        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        JButton btnNewButton_1 = new JButton("Pay");
        btnNewButton_1.setBounds(486, 424, 137, 64);
        getContentPane().add(btnNewButton_1);

        JButton btnNewButton_1_1 = new JButton("Back");
        btnNewButton_1_1.setBounds(31, 26, 137, 30);
        getContentPane().add(btnNewButton_1_1);

        totalLabel = new JLabel("");
        totalLabel.setBounds(486, 375, 268, 30);
        getContentPane().add(totalLabel);
        totalLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

        JLabel lblNewLabel = new JLabel("Address :");
        lblNewLabel.setBounds(62, 361, 144, 26);
        getContentPane().add(lblNewLabel);

        addressGroup = new ButtonGroup();
        rdbtnNewRadioButton = new JRadioButton("Home");
        rdbtnNewRadioButton.setBounds(59, 394, 109, 23);
        getContentPane().add(rdbtnNewRadioButton);
        addressGroup.add(rdbtnNewRadioButton);

        rdbtnOffice = new JRadioButton("Office");
        rdbtnOffice.setBounds(59, 432, 109, 23);
        getContentPane().add(rdbtnOffice);
        addressGroup.add(rdbtnOffice);

        paymentMethodGroup = new ButtonGroup();
        rdbtnVirtualAccount = new JRadioButton("Virtual Account");
        rdbtnVirtualAccount.setBounds(267, 394, 109, 23);
        getContentPane().add(rdbtnVirtualAccount);
        paymentMethodGroup.add(rdbtnVirtualAccount);

        rdbtnCod = new JRadioButton("COD");
        rdbtnCod.setBounds(267, 432, 109, 23);
        getContentPane().add(rdbtnCod);
        paymentMethodGroup.add(rdbtnCod);

        JLabel lblPaymentMethod = new JLabel("Payment Method :");
        lblPaymentMethod.setBounds(267, 361, 144, 26);
        getContentPane().add(lblPaymentMethod);

        btnNewButton_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Pagemakanan balik = new Pagemakanan();
                balik.setVisible(true);
                dispose();
            }
        });

        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedAddress = getAddressSelection();
                String selectedPaymentMethod = getPaymentMethodSelection();
                String totalBill = totalLabel.getText();

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(2, 1));

                JButton homeButton = new JButton("Home");
                homeButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        First home = new First();
                        home.setVisible(true);
                        dispose();
                    }
                });

                JButton backButton = new JButton("Go Back to First Page");
                backButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Pagemakanan balik = new Pagemakanan();
                        balik.setVisible(true);
                        dispose();
                    }
                });

                panel.add(homeButton);
                panel.add(backButton);

                String message = "Thank you for purchasing at ResQ Food!\n\n"
                        + "Address: " + selectedAddress + "\n"
                        + "Payment Method: " + selectedPaymentMethod + "\n"
                        + "Total Bill: " + totalBill;

                JOptionPane.showOptionDialog(null, message, "Purchase Information", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, new Object[] { homeButton }, homeButton);
            }
        });

        showItem();
    }

    private String getAddressSelection() {
        if (rdbtnNewRadioButton.isSelected()) {
            return rdbtnNewRadioButton.getText();
        } else if (rdbtnOffice.isSelected()) {
            return rdbtnOffice.getText();
        } else {
            return "N/A";
        }
    }

    private String getPaymentMethodSelection() {
        if (rdbtnVirtualAccount.isSelected()) {
            return rdbtnVirtualAccount.getText();
        } else if (rdbtnCod.isSelected()) {
            return rdbtnCod.getText();
        } else {
            return "N/A";
        }
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
                            rowData[4],
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
        String totalStr = (String) tableModel.getValueAt(row, 3);
        int total = Integer.parseInt(totalStr);
        double price = Double.parseDouble((String) tableModel.getValueAt(row, 2));

        if (quantityToDelete >= total) {
            tableModel.removeRow(row);
        } else {
            int newTotal = total - quantityToDelete;
            tableModel.setValueAt(String.valueOf(newTotal), row, 3);

            double newTotalPrice = newTotal * price;
            tableModel.setValueAt(decimalFormat.format(newTotalPrice), row, 4);
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
            Pagepay window = new Pagepay();
            window.setVisible(true);
        });
    }
}
