package sdu.se9.tv2.management.system.persistence;

import sdu.se9.tv2.management.system.helpers.Config;

import java.sql.*;

public class PersistenceDatabaseHelper {
    private static Connection connection;

    public static Connection getConnection () {
        if (connection == null) {
            try {
                DriverManager.registerDriver(new org.postgresql.Driver());

                connection = DriverManager.getConnection((String)Config.get("database_connection_url"),
                        (String)Config.get("database_user"),
                        (String)Config.get("database_password"));
            } catch (SQLException err) {
                err.printStackTrace();
            }
        }

        return connection;
    }

    public static void main(String[] args) {
        // Load config
        Config.load();

        // Get connection
        Connection connection = getConnection();

        // Create table Program
        try {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Program (" +
                    "id SERIAL PRIMARY KEY," +
                    "producerID INTEGER NOT NULL REFERENCES Producer(id)," +
                    "name VARCHAR(255) UNIQUE NOT NULL," +
                    "internalID INTEGER NOT NULL," +
                    "pendingApproval BOOLEAN NOT NULL," +
                    "approved BOOLEAN NOT NULL" +
                    ")");

            stmt.execute();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
}