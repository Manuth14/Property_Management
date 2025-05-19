/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package lk.sky_land.dialog;


import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lk.propertymanagement.Connection.MySQL;
import lk.skyland.idGenerator.ConCompanyIdGenerator;


/** 
 *
 * @author hiran
 */
public class ConstructorCompanyManage extends javax.swing.JPanel {

    HashMap<String, String> cityMap = new HashMap<>();

    private ConstructorRegistration t;

    public ConstructorCompanyManage(ConstructorRegistration t) {
        initComponents();

        this.t = t;
        loadCities();
        setCompanyId();
        loadConComapany("company.id", "ASC", "");
    }

    private void loadCities() {
        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `city`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("city_name"));
                cityMap.put(resultSet.getString("city_name"), resultSet.getString("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            cityBox.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCompanyId() {
        String generateNewConCompanyId = ConCompanyIdGenerator.generateNewConCompanyId();
        conComIdField.setText(generateNewConCompanyId);

    }

    private void loadConComapany(String column, String orderby, String comName) {

        try {
            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `company` INNER JOIN `city` ON"
                    + " `company`.`city_id`=`city`.`id` WHERE `company_name` LIKE '%" + comName + "%' ORDER BY " + column + " " + orderby + "");

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("company.id"));
                vector.add(resultSet.getString("company.company_name"));
                vector.add(resultSet.getString("city.city_name"));
                vector.add(resultSet.getString("company.hotline"));
                vector.add(resultSet.getString("company.email"));
                vector.add(resultSet.getString("line_01"));
                vector.add(resultSet.getString("line_02"));
                
                model.addRow(vector);
            }

            jTable2.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void search() {
        String companySearchText = companySearchField.getText();

        if (companySearchField.getText().equals("Search")) {
            companySearchText = "";
        }
        
        if (conComSortComboBox.getSelectedIndex() == 0) {

            loadConComapany("company_name", "ASC", companySearchText);
        }
        if (conComSortComboBox.getSelectedIndex() == 1) {

            loadConComapany("company_name", "DESC", companySearchText);
        }
        if (conComSortComboBox.getSelectedIndex() == 2) {

            loadConComapany("company.id", "ASC", companySearchText);
        }
        if (conComSortComboBox.getSelectedIndex() == 3) {

            loadConComapany("company.id", "DESC", companySearchText);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ConComNameField = new javax.swing.JTextField();
        ConComHotline1Field = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ConComEmailField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        ConComLine1Field = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ConComLine2Field = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cityBox = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        conComIdField = new javax.swing.JLabel();
        addCityField = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        conComSortComboBox = new javax.swing.JComboBox<>();
        companySearchField = new javax.swing.JTextField();

        jPanel1.setPreferredSize(new java.awt.Dimension(764, 501));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Poppins", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("   Constructor Company Manage");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 102, 102), null));
        jLabel1.setOpaque(true);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        jLabel2.setText("Name");

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        jLabel3.setText("Hotline ");

        ConComNameField.setMinimumSize(new java.awt.Dimension(64, 28));

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        jLabel5.setText("Email");

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        jLabel6.setText("Line 1");

        jLabel7.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        jLabel7.setText("Line 2");

        jLabel8.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        jLabel8.setText("City");

        cityBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Clear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Poppins", 1, 15)); // NOI18N
        jLabel9.setText("Company ID");

        conComIdField.setFont(new java.awt.Font("Helvetica Neue", 0, 15)); // NOI18N
        conComIdField.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        conComIdField.setText("ComID");
        conComIdField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton4.setText("+");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(conComIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ConComHotline1Field, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(ConComEmailField, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(ConComLine1Field, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(ConComLine2Field)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(cityBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(ConComNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(333, 333, 333))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addCityField, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(conComIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(ConComNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(ConComHotline1Field, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ConComEmailField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ConComLine1Field)
                    .addComponent(jLabel6))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(ConComLine2Field, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cityBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addCityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(28, 28, 28)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        jLabel11.setFont(new java.awt.Font("Poppins", 0, 15)); // NOI18N
        jLabel11.setText("Sort");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lk/propertymanagement/Resourses/back2.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("Poppins", 0, 13)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Com ID", "Name", "City", "Hotline", "Email", "Line 1", "Line 2", "Address ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(145);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(140);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(140);
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(250);
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(250);
        jTable2.getColumnModel().getColumn(6).setPreferredWidth(250);
        jTable2.getColumnModel().getColumn(7).setPreferredWidth(80);
        jTable2.setAutoResizeMode(0);
        jTable2.setRowHeight(27);
        jTable2.setShowGrid(true);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
            jTable2.getColumnModel().getColumn(4).setResizable(false);
            jTable2.getColumnModel().getColumn(5).setResizable(false);
            jTable2.getColumnModel().getColumn(6).setResizable(false);
            jTable2.getColumnModel().getColumn(7).setResizable(false);
        }

        conComSortComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name ASC", "Name DESC", "Com ID ASC", "Com ID DESC" }));
        conComSortComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                conComSortComboBoxItemStateChanged(evt);
            }
        });

        companySearchField.setForeground(new java.awt.Color(102, 102, 102));
        companySearchField.setText("Search");
        companySearchField.setToolTipText("search");
        companySearchField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                companySearchFieldMouseClicked(evt);
            }
        });
        companySearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                companySearchFieldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(conComSortComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                .addComponent(companySearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(conComSortComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(companySearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked

        t.remove();
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String conComId = conComIdField.getText();
        String conComName = ConComNameField.getText();
        String conComHotline1 = ConComHotline1Field.getText();
        String conComEmail = ConComEmailField.getText();
        String conComLine1 = ConComLine1Field.getText();
        String conComLine2 = ConComLine2Field.getText();
        String conComCity = String.valueOf(cityBox.getSelectedItem());

        if (conComId.equals("ComID")) {
            JOptionPane.showMessageDialog(this, "Error in generating CompanyId.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (conComName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the company name",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (conComHotline1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Hotline 1",
                    "Warning", JOptionPane.WARNING_MESSAGE);

        } else if (!conComHotline1.matches("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Hotline 1",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (conComEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Add a email address");
        } else if (!conComEmail.matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")) {
            JOptionPane.showMessageDialog(this, "Please add a valid email address.");
        } else if (conComLine1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add a Company line 1",
                    conComId, HEIGHT);
        } else if (conComCity.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please add a city");
        } else {

            try {
                ResultSet resultSet1 = MySQL.executeSearch("SELECT * FROM `company` WHERE `company_name`='" + conComName + "' OR `hotline` = '" + conComHotline1 + "' OR `email`='" + conComEmail + "'");
                if (resultSet1.next()) {
                    JOptionPane.showMessageDialog(this, "This company Name or Hotline or Email alreaday used");
                } else {

                    MySQL.executeIUD("INSERT INTO `address`(`line_01`,`line_02`,`city_id`) "
                            + "VALUES('" + conComLine1 + "','" + conComLine2 + "','" + cityMap.get(conComCity) + "')");

                    ResultSet resultSet2 = MySQL.executeSearch("SELECT * FROM `address` WHERE `line_01`='" + conComLine1 + "' AND "
                            + "`line_02`='" + conComLine2 + "' AND "
                            + "`city_id`='" + cityMap.get(conComCity) + "'");

                    String aid = "";
                    if (resultSet2.next()) {
                        aid = resultSet2.getString("id");
                    }

                    MySQL.executeIUD("INSERT INTO `company`(`id`,`company_name`,`hotline`,`email`,`address_id`) "
                            + "VALUES('" + conComId + "','" + conComName + "','" + conComHotline1 + "','" + conComEmail + "','" + aid + "')");

                    //loadConComapany();
                    reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (!addCityField.getText().isEmpty()) {
            String addCiry = addCityField.getText();

            try {
                ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `city` WHERE `city_name`='" + addCiry + "' ");

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "" + addCiry + " is Already registerd",
                            "Warning", JOptionPane.WARNING_MESSAGE);

                    addCityField.setText("");
                    addCityField.grabFocus();
                } else {
                    MySQL.executeIUD("INSERT INTO `city`(`city_name`) VALUES('" + addCiry + "')");
                    loadCities();
                    addCityField.setText("");
                    cityBox.setSelectedItem(addCiry);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter the city", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void companySearchFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_companySearchFieldMouseClicked
        companySearchField.setText("");
    }//GEN-LAST:event_companySearchFieldMouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int row = jTable2.getSelectedRow();

        conComIdField.setText(String.valueOf(jTable2.getValueAt(row, 0)));
        ConComNameField.setText(String.valueOf(jTable2.getValueAt(row, 1)));
        ConComHotline1Field.setText(String.valueOf(jTable2.getValueAt(row, 3)));
        ConComEmailField.setText(String.valueOf(jTable2.getValueAt(row, 4)));
        ConComLine1Field.setText(String.valueOf(jTable2.getValueAt(row, 5)));
        ConComLine2Field.setText(String.valueOf(jTable2.getValueAt(row, 6)));
        cityBox.setSelectedItem(String.valueOf(jTable2.getValueAt(row, 2)));
        
        if (evt.getClickCount() == 2) {
            if (t != null) {
                t.getcomIDLabel().setText(String.valueOf(jTable2.getValueAt(row, 0)));
                t.getcomNameField().setText(String.valueOf(jTable2.getValueAt(row, 1)));
                t.getcomHotlineField().setText(String.valueOf(jTable2.getValueAt(row, 3)));
                t.getcomEmailField().setText(String.valueOf(jTable2.getValueAt(row, 4)));
                t.getcomCityField().setText(String.valueOf(jTable2.getValueAt(row, 2)));
                t.remove();
            }
            
        }

    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int row = jTable2.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row firstly.", "warning", JOptionPane.WARNING_MESSAGE);
        } else {
            String conComId = conComIdField.getText();
            String conComName = ConComNameField.getText();
            String conComHotline1 = ConComHotline1Field.getText();

            String conComEmail = ConComEmailField.getText();
            String conComLine1 = ConComLine1Field.getText();
            String conComLine2 = ConComLine2Field.getText();
            String addressId = String.valueOf(jTable2.getValueAt(row, 7));
            String conComCity = String.valueOf(cityBox.getSelectedItem());

            if (conComId.equals("ComID")) {
                JOptionPane.showMessageDialog(this, "Error in generating CompanyId.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (conComName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the company name",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (conComHotline1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the Hotline 1",
                        "Warning", JOptionPane.WARNING_MESSAGE);

            } else if (!conComHotline1.matches("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Hotline 1",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (conComEmail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Add a email address");
            } else if (!conComEmail.matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                    + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")) {
                JOptionPane.showMessageDialog(this, "Please add a valid email address.");
            } else if (conComLine1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please add a Company line 1",
                        conComId, HEIGHT);
            } else if (conComCity.equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please add a city");
            } else {

                try {
                    ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `company` WHERE (`company_name`='" + conComName + "' OR `email`='" + conComEmail + "' OR `hotline`='" + conComHotline1 + "') AND `id` != '" + conComId + "'");

                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(this, "This Company name or Email Already Registered", conComId, HEIGHT);
                    } else {
                        MySQL.executeIUD("UPDATE `address` SET `line_01`='" + conComLine1 + "',`line_02`='" + conComLine2 + "',`city_id`='" + cityMap.get(conComCity) + "' WHERE `id`='" + addressId + "'");

                        ResultSet resultSet1 = MySQL.executeSearch("SELECT * FROM `address` WHERE `line_01`='" + conComLine1 + "' AND "
                                + "`line_02`='" + conComLine2 + "' AND "
                                + "`city_id`='" + cityMap.get(conComCity) + "'");

                        String aid = "";
                        if (resultSet1.next()) {
                            aid = resultSet1.getString("id");
                        }

                        MySQL.executeIUD("UPDATE `company` SET `company_name`='" + conComName + "',`hotline`='" + conComHotline1 + "',`email`='" + conComEmail + "',"
                                + "`address_id`='" + addressId + "' WHERE `id`='" + conComId + "'");

                        JOptionPane.showMessageDialog(this, "Successfully Updated", "Success", JOptionPane.INFORMATION_MESSAGE);

                        // loadConComapany(); 
                        
                        reset();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        reset();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void companySearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_companySearchFieldKeyReleased
        search();
    }//GEN-LAST:event_companySearchFieldKeyReleased

    private void conComSortComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_conComSortComboBoxItemStateChanged
        search();
    }//GEN-LAST:event_conComSortComboBoxItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ConComEmailField;
    private javax.swing.JTextField ConComHotline1Field;
    private javax.swing.JTextField ConComLine1Field;
    private javax.swing.JTextField ConComLine2Field;
    private javax.swing.JTextField ConComNameField;
    private javax.swing.JTextField addCityField;
    private javax.swing.JComboBox<String> cityBox;
    private javax.swing.JTextField companySearchField;
    private javax.swing.JLabel conComIdField;
    private javax.swing.JComboBox<String> conComSortComboBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables

    private void reset() {

        ConComNameField.setText("");
        ConComHotline1Field.setText("");
        ConComEmailField.setText("");
        ConComLine1Field.setText("");
        ConComLine2Field.setText("");
        cityBox.setSelectedIndex(0);
        //loadConComapany();
        setCompanyId();
    }
}
