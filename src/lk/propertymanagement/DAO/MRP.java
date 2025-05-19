package lk.propertymanagement.DAO;

import java.sql.ResultSet;
import lk.propertymanagement.Connection.MySQL;

public class MRP {
    
        public static String generateMrpID() {
        String lastId = getLastMrpId();
        if (lastId == null) {
            return "MRP_001";
        }

        int lastNumericID = Integer.parseInt(lastId.substring(4));

        int newNumericID = lastNumericID + 1;

        String newID = String.format("MRP%03d", newNumericID);
        return newID;
    }

    public static String getLastMrpId() {
        String lastId = null;

        try {
            ResultSet rs = MySQL.executeSearch("SELECT `id` FROM `monthly_rental_paymet` ORDER BY `id` DESC LIMIT 1 ");

            if (rs.next()) {
                lastId = rs.getString("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastId;
    }
    
}
