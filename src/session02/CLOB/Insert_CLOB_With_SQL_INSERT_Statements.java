/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session02.CLOB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author June
 */
public class Insert_CLOB_With_SQL_INSERT_Statements {

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
        sta.executeUpdate("DELETE FROM Article WHERE Subject = '"
                + subject + "'");

// Inserting CLOB value with a regular insert statement
        sta = conn.createStatement();
        int count = sta.executeUpdate(
                "INSERT INTO Article"
                + " (Subject, Body)"
                + " VALUES ('" + subject + "', 'A CLOB (Character Large OBject) is"
                + " a large chunk of data which is stored in a database.')");

// Retrieving CLOB value with getString()
        ResultSet res = sta.executeQuery(
                "SELECT * FROM Article WHERE Subject = '" + subject + "'");
        res.next();
        System.out.println("The inserted record: ");
        System.out.println("   Subject = " + res.getString("Subject"));
        System.out.println("   Body = " + res.getString("Body"));
        res.close();
        sta.close();
        conn.close();
    }
}

/*
The simplest way to insert a character string into a CLOB column is to use a SQL INSERT statement 
and include the character string a SQL string literal in the statement

Using SQL string literals to insert CLOB values into database is not recommended, 
because quote characters (') in the CLOB values must be replaced with escape sequences ('').

Using PreparedStatement with setXXX() method is a much safer choice.
*/
