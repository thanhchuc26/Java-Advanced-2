/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session06_HandlingXMLData;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author June
 */
public class SQLXML {
    // Ten cua driver va dia chi URL cua co so du lieu

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://192.168.61.139;";

    //  Ten database, nguoi dung va mat khau cua co so du lieu
    static final String DATABASENAME = "databaseName=session06;";
    static final String USER = "user=sa;";
    static final String PASS = "password=123";

    public static void create_SQLXML() throws SQLException {
        Connection conn = null;
        Statement stmtDetails = null;
        try {
            // Buoc 2: Dang ky Driver
            Class.forName(JDBC_DRIVER);

            // Buoc 3: Mo mot ket noi
            System.out.println("Dang ket noi toi co so du lieu ...");
//      conn = DriverManager.getConnection("jdbc:sqlserver://192.168.61.139;databaseName=quanlymaybay;user=sa;password=123");
            conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

            // Buoc 4: Thuc thi truy van
            System.out.println("Tao table ...");
            stmtDetails = conn.createStatement();
            String strSQL = "Create table Invoice(Id int, OrderDetails xml)";
            boolean result = stmtDetails.execute(strSQL);
            java.sql.SQLXML order = conn.createSQLXML();
            order.setString("<order id =\"O101\">"
                    + "<items>"
                    + "<item id=\"100\">"
                    + "<name>Book</name>"
                    + "<quantity>5</quantity>"
                    + "<unitprice>15.95</unitprice>"
                    + "</item>"
                    + "<item id=\"2\">"
                    + "<name>DVD Player</name>"
                    + "<quantity>3</quantity>"
                    + "<unitprice>103.95</unitprice>"
                    + "</item>"
                    + "</items>"
                    + "</order>");
            String SQLString = "Insert into Invoice(Id, OrderDetails) values(?,?)";
            PreparedStatement pstDetails = conn.prepareStatement(SQLString);
            pstDetails.setInt(1, 1000);
            pstDetails.setSQLXML(2, order);
            pstDetails.executeUpdate();
            order.free();

            // Buoc 6: Don sach moi truong va giai phong resource
            stmtDetails.close();
            conn.close();
        } catch (SQLException se) {
            // Xu ly cac loi cho JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Xu ly cac loi cho Class.forName
            e.printStackTrace();
        } finally {
            // Khoi finally duoc su dung de dong cac resource
            try {
                if (stmtDetails != null) {
                    stmtDetails.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }// Ket thuc khoi finally
        }// Ket thuc khoi try
    }

    public static void read_SQLXML() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstDetails = null;
        try {

            // Buoc 2: Dang ky Driver
            Class.forName(JDBC_DRIVER);

            // Buoc 3: Mo mot ket noi
            System.out.println("Dang ket noi toi co so du lieu ...");
//      conn = DriverManager.getConnection("jdbc:sqlserver://192.168.61.139;databaseName=quanlymaybay;user=sa;password=123");
            conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

            // Buoc 4: Thuc thi truy van
            System.out.println("Tao cac lenh truy van SQL ...");

            String SQLString = "SELECT * FROM Invoice WHERE Id=?";
            pstDetails = conn.prepareStatement(SQLString);
            pstDetails.setInt(1, 1000);
            ResultSet rsDetails = pstDetails.executeQuery();

            while (rsDetails.next()) {
                java.sql.SQLXML sqlXML = rsDetails.getSQLXML(2);
                System.out.println(sqlXML.getString());
            }

            // Buoc 6: Don sach moi truong va giai phong resource
            pstDetails.close();
            conn.close();

        } catch (SQLException se) {
            // Xu ly cac loi cho JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Xu ly cac loi cho Class.forName
            e.printStackTrace();
        } finally {
            // Khoi finally duoc su dung de dong cac resource
            try {
                if (pstDetails != null) {
                    pstDetails.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }// Ket thuc khoi finally
        }// Ket thuc khoi try
    }

    public static void update_SQLXML() {
        Connection conn = null;
        Statement stmtDetails = null;
        try {
            // Buoc 2: Dang ky Driver
            Class.forName(JDBC_DRIVER);

            // Buoc 3: Mo mot ket noi
            System.out.println("Dang ket noi toi co so du lieu ...");
//      conn = DriverManager.getConnection("jdbc:sqlserver://192.168.61.139;databaseName=quanlymaybay;user=sa;password=123");
            conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

            // Buoc 4: Thuc thi truy van
            System.out.println("Tao cac lenh truy van SQL ...");
            stmtDetails = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            java.sql.SQLXML order = conn.createSQLXML();
            order.setString("<order id=\"O101\">"
                    + "<items>"
                    + "<item id=\"3\">"
                    + "<name>MP3 Player</name>"
                    + "<quantity>3</quantity>"
                    + "<unitprice>12.45</unitprice>"
                    + "</item>"
                    + "</items>"
                    + "</order>");
            ResultSet rsDetails = stmtDetails.executeQuery("SELECT * from Invoice");
            rsDetails.moveToInsertRow();
            rsDetails.updateInt(1, 3);
            rsDetails.updateSQLXML(2, order);
            rsDetails.insertRow();
            while (rsDetails.next()) {
                java.sql.SQLXML sqlXML = rsDetails.getSQLXML(2);
                System.out.println("OrderDetails: " + sqlXML.getString());
            }

            // Buoc 6: Don sach moi truong va giai phong resource
            stmtDetails.close();
            conn.close();
        } catch (SQLException se) {
            // Xu ly cac loi cho JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Xu ly cac loi cho Class.forName
            e.printStackTrace();
        } finally {
            // Khoi finally duoc su dung de dong cac resource
            try {
                if (stmtDetails != null) {
                    stmtDetails.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }// Ket thuc khoi finally
        }// Ket thuc khoi try
    }

    public static void read_SQLXML_element() {
        Connection conn = null;
        Statement stmtDetails = null;
        try {
            // Buoc 2: Dang ky Driver
            Class.forName(JDBC_DRIVER);

            // Buoc 3: Mo mot ket noi
            System.out.println("Dang ket noi toi co so du lieu ...");
//      conn = DriverManager.getConnection("jdbc:sqlserver://192.168.61.139;databaseName=quanlymaybay;user=sa;password=123");
            conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);

            // Buoc 4: Thuc thi truy van
            System.out.println("Tao cac lenh truy van SQL ...");
            stmtDetails = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            XMLStreamReader streamReader = null;
            ResultSet rsDetails = stmtDetails.executeQuery("Select * from Invoice");
            rsDetails.next();
//            rsDetails.next();
            java.sql.SQLXML sqlXML = rsDetails.getSQLXML(2);
            InputStream bStream = sqlXML.getBinaryStream();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            streamReader = factory.createXMLStreamReader(bStream);
            while (streamReader.hasNext()) {
                int parseEvent = streamReader.next();
                System.out.println("" + parseEvent);
            }

            // Buoc 6: Don sach moi truong va giai phong resource
            stmtDetails.close();
            conn.close();
        } catch (SQLException se) {
            // Xu ly cac loi cho JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Xu ly cac loi cho Class.forName
            e.printStackTrace();
        } finally {
            // Khoi finally duoc su dung de dong cac resource
            try {
                if (stmtDetails != null) {
                    stmtDetails.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }// Ket thuc khoi finally
        }// Ket thuc khoi try
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        create_SQLXML();
//        read_SQLXML();
//        update_SQLXML();
        read_SQLXML_element();
    }
}
