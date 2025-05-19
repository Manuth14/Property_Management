/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package lk.propertymanagement.GUI;

import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lk.propertymanagement.Connection.MySQL;
import lk.propertymanagement.Logger.LoggerFile;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author HP
 */
public class Employee_Salary_List extends javax.swing.JPanel {

    /**
     * Creates new form A
     */
    public Employee_Salary_List() {
        initComponents();

    }

    private void loadEmployeeSalary() {
        String id = jTextField1.getText();
        Date date = jDateChooser1.getDate();
        String sort = String.valueOf(jComboBox1.getSelectedItem());
        String detailQuery = "SELECT e_to.first_name AS to_first,e_to.last_name AS to_last,"
                + "e_by.first_name AS by_first,e_by.last_name AS by_last,"
                + "paid_to,salary.id,epf,emp_epf,etf,deduction,earning,net,DATE,method FROM salary "
                + "JOIN employee e_to ON salary.paid_to = e_to.id "
                + "JOIN employee e_by ON salary.paid_by = e_by.id "
                + "JOIN payment_method ON salary.payment_method_id = payment_method.id";
        String countQuery = "SELECT COUNT(`id`) AS salaryCount, COUNT(DISTINCT `paid_to`) AS employeeCount,"
                + "SUM(`epf`) AS totalEmployerPaidEPF,SUM(`etf`) AS totalEmployerPaidETF,SUM(`earning`) AS salaryEarningSum "
                + "FROM salary ";

        int queryState = 0;
        if (!id.isEmpty()) {
            String concatID = " WHERE salary.`paid_to` LIKE '" + id + "%'";
            countQuery += concatID;
            detailQuery += concatID;
            queryState = 1;
        }

        if (date != null) {
            String concatDate = "salary.date ='" + new SimpleDateFormat("yyyy-MM-dd").format(date) + "'";

            if (queryState == 1) {
                countQuery += " AND " + concatDate;
                detailQuery += " AND " + concatDate;
            } else {
                countQuery += " WHERE " + concatDate;
                detailQuery += " WHERE " + concatDate;
                queryState = 1;
            }

        }

        if (!sort.equals("All Details")) {
            int year = Year.now().getValue();
            int month = LocalDate.now().getMonthValue();
            String concatYear = "YEAR(date) ='" + year + "'";
            String concatMonth = concatYear + " AND MONTH(date) = " + month + "";
            if (!sort.equals("Current Month Details")) {
                if (queryState == 1) {
                    countQuery += " AND " + concatMonth;
                    detailQuery += " AND " + concatMonth;
                } else {
                    countQuery += " WHERE " + concatMonth;
                    detailQuery += " WHERE " + concatMonth;

                }
            } else if (!sort.equals("Current Year Details")) {
                if (queryState == 1) {
                    countQuery += " AND " + concatYear;
                    detailQuery += " AND " + concatYear;
                } else {
                    countQuery += " WHERE " + concatYear;
                    detailQuery += " WHERE " + concatYear;

                }
            }
        }

        try {
            ResultSet countResult = MySQL.executeSearch(countQuery);
            ResultSet detailsResult = MySQL.executeSearch(detailQuery);
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            while (detailsResult.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(detailsResult.getString("salary.id"));
                vector.add(detailsResult.getString("paid_to"));
                vector.add(detailsResult.getString("to_first") + " " + detailsResult.getString("to_last"));
                vector.add(detailsResult.getString("by_first") + " " + detailsResult.getString("by_last"));
                vector.add(detailsResult.getString("emp_epf"));
                vector.add(detailsResult.getString("epf"));
                vector.add(detailsResult.getString("etf"));
                vector.add(detailsResult.getString("earning"));
                vector.add(detailsResult.getString("deduction"));
                vector.add(detailsResult.getString("net"));
                vector.add(detailsResult.getString("date"));
                vector.add(detailsResult.getString("method"));

                dtm.addRow(vector);
            }
            jTable1.setModel(dtm);

            if (countResult.next()) {
                jTextField2.setText(countResult.getString("salaryCount"));
                jTextField3.setText(String.valueOf(countResult.getInt("employeeCount")));

                double epf = countResult.getDouble("totalEmployerPaidEPF");
                double etf = countResult.getDouble("totalEmployerPaidETF");
                double earning = countResult.getDouble("salaryEarningSum");

                jTextField4.setText("" + (epf + etf + earning));
                jTextField5.setText("" + (epf + etf));
            }
        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel1 = new lk.propertymanagement.swing.RoundPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        roundPanel2 = new lk.propertymanagement.swing.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel2.setText("Fillter Data By");

        jLabel3.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        jLabel3.setText("Employee ID");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        jLabel4.setText("Date");

        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel5.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        jLabel5.setText("Display");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Details", "Current Month Details", "Current Year Details" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(213, 55, 62));
        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(34, 34, 34)
                        .addComponent(jTextField1)))
                .addGap(20, 20, 20)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jSeparator3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel1.setText("Employee Salary List");

        roundPanel2.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Payment ID", "Employee ID", "Paid To", "Paid By", "Employee EPF", "Employer Paid EPF", "ETF", "Total Earning", "Total Deduction", "Net Pay", "Date", "Payment Method"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setShowGrid(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel6.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        jLabel6.setText("Employee Salary Payments Count");

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField3.setEditable(false);
        jTextField3.setBackground(new java.awt.Color(255, 255, 255));
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        jLabel7.setText("Employee  Count");

        jLabel8.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        jLabel8.setText("Employee Salary Payments Total");

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(255, 255, 255));
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel9.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        jLabel9.setText("Employer Payments Total");

        jTextField5.setEditable(false);
        jTextField5.setBackground(new java.awt.Color(255, 255, 255));
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton2.setBackground(new java.awt.Color(0, 153, 153));
        jButton2.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Print Salary Report");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 153, 153));
        jButton3.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Print Pay Sheet");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(104, 104, 104)
                                .addComponent(jTextField3))
                            .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 347, Short.MAX_VALUE)
                        .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 791, Short.MAX_VALUE))
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTextField1.setText("");
        jDateChooser1.setCalendar(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadEmployeeSalary();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        loadEmployeeSalary();
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        loadEmployeeSalary();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int row = jTable1.getSelectedRow();
        if (row > -1) {
            String paymentID = String.valueOf(jTable1.getValueAt(row, 0));
            String base = null;
            String bonus = null;
            String overtime_pay = null;
            String allowences = null;
            String transport = null;
            String tax = null;
            String post = null;

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
            String formattedDate = currentDate.format(formatter);
            try {
                ResultSet resultSet = MySQL.executeSearch("SELECT base,bonus,overtime_pay,allowences,transport,tax,employee_type "
                        + "FROM salary INNER JOIN employee ON salary.paid_to = employee.id "
                        + "INNER JOIN employee_type ON employee.employee_type_id=employee_type.id WHERE salary.id ='" + paymentID + "'");
                if (resultSet.next()) {
                    base = resultSet.getString("base");
                    bonus = resultSet.getString("bonus");
                    overtime_pay = resultSet.getString("overtime_pay");
                    allowences = resultSet.getString("allowences");
                    transport = resultSet.getString("transport");
                    tax = resultSet.getString("tax");
                    post = resultSet.getString("employee_type");
                }
                InputStream s = this.getClass().getResourceAsStream("/lk/propertymanagement/Reports/SkyLandSalarySheet.jasper");

                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("Parameter1", formattedDate);
                parameters.put("Parameter2", jTable1.getValueAt(row, 2));
                parameters.put("Parameter5", post);

                parameters.put("Parameter6", base);
                parameters.put("Parameter7", allowences);
                parameters.put("Parameter8", bonus);
                parameters.put("Parameter9", transport);
                parameters.put("Parameter10", overtime_pay);
                parameters.put("Parameter11", jTable1.getValueAt(row, 7));

                parameters.put("Parameter12", tax);
                parameters.put("Parameter13", jTable1.getValueAt(row, 4));
                parameters.put("Parameter14", jTable1.getValueAt(row, 8));
                parameters.put("Parameter15", jTable1.getValueAt(row, 6));
                parameters.put("Parameter16", jTable1.getValueAt(row, 5));
                parameters.put("Parameter17", jTable1.getValueAt(row, 9));

                parameters.put("Parameter18", jTable1.getValueAt(row, 11));
                parameters.put("Parameter19", jTable1.getValueAt(row, 3));

                JREmptyDataSource emptyDataSource = new JREmptyDataSource();

                JasperPrint jasperPrint = JasperFillManager.fillReport(s, parameters, emptyDataSource);

                JasperViewer.viewReport(jasperPrint, false);
                
                LoggerFile.setMessageLogger("Print salary sheet for employee "+ jTable1.getValueAt(row, 2));
            } catch (Exception e) {
                LoggerFile.setException(e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please Select the Row from Table", "WARNING",
                    JOptionPane.WARNING_MESSAGE);
            jTable1.grabFocus();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        InputStream s = this.getClass().getResourceAsStream("/lk/propertymanagement/Reports/SkyLandSalaryList.jasper");

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("Parameter1", jTextField2.getText());
        parameters.put("Parameter2", jTextField3.getText());
        parameters.put("Parameter3", jTextField4.getText());
        parameters.put("Parameter4", jTextField5.getText());
        JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());

        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager.fillReport(s, parameters, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
            
            LoggerFile.setMessageLogger("Print Salary List Report");
        } catch (JRException ex) {
           LoggerFile.setException(ex);
                 
        }


    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private lk.propertymanagement.swing.RoundPanel roundPanel1;
    private lk.propertymanagement.swing.RoundPanel roundPanel2;
    // End of variables declaration//GEN-END:variables
}
