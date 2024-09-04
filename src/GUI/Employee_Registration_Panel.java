/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.util.HashMap;
import java.util.Vector;
import model.MySQL;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ButtonModel;
import javax.swing.table.DefaultTableModel;
import raven.toast.Notifications;

/**
 *
 * @author User
 */
public class Employee_Registration_Panel extends javax.swing.JPanel {

    private static HashMap<String, Integer> employeeTypeMap = new HashMap<>();
    private static Home home = new Home();
    private static SignIn signin = new SignIn();

    /**
     * Creates new form Register_Panel
     */
    public Employee_Registration_Panel() {
        initComponents();
        init();
        styleTheTextFieldsAndButtons();
        loadEmployeeTypes();
        loadProfiles();

        // set notification position
        Notifications.getInstance();

        // disable the buttons
        update.setEnabled(false);
        addressBtn.setEnabled(false);
    }

    // load profiles to table
    public void loadProfiles() {

        try {

            ResultSet rs = MySQL.executeSearch("SELECT * FROM `employee` INNER JOIN `employee_type` ON `employee`.`employee_type_id` = `employee_type`.`id` "
                    + "INNER JOIN `gender` ON `employee`.`gender_id` = `gender`.`id` ORDER BY `employee`.`email` ASC;");

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();

            dtm.setRowCount(0);

            while (rs.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(rs.getString("email"));
                vector.add(rs.getString("first_name"));
                vector.add(rs.getString("last_name"));
                vector.add(rs.getString("nic"));
                vector.add(rs.getString("password"));
                vector.add(rs.getString("mobile"));
                vector.add(rs.getString("gender.name"));
                vector.add(rs.getString("employee_type.name"));

                dtm.addRow(vector);
            }

            jTable1.setModel(dtm);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // reset the field
    public void resetField() {
        firstname.setText("");
        lastname.setText("");
        nic.setText("");
        email.setText("");
        password.setText("");
        mobile.setText("");
        genderButtonGroup.clearSelection();
        type.setSelectedIndex(0);

        jTable1.setEnabled(true);
        searchId.setEnabled(true);
        create.setEnabled(true);
        email.setEnabled(true);
        update.setEnabled(false);
        addressBtn.setEnabled(false);
        searchId.setText("");

        jLabel1.setText("REGISTER");
        firstname.grabFocus();
        loadProfiles();
    }

    // load countries
    private void loadEmployeeTypes() {

        try {

            ResultSet rs = MySQL.executeSearch("SELECT * FROM `employee_type`;");

            Vector<String> vector = new Vector<>();
            vector.add("Select Type");
            while (rs.next()) {
                vector.add(rs.getString("name"));
                employeeTypeMap.put(rs.getString("name"), rs.getInt("id"));

            }

            DefaultComboBoxModel dcbm = new DefaultComboBoxModel(vector);
            type.setModel(dcbm);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // style the text fields and buttons
    private void styleTheTextFieldsAndButtons() {
        firstname.putClientProperty("JComponent.roundRect", true);
        firstname.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- John");
        firstname.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        firstname.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        lastname.putClientProperty("JComponent.roundRect", true);
        lastname.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- Doely");
        lastname.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        lastname.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        nic.putClientProperty("JComponent.roundRect", true);
        nic.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- xxxxxxxxxV or xxxxxxxxxxxx");
        nic.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        nic.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        email.putClientProperty("JComponent.roundRect", true);
        email.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- john@gmail.com");
        email.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        email.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        password.putClientProperty("JComponent.roundRect", true);
        password.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- john123");
        password.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        password.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        show_button.putClientProperty("JButton.buttonType", "roundRect");

        mobile.putClientProperty("JComponent.roundRect", true);
        mobile.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- 0712345678");
        mobile.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        mobile.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        type.putClientProperty("JComponent.roundRect", true);

        create.putClientProperty("JButton.buttonType", "roundRect");
        update.putClientProperty("JButton.buttonType", "roundRect");
        addressBtn.putClientProperty("JButton.buttonType", "roundRect");
        reset.putClientProperty("JButton.buttonType", "roundRect");

        searchId.putClientProperty("JComponent.roundRect", true);
        searchId.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search by Email");
        searchId.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");

    }

    private void init() {
        // icons, logos & images load
        FlatSVGIcon back = new FlatSVGIcon("resources//backTo.svg", 38, 34);
        back_label.setIcon(back);

        FlatSVGIcon showEye = new FlatSVGIcon("resources//show.svg", 30, 18);
        show_button.setIcon(showEye);
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        backLogo = new javax.swing.JPanel();
        back_label = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        firstname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lastname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        show_button = new javax.swing.JButton();
        mobile = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        type = new javax.swing.JComboBox<>();
        create = new javax.swing.JButton();
        update = new javax.swing.JButton();
        password = new javax.swing.JPasswordField();
        nic = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        addressBtn = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        searchId = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();

        jPanel1.setPreferredSize(new java.awt.Dimension(1260, 1403));

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

        jLabel3.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText(" First Name");

        firstname.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 17)); // NOI18N
        firstname.setForeground(new java.awt.Color(0, 0, 0));

        jLabel4.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText(" Last Name");

        lastname.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 17)); // NOI18N
        lastname.setForeground(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText(" Email");

        email.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 17)); // NOI18N
        email.setForeground(new java.awt.Color(0, 0, 0));

        jLabel6.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText(" Password");

        show_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        show_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                show_buttonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                show_buttonMouseReleased(evt);
            }
        });

        mobile.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 17)); // NOI18N
        mobile.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText(" Mobile");

        jLabel8.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText(" Gender");

        genderButtonGroup.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 18)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton1.setText("Male");
        jRadioButton1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRadioButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jRadioButton1.setIconTextGap(25);
        jRadioButton1.setActionCommand("1");

        genderButtonGroup.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 18)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton2.setText("Female");
        jRadioButton2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRadioButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jRadioButton2.setIconTextGap(25);
        jRadioButton2.setActionCommand("2");

        jLabel9.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText(" Type");

        type.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 17)); // NOI18N
        type.setForeground(new java.awt.Color(0, 0, 0));

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

        password.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 17)); // NOI18N
        password.setForeground(new java.awt.Color(0, 0, 0));

        nic.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 17)); // NOI18N
        nic.setForeground(new java.awt.Color(0, 0, 0));

        jLabel11.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText(" NIC");

        addressBtn.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 22)); // NOI18N
        addressBtn.setForeground(new java.awt.Color(0, 0, 0));
        addressBtn.setText("View Address");
        addressBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addressBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressBtnActionPerformed(evt);
            }
        });

        reset.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 22)); // NOI18N
        reset.setForeground(new java.awt.Color(0, 0, 0));
        reset.setText("Reset");
        reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(update, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(create, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(type, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mobile, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(show_button, javax.swing.GroupLayout.PREFERRED_SIZE, 56, Short.MAX_VALUE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(email, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lastname, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(firstname, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nic, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addressBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(firstname, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lastname, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(nic, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(show_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(password, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(mobile, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(create, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(addressBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(searchId)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(searchId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));

        jTable1.setBackground(new java.awt.Color(215, 249, 204));
        jTable1.setFont(new java.awt.Font("JetBrains Mono Medium", 0, 10)); // NOI18N
        jTable1.setForeground(new java.awt.Color(0, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Email", "First Name", "Last Name", "NIC", "Password", "Mobile", "Gender", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        jLabel12.setBackground(new java.awt.Color(121, 203, 96));
        jLabel12.setFont(new java.awt.Font("Retro Signed", 0, 66)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("EMPLOYEE REGISTRATION");
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
                                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                        .addGap(15, 15, 15)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1260, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1403, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void backLogobackToHome(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backLogobackToHome

        // back to home
        resetField();
        
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentWindow != null) {
            parentWindow.dispose();
        }

        if (Employee_Address.backToHomeChange == 1) {
            home.setVisible(true);
        } else {
            signin.setVisible(true);
        }

    }//GEN-LAST:event_backLogobackToHome

    private void show_buttonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show_buttonMousePressed
        // visible the password
        password.setEchoChar('\u0000');
    }//GEN-LAST:event_show_buttonMousePressed

    private void show_buttonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show_buttonMouseReleased
        // hide the password
        password.setEchoChar('\u2022');
    }//GEN-LAST:event_show_buttonMouseReleased

    private void createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createActionPerformed

        // create account
        String firstNameValue = firstname.getText();
        String lastNameValue = lastname.getText();
        String nicValue = nic.getText();
        String emailValue = email.getText();
        String passwordValue = String.valueOf(password.getPassword());
        String mobileValue = mobile.getText();
        ButtonModel genderValue = genderButtonGroup.getSelection();
        String employeeTypeValue = String.valueOf(type.getSelectedItem());

        if (firstNameValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the First Name!");
        } else if (lastNameValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Last Name!");
        } else if (nicValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the NIC!");
        } else if (!(nicValue.length() == 10 || nicValue.length() == 12)) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid NIC!");
        } else if (emailValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Email Address!");
        } else if (!emailValue.matches("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Email Address!");
        } else if (passwordValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Password!");
        } else if (!passwordValue.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Password must be in minimum eight characters, at least one letter and one number!");
        } else if (mobileValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Mobile Number!");
        } else if (!mobileValue.matches("^07[01245678]{1}[0-9]{7}$")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Mobile Number!");
        } else if (genderValue == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Gender!");
        } else if (employeeTypeValue.equals("Select Type")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Type!");
        } else {

            try {

                ResultSet rs = MySQL.executeSearch("SELECT * FROM `employee` WHERE `email` = '" + emailValue + "' OR `nic` = '" + nicValue + "' OR `mobile` = '" + mobileValue + "';");

                if (!rs.next()) {

                    String genderId = genderValue.getActionCommand();

                    int employeeTypeId = employeeTypeMap.get(employeeTypeValue);

                    try {

                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String formatedDate = sdf.format(date);

                        MySQL.executeIUD("INSERT INTO `employee` (`email`,`first_name`,`last_name`,`nic`,`password`,`mobile`,`registered_date`,`gender_id`,`employee_type_id`) "
                                + "VALUES ('" + emailValue + "', '" + firstNameValue + "', '" + lastNameValue + "', '" + nicValue + "', '" + passwordValue + "', '" + mobileValue + "', "
                                + "'" + formatedDate + "', '" + genderId + "', '" + employeeTypeId + "');");

                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, 3500l, "Profile created Successfully!");

                        resetField();

                        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
                        if (parentWindow != null) {
                            parentWindow.setEnabled(false);
                        }

                        int add = 1;

                        Employee_Address employee_address = new Employee_Address(parentWindow, this, add, emailValue);
                        employee_address.setVisible(true);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "This Employee is already Registered!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_createActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed

        // update account
        String emailValue = email.getText();

        String firstNameValue = firstname.getText();
        String lastNameValue = lastname.getText();
        String nicValue = nic.getText();
        String passwordValue = String.valueOf(password.getPassword());
        String mobileValue = mobile.getText();
        ButtonModel genderValue = genderButtonGroup.getSelection();
        String employeeTypeValue = String.valueOf(type.getSelectedItem());

        if (firstNameValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the First Name!");
        } else if (lastNameValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Last Name!");
        } else if (nicValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the NIC!");
        } else if (!(nicValue.length() == 10 || nicValue.length() == 12)) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid NIC!");
        } else if (passwordValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Password!");
        } else if (!passwordValue.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Password must be in minimum eight characters, at least one letter and one number!");
        } else if (mobileValue.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Mobile Number!");
        } else if (!mobileValue.matches("^07[01245678]{1}[0-9]{7}$")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Mobile Number!");
        } else if (genderValue == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Gender!");
        } else if (employeeTypeValue.equals("Select Type")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Type!");
        } else {

            try {

                ResultSet rs = MySQL.executeSearch("SELECT * FROM `employee` WHERE `nic` = '" + nicValue + "'  OR `mobile` = '" + mobileValue + "';");

                String genderId = genderValue.getActionCommand();

                int employeeTypeId = employeeTypeMap.get(employeeTypeValue);

                boolean canUpdate = false;

                if (!rs.next()) {
                    canUpdate = true;
                } else {

                    if (!rs.getString("email").equals(emailValue)) {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "This NIC or Mobile Number is already existed!");
                    } else {
                        canUpdate = true;
                    }

                }

                if (canUpdate) {

                    MySQL.executeIUD("UPDATE `employee` SET `first_name` = '" + firstNameValue + "', `last_name` = '" + lastNameValue + "', `nic` = '" + nicValue + "', `password` = '" + passwordValue + "', "
                            + "`mobile` = '" + mobileValue + "', `gender_id` = '" + genderId + "', `employee_type_id` = '" + employeeTypeId + "' WHERE `email` = '" + emailValue + "';");

                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, 3500l, "Profile updated Successfully!");

                    resetField();

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_updateActionPerformed

    private void addressBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressBtnActionPerformed

        // go to employee address
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parentWindow != null) {
            parentWindow.setEnabled(false);
        }

        int selectedRow = jTable1.getSelectedRow();

        String emailValue = String.valueOf(jTable1.getValueAt(selectedRow, 0));

        int add = 2;

        Employee_Address employee_address = new Employee_Address(parentWindow, this, add, emailValue);
        employee_address.setVisible(true);

    }//GEN-LAST:event_addressBtnActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // reset all
        resetField();
    }//GEN-LAST:event_resetActionPerformed

    private void searchIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchIdKeyReleased

        // search accounts
        String searchValue = searchId.getText();

        if (searchValue.isEmpty()) {
            loadProfiles();
        } else {

            try {

                ResultSet rs = MySQL.executeSearch("SELECT * FROM `employee` INNER JOIN `employee_type` ON `employee`.`employee_type_id` = `employee_type`.`id` "
                        + "INNER JOIN `gender` ON `employee`.`gender_id` = `gender`.`id` WHERE `email` LIKE '%" + searchValue + "%' ORDER BY `employee`.`email` ASC;");

                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();

                dtm.setRowCount(0);

                while (rs.next()) {
                    Vector<String> vector = new Vector<>();
                    vector.add(rs.getString("email"));
                    vector.add(rs.getString("first_name"));
                    vector.add(rs.getString("last_name"));
                    vector.add(rs.getString("nic"));
                    vector.add(rs.getString("password"));
                    vector.add(rs.getString("mobile"));
                    vector.add(rs.getString("gender.name"));
                    vector.add(rs.getString("employee_type.name"));

                    dtm.addRow(vector);
                }

                jTable1.setModel(dtm);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_searchIdKeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        // get a row to update
        if (evt.getClickCount() == 2) {

            jTable1.setEnabled(false);
            searchId.setEnabled(false);
            create.setEnabled(false);
            email.setEnabled(false);
            update.setEnabled(true);
            addressBtn.setEnabled(true);
            update.grabFocus();

            jLabel1.setText("UPDATE");

            int seletedRow = jTable1.getSelectedRow();

            String emailValue = String.valueOf(jTable1.getValueAt(seletedRow, 0));
            String firstNameValue = String.valueOf(jTable1.getValueAt(seletedRow, 1));
            String lastNameValue = String.valueOf(jTable1.getValueAt(seletedRow, 2));
            String nicValue = String.valueOf(jTable1.getValueAt(seletedRow, 3));
            String passwordValue = String.valueOf(jTable1.getValueAt(seletedRow, 4));
            String mobileValue = String.valueOf(jTable1.getValueAt(seletedRow, 5));
            String genderValue = String.valueOf(jTable1.getValueAt(seletedRow, 6));
            String employeeTypeValue = String.valueOf(jTable1.getValueAt(seletedRow, 7));

            firstname.setText(firstNameValue);
            lastname.setText(lastNameValue);
            nic.setText(nicValue);
            email.setText(emailValue);
            password.setText(passwordValue);
            mobile.setText(mobileValue);
            if (genderValue.equals("Male")) {
                jRadioButton1.setSelected(true);
            } else if (genderValue.equals("Female")) {
                jRadioButton2.setSelected(true);
            }
            type.setSelectedItem(employeeTypeValue);

        }

    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addressBtn;
    private javax.swing.JPanel backLogo;
    private javax.swing.JLabel back_label;
    private javax.swing.JButton create;
    private javax.swing.JTextField email;
    private javax.swing.JTextField firstname;
    private javax.swing.ButtonGroup genderButtonGroup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
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
    private javax.swing.JTextField nic;
    private javax.swing.JPasswordField password;
    private javax.swing.JButton reset;
    private javax.swing.JTextField searchId;
    private javax.swing.JButton show_button;
    private javax.swing.JComboBox<String> type;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
