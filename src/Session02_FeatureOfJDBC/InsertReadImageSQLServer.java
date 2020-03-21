/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session02_FeatureOfJDBC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author June
 */
public class InsertReadImageSQLServer {
    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://localhost;";
    static final String DATABASENAME = "databasename=session02;";
    static final String USER = "user=sa;";
    static final String PASS = "password=123";
    static void InsertImage_SQLServer() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException{
        Connection conn;
        String filename = "samsungS8.jpg";
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);
        
        File myFile = new File("./img/insert/samsungS8.jpg");
        FileInputStream in = new FileInputStream(myFile);
             byte[] image = new byte[(int) myFile.length()];
             in.read(image);
             // Below: the question marks are IN parameter placeholders.
             String sql = "INSERT INTO testImage VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, filename);
            stmt.setBytes(2, image);
            stmt.executeUpdate();
        }
        conn.close();
    }
    static void ReadImage_SQLServer() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException{
        Connection conn;
        String filename = "";
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);
        byte[] fileBytes;
        String sql = "select fname,img from testImage";
        Statement state = conn.createStatement();
        try (ResultSet rs = state.executeQuery(sql)) {
            if (rs.next()) {
                fileBytes = rs.getBytes("img");
                try (OutputStream targetFile = new FileOutputStream("./img/read/"+rs.getString("fname"))) {
                    targetFile.write(fileBytes);
                }
            }
        }
        conn.close();
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
//        InsertImage_SQLServer();
        ReadImage_SQLServer();
    }
}
