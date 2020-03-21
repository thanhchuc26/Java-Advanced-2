/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session01_JDBCConcepts;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author June
 */
public class CallableStatement_Example {

    static void CallableStatement_without_INOUT() throws ClassNotFoundException, SQLException {
        Connection conn;
        CallableStatement cstmt;
        Class.forName(database.JDBC_DRIVER);
        System.out.println("Dang ket noi toi co so du lieu ...");
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        System.out.println("Tao cac lenh truy van SQL bang CallableStatement ...");
        String sql = "{call storeprocedure_whithoutINOUT}";
        cstmt = conn.prepareCall(sql);
        try (ResultSet rs = cstmt.executeQuery()) {
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
        cstmt.close();
        conn.close();
    }

    static void CallableStatement_IN() throws ClassNotFoundException, SQLException {
        Connection conn;
        CallableStatement cstmt;
        Class.forName(database.JDBC_DRIVER);
        System.out.print("Nhập tên lớp: ");
        Scanner input = new Scanner(System.in);
        String lop = input.nextLine();
        System.out.println("Dang ket noi toi co so du lieu ...");
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        System.out.println("Tao cac lenh truy van SQL bang CallableStatement ...");
        String sql = "{call storeprocedure_IN(?)}";
        cstmt = conn.prepareCall(sql);
        cstmt.setString(1, lop);
        try (ResultSet rs = cstmt.executeQuery()) {
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
        cstmt.close();
        conn.close();
    }

    static void CallableStatement_OUT() throws ClassNotFoundException, SQLException {
        Connection conn;
        CallableStatement cstmt;
        Class.forName(database.JDBC_DRIVER);
        System.out.println("Dang ket noi toi co so du lieu ...");
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        System.out.println("Tao cac lenh truy van SQL bang CallableStatement ...");
        String sql = "{call storeprocedure_OUT(?)}";
        cstmt = conn.prepareCall(sql);
        //đăng ký tham số đầu ra
        cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
        cstmt.execute();
        System.out.println("Tổng số học viên của batch130 là: " + cstmt.getInt(1));
        cstmt.close();
        conn.close();
    }

    static void CallableStatement_INOUT() throws ClassNotFoundException, SQLException {
        Connection conn;
        CallableStatement cstmt;
        Class.forName(database.JDBC_DRIVER);
        System.out.print("Nhập tên lớp: ");
        Scanner input = new Scanner(System.in);
        String lop = input.nextLine();
        System.out.println("Dang ket noi toi co so du lieu ...");
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        System.out.println("Tao cac lenh truy van SQL bang CallableStatement ...");
        String sql = "{call storeprocedure_INOUT(?,?)}";
        cstmt = conn.prepareCall(sql);
        cstmt.setString(1, lop);
        //đăng ký tham số đầu ra
        cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
        cstmt.execute();
        System.out.println("Tổng số học viên của " + lop + " là: " + cstmt.getInt(2));
        cstmt.close();
        conn.close();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        CallableStatement_without_INOUT();
//        CallableStatement_IN();
        CallableStatement_OUT();
//        CallableStatement_INOUT();
    }
}
