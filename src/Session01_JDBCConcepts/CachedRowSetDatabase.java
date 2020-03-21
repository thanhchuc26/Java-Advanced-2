/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session01_JDBCConcepts;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author June
 */
public class CachedRowSetDatabase {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://192.168.61.139;";
    static final String DATABASENAME = "databasename=softech1";
    static final String USER = "sa";
    static final String PASS = "123";

    public static void queryDatabase_by_CachedRowSet() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        CachedRowSet rowSet = new CachedRowSetImpl();
        rowSet.setUrl(DB_URL + DATABASENAME);
        rowSet.setUsername(USER);
        rowSet.setPassword(PASS);
        String sql = "select studentid, batch, name from student";
        rowSet.setCommand(sql);
        rowSet.execute();
        while (rowSet.next()) {
            // Generating cursor Moved event  
            System.out.println("Student ID: " + rowSet.getString("studentid"));
            System.out.println("Batch: " + rowSet.getString("batch"));
            System.out.println("Name: " + rowSet.getString("name"));
            System.out.println("====================================");
        }
    }

    public static void insertDatabase_by_CachedRowSet() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        CachedRowSet rowSet = new CachedRowSetImpl();
        rowSet.setUrl(DB_URL + DATABASENAME);
        rowSet.setUsername(USER);
        rowSet.setPassword(PASS);
        String sql = "select studentid, batch, name from student";
        rowSet.setCommand(sql);
        rowSet.execute();
        rowSet.moveToInsertRow();
        rowSet.updateString("studentid", "Softech999");
        rowSet.updateString("batch", "batch999");
        rowSet.updateString("name", "Softech");
        rowSet.insertRow();
        rowSet.moveToCurrentRow();
        rowSet.acceptChanges();
        System.out.println("Insert data successful");
    }
    public static void updateDatabase_by_CachedRowSet() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        CachedRowSet rowSet = new CachedRowSetImpl();
        rowSet.setUrl(DB_URL + DATABASENAME);
        rowSet.setUsername(USER);
        rowSet.setPassword(PASS);
        String sql = "select studentid, batch, name from student";
        rowSet.setCommand(sql);
        rowSet.execute();
        rowSet.first();
        rowSet.updateString("studentid", "softech998");
        rowSet.updateRow();
        rowSet.acceptChanges();
        System.out.println("Update data successful");
    }
public static void deleteDatabase_by_CachedRowSet() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        CachedRowSet rowSet = new CachedRowSetImpl();
        rowSet.setUrl(DB_URL + DATABASENAME);
        rowSet.setUsername(USER);
        rowSet.setPassword(PASS);
        String sql = "select studentid, batch, name from student";
        rowSet.setCommand(sql);
        rowSet.execute();
        rowSet.last();
        rowSet.deleteRow();
        rowSet.acceptChanges();
        System.out.println("Delete a row successful");
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        queryDatabase_by_CachedRowSet();
//        insertDatabase_by_CachedRowSet();
//        queryDatabase_by_CachedRowSet();
//        updateDatabase_by_CachedRowSet();
        deleteDatabase_by_CachedRowSet();
    }
}
/*
CachedRowSet không yêu cầu phải duy trì một kết nối với cơ sở dữ liệu. 
Nó chỉ thực hiện kết nối khi có submit và điều này sẽ làm tăng hiệu suất cho cơ sở dữ liệu. 
CachedRowSet có thể thực hiện các hoạt động sau đây trên cơ sở dữ liệu

Insert: Để thực hiện thêm một dòng dữ liệu vào bảng, chúng ta sử dụng phương thức moveToInsertRow()

Update: Phương thức updateRow() được sử dụng để cập nhật một dòng dữ liệu của bảng

Delete: Sử dụng phương thức deleteRow() để xoá một dòng dữ liệu của bảng.

Select: Một đối tượng CachedRowSet là có thể cuộn (scrollable) và điều này cho phép chúng ta 
duyệt bản ghi theo nhiều cách. Một khi con trỏ nằm tại dòng mong muốn, các phương thức getter 
có thể được gọi để lấy các giá trị của các cột.

CachedRowSet cho phép chúng ta duyệt dữ liệu với các thao tác:
First (bản ghi đầu tiên), 
Next (bản ghi kế tiếp), 
Previous (bản ghi trước) 
và Last (bản ghi cuối cùng). 
Bên dưới là các phương thức tương ứng.

Hiển thị bản ghi đầu tiên

crs.beforeFirst();
if(crs.next()) {
   crs.getDatatype(String columnLabel);
}
Hiển thị bản ghi kế tiếp (Sử dụng phương thức crs.isLast() 
để kiểm tra xem đang là bản ghi  cuối cùng chưa. Nếu trả về true thì đang ở bản ghi cuối cùng)

if(crs.next()) {
   crs.getDatatype(String columnLabel);
}
Hiển thị bản ghi trước (Sử dụng phương thức crs.isFirst() 
để kiểm tra xem đang là bản ghi đầu tiên chưa. Nếu trả về true thì đang ở đầu bản ghi đầu tiên)

if(crs.previous()) {
   crs.getDatatype(String columnLabel);
}
Hiển thị bản ghi cuối cùng

crs.afterLast();
if(crs.previous()) {
   crs.getDatatype(String columnLabel);
}
 */
