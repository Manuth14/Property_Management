/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package lk.propertymanagement.GUI;

import com.raven.chart.LegendItem;
import com.raven.chart.ModelChart;
import com.raven.chart.ModelLegend;
import java.awt.Color;
import java.awt.Panel;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Vector;
import javaswingdev.chart.ModelPieChart;
import javax.swing.JPanel;
import lk.propertymanagement.Connection.MySQL;

/**
 *
 * @author HP
 */
public class Income_Finance extends javax.swing.JPanel {

    /**
     * Creates new form Income_Finance
     */
    public Income_Finance() {
        initComponents();
        loadIncome();
        loadExpenses();
        loadBarChart();
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
    double income = 0;

    private void loadIncome() {
        int year = getCurrentYear();
        int month = getCurrentMonth();
        String salesIncome = "SELECT SUM(rental_ammount) AS payment FROM rental_sales "
                + "WHERE YEAR(paid_date) ='" + year + "' AND MONTH(paid_date) = " + month + "";
        String rentalIncome = "SELECT SUM(payment) AS payment FROM monthly_rental_paymet "
                + "WHERE YEAR(date) ='" + year + "' AND MONTH(date) = " + month + "";

        try {
            pieChart1.clearData();
            ResultSet salesIncomeResult = MySQL.executeSearch(salesIncome);
            ResultSet rentalIncomeResult = MySQL.executeSearch(rentalIncome);
            if (salesIncomeResult.next()) {

                Color color = getColor();
                addLegend("Rental Sales Income", color, getColor(), panelLegend);
                if (salesIncomeResult.getString("payment") != null) {
                    double sales = salesIncomeResult.getDouble("payment");
                    income += sales;
                    pieChart1.addData(new ModelPieChart("Rental Sales Income", sales, color));
                } else {
                    pieChart1.addData(new ModelPieChart("Rental Sales Income", 0, color));
                }

            }

            if (rentalIncomeResult.next()) {

                Color color = getColor();
                addLegend("Monthly Rental Income", color, getColor(), panelLegend);
                if (rentalIncomeResult.getString("payment") != null) {
                    double rental = rentalIncomeResult.getDouble("payment");
                    income += rental;
                    pieChart1.addData(new ModelPieChart("Monthly Rental Income", rental, color));
                } else {
                    pieChart1.addData(new ModelPieChart("Monthly Rental Income", 0, color));
                }

            }

            jLabel7.setText("Rs." + income);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    double expenses = 0;

    private void loadExpenses() {
        int year = getCurrentYear();
        int month = getCurrentMonth();
        String expensesQuery = "SELECT expenses_type,SUM(amount) AS amount FROM expenses "
                + "INNER JOIN expenses_type ON expenses.expenses_type_id=expenses_type.id "
                + "WHERE YEAR(date) ='" + year + "' AND MONTH(DATE) = " + month + " GROUP BY expenses_type.expenses_type";
        try {
            pieChart2.clearData();
            ResultSet resultSet = MySQL.executeSearch(expensesQuery);

            while (resultSet.next()) {
                Color color = getColor();
                double amount = resultSet.getDouble("amount");
                String lable = resultSet.getString("expenses_type");
                addLegend(lable, color, getColor(), panelLegend1);
                pieChart2.addData(new ModelPieChart(lable, amount, color));
                expenses += amount;
            }
            jLabel9.setText("Rs." + this.expenses);
            double profit = this.income - this.expenses;
            jLabel11.setText("Rs." + profit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double income(String month) {
        int year = getCurrentYear();
        double income = 0;
        String salesIncome = "SELECT SUM(rental_ammount) AS payment FROM rental_sales "
                + "WHERE YEAR(paid_date) ='" + year + "' AND UPPER(DATE_FORMAT(paid_date, '%M')) = '" + month + "'";
        String rentalIncome = "SELECT SUM(payment) AS payment FROM monthly_rental_paymet "
                + "WHERE YEAR(date) ='" + year + "' AND UPPER(DATE_FORMAT(date, '%M')) = '" + month + "'";

        try {

            ResultSet salesIncomeResult = MySQL.executeSearch(salesIncome);
            ResultSet rentalIncomeResult = MySQL.executeSearch(rentalIncome);
            if (salesIncomeResult.next()) {

                if (salesIncomeResult.getString("payment") != null) {
                    double sales = salesIncomeResult.getDouble("payment");
                    income += sales;

                }

            }

            if (rentalIncomeResult.next()) {

                if (rentalIncomeResult.getString("payment") != null) {
                    double rental = rentalIncomeResult.getDouble("payment");
                    income += rental;

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return income;
    }

    private double expenses(String month) {
        int year = getCurrentYear();
        double expenses = 0;
        String expensesQuery = "SELECT SUM(amount) AS amount FROM expenses "
                + "WHERE YEAR(date) ='" + year + "' AND UPPER(DATE_FORMAT(date, '%M')) = '" + month + "'";
        try {

            ResultSet resultSet = MySQL.executeSearch(expensesQuery);

            while (resultSet.next()) {
                double amount = resultSet.getDouble("amount");
                expenses += amount;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenses;

    }

    private void loadBarChart() {
        Vector<String> months = new Vector<>();

        String[] month = {
            "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
            "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
        };
        months.addAll(Arrays.asList(month));

        chart.addLegend("Income", new Color(12, 84, 175), new Color(0, 108, 247));
        chart.addLegend("Expense", new Color(54, 4, 143), new Color(104, 49, 200));
        chart.addLegend("Profit", new Color(5, 125, 0), new Color(95, 209, 69));

        for (String monthName : month) {
            double monthlyIncome = income(monthName);
            double monthlyExpense = expenses(monthName);
            double monthlyProfit = monthlyIncome -monthlyExpense;
            chart.addData(new ModelChart("February", new double[]{monthlyIncome, monthlyExpense, monthlyProfit}));
        }
        
        chart.start();
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
        jPanel2 = new javax.swing.JPanel();
        roundPanel5 = new com.raven.swing.RoundPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        roundPanel3 = new com.raven.swing.RoundPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        roundPanel6 = new com.raven.swing.RoundPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        roundPanel1 = new com.raven.swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        pieChart1 = new javaswingdev.chart.PieChart();
        panelLegend = new javax.swing.JPanel();
        roundPanel2 = new com.raven.swing.RoundPanel();
        jLabel2 = new javax.swing.JLabel();
        chart = new com.raven.chart.Chart();
        roundPanel4 = new com.raven.swing.RoundPanel();
        jLabel5 = new javax.swing.JLabel();
        pieChart2 = new javaswingdev.chart.PieChart();
        panelLegend1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(3, 1, 10, 10));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel6.setText("Monthly Income");

        jLabel7.setText("Rs.0222");

        javax.swing.GroupLayout roundPanel5Layout = new javax.swing.GroupLayout(roundPanel5);
        roundPanel5.setLayout(roundPanel5Layout);
        roundPanel5Layout.setHorizontalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(39, 39, 39)
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        roundPanel5Layout.setVerticalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7))
                    .addComponent(jLabel4))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel2.add(roundPanel5);

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel8.setText("Monthly Expense");

        jLabel9.setText("Rs.0222");

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addGap(38, 38, 38)
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9))
                    .addComponent(jLabel17))
                .addGap(72, 72, 72))
        );

        jPanel2.add(roundPanel3);

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel10.setText("Monthly Profit");

        jLabel11.setText("Rs.0222");

        javax.swing.GroupLayout roundPanel6Layout = new javax.swing.GroupLayout(roundPanel6);
        roundPanel6.setLayout(roundPanel6Layout);
        roundPanel6Layout.setHorizontalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addGap(34, 34, 34)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addContainerGap(130, Short.MAX_VALUE))
        );
        roundPanel6Layout.setVerticalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11))
                    .addComponent(jLabel18))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel2.add(roundPanel6);

        jLabel3.setText("Finance");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );

        roundPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("Monthly  Income");

        panelLegend.setLayout(new java.awt.GridLayout(1, 2, 10, 10));

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelLegend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pieChart1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 222, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pieChart1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelLegend, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setText("Income");

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel4.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setText("Monthly  Expense");

        panelLegend1.setLayout(new java.awt.GridLayout(7, 1, 10, 10));

        jButton2.setText("View Expenses");

        javax.swing.GroupLayout roundPanel4Layout = new javax.swing.GroupLayout(roundPanel4);
        roundPanel4.setLayout(roundPanel4Layout);
        roundPanel4Layout.setHorizontalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addComponent(pieChart2, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelLegend1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel4Layout.setVerticalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pieChart2, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                    .addGroup(roundPanel4Layout.createSequentialGroup()
                        .addComponent(panelLegend1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(roundPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 6, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(roundPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.chart.Chart chart;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelLegend;
    private javax.swing.JPanel panelLegend1;
    private javaswingdev.chart.PieChart pieChart1;
    private javaswingdev.chart.PieChart pieChart2;
    private com.raven.swing.RoundPanel roundPanel1;
    private com.raven.swing.RoundPanel roundPanel2;
    private com.raven.swing.RoundPanel roundPanel3;
    private com.raven.swing.RoundPanel roundPanel4;
    private com.raven.swing.RoundPanel roundPanel5;
    private com.raven.swing.RoundPanel roundPanel6;
    // End of variables declaration//GEN-END:variables
}