package lk.propertymanagement.Dialog;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import lk.propertymanagement.Connection.MySQL;
import lk.propertymanagement.DAO.TenantID;
import lk.propertymanagement.Logger.LoggerFile;
import lk.propertymanagement.Panel.TenantList;
import lk.propertymanagement.Validation.Validation;

public class TenantRegistration extends javax.swing.JDialog {

    private static HashMap<String, String> tenantStatusMap = new HashMap<>();
    private static HashMap<String, String> tenantCityMap = new HashMap<>();

    public TenantRegistration(TenantList parent, boolean par) {
//        super(parent, modal);
        initComponents();
        loadStatus();
        loadCity();
    }

    public TenantRegistration(String id, String fName, String lName, String email, String mobile, String nic, String gender, String status, String address1, String address2, String city) {
        initComponents();
        loadStatus();
        loadCity();
        tenantIdField.setText(id);
        tenantFNameField.setText(fName);
        tenantLNameField.setText(lName);
        tenantEmailField.setText(email);
        tenantMobileField.setText(mobile);
        tenantNICField.setText(nic);
        if (gender.equals("Male")) {
            jMale.setSelected(true);
        }

        if (gender.equals("Female")) {
            jFemale.setSelected(true);
        }
        tenantStatusComboBox.setSelectedItem(status);
        tenantAddressField1.setText(address1);
        tenantAddressField2.setText(address2);
        cityComboBox.setSelectedItem(city);
    }

    private void loadStatus() {
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `status`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("status"));
                tenantStatusMap.put(resultSet.getString("status"), resultSet.getString("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            tenantStatusComboBox.setModel(model);

        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }

    private void loadCity() {
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `city`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("city_name"));
                tenantCityMap.put(resultSet.getString("city_name"), resultSet.getString("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            cityComboBox.setModel(model);

        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tenantMobileField = new javax.swing.JTextField();
        tenantNICField = new javax.swing.JTextField();
        tenantEmailField = new javax.swing.JTextField();
        tenantFNameField = new javax.swing.JTextField();
        tenantLNameField = new javax.swing.JTextField();
        tenantIdField = new javax.swing.JTextField();
        tenantStatusComboBox = new javax.swing.JComboBox<>();
        jFemale = new javax.swing.JRadioButton();
        jMale = new javax.swing.JRadioButton();
        updateTenantButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        addTenantButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        tenantAddressField1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tenantAddressField2 = new javax.swing.JTextField();
        cityComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 1, 24)); // NOI18N
        jLabel1.setText("Tenant Registration");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        jLabel2.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel2.setText("First Name");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 30));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 360, 10));

        jLabel3.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel3.setText("Email");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, 30));

        jLabel4.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel4.setText("Mobile");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, 30));

        jLabel5.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel5.setText("NIC");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, 30));

        jLabel6.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel6.setText("Gender");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, 30));

        jLabel7.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel7.setText("Status");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, 30));

        jLabel8.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel8.setText("City");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, -1, 30));

        jLabel9.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel9.setText("Last Name");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, 30));

        jLabel11.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel11.setText("Tenant Id");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 30));

        tenantMobileField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        getContentPane().add(tenantMobileField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 250, 30));

        tenantNICField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        getContentPane().add(tenantNICField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, 250, 30));

        tenantEmailField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        getContentPane().add(tenantEmailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 250, 30));

        tenantFNameField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        getContentPane().add(tenantFNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 250, 30));

        tenantLNameField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        getContentPane().add(tenantLNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 250, 30));

        tenantIdField.setEditable(false);
        tenantIdField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        tenantIdField.setEnabled(false);
        getContentPane().add(tenantIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 250, 30));

        tenantStatusComboBox.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        tenantStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(tenantStatusComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 350, 250, 30));

        buttonGroup1.add(jFemale);
        jFemale.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jFemale.setText("Female");
        getContentPane().add(jFemale, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 310, -1, 30));
        jFemale.setActionCommand("2");

        buttonGroup1.add(jMale);
        jMale.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jMale.setText("Male");
        getContentPane().add(jMale, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 310, -1, 30));
        jMale.setActionCommand("1");

        updateTenantButton.setFont(new java.awt.Font("Poppins Medium", 1, 14)); // NOI18N
        updateTenantButton.setText("Update Tenant");
        updateTenantButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTenantButtonActionPerformed(evt);
            }
        });
        getContentPane().add(updateTenantButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 340, 30));

        clearButton.setFont(new java.awt.Font("Poppins Medium", 1, 14)); // NOI18N
        clearButton.setText("Clear All");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        getContentPane().add(clearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 640, 340, 30));

        addTenantButton.setFont(new java.awt.Font("Poppins Medium", 1, 14)); // NOI18N
        addTenantButton.setText("Add Tenant");
        addTenantButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTenantButtonActionPerformed(evt);
            }
        });
        getContentPane().add(addTenantButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, 340, 30));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 43, 360, 10));

        jLabel10.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel10.setText("Address Line 01");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, -1, 30));

        tenantAddressField1.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        getContentPane().add(tenantAddressField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 420, 220, 30));

        jLabel12.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel12.setText("Address Line 02");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, -1, 30));

        tenantAddressField2.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        getContentPane().add(tenantAddressField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 460, 220, 30));

        cityComboBox.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        cityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(cityComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 500, 220, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addTenantButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTenantButtonActionPerformed
        try {
            String fName = tenantFNameField.getText();
            String lName = tenantLNameField.getText();
            String email = tenantEmailField.getText();
            String mobile = tenantMobileField.getText();
            String nic = tenantNICField.getText();
            ButtonModel gender = buttonGroup1.getSelection();
            String status = String.valueOf(tenantStatusComboBox.getSelectedItem());
            String line1 = tenantAddressField1.getText();
            String line2 = tenantAddressField2.getText();
            String city = String.valueOf(cityComboBox.getSelectedItem());

            if (fName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter First Name.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (lName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Last Name.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Email.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (!email.matches(Validation.VALIDATE_EMAIL.validate())) {
                JOptionPane.showMessageDialog(this, "Please enter valid Email.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (mobile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Mobile Number.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (!mobile.matches(Validation.VALIDATE_MOBILE.validate())) {
                JOptionPane.showMessageDialog(this, "Please enter valid Mobile Number.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (nic.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter NIC.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (!nic.matches(Validation.VALIDATE_NIC.validate())) {
                JOptionPane.showMessageDialog(this, "Please enter valid NIC.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (gender == null) {
                JOptionPane.showMessageDialog(this, "Please select a gender.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (status.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a Tenant Status.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                String Id = TenantID.generateTenantID();
                String genderId = gender.getActionCommand();

                MySQL.executeIUD("INSERT INTO `tenant` VALUES ('" + Id + "','" + fName + "','" + lName + "','" + nic + "','" + mobile + "',"
                        + "'" + email + "','" + genderId + "','" + line1 + "','" + line2 + "','" + tenantCityMap.get(city) + "','" + tenantStatusMap.get(status) + "')");

                clear();
                this.dispose();

            }
        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }//GEN-LAST:event_addTenantButtonActionPerformed

    private void updateTenantButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateTenantButtonActionPerformed
        try {
            String Id = tenantIdField.getText();
            String fName = tenantFNameField.getText();
            String lName = tenantLNameField.getText();
            String email = tenantEmailField.getText();
            String mobile = tenantMobileField.getText();
            String nic = tenantNICField.getText();
            ButtonModel gender = buttonGroup1.getSelection();
            String status = String.valueOf(tenantStatusComboBox.getSelectedItem());
            String line1 = tenantAddressField1.getText();
            String line2 = tenantAddressField2.getText();
            String city = String.valueOf(cityComboBox.getSelectedItem());

            if (fName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter First Name.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (lName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Last Name.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Email.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (!email.matches(Validation.VALIDATE_EMAIL.validate())) {
                JOptionPane.showMessageDialog(this, "Please enter valid Email.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (mobile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Mobile Number.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (!mobile.matches(Validation.VALIDATE_MOBILE.validate())) {
                JOptionPane.showMessageDialog(this, "Please enter valid Mobile Number.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (nic.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter NIC.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (!nic.matches(Validation.VALIDATE_NIC.validate())) {
                JOptionPane.showMessageDialog(this, "Please enter valid NIC.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (gender == null) {
                JOptionPane.showMessageDialog(this, "Please select a gender.", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (status.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a Tenant Status.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                ResultSet resultSet = MySQL.executeSearch("SELECT `id`, `email`, `mobile` FROM `tenant` "
                        + "WHERE (`email` = '" + email + "' OR `mobile` = '" + mobile + "') AND `id` != '" + Id + "'");

                boolean canUpdate = true;

                while (resultSet.next()) {
                    String tenantId = resultSet.getString("id");
                    String dbEmail = resultSet.getString("email");
                    String dbMobile = resultSet.getString("mobile");

                    if (dbEmail.equals(email)) {
                        JOptionPane.showMessageDialog(this, "This email is already used by another tenant (ID: " + tenantId + ").", "Warning", JOptionPane.WARNING_MESSAGE);
                        canUpdate = false;
                    }

                    if (dbMobile.equals(mobile)) {
                        JOptionPane.showMessageDialog(this, "This mobile number is already used by another tenant (ID: " + tenantId + ").", "Warning", JOptionPane.WARNING_MESSAGE);
                        canUpdate = false;
                    }
                }

                if (canUpdate) {
                    int rowsUpdated = MySQL.executeIUD("UPDATE `tenant` SET "
                            + "`first_name` = '" + fName + "', "
                            + "`last_name` = '" + lName + "',"
                            + " `nic`='" + nic + "',"
                            + "`mobile` = '" + mobile + "', "
                            + "`email` = '" + email + "',"
                            + "`gender_id` = '" + gender.getActionCommand() + "',"
                            + "`line_01` = '" + line1 + "',"
                            + "`line_02` = '" + line2 + "',"
                            + "`city_id` = '" + tenantCityMap.get(city) + "',"
                            + "`status_id` = '" + tenantStatusMap.get(status) + "' "
                            + "WHERE `id` = '" + Id + "'");

                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(this, "Customer details have been successfully updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Update failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Update not allowed due to conflicts with other customers.", "Warning", JOptionPane.WARNING_MESSAGE);
                }

                clear();
                this.dispose();

            }
        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }//GEN-LAST:event_updateTenantButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTenantButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cityComboBox;
    private javax.swing.JButton clearButton;
    private javax.swing.JRadioButton jFemale;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jMale;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField tenantAddressField1;
    private javax.swing.JTextField tenantAddressField2;
    private javax.swing.JTextField tenantEmailField;
    private javax.swing.JTextField tenantFNameField;
    private javax.swing.JTextField tenantIdField;
    private javax.swing.JTextField tenantLNameField;
    private javax.swing.JTextField tenantMobileField;
    private javax.swing.JTextField tenantNICField;
    private javax.swing.JComboBox<String> tenantStatusComboBox;
    private javax.swing.JButton updateTenantButton;
    // End of variables declaration//GEN-END:variables

    private void clear() {
        tenantIdField.setText("");
        tenantFNameField.setText("");
        tenantLNameField.setText("");
        tenantEmailField.setText("");
        tenantMobileField.setText("");
        tenantNICField.setText("");
        buttonGroup1.clearSelection();
        tenantStatusComboBox.setSelectedItem("Select");
        tenantAddressField1.setText("");
        tenantAddressField2.setText("");
        cityComboBox.setSelectedItem("Select");
    }
}
