/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package lk.propertymanagement.GUI;

import lk.propertyManagement.Chart.LegendItem;
import lk.propertyManagement.Chart.ModelLegend;
import java.awt.Color;
import java.awt.Component;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Vector;
import javaswingdev.chart.ModelPieChart;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import lk.propertymanagement.Connection.MySQL;
import lk.propertymanagement.Logger.LoggerFile;

/**
 *
 * @author HP
 */
public class Home extends javax.swing.JPanel {

    /**
     * Creates new form Income_Finance
     */
    public Home() {
        initComponents();
        loadRecentSalesDetails();
        loadProperty();
        loadCardData();
        loadMaintenance();
    }

    private Color getColor() {
        Random random = new Random();

        // Generate random RGB values (0-255)
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // Create a new color with the random RGB values
        Color randomColor = new Color(red, green, blue);
        return randomColor;
    }

    private void addLegend(String name, Color color, Color color1, JPanel variable) {
        List<ModelLegend> legends = new ArrayList<>();
        ModelLegend data = new ModelLegend(name, color, color1);
        legends.add(data);
        variable.add(new LegendItem(data));
        variable.repaint();
        variable.revalidate();
    }

    private int getCurrentYear() {
        return Year.now().getValue(); // Get the current year as an integer
    }

    private int getCurrentMonth() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        return currentDate.getMonthValue(); // Get the month number
    }

    private void loadCardData() {
        int year = getCurrentYear();
        int month = getCurrentMonth();

        String propertyCount = "SELECT COUNT(rental_properties.`id`) AS PropertyCount FROM rental_properties";
        String rentalProperty = "SELECT COUNT(rental_properties_id) AS COUNT, SUM(rental_amount) AS amount FROM rental_sales "
                + "WHERE YEAR(paid_date) ='" + year + "' AND MONTH(paid_date) = " + month + "";

        String pending = rentalProperty + " " + "AND payment_status_id='2'";

        try {
            ResultSet propertyCountResult = MySQL.executeSearch(propertyCount);
            ResultSet rentalPropertyResult = MySQL.executeSearch(rentalProperty);
            ResultSet pendingResult = MySQL.executeSearch(pending);

            if (propertyCountResult.next()) {
                jLabel7.setText(propertyCountResult.getString("PropertyCount"));
            }

            if (rentalPropertyResult.next()) {
                jLabel8.setText(rentalPropertyResult.getString("COUNT"));
                jLabel11.setText("Rs." + rentalPropertyResult.getDouble("amount"));

            }

            if (pendingResult.next()) {
                jLabel14.setText(pendingResult.getString("COUNT") + " Pending Sales");
                jLabel20.setText("Rs." + pendingResult.getDouble("amount"));
            }

        } catch (Exception e) {
             LoggerFile.setException(e);
        }
    }

    private void loadMaintenance() {
        int year = getCurrentYear();
        int month = getCurrentMonth();

        String maintains = "SELECT COUNT(id) AS COUNT FROM maintenance_request "
                + "WHERE YEAR(date) ='" + year + "' AND MONTH(date) =" + month + "";

        String paid = "SELECT COUNT(id) AS COUNT,SUM(amount) AS amount FROM maintenance_payment"
                + " WHERE YEAR(date) ='" + year + "' AND MONTH(date) =" + month + "";

        String query = "SELECT COUNT(id) AS COUNT FROM maintenance WHERE "
                + "YEAR(date) ='" + year + "' AND MONTH(date) =" + month + "";

        String prograss = query + " " + "AND maintenance_status_id ='2'";
        String hold = query + " " + "AND maintenance_status_id ='3'";

        try {
            ResultSet maintainsResultSet = MySQL.executeSearch(maintains);
            ResultSet paidResultSet = MySQL.executeSearch(paid);
            ResultSet prograssResultSet = MySQL.executeSearch(prograss);
            ResultSet holdResultSet = MySQL.executeSearch(hold);
            
            if (maintainsResultSet.next()) {
                jLabel19.setText(maintainsResultSet.getString("COUNT") + " Requests");
            }
            
            if (paidResultSet.next()) {
                jLabel22.setText(maintainsResultSet.getString("COUNT") + " Tenants");
                jLabel21.setText("Rs."+ paidResultSet.getDouble("amount"));
            }
            
            if (prograssResultSet.next()) {
                jLabel34.setText(prograssResultSet.getString("COUNT") + " Propeties");
                
            }
            
            if (holdResultSet.next()) {
                jLabel38.setText(holdResultSet.getString("COUNT") + " Propeties");
            }
        } catch (Exception e) {
             LoggerFile.setException(e);
        }

    }

    private void loadProperty() {

        String query = "SELECT COUNT(rental_properties.`id`) AS PropertyCount,property_type.`type` FROM rental_properties "
                + "INNER JOIN property_type ON rental_properties.property_type_id = property_type.id "
                + "INNER JOIN property_status ON rental_properties.property_status_id =property_status.id WHERE property_status.`status` = 'Available' "
                + "GROUP BY property_type.`type`";
        try {
            pieChart1.clearData();
            ResultSet resultSet = MySQL.executeSearch(query);

            while (resultSet.next()) {
                Color color = getColor();
                String lable = resultSet.getString("type");
                addLegend(lable, color, getColor(), panelLegend);
                pieChart1.addData(new ModelPieChart(lable, resultSet.getDouble("PropertyCount"), color));

            }

        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }

    private void loadRecentSalesDetails() {
        int year = getCurrentYear();
        int month = getCurrentMonth();

        String query = "SELECT * FROM rental_sales INNER JOIN payment_status ON rental_sales.payment_status_id = payment_status.id"
                + " INNER JOIN tenant ON rental_sales.tenant_id = tenant.id "
                + " INNER JOIN employee ON rental_sales.employee_id = employee.id"
                + " WHERE YEAR(paid_date) ='" + year + "' AND MONTH(paid_date) = " + month + "";

        try {
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            ResultSet resultSet = MySQL.executeSearch(query);
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("rental_properties_id"));
                vector.add(resultSet.getString("from"));
                vector.add(resultSet.getString("to"));
                vector.add(resultSet.getString("tenant.first_name") + " " + resultSet.getString("tenant.last_name"));
                vector.add(resultSet.getString("employee.first_name") + " " + resultSet.getString("employee.last_name"));
                vector.add(resultSet.getString("rental_amount"));
                vector.add(resultSet.getString("paid_date"));
                vector.add(resultSet.getString("payment_status.status"));
                dtm.addRow(vector);
            }
            jTable1.setModel(dtm);
            changeTable(jTable1, 8);
        } catch (Exception e) {
             LoggerFile.setException(e);
        }
    }

    public void changeTable(JTable table, int column_index) {
        table.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = String.valueOf(table.getValueAt(row, column_index));
                
                if (status.equals("Recieved")) {
                    c.setBackground(Color.GREEN);
                } else {
                    c.setBackground(Color.RED);
                }
                return c;
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        roundPanel7 = new lk.propertymanagement.swing.RoundPanel();
        roundPanel4 = new lk.propertymanagement.swing.RoundPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        roundPanel6 = new lk.propertymanagement.swing.RoundPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        roundPanel5 = new lk.propertymanagement.swing.RoundPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        roundPanel3 = new lk.propertymanagement.swing.RoundPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        roundPanel8 = new lk.propertymanagement.swing.RoundPanel();
        roundPanel10 = new lk.propertymanagement.swing.RoundPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        roundPanel11 = new lk.propertymanagement.swing.RoundPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        roundPanel12 = new lk.propertymanagement.swing.RoundPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        roundPanel13 = new lk.propertymanagement.swing.RoundPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        roundPanel1 = new lk.propertymanagement.swing.RoundPanel();
        panelLegend = new lk.propertymanagement.swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        pieChart1 = new javaswingdev.chart.PieChart();
        roundPanel2 = new lk.propertymanagement.swing.RoundPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel3.setText("Dashbord");

        roundPanel7.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel7.setLayout(new java.awt.GridLayout(1, 4, 15, 15));

        roundPanel4.setBackground(new java.awt.Color(251, 251, 251));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-house-50.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Total Properties");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("01");

        javax.swing.GroupLayout roundPanel4Layout = new javax.swing.GroupLayout(roundPanel4);
        roundPanel4.setLayout(roundPanel4Layout);
        roundPanel4Layout.setHorizontalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        roundPanel4Layout.setVerticalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel4Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        roundPanel7.add(roundPanel4);

        roundPanel6.setBackground(new java.awt.Color(251, 251, 251));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("01");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Total Rented Properties");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-house-50.png"))); // NOI18N

        javax.swing.GroupLayout roundPanel6Layout = new javax.swing.GroupLayout(roundPanel6);
        roundPanel6.setLayout(roundPanel6Layout);
        roundPanel6Layout.setHorizontalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        roundPanel6Layout.setVerticalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel6Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        roundPanel7.add(roundPanel6);

        roundPanel5.setBackground(new java.awt.Color(251, 251, 251));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("01");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Total Revenue  from Sales");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        javax.swing.GroupLayout roundPanel5Layout = new javax.swing.GroupLayout(roundPanel5);
        roundPanel5.setLayout(roundPanel5Layout);
        roundPanel5Layout.setHorizontalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        roundPanel5Layout.setVerticalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel5Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        roundPanel7.add(roundPanel5);

        roundPanel3.setBackground(new java.awt.Color(251, 251, 251));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("01");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Pending Sales");

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Rs.");

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        roundPanel7.add(roundPanel3);

        roundPanel8.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        roundPanel8.setLayout(new java.awt.GridLayout(1, 4, 15, 15));

        roundPanel10.setBackground(new java.awt.Color(204, 255, 204));

        jLabel17.setText("Total Maintains");

        jLabel19.setForeground(new java.awt.Color(153, 153, 153));
        jLabel19.setText("20 Requests");

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/Maintaince1.png"))); // NOI18N

        javax.swing.GroupLayout roundPanel10Layout = new javax.swing.GroupLayout(roundPanel10);
        roundPanel10.setLayout(roundPanel10Layout);
        roundPanel10Layout.setHorizontalGroup(
            roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel10Layout.createSequentialGroup()
                .addGroup(roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel10Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19))
                    .addGroup(roundPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        roundPanel10Layout.setVerticalGroup(
            roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addGroup(roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel10Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel19))
                    .addGroup(roundPanel10Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        roundPanel8.add(roundPanel10);

        roundPanel11.setBackground(new java.awt.Color(204, 255, 204));

        jLabel21.setText("Rs. 1500");

        jLabel22.setForeground(new java.awt.Color(153, 153, 153));
        jLabel22.setText("10 Tenants");

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/Paid.png"))); // NOI18N

        jLabel24.setText("Paid");

        javax.swing.GroupLayout roundPanel11Layout = new javax.swing.GroupLayout(roundPanel11);
        roundPanel11.setLayout(roundPanel11Layout);
        roundPanel11Layout.setHorizontalGroup(
            roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel11Layout.createSequentialGroup()
                .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel11Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel24))
                    .addGroup(roundPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel23)
                        .addGap(21, 21, 21)
                        .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        roundPanel11Layout.setVerticalGroup(
            roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel11Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addGap(33, 33, 33))
        );

        roundPanel8.add(roundPanel11);

        roundPanel12.setBackground(new java.awt.Color(204, 255, 204));

        jLabel34.setForeground(new java.awt.Color(153, 153, 153));
        jLabel34.setText("6 Propeties");

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/pending.png"))); // NOI18N

        jLabel36.setText("Maintain In  Progress");

        javax.swing.GroupLayout roundPanel12Layout = new javax.swing.GroupLayout(roundPanel12);
        roundPanel12.setLayout(roundPanel12Layout);
        roundPanel12Layout.setHorizontalGroup(
            roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(23, 23, 23)
                        .addComponent(jLabel34))
                    .addComponent(jLabel36))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        roundPanel12Layout.setVerticalGroup(
            roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addGroup(roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel12Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel34))
                    .addGroup(roundPanel12Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel35)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        roundPanel8.add(roundPanel12);

        roundPanel13.setBackground(new java.awt.Color(204, 255, 204));

        jLabel38.setForeground(new java.awt.Color(153, 153, 153));
        jLabel38.setText("6 Propeties");

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/pending.png"))); // NOI18N

        jLabel40.setText("Maintain In  Hold");

        javax.swing.GroupLayout roundPanel13Layout = new javax.swing.GroupLayout(roundPanel13);
        roundPanel13.setLayout(roundPanel13Layout);
        roundPanel13Layout.setHorizontalGroup(
            roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addGap(23, 23, 23)
                        .addComponent(jLabel38))
                    .addComponent(jLabel40))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        roundPanel13Layout.setVerticalGroup(
            roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel13Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel38))
                    .addGroup(roundPanel13Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel39)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        roundPanel8.add(roundPanel13);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText(" Maintenance Overview");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(roundPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 774, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGap(752, 752, 752))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(roundPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(94, 94, 94)))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(roundPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        roundPanel1.setBackground(new java.awt.Color(204, 204, 204));

        panelLegend.setLayout(new java.awt.GridLayout(1, 3, 15, 15));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Available Proparty ");

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pieChart1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(panelLegend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pieChart1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelLegend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        roundPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Recent Sales");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sales ID", "Property ID", "Date From", "Date To", "Tenant", "Employee", "Amount", "Confirmation Date", "Payment Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1))
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 793, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private lk.propertymanagement.swing.RoundPanel panelLegend;
    private javaswingdev.chart.PieChart pieChart1;
    private lk.propertymanagement.swing.RoundPanel roundPanel1;
    private lk.propertymanagement.swing.RoundPanel roundPanel10;
    private lk.propertymanagement.swing.RoundPanel roundPanel11;
    private lk.propertymanagement.swing.RoundPanel roundPanel12;
    private lk.propertymanagement.swing.RoundPanel roundPanel13;
    private lk.propertymanagement.swing.RoundPanel roundPanel2;
    private lk.propertymanagement.swing.RoundPanel roundPanel3;
    private lk.propertymanagement.swing.RoundPanel roundPanel4;
    private lk.propertymanagement.swing.RoundPanel roundPanel5;
    private lk.propertymanagement.swing.RoundPanel roundPanel6;
    private lk.propertymanagement.swing.RoundPanel roundPanel7;
    private lk.propertymanagement.swing.RoundPanel roundPanel8;
    // End of variables declaration//GEN-END:variables
}
