package lk.propertymanagement.Panel;

import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import lk.propertymanagement.Connection.MySQL;
import lk.propertymanagement.GUI.DashBoard;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class SalesHistory extends javax.swing.JPanel {

    private DashBoard dashBoard;

    public void setDashboard(DashBoard dashBoard) {
        this.dashBoard = dashBoard;
    }

    public SalesHistory() {
        initComponents();
        loadSalesHistory();
    }

    private void loadSalesHistory() {
        try {
            String query = "SELECT * FROM `rental_sales`"
                    + " INNER JOIN `tenant` ON `rental_sales`.`tenant_id` = `tenant`.`id`"
                    + " INNER JOIN `employee` ON `rental_sales`.`employee_id` = `employee`.`id`"
                    + " INNER JOIN `rental_properties` ON `rental_sales`.`rental_properties_id`=`rental_properties`.`id`"
                    + " INNER JOIN `payment_method` ON `rental_sales`.`payment_method_id`=`payment_method`.`id`";

            ArrayList<String> conditions = new ArrayList<>();

            if (!jTextField1.getText().isEmpty()) {
                String Sid = jTextField1.getText();
                conditions.add("`rental_sales`.`id` = '" + Sid + "'");
            }

            if (jDateChooser2.getDate() != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String rentalDate = format.format(jDateChooser2.getDate());
                conditions.add("`rental_sales`.`paid_date` = '" + rentalDate + "'");
            }

            if (!conditions.isEmpty()) {
                query += " WHERE " + String.join(" AND ", conditions);
            }

            ResultSet resultSet = MySQL.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) salesHistoryTable.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("tenant_id"));
                vector.add(resultSet.getString("rental_properties_id"));
                vector.add(resultSet.getString("employee_id"));
                vector.add(resultSet.getString("from"));
                vector.add(resultSet.getString("to"));
                vector.add(resultSet.getString("rental_amount"));
                vector.add(resultSet.getString("payment_method.method"));
                vector.add(resultSet.getString("paid_date"));

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
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        salesHistoryTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 1, 24)); // NOI18N
        jLabel1.setText("Sales History");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        jLabel4.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel4.setText("Rental Date");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, 30));

        jTextField1.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 200, 30));

        jLabel5.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel5.setText("Sales Id");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, 30));

        jDateChooser2.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jDateChooser2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooser2KeyReleased(evt);
            }
        });
        add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 200, 30));

        salesHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sales Id", "Tenant Id", "Property Id", "Employee Id", "Start", "End", "Rental Amount", "Payment Method", "Paid Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(salesHistoryTable);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 960, 470));

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Poppins Medium", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 120, 150, 30));

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 60, 150, 35));
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadSalesHistory();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jDateChooser2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooser2KeyReleased
        loadSalesHistory();
    }//GEN-LAST:event_jDateChooser2KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        loadSalesHistory();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            InputStream s = this.getClass().getResourceAsStream("/lk/propertymanagement/Reports/SalesHistory.jasper");

            HashMap<String, Object> params = new HashMap<>();
            JRTableModelDataSource dataSource = new JRTableModelDataSource(salesHistoryTable.getModel());
            JasperPrint jasperPrint = JasperFillManager.fillReport(s, params, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable salesHistoryTable;
    // End of variables declaration//GEN-END:variables
}
