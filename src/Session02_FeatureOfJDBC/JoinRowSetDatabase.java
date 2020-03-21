/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session02_FeatureOfJDBC;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.JoinRowSetImpl;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JoinRowSet;

/**
 *
 * @author June
 */
public class JoinRowSetDatabase {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://192.168.61.139;";
    static final String DATABASENAME = "databasename=softech1";
    static final String USER = "sa";
    static final String PASS = "123";

    static CachedRowSet crClass, crStudent;
    static PreparedStatement ps;

    public static void getClassInfo() throws SQLException {
        String sql = "Select batchid, batchname from batch";
        crClass = new CachedRowSetImpl();
        crClass.setUrl(DB_URL + DATABASENAME);
        crClass.setUsername(USER);
        crClass.setPassword(PASS);
        crClass.setCommand(sql);
        crClass.execute();
    }

    public static void getStudentInfo() throws SQLException {
        String sql = "Select studentid, batchid, name from student1";
        crStudent = new CachedRowSetImpl();
        crStudent.setUrl(DB_URL + DATABASENAME);
        crStudent.setUsername(USER);
        crStudent.setPassword(PASS);
        crStudent.setCommand(sql);
        crStudent.execute();
    }

    public static void getStudentBaseClassId() throws SQLException {
        JoinRowSet jrs = new JoinRowSetImpl();
        jrs.addRowSet(crClass, "batchid");
        jrs.addRowSet(crStudent, "batchid");
        while (jrs.next()) {
            System.out.println("Student id: " + jrs.getString("studentid"));
            System.out.println("Batch ID: " + jrs.getString("batchid"));
            System.out.println("Batch name: " + jrs.getString("batchname"));
            System.out.println("Name: " + jrs.getString("name"));
            System.out.println("=====================================");
        }
    }

    public static void queryDatabase_by_JoinRowSet() throws SQLException {
        getClassInfo();
        getStudentInfo();
        getStudentBaseClassId();
    }

    public static void main(String[] args) throws SQLException {
        queryDatabase_by_JoinRowSet();
    }
}

/*
JoinRowSet được sử dụng để kết nối nhiều RowSet trong database. 
Sử dụng JoinRowSet tương tự như việc kết nối giữa 2 bảng trong database 
phải thông qua điều kiện kết với mục đích truy vấn dữ liệu từ nhiều bảng.

Ví dụ chúng ta có 2 bảng batch và student1. Hai bảng này liên kết với nhau thông qua khoá ngoại là batch. 
Trong trường hợp này chúng ta sẽ có 2 RowSet, một cho bảng batch và một cho bảng student1. 
Bây giờ chúng ta sẽ thực hiện kết nối 2 RowSet này sử dụng JoinRowSet để có được thông tin của lớp và sinh viên.
 */
