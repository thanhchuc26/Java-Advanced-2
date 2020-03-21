/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session01_JDBCConcepts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author June
 */
public class Transaction_Commit_Rollback_Example {

    static void queryDatabase() throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        Class.forName(database.JDBC_DRIVER);
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        stmt = conn.createStatement();
        String sql = "SELECT num FROM test";
        try (ResultSet rs = stmt.executeQuery(sql)) {
            int count = 0;
            while (rs.next()) {
                count++;
                int num = rs.getInt("num");
                System.out.print("\nNum " + count + ": " + num);
            }
        }
        stmt.close();
        conn.close();
    }

    static void commitExample() throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        Class.forName(database.JDBC_DRIVER);
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        conn.setAutoCommit(false);
        System.out.println("Tao cac lenh truy van SQL ...");
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        // Chen mot hang vao trong bang student
        System.out.println("\nChen mot hang ...");
        String sql = "INSERT INTO test "
                + "VALUES (3)";
        stmt.executeUpdate(sql);
        // Chen them mot hang vao trong bang student
        sql = "INSERT INTO test "
                + "VALUES ('a')";
        stmt.executeUpdate(sql); //error vì dữ liệu nhập vào là ký tự trong khi table là kiểu số (int)->dữ liệu ko được cập nhật
        conn.commit();
    }

    static void commitRollbackExample() throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        Class.forName(database.JDBC_DRIVER);
        conn = DriverManager.getConnection(database.DB_URL + database.DATABASENAME + database.USER + database.PASS);
        conn.setAutoCommit(false);
        stmt = conn.createStatement();
        while (true) {
            System.out.print("Nhập một số nguyên bất kỳ để lưu vào cơ sở dữ liệu (0: thoat): ");
            Scanner input = new Scanner(System.in);
            int num = 0;
            try {
                num = input.nextInt();
            } catch (InputMismatchException error) {
                System.out.println("Đã xảy ra lỗi trong quá trình nhập một số nguyên. CHương trình sẽ rollback dữ liệu!");
                conn.rollback();
            }
            if (num == 0) {
                break;
            } else {
                String sql = "INSERT INTO test values(" + num + ")";
                stmt.executeUpdate(sql);
            }
        }

        conn.commit();
        stmt.close();
        conn.close();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        System.out.print("Database trước khi thêm dữ liệu: ");
        queryDatabase();
        System.out.println("");
        System.out.println("");
//        commitExample();
        commitRollbackExample();
        System.out.print("Database sau khi thêm dữ liệu: ");
        queryDatabase();
        System.out.println("");
    }
}

/*
commit(): đảm bảo tính toàn vẹn của dữ liệu. Nếu xảy ra lỗi (exception)
khi thao tác với cơ sở dữ liệu thì cơ sở dữ liệu sẽ không được cập nhật dữ liệu mới

rollback(): muốn xóa các thay đổi đã được thực hiện trước đó để quay về trạng thái 
trước khi thực hiện thay đổi khi thấy rằng có lỗi xảy ra, bạn sử dụng phương thức rollback().
 */
