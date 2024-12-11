package lk.propertymanagement.Panel;

import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import lk.propertymanagement.Connection.MySQL;

public class SalesHistory extends javax.swing.JPanel {

    public SalesHistory() {
        initComponents();
        loadSalesHistory();
    }
    
     private void loadSalesHistory() {
        try {

            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `rental_sales`"
                    + "INNER JOIN `tenant` ON `rental_sales`.`tenant_id` = `tenant`.`id`"
                    + "INNER JOIN `employee` ON `rental_sales`.`employee_id` = `employee`.`id`"
                    + "INNER JOIN `rental_properties` ON `rental_sales`.`rental_properties_id`=`rental_properties`.`id`"
                    + "INNER JOIN `payment_method` ON `rental_sales`.`payment_method_id`=`payment_method`.`id`");

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
                vector.add(resultSet.getString("payment_method_id"));

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

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 1, 24)); // NOI18N
        jLabel1.setText("Sales History");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        jLabel4.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel4.setText("Rental Date");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, -1, 30));

        jTextField1.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 200, 30));

        jLabel5.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel5.setText("Sales Id");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, 30));

        jDateChooser2.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 200, 30));

        salesHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sales Id", "Tenant Id", "Property Id", "Employee Id", "Start", "End", "Rental Amount", "Payment Method"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(salesHistoryTable);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 740, 470));

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Poppins Medium", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Print");
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 150, 30));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable salesHistoryTable;
    // End of variables declaration//GEN-END:variables
}
