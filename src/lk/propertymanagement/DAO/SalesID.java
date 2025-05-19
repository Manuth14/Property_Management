package lk.propertymanagement.DAO;

import java.sql.ResultSet;
import lk.propertymanagement.Connection.MySQL;

public class SalesID {
    
    public static String generateSalesID() {
        String lastID = getLastSalesID();  // Fetch the last ID from the database
        if (lastID == null) {
            return "INV_001";  //  start with CUS_001
        }

        // Extract the numeric part from the last ID
        int lastNumericID = Integer.parseInt(lastID.substring(4));

        // Increment the numeric part
        int newNumericID = lastNumericID + 1;

        // Format the new ID with leading zeros (e.g., CUS_002)
        String newID = String.format("INV_%03d", newNumericID);
        return newID;
    }

    public static String getLastSalesID() {
        String lastID = null;
        try {
            
            String query = "SELECT `id` FROM `rental_sales` ORDER BY id DESC LIMIT 1";
            
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
