package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AppDao<T> implements AppDaoInterface {
    protected Connection getConnection() throws SQLException {
        return getConnection(DB_URL);
    }

    protected Connection getConnection(String dbUrl) throws SQLException {
        return DriverManager.getConnection(dbUrl, USER, PASS);
    }

    private List<String> getAllDatabases() {
        String QUERY = "SHOW DATABASES;";
        List<String> dbNames = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                dbNames.add(rs.getString("Database"));
            }
        } catch (SQLException e) {
            System.out.printf("show DBs query failed: %s%n", e.getMessage());
            e.printStackTrace();
        }

        return dbNames;
    }

    public boolean createDatabase() {
        if (getAllDatabases().contains(DB_NAME)) {
            return true;
        }

        String SQL = "CREATE DATABASE ?;";
        try (Connection conn = getConnection(DB_BASE_URL); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, DB_NAME);
            pstmt.executeQuery();
            System.out.println("Database created successfully...");
            return true;
        } catch (SQLException e) {
            System.out.printf("DB creation failed: %s%n", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean createTables() {
        for (String table : TABLES) {
            if (getAllTables().contains(table)) {
                continue;
            }
            try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
                if (table.equalsIgnoreCase("Employee")) {
                    stmt.executeUpdate(CREATE_EMPLOYEE_TABLE);
                }
            } catch (SQLException e) {
                System.out.printf("Table creation failed: %s%n", e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private List<String> getAllTables() {
        String QUERY = "SHOW TABLES;";
        List<String> dbNames = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                dbNames.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.printf("show tables query failed: %s%n", e.getMessage());
            e.printStackTrace();
        }

        return dbNames;
    }

    public abstract boolean create(T t);
    public abstract boolean update(T t);
    public abstract boolean delete(T t);
    public abstract List<T> getAll();
    public abstract T getById(Integer id);
}
