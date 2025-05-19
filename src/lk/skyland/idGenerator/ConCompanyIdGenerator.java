package lk.skyland.idGenerator;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.*;
import lk.propertymanagement.Connection.MySQL;



public class ConCompanyIdGenerator {

    public static String generateNewConCompanyId() {
        String lastID = getLastConCompanyId();  // Fetch the last ID from the database
        if (lastID == null) {
            return "COM_001";  //  start with CUS_001
        }

        // Extract the numeric part from the last ID
        int lastNumericID = Integer.parseInt(lastID.substring(4));

        // Increment the numeric part
        int newNumericID = lastNumericID + 1;

        // Format the new ID with leading zeros (e.g., CUS_002)
        String newID = String.format("COM_%03d", newNumericID);
        return newID;
    }

    public static String getLastConCompanyId() {
        String lastID = null;
        try {
            
            String query = "SELECT id FROM company ORDER BY id DESC LIMIT 1";
            
            ResultSet rs = MySQL.executeSearch(query);

            if (rs.next()) {
                lastID = rs.getString("id");
            }

            rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastID;
    }

//    public static void main(String[] args) {
//        String newID = generateNewConCompanyId();
//        try{
//            Mysql2.executeIUD("INSERT INTO `company`(`id`,`company_name`,`hotline_01`,`hotline_02`,`email`,`address_id`)VALUES('" + newID + "','rex','0112','0113','rex@gmail.com','1')");
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        System.out.println("New Company ID: " + newID);
//
//        // You can now use this newID in your Swing application when inserting new records
//    }

}

