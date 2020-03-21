/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session02.CLOB;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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
public class CLOB_setCharacterStream_getCharacterStream {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://localhost;";
    static final String DATABASENAME = "databasename=session02;";
    static final String USER = "user=sa;";
    static final String PASS = "password=123";

    static void ClobSetCharacterStream() throws ClassNotFoundException, SQLException, IOException {
        Connection conn;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

        // Deleting the record for re-testing
        String subject = "Test of setCharacterStream() methods";
        Statement sta = conn.createStatement();
        sta.executeUpdate("DELETE FROM Article WHERE Subject = '"
                + subject + "'");

        // Inserting CLOB value with a PreparedStatement
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Article (Subject, Body) VALUES (?,?)");
        ps.setString(1, subject);
        Reader bodyIn = new FileReader("./build/classes/session02/CLOB/CLOB_setCharacterStream_getCharacterStream.class");

        // Test 1 - This will not work with JDBC 3.0 drivers
        //    ps.setCharacterStream(2, bodyIn);
        // Test 2 - This will not work with JDBC 3.0 drivers
        //   File fileIn = new File("SqlServerClobSetCharacterStream.java");
        //    ps.setCharacterStream(2, bodyIn, fileIn.length());
        // Test 3 - This works with JDBC 3.0 drivers
        File fileIn = new File("./build/classes/session02/CLOB/CLOB_setCharacterStream_getCharacterStream.class");
        ps.setCharacterStream(2, bodyIn, (int) fileIn.length());

        int count = ps.executeUpdate();
        bodyIn.close();
        ps.close();

        // Retrieving CLOB value with getString()
        sta = conn.createStatement();
        ResultSet res = sta.executeQuery("SELECT * FROM Article"
                + " WHERE Subject = '" + subject + "'");
        res.next();
        System.out.println("The inserted record: ");
        System.out.println("   Subject = " + res.getString("Subject"));
        System.out.println("   Body = "
                + res.getString("Body").substring(0, 256));
        res.close();
        sta.close();
        conn.close();
    }

    static void ClobGetCharacterStream() throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

        // Retrieving CLOB value with getCharacterStream()
        Statement sta = conn.createStatement();
        ResultSet res = sta.executeQuery("SELECT * FROM Article");
        int i = 0;
        while (res.next() && i < 3) {
            i++;
            System.out.println("Record ID: " + res.getInt("ID"));
            System.out.println("   Subject = " + res.getString("Subject"));
            System.out.println("   Body = " + res.getString("Body"));
        }
        res.close();

        sta.close();
        conn.close();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
//        ClobSetCharacterStream();
        ClobGetCharacterStream();
    }
}

/*
If you want to insert the entire content of a text file into a CLOB column, 
you should create a Reader object from this file, and use the PreparedStatement.setCharacterStream() method
to pass the text file content to the CLOB column through the Reader object. 
There are 3 versions of the setCharacterStream() method, two of them were added as part of JDBC 4.0 
(Java 1.6). Your JDBC driver may not support them:
1. setCharacterStream(int parameterIndex, Reader reader) - The data will be read from the Reader stream as needed until end-of-file is reached. This was added in JDBC 4.0 (Java 1.6).
2. setCharacterStream(int parameterIndex, Reader reader, int length) - The data will be read from the Reader stream as needed for "length" characters.
3. setCharacterStream(int parameterIndex, Reader reader, long length) - The data will be read from the Reader stream as needed for "length" characters. This was added in JDBC 4.0 (Java 1.6).


CLOB values can also be retrieved with the getCharacterStream() method on the ResultSet object, 
which will return a Reader object. Then you can read the CLOB values from the Reader object 
with the read() method.
*/
