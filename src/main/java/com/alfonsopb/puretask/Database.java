package com.alfonsopb.puretask;

/**
 *
 * @author Alfon
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:db/tasks.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            if (conn != null) {
                // Verificar y crear la tabla si no existe
                createTable(conn);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static void createTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS tasks (\n"
                   + " id integer PRIMARY KEY AUTOINCREMENT,\n"
                   + " title text NOT NULL,\n"
                   + " description text,\n"
                   + " completed boolean\n"
                   + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        connect();
    }
}