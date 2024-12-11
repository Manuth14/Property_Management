package lk.propertymanagement.Panel;

import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import lk.propertymanagement.Connection.MySQL;
import lk.propertymanagement.Dialog.TenantRegistration;

public class TenantList extends javax.swing.JPanel {

    public TenantList() {
        initComponents();
        loadTenant();
    }
    
    private void loadTenant() {
        try {

            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `tenant`"
                    + "INNER JOIN `status` ON `tenant`.`status_id` = `status`.`id`"
                    + "INNER JOIN `gender` ON `tenant`.`gender_id` = `gender`.`id`"
                    + "INNER JOIN `city` ON `tenant`.`city_id`=`city`.`id`");

            DefaultTableModel model = (DefaultTableModel) tenantListTable.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("first_name"));
                vector.add(resultSet.getString("last_name"));
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("mobile"));
                vector.add(resultSet.getString("nic"));
                vector.add(resultSet.getString("gender.name"));
                vector.add(resultSet.getString("status.name"));
                vector.add(resultSet.getString("line_01"));
                vector.add(resultSet.getString("line_02"));
                vector.add(resultSet.getString("city.name"));

                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        addTenantButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        tenantSort = new javax.swing.JLabel();
        tenantSortComboBox = new javax.swing.JComboBox<>();
        tenantName = new javax.swing.JLabel();
        teanntNameField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tenantListTable = new javax.swing.JTable();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 1, 24)); // NOI18N
        jLabel1.setText("Tenant");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        addTenantButton.setFont(new java.awt.Font("Poppins Medium", 1, 14)); // NOI18N
        addTenantButton.setText("Add Tenant");
        addTenantButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTenantButtonActionPerformed(evt);
            }
        });
        add(addTenantButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, 210, 30));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tenant List", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Poppins Medium", 1, 14))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tenantSort.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        tenantSort.setText("SORT BY :");
        jPanel1.add(tenantSort, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, 30));

        tenantSortComboBox.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        tenantSortComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name ASC", "Name DESC", "Type ASC", "Type DESC" }));
        tenantSortComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(tenantSortComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 150, 30));

        tenantName.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        tenantName.setText("Tenant Name :");
        jPanel1.add(tenantName, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 40, -1, 30));

        teanntNameField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jPanel1.add(teanntNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 40, 150, 30));

        tenantListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "First Name", "Last Name", "Email", "Mobile", "NIC", "Gender", "Status", "Address Line1", "Address Line2", "City"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tenantListTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 100, 1010, 490));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1030, 600));
    }// </editor-fold>//GEN-END:initComponents

    private void addTenantButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTenantButtonActionPerformed
        TenantRegistration registration = new TenantRegistration(this, true);
        registration.setVisible(true);
    }//GEN-LAST:event_addTenantButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTenantButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField teanntNameField;
    private javax.swing.JTable tenantListTable;
    private javax.swing.JLabel tenantName;
    private javax.swing.JLabel tenantSort;
    private javax.swing.JComboBox<String> tenantSortComboBox;
    // End of variables declaration//GEN-END:variables
}
