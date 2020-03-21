/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session01_JDBCConcepts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author June
 */
public class Stored_Procedure_Example {

    static void create_Stored_Procedure_without_INOUT() throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        Class.forName(database.JDBC_DRIVER);
        System.out.println("Dang ket noi toi co so du lieu ...");
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        System.out.println("Tao Stored Procedure ...");
        stmt = conn.createStatement();
        String sql = "create proc storeprocedure_whithoutINOUT "
                + " as select * from student";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
        System.out.println("Tao Stored Procedure thanh cong");
    }

    static void create_Stored_Procedure_IN() throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        Class.forName(database.JDBC_DRIVER);
        System.out.println("Dang ket noi toi co so du lieu ...");
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        System.out.println("Tao Stored Procedure ...");
        stmt = conn.createStatement();
        String sql = "create proc storeprocedure_IN "
                + "@batch nvarchar(10) "
                + "AS "
                + "select * from student "
                + "where student.batch like @batch ";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
        System.out.println("Tao Stored Procedure thanh cong");
    }

    static void create_Stored_Procedure_OUT() throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        Class.forName(database.JDBC_DRIVER);
        System.out.println("Dang ket noi toi co so du lieu ...");
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        System.out.println("Tao Stored Procedure ...");
        stmt = conn.createStatement();
        String sql = "create proc storeprocedure_OUT "
                + "@num int OUTPUT"
                + " as "
                + " select @num=count(batch) "
                + " from student "
                + "where student.batch like 'batch130' ";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
        System.out.println("Tao Stored Procedure thanh cong");
    }

    static void create_Stored_Procedure_INOUT() throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        Class.forName(database.JDBC_DRIVER);
        System.out.println("Dang ket noi toi co so du lieu ...");
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        System.out.println("Tao Stored Procedure ...");
        stmt = conn.createStatement();
        String sql = "create proc storeprocedure_INOUT "
                + "@batch nvarchar(10),@num int OUTPUT"
                + " as "
                + " select @num=count(batch) "
                + " from student "
                + "where student.batch like @batch ";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
        System.out.println("Tao Stored Procedure thanh cong");
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        create_Stored_Procedure_without_INOUT();
//        create_Stored_Procedure_IN();
        create_Stored_Procedure_OUT();
//        create_Stored_Procedure_INOUT();
    }
}
