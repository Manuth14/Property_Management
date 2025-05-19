/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package lk.propertymanagement.GUI;

import java.io.InputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import lk.propertymanagement.Connection.MySQL;
import lk.propertymanagement.Logger.LoggerFile;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author HP
 */
public class Reports extends javax.swing.JPanel {

    /**
     * Creates new form Sales_Report1
     */
    public Reports() {
        initComponents();

        loadYear();
        loadYear2();
        loadYear3();

        loadSellsCart(); // Sales & Monthly Rental     
        loadSellsTable();
        loadMonthlyIncomeTable();

        loadIncomeCartData();//Income
        loadProfitTable();
    }

    private void loadSellsCart() {

        String currentYear = String.valueOf(Year.now().getValue());

        String monthly_rentalQuery = "SELECT SUM(payment) AS payment FROM monthly_rental_paymet ";
        String outstandingPaymentQuery = "SELECT SUM(rental_amount) AS payment,COUNT(`rental_sales`.`id`) AS outstandingPaymentCount "
                + "FROM rental_sales INNER JOIN payment_status ON "
                + "rental_sales.payment_status_id = payment_status.id "
                + "WHERE payment_status.`status`='Not Recieved' ";
        String paymentReceivedQuery = "SELECT SUM(rental_amount) AS payment,COUNT(`rental_sales`.`id`) AS paymentReceivedCount "
                + "FROM rental_sales INNER JOIN payment_status ON "
                + "rental_sales.payment_status_id = payment_status.id "
                + "WHERE payment_status.`status`='Recieved' ";

        Date start = null;
        Date end = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        if (jDateChooser1.getDate() == null && jDateChooser2.getDate() == null) {
            start = jDateChooser1.getDate();

            monthly_rentalQuery += "WHERE YEAR(date) ='" + currentYear + "'";
            outstandingPaymentQuery += "AND YEAR(paid_date) ='" + currentYear + "'";
            paymentReceivedQuery += "AND YEAR(paid_date) ='" + currentYear + "'";
        }

        if (jDateChooser1.getDate() != null && jDateChooser2.getDate() == null) {
            start = jDateChooser1.getDate();

            monthly_rentalQuery += "WHERE date > '" + dateFormat.format(start) + "'";
            outstandingPaymentQuery += "AND paid_date > '" + dateFormat.format(start) + "'";
            paymentReceivedQuery += "AND paid_date > '" + dateFormat.format(start) + "'";
        }
        if (jDateChooser2.getDate() != null && jDateChooser1.getDate() == null) {
            end = jDateChooser2.getDate();

            monthly_rentalQuery += "WHERE date < '" + dateFormat.format(end) + "'";
            outstandingPaymentQuery += "AND paid_date < '" + dateFormat.format(end) + "'";
            paymentReceivedQuery += "AND paid_date < '" + dateFormat.format(end) + "'";
        }

        if (jDateChooser2.getDate() != null && jDateChooser1.getDate() != null) {
            start = jDateChooser1.getDate();
            end = jDateChooser2.getDate();

            monthly_rentalQuery += "WHERE date BETWEEN '" + dateFormat.format(start) + "' AND '" + dateFormat.format(end) + "'";
            outstandingPaymentQuery += "AND paid_date BETWEEN '" + dateFormat.format(start) + "' AND '" + dateFormat.format(end) + "'";
            paymentReceivedQuery += "AND paid_date BETWEEN '" + dateFormat.format(start) + "' AND '" + dateFormat.format(end) + "'";
        }

        try {

            ResultSet monthly_rental = MySQL.executeSearch(monthly_rentalQuery);
            ResultSet outstandingPayment = MySQL.executeSearch(outstandingPaymentQuery);
            ResultSet rentalPaymentReceived = MySQL.executeSearch(paymentReceivedQuery);
            double revenue = 0;
            double salesCount = 0;
            if (monthly_rental.next()) {
                double monthlyIncome = monthly_rental.getDouble("payment");
                jLabel24.setText("Rs." + monthlyIncome);
                revenue += monthlyIncome;
            }

            double salesIncome = 0;
            if (outstandingPayment.next()) {
                jLabel13.setText(outstandingPayment.getString("outstandingPaymentCount"));
                salesCount = outstandingPayment.getDouble("outstandingPaymentCount");
                salesIncome += outstandingPayment.getDouble("payment");
            }

            if (rentalPaymentReceived.next()) {
                jLabel10.setText(rentalPaymentReceived.getString("paymentReceivedCount"));
                salesCount += rentalPaymentReceived.getDouble("paymentReceivedCount");
                salesIncome += rentalPaymentReceived.getDouble("payment");
                jLabel21.setText("Rs." + salesIncome);
                revenue += salesIncome;
            }
            jLabel7.setText("Rs." + revenue);
            jLabel8.setText("" + salesCount);
        } catch (Exception e) {
            LoggerFile.setException(e);
        }

    }

    private void loadYear() {
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT DISTINCT YEAR(paid_date) AS year_extracted\n"
                    + "FROM rental_sales\n"
                    + "ORDER BY year_extracted DESC;");
            Vector<String> vector = new Vector<>();
            vector.add("Select Year");
            while (resultSet.next()) {
                vector.add(resultSet.getString("year_extracted"));
            }
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(dcbm);

        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }

    private void loadYear2() {
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT DISTINCT YEAR(paid_date) AS year_extracted\n"
                    + "FROM rental_sales\n"
                    + "ORDER BY year_extracted DESC;");
            Vector<String> vector = new Vector<>();
            vector.add("Select Year");
            while (resultSet.next()) {
                vector.add(resultSet.getString("year_extracted"));
            }
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel(vector);

            jComboBox2.setModel(dcbm);

        } catch (Exception e) {
             LoggerFile.setException(e);
        }
    }

    private void loadYear3() {
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT DISTINCT YEAR(paid_date) AS year_extracted\n"
                    + "FROM rental_sales\n"
                    + "ORDER BY year_extracted DESC;");
            Vector<String> vector = new Vector<>();
            vector.add("Select Year");
            while (resultSet.next()) {
                vector.add(resultSet.getString("year_extracted"));
            }
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel(vector);

            jComboBox3.setModel(dcbm);
        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }

    private Vector getMonthName() {
        Vector<String> months = new Vector<>();

        String[] monthName = {
            "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
            "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
        };
        months.addAll(Arrays.asList(monthName));
        return months;
    }

    private void loadSellsTable() {

        String currentYear = getCurrentYear();
        String yearChooser = String.valueOf(jComboBox1.getSelectedItem());

        if (!yearChooser.equals("Select Year")) {
            currentYear = yearChooser;
        }

        jLabel12.setText("Property Rental Sales Details For the Year " + currentYear + " ");
        Vector<String> propertyTypeID = new Vector<>();
        HashMap<String, String> propertyType = new HashMap<>();
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM property_type");
            while (resultSet.next()) {
                propertyTypeID.add(resultSet.getString("id"));
                propertyType.put(resultSet.getString("id"), resultSet.getString("type"));

            }
        } catch (Exception e) {
             LoggerFile.setException(e);
        }

        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);

        Vector<String> months = getMonthName();
        for (String month : months) {
            int monthAddedTime = 1;
            for (String id : propertyTypeID) {

                Vector<String> rentalIncomeData = new Vector<>(); // Rental Sales

                if (monthAddedTime == 1) {

                    rentalIncomeData.add(month);
                } else {

                    rentalIncomeData.add(" ");
                }

                monthAddedTime = 2;

                rentalIncomeData.add(propertyType.get(id));
                // Rental Sales Query
                String rental_sales = "SELECT SUM(rental_amount) AS payment FROM rental_sales "
                        + "INNER JOIN rental_properties ON rental_sales.rental_properties_id = rental_properties.id "
                        + "INNER JOIN property_type ON rental_properties.property_type_id= property_type.id "
                        + "WHERE YEAR(paid_date) ='" + currentYear + "' "
                        + "AND UPPER(DATE_FORMAT(paid_date, '%M')) = '" + month + "' AND property_type.id ='" + id + "'";

                try {
                    // Rental Sales
                    ResultSet rental_salesResultSet = MySQL.executeSearch(rental_sales);
                    if (rental_salesResultSet.next()) {

                        if (rental_salesResultSet.getString("payment") != null) {

                            rentalIncomeData.add(rental_salesResultSet.getString("payment"));
                        } else {
                            rentalIncomeData.add("0");
                        }

                    }

                } catch (Exception e) {
                    LoggerFile.setException(e);
                }

                dtm.addRow(rentalIncomeData);

            }

        }
        jTable1.setModel(dtm);

    }

    private void loadMonthlyIncomeTable() {

        String currentYear = getCurrentYear();
        String yearChooser = String.valueOf(jComboBox2.getSelectedItem());

        if (!yearChooser.equals("Select Year")) {
            currentYear = yearChooser;
        }

        jLabel15.setText("Monthly Rental Income Details For the Year " + currentYear + " ");
        Vector<String> months = getMonthName();

        Vector<String> propertyTypeID = new Vector<>();
        HashMap<String, String> propertyType = new HashMap<>();
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM property_type");
            while (resultSet.next()) {
                propertyTypeID.add(resultSet.getString("id"));
                propertyType.put(resultSet.getString("id"), resultSet.getString("type"));

            }
        } catch (Exception e) {
             LoggerFile.setException(e);
        }

        DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
        dtm.setRowCount(0);

        for (String month : months) {
            int monthAddedTime = 1;
            for (String id : propertyTypeID) {

                Vector<String> monthlyRentalIncomeData = new Vector<>(); //Monthly Rental Income

                if (monthAddedTime == 1) {
                    monthlyRentalIncomeData.add(month);

                } else {
                    monthlyRentalIncomeData.add(" ");

                }

                monthAddedTime = 2;

                monthlyRentalIncomeData.add(propertyType.get(id));

                //Monthly Rental Income Query
                String monthly_rental_paymet = "SELECT SUM(payment) AS payment FROM monthly_rental_paymet "
                        + "INNER JOIN rental_properties ON monthly_rental_paymet.rental_properties_id = rental_properties.id "
                        + "INNER JOIN property_type ON rental_properties.property_type_id= property_type.id "
                        + "WHERE YEAR(DATE) ='" + currentYear + "' "
                        + "AND UPPER(DATE_FORMAT(date, '%M')) = '" + month + "' AND property_type.id ='" + id + "'";

                try {

                    //Monthly Rental Income
                    ResultSet monthly_rental_paymetResultSet = MySQL.executeSearch(monthly_rental_paymet);
                    if (monthly_rental_paymetResultSet.next()) {

                        if (monthly_rental_paymetResultSet.getString("payment") != null) {

                            monthlyRentalIncomeData.add(monthly_rental_paymetResultSet.getString("payment"));
                        } else {
                            monthlyRentalIncomeData.add("0");
                        }

                    }

                } catch (Exception e) {
                    LoggerFile.setException(e);
                }

                dtm.addRow(monthlyRentalIncomeData);
            }

        }

        jTable2.setModel(dtm);
    }

    private String getCurrentYear() {
        return String.valueOf(Year.now().getValue()); // Get the current year 
    }

    private void loadIncomeCartData() {

        // Income Details
        String salesIncome = "SELECT SUM(rental_amount) AS payment FROM rental_sales ";
        String rentalIncome = "SELECT SUM(payment) AS payment FROM monthly_rental_paymet ";

        // Expenses Details
        String expensesQuery = "SELECT SUM(amount) AS amount FROM expenses ";
        String salaryQuery = "SELECT SUM(`epf`) AS totalEmployerPaidEPF,SUM(`etf`) AS totalEmployerPaidETF,"
                + "SUM(`earning`) AS salaryEarningSum FROM salary ";

        Date start = null;
        Date end = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (jDateChooser3.getDate() == null && jDateChooser4.getDate() == null) {
            String year = getCurrentYear();
            String dateQuery = "WHERE YEAR(date) ='" + year + "' ";

            salesIncome += "WHERE YEAR(paid_date) ='" + year + "'";
            rentalIncome += dateQuery;

            expensesQuery += dateQuery;
            salaryQuery += dateQuery;
        }
        int queryState = 0;
        if (jDateChooser3.getDate() != null) {
            start = jDateChooser3.getDate();

            String dateQuery = "WHERE date >'" + dateFormat.format(start) + "' ";

            salesIncome += "WHERE paid_date >'" + dateFormat.format(start) + "'";
            rentalIncome += dateQuery;

            expensesQuery += dateQuery;
            salaryQuery += dateQuery;
            queryState = 1;
        }

        if (jDateChooser4.getDate() != null) {
            end = jDateChooser4.getDate();
            String query = "";
            if (queryState == 0) {
                query = " WHERE ";
            } else {
                query = " AND ";
            }
            String dateQuery = query + "date <'" + dateFormat.format(end) + "' ";

            salesIncome += query + "paid_date <'" + dateFormat.format(end) + "'";
            rentalIncome += dateQuery;

            expensesQuery += dateQuery;
            salaryQuery += dateQuery;
        }

        double income = 0;
        double expenses = 0;

        try {
            ResultSet salesIncomeResultSet = MySQL.executeSearch(salesIncome); // Income
            ResultSet rentalIncomeResultSet = MySQL.executeSearch(rentalIncome);

            ResultSet expensesQueryResultSet = MySQL.executeSearch(expensesQuery); //Expenses
            ResultSet salaryQueryResultSet = MySQL.executeSearch(salaryQuery);

            if (salesIncomeResultSet.next()) {
                double sales = salesIncomeResultSet.getDouble("payment");
                income += sales;
                jLabel37.setText("Rs." + sales);
            }
            if (rentalIncomeResultSet.next()) {
                double rental = rentalIncomeResultSet.getDouble("payment");
                income += rental;
                jLabel46.setText("Rs." + rental);
            }

            if (salaryQueryResultSet.next()) {

                double epf = salaryQueryResultSet.getDouble("totalEmployerPaidEPF");
                double etf = salaryQueryResultSet.getDouble("totalEmployerPaidETF");
                double salary = salaryQueryResultSet.getDouble("salaryEarningSum");
                double amount = epf + etf + salary;

                expenses += amount;
                jLabel43.setText("Rs." + amount);
            }
            if (expensesQueryResultSet.next()) {
                double otherExpenses = expensesQueryResultSet.getDouble("amount");
                expenses += otherExpenses;
                jLabel40.setText("Rs." + otherExpenses);
            }
            double profit = income - expenses;
            jLabel28.setText("Rs." + income);
            jLabel31.setText("Rs." + expenses);
            jLabel34.setText("Rs." + profit);
        } catch (Exception e) {
            LoggerFile.setException(e);
        }
    }

    private double monthlyIncomeData(String month, String year) {

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
             LoggerFile.setException(e);
        }
        return income;
    }

    private double monthlyExpensesData(String month, String year) {
        double expenses = 0;
        String dateQuery = "WHERE YEAR(date) ='" + year + "' AND UPPER(DATE_FORMAT(date, '%M')) = '" + month + "'";
        String expensesQuery = "SELECT SUM(amount) AS amount FROM expenses " + dateQuery + "";
        String salaryQuery = "SELECT SUM(`epf`) AS totalEmployerPaidEPF,SUM(`etf`) AS totalEmployerPaidETF,"
                + "SUM(`earning`) AS salaryEarningSum FROM salary " + dateQuery + "";
        try {
            ResultSet resultSet = MySQL.executeSearch(expensesQuery);
            ResultSet salaryResultSet = MySQL.executeSearch(salaryQuery);

            if (resultSet.next()) {
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
            LoggerFile.setException(e);
        }
        return expenses;

    }

    private void loadProfitTable() {

        String currentYear = getCurrentYear();
        String yearChooser = String.valueOf(jComboBox3.getSelectedItem());

        if (!yearChooser.equals("Select Year")) {
            currentYear = yearChooser;
        }

        jLabel3.setText("Income, Outcome & Profit Details For the Year " + currentYear + " ");
        Vector<String> months = getMonthName();

        

        DefaultTableModel dtm = (DefaultTableModel) jTable4.getModel();
        dtm.setRowCount(0);

        for (String month : months) {

            Vector<String> vector = new Vector<>();
            double income = monthlyIncomeData(month, currentYear);
            double expense = monthlyExpensesData(month, currentYear);
            double profit = income - expense;

            vector.add(month);
            vector.add(""+income);
            vector.add(""+expense);
            vector.add(""+profit);
            dtm.addRow(vector);
        }

        jTable4.setModel(dtm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        roundPanel2 = new lk.propertymanagement.swing.RoundPanel();
        roundPanel1 = new lk.propertymanagement.swing.RoundPanel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        roundPanel3 = new lk.propertymanagement.swing.RoundPanel();
        roundPanel5 = new lk.propertymanagement.swing.RoundPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        roundPanel10 = new lk.propertymanagement.swing.RoundPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        roundPanel11 = new lk.propertymanagement.swing.RoundPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        roundPanel4 = new lk.propertymanagement.swing.RoundPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        roundPanel6 = new lk.propertymanagement.swing.RoundPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        roundPanel7 = new lk.propertymanagement.swing.RoundPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        roundPanel8 = new lk.propertymanagement.swing.RoundPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        roundPanel9 = new lk.propertymanagement.swing.RoundPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        roundPanel12 = new lk.propertymanagement.swing.RoundPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        roundPanel13 = new lk.propertymanagement.swing.RoundPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        roundPanel16 = new lk.propertymanagement.swing.RoundPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        roundPanel21 = new lk.propertymanagement.swing.RoundPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        roundPanel14 = new lk.propertymanagement.swing.RoundPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        roundPanel18 = new lk.propertymanagement.swing.RoundPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        roundPanel17 = new lk.propertymanagement.swing.RoundPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        roundPanel15 = new lk.propertymanagement.swing.RoundPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        roundPanel20 = new lk.propertymanagement.swing.RoundPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        roundPanel19 = new lk.propertymanagement.swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        roundPanel2.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Filter Cart Deatils By", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("To");

        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
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

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setText("Date");

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel3.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        roundPanel3.setLayout(new java.awt.GridLayout(1, 3, 12, 12));

        roundPanel5.setBackground(new java.awt.Color(204, 250, 214));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Total Revenue");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("200");

        javax.swing.GroupLayout roundPanel5Layout = new javax.swing.GroupLayout(roundPanel5);
        roundPanel5.setLayout(roundPanel5Layout);
        roundPanel5Layout.setHorizontalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        roundPanel5Layout.setVerticalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel3.add(roundPanel5);

        roundPanel10.setBackground(new java.awt.Color(204, 250, 214));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Total Rental Sales Income");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("200");

        javax.swing.GroupLayout roundPanel10Layout = new javax.swing.GroupLayout(roundPanel10);
        roundPanel10.setLayout(roundPanel10Layout);
        roundPanel10Layout.setHorizontalGroup(
            roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel10Layout.setVerticalGroup(
            roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel3.add(roundPanel10);

        roundPanel11.setBackground(new java.awt.Color(204, 250, 214));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setText("Total Monthly Rental Income");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setText("200");

        javax.swing.GroupLayout roundPanel11Layout = new javax.swing.GroupLayout(roundPanel11);
        roundPanel11.setLayout(roundPanel11Layout);
        roundPanel11Layout.setHorizontalGroup(
            roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel11Layout.setVerticalGroup(
            roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel3.add(roundPanel11);

        roundPanel4.setBackground(new java.awt.Color(204, 250, 214));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("200");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Total Sales");

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        javax.swing.GroupLayout roundPanel4Layout = new javax.swing.GroupLayout(roundPanel4);
        roundPanel4.setLayout(roundPanel4Layout);
        roundPanel4Layout.setHorizontalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel16))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        roundPanel4Layout.setVerticalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel3.add(roundPanel4);

        roundPanel6.setBackground(new java.awt.Color(204, 250, 214));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("200");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Total Payment Recieved");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        javax.swing.GroupLayout roundPanel6Layout = new javax.swing.GroupLayout(roundPanel6);
        roundPanel6.setLayout(roundPanel6Layout);
        roundPanel6Layout.setHorizontalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel17))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel6Layout.setVerticalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(22, 22, 22))
        );

        roundPanel3.add(roundPanel6);

        roundPanel7.setBackground(new java.awt.Color(204, 250, 214));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("200");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Total Outstanding Payment");

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        javax.swing.GroupLayout roundPanel7Layout = new javax.swing.GroupLayout(roundPanel7);
        roundPanel7.setLayout(roundPanel7Layout);
        roundPanel7Layout.setHorizontalGroup(
            roundPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel7Layout.setVerticalGroup(
            roundPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel3.add(roundPanel7);

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        roundPanel8.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Month", "Property Type", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Rental Sales");

        jButton4.setBackground(new java.awt.Color(42, 164, 94));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Print Report");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel8Layout = new javax.swing.GroupLayout(roundPanel8);
        roundPanel8.setLayout(roundPanel8Layout);
        roundPanel8Layout.setHorizontalGroup(
            roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        roundPanel8Layout.setVerticalGroup(
            roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap())
        );

        roundPanel9.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Month", "Property Type", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Monthly Rental Income");

        jButton5.setBackground(new java.awt.Color(42, 164, 94));
        jButton5.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Print Report");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel9Layout = new javax.swing.GroupLayout(roundPanel9);
        roundPanel9.setLayout(roundPanel9Layout);
        roundPanel9Layout.setHorizontalGroup(
            roundPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        roundPanel9Layout.setVerticalGroup(
            roundPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(roundPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Sales & Monthly Income", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        roundPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(7, 1, 10, 10));

        roundPanel13.setBackground(new java.awt.Color(204, 255, 204));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Total Income");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Rs.0222");

        javax.swing.GroupLayout roundPanel13Layout = new javax.swing.GroupLayout(roundPanel13);
        roundPanel13.setLayout(roundPanel13Layout);
        roundPanel13Layout.setHorizontalGroup(
            roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addGap(39, 39, 39)
                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel13Layout.setVerticalGroup(
            roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28))
                    .addComponent(jLabel26))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(roundPanel13);

        roundPanel16.setBackground(new java.awt.Color(204, 255, 204));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel36.setText("Total Sales Income");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel37.setText("Rs.0222");

        javax.swing.GroupLayout roundPanel16Layout = new javax.swing.GroupLayout(roundPanel16);
        roundPanel16.setLayout(roundPanel16Layout);
        roundPanel16Layout.setHorizontalGroup(
            roundPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addGap(39, 39, 39)
                .addGroup(roundPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        roundPanel16Layout.setVerticalGroup(
            roundPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel37))
                    .addComponent(jLabel35))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.add(roundPanel16);

        roundPanel21.setBackground(new java.awt.Color(204, 255, 204));

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel45.setText("Total Monthly Rental Income");

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel46.setText("Rs.0222");

        javax.swing.GroupLayout roundPanel21Layout = new javax.swing.GroupLayout(roundPanel21);
        roundPanel21.setLayout(roundPanel21Layout);
        roundPanel21Layout.setHorizontalGroup(
            roundPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addGap(39, 39, 39)
                .addGroup(roundPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addComponent(jLabel46))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        roundPanel21Layout.setVerticalGroup(
            roundPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel46))
                    .addComponent(jLabel44))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.add(roundPanel21);

        roundPanel14.setBackground(new java.awt.Color(255, 204, 255));

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("Total Expense");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("Rs.0222");

        javax.swing.GroupLayout roundPanel14Layout = new javax.swing.GroupLayout(roundPanel14);
        roundPanel14.setLayout(roundPanel14Layout);
        roundPanel14Layout.setHorizontalGroup(
            roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addGap(38, 38, 38)
                .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel14Layout.setVerticalGroup(
            roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel31))
                    .addComponent(jLabel29))
                .addGap(72, 72, 72))
        );

        jPanel4.add(roundPanel14);

        roundPanel18.setBackground(new java.awt.Color(255, 204, 255));

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel42.setText("Total Employee Salary Expenses");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel43.setText("Rs.0222");

        javax.swing.GroupLayout roundPanel18Layout = new javax.swing.GroupLayout(roundPanel18);
        roundPanel18.setLayout(roundPanel18Layout);
        roundPanel18Layout.setHorizontalGroup(
            roundPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addGap(38, 38, 38)
                .addGroup(roundPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addComponent(jLabel43))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel18Layout.setVerticalGroup(
            roundPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(roundPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel43))
                    .addComponent(jLabel41))
                .addGap(72, 72, 72))
        );

        jPanel4.add(roundPanel18);

        roundPanel17.setBackground(new java.awt.Color(255, 204, 255));

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel39.setText("Total Other Expenes");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel40.setText("Rs.0222");

        javax.swing.GroupLayout roundPanel17Layout = new javax.swing.GroupLayout(roundPanel17);
        roundPanel17.setLayout(roundPanel17Layout);
        roundPanel17Layout.setHorizontalGroup(
            roundPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addGap(39, 39, 39)
                .addGroup(roundPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel17Layout.setVerticalGroup(
            roundPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel40))
                    .addComponent(jLabel38))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(roundPanel17);

        roundPanel15.setBackground(new java.awt.Color(255, 255, 204));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setText("Total Profit");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setText("Rs.0222");

        javax.swing.GroupLayout roundPanel15Layout = new javax.swing.GroupLayout(roundPanel15);
        roundPanel15.setLayout(roundPanel15Layout);
        roundPanel15Layout.setHorizontalGroup(
            roundPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addGap(34, 34, 34)
                .addGroup(roundPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        roundPanel15Layout.setVerticalGroup(
            roundPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel34))
                    .addComponent(jLabel32))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(roundPanel15);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 555, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout roundPanel12Layout = new javax.swing.GroupLayout(roundPanel12);
        roundPanel12.setLayout(roundPanel12Layout);
        roundPanel12Layout.setHorizontalGroup(
            roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel12Layout.setVerticalGroup(
            roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        roundPanel20.setBackground(new java.awt.Color(251, 251, 251));
        roundPanel20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Month", "Total Income", "Total Expenses", "Total Profit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("jLabel3");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 153, 153));
        jButton3.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Print Report");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel20Layout = new javax.swing.GroupLayout(roundPanel20);
        roundPanel20.setLayout(roundPanel20Layout);
        roundPanel20Layout.setHorizontalGroup(
            roundPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel20Layout.createSequentialGroup()
                        .addComponent(jScrollPane4)
                        .addContainerGap())
                    .addGroup(roundPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        roundPanel20Layout.setVerticalGroup(
            roundPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        roundPanel19.setBackground(new java.awt.Color(251, 251, 251));
        roundPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Filter Details By", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Date");

        jDateChooser3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser3PropertyChange(evt);
            }
        });

        jDateChooser4.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser4PropertyChange(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("To");

        jButton2.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(213, 55, 62));
        jButton2.setText("Clear Date Field");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel19Layout = new javax.swing.GroupLayout(roundPanel19);
        roundPanel19.setLayout(roundPanel19Layout);
        roundPanel19Layout.setHorizontalGroup(
            roundPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(35, 35, 35)
                .addComponent(jDateChooser4, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        roundPanel19Layout.setVerticalGroup(
            roundPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel19Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(roundPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(roundPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(roundPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(roundPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Income,Outcome & Profit Details", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        loadSellsTable();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        loadMonthlyIncomeTable();
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jDateChooser1.setDate(null);
        jDateChooser2.setDate(null);
        loadSellsCart();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        loadSellsCart();
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        loadSellsCart();
    }//GEN-LAST:event_jDateChooser2PropertyChange

    private void jDateChooser3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser3PropertyChange
        loadIncomeCartData();
    }//GEN-LAST:event_jDateChooser3PropertyChange

    private void jDateChooser4PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser4PropertyChange
        loadIncomeCartData();
    }//GEN-LAST:event_jDateChooser4PropertyChange

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jDateChooser3.setDate(null);
        jDateChooser4.setDate(null);
        loadIncomeCartData();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //Rental Sales Report

        int rowCount = jTable1.getRowCount();
        double totalIncome = 0;
        for (int i = 0; i < rowCount; i++) {
            totalIncome += Double.parseDouble(String.valueOf(jTable1.getValueAt(i, 2)));
        }

        InputStream s = this.getClass().getResourceAsStream("/lk/propertymanagement/Reports/SkyLandRentalSalesReport.jasper");

        HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("Parameter1", jLabel12.getText()); //Title
        parameters.put("Parameter2", "" + totalIncome);

        JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());

        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager.fillReport(s, parameters, dataSource);
            JasperViewer.viewReport(jasperPrint, false); // Because of the false GUI will not close
            LoggerFile.setMessageLogger("Successfully print rental sales report");
        } catch (JRException ex) {
             LoggerFile.setException(ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //Monthly Rental Income Report

        int rowCount = jTable2.getRowCount();
        double totalIncome = 0;
        for (int i = 0; i < rowCount; i++) {
            totalIncome += Double.parseDouble(String.valueOf(jTable2.getValueAt(i, 2)));
        }

        InputStream s = this.getClass().getResourceAsStream("/lk/propertymanagement/Reports/SkyLandMonthlyRentalIncomeReport.jasper");

        HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("Parameter1", jLabel15.getText()); //Title
        parameters.put("Parameter2", "" + totalIncome);

        JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable2.getModel());

        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager.fillReport(s, parameters, dataSource);
            JasperViewer.viewReport(jasperPrint, false); // Because of the false GUI will not close
            LoggerFile.setMessageLogger("Successfully print the Monthly Income report");
        } catch (JRException ex) {
             LoggerFile.setException(ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Profit Report

        int rowCount = jTable4.getRowCount();
        double totalIncome = 0;
        double totalExpenses = 0;
        double profit = 0;
        for (int i = 0; i < rowCount; i++) {
            totalIncome += Double.parseDouble(String.valueOf(jTable4.getValueAt(i, 1)));
            totalExpenses += Double.parseDouble(String.valueOf(jTable4.getValueAt(i, 2)));
            profit += Double.parseDouble(String.valueOf(jTable4.getValueAt(i, 3)));
        }

        InputStream s = this.getClass().getResourceAsStream("/lk/propertymanagement/Reports/SkyLandProfitReport.jasper");

        HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("Parameter1", jLabel3.getText()); //Title
        parameters.put("Parameter2", "" + totalIncome);
        parameters.put("Parameter3", "" + totalExpenses);
        parameters.put("Parameter4", "" + profit);

        JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable4.getModel());

        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager.fillReport(s, parameters, dataSource);
            JasperViewer.viewReport(jasperPrint, false); // Because of the false GUI will not close
            LoggerFile.setMessageLogger("Successfull print the Income, expenses and profit report");
        } catch (JRException ex) {
            LoggerFile.setException(ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
       loadProfitTable();
    }//GEN-LAST:event_jComboBox3ItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable4;
    private lk.propertymanagement.swing.RoundPanel roundPanel1;
    private lk.propertymanagement.swing.RoundPanel roundPanel10;
    private lk.propertymanagement.swing.RoundPanel roundPanel11;
    private lk.propertymanagement.swing.RoundPanel roundPanel12;
    private lk.propertymanagement.swing.RoundPanel roundPanel13;
    private lk.propertymanagement.swing.RoundPanel roundPanel14;
    private lk.propertymanagement.swing.RoundPanel roundPanel15;
    private lk.propertymanagement.swing.RoundPanel roundPanel16;
    private lk.propertymanagement.swing.RoundPanel roundPanel17;
    private lk.propertymanagement.swing.RoundPanel roundPanel18;
    private lk.propertymanagement.swing.RoundPanel roundPanel19;
    private lk.propertymanagement.swing.RoundPanel roundPanel2;
    private lk.propertymanagement.swing.RoundPanel roundPanel20;
    private lk.propertymanagement.swing.RoundPanel roundPanel21;
    private lk.propertymanagement.swing.RoundPanel roundPanel3;
    private lk.propertymanagement.swing.RoundPanel roundPanel4;
    private lk.propertymanagement.swing.RoundPanel roundPanel5;
    private lk.propertymanagement.swing.RoundPanel roundPanel6;
    private lk.propertymanagement.swing.RoundPanel roundPanel7;
    private lk.propertymanagement.swing.RoundPanel roundPanel8;
    private lk.propertymanagement.swing.RoundPanel roundPanel9;
    // End of variables declaration//GEN-END:variables
}
