/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session02.BLOB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author June
 */
public class Insert_BLOB_With_SQL_INSERT_Statements {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://localhost;";
    static final String DATABASENAME = "databasename=session02;";
    static final String USER = "user=sa;";
    static final String PASS = "password=123";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

        // Deleting the record for re-testing
        String subject = "Test on INSERT statement";
        Statement sta = conn.createStatement();
        sta.executeUpdate("DELETE FROM Image WHERE Subject = '" + subject + "'");

        // Inserting BLOB value with a regular insert statement
        sta = conn.createStatement();
        int count = sta.executeUpdate(
                "INSERT INTO Image"
                + " (Subject, Body)"
                + " VALUES ('" + subject + "'"
                + ", 0xC9CBBBCCCEB9C8CABCCCCEB9C9CBBB)"); //SQL Server format
//      +", x'C9CBBBCCCEB9C8CABCCCCEB9C9CBBB')"); // MySQL format

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
}

/*
The simplest way to insert a binary string into a BLOB column is to use a SQL INSERT statement 
and include the binary string a SQL binary literal in the statement as shown in this sample program. 
Note that SQL binary literal format is 0x<hex_numbers>.
 */
