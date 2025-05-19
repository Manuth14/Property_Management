package lk.propertymanagement.DAO;

import java.sql.ResultSet;
import lk.propertymanagement.Connection.MySQL;

public class SalesID {

    public static String generateSalesID() {
        String lastId = getLastSalesId();
        if (lastId == null) {
            return "INVOICE_001";
        }

        int lastNumericID = Integer.parseInt(lastId.substring(8));

        int newNumericID = lastNumericID + 1;

        String newID = String.format("INVOICE_%03d", newNumericID);
        return newID;
    }

    public static String getLastSalesId() {
        String lastId = null;

        try {
            ResultSet rs = MySQL.executeSearch("SELECT `id` FROM `rental_sales` ORDER BY `id` DESC LIMIT 1 ");

            if (rs.next()) {
                lastId = rs.getString("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastId;
    }

}
