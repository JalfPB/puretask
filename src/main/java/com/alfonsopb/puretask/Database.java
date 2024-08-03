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
                // Verificar y crear las tablas si no existen
                createTables(conn);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static void createTables(Connection conn) {
        String createTasksTable = "CREATE TABLE IF NOT EXISTS tasks (\n"
                                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                                + " title TEXT NOT NULL,\n"
                                + " description TEXT,\n"
                                + " completed BOOLEAN,\n"
                                + " category_id INTEGER,\n"
                                + " status TEXT DEFAULT 'To Do',\n"
                                + " FOREIGN KEY (category_id) REFERENCES categories(id)\n"
                                + ");";

        String createCategoriesTable = "CREATE TABLE IF NOT EXISTS categories (\n"
                                     + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                                     + " name TEXT NOT NULL UNIQUE\n"
                                     + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTasksTable);
            stmt.execute(createCategoriesTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        connect();
    }
}