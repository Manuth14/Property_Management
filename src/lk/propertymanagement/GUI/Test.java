/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.propertymanagement.GUI;

import java.util.Vector;
import java.sql.ResultSet;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import lk.propertymanagement.Connection.MySQL;

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

    public static void main(String[] args) {
        String a = "a";
        for (int i = 0; i < 10; i++) {
            a+="a";
        }

    }
}
