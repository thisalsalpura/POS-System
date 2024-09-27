/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;
import javax.swing.table.DefaultTableModel;
import model.LoggerUtils;
import model.MySQL;
import model.customizedTable;
import raven.toast.Notifications;

/**
 *
 * @author User
 */
public class Supplier_Registration_Panel extends javax.swing.JPanel {

    private static Home home = new Home();
    private String companyId;
    private static Logger logger = LoggerUtils.getLogger();

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * Creates new form Supplier_Registration_Panel
     */
    public Supplier_Registration_Panel() {
        initComponents();
        init();
        styleTheTextFieldsAndButtons();
        loadSuppliers("first_name", "ASC", "");

        // set notification position
        Notifications.getInstance();

        // disable buttons
        update.setEnabled(false);

        total_grns.setText("0");
        pending_payments.setText("0");

        // customize the table
        customizedTable.TableStyle(jTable1);
        customizedTable.centeredValues(jTable1);
    }

    // set company name
    public void setCompanyName(String companyName) {
        company_name.setText(companyName);
    }

    // clear company name
    public void clearCompany() {
        company_name.setText("");
    }

    private void loadSuppliers(String column, String orderby, String email) {

        try {

            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `supplier` INNER JOIN `gender` ON `supplier`.`gender_id` = `gender`.`id` "
                    + "INNER JOIN `supplier's_company` ON `supplier`.`supplier's_company_id` = `supplier's_company`.`id` WHERE `email` LIKE '%" + email + "%' ORDER BY `" + column + "` " + orderby + ";");

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("email"));
                vector.add(resultSet.getString("first_name"));
                vector.add(resultSet.getString("last_name"));
                vector.add(resultSet.getString("mobile"));
                vector.add(resultSet.getString("gender.name"));
                vector.add(resultSet.getString("supplier's_company.name"));

                dtm.addRow(vector);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Load Suppliers Error in the Suppliers Registration!", e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Load Suppliers Error in the Suppliers Registration!", e);
        }

    }

    // style the text fields and buttons
    private void styleTheTextFieldsAndButtons() {
        email.putClientProperty("JComponent.roundRect", true);
        email.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- john@gmail.com");
        email.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        email.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        firstname.putClientProperty("JComponent.roundRect", true);
        firstname.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- John");
        firstname.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        firstname.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        lastname.putClientProperty("JComponent.roundRect", true);
        lastname.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- Doely");
        lastname.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        lastname.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        mobile.putClientProperty("JComponent.roundRect", true);
        mobile.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- 0712345678");
        mobile.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        mobile.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        create.putClientProperty("JButton.buttonType", "roundRect");
        update.putClientProperty("JButton.buttonType", "roundRect");
        reset.putClientProperty("JButton.buttonType", "roundRect");
        selectCompany.putClientProperty("JButton.buttonType", "roundRect");

        searchId.putClientProperty("JComponent.roundRect", true);
        searchId.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search by Email Address");
        searchId.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");

        jComboBox1.putClientProperty("JComponent.roundRect", true);
    }

    private void init() {
        // icons, logos & images load
        FlatSVGIcon back = new FlatSVGIcon("resources//backTo.svg", 38, 34);
        back_label.setIcon(back);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        genderButtonGroup = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        backLogo = new javax.swing.JPanel();
        back_label = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        firstname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        mobile = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        create = new javax.swing.JButton();
        update = new javax.swing.JButton();
        lastname = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        reset = new javax.swing.JButton();
        email = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        selectCompany = new javax.swing.JButton();
        company_name = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        searchId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        total_grns = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        pending_payments = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1260, 1222));

        backLogo.setBackground(new java.awt.Color(121, 203, 96));
        backLogo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        backLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backLogobackToHome(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Back");

        javax.swing.GroupLayout backLogoLayout = new javax.swing.GroupLayout(backLogo);
        backLogo.setLayout(backLogoLayout);
        backLogoLayout.setHorizontalGroup(
            backLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backLogoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(back_label, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        backLogoLayout.setVerticalGroup(
            backLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
            .addComponent(back_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(121, 203, 96), 3, true));
        jPanel3.setPreferredSize(new java.awt.Dimension(290, 507));

        jLabel1.setBackground(new java.awt.Color(121, 203, 96));
        jLabel1.setFont(new java.awt.Font("Retro Signed", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTER");
        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 2, true));
        jLabel1.setOpaque(true);

        jPanel7.setBackground(new java.awt.Color(215, 249, 204));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 2, true));

        jLabel4.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText(" First Name");

        firstname.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 17)); // NOI18N
        firstname.setForeground(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText(" Mobile");

        mobile.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 17)); // NOI18N
        mobile.setForeground(new java.awt.Color(0, 0, 0));

        jLabel8.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText(" Gender");

        genderButtonGroup.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 18)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton1.setText("Male");
        jRadioButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jRadioButton1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRadioButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jRadioButton1.setIconTextGap(25);
        jRadioButton1.setActionCommand("1");

        genderButtonGroup.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 18)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton2.setText("Female");
        jRadioButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jRadioButton2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRadioButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jRadioButton2.setIconTextGap(25);
        jRadioButton2.setActionCommand("2");

        create.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 22)); // NOI18N
        create.setForeground(new java.awt.Color(0, 0, 0));
        create.setText("Create Account");
        create.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createActionPerformed(evt);
            }
        });

        update.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 22)); // NOI18N
        update.setForeground(new java.awt.Color(0, 0, 0));
        update.setText("Update Account");
        update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        lastname.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 17)); // NOI18N
        lastname.setForeground(new java.awt.Color(0, 0, 0));

        jLabel11.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText(" Last Name");

        reset.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 22)); // NOI18N
        reset.setForeground(new java.awt.Color(0, 0, 0));
        reset.setText("Reset");
        reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        email.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 17)); // NOI18N
        email.setForeground(new java.awt.Color(0, 0, 0));

        jLabel13.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText(" Email");

        jPanel12.setBackground(new java.awt.Color(204, 229, 255));
        jPanel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(121, 203, 96), 2, true));

        jLabel9.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Company Name :-");

        selectCompany.setBackground(new java.awt.Color(215, 249, 204));
        selectCompany.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 24)); // NOI18N
        selectCompany.setForeground(new java.awt.Color(0, 0, 0));
        selectCompany.setText("Select Company");
        selectCompany.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectCompany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectCompanyActionPerformed(evt);
            }
        });

        company_name.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        company_name.setForeground(new java.awt.Color(0, 0, 0));
        company_name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(company_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(selectCompany, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(selectCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(company_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(update, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(create, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mobile, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(firstname, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lastname, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(email, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(firstname, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lastname, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(mobile, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(create, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel4.setBackground(new java.awt.Color(121, 203, 96));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(121, 203, 96));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));

        jLabel10.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("SEARCH ACCOUNTS");

        searchId.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 18)); // NOI18N
        searchId.setForeground(new java.awt.Color(0, 0, 0));
        searchId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchIdKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText(" SORT BY :-");

        jComboBox1.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Supplier Name Ascending Order", "Supplier Name Descending Order", "Company Name Ascending Order", "Company Name Descending Order" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, 0, 491, Short.MAX_VALUE))
                    .addComponent(searchId, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(searchId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        jPanel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));

        jTable1.setBackground(new java.awt.Color(215, 249, 204));
        jTable1.setFont(new java.awt.Font("JetBrains Mono Medium", 0, 12)); // NOI18N
        jTable1.setForeground(new java.awt.Color(0, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Email", "First Name", "Last Name", "Mobile", "Gender", "Company"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        jLabel12.setBackground(new java.awt.Color(121, 203, 96));
        jLabel12.setFont(new java.awt.Font("Retro Signed", 0, 66)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("SUPPLIER REGISTRATION");
        jLabel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel12.setOpaque(true);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));

        jLabel7.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 0, 28)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("TOTAL GRN'S :-");

        total_grns.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 0, 28)); // NOI18N
        total_grns.setForeground(new java.awt.Color(0, 0, 0));
        total_grns.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total_grns.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(total_grns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(total_grns, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));

        jLabel15.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 0, 28)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("PENDING PAYMENTS :-");

        pending_payments.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 0, 28)); // NOI18N
        pending_payments.setForeground(new java.awt.Color(0, 0, 0));
        pending_payments.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pending_payments.setText("0");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pending_payments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pending_payments, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(backLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(backLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 984, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backLogobackToHome(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backLogobackToHome

        // back to home
        resetField();

        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentWindow != null) {
            parentWindow.dispose();
        }

        home.setVisible(true);

    }//GEN-LAST:event_backLogobackToHome

    private void createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createActionPerformed

        // create account
        String emailValue = email.getText();
        String firstNameValue = firstname.getText();
        String lastNameValue = lastname.getText();
        String mobileValue = mobile.getText();
        ButtonModel genderValue = genderButtonGroup.getSelection();

        if (companyId == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Company!");
        } else if (emailValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Email Address!");
        } else if (!emailValue.matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Email Address!");
        } else if (firstNameValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the First Name!");
        } else if (lastNameValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Last Name!");
        } else if (mobileValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Mobile Number!");
        } else if (!mobileValue.matches("^07[01245678]{1}[0-9]{7}$")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Mobile Number!");
        } else if (genderValue == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Gender!");
        } else {

            String genderId = genderValue.getActionCommand();

            try {

                ResultSet rs = MySQL.executeSearch("SELECT * FROM `supplier` WHERE `email` = '" + emailValue + "' OR `mobile` = '" + mobileValue + "'");

                if (rs.next()) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "This Email Address or Mobile Number is already existed!");
                } else {

                    MySQL.executeIUD("INSERT INTO `supplier` (`email`,`first_name`,`last_name`,`mobile`,`gender_id`,`supplier's_company_id`) VALUES "
                            + "('" + emailValue + "', '" + firstNameValue + "', '" + lastNameValue + "', '" + mobileValue + "', '" + genderId + "', '" + companyId + "')");

                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, 3500l, "Profile created Successfully!");

                    resetField();
                    loadSuppliers("first_name", "ASC", "");

                }

            } catch (SQLException e) {
                e.printStackTrace();
                logger.log(Level.WARNING, "Add Suppliers Error!", e);
            } catch (Exception e) {
                e.printStackTrace();
                logger.log(Level.WARNING, "Add Suppliers Error!", e);
            }

        }

    }//GEN-LAST:event_createActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed

        // update account
        String companyValue = company_name.getText();
        String emailValue = email.getText();
        String firstNameValue = firstname.getText();
        String lastNameValue = lastname.getText();
        String mobileValue = mobile.getText();
        ButtonModel genderValue = genderButtonGroup.getSelection();

        if (companyValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Company!");
        } else if (firstNameValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the First Name!");
        } else if (lastNameValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Last Name!");
        } else if (mobileValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Mobile Number!");
        } else if (!mobileValue.matches("^07[01245678]{1}[0-9]{7}$")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Mobile Number!");
        } else if (genderValue == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Gender!");
        } else {

            String genderId = genderValue.getActionCommand();

            try {

                int row = jTable1.getSelectedRow();

                ResultSet rs = MySQL.executeSearch("SELECT * FROM `supplier` WHERE `email` != '" + emailValue + "' AND `mobile` = '" + mobileValue + "'");

                if (rs.next()) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "This Mobile Number is already existed!");
                } else {

                    String queryPart;

                    if (companyValue.equals(String.valueOf(jTable1.getValueAt(row, 5)))) {
                        // company update not required
                        queryPart = "";
                    } else {
                        // company update required
                        queryPart = ", `supplier's_company_id` = '" + companyId + "'";
                    }

                    MySQL.executeIUD("UPDATE `supplier` SET `first_name` = '" + firstNameValue + "', `last_name` = '" + lastNameValue + "', `mobile` = '" + mobileValue + "', "
                            + "`gender_id` = '" + genderId + "' " + queryPart + " WHERE `email` = '" + emailValue + "'");

                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, 3500l, "Profile updated Successfully!");

                    resetField();
                    loadSuppliers("first_name", "ASC", "");

                }

            } catch (SQLException e) {
                e.printStackTrace();
                logger.log(Level.WARNING, "Add Suppliers Error!", e);
            } catch (Exception e) {
                e.printStackTrace();
                logger.log(Level.WARNING, "Add Suppliers Error!", e);
            }

        }

    }//GEN-LAST:event_updateActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // reset all
        resetField();
    }//GEN-LAST:event_resetActionPerformed

    private void searchIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchIdKeyReleased
        // search accounts
        searchProfiles();
    }//GEN-LAST:event_searchIdKeyReleased

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // search accounts
        searchProfiles();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        // get a row to update
        if (evt.getClickCount() == 2) {

            jTable1.setEnabled(false);
            searchId.setEnabled(false);
            jComboBox1.setEnabled(false);
            create.setEnabled(false);
            email.setEnabled(false);
            update.setEnabled(true);
            update.grabFocus();

            jLabel1.setText("UPDATE");

            int seletedRow = jTable1.getSelectedRow();

            String emailValue = String.valueOf(jTable1.getValueAt(seletedRow, 0));
            String firstNameValue = String.valueOf(jTable1.getValueAt(seletedRow, 1));
            String lastNameValue = String.valueOf(jTable1.getValueAt(seletedRow, 2));
            String mobileValue = String.valueOf(jTable1.getValueAt(seletedRow, 3));
            String genderValue = String.valueOf(jTable1.getValueAt(seletedRow, 4));
            String companyValue = String.valueOf(jTable1.getValueAt(seletedRow, 5));

            email.setText(emailValue);
            firstname.setText(firstNameValue);
            lastname.setText(lastNameValue);
            mobile.setText(mobileValue);
            if (genderValue.equals("Male")) {
                jRadioButton1.setSelected(true);
            } else if (genderValue.equals("Female")) {
                jRadioButton2.setSelected(true);
            }
            company_name.setText(companyValue);

            // set supplier grn count and want to paid price
            try {

                ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `grn` INNER JOIN `grn_item` ON `grn`.`id` = `grn_item`.`grn_id` WHERE `supplier_email` = '" + emailValue + "'");

                double total = 0;

                HashMap<Long, Double> grns = new HashMap<>();

                while (resultSet.next()) {

                    double qty = resultSet.getShort("grn_item.qty");
                    double price = resultSet.getShort("grn_item.price");

                    double itemTotal = qty * price;

                    total += itemTotal;

                    grns.put(resultSet.getLong("grn.id"), resultSet.getDouble("grn.paid_amount"));

                }

                double totalPaid = 0;

                for (Double paid : grns.values()) {

                    totalPaid += paid;

                }

                total_grns.setText(String.valueOf(grns.size()));
                pending_payments.setText(String.valueOf(total - totalPaid));

            } catch (SQLException e) {
                e.printStackTrace();
                logger.log(Level.WARNING, "Error in the Supplier Selection Process!", e);
            } catch (Exception e) {
                e.printStackTrace();
                logger.log(Level.WARNING, "Error in the Supplier Selection Process!", e);
            }
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void selectCompanyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectCompanyActionPerformed

        // go to supplier company
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentWindow != null) {
            parentWindow.setEnabled(false);
        }

        Supplier_Company supplier_Company = new Supplier_Company(parentWindow, this);
        supplier_Company.setVisible(true);

    }//GEN-LAST:event_selectCompanyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backLogo;
    private javax.swing.JLabel back_label;
    private javax.swing.JLabel company_name;
    private javax.swing.JButton create;
    private javax.swing.JTextField email;
    private javax.swing.JTextField firstname;
    private javax.swing.ButtonGroup genderButtonGroup;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField lastname;
    private javax.swing.JTextField mobile;
    private javax.swing.JLabel pending_payments;
    private javax.swing.JButton reset;
    private javax.swing.JTextField searchId;
    private javax.swing.JButton selectCompany;
    private javax.swing.JLabel total_grns;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables

    // reset fields 
    private void resetField() {
        company_name.setText("");
        email.setText("");
        firstname.setText("");
        lastname.setText("");
        mobile.setText("");
        genderButtonGroup.clearSelection();
        companyId = null;

        email.setEnabled(true);
        create.setEnabled(true);
        update.setEnabled(false);

        searchId.setText("");
        searchId.setEnabled(true);
        jComboBox1.setEnabled(true);

        jComboBox1.setSelectedIndex(0);

        jTable1.setEnabled(true);

        email.grabFocus();

        jLabel1.setText("REGISTER");

        loadSuppliers("first_name", "ASC", "");

        total_grns.setText("0");
        pending_payments.setText("0");
    }

    private void searchProfiles() {

        int sort = jComboBox1.getSelectedIndex();

        if (sort == 0) {
            loadSuppliers("first_name", "ASC", searchId.getText());
        } else if (sort == 1) {
            loadSuppliers("first_name", "DESC", searchId.getText());
        } else if (sort == 2) {
            loadSuppliers("supplier's_company`.`name", "ASC", searchId.getText());
        } else if (sort == 3) {
            loadSuppliers("supplier's_company`.`name", "DESC", searchId.getText());
        }

    }

}
