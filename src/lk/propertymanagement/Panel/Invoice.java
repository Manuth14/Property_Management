package lk.propertymanagement.Panel;

import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import lk.propertymanagement.Connection.MySQL;
import lk.propertymanagement.DAO.MRP;
import lk.propertymanagement.Dialog.Property;
import lk.propertymanagement.Dialog.Tenant;
import lk.propertymanagement.GUI.DashBoard;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class Invoice extends javax.swing.JPanel {

    private DashBoard dashBoard;

    public void setDashboard(DashBoard dashBoard) {
        this.dashBoard = dashBoard;
    }

    private static HashMap<String, String> paymentMap = new HashMap<>();

    public Invoice() {
        initComponents();
        loadPaymentMethod();
    }

    public JTextField gettenantIdField() {
        return jTextField2;
    }

    public JTextField getpropertyIdField() {
        return jTextField3;
    }

    public JFormattedTextField getpropertyPaymentField() {
        return jFormattedTextField2;
    }

    private void loadPaymentMethod() {
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
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        payMethodComboBox = new javax.swing.JComboBox<>();
        AddSalesButton = new javax.swing.JButton();
        ClearButton = new javax.swing.JButton();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        MRPId = new javax.swing.JTextField();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 1, 24)); // NOI18N
        jLabel1.setText("Invoice");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel2.setText("Employee Id");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 30));

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jTextField1.setEnabled(false);
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 230, 30));

        jButton3.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jButton3.setText("Select");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, 170, 30));

        jLabel3.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel3.setText("Tenant Id");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, 30));

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jTextField2.setEnabled(false);
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 230, 30));

        jLabel4.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel4.setText("Property Id");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, 30));

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jTextField3.setEnabled(false);
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 230, 30));

        jButton4.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jButton4.setText("Select");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 160, 170, 30));

        jLabel5.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel5.setText("Payment");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, 30));

        jLabel6.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel6.setText("Paid Amount");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, 30));

        jLabel7.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel7.setText("Payment Method");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, 30));

        payMethodComboBox.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        payMethodComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(payMethodComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, 230, 30));

        AddSalesButton.setFont(new java.awt.Font("Poppins Medium", 1, 14)); // NOI18N
        AddSalesButton.setText("Add Sale");
        AddSalesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddSalesButtonActionPerformed(evt);
            }
        });
        jPanel1.add(AddSalesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 250, 40));

        ClearButton.setFont(new java.awt.Font("Poppins Medium", 1, 14)); // NOI18N
        ClearButton.setText("Clear All");
        ClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearButtonActionPerformed(evt);
            }
        });
        jPanel1.add(ClearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 380, 260, 40));

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jPanel1.add(jFormattedTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 230, 30));

        jFormattedTextField2.setEditable(false);
        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField2.setEnabled(false);
        jPanel1.add(jFormattedTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 230, 30));

        jLabel8.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel8.setText("ID");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        MRPId.setEditable(false);
        MRPId.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        MRPId.setEnabled(false);
        jPanel1.add(MRPId, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 230, 30));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 560, 470));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Tenant st = new Tenant();
        st.setVisible(true);
        st.setInvoice(this);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Property pt = new Property();
        pt.setVisible(true);
        pt.setInvoice(this);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void AddSalesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddSalesButtonActionPerformed
        try {
            String empId = jTextField1.getText();
            String tntId = jTextField2.getText();
            String propId = jTextField3.getText();
            String payment = jFormattedTextField2.getText();
            String paidAmount = jFormattedTextField1.getText();
            String paymentMethod = String.valueOf(payMethodComboBox.getSelectedItem());

            if (tntId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a Tenant", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (propId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a Property", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (payment.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the Paymet", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (paidAmount.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the Paid Amount", "warning", JOptionPane.WARNING_MESSAGE);
            } else if (paymentMethod.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a payment method", "warning", JOptionPane.WARNING_MESSAGE);
            } else {
                String Id = MRP.generateMrpID();
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                MySQL.executeIUD("INSERT INTO `monthly_rental_paymet` VALUES "
                        + "('" + Id + "','" + paidAmount + "','" + payment + "','" + sdf.format(date) + "',"                                 
                        + "'" + empId + "' ,'" + tntId + "','" + paymentMap.get(paymentMethod) + "','" + propId + "')");

                JOptionPane.showMessageDialog(this, "Monthly Payment added Successfully", "success", JOptionPane.INFORMATION_MESSAGE);
               
                InputStream s = this.getClass().getResourceAsStream("/lk/propertymanagement/Reports/Sales.jasper");

                HashMap<String, Object> params = new HashMap<>();
                params.put("Parameter1", Id);
                params.put("Parameter2", empId);
                params.put("Parameter3", sdf.format(date));
                params.put("Parameter4", tntId);
                params.put("Parameter7", propId);
                params.put("Parameter8", payment);
                params.put("Parameter9", paidAmount);
                params.put("Parameter10", paymentMap.get(paymentMethod));
                JasperPrint jasperPrint = JasperFillManager.fillReport(s, params);
                JasperViewer.viewReport(jasperPrint, false);
                
                reset();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_AddSalesButtonActionPerformed

    private void ClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearButtonActionPerformed
        reset();
    }//GEN-LAST:event_ClearButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddSalesButton;
    private javax.swing.JButton ClearButton;
    private javax.swing.JTextField MRPId;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JComboBox<String> payMethodComboBox;
    // End of variables declaration//GEN-END:variables

    private void reset() {
        jTextField2.setText("");
        jTextField3.setText("");
        jFormattedTextField2.setText("");
        jFormattedTextField1.setText("");
        payMethodComboBox.setSelectedItem("Select");
    }
}
