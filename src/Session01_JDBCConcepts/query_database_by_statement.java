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
import java.sql.Statement;

/**
 *
 * @author June
 */
public class query_database_by_statement {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        Class.forName(database.JDBC_DRIVER);
        System.out.println("Dang ket noi toi co so du lieu ...");
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        System.out.println("Tao cac lenh truy van SQL ...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT studentid, batch, name FROM student";
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String studentid = rs.getString("studentid");
                String batch = rs.getString("batch");
                String name = rs.getString("name");
                System.out.print("\nStudent ID: " + studentid);
                System.out.print("\nBatch: " + batch);
                System.out.println("\nName: " + name);
                System.out.print("\n=================");
            }
        }
        stmt.close();
        conn.close();
    }
}
