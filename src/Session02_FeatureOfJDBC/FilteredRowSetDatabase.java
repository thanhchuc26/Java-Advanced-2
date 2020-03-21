/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session02_FeatureOfJDBC;

import com.sun.rowset.FilteredRowSetImpl;
import java.sql.SQLException;
import javax.sql.RowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;

/**
 *
 * @author June
 */
class filterClassID implements Predicate {

    private final String batchFilter;

    public filterClassID(String classID) {
        this.batchFilter = classID;
    }

    @Override
    public boolean evaluate(RowSet rowset) {
        if (rowset == null) {
            return false;
        }

        FilteredRowSet frs = (FilteredRowSet) rowset;
        boolean evaluation = false;
        try {
            String classIDValue = frs.getString("batch");
            if (classIDValue.equals(this.batchFilter)) {
                evaluation = true;
            }
        } catch (SQLException e) {
            return false;
        }
        return evaluation;
    }

    @Override
    public boolean evaluate(Object o, int i) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean evaluate(Object o, String string) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

public class FilteredRowSetDatabase {
    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://localhost;";
    static final String DATABASENAME = "databasename=softech1";
    static final String USER = "sa";
    static final String PASS = "123";

    public static void queryDatabase_by_FilteredRowSet() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        FilteredRowSet rowSet = new FilteredRowSetImpl();
        rowSet.setUrl(DB_URL + DATABASENAME);
        rowSet.setUsername(USER);
        rowSet.setPassword(PASS);
        String sql = "select studentid, batch, name from student";
        rowSet.setCommand(sql);
        rowSet.execute();
        System.out.println("--- Unfiltered RowSet: ---");
        while (rowSet.next()) {
            // Generating cursor Moved event  
            System.out.println("Student ID: " + rowSet.getString("studentid"));
            System.out.println("Batch: " + rowSet.getString("batch"));
            System.out.println("Name: " + rowSet.getString("name"));
            System.out.println("====================================");
        }
        filterClassID myfilterClassID = new filterClassID("batch130");
        rowSet.beforeFirst();
        rowSet.setFilter(myfilterClassID);
        System.out.println("--- Filtered RowSet: ---");
        while (rowSet.next()) {
            // Generating cursor Moved event  
            System.out.println("Student ID: " + rowSet.getString("studentid"));
            System.out.println("Batch: " + rowSet.getString("batch"));
            System.out.println("Name: " + rowSet.getString("name"));
            System.out.println("====================================");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        queryDatabase_by_FilteredRowSet();
    }
}

/*
FilteredRowSet được sử dụng để lấy về tập giá trị từ RowSet dựa vào điều kiện lọc 
tương tự như mệnh đề where trong câu lệnh Select.
 */
