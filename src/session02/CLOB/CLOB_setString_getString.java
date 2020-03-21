/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session02.CLOB;

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
public class CLOB_setString_getString {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://localhost;";
    static final String DATABASENAME = "databasename=session02;";
    static final String USER = "user=sa;";
    static final String PASS = "password=123";

    static void ClobSetString() throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

        // Deleting the record for re-testing
        String subject = "Test of the setString() method";
        Statement sta = conn.createStatement();
        sta.executeUpdate("DELETE FROM Article WHERE Subject = '"
                + subject + "'");

        // Inserting CLOB value with a PreparedStatement
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Article (Subject, Body) VALUES (?,?)");
        ps.setString(1, subject);
        ps.setString(2, "He is wonderful and strange and who knows"
                + " how old he is, he thought. Never have I had such"
                + " a strong fish nor one who acted so strangely..."
                + " He cannot know that it is only one man against him,"
                + " nor that it is an old man. But what a great fish"
                + " he is and what will he bring in the market"
                + " if the flesh is good.");
        int count = ps.executeUpdate();
        ps.close();

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

    static void ClobGetString() throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

        // Retrieving CLOB value with getString()
        Statement sta = conn.createStatement();
        ResultSet res = sta.executeQuery("SELECT * FROM Article");
        int i = 0;
        while (res.next() && i < 3) {
            i++;
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

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        ClobSetString();
        ClobGetString();
    }
}

/*
Another way to insert a character string into a CLOB column is to create a PreparedStatement object 
and use the setString() method.

The simplest way to retrieve the character string value from a CLOB column is to use the getString() method
on the ResultSet object.

*/
