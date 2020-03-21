/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session01_JDBCConcepts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

/**
 *
 * @author June
 */
public class Transaction_Savepoint_Rollback_Example {

    static void queryDatabase() throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        Class.forName(database.JDBC_DRIVER);
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        stmt = conn.createStatement();
        String sql = "SELECT num FROM test";
        try (ResultSet rs = stmt.executeQuery(sql)) {
            int count = 0;
            while (rs.next()) {
                count++;
                int num = rs.getInt("num");
                System.out.print("\nNum " + count + ": " + num);
            }
        }
        stmt.close();
        conn.close();
    }

    static void savepointExample() throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        Class.forName(database.JDBC_DRIVER);
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        conn.setAutoCommit(false);
        stmt = conn.createStatement();
        Savepoint savepoint1 = conn.setSavepoint("savepoint1");
        String sql = "INSERT INTO test values(10)";
        stmt.executeUpdate(sql);
        Savepoint savepoint2 = conn.setSavepoint("savepoint2");
        sql = "INSERT INTO test values(11)";
        stmt.executeUpdate(sql);
        Savepoint savepoint3 = conn.setSavepoint("savepoint3");
        sql = "INSERT INTO test values(12)";
        stmt.executeUpdate(sql);
        conn.rollback(savepoint3);
        conn.commit();
        stmt.close();
        conn.close();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        System.out.print("Database trước khi thêm dữ liệu: ");
        queryDatabase();
        System.out.println("");
        System.out.println("");
        savepointExample();
        System.out.print("Database sau khi thêm dữ liệu: ");
        queryDatabase();
        System.out.println("");
    }
}
