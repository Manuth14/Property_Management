/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package lk.propertymanagement.GUI;

import lk.propertyManagement.Chart.LegendItem;
import lk.propertyManagement.Chart.ModelChart;
import lk.propertyManagement.Chart.ModelLegend;
import java.awt.Color;
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
        String salesIncome = "SELECT SUM(rental_amount) AS payment FROM rental_sales WHERE YEAR(paid_date) ='" + year + "' AND MONTH(paid_date) = " + month + "";
        String rentalIncome = "SELECT SUM(payment) AS payment FROM monthly_rental_paymet WHERE YEAR(date) ='" + year + "' AND MONTH(DATE) = " + month + "";


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

    private void loadExpenses() {
        double expenses = 0;
        int year = getCurrentYear();
        int month = getCurrentMonth();
        String dateQuery = "WHERE YEAR(date) ='" + year + "' AND MONTH(DATE) = " + month + "";
        String expensesQuery = "SELECT expenses_type,SUM(amount) AS amount FROM expenses "
                + "INNER JOIN expenses_type ON expenses.expenses_type_id=expenses_type.id "
                + " " + dateQuery + " GROUP BY expenses_type.expenses_type";
        String salaryQuery = "SELECT SUM(`epf`) AS totalEmployerPaidEPF,SUM(`etf`) AS totalEmployerPaidETF,"
                + "SUM(`earning`) AS salaryEarningSum FROM salary " + dateQuery + "";

        try {
            pieChart2.clearData();
            ResultSet resultSet = MySQL.executeSearch(expensesQuery);
            ResultSet salaryResultSet = MySQL.executeSearch(salaryQuery);

            while (resultSet.next()) {
                Color color = getColor();
                double amount = resultSet.getDouble("amount");
                String lable = resultSet.getString("expenses_type");
                addLegend(lable, color, getColor(), panelLegend1);
                pieChart2.addData(new ModelPieChart(lable, amount, color));
                expenses += amount;
            }

            if (salaryResultSet.next()) {
                Color color = getColor();
                double epf = salaryResultSet.getDouble("totalEmployerPaidEPF");
                double etf = salaryResultSet.getDouble("totalEmployerPaidETF");
                double salary = salaryResultSet.getDouble("salaryEarningSum");
                double amount = epf + etf + salary;
                addLegend("Employee Salary", color, getColor(), panelLegend1);
                pieChart2.addData(new ModelPieChart("Employee Salary", amount, color));
                expenses += amount;
            }
            jLabel9.setText("Rs." + expenses);
            double profit = this.income - expenses;
            jLabel11.setText("Rs." + profit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double income(String month) {
        int year = getCurrentYear();
        double income = 0;
        String salesIncome = "SELECT SUM(rental_amount) AS payment FROM rental_sales "
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
        String dateQuery = "WHERE YEAR(date) ='" + year + "' AND UPPER(DATE_FORMAT(date, '%M')) = '" + month + "'";
        String expensesQuery = "SELECT SUM(amount) AS amount FROM expenses " + dateQuery + "";
        String salaryQuery = "SELECT SUM(`epf`) AS totalEmployerPaidEPF,SUM(`etf`) AS totalEmployerPaidETF,"
                + "SUM(`earning`) AS salaryEarningSum FROM salary " + dateQuery + "";
        try {

            ResultSet resultSet = MySQL.executeSearch(expensesQuery);
            ResultSet salaryResultSet = MySQL.executeSearch(salaryQuery);

            while (resultSet.next()) {
                double amount = resultSet.getDouble("amount");
                expenses += amount;
            }

            if (salaryResultSet.next()) {

                double epf = salaryResultSet.getDouble("totalEmployerPaidEPF");
                double etf = salaryResultSet.getDouble("totalEmployerPaidETF");
                double salary = salaryResultSet.getDouble("salaryEarningSum");
                double amount = epf + etf + salary;
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
            double monthlyProfit = monthlyIncome - monthlyExpense;
            chart.addData(new ModelChart(monthName, new double[]{monthlyIncome, monthlyExpense, monthlyProfit}));
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
        roundPanel5 = new lk.propertymanagement.swing.RoundPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        roundPanel3 = new lk.propertymanagement.swing.RoundPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        roundPanel6 = new lk.propertymanagement.swing.RoundPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        roundPanel1 = new lk.propertymanagement.swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        pieChart1 = new javaswingdev.chart.PieChart();
        panelLegend = new lk.propertymanagement.swing.RoundPanel();
        roundPanel2 = new lk.propertymanagement.swing.RoundPanel();
        jLabel2 = new javax.swing.JLabel();
        chart = new lk.propertyManagement.Chart.Chart();
        roundPanel4 = new lk.propertymanagement.swing.RoundPanel();
        jLabel5 = new javax.swing.JLabel();
        pieChart2 = new javaswingdev.chart.PieChart();
        panelLegend1 = new lk.propertymanagement.swing.RoundPanel();
        jLabel3 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(3, 1, 10, 10));

        roundPanel5.setBackground(new java.awt.Color(204, 255, 204));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Monthly Income");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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
                .addContainerGap(138, Short.MAX_VALUE))
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
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel2.add(roundPanel5);

        roundPanel3.setBackground(new java.awt.Color(255, 204, 204));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Monthly Expense");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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
                .addContainerGap(135, Short.MAX_VALUE))
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

        roundPanel6.setBackground(new java.awt.Color(255, 255, 204));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Monthly Profit");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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
                .addContainerGap(153, Short.MAX_VALUE))
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
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel2.add(roundPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 292, Short.MAX_VALUE)
                .addContainerGap())
        );

        roundPanel1.setBackground(new java.awt.Color(228, 226, 226));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Monthly  Income");

        panelLegend.setLayout(new java.awt.GridLayout(1, 2, 15, 15));

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pieChart1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(64, 216, Short.MAX_VALUE))
                    .addComponent(panelLegend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pieChart1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelLegend, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        roundPanel2.setBackground(new java.awt.Color(228, 226, 226));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Income");

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel4.setBackground(new java.awt.Color(228, 226, 226));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Monthly  Expense");

        panelLegend1.setLayout(new java.awt.GridLayout(7, 1, 10, 10));

        javax.swing.GroupLayout roundPanel4Layout = new javax.swing.GroupLayout(roundPanel4);
        roundPanel4.setLayout(roundPanel4Layout);
        roundPanel4Layout.setHorizontalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addComponent(pieChart2, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(panelLegend1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        roundPanel4Layout.setVerticalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelLegend1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pieChart2, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel3.setText("Finance");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(roundPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private lk.propertyManagement.Chart.Chart chart;
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
    private lk.propertymanagement.swing.RoundPanel panelLegend;
    private lk.propertymanagement.swing.RoundPanel panelLegend1;
    private javaswingdev.chart.PieChart pieChart1;
    private javaswingdev.chart.PieChart pieChart2;
    private lk.propertymanagement.swing.RoundPanel roundPanel1;
    private lk.propertymanagement.swing.RoundPanel roundPanel2;
    private lk.propertymanagement.swing.RoundPanel roundPanel3;
    private lk.propertymanagement.swing.RoundPanel roundPanel4;
    private lk.propertymanagement.swing.RoundPanel roundPanel5;
    private lk.propertymanagement.swing.RoundPanel roundPanel6;
    // End of variables declaration//GEN-END:variables
}
