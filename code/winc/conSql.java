/*
 * Decompiled with CFR 0.150.
 */
package wordeotest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conSql {
    static String url = "jdbc:mysql://mysql3.gear.host/dbsubs?useSSL=false";
    static String username = "dbsubs";
    static String password = "Ri7tQE!i?m3E";

    public static Connection getSql() {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return dbConnection;
    }
}

