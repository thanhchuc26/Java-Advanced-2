/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session01_JDBCConcepts;

import com.sun.rowset.JdbcRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.JdbcRowSet;

/**
 *
 * @author June
 */
public class JdbcRowSet_Example {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        ResultSet rs;
        Class.forName(database.JDBC_DRIVER);
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        String strSQL = "SELECT studentid, batch, name FROM student";
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = stmt.executeQuery(strSQL);
        try (JdbcRowSet jdbcRs = new JdbcRowSetImpl(rs)) {
            jdbcRs.beforeFirst();
            while (jdbcRs.next()) {
                System.out.print("\nStudent ID: " + jdbcRs.getString("studentid"));
                System.out.print("\nBatch: " + jdbcRs.getString("batch"));
                System.out.println("\nName: " + jdbcRs.getString("name"));
                System.out.print("\n=================");
            }
        }
        rs.close();
        stmt.close();
        conn.close();
    }
}
/*
jdbcRowset (Connected RowSet): nó gần như tương tự với ResultSet.
Vậy tại sao vẫn dùng : nó là một thành phần của JavaBeans. 
Nó được sử dụng như một thành phần bao bọc các Driver duy trì cho kết nối.
 */
