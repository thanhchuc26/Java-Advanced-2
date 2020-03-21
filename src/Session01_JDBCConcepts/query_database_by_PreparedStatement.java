/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session01_JDBCConcepts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author June
 */
public class query_database_by_PreparedStatement {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        Class.forName(database.JDBC_DRIVER);
        System.out.println("Dang ket noi toi co so du lieu ...");
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        System.out.println("Tao cac lenh truy van SQL ...");
        stmt = conn.createStatement();
        String sql;
        sql = "SELECT studentid, batch, name FROM student where batch like ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        Scanner input = new Scanner(System.in);
        System.out.print("Nhap tên lớp cần xem: ");
        pstmt.setString(1, input.nextLine());
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String studentid = rs.getString("studentid");
                String batch = rs.getString("batch");
                String name = rs.getString("name");
                System.out.print("\nClass ID: " + studentid);
                System.out.print("\nClass name: " + batch);
                System.out.println("\nStudents number: " + name);
                System.out.print("\n=================");

            }
        }
        stmt.close();
        conn.close();
    }
}
