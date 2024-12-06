/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.propertymanagement.GUI;

import java.util.Vector;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author HP
 */
public class Test {

    private static Vector months() {
        Vector<String> months = new Vector<>();
//        try {
//            ResultSet resultSet = MySQL.executeSearch("SELECT DISTINCT DATE_FORMAT(date, '%M') AS month_name FROM monthly_rental_paymet");
//            while (resultSet.next()) {
//                months.add(resultSet.getString("month_name"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String[] month = {
            "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
            "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
        };
        months.addAll(Arrays.asList(month));
        return months;
    }

    public static void mon() {
        String[] monthNames = new DateFormatSymbols().getMonths();

        // Print month names in uppercase
        System.out.println("Month names in uppercase:");
        for (String month : monthNames) {
            if (month != null && !month.isEmpty()) { // Ensure non-null and non-empty values
                System.out.println(month.toUpperCase());
            }
        }
    }

    public static double calculateTax(double income) {
        double tax = 0.0;
        double releif = 0;
        double first = 0;
        double next1 = 0;
        double next2 = 0;
        double next3 = 0;
        double next4 = 0;
        double balance = 0;
        // Define the income slabs and respective tax rates
        double[] slabs = {100000, 41666.67, 41666.67, 41666.67, 41666.67, 41666.67};
        double[] rates = {0.0, 0.06, 0.12, 0.18, 0.24, 0.30};

        // Calculate tax based on slabs
        for (int i = 0; i < slabs.length; i++) {
            if (income <= 0) {
                break;
            }
            double taxableIncome = Math.min(income, slabs[i]);

            tax += taxableIncome * rates[i];
            System.out.println("" + tax + " " + taxableIncome);
            income -= taxableIncome;

        }

        // Apply the 36% rate for the remaining income (if any)
        if (income > 0) {
            tax += income * 0.36;

        }

        return tax;
    }

    public static void main(String[] args) {
        
        }
    }


