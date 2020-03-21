/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session01_JDBCConcepts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author June
 */
public class update_database_by_PreparedStatement {
    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://localhost;";

    //  Ten database, nguoi dung va mat khau cua co so du lieu
    static final String DATABASENAME = "databasename=softech1;";
    static final String USER = "user=sa;";
    static final String PASS = "password=123";
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        
        Class.forName(JDBC_DRIVER);
        System.out.println("Dang ket noi toi co so du lieu ...");
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);
        System.out.println("Tao cac lenh truy van SQL ...");
        String sql;
        sql = "update student set batch=?, name=? where studentid like ?";
        stmt = conn.createStatement();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "batchnew");
            pstmt.setString(2, "new name");
            pstmt.setString(3, "softech001");
            pstmt.executeUpdate();
            System.out.println("Done!");
        }
        stmt.close();
        conn.close();
    }
}
