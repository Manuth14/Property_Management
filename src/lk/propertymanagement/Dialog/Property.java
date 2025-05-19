package lk.propertymanagement.Dialog;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import lk.propertymanagement.Connection.MySQL;
import lk.propertymanagement.Logger.LoggerFile;
import lk.propertymanagement.Panel.Invoice;
import lk.propertymanagement.Panel.Sales;

public class Property extends javax.swing.JDialog {

    private static HashMap<String, String> propertyTypeMap = new HashMap<>();
    private static HashMap<String, String> propertyConditionMap = new HashMap<>();
    private static HashMap<String, String> propertyFurnishingMap = new HashMap<>();

    private Sales sale;
    private Invoice invoice;

    public void setSales(Sales sale) {
        this.sale = sale;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Property() {
        initComponents();
        init();
        loadProperty();
        loadPropertyType();
        loadPropertyCondition();
        loadPropertyFurnishing();
    }

    private void init() {
        FlatSVGIcon icon = new FlatSVGIcon("lk//propertymanagement//Icons//refresh.svg", 20, 20);
        refreshButton.setIcon(icon);
    }

    private void loadPropertyType() {
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `property_type`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("type"));
                propertyTypeMap.put(resultSet.getString("type"), resultSet.getString("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            TypeComboBox.setModel(model);

        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }

    private void loadPropertyCondition() {
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `property_condition`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("condition"));
                propertyConditionMap.put(resultSet.getString("condition"), resultSet.getString("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            conditionComboBox.setModel(model);

        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }

    private void loadPropertyFurnishing() {
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `furnishing_status`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("furnishing_status"));
                propertyFurnishingMap.put(resultSet.getString("furnishing_status"), resultSet.getString("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            furnishingComboBox.setModel(model);

        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }

    private void loadProperty() {
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `rental_properties`"
                    + "INNER JOIN `property_type` ON `rental_properties`.`property_type_id` = `property_type`.`id`"
                    + "INNER JOIN `property_condition` ON `rental_properties`.`property_condition_id` = `property_condition`.`id`"
                    + "INNER JOIN `furnishing_status` ON `rental_properties`.`furnishing_status_id`=`furnishing_status`.`id`"
                    + "INNER JOIN `property_status` ON `rental_properties`.`property_status_id`=`property_status`.`id`");

            DefaultTableModel model = (DefaultTableModel) propertyListTable.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("property_type.type"));
                vector.add(resultSet.getString("property_condition.condition"));
                vector.add(resultSet.getString("furnishing_status.furnishing_status"));
                vector.add(resultSet.getString("construction_date"));
                vector.add(resultSet.getString("property_status.status"));
                vector.add(resultSet.getString("monthly_rent"));
                vector.add(resultSet.getString("security_deposit"));

                model.addRow(vector);
            }
        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        propertyIdField = new javax.swing.JTextField();
        conditionComboBox = new javax.swing.JComboBox<>();
        propertyPriceToFormattedField = new javax.swing.JFormattedTextField();
        furnishingComboBox = new javax.swing.JComboBox<>();
        constructionDateField = new com.toedter.calendar.JDateChooser();
        TypeComboBox = new javax.swing.JComboBox<>();
        propertyPriceFormattedField = new javax.swing.JFormattedTextField();
        filterButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        propertyListTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 1, 24)); // NOI18N
        jLabel1.setText("Property List");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        jLabel2.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel2.setText("Property Type");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 30));

        jLabel3.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel3.setText("Property Condition");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, 30));

        jLabel4.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel4.setText("Property Id");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 30));

        jLabel5.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel5.setText("To");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 150, -1, 30));

        jLabel6.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel6.setText("Furnishing Status");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, -1, 30));

        jLabel7.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel7.setText("Property Price");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, 30));

        jLabel8.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel8.setText("Construction Year");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, -1, 30));

        propertyIdField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jPanel1.add(propertyIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 230, 30));

        conditionComboBox.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        conditionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(conditionComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 230, 30));

        propertyPriceToFormattedField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        propertyPriceToFormattedField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jPanel1.add(propertyPriceToFormattedField, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 230, 30));

        furnishingComboBox.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        furnishingComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(furnishingComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, 230, 30));

        constructionDateField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jPanel1.add(constructionDateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 230, 30));

        TypeComboBox.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        TypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(TypeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 230, 30));

        propertyPriceFormattedField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        propertyPriceFormattedField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jPanel1.add(propertyPriceFormattedField, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 230, 30));

        filterButton.setFont(new java.awt.Font("Poppins Medium", 1, 14)); // NOI18N
        filterButton.setText("Filter Result");
        jPanel1.add(filterButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 190, 370, 30));

        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        jPanel1.add(refreshButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, 40, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 240));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        propertyListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Type", "Condition", "Furnishing", "Year", "Status", "Price", "Key Money"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        propertyListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                propertyListTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(propertyListTable);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 820, 400));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 840, 420));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        loadProperty();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void propertyListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_propertyListTableMouseClicked
        int row = propertyListTable.getSelectedRow();
        if (evt.getClickCount() == 2) {

            if (sale != null) {
                sale.getpropertyIdField().setText(String.valueOf(propertyListTable.getValueAt(row, 0)));
                sale.getpropertyKeyMoneyField().setText(String.valueOf(propertyListTable.getValueAt(row, 7)));
                this.dispose();
            }
            
            if(invoice != null){
                invoice.getpropertyIdField().setText(String.valueOf(propertyListTable.getValueAt(row, 0)));
                invoice.getpropertyPaymentField().setText(String.valueOf(propertyListTable.getValueAt(row, 6)));
                this.dispose();
            }
        }
    }//GEN-LAST:event_propertyListTableMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> TypeComboBox;
    private javax.swing.JComboBox<String> conditionComboBox;
    private com.toedter.calendar.JDateChooser constructionDateField;
    private javax.swing.JButton filterButton;
    private javax.swing.JComboBox<String> furnishingComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField propertyIdField;
    private javax.swing.JTable propertyListTable;
    private javax.swing.JFormattedTextField propertyPriceFormattedField;
    private javax.swing.JFormattedTextField propertyPriceToFormattedField;
    private javax.swing.JButton refreshButton;
    // End of variables declaration//GEN-END:variables
}
