/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session02.BLOB;

import java.sql.Blob;
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
public class BLOB_setBlob_getBlob {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://localhost;";
    static final String DATABASENAME = "databasename=session02;";
    static final String USER = "user=sa;";
    static final String PASS = "password=123";

    static void BlobSetBlob() throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

        // Deleting records for re-testing
        Statement sta = conn.createStatement();
        sta.executeUpdate(
                "DELETE FROM Image WHERE Subject LIKE 'Copy of %'");

        // Creating a PreparedStatement for inserting new records
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Image (Subject, Body) VALUES (?,?)");

        // Looping though the first 3 records
        ResultSet res = sta.executeQuery(
                "SELECT * FROM Image ORDER BY ID");
        int i = 0;
        while (res.next() && i < 3) {
            i++;
            System.out.println("Copying record ID: " + res.getInt("ID"));
            String subject = res.getString("Subject");
            Blob body = res.getBlob("Body");

            // Modifying the Blob object
            byte[] chuck = {(byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00};
            body.setBytes(1, chuck);

            // Inserting a new record with setBlob()
            ps.setString(1, "Copy of " + subject);
            ps.setBlob(2, body);
            ps.executeUpdate();
        }
        ps.close();
        res.close();

        // Checking the new records
        res = sta.executeQuery(
                "SELECT * FROM Image WHERE Subject LIKE 'Copy of %'");
        while (res.next()) {
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

    static void BlobGetBlob() throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

        // Retrieving BLOB value with getBlob()
        Statement sta = conn.createStatement();
        ResultSet res = sta.executeQuery("SELECT * FROM Image");
        int i = 0;
        while (res.next() && i < 3) {
            i++;
            System.out.println("Record ID: " + res.getInt("ID"));
            System.out.println("   Subject = " + res.getString("Subject"));
            Blob bodyOut = res.getBlob("Body");
            int length = (int) bodyOut.length();
            System.out.println("   Body Size = " + length);
            byte[] body = bodyOut.getBytes(1, length);
            String bodyHex = bytesToHex(body, 32);
            System.out.println("   Body in HEX = " + bodyHex + "...");
            bodyOut.free(); // new in JDBC 4.0
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
//        BlobSetBlob();
        BlobGetBlob();
    }
}

/*
If you want to insert a BLOB column with a character string that comes from a java.sql.Blob object, 
you can directly set the value with PreparedStatement.setBlob() method.

To test this, I wrote the following program to copy some records with BLOB values as new records back 
into the same table. During the copy process, the BLOB values are also modified with some Blob object 
methods - The first 6 bytes are replaced with 0 values.


If you like to work with java.sql.Blob objects, you can retrieve BLOB values with the getBlob() method 
on ResultSet objects. The Blob object offers some interesting methods:
1. length() - Returns the number of bytes in the Blob object. The return value has a type of "long". 
You may need to convert it to "int" to be used in other methods.
2. getBytes(long pos, int length) - Returns a substring of characters from the Blob object 
with a specified starting position and length. Note the start position is "long" value, 
but the length is "int" value.
3. getBinaryStream() - Returns a InputStream object from the Blob object so that 
you can read the content as a stream.
4. free() - Releases the resources that the Blob object holds. This was added in JDBC 4.0 (Java 1.6).
 */
