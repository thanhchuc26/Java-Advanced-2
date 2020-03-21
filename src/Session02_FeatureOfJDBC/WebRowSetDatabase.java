/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session02_FeatureOfJDBC;

import com.sun.rowset.WebRowSetImpl;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.WebRowSet;

/**
 *
 * @author June
 */
public class WebRowSetDatabase {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://192.168.61.139;";
    static final String DATABASENAME = "databasename=softech1;";
    static final String USER = "user=sa;";
    static final String PASS = "password=123";

    public static void WebRowSetExporttoXML() throws ClassNotFoundException, SQLException, IOException {
        Connection conn;
        Statement stmt;
        Class.forName(JDBC_DRIVER);
        System.out.println("Dang ket noi toi co so du lieu ...");
        conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);
        System.out.println("Tao cac lenh truy van SQL ...");
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql;
        sql = "SELECT batchid, batchname FROM batch";
        FileWriter writer;
        try (ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Dang tao file xml ...");
            writer = new FileWriter("class.xml");
            WebRowSet wrs = new WebRowSetImpl();
            wrs.writeXml(rs, writer);
            System.out.println("Tao file xml thanh cong ...");
        }
        writer.close();
        stmt.close();
        conn.close();
    }

    public static void WebRowSetReadFromXML() throws SQLException, FileNotFoundException, IOException {
        try (WebRowSet receiver = new WebRowSetImpl(); FileReader fReader = new FileReader("class.xml")) {
            receiver.readXml(fReader);
            receiver.beforeFirst();
            while (receiver.next()) {
                System.out.println("Class ID: " + receiver.getString("batchid"));
                System.out.println("Class name: " + receiver.getString("batchname"));
                System.out.println("====================================");
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
        WebRowSetExporttoXML();
        WebRowSetReadFromXML();
    }
}

/*
WebRowSet - extends từ CachedRowSet trợ giúp việc chuyển đổi rowset data tới XML hoặc ngược lại theo đúng XML schema, 
trên WebRowSet chúng ta hoàn toàn có thể làm việc như với một rowset object bình thường 
mặc dù nguồn dữ liệu đó đuợc load từ xml và chưa dính dáng gì tới database
 */
