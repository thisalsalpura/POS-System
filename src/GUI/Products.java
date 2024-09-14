/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
import raven.toast.Notifications;

/**
 *
 * @author User
 */
public class Products extends javax.swing.JFrame {

    private static HashMap<String, Integer> materialMap = new HashMap<>();
    private static Employee_Registration register = new Employee_Registration();
    private static Customer_Registration customer_Registration = new Customer_Registration();
    private static Supplier_Registration supplier_Registration = new Supplier_Registration();
    private static Invoice invoice = new Invoice();
    private static SignIn signIn = new SignIn();
    private static Home home = new Home();
    private static GRN grn = new GRN();
    private Timer stockUpdateTimer;
    private int uniqueId = 0;

    /**
     * Creates new form Products
     */
    public Products() {
        initComponents();
        this.setExtendedState(Home.MAXIMIZED_BOTH);
        init();
        getDateTime();
        styleTheTextFieldsAndButtons();
        loadMaterials();
        loadProducts();
        loadStock();

        // set notification position
        Notifications.getInstance();

        // disable buttons
        update.setEnabled(false);

        startStockUpdateTimer();

        menuButton2.grabFocus();

        // add a icon image
        Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/icon.png"));
        this.setIconImage(image);

        // set the table details center
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        jTable1.setDefaultRenderer(Object.class, renderer);
        jTable2.setDefaultRenderer(Object.class, renderer);
        jTable3.setDefaultRenderer(Object.class, renderer);
    }

    // highlited the current page button
    public void highlitedTheButton() {
        menuButton2.grabFocus();
    }

    // load stock always
    private void startStockUpdateTimer() {
        int interval = 100;

        stockUpdateTimer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadStock();
            }
        });

        stockUpdateTimer.start();
    }

    // load stock
    public void loadStock() {

        try {

            String query = "SELECT * FROM `stock` INNER JOIN `material` ON `stock`.`material_id` = `material`.`id` ";

            if (sortBy.getSelectedIndex() == 0) {
                query += "ORDER BY `stock`.`id` ASC";
            } else if (sortBy.getSelectedIndex() == 1) {
                query += "ORDER BY `stock`.`id` DESC";
            } else if (sortBy.getSelectedIndex() == 2) {
                query += "ORDER BY `stock`.`qty` ASC";
            } else if (sortBy.getSelectedIndex() == 3) {
                query += "ORDER BY `stock`.`qty` DESC";
            }

            ResultSet resultSet = MySQL.executeSearch(query);

            DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
            dtm.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("stock.id"));
                vector.add(resultSet.getString("material.id"));
                vector.add(resultSet.getString("material.name"));
                vector.add(resultSet.getString("stock.qty"));

                dtm.addRow(vector);
            }

            jTable2.setModel(dtm);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // load products
    private void loadProducts() {

        try {

            String query = "SELECT * FROM `product` ";

            double min_price = 0;
            double max_price = 0;

            if (!jFormattedTextField1.getText().isEmpty()) {
                if (!jFormattedTextField1.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Price!");
                } else {
                    min_price = Double.parseDouble(jFormattedTextField1.getText());
                }
            }

            if (!jFormattedTextField2.getText().isEmpty()) {
                if (!jFormattedTextField2.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Price!");
                } else {
                    max_price = Double.parseDouble(jFormattedTextField2.getText());
                }
            }

            if (min_price > 0 && max_price == 0) {
                query += "WHERE `price` > '" + min_price + "' ";
            } else if (min_price == 0 && max_price > 0) {
                query += "WHERE `price` < '" + max_price + "' ";
            } else if (min_price > 0 && max_price > 0) {
                query += "WHERE `price` > '" + min_price + "' AND `price` < '" + max_price + "' ";
            }

            if (sortBy1.getSelectedIndex() == 0) {
                query += "ORDER BY `product`.`id` ASC";
            } else if (sortBy1.getSelectedIndex() == 1) {
                query += "ORDER BY `product`.`id` DESC";
            } else if (sortBy1.getSelectedIndex() == 2) {
                query += "ORDER BY `product`.`name` ASC";
            } else if (sortBy1.getSelectedIndex() == 3) {
                query += "ORDER BY `product`.`name` DESC";
            } else if (sortBy1.getSelectedIndex() == 4) {
                query += "ORDER BY `product`.`price` ASC";
            } else if (sortBy1.getSelectedIndex() == 5) {
                query += "ORDER BY `product`.`price` DESC";
            }

            ResultSet resultSet = MySQL.executeSearch(query);

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();

            dtm.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("name"));
                vector.add(resultSet.getString("price"));

                dtm.addRow(vector);
            }

            jTable1.setModel(dtm);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // load materials
    private void loadMaterials() {

        try {

            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `material`");

            Vector<String> vector = new Vector<>();
            vector.add("Select Material");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                materialMap.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel dcbm = new DefaultComboBoxModel(vector);
            selectMaterial.setModel(dcbm);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // style the text fields and buttons
    private void styleTheTextFieldsAndButtons() {
        menuButton1.putClientProperty("JButton.buttonType", "roundRect");
        menuButton2.putClientProperty("JButton.buttonType", "roundRect");
        menuButton3.putClientProperty("JButton.buttonType", "roundRect");
        menuButton4.putClientProperty("JButton.buttonType", "roundRect");
        menuButton5.putClientProperty("JButton.buttonType", "roundRect");
        menuButton6.putClientProperty("JButton.buttonType", "roundRect");

        product_id.putClientProperty("JComponent.roundRect", true);
        product_id.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- 001");
        product_id.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        product_id.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        product_name.putClientProperty("JComponent.roundRect", true);
        product_name.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- Chocolate Milk Shake");
        product_name.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        product_name.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        product_price.putClientProperty("JComponent.roundRect", true);
        product_price.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- 250");
        product_price.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        product_price.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        selectMaterial.putClientProperty("JComponent.roundRect", true);
        saveMaterial.putClientProperty("JButton.buttonType", "roundRect");

        addMaterialText.putClientProperty("JComponent.roundRect", true);
        addMaterialText.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "ex :- Milk");
        addMaterialText.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        addMaterialText.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        addMaterialBtn.putClientProperty("JButton.buttonType", "roundRect");

        add.putClientProperty("JButton.buttonType", "roundRect");
        update.putClientProperty("JButton.buttonType", "roundRect");
        clear_all_products.putClientProperty("JButton.buttonType", "roundRect");

        sortBy.putClientProperty("JComponent.roundRect", true);
        sortBy1.putClientProperty("JComponent.roundRect", true);

        jFormattedTextField1.putClientProperty("JComponent.roundRect", true);
        jFormattedTextField1.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");

        jFormattedTextField2.putClientProperty("JComponent.roundRect", true);
        jFormattedTextField2.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");

        clear_all_stocks.putClientProperty("JButton.buttonType", "roundRect");
    }

    // get date and time
    private void getDateTime() {

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();

                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh.mm.ss a");

                String formatedDate = dateFormat.format(date);
                String formatedTime = timeFormat.format(date);

                date_label.setText(formatedDate);
                time_label.setText(formatedTime);
            }
        });
        timer.start();

    }

    private void init() {
        // icons, logos & images load
        FlatSVGIcon icon = new FlatSVGIcon("resources//bubble_tea.svg", logo.getWidth(), logo.getHeight());
        logo.setIcon(icon);

        FlatSVGIcon UserIcon = new FlatSVGIcon("resources//user.svg", 49, 38);
        user_logo.setIcon(UserIcon);

        FlatSVGIcon LogOutIcon = new FlatSVGIcon("resources//logout.svg", 36, 40);
        logout_logo.setIcon(LogOutIcon);

        FlatSVGIcon menuIcon = new FlatSVGIcon("resources//menu_logo.svg", 24, 30);
        menu_logo.setIcon(menuIcon);

        FlatSVGIcon billToArrow = new FlatSVGIcon("resources//arrow_right.svg", 20, 20);
        billTo.setIcon(billToArrow);

        FlatSVGIcon star = new FlatSVGIcon("resources//star.svg", 78, 74);
        star_label.setIcon(star);

        FlatSVGIcon add = new FlatSVGIcon("resources//add.svg", 45, 44);
        addMaterialBtn.setIcon(add);
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
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        menu_logo = new javax.swing.JLabel();
        menuButton1 = new javax.swing.JButton();
        menuButton3 = new javax.swing.JButton();
        menuButton4 = new javax.swing.JButton();
        menuButton5 = new javax.swing.JButton();
        menuButton6 = new javax.swing.JButton();
        menuButton2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        bill_btn_txt = new javax.swing.JButton();
        billTo = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        date_label = new javax.swing.JLabel();
        time_label = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        user_logo = new javax.swing.JLabel();
        logout_logo = new javax.swing.JLabel();
        panel_loader = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        product_id = new javax.swing.JTextField();
        product_name = new javax.swing.JTextField();
        addMaterialText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        selectMaterial = new javax.swing.JComboBox<>();
        addMaterialBtn = new javax.swing.JButton();
        add = new javax.swing.JButton();
        update = new javax.swing.JButton();
        clear_all_products = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        product_price = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        saveMaterial = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        sortBy1 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        clear_all_stocks = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        sortBy = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        star_label = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(215, 249, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 229, 255), 6));

        jPanel5.setBackground(new java.awt.Color(204, 229, 255));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(121, 203, 96), 3, true));

        jLabel2.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 32)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("MENU");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(menu_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(menu_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        menuButton1.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        menuButton1.setForeground(new java.awt.Color(0, 0, 0));
        menuButton1.setText("Home");
        menuButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButton1ActionPerformed(evt);
            }
        });

        menuButton3.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        menuButton3.setForeground(new java.awt.Color(0, 0, 0));
        menuButton3.setText("Customers");
        menuButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButton3ActionPerformed(evt);
            }
        });

        menuButton4.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        menuButton4.setForeground(new java.awt.Color(0, 0, 0));
        menuButton4.setText("Suppliers");
        menuButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButton4ActionPerformed(evt);
            }
        });

        menuButton5.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        menuButton5.setForeground(new java.awt.Color(0, 0, 0));
        menuButton5.setText("GRN");
        menuButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButton5ActionPerformed(evt);
            }
        });

        menuButton6.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        menuButton6.setForeground(new java.awt.Color(0, 0, 0));
        menuButton6.setText("Earnings");
        menuButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButton6ActionPerformed(evt);
            }
        });

        menuButton2.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        menuButton2.setForeground(new java.awt.Color(0, 0, 0));
        menuButton2.setText("Products");
        menuButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButton2ActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(121, 203, 96));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 2, true));

        bill_btn_txt.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        bill_btn_txt.setForeground(new java.awt.Color(0, 0, 0));
        bill_btn_txt.setText("Invoice");
        bill_btn_txt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bill_btn_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bill_btn_txtActionPerformed(evt);
            }
        });

        billTo.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        billTo.setForeground(new java.awt.Color(0, 0, 0));
        billTo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        billTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billToActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(bill_btn_txt)
                .addGap(0, 0, 0)
                .addComponent(billTo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(billTo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bill_btn_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(menuButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(menuButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addComponent(menuButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(menuButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(menuButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(menuButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(menuButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(menuButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(menuButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(121, 203, 95));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 229, 255), 6));

        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setFont(new java.awt.Font("Retro Signed", 0, 75)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Bubble Bay");

        date_label.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 14)); // NOI18N
        date_label.setForeground(new java.awt.Color(0, 0, 0));
        date_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        time_label.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 30)); // NOI18N
        time_label.setForeground(new java.awt.Color(0, 0, 0));
        time_label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jPanel4.setBackground(new java.awt.Color(121, 203, 95));

        user_logo.setToolTipText("Register");
        user_logo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        user_logo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                user_logoMouseClicked(evt);
            }
        });

        logout_logo.setToolTipText("Register");
        logout_logo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logout_logo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logout_logoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(user_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(logout_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(logout_logo, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(user_logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(date_label, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(time_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(48, 48, 48)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(date_label, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(time_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                        .addComponent(logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );

        panel_loader.setBackground(new java.awt.Color(215, 249, 204));
        panel_loader.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 229, 255), 6));
        panel_loader.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel_loader.setOpaque(true);
        panel_loader.setRowHeaderView(null);

        jPanel7.setPreferredSize(new java.awt.Dimension(1036, 1637));

        jPanel13.setPreferredSize(new java.awt.Dimension(986, 1637));

        jPanel14.setBackground(new java.awt.Color(215, 249, 204));
        jPanel14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 5, true));

        jLabel16.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 22)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText(" Product ID :-");

        jLabel17.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 22)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText(" Product Name :-");

        product_id.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 16)); // NOI18N
        product_id.setForeground(new java.awt.Color(0, 0, 0));

        product_name.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 16)); // NOI18N
        product_name.setForeground(new java.awt.Color(0, 0, 0));

        addMaterialText.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 16)); // NOI18N
        addMaterialText.setForeground(new java.awt.Color(0, 0, 0));

        jLabel3.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 22)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText(" Select Material :-");

        jLabel4.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText(" Add New Material :-");

        selectMaterial.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 16)); // NOI18N
        selectMaterial.setForeground(new java.awt.Color(0, 0, 0));

        addMaterialBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addMaterialBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMaterialBtnActionPerformed(evt);
            }
        });

        add.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 24)); // NOI18N
        add.setForeground(new java.awt.Color(0, 0, 0));
        add.setText("Add Product");
        add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        update.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 24)); // NOI18N
        update.setForeground(new java.awt.Color(0, 0, 0));
        update.setText("Update Product");
        update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        clear_all_products.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 24)); // NOI18N
        clear_all_products.setForeground(new java.awt.Color(0, 0, 0));
        clear_all_products.setText("Clear All");
        clear_all_products.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clear_all_products.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_all_productsActionPerformed(evt);
            }
        });

        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));

        jScrollPane3.setBorder(null);

        jTable3.setBackground(new java.awt.Color(215, 249, 204));
        jTable3.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 12)); // NOI18N
        jTable3.setForeground(new java.awt.Color(0, 0, 0));
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Material Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.getTableHeader().setReorderingAllowed(false);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
        );

        product_price.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 16)); // NOI18N
        product_price.setForeground(new java.awt.Color(0, 0, 0));

        jLabel8.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText(" Product Price :-");

        saveMaterial.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        saveMaterial.setText("Save");
        saveMaterial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMaterialActionPerformed(evt);
            }
        });

        jPanel21.setBackground(new java.awt.Color(204, 229, 255));

        jFormattedTextField2.setForeground(new java.awt.Color(0, 0, 0));
        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField2.setText("0");
        jFormattedTextField2.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 12)); // NOI18N
        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyReleased(evt);
            }
        });

        jFormattedTextField1.setForeground(new java.awt.Color(0, 0, 0));
        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextField1.setText("0");
        jFormattedTextField1.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 12)); // NOI18N
        jFormattedTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField1KeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText(" Selling Price From");

        jLabel7.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("To");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel7)
                .addGap(15, 15, 15)
                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(10, 10, 10))
        );

        jPanel20.setBackground(new java.awt.Color(121, 203, 96));

        sortBy1.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 16)); // NOI18N
        sortBy1.setForeground(new java.awt.Color(0, 0, 0));
        sortBy1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Product ID Ascending Order", "Product ID Descending Order", "Product Name Ascending Order", "Product Name Descending Order", "Product Selling Price Ascending Order", "Product Selling Price Descending Order" }));
        sortBy1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                sortBy1ItemStateChanged(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 22)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText(" Sort By :-");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(sortBy1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sortBy1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel14Layout.createSequentialGroup()
                                    .addComponent(selectMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(6, 6, 6)
                                    .addComponent(saveMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(44, 44, 44)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel14Layout.createSequentialGroup()
                                    .addComponent(addMaterialText, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(6, 6, 6)
                                    .addComponent(addMaterialBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel14Layout.createSequentialGroup()
                            .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(68, 68, 68)
                            .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(68, 68, 68)
                            .addComponent(clear_all_products, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(product_id))
                            .addGap(23, 23, 23)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(product_name))
                            .addGap(23, 23, 23)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(product_price)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))))
                .addGap(22, 22, 22))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel14Layout.createSequentialGroup()
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(product_id, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel14Layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(product_price, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(product_name, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addMaterialText)
                    .addComponent(selectMaterial)
                    .addComponent(addMaterialBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clear_all_products, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel15.setBackground(new java.awt.Color(215, 249, 204));
        jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 5, true));

        jPanel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));

        jScrollPane1.setBorder(null);

        jTable1.setBackground(new java.awt.Color(215, 249, 204));
        jTable1.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 14)); // NOI18N
        jTable1.setForeground(new java.awt.Color(0, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product Name", "Product_Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel16.setBackground(new java.awt.Color(215, 249, 204));
        jPanel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 5, true));

        clear_all_stocks.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 24)); // NOI18N
        clear_all_stocks.setForeground(new java.awt.Color(0, 0, 0));
        clear_all_stocks.setText("Clear All");
        clear_all_stocks.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clear_all_stocks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_all_stocksActionPerformed(evt);
            }
        });

        jPanel19.setBackground(new java.awt.Color(121, 203, 96));

        sortBy.setFont(new java.awt.Font("JetBrains Mono SemiBold", 0, 16)); // NOI18N
        sortBy.setForeground(new java.awt.Color(0, 0, 0));
        sortBy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Stock ID Ascending Order", "Stock ID Descending Order", "Stock Quantity Ascending Order", "Stock Quantity Descending Order" }));
        sortBy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                sortByItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 22)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText(" Sort By :-");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(sortBy, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sortBy, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clear_all_stocks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(clear_all_stocks, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel25.setBackground(new java.awt.Color(215, 249, 204));
        jPanel25.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 5, true));

        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));

        jScrollPane2.setBorder(null);

        jTable2.setBackground(new java.awt.Color(215, 249, 204));
        jTable2.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 14)); // NOI18N
        jTable2.setForeground(new java.awt.Color(0, 0, 0));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Material ID", "Material Name", "Material Stock Qty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel9.setBackground(new java.awt.Color(204, 229, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(121, 203, 96), 3, true));

        jLabel11.setFont(new java.awt.Font("Retro Signed", 0, 72)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("PRODUCTS & STOCKS");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(star_label, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 774, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(star_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panel_loader.setViewportView(jPanel7);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_loader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(panel_loader, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton1ActionPerformed
        // go to home
        firstClear();
        secondClear();
        this.dispose();
        home.setVisible(true);
        home.highlitedTheButton();
    }//GEN-LAST:event_menuButton1ActionPerformed

    private void menuButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton3ActionPerformed
        // customer panel load
        firstClear();
        secondClear();
        this.dispose();
        customer_Registration.setVisible(true);
    }//GEN-LAST:event_menuButton3ActionPerformed

    private void menuButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton4ActionPerformed
        // supplier panel load
        firstClear();
        secondClear();
        this.dispose();
        supplier_Registration.setVisible(true);
    }//GEN-LAST:event_menuButton4ActionPerformed

    private void menuButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton5ActionPerformed
        // GRN panel load
        firstClear();
        secondClear();
        this.dispose();
        grn.setVisible(true);
    }//GEN-LAST:event_menuButton5ActionPerformed

    private void menuButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton6ActionPerformed
        // earning panel load
    }//GEN-LAST:event_menuButton6ActionPerformed

    private void menuButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton2ActionPerformed
        // current JFrame
    }//GEN-LAST:event_menuButton2ActionPerformed

    private void bill_btn_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bill_btn_txtActionPerformed
        // invoice panel load
        this.dispose();
        invoice.setVisible(true);
    }//GEN-LAST:event_bill_btn_txtActionPerformed

    private void billToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billToActionPerformed
        // invoice panel load
        this.dispose();
        invoice.setVisible(true);
    }//GEN-LAST:event_billToActionPerformed

    private void user_logoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_user_logoMouseClicked
        // go to register
        firstClear();
        secondClear();
        this.dispose();
        register.setVisible(true);
    }//GEN-LAST:event_user_logoMouseClicked

    private void logout_logoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logout_logoMouseClicked
        // go to login
        firstClear();
        secondClear();
        this.dispose();
        Employee_Address.backToHomeChange = 0;
        signIn.setVisible(true);
    }//GEN-LAST:event_logout_logoMouseClicked

    private void addMaterialBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMaterialBtnActionPerformed

        // add new material
        String material = addMaterialText.getText();

        if (material.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the New Material!");
        } else {

            try {

                ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `material` WHERE `name` = '" + material + "'");

                if (resultSet.next()) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "This Material has been already existed!");
                } else {

                    MySQL.executeIUD("INSERT INTO `material` (`name`) VALUES ('" + material + "')");

                    loadMaterials();
                    addMaterialText.setText("");
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, 3500l, "Added new Material!");

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_addMaterialBtnActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed

        // add new product
        String PrID = product_id.getText();
        String PrName = product_name.getText();
        String PrPrice = product_price.getText();

        if (PrID.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Product ID!");
        } else if (PrName.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Product Name!");
        } else if (PrPrice.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Product Price!");
        } else if (!PrPrice.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Product Price!");
        } else {

            try {

                ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `product` WHERE `id` = '" + PrID + "' OR `name` = '" + PrName + "'");

                if (resultSet.next()) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "This Product ID or Product Name already existed!");
                } else {

                    if (jTable3.getRowCount() == 0) {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please add a Materials!");
                    } else {

                        MySQL.executeIUD("INSERT INTO `product` (`id`,`name`,`price`) VALUES ('" + PrID + "', '" + PrName + "', '" + PrPrice + "')");

                        for (int i = 0; i < jTable3.getRowCount(); i++) {

                            String material_name = String.valueOf(jTable3.getValueAt(i, 1));
                            int material_id = materialMap.get(material_name);

                            MySQL.executeIUD("INSERT INTO `product_has_material` (`product_id`,`material_id`) VALUES ('" + PrID + "', '" + material_id + "')");

                        }

                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, 3500l, "Product added Successfully!");

                        firstClear();
                        loadProducts();
                        uniqueId = 0;

                    }

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_addActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed

        // update product
        String PrID = product_id.getText();
        String PrName = product_name.getText();
        String PrPrice = product_price.getText();

        if (PrName.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Product Name!");
        } else if (PrPrice.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Product Price!");
        } else if (!PrPrice.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Product Price!");
        } else {

            try {

                ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `product` WHERE `name` = '" + PrName + "'");

                boolean canUpdate = false;

                if (resultSet.next()) {

                    if (resultSet.getString("id").equals(PrID)) {
                        canUpdate = true;
                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "This Product Name already existed!");
                    }

                } else {
                    canUpdate = true;
                }

                if (canUpdate) {

                    if (jTable3.getRowCount() == 0) {
                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please add a Materials!");
                    } else {

                        MySQL.executeIUD("UPDATE `product` SET `name` = '" + PrName + "', `price` = '" + PrPrice + "' WHERE `id` = '" + PrID + "'");

                        MySQL.executeIUD("DELETE FROM `product_has_material` WHERE `product_id` = '" + PrID + "'");

                        for (int i = 0; i < jTable3.getRowCount(); i++) {

                            String material_name = String.valueOf(jTable3.getValueAt(i, 1));
                            int material_id = materialMap.get(material_name);

                            MySQL.executeIUD("INSERT INTO `product_has_material` (`product_id`,`material_id`) VALUES ('" + PrID + "', '" + material_id + "')");

                        }

                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, 3500l, "Product updated Successfully!");

                        firstClear();
                        loadProducts();
                        uniqueId = 0;

                    }

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_updateActionPerformed

    private void clear_all_productsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_all_productsActionPerformed
        // reset fields
        firstClear();
        loadProducts();
    }//GEN-LAST:event_clear_all_productsActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked

        // delete a row
        if (evt.getClickCount() == 2) {

            int row = jTable3.getSelectedRow();

            if (row != -1) {
                DefaultTableModel dtm = (DefaultTableModel) jTable3.getModel();
                dtm.removeRow(row);
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Row!");
            }

        }

    }//GEN-LAST:event_jTable3MouseClicked

    private void saveMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMaterialActionPerformed

        // add the materials to the table
        String material = String.valueOf(selectMaterial.getSelectedItem());

        if (material.equals("Select Material")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Material!");
        } else {

            DefaultTableModel dtm = (DefaultTableModel) jTable3.getModel();
            boolean materialExists = false;

            for (int i = 0; i < dtm.getRowCount(); i++) {
                if (String.valueOf(dtm.getValueAt(i, 1)).equals(material)) {
                    materialExists = true;
                    break;
                }
            }

            if (materialExists) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Material already added to the table!");
            } else {
                uniqueId++;

                Vector<String> vector = new Vector<>();
                vector.add(String.valueOf(uniqueId));
                vector.add(material);

                dtm.addRow(vector);
                jTable3.setModel(dtm);
            }
        }

    }//GEN-LAST:event_saveMaterialActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        // set details to update
        if (evt.getClickCount() == 2) {

            jTable1.setEnabled(false);

            int row = jTable1.getSelectedRow();

            String PrID = String.valueOf(jTable1.getValueAt(row, 0));
            String PrName = String.valueOf(jTable1.getValueAt(row, 1));
            String PrPrice = String.valueOf(jTable1.getValueAt(row, 2));

            product_id.setEnabled(false);
            product_id.setText(PrID);
            product_name.setText(PrName);
            product_price.setText(PrPrice);
            add.setEnabled(false);
            update.setEnabled(true);
            update.grabFocus();

            sortBy1.setEnabled(false);
            jFormattedTextField1.setEnabled(false);
            jFormattedTextField2.setEnabled(false);

            loadProdutsDetailsToUpdate();

        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void sortBy1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sortBy1ItemStateChanged
        // sort by products
        loadProducts();
    }//GEN-LAST:event_sortBy1ItemStateChanged

    private void jFormattedTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField2KeyReleased
        // sort by products
        loadProducts();
    }//GEN-LAST:event_jFormattedTextField2KeyReleased

    private void jFormattedTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyReleased
        // sort by products
        loadProducts();
    }//GEN-LAST:event_jFormattedTextField1KeyReleased

    private void clear_all_stocksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_all_stocksActionPerformed
        // reset fields
        secondClear();
        loadStock();
    }//GEN-LAST:event_clear_all_stocksActionPerformed

    private void sortByItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sortByItemStateChanged
        // load stock
        loadStock();
    }//GEN-LAST:event_sortByItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton addMaterialBtn;
    private javax.swing.JTextField addMaterialText;
    private javax.swing.JButton billTo;
    private javax.swing.JButton bill_btn_txt;
    private javax.swing.JButton clear_all_products;
    private javax.swing.JButton clear_all_stocks;
    private javax.swing.JLabel date_label;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel logout_logo;
    private javax.swing.JButton menuButton1;
    private javax.swing.JButton menuButton2;
    private javax.swing.JButton menuButton3;
    private javax.swing.JButton menuButton4;
    private javax.swing.JButton menuButton5;
    private javax.swing.JButton menuButton6;
    private javax.swing.JLabel menu_logo;
    private javax.swing.JScrollPane panel_loader;
    private javax.swing.JTextField product_id;
    private javax.swing.JTextField product_name;
    private javax.swing.JTextField product_price;
    private javax.swing.JButton saveMaterial;
    private javax.swing.JComboBox<String> selectMaterial;
    private javax.swing.JComboBox<String> sortBy;
    private javax.swing.JComboBox<String> sortBy1;
    private javax.swing.JLabel star_label;
    private javax.swing.JLabel time_label;
    private javax.swing.JButton update;
    private javax.swing.JLabel user_logo;
    // End of variables declaration//GEN-END:variables

    // clear the field in first section
    private void firstClear() {
        product_id.setText("");
        product_name.setText("");
        product_price.setText("");
        selectMaterial.setSelectedIndex(0);
        addMaterialText.setText("");

        product_id.setEnabled(true);
        add.setEnabled(true);
        update.setEnabled(false);
        jTable1.setEnabled(true);

        DefaultTableModel dtm = (DefaultTableModel) jTable3.getModel();
        dtm.setRowCount(0);

        sortBy1.setEnabled(true);
        jFormattedTextField1.setEnabled(true);
        jFormattedTextField2.setEnabled(true);

        jFormattedTextField1.setText("");
        jFormattedTextField2.setText("");
        sortBy1.setSelectedIndex(0);
        loadProducts();

        product_id.grabFocus();
    }

    private void loadProdutsDetailsToUpdate() {

        try {

            DefaultTableModel dtm = (DefaultTableModel) jTable3.getModel();
            boolean materialExists = false;

            int row = jTable1.getSelectedRow();

            String PrID = String.valueOf(jTable1.getValueAt(row, 0));

            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `product_has_material` INNER JOIN `material` ON "
                    + "`product_has_material`.`material_id` = `material`.`id` WHERE `product_has_material`.`product_id` = '" + PrID + "'");

            while (resultSet.next()) {
                for (int i = 0; i < dtm.getRowCount(); i++) {
                    if (String.valueOf(dtm.getValueAt(i, 1)).equals(resultSet.getString("material.name"))) {
                        materialExists = true;
                        break;
                    }
                }

                if (!materialExists) {
                    uniqueId++;

                    Vector<String> vector = new Vector<>();
                    vector.add(String.valueOf(uniqueId));
                    vector.add(resultSet.getString("material.name"));

                    dtm.addRow(vector);
                }
            }

            jTable3.setModel(dtm);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void secondClear() {
        sortBy.setSelectedIndex(0);
        sortBy.grabFocus();
    }

}
