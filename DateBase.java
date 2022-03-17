package com.company;

import java.sql.*;
import java.util.HashSet;

public class DateBase {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/dictionary";

    static final String USER = "";
    static final String PASS = "";

    public Connection getConnection() throws ClassCastException, SQLException {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public Statement getStatement() {
        Statement stmt = null;
        try {
            stmt = getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    public void insertData(int lastId, HashSet<String> dictionary) throws SQLException {
        Statement statement = getStatement();
        for (String engWord : dictionary) {
            lastId++;
            String sql = String.format("INSERT INTO russianenglish VALUES (%d, '%s', null)", lastId, engWord);
            statement.executeUpdate(sql);
        }

    }

    public int selectLastId() throws SQLException {
        int id = 0;
        String sql = "SELECT id FROM russianenglish ORDER BY id DESC LIMIT 1";
        ResultSet resultSet = getStatement().executeQuery(sql);
        while (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        resultSet.close();
        return id;
    }
}
