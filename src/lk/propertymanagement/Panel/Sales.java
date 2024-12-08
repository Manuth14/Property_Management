package lk.propertymanagement.Panel;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import lk.propertymanagement.Connection.MySQL;

public class Sales extends javax.swing.JPanel {

    private static HashMap<String, String> paymentMap = new HashMap<>();

    public Sales() {
        initComponents();
        loadStatus();
    }

    private void loadStatus() {
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `payment_method`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("method"));
                paymentMap.put(resultSet.getString("method"), resultSet.getString("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            payMethodComboBox.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        employeeIdField = new javax.swing.JTextField();
        tenantIdField = new javax.swing.JTextField();
        propertyIdField = new javax.swing.JTextField();
        salesIdField = new javax.swing.JTextField();
        rentalAmountFormattedTextField = new javax.swing.JFormattedTextField();
        keyMoneyFormattedTextField = new javax.swing.JFormattedTextField();
        endDateChooser = new com.toedter.calendar.JDateChooser();
        payMethodComboBox = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        startDateChooser = new com.toedter.calendar.JDateChooser();
        selectPropertyButton = new javax.swing.JButton();
        selectTenantButton = new javax.swing.JButton();
        ClearButton = new javax.swing.JButton();
        AddSalesButton = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 1, 24)); // NOI18N
        jLabel1.setText("Sales");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        jLabel2.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel2.setText("Employee Id");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, 30));

        jLabel3.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel3.setText("Tenant Id");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, 30));

        jLabel4.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel4.setText("Property Id");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, 30));

        jLabel5.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel5.setText("Key Money");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, 30));

        jLabel6.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel6.setText("Rental Amount");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, 30));

        jLabel7.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel7.setText("TO");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 370, -1, 30));

        jLabel8.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel8.setText("Payment Method");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, -1, 30));

        jLabel9.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel9.setText("Sales Id");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 30));

        employeeIdField.setEditable(false);
        employeeIdField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        employeeIdField.setEnabled(false);
        add(employeeIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 200, 30));

        tenantIdField.setEditable(false);
        tenantIdField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        tenantIdField.setEnabled(false);
        add(tenantIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 200, 30));

        propertyIdField.setEditable(false);
        propertyIdField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        propertyIdField.setEnabled(false);
        add(propertyIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 200, 30));

        salesIdField.setEditable(false);
        salesIdField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        salesIdField.setEnabled(false);
        add(salesIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 200, 30));

        rentalAmountFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        rentalAmountFormattedTextField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        add(rentalAmountFormattedTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 200, 30));

        keyMoneyFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        keyMoneyFormattedTextField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        add(keyMoneyFormattedTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 200, 30));

        endDateChooser.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        add(endDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 370, 200, 30));

        payMethodComboBox.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        payMethodComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(payMethodComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 200, 30));

        jLabel10.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel10.setText("Rental Period");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, 30));

        startDateChooser.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        add(startDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 200, 30));

        selectPropertyButton.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        selectPropertyButton.setText("Select");
        add(selectPropertyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 220, 150, 30));

        selectTenantButton.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        selectTenantButton.setText("Select");
        add(selectTenantButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 150, 30));

        ClearButton.setFont(new java.awt.Font("Poppins Medium", 1, 14)); // NOI18N
        ClearButton.setText("Clear All");
        add(ClearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 490, 260, 30));

        AddSalesButton.setFont(new java.awt.Font("Poppins Medium", 1, 14)); // NOI18N
        AddSalesButton.setText("Add Sale");
        add(AddSalesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 260, 30));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddSalesButton;
    private javax.swing.JButton ClearButton;
    private javax.swing.JTextField employeeIdField;
    private com.toedter.calendar.JDateChooser endDateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JFormattedTextField keyMoneyFormattedTextField;
    private javax.swing.JComboBox<String> payMethodComboBox;
    private javax.swing.JTextField propertyIdField;
    private javax.swing.JFormattedTextField rentalAmountFormattedTextField;
    private javax.swing.JTextField salesIdField;
    private javax.swing.JButton selectPropertyButton;
    private javax.swing.JButton selectTenantButton;
    private com.toedter.calendar.JDateChooser startDateChooser;
    private javax.swing.JTextField tenantIdField;
    // End of variables declaration//GEN-END:variables
}
