/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session01_session02_baitap;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.JoinRowSetImpl;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author June
 */
public final class session01_session02_baitap3 extends javax.swing.JFrame {

    /**
     * Creates new form session01_session02_baitap3
     */
    Vector vColumn;
    Vector vData;
    static CachedRowSet cachedRowSetClass, cachedRowSetStudent;
    static JoinRowSet joinRowSetClassAndStudent;

    void initClassTable() throws ClassNotFoundException, SQLException {
        vColumn = new Vector();
        vData = new Vector();
        vColumn.add("Class ID");
        vColumn.add("Class name");
        Class.forName(DBparameters1.JDBC_DRIVER);
        cachedRowSetClass = new CachedRowSetImpl();
        cachedRowSetClass.setUrl(DBparameters1.DB_URL + DBparameters1.DATABASENAME);
        cachedRowSetClass.setUsername(DBparameters1.USER);
        cachedRowSetClass.setPassword(DBparameters1.PASS);
        String sql = "select classid, classname from class";
        cachedRowSetClass.setCommand(sql);
        cachedRowSetClass.execute();
        while (cachedRowSetClass.next()) {
            Vector vRow = new Vector();
            vRow.add(cachedRowSetClass.getString("classid"));
            vRow.add(cachedRowSetClass.getString("classname"));
            vData.add(vRow);
        }
        DefaultTableModel model = new DefaultTableModel(vData, vColumn);
        tblClass.setModel(model);
    }

    void initStudentTable() throws ClassNotFoundException, SQLException {
        vColumn = new Vector();
        vData = new Vector();
        vColumn.add("Student ID");
        vColumn.add("Student name");
        vColumn.add("Sex");
        vColumn.add("Class ID");
        Class.forName(DBparameters1.JDBC_DRIVER);
        cachedRowSetStudent = new CachedRowSetImpl();
        cachedRowSetStudent.setUrl(DBparameters1.DB_URL + DBparameters1.DATABASENAME);
        cachedRowSetStudent.setUsername(DBparameters1.USER);
        cachedRowSetStudent.setPassword(DBparameters1.PASS);
        String sql = "select studentid, studentname, sex, classid from student";
        cachedRowSetStudent.setCommand(sql);
        cachedRowSetStudent.execute();
        while (cachedRowSetStudent.next()) {
            Vector vRow = new Vector();
            vRow.add(cachedRowSetStudent.getString("studentid"));
            vRow.add(cachedRowSetStudent.getString("studentname"));
            boolean sex = cachedRowSetStudent.getBoolean("sex");
            if (sex) {
                vRow.add("Male");
            } else {
                vRow.add("Female");
            }
            vRow.add(cachedRowSetStudent.getString("classid"));
            vData.add(vRow);
        }
        DefaultTableModel model = new DefaultTableModel(vData, vColumn);
        tblStudent.setModel(model);
    }

    void initClassAndStudentTable() throws SQLException {
        vColumn = new Vector();
        vData = new Vector();
        vColumn.add("Student ID");
        vColumn.add("Student name");
        vColumn.add("Sex");
        vColumn.add("Class name");
        joinRowSetClassAndStudent = new JoinRowSetImpl();
        joinRowSetClassAndStudent.addRowSet(cachedRowSetClass, "classid");
        joinRowSetClassAndStudent.addRowSet(cachedRowSetStudent, "classid");
        while (joinRowSetClassAndStudent.next()) {
            Vector vRow = new Vector();
            vRow.add(joinRowSetClassAndStudent.getString("studentid"));
            vRow.add(joinRowSetClassAndStudent.getString("studentname"));
            boolean sex = joinRowSetClassAndStudent.getBoolean("sex");
            if (sex) {
                vRow.add("Male");
            } else {
                vRow.add("Female");
            }
            vRow.add(joinRowSetClassAndStudent.getString("classname"));
            vData.add(vRow);
        }
        Collections.reverse(vData);
        DefaultTableModel model = new DefaultTableModel(vData, vColumn);
        tblClassAndStudent.setModel(model);
    }

    void initListClassID() throws SQLException {
        Vector<String> listClass = new Vector<>();
        cachedRowSetClass.beforeFirst();
        while (cachedRowSetClass.next()) {
            listClass.add(cachedRowSetClass.getString("classid"));
        }
        listClassID.setListData(listClass);
    }

    public session01_session02_baitap3() throws ClassNotFoundException, SQLException {
        initComponents();
        initClassTable();
        initStudentTable();
        initClassAndStudentTable();
        initListClassID();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClass = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStudent = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listClassID = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblClassAndStudent = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Show Class and Student Information");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Class Information"));

        tblClass.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Class ID", "Class Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblClass);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Information"));

        tblStudent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Student ID", "Student name", "Sex", "Class ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblStudent);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Display a list of students by class"));

        listClassID.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "1", "2", "3", "4", "5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listClassID.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listClassID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                listClassIDMouseReleased(evt);
            }
        });
        listClassID.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listClassIDValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(listClassID);

        tblClassAndStudent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Student ID", "Student name", "Sex", "Class name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblClassAndStudent);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(220, 220, 220))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void listClassIDValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listClassIDValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_listClassIDValueChanged

    private void listClassIDMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listClassIDMouseReleased
        // TODO add your handling code here:
//        System.out.println(listClassID.getSelectedValue());
        tblClassAndStudent.setModel(new DefaultTableModel());
        try {
            vColumn = new Vector();
            vData = new Vector();
            vColumn.add("Student ID");
            vColumn.add("Student name");
            vColumn.add("Sex");
            vColumn.add("Class name");
            joinRowSetClassAndStudent.beforeFirst();
            while (joinRowSetClassAndStudent.next()) {
                if (joinRowSetClassAndStudent.getString("classid").equalsIgnoreCase(listClassID.getSelectedValue())) {
                    Vector vRow = new Vector();
                    vRow.add(joinRowSetClassAndStudent.getString("studentid"));
                    vRow.add(joinRowSetClassAndStudent.getString("studentname"));
                    boolean sex = joinRowSetClassAndStudent.getBoolean("sex");
                    if (sex) {
                        vRow.add("Male");
                    } else {
                        vRow.add("Female");
                    }
                    vRow.add(joinRowSetClassAndStudent.getString("classname"));
                    vData.add(vRow);
                }
            }
            Collections.reverse(vData);
            DefaultTableModel model = new DefaultTableModel(vData, vColumn);
            tblClassAndStudent.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(session01_session02_baitap3.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_listClassIDMouseReleased

    /**
     * @param args the command line arguments
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
            java.util.logging.Logger.getLogger(session01_session02_baitap3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(session01_session02_baitap3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(session01_session02_baitap3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(session01_session02_baitap3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                    new session01_session02_baitap3().setVisible(true);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(session01_session02_baitap3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList<String> listClassID;
    private javax.swing.JTable tblClass;
    private javax.swing.JTable tblClassAndStudent;
    private javax.swing.JTable tblStudent;
    // End of variables declaration//GEN-END:variables
}
