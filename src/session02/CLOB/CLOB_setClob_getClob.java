/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session02.CLOB;

import java.sql.Clob;
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
public class CLOB_setClob_getClob {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://localhost;";
    static final String DATABASENAME = "databasename=session02;";
    static final String USER = "user=sa;";
    static final String PASS = "password=123";

    static void ClobSetClob() throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

        // Deleting records for re-testing
        Statement sta = conn.createStatement();
        sta.executeUpdate(
                "DELETE FROM Article WHERE Subject LIKE 'Copy of %'");

        // Creating a PreparedStatement for inserting new records
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Article (Subject, Body) VALUES (?,?)");

        // Looping though the first 3 records
        ResultSet res = sta.executeQuery(
                "SELECT * FROM Article ORDER BY ID");
        int i = 0;
        while (res.next() && i < 3) {
            i++;
            System.out.println("Copying record ID: " + res.getInt("ID"));
            String subject = res.getString("Subject");
            Clob body = res.getClob("Body");

            // Modifying the Clob object
            String chuck = body.getSubString(1, 32);
            chuck = chuck.toUpperCase();
            body.setString(1, chuck);

            // Inserting a new record with setClob()
            ps.setString(1, "Copy of " + subject);
            ps.setClob(2, body);
            ps.executeUpdate();
        }
        ps.close();
        res.close();

        // Checking the new records
        res = sta.executeQuery(
                "SELECT * FROM Article WHERE Subject LIKE 'Copy of %'");
        while (res.next()) {
            System.out.println("Record ID: " + res.getInt("ID"));
            System.out.println("   Subject = " + res.getString("Subject"));
            String body = res.getString("Body");
            if (body.length() > 100) {
                body = body.substring(0, 100);
            }
            System.out.println("   Body = " + body + "...");
        }
        res.close();
        sta.close();
        conn.close();
    }

    static void ClobGetClob() throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

        // Retrieving CLOB value with getClob()
        Statement sta = conn.createStatement();
        ResultSet res = sta.executeQuery("SELECT * FROM Article");
        int i = 0;
        while (res.next() && i < 3) {
            i++;
            System.out.println("Record ID: " + res.getInt("ID"));
            System.out.println("   Subject = " + res.getString("Subject"));
            Clob bodyOut = res.getClob("Body");
            int length = (int) bodyOut.length();
            System.out.println("   Body Size = " + length);
            String body = bodyOut.getSubString(1, length);
            if (body.length() > 100) {
                body = body.substring(0, 100);
            }
            System.out.println("   Body = " + body + "...");
            bodyOut.free(); // new in JDBC 4.0
        }
        res.close();
        sta.close();
        conn.close();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        ClobSetClob();
        ClobGetClob();
    }
}

/*
If you want to insert a CLOB column with a character string that comes from a java.sql.Clob object, 
you can directly set the value with PreparedStatement.setClob() method.

If you like to work with java.sql.Clob objects, you can retrieve CLOB values with the getClob() method 
on ResultSet objects. The Clob object offers some interesting methods:
1. length() - Returns the number of characters in the Clob object. 
The return value has a type of "long". You may need to convert it to "int" to be used in other methods.
2. getSubString(long pos, int length) - Returns a substring of characters from the Clob object 
with a specified starting position and length. Note the start position is "long" value, 
but the length is "int" value.
3. getCharacterStream() - Returns a Reader object from the Clob object 
so that you can read the content as a stream.
4. free() - Releases the resources that the Clob object holds. This was added in JDBC 4.0 (Java 1.6).
 */
