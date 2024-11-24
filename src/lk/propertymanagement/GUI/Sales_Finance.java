/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package lk.propertymanagement.GUI;

import com.raven.chart.ModelChart;
import java.awt.Color;
import java.time.Year;
import java.util.Vector;
import java.sql.ResultSet;
import java.text.DateFormatSymbols;
import java.time.Month;
import java.util.Arrays;
import java.util.Random;
import lk.propertymanagement.Connection.MySQL;

/**
 *
 * @author HP
 */
public class Sales_Finance extends javax.swing.JPanel {

    /**
     * Creates new form Sales_Finance
     */
    public Sales_Finance() {
        initComponents();
        loadCartData();
        rentalSalesIncome();
    }

    private int getCurrentYear() {
        return Year.now().getValue(); // Get the current year as an integer
    }

    private void loadCartData() {
        String currentYear = String.valueOf(getCurrentYear());

        String monthly_rentalQuery = "SELECT SUM(payment) AS payment FROM monthly_rental_paymet "
                + "WHERE YEAR(date) ='" + currentYear + "' ";
        String outstandingPaymentQuery = "SELECT SUM(rental_ammount) AS payment,COUNT(`rental_sales`.`id`) AS outstandingPaymentCount FROM rental_sales INNER JOIN payment_status ON rental_sales.payment_status_id = payment_status.id WHERE YEAR(paid_date) ='"+currentYear+"' AND payment_status.`status`='Not Recieved'";
        String paymentReceivedQuery = "SELECT SUM(rental_ammount) AS payment,COUNT(`rental_sales`.`id`) AS paymentReceivedCount FROM rental_sales INNER JOIN payment_status ON rental_sales.payment_status_id = payment_status.id WHERE YEAR(paid_date) ='2024' AND payment_status.`status`='Recieved'";

        try {

            ResultSet monthly_rental = MySQL.executeSearch(monthly_rentalQuery);
            ResultSet outstandingPayment = MySQL.executeSearch(outstandingPaymentQuery);
            ResultSet rentalPaymentReceived = MySQL.executeSearch(paymentReceivedQuery);
            double revenue = 0;
            double salesCount = 0;
            if (monthly_rental.next()) {
                revenue = monthly_rental.getDouble("payment");
            }

            if (outstandingPayment.next()) {
                jLabel13.setText(outstandingPayment.getString("outstandingPaymentCount"));
                salesCount = outstandingPayment.getDouble("outstandingPaymentCount");
            }

            if (rentalPaymentReceived.next()) {
                jLabel10.setText(rentalPaymentReceived.getString("paymentReceivedCount"));
                salesCount += rentalPaymentReceived.getDouble("paymentReceivedCount");
                revenue += rentalPaymentReceived.getDouble("payment");
            }
            jLabel6.setText("Rs." + revenue);
            jLabel7.setText("" + salesCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private Vector getPropertyType() {
        Vector<String> vector = new Vector<>();
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM property_type");
            while (resultSet.next()) {
                vector.add(resultSet.getString("id"));
                Color color1 = getColor();
                Color color2 = getColor();
                chart.addLegend(resultSet.getString("type"), color1, color2);
                lineChart1.addLegend(resultSet.getString("type"), color1, color1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vector;
    }

    private static Vector months() {
        Vector<String> months = new Vector<>();

        String[] month = {
            "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
            "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
        };
        months.addAll(Arrays.asList(month));
        return months;
    }

    private void rentalSalesIncome() {
        String currentYear = String.valueOf(getCurrentYear());
        Vector<String> monthsName = months();

        Vector<String> propertyTypeID = getPropertyType();

        for (String month : monthsName) {

            Vector<Double> monthlyRentalIncomeData = new Vector<>(); //Monthly Rental Income
            Vector<Double> rentalIncomeData = new Vector<>(); // Rental Sales

            for (String id : propertyTypeID) {

                // Rental Sales Query
                String rental_sales = "SELECT SUM(rental_ammount) AS payment FROM rental_sales "
                        + "INNER JOIN rental_properties ON rental_sales.rental_properties_id = rental_properties.id "
                        + "INNER JOIN property_type ON rental_properties.property_type_id= property_type.id "
                        + "WHERE YEAR(paid_date) ='" + currentYear + "' "
                        + "AND UPPER(DATE_FORMAT(paid_date, '%M')) = '" + month + "' AND property_type.id ='" + id + "'";

                //Monthly Rental Income Query
                String monthly_rental_paymet = "SELECT SUM(payment) AS payment FROM monthly_rental_paymet "
                        + "INNER JOIN rental_properties ON monthly_rental_paymet.rental_properties_id = rental_properties.id "
                        + "INNER JOIN property_type ON rental_properties.property_type_id= property_type.id "
                        + "WHERE YEAR(DATE) ='" + currentYear + "' "
                        + "AND UPPER(DATE_FORMAT(date, '%M')) = '" + month + "' AND property_type.id ='" + id + "'";

                try {
                    // Rental Sales
                    ResultSet resultSet = MySQL.executeSearch(rental_sales);
                    if (resultSet.next()) {

                        if (resultSet.getString("payment") != null) {

                            monthlyRentalIncomeData.add(resultSet.getDouble("payment"));
                        } else {
                            monthlyRentalIncomeData.add(Double.parseDouble("0"));
                        }

                    } 

                    //Monthly Rental Income
                    ResultSet resultSet1 = MySQL.executeSearch(monthly_rental_paymet);
                    if (resultSet1.next()) {

                        if (resultSet1.getString("payment") != null) {

                            rentalIncomeData.add(resultSet1.getDouble("payment"));
                        } else {
                            rentalIncomeData.add(Double.parseDouble("0"));
                        }

                    } 
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            double[] data = monthlyRentalIncomeData.stream().mapToDouble(Double::doubleValue).toArray();// Rental Sales
            double[] data1 = rentalIncomeData.stream().mapToDouble(Double::doubleValue).toArray(); //Monthly Rental Income
            chart.addData(new ModelChart(month, data));// Rental Sales
            lineChart1.addData(new ModelChart(month, data1));//Monthly Rental Income
        }
        chart.start();// Rental Sales
        lineChart1.start();//Monthly Rental Income
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        roundPanel4 = new com.raven.swing.RoundPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        roundPanel3 = new com.raven.swing.RoundPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        roundPanel6 = new com.raven.swing.RoundPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        roundPanel1 = new com.raven.swing.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        lineChart1 = new com.raven.chart.LineChart();
        roundPanel2 = new com.raven.swing.RoundPanel();
        jLabel2 = new javax.swing.JLabel();
        chart = new com.raven.chart.Chart();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(2, 2, 10, 10));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        jLabel5.setText("Total Revenue");

        jLabel6.setText("200");

        javax.swing.GroupLayout roundPanel5Layout = new javax.swing.GroupLayout(roundPanel5);
        roundPanel5.setLayout(roundPanel5Layout);
        roundPanel5Layout.setHorizontalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        roundPanel5Layout.setVerticalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.add(roundPanel5);

        jLabel7.setText("200");

        jLabel8.setText("Total Sales");

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        javax.swing.GroupLayout roundPanel4Layout = new javax.swing.GroupLayout(roundPanel4);
        roundPanel4.setLayout(roundPanel4Layout);
        roundPanel4Layout.setHorizontalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel16))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        roundPanel4Layout.setVerticalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.add(roundPanel4);

        jLabel10.setText("200");

        jLabel11.setText("Total Payment Recieved");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel17))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(16, 16, 16))
        );

        jPanel2.add(roundPanel3);

        jLabel13.setText("200");

        jLabel14.setText("Total Outstanding Payment");

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/icons8-payment-50.png"))); // NOI18N

        javax.swing.GroupLayout roundPanel6Layout = new javax.swing.GroupLayout(roundPanel6);
        roundPanel6.setLayout(roundPanel6Layout);
        roundPanel6Layout.setHorizontalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel18))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        roundPanel6Layout.setVerticalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.add(roundPanel6);

        jLabel3.setText("Finance");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        roundPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("Monthly Rental Income");

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 669, Short.MAX_VALUE))
                    .addComponent(lineChart1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineChart1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setText("Rental Sales Income");

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
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.chart.Chart chart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.raven.chart.LineChart lineChart1;
    private com.raven.swing.RoundPanel roundPanel1;
    private com.raven.swing.RoundPanel roundPanel2;
    private com.raven.swing.RoundPanel roundPanel3;
    private com.raven.swing.RoundPanel roundPanel4;
    private com.raven.swing.RoundPanel roundPanel5;
    private com.raven.swing.RoundPanel roundPanel6;
    // End of variables declaration//GEN-END:variables
}
