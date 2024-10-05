/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;
import raven.toast.Notifications;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import model.InvoiceItem;
import model.MySQL;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LoggerUtils;
import model.customizedTable;
import net.sf.jasperreports.engine.JasperExportManager;

/**
 *
 * @author User
 */
public class Invoice extends javax.swing.JFrame {

    private static HashMap<String, Integer> pmethods = new HashMap<>();
    private static Home home;
    private static InvoiceItem invoiceItem = new InvoiceItem();
    private static SignIn signIn;
    private static String user;
    private static boolean saveInvoice = false;
    private static boolean ItemExists = false;
    private static boolean updatePoints = false;
    private static boolean stockOk = true;
    private static String newPointsCount;
    private static double usedPoints;
    private static Products products;
    private static Logger logger = LoggerUtils.getLogger();

    /**
     * Creates new form Invoice
     */
    public Invoice(Home home, Products products) {
        this.products = products;
        this.home = home;
        initComponents();
        this.setExtendedState(Home.MAXIMIZED_BOTH);
        init();
        styleTheTextFieldsAndButtons();
        generateInvoiceId();
        loadPaymentMethods();
        calTotal();

        // set notification position
        Notifications.getInstance();

        // add a icon image
        Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/icon.png"));
        this.setIconImage(image);

        // customize the table
        customizedTable.TableStyle(jTable1);
        customizedTable.centeredValues(jTable1);

        // set disable buttons
        total_lable.setEnabled(false);
        balance_label.setEnabled(false);

        discount_per.setText("0");
        paying_price.setText("0");
    }

    // set customer details
    public void setCustomerDetails() {
        jLabel5.setText(invoiceItem.getCustomer_mobile());
        jLabel9.setText(invoiceItem.getCustomer_name());
        jLabel12.setText(invoiceItem.getCustomer_points());

        String points = jLabel12.getText();

        double points_count = Double.parseDouble(points);

        if (points_count <= 0) {
            jCheckBox1.setEnabled(false);
        } else {
            jCheckBox1.setEnabled(true);
        }
    }

    // set customer details
    public void setProductDetails() {
        jLabel11.setText(invoiceItem.getProduct_id());
        jLabel19.setText(invoiceItem.getProduct_name());
        jLabel15.setText(String.valueOf(invoiceItem.getProduct_price()));
    }

    // load methods
    private void loadPaymentMethods() {

        try {

            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `payment_method`");

            Vector<String> vector = new Vector<>();

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                pmethods.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel dcbm = new DefaultComboBoxModel(vector);
            payment_method.setModel(dcbm);

        } catch (SQLException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Load Methods Error in the Invoice!", e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Load Methods Error in the Invoice!", e);
        }

    }

    // get use email with a bean
    public void getUserEmail(SignIn signIn) {
        this.signIn = signIn;
        if (!(this.signIn.getUser() == null)) {
            user = this.signIn.getUser();
            jLabel3.setText(user);
        }
    }

    // store the email staticly
    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b && user != null) {
            jLabel3.setText(user);
        }
    }

    // genarate unique GRN Id
    private void generateInvoiceId() {
        long id = System.currentTimeMillis();
        jLabel6.setText(String.valueOf(id));
    }

    // style the text fields and buttons
    private void styleTheTextFieldsAndButtons() {
        qty.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Quantity");
        qty.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");
        qty.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);

        total_lable.putClientProperty("JComponent.roundRect", true);
        total_lable.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");

        discount_per.putClientProperty("JComponent.roundRect", true);
        discount_per.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Discount Percentage");
        discount_per.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");

        payment_method.putClientProperty("JComponent.roundRect", true);

        paying_price.putClientProperty("JComponent.roundRect", true);
        paying_price.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Paying Price");
        paying_price.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");

        balance_label.putClientProperty("JComponent.roundRect", true);
        balance_label.putClientProperty(FlatClientProperties.STYLE, "margin:0, 20, 0, 20");

        selectCustomer.putClientProperty("JButton.buttonType", "roundRect");
        selectProduct.putClientProperty("JButton.buttonType", "roundRect");
        addInvoiceItem.putClientProperty("JButton.buttonType", "roundRect");
        clear.putClientProperty("JButton.buttonType", "roundRect");
        invoicePrint.putClientProperty("JButton.buttonType", "roundRect");
        clearall.putClientProperty("JButton.buttonType", "roundRect");
    }

    private void init() {
        // icons, logos & images load
        FlatSVGIcon back = new FlatSVGIcon(this.getClass().getResource("/resources/backTo.svg"));
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        backLogo = new javax.swing.JPanel();
        back_label = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        selectCustomer = new javax.swing.JButton();
        selectProduct = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        qty = new javax.swing.JFormattedTextField();
        addInvoiceItem = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        invoicePrint = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        discount_per = new javax.swing.JFormattedTextField();
        jLabel26 = new javax.swing.JLabel();
        balance_label = new javax.swing.JFormattedTextField();
        total_lable = new javax.swing.JFormattedTextField();
        clearall = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        paying_price = new javax.swing.JFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        payment_method = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Invoice");
        setFont(new java.awt.Font("JetBrains Mono", 0, 8)); // NOI18N
        setResizable(false);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel2.setPreferredSize(new java.awt.Dimension(1260, 1382));

        jLabel14.setBackground(new java.awt.Color(121, 203, 96));
        jLabel14.setFont(new java.awt.Font("Retro Signed", 0, 81)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("INVOICE");
        jLabel14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel14.setOpaque(true);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        backLogo.setBackground(new java.awt.Color(121, 203, 96));
        backLogo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        backLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backLogobackToHome(evt);
            }
        });

        back_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

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
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        backLogoLayout.setVerticalGroup(
            backLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
            .addComponent(back_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(204, 229, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(121, 203, 96), 3, true));

        jLabel3.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText(" Employee Email :-");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel5.setBackground(new java.awt.Color(204, 229, 255));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(121, 203, 96), 3, true));

        jLabel6.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText(" Invoice Number :-");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        selectCustomer.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        selectCustomer.setForeground(new java.awt.Color(0, 0, 0));
        selectCustomer.setText("Select Customer");
        selectCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectCustomerActionPerformed(evt);
            }
        });

        selectProduct.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 18)); // NOI18N
        selectProduct.setForeground(new java.awt.Color(0, 0, 0));
        selectProduct.setText("Select Product");
        selectProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectProductActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(121, 203, 96));
        jLabel4.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText(" Customer Mobile :-");
        jLabel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel4.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(121, 203, 96));
        jLabel5.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel5.setOpaque(true);

        jLabel8.setBackground(new java.awt.Color(121, 203, 96));
        jLabel8.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText(" Customer Name :-");
        jLabel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel8.setOpaque(true);

        jLabel9.setBackground(new java.awt.Color(121, 203, 96));
        jLabel9.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel9.setOpaque(true);

        jLabel10.setBackground(new java.awt.Color(121, 203, 96));
        jLabel10.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText(" Product ID :-");
        jLabel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel10.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(121, 203, 96));
        jLabel11.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel11.setOpaque(true);

        jLabel13.setBackground(new java.awt.Color(121, 203, 96));
        jLabel13.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText(" Product Price :-");
        jLabel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel13.setOpaque(true);

        jLabel15.setBackground(new java.awt.Color(121, 203, 96));
        jLabel15.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel15.setOpaque(true);

        jLabel16.setBackground(new java.awt.Color(121, 203, 96));
        jLabel16.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText(" Points Count :-");
        jLabel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel16.setOpaque(true);

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText(" Buying Quantity :-");
        jLabel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 2, true));
        jLabel17.setOpaque(true);

        qty.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 2, true));
        qty.setForeground(new java.awt.Color(0, 0, 0));
        qty.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        qty.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        qty.setText("0");
        qty.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N

        addInvoiceItem.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        addInvoiceItem.setForeground(new java.awt.Color(0, 0, 0));
        addInvoiceItem.setText("Add Invoice Item");
        addInvoiceItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addInvoiceItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addInvoiceItemActionPerformed(evt);
            }
        });

        clear.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        clear.setForeground(new java.awt.Color(0, 0, 0));
        clear.setText("Clear");
        clear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));

        jTable1.setBackground(new java.awt.Color(215, 249, 204));
        jTable1.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 14)); // NOI18N
        jTable1.setForeground(new java.awt.Color(0, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product Name", "Product Price", "Buying Quantity", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(215, 249, 204));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));

        invoicePrint.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        invoicePrint.setForeground(new java.awt.Color(0, 0, 0));
        invoicePrint.setText("Print Invoice");
        invoicePrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        invoicePrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invoicePrintActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText(" Balance :-");

        jLabel24.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText(" Discount Percentage :-");

        discount_per.setForeground(new java.awt.Color(0, 0, 0));
        discount_per.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        discount_per.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        discount_per.setText("0");
        discount_per.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N
        discount_per.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                discount_perKeyReleased(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText(" Total :-");

        balance_label.setForeground(new java.awt.Color(0, 0, 0));
        balance_label.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        balance_label.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        balance_label.setText("0");
        balance_label.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N

        total_lable.setForeground(new java.awt.Color(0, 0, 0));
        total_lable.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        total_lable.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total_lable.setText("0");
        total_lable.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N

        clearall.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        clearall.setForeground(new java.awt.Color(0, 0, 0));
        clearall.setText("Clear All");
        clearall.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearallActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText(" Paying Price :-");

        paying_price.setForeground(new java.awt.Color(0, 0, 0));
        paying_price.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        paying_price.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        paying_price.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N
        paying_price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                paying_priceKeyReleased(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText(" Withdraw Points :-");

        jLabel28.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText(" Payment Methood :-");

        jCheckBox1.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(0, 0, 0));
        jCheckBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jCheckBox1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        jCheckBox1.setMaximumSize(new java.awt.Dimension(25, 25));
        jCheckBox1.setMinimumSize(new java.awt.Dimension(25, 25));
        jCheckBox1.setPreferredSize(new java.awt.Dimension(25, 25));
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        payment_method.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N
        payment_method.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                payment_methodItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(invoicePrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(discount_per, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .addComponent(balance_label)
                            .addComponent(total_lable)
                            .addComponent(paying_price, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(payment_method, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(clearall, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total_lable, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(discount_per, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(payment_method))
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paying_price, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(balance_label, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(invoicePrint, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(clearall, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jLabel12.setBackground(new java.awt.Color(121, 203, 96));
        jLabel12.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel12.setOpaque(true);

        jLabel18.setBackground(new java.awt.Color(121, 203, 96));
        jLabel18.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText(" Product Name :-");
        jLabel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel18.setOpaque(true);

        jLabel19.setBackground(new java.awt.Color(121, 203, 96));
        jLabel19.setFont(new java.awt.Font("JetBrains Mono SemiBold", 1, 16)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));
        jLabel19.setOpaque(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(backLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addInvoiceItem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(selectCustomer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 98, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(selectProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(qty)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(backLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addInvoiceItem, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backLogobackToHome(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backLogobackToHome
        //  back to home
        this.dispose();
        home.setVisible(true);
    }//GEN-LAST:event_backLogobackToHome

    private void selectCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectCustomerActionPerformed
        // open select customer JFrame Form
        this.setEnabled(false);
        InvoiceSelectCustomer invoiceSelectCustomer = new InvoiceSelectCustomer(this, invoiceItem);
        invoiceSelectCustomer.setVisible(true);
    }//GEN-LAST:event_selectCustomerActionPerformed

    private void selectProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectProductActionPerformed
        // open select customer JFrame Form
        this.setEnabled(false);
        InvoiceSelectProduct invoiceSelectProduct = new InvoiceSelectProduct(this, invoiceItem);
        invoiceSelectProduct.setVisible(true);
    }//GEN-LAST:event_selectProductActionPerformed

    private void addInvoiceItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addInvoiceItemActionPerformed

        // add invoice item to table
        String employee_email = jLabel3.getText();
        String invoice_number = jLabel6.getText();
        String customer_mobile = jLabel5.getText();
        String customer_name = jLabel9.getText();
        String customer_points = jLabel12.getText();
        String product_id = jLabel11.getText();
        String product_name = jLabel19.getText();
        String product_price = jLabel15.getText();
        String buying_qty = qty.getText();

        if (employee_email.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Something went Wrong!");
        } else if (invoice_number.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Something went Wrong!");
        } else if (customer_mobile.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Customer!");
        } else if (customer_name.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Customer!");
        } else if (customer_points.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Customer!");
        } else if (product_id.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Product!");
        } else if (product_name.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Product!");
        } else if (product_price.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Product!");
        } else if (buying_qty.isEmpty() || buying_qty.equals("0") || buying_qty.equals("0.00")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Buying Quantity!");
        } else if (!buying_qty.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Buying Quantity!");
        } else {

            selectCustomer.setEnabled(false);

            try {

                double product_price_value = Double.parseDouble(product_price);
                double buying_qty_value = Double.parseDouble(buying_qty);

                ItemExists = false;

                DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();

                for (int i = 0; i < dtm.getRowCount(); i++) {
                    if (String.valueOf(dtm.getValueAt(i, 0)).equals(product_id)) {

                        double existingQty = Double.parseDouble(String.valueOf(dtm.getValueAt(i, 3)));

                        double updatedQty = existingQty + buying_qty_value;
                        double updatedTotal = updatedQty * product_price_value;

                        dtm.setValueAt(String.valueOf(updatedQty), i, 3);
                        dtm.setValueAt(String.valueOf(updatedTotal), i, 4);

                        ItemExists = true;
                        break;
                    }
                }

                if (!ItemExists) {

                    Vector<String> vector = new Vector<>();
                    vector.add(product_id);
                    vector.add(product_name);
                    vector.add(product_price);
                    vector.add(String.valueOf(buying_qty_value));
                    vector.add(String.valueOf(product_price_value * buying_qty_value));

                    dtm.addRow(vector);

                }

                jTable1.setModel(dtm);
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, 3500l, "Invoice added Successfully!");
                normalReset();
                calTotal();
                calPoints();
                calBalance();

            } catch (Exception e) {
                e.printStackTrace();
                logger.log(Level.WARNING, "Add Invoice Error!", e);
            }

        }

    }//GEN-LAST:event_addInvoiceItemActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        // reset
        normalReset();
    }//GEN-LAST:event_clearActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        // get a values to fields
        int row = jTable1.getSelectedRow();

        if (row != -1) {

            String productID = String.valueOf(jTable1.getValueAt(row, 0));
            String productName = String.valueOf(jTable1.getValueAt(row, 1));
            String productPrice = String.valueOf(jTable1.getValueAt(row, 2));
            String buyingQty = String.valueOf(jTable1.getValueAt(row, 3));

            jLabel11.setText(productID);
            jLabel19.setText(productName);
            jLabel15.setText(productPrice);
            qty.setText(buyingQty);

            jTable1.setEnabled(false);

            if (evt.getClickCount() == 2) {

                if (row != -1) {
                    DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                    dtm.removeRow(row);
                    calTotal();
                    calPoints();
                    calBalance();
                    jTable1.setEnabled(true);
                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Row!");
                }

            }

        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void invoicePrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_invoicePrintActionPerformed

        // save and print invoice
        try {

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();

            if (dtm.getRowCount() == 0) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please add a Invoice to the Table!");
            } else {

                String employee_email = jLabel3.getText();
                String invoice_number = jLabel6.getText();
                String customer_mobile = jLabel5.getText();
                String customer_name = jLabel9.getText();
                String method = String.valueOf(payment_method.getSelectedItem());
                String paying = paying_price.getText();
                String points = jLabel12.getText();
                String total = total_lable.getText();

                double cus_points = Double.parseDouble(points);
                double totalValue = Double.parseDouble(total);

                double balanceValue = 0;

                if (employee_email.isEmpty()) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Something went Wrong!");
                } else if (invoice_number.isEmpty()) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Something went Wrong!");
                } else {

                    saveInvoice = false;

                    calTotal();
                    calPoints();

                    if (method.equals("Cash")) {

                        if (paying.isEmpty() || paying.equals("0") || paying.equals("0.00")) {

                            if (totalValue == 0) {
                                saveInvoice = true;
                                balanceValue = 0;
                            } else {
                                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please enter the Paying Price!");
                            }

                        } else if (!paying.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Paying Price!");
                        } else {
                            calBalance();

                            String balance = balance_label.getText();
                            balanceValue = Double.parseDouble(balance);

                            if (balanceValue == 0) {
                                balanceValue = 0;
                            }

                            if (balanceValue < 0) {
                                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Paying Price!");
                            } else {
                                saveInvoice = true;
                            }

                        }

                    } else {
                        saveInvoice = true;
                    }

                    String discount = discount_per.getText();
                    double discountValue = Double.parseDouble(discount);

                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String invoiceDate = sdf.format(date);

                    int methodId = pmethods.get(method);

                    stockOk = true;

                    if (saveInvoice) {

                        for (int i = 0; i < dtm.getRowCount(); i++) {

                            String PrID = String.valueOf(jTable1.getValueAt(i, 0));
                            String buyingQty = String.valueOf(jTable1.getValueAt(i, 3));
                            double buyingQtyValue = Double.parseDouble(buyingQty);

                            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `product_has_material` WHERE `product_id` = '" + PrID + "'");

                            while (resultSet.next()) {

                                String MaterialID = resultSet.getString("material_id");

                                ResultSet resultSet1 = MySQL.executeSearch("SELECT * FROM `stock` WHERE `material_id` = '" + MaterialID + "'");

                                if (!resultSet1.next()) {
                                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Don't have a Stock!");
                                    stockOk = false;
                                } else {

                                    String stockQty = resultSet1.getString("qty");
                                    double availableStockQty = Double.parseDouble(stockQty);

                                    if (availableStockQty < buyingQtyValue) {
                                        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Don't have a enough Stock!");
                                        stockOk = false;
                                    }

                                }

                            }

                        }

                        if (stockOk) {

                            for (int i = 0; i < dtm.getRowCount(); i++) {

                                String PrID = String.valueOf(jTable1.getValueAt(i, 0));
                                String buyingQty = String.valueOf(jTable1.getValueAt(i, 3));
                                double buyingQtyValue = Double.parseDouble(buyingQty);

                                ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `product_has_material` WHERE `product_id` = '" + PrID + "'");

                                while (resultSet.next()) {
                                    String MaterialID = resultSet.getString("material_id");
                                    MySQL.executeIUD("UPDATE `stock` SET `qty` = `qty` - '" + buyingQtyValue + "' WHERE `material_id` = '" + MaterialID + "'");
                                }

                            }

                            if (updatePoints) {

                                jLabel12.setText(newPointsCount);

                                MySQL.executeIUD("UPDATE `customer` SET `points` = '" + newPointsCount + "' WHERE `mobile` = '" + customer_mobile + "'");

                                usedPoints = cus_points - Double.parseDouble(newPointsCount);

                            } else {

                                usedPoints = 0;

                            }

                            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `invoice` WHERE `id` = '" + invoice_number + "'");

                            if (resultSet.next()) {
                                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Something went wrong!");
                            } else {

                                if (method.equals("Card")) {
                                    paying = total;
                                }

                                if (discount.isEmpty() || discountValue == 0) {
                                    discountValue = 0;
                                }

                                MySQL.executeIUD("INSERT INTO `invoice` (`id`,`date`,`total`,`discount_percentage`,`withdraw_points_count`,`paid_amount`,`payment_method_id`,`employee_email`,`customer_mobile`) "
                                        + "VALUES ('" + invoice_number + "', '" + invoiceDate + "', '" + total + "', '" + discountValue + "', '" + usedPoints + "', '" + paying + "', '" + methodId + "', '" + employee_email + "', '" + customer_mobile + "')");

                            }

                            for (int i = 0; i < dtm.getRowCount(); i++) {

                                String PrID = String.valueOf(jTable1.getValueAt(i, 0));
                                String prBuyingQty = String.valueOf(jTable1.getValueAt(i, 3));

                                MySQL.executeIUD("INSERT INTO `invoice_item` (`qty`,`product_id`,`invoice_id`) VALUES "
                                        + "('" + prBuyingQty + "', '" + PrID + "', '" + invoice_number + "')");

                            }

                            MySQL.executeIUD("UPDATE `customer` SET `points` = `points` + '5' WHERE `mobile` = '" + customer_mobile + "'");

                            HashMap<String, Object> params = new HashMap<>();
                            params.put("Parameter1", customer_name);
                            params.put("Parameter2", customer_mobile);
                            params.put("Parameter3", invoice_number);
                            params.put("Parameter4", employee_email);
                            params.put("Parameter5", total);
                            params.put("Parameter6", String.valueOf(discountValue + " %"));
                            params.put("Parameter7", String.valueOf(usedPoints));
                            params.put("Parameter8", method);
                            params.put("Parameter9", paying);
                            params.put("Parameter10", String.valueOf(balanceValue));

                            String path = "src//reports//Bubble_Bay_Invoice_Report.jasper";

                            Connection connection = MySQL.getConnection();

                            JasperPrint report = JasperFillManager.fillReport(path, params, connection);

                            JasperViewer viewer = new JasperViewer(report, false);

                            viewer.setBounds(2, 20, 800, 500);
                            viewer.setLocationRelativeTo(null);

                            viewer.setVisible(true);

                            String folderPath = "reports//Customer_Invoices//";
                            String fileName = folderPath + invoice_number + "_" + invoiceDate + ".pdf";

                            JasperExportManager.exportReportToPdfFile(report, fileName);

                            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, 3500l, "Invoice saved Successfully!");
                            fullReset();
                            calTotal();

                            products.loadStock();
                            home.summaryUpdate();

                        }

                    }

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Save Invoice Error!", e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Save Invoice Error!", e);
        }

    }//GEN-LAST:event_invoicePrintActionPerformed

    private void discount_perKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_discount_perKeyReleased
        // when enter the discount
        calTotal();
        calPoints();
        calBalance();
    }//GEN-LAST:event_discount_perKeyReleased

    private void clearallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearallActionPerformed
        // reset
        fullReset();
    }//GEN-LAST:event_clearallActionPerformed

    private void paying_priceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paying_priceKeyReleased
        // when enter the paying price
        calTotal();
        calPoints();
        calBalance();
    }//GEN-LAST:event_paying_priceKeyReleased

    private void payment_methodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_payment_methodItemStateChanged

        // set enable or disable paying price field
        String method = String.valueOf(payment_method.getSelectedItem());

        if (method.equals("Cash")) {
            paying_price.setEnabled(true);
            calTotal();
            calPoints();
            calBalance();
        } else if (method.equals("Card")) {
            paying_price.setEnabled(false);
            paying_price.setText("0");
            balance_label.setText("0");
        }

    }//GEN-LAST:event_payment_methodItemStateChanged

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed

        // when check the withdraw points
        String points = jLabel12.getText();

        if (!points.isEmpty()) {

            double pointsValue = Double.parseDouble(points);

            if (pointsValue > 0) {
                calTotal();
                calPoints();
                calBalance();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Points Count!");
                jCheckBox1.setSelected(false);
            }

        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Please select a Customer!");
            jCheckBox1.setSelected(false);
        }

    }//GEN-LAST:event_jCheckBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addInvoiceItem;
    private javax.swing.JPanel backLogo;
    private javax.swing.JLabel back_label;
    private javax.swing.JFormattedTextField balance_label;
    private javax.swing.JButton clear;
    private javax.swing.JButton clearall;
    private javax.swing.JFormattedTextField discount_per;
    private javax.swing.JButton invoicePrint;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JFormattedTextField paying_price;
    private javax.swing.JComboBox<String> payment_method;
    private javax.swing.JFormattedTextField qty;
    private javax.swing.JButton selectCustomer;
    private javax.swing.JButton selectProduct;
    private javax.swing.JFormattedTextField total_lable;
    // End of variables declaration//GEN-END:variables

    // normal reset
    private void normalReset() {
        jLabel11.setText("");
        jLabel19.setText("");
        jLabel15.setText("");
        qty.setText("0");
        jTable1.clearSelection();
        jTable1.setEnabled(true);

        ItemExists = false;
    }

    // full reset
    private void fullReset() {
        normalReset();

        jLabel5.setText("");
        jLabel9.setText("");
        jLabel12.setText("");
        selectCustomer.setEnabled(true);

        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);

        total_lable.setText("0");
        discount_per.setText("0");
        jCheckBox1.setSelected(false);
        payment_method.setSelectedItem("Cash");
        paying_price.setText("0");
        balance_label.setText("0");

        updatePoints = false;
        newPointsCount = "0";
        saveInvoice = false;

        generateInvoiceId();
    }

    // cal total
    private void calTotal() {

        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();

        String discount = discount_per.getText();

        double total = 0;

        for (int i = 0; i < dtm.getRowCount(); i++) {
            total += Double.parseDouble(String.valueOf(jTable1.getValueAt(i, 4)));
        }

        if (discount.isEmpty() || discount.equals("0") || discount.equals("0.00")) {

            total_lable.setText(String.valueOf(total));
            discount_per.setText("0");

        } else {

            if (!discount.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Discount Percentage!");
            } else {

                double discount_value = Double.parseDouble(discount);

                if (discount_value >= 100) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Discount Percentage!");
                    discount_per.setText("0");
                    calTotal();
                } else {

                    double deduction_amount = total * discount_value / 100;

                    total_lable.setText(String.valueOf(total - deduction_amount));

                }

            }

        }

    }

    // cal balance
    private void calBalance() {

        String method = String.valueOf(payment_method.getSelectedItem());

        if (method.equals("Cash")) {

            String paying = paying_price.getText();
            String total = total_lable.getText();

            double totalValue = Double.parseDouble(total);

            if (paying.isEmpty() || paying.equals("0") || paying.equals("0.00")) {

                balance_label.setText(String.valueOf(0 - totalValue));
                paying_price.setText("0");

            } else {

                if (!paying.matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Invalid Paying Price!");
                } else {

                    double paying_value = Double.parseDouble(paying);

                    balance_label.setText(String.valueOf(paying_value - totalValue));

                }

            }

        }

    }

    // cal points
    private void calPoints() {

        calTotal();

        String points = jLabel12.getText();

        String total = total_lable.getText();

        if (!points.isEmpty() && !total.isEmpty()) {

            double totalValue = Double.parseDouble(total);

            double pointsValue = Double.parseDouble(points);

            if (pointsValue > 0 && totalValue > 0) {

                if (jCheckBox1.isSelected()) {

                    if (totalValue < pointsValue) {

                        total_lable.setText("0");

                        // update the customer points
                        updatePoints = true;
                        double updatedPoints = pointsValue - totalValue;
                        long roundedPoints = Math.round(updatedPoints);
                        newPointsCount = String.valueOf(roundedPoints);

                    } else {

                        double updatedTotal = totalValue - pointsValue;

                        total_lable.setText(String.valueOf(updatedTotal));

                        // update the customer points
                        updatePoints = true;
                        double updatedPoints = 0;
                        newPointsCount = String.valueOf(updatedPoints);

                    }

                }

            }

        }

    }

}
