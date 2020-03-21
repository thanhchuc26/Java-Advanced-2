/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session02.BLOB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class BLOB_setBinaryStream_getBinaryStream {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://localhost;";
    static final String DATABASENAME = "databasename=session02;";
    static final String USER = "user=sa;";
    static final String PASS = "password=123";

    static void BlobSetBinaryStream() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
        Connection conn;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

        // Deleting the record for re-testing
        String subject = "Test of setBinaryStream() methods";
        Statement sta = conn.createStatement();
        sta.executeUpdate("DELETE FROM Image WHERE Subject = '"
                + subject + "'");

// Inserting BLOB value with a PreparedStatement
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Image (Subject, Body) VALUES (?,?)");
        ps.setString(1, subject);
        InputStream bodyIn = new FileInputStream("./build/classes/session02/BLOB/BLOB_setBinaryStream_getBinaryStream.class");

// Test 1 - This will not work with JDBC 3.0 drivers
//    ps.setBinaryStream(2, bodyIn);
// Test 2 - This will not work with JDBC 3.0 drivers
//    File fileIn = new File("SqlServerBlobSetBinaryStream.class");
//    ps.setBinaryStream(2, bodyIn, fileIn.length());
// Test 3 - This works with JDBC 3.0 drivers
        File fileIn = new File("./build/classes/session02/BLOB/BLOB_setBinaryStream_getBinaryStream.class");
        ps.setBinaryStream(2, bodyIn, (int) fileIn.length());

        int count = ps.executeUpdate();
        bodyIn.close();
        ps.close();

// Retrieving BLOB value with getBytes()
        sta = conn.createStatement();
        ResultSet res = sta.executeQuery("SELECT * FROM Image"
                + " WHERE Subject = '" + subject + "'");
        res.next();
        System.out.println("The inserted record: ");
        System.out.println("   Subject = " + res.getString("Subject"));
        System.out.println("   Body = "
                + new String(res.getBytes("Body"), 0, 32));
        res.close();

        sta.close();
        conn.close();
    }

    static void BlobGetBinaryStream() throws ClassNotFoundException, SQLException, IOException {
        Connection conn;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

        try ( // Retrieving BLOB value with getBinaryStream()
                Statement sta = conn.createStatement()) {
            ResultSet res = sta.executeQuery("SELECT * FROM Image");
            int i = 0;
            while (res.next() && i < 1) {
                i++;
                System.out.println("Record ID: " + res.getInt("ID"));
                System.out.println("   Subject = " + res.getString("Subject"));
                System.out.println("   Body = " + res.getString("Body"));
            }
            res.close();
        }
        conn.close();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        BlobSetBinaryStream();
        BlobGetBinaryStream();
    }
}

/*
If you want to insert the entire content of a binary file into a BLOB column, 
you should create an InputStream object from this file, and use the PreparedStatement.setBinaryStream() 
method to pass the binary file content to the BLOB column through the InputStream object. 
There are 3 versions of the setBinaryStream() method, two of them were added as part of JDBC 4.0 (Java 1.6).
Your JDBC driver may not support them:
1. setBinaryStream(int parameterIndex, InputStream x):
The data will be read from the InputStream as needed until end-of-file is reached. 
This was added in JDBC 4.0 (Java 1.6).
2. setBinaryStream(int parameterIndex, InputStream x, int length):
The data will be read from the InputStream as needed for "length" bytes.
3. setBinaryStream(int parameterIndex, InputStream x, long length):
The data will be read from the InputStream as needed for "length" bytes. 
This was added in JDBC 4.0 (Java 1.6).


BLOB values can also be retrieved with the getBinaryStream() method on the ResultSet object, 
which will return an OutputStream object. 
Then you can read the BLOB values from the OutputStream object with the read() method.
 */
