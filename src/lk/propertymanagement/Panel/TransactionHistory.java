package lk.propertymanagement.Panel;

import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import lk.propertymanagement.Connection.MySQL;
import lk.propertymanagement.GUI.DashBoard;
import lk.propertymanagement.Logger.LoggerFile;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class TransactionHistory extends javax.swing.JPanel {

    private DashBoard dashBoard;

    public void setDashboard(DashBoard dashBoard) {
        this.dashBoard = dashBoard;
    }

    public TransactionHistory() {
        initComponents();
        loadTransactionHistory();
    }

    private void loadTransactionHistory() {
        try {
            String query = "SELECT * FROM `monthly_rental_paymet`"
                    + "INNER JOIN `tenant` ON `monthly_rental_paymet`.`tenant_id` = `tenant`.`id`"
                    + "INNER JOIN `employee` ON `monthly_rental_paymet`.`employee_id` = `employee`.`id`"
                    + "INNER JOIN `rental_properties` ON `monthly_rental_paymet`.`rental_properties_id`=`rental_properties`.`id`"
                    + "INNER JOIN `payment_method` ON `monthly_rental_paymet`.`payment_method_id`=`payment_method`.`id`";

            ArrayList<String> conditions = new ArrayList<>();

            if (!jTextField1.getText().isEmpty()) {
                String tId = jTextField1.getText();
                conditions.add("`monthly_rental_paymet`.`tenant_id` = '" + tId + "'");
            }

            if (!jTextField2.getText().isEmpty()) {
                String pId = jTextField2.getText();
                conditions.add("`monthly_rental_paymet`.`rental_properties_id` = '" + pId + "'");
            }

            if (!jTextField3.getText().isEmpty()) {
                String id = jTextField3.getText();
                conditions.add("`monthly_rental_paymet`.`id` = '" + id + "'");
            }

            if (jDateChooser2.getDate() != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(jDateChooser2.getDate());
                conditions.add("`monthly_rental_paymet`.`date` = '" + date + "'");
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
                vector.add(resultSet.getString("date"));
                vector.add(resultSet.getString("payment"));
                vector.add(resultSet.getString("payment_method.method"));

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
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        salesHistoryTable = new javax.swing.JTable();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 1, 24)); // NOI18N
        jLabel1.setText("Transaction History");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        jLabel5.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel5.setText("Tenant Id");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 30));

        jTextField1.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 200, 30));

        jLabel4.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel4.setText("Date");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, -1, 30));

        jDateChooser2.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jDateChooser2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooser2KeyReleased(evt);
            }
        });
        add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 200, 30));

        jLabel6.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel6.setText("Property ID");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, 30));

        jTextField2.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });
        add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 200, 30));

        jLabel7.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel7.setText("Sales ID");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, -1, 30));

        jTextField3.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });
        add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 200, 30));

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Poppins Medium", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 115, 230, 35));

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 65, 230, 35));

        salesHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tenant Id", "Property Id", "Employee Id", "Date", "Amount", "Payment Method"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(salesHistoryTable);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 910, 470));
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadTransactionHistory();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jDateChooser2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooser2KeyReleased
        loadTransactionHistory();
    }//GEN-LAST:event_jDateChooser2KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        loadTransactionHistory();
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        loadTransactionHistory();
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        loadTransactionHistory();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            InputStream s = this.getClass().getResourceAsStream("/lk/propertymanagement/Reports/TransactionHistory.jasper");

            HashMap<String, Object> params = new HashMap<>();
            JRTableModelDataSource dataSource = new JRTableModelDataSource(salesHistoryTable.getModel());
            JasperPrint jasperPrint = JasperFillManager.fillReport(s, params, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTable salesHistoryTable;
    // End of variables declaration//GEN-END:variables
}
