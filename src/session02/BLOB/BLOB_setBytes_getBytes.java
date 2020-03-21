/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session02.BLOB;

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
public class BLOB_setBytes_getBytes {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://localhost;";
    static final String DATABASENAME = "databasename=session02;";
    static final String USER = "user=sa;";
    static final String PASS = "password=123";

    static void BlobSetBytes() throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

        // Deleting the record for re-testing
        String subject = "Test of the setBytes() method";
        Statement sta = conn.createStatement();
        sta.executeUpdate("DELETE FROM Image WHERE Subject = '" + subject + "'");

        // Inserting BLOB value with PreparedStatement.setBytes()
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Image (Subject, Body) VALUES (?,?)");
        ps.setString(1, subject);
        byte[] bodyIn = {(byte) 0xC9, (byte) 0xCB, (byte) 0xBB,
            (byte) 0xCC, (byte) 0xCE, (byte) 0xB9,
            (byte) 0xC8, (byte) 0xCA, (byte) 0xBC,
            (byte) 0xCC, (byte) 0xCE, (byte) 0xB9,
            (byte) 0xC9, (byte) 0xCB, (byte) 0xBB};
        ps.setBytes(2, bodyIn);
        int count = ps.executeUpdate();
        ps.close();

// Retrieving BLOB value with getBytes()
        ResultSet res = sta.executeQuery(
                "SELECT * FROM Image WHERE Subject = '" + subject + "'");
        res.next();
        System.out.println("The inserted record: ");
        System.out.println("   Subject = " + res.getString("Subject"));
        System.out.println("   Body = "
                + new String(res.getBytes("Body")));
        res.close();
        sta.close();
        conn.close();
    }

    static void BlobGetBytes() throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

        Statement sta = conn.createStatement();
        ResultSet res = sta.executeQuery("SELECT * FROM Image");
        int i = 0;
        while (res.next() && i < 3) {
            i++;
            System.out.println("Record ID: " + res.getInt("ID"));
            System.out.println("   Subject = " + res.getString("Subject"));
            byte[] body = res.getBytes("Body");
            String bodyHex = bytesToHex(body, 32);
            System.out.println("   Body in HEX = " + bodyHex + "...");
        }
        res.close();
        sta.close();
        conn.close();
    }

    public static String bytesToHex(byte[] bytes, int max) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < bytes.length && i < max; i++) {
            buffer.append(Integer.toHexString(bytes[i] & 0xFF));
        }
        return buffer.toString().toUpperCase();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        BlobSetBytes();
        BlobGetBytes();
    }
}

/*
Another way to insert a binary string into a BLOB column is to create a PreparedStatement object 
and use the setBytes() method. 

The simplest way to retrieve the character string value from a BLOB column is to use the getBytes() 
method on the ResultSet object.
 */
