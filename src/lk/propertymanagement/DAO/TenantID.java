package lk.propertymanagement.DAO;

import java.sql.ResultSet;
import lk.propertymanagement.Connection.MySQL;

public class TenantID {
        public static String generateTenantID() {
        String lastId = getLastTenantId();
        if (lastId == null) {
            return "CUS_001";
        }

        int lastNumericID = Integer.parseInt(lastId.substring(4));

        int newNumericID = lastNumericID + 1;

        String newID = String.format("CUS_%03d", newNumericID);
        return newID;
    }

    public static String getLastTenantId() {
        String lastId = null;

        try {
            ResultSet rs = MySQL.executeSearch("SELECT `id` FROM `tenant` ORDER BY `id` DESC LIMIT 1 ");

            if (rs.next()) {
                lastId = rs.getString("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastId;
    }
}
