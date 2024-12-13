package lk.skyland.idGenerator;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.*;
import lk.propertymanagement.Connection.MySQL;



public class ConstructorIdGenerator {

    public static String generateNewConstructorId() {
        String lastID = getLastConstructorId();  
        if (lastID == null) {
            return "CON_001";  
        }

        
        int lastNumericID = Integer.parseInt(lastID.substring(4));

       
        int newNumericID = lastNumericID + 1;

        
        String newID = String.format("CON_%03d", newNumericID);
        return newID;
    }

    public static String getLastConstructorId() {
        String lastID = null;
        try {
            
            String query = "SELECT id FROM constructor ORDER BY id DESC LIMIT 1";
           
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



}

