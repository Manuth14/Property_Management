package lk.propertymanagement.Dialog;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import lk.propertymanagement.Connection.MySQL;
import lk.propertymanagement.Logger.LoggerFile;
import lk.propertymanagement.Panel.Invoice;
import lk.propertymanagement.Panel.Sales;

public class Tenant extends javax.swing.JDialog {

    private Sales sale;
    private Invoice invoice;

    public void setSales(Sales sale) {
        this.sale = sale;
    }
    
     public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Tenant() {
//        super(parent, modal);
        initComponents();
        init();
        loadTenant("first_name", "ASC", "");
    }

    private void init() {
        FlatSVGIcon icon = new FlatSVGIcon("lk//propertymanagement//Icons//refresh.svg", 20, 20);
        refreshButton.setIcon(icon);
    }

    private void loadTenant(String column, String orderby, String mobile) {
        try {

            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `tenant`"
                    + "INNER JOIN `status` ON `tenant`.`status_id` = `status`.`id`"
                    + "INNER JOIN `gender` ON `tenant`.`gender_id` = `gender`.`id`"
                    + "INNER JOIN `city` ON `tenant`.`city_id`=`city`.`id` "
                    + "WHERE `mobile` LIKE '" + mobile + "%' ORDER BY `" + column + "` " + orderby + "");

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
                vector.add(resultSet.getString("gender.gender_type"));
                vector.add(resultSet.getString("status.status"));
                vector.add(resultSet.getString("line_01"));
                vector.add(resultSet.getString("line_02"));
                vector.add(resultSet.getString("city.city_name"));

                model.addRow(vector);
            }
        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tenantSort = new javax.swing.JLabel();
        tenantSortComboBox = new javax.swing.JComboBox<>();
        tenantName = new javax.swing.JLabel();
        tenantMobileField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tenantListTable = new javax.swing.JTable();
        refreshButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 1, 24)); // NOI18N
        jLabel1.setText("Tenant");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tenant List", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Poppins Medium", 1, 14))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tenantSort.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        tenantSort.setText("SORT BY :");
        jPanel1.add(tenantSort, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, 30));

        tenantSortComboBox.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        tenantSortComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name ASC", "Name DESC", "Type ASC", "Type DESC" }));
        tenantSortComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tenantSortComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tenantSortComboBoxItemStateChanged(evt);
            }
        });
        jPanel1.add(tenantSortComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 150, 30));

        tenantName.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        tenantName.setText("Tenant Mobile :");
        jPanel1.add(tenantName, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 40, -1, 30));

        tenantMobileField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        tenantMobileField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tenantMobileFieldKeyReleased(evt);
            }
        });
        jPanel1.add(tenantMobileField, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 40, 150, 30));

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
        tenantListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tenantListTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tenantListTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 100, 1010, 490));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1030, 600));

        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        getContentPane().add(refreshButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 10, 35, 35));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tenantSortComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tenantSortComboBoxItemStateChanged
        search();
    }//GEN-LAST:event_tenantSortComboBoxItemStateChanged

    private void tenantMobileFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tenantMobileFieldKeyReleased
        search();
    }//GEN-LAST:event_tenantMobileFieldKeyReleased

    private void tenantListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tenantListTableMouseClicked
        int row = tenantListTable.getSelectedRow();
        if (evt.getClickCount() == 2) {

            if (sale != null) {
                sale.gettenantIdField().setText(String.valueOf(tenantListTable.getValueAt(row, 0)));
                this.dispose();
            }
            
            if (invoice != null) {
                invoice.gettenantIdField().setText(String.valueOf(tenantListTable.getValueAt(row, 0)));
                this.dispose();
            }
        }

    }//GEN-LAST:event_tenantListTableMouseClicked

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        loadTenant("first_name", "ASC", "");
    }//GEN-LAST:event_refreshButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTable tenantListTable;
    private javax.swing.JTextField tenantMobileField;
    private javax.swing.JLabel tenantName;
    private javax.swing.JLabel tenantSort;
    private javax.swing.JComboBox<String> tenantSortComboBox;
    // End of variables declaration//GEN-END:variables

    private void search() {
        String mobile = tenantMobileField.getText();

        if (tenantSortComboBox.getSelectedIndex() == 0) {
            loadTenant("first_name", "ASC", mobile);
        } else if (tenantSortComboBox.getSelectedIndex() == 1) {
            loadTenant("first_name", "DESC", mobile);
        } else if (tenantSortComboBox.getSelectedIndex() == 2) {
            loadTenant("status_id", "ASC", mobile);
        } else if (tenantSortComboBox.getSelectedIndex() == 3) {
            loadTenant("status_id", "DESC", mobile);
        }

    }
}
