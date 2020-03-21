/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session01_session02_baitap;

import com.sun.rowset.JdbcRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.JdbcRowSet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author June
 */
public final class session01_session02_baitap1 extends javax.swing.JFrame {

    /**
     * Creates new form session01_session02_baitap1
     */
    Vector vColumn;
    Vector vData;

    void initClassTable() throws ClassNotFoundException, SQLException {
        vColumn = new Vector();
        vData = new Vector();
        Connection conn;
        Statement stmt;
        ResultSet rs;

        vColumn.add("No.");
        vColumn.add("Class ID");
        vColumn.add("Class name");

        Class.forName(DBparameters.JDBC_DRIVER);
        conn = DriverManager.getConnection(DBparameters.DB_URL + DBparameters.DATABASENAME + DBparameters.USER + DBparameters.PASS);
        String strSQL = "SELECT classID, classname FROM class";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(strSQL);
        ResultSetMetaData rsmt = rs.getMetaData();
        try (JdbcRowSet jdbcRs = new JdbcRowSetImpl(rs)) {
//            jdbcRs.beforeFirst();
            int countRow = 0;
            while (jdbcRs.next()) {
                Vector vRow = new Vector();
                Vector vtemp = new Vector();
                for (int j = 1; j <= rsmt.getColumnCount(); j++) {
                    vtemp.add(rs.getString(j));
                }
                countRow++;
                vRow.add(countRow);
                for (int i = 0; i < vtemp.size(); i++) {
                    vRow.add(vtemp.get(i));
                }
                vData.add(vRow);

            }
            DefaultTableModel model = new DefaultTableModel(vData, vColumn);
            tblClass.setModel(model);
        }
        rs.close();
        stmt.close();
        conn.close();
    }

    int getNumberStudent(String classID) throws ClassNotFoundException, SQLException {
        Connection conn;
        int countRow = 0;
        Class.forName(DBparameters.JDBC_DRIVER);
        conn = DriverManager.getConnection(DBparameters.DB_URL + DBparameters.DATABASENAME + DBparameters.USER + DBparameters.PASS);
        String sql = "SELECT * from student where classID like ? ";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    countRow++;
                }
            }
        }
        conn.close();
        return countRow;
    }

    void deleteTableRow(String table, String colum, String content) throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        Class.forName(DBparameters.JDBC_DRIVER);
        conn = DriverManager.getConnection(DBparameters.DB_URL + DBparameters.DATABASENAME + DBparameters.USER + DBparameters.PASS);
        stmt = conn.createStatement();
        String sql = "delete " + table + " where " + colum + "='" + content + "'";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
        JOptionPane.showMessageDialog(null, "Delete one class ID successful!", "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    void updateTableRow(String table, String colum1, String colum2, String content1, String content2) throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        int countRow = 0;
        boolean check = false;
        Class.forName(DBparameters.JDBC_DRIVER);
        conn = DriverManager.getConnection(DBparameters.DB_URL + DBparameters.DATABASENAME + DBparameters.USER + DBparameters.PASS);
        stmt = conn.createStatement();
        String sql = "SELECT * from " + table + " where classname like ? ";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, content1);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    countRow++;
                }
            }
        }
        conn.close();
        if (countRow > 0) {
            JOptionPane.showMessageDialog(null, "Class name " + content1 + " already exists. Can not update!", "Error", JOptionPane.INFORMATION_MESSAGE);
            check = false;
        } else {
            check = true;
        }
        if (check) {
            conn = DriverManager.getConnection(DBparameters.DB_URL + DBparameters.DATABASENAME + DBparameters.USER + DBparameters.PASS);
            stmt = conn.createStatement();
            sql = "update " + table + " set "
                    + colum1 + "='" + content1 + "' where " + colum2 + "='" + content2 + "'";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Update new class name successful!", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    void insertTableRow(String table, String content1, String content2) throws ClassNotFoundException, SQLException {
        Connection conn;
        Statement stmt;
        boolean checkClassID = false;
        boolean checkClassName = false;
        Class.forName(DBparameters.JDBC_DRIVER);
        conn = DriverManager.getConnection(DBparameters.DB_URL + DBparameters.DATABASENAME + DBparameters.USER + DBparameters.PASS);
        int countRow = 0;
        String sql = "SELECT * from " + table + " where classID like ? ";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, content1);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    countRow++;
                }
            }
        }
        conn.close();
        if (countRow > 0) {
            JOptionPane.showMessageDialog(null, "Class ID " + content1 + " already exists. Can not insert!", "Error", JOptionPane.INFORMATION_MESSAGE);
            checkClassID = false;
        } else {
            checkClassID = true;
        }
        countRow = 0;
        conn = DriverManager.getConnection(DBparameters.DB_URL + DBparameters.DATABASENAME + DBparameters.USER + DBparameters.PASS);
        sql = "SELECT * from " + table + " where classname like ? ";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, content2);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    countRow++;
                }
            }
        }
        conn.close();
        if (countRow > 0) {
            JOptionPane.showMessageDialog(null, "Class name " + content2 + " already exists. Can not insert!", "Error", JOptionPane.INFORMATION_MESSAGE);
            checkClassName = false;
        } else {
            checkClassName = true;
        }
        if (checkClassID && checkClassName) {
            conn = DriverManager.getConnection(DBparameters.DB_URL + DBparameters.DATABASENAME + DBparameters.USER + DBparameters.PASS);
            stmt = conn.createStatement();
            sql = "insert into " + table + " values "
                    + "('" + content1 + "','" + content2 + "')";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Insert new class ID successful!", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public session01_session02_baitap1() throws ClassNotFoundException, SQLException {
        initComponents();
        initClassTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtClassNameFilter = new javax.swing.JTextField();
        btnDisplay = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClass = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        jLabel2 = new javax.swing.JLabel();
        txtNumberStudent = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtClassNameModify = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Manager Class");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filter"));

        jLabel1.setText("Class Name:");

        btnDisplay.setText("Display");
        btnDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnInsert.setText("Insert");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtClassNameFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDisplay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnInsert)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtClassNameFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDisplay)
                    .addComponent(btnDelete)
                    .addComponent(btnInsert))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblClass.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "No.", "Class ID", "Class name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClass.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblClass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblClassMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblClass);
        if (tblClass.getColumnModel().getColumnCount() > 0) {
            tblClass.getColumnModel().getColumn(0).setPreferredWidth(0);
        }

        jLabel2.setText("Number of students");

        txtNumberStudent.setEditable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Modify"));

        jLabel3.setText("Class Name:");

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtClassNameModify)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtClassNameModify, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumberStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNumberStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblClassMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClassMouseReleased
        // TODO add your handling code here:
        int index = tblClass.getSelectedRow();
        try {
            txtNumberStudent.setText(Integer.toString(getNumberStudent(tblClass.getValueAt(index, 1).toString())));
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(session01_session02_baitap1.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtClassNameModify.setText(tblClass.getValueAt(index, 2).toString());
    }//GEN-LAST:event_tblClassMouseReleased

    @SuppressWarnings("empty-statement")
    private void btnDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayActionPerformed
        // TODO add your handling code here:
        Vector vTemp = new Vector();
        vTemp.addAll(vData);
        int i = 0;
        do {
            if (!vTemp.get(i).toString().toLowerCase().contains(txtClassNameFilter.getText())) {
                vTemp.remove(i);
            } else {
                i++;
            }
        } while (i < vTemp.size());
        DefaultTableModel model = new DefaultTableModel(vTemp, vColumn);
        tblClass.setModel(model);
    }//GEN-LAST:event_btnDisplayActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int index = tblClass.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(null, "Please choose an row you want to delete!", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Do you want to delete class ID " + tblClass.getValueAt(index, 1) + "?", "Comfirm Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (i == 0) {
                try {
                    if (getNumberStudent(tblClass.getValueAt(index, 1).toString()) != 0) {
                        JOptionPane.showMessageDialog(null, "This class has students. You can't delete!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        deleteTableRow("class", "classid", tblClass.getValueAt(index, 1).toString());
                        initClassTable();
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(session01_session02_baitap1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        int index = tblClass.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(null, "Please choose an row you want to update!", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (txtClassNameModify.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please input new class ID you want to update!", "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (txtClassNameModify.getText().length() > 10) {
                    JOptionPane.showMessageDialog(null, "Class name is only 10 characters!", "Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    try {
                        updateTableRow("class", "classname", "classID", txtClassNameModify.getText(), tblClass.getValueAt(index, 1).toString());
                        initClassTable();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(session01_session02_baitap1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        String strClassID = "";
        String strClassName = "";
        do {
            strClassID = JOptionPane.showInputDialog(null, "Please input class ID:", "Input class ID", JOptionPane.INFORMATION_MESSAGE);
            if (strClassID != null) {
                if (strClassID.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Class ID can't empty. Please reinput!", "Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (strClassID.length() > 10) {
                        JOptionPane.showMessageDialog(null, "Class name is only 10 characters. Please reinput!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }

        } while (true);
        while ((true) && (strClassID != null)) {
            strClassName = JOptionPane.showInputDialog(null, "Please input class name:", "Input class ID", JOptionPane.INFORMATION_MESSAGE);
            if (strClassName != null) {
                if (strClassName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Class ID can't empty. Please reinput!", "Error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (strClassName.length() > 10) {
                        JOptionPane.showMessageDialog(null, "Class name is only 10 characters. Please reinput!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        };
        if ((strClassID != null) && (strClassName != null)) {
            try {
                insertTableRow("class", strClassID, strClassName);
                initClassTable();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(session01_session02_baitap1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    /**
     * @param args the command line arguments
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws javax.swing.UnsupportedLookAndFeelException
     */
    public static void main(String args[]) throws InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(session01_session02_baitap1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(session01_session02_baitap1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(session01_session02_baitap1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(session01_session02_baitap1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException ex) {
            System.out.println("Exception : " + ex.getMessage());
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    new session01_session02_baitap1().setVisible(true);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(session01_session02_baitap1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDisplay;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClass;
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    private javax.swing.JTextField txtClassNameFilter;
    private javax.swing.JTextField txtClassNameModify;
    private javax.swing.JTextField txtNumberStudent;
    // End of variables declaration//GEN-END:variables
}
