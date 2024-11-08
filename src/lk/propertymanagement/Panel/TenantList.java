/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package lk.propertymanagement.Panel;

/**
 *
 * @author manut
 */
public class TenantList extends javax.swing.JPanel {

    /**
     * Creates new form TenantList
     */
    public TenantList() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        addTenantButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        tenantSort = new javax.swing.JLabel();
        tenantSortComboBox = new javax.swing.JComboBox<>();
        tenantName = new javax.swing.JLabel();
        teanntNameField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tenantListTable = new javax.swing.JTable();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 1, 24)); // NOI18N
        jLabel1.setText("Tenant");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        addTenantButton.setFont(new java.awt.Font("Poppins Medium", 1, 14)); // NOI18N
        addTenantButton.setText("Add Tenant");
        add(addTenantButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 210, 30));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tenant List", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Poppins Medium", 1, 14))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tenantSort.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        tenantSort.setText("SORT BY :");
        jPanel1.add(tenantSort, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, 30));

        tenantSortComboBox.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        tenantSortComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name ASC", "Name DESC", "Type ASC", "Type DESC" }));
        tenantSortComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(tenantSortComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 150, 30));

        tenantName.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        tenantName.setText("Tenant Name :");
        jPanel1.add(tenantName, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, -1, 30));

        teanntNameField.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jPanel1.add(teanntNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 40, 150, 30));

        tenantListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "First Name", "Last Name", "Email", "Mobile", "NIC", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tenantListTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 100, 740, 490));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 760, 600));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTenantButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField teanntNameField;
    private javax.swing.JTable tenantListTable;
    private javax.swing.JLabel tenantName;
    private javax.swing.JLabel tenantSort;
    private javax.swing.JComboBox<String> tenantSortComboBox;
    // End of variables declaration//GEN-END:variables
}
