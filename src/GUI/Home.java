/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.Timer;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LoggerUtils;
import model.MySQL;
import raven.toast.Notifications;

/**
 *
 * @author User
 */
public class Home extends javax.swing.JFrame {

    private static Employee_Registration register = new Employee_Registration();
    private static Products products = new Products();
    private static Reports reports = new Reports();
    private static Customer_Registration customer_Registration = new Customer_Registration();
    private static Supplier_Registration supplier_Registration = new Supplier_Registration();
    private static int currentIndex = 0;
    private static FlatSVGIcon[] sliderImages;
    private static int analyseIndex1 = 0;
    private static String[] analyseDetails1;
    private static int analyseIndex2 = 0;
    private static String[] analyseDetails2;
    private static SignIn signIn;
    private static String user;
    private static String fullName;
    private static Logger logger = LoggerUtils.getLogger();
    private static String employeeType;

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        this.setExtendedState(Home.MAXIMIZED_BOTH);
        init();
        sliderAnimation();
        summaryUpdate();
        getDateTime();
        setButtonBorderRadius();
        getEmployeeRole();

        // set notification position
        Notifications.getInstance();

        menuButton1.grabFocus();

        // add a icon image
        Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/icon.png"));
        this.setIconImage(image);
    }

    // highlited the current page button
    public void highlitedTheButton() {
        menuButton1.grabFocus();
    }

    // get use email with a bean
    public void getUserEmail(SignIn signIn) {
        this.signIn = signIn;
        if (!(this.signIn.getUser() == null)) {
            user = this.signIn.getUser();
            setUserName();
        }
    }

    // get employee role
    private void getEmployeeRole() {

        try {

            ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `employee` WHERE `email` = '" + user + "'");

            if (resultSet.next()) {
                String empType = resultSet.getString("employee_type_id");

                if (empType.equals("1")) {
                    employeeType = "Cashier";
                } else if (empType.equals("2")) {
                    employeeType = "Admin";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Something went wrong!", e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Something went wrong!", e);
        }

    }

    // store the email staticly
    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b && fullName != null) {
            jLabel4.setText(" Welcome! " + fullName + ".");
        }
    }

    // set user name
    private void setUserName() {

        try {
            ResultSet rs = MySQL.executeSearch("SELECT * FROM `employee` WHERE `email` = '" + user + "';");

            if (rs.next()) {
                String firstNameValue = rs.getString("first_name");
                String lastNameValue = rs.getString("last_name");
                fullName = firstNameValue + " " + lastNameValue;
                jLabel4.setText(" Welcome! " + fullName + ".");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Error in the Sign In!", e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Error in the Sign In!", e);
        }

    }

    private void sliderAnimation() {

        // icons, logos & images load
        sliderImages = new FlatSVGIcon[]{
            new FlatSVGIcon(this.getClass().getResource("/resources/slider1.svg")),
            new FlatSVGIcon(this.getClass().getResource("/resources/slider2.svg")),
            new FlatSVGIcon(this.getClass().getResource("/resources/slider3.svg"))
        };
        slider.setIcon(sliderImages[0]);

        // slider animation
        Timer sliderTimer = new Timer(3500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndex = (currentIndex + 1) % sliderImages.length;
                slider.setIcon(sliderImages[currentIndex]);
            }
        });
        sliderTimer.start();

    }

    // set analyse details
    public void summaryUpdate() {

        try {

            String title1 = "MOST SELLING PRODUCT TODAY";
            String title2 = "MONTHLY MOST SELLING PRODUCT";
            String title3 = "TODAY EARNING";
            String title4 = "MONTHLY EARNING";
            String title5 = "MOST ACTIVE CUSTOMER";

            String todayMostSellingProduct = "empty";
            String monthlyMostSellingProduct = "empty";
            String todayEarning = "0.0";
            String monthlyEarning = "0.0";
            String mostActiveCustomer = "empty";

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            String formattedTodayDate = sdf.format(date);

            ResultSet resultSet = MySQL.executeSearch("SELECT `name`, `product_id`, SUM(`qty`) AS `total_sold` FROM `invoice_item` "
                    + "INNER JOIN `invoice` ON `invoice`.`id` = `invoice_item`.`invoice_id` INNER JOIN `product` ON `product`.`id` = `invoice_item`.`product_id` "
                    + "WHERE `invoice`.`date` = '" + formattedTodayDate + "' GROUP BY `product_id` ORDER BY `total_sold` DESC LIMIT 1;");

            if (resultSet.next()) {

                todayMostSellingProduct = resultSet.getString("name");

                if (todayMostSellingProduct == null) {
                    todayMostSellingProduct = "empty";
                }

            } else {

                todayMostSellingProduct = "empty";

            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -1);
            Date beforeDate = cal.getTime();

            String formattedBeforeDate = sdf.format(beforeDate);

            ResultSet resultSet1 = MySQL.executeSearch("SELECT `name`, `product_id`, SUM(`qty`) AS `total_sold` FROM `invoice_item` "
                    + "INNER JOIN `invoice` ON `invoice`.`id` = `invoice_item`.`invoice_id` INNER JOIN `product` ON `product`.`id` = `invoice_item`.`product_id` "
                    + "WHERE `invoice`.`date` BETWEEN '" + formattedBeforeDate + "' AND '" + formattedTodayDate + "' "
                    + "GROUP BY `product_id` ORDER BY `total_sold` DESC LIMIT 1;");

            if (resultSet1.next()) {

                monthlyMostSellingProduct = resultSet1.getString("name");

                if (monthlyMostSellingProduct == null) {
                    monthlyMostSellingProduct = "empty";
                }

            } else {

                monthlyMostSellingProduct = "empty";

            }

            ResultSet resultSet2 = MySQL.executeSearch("SELECT SUM(`total`) AS `today_earning` FROM `invoice` WHERE `date` = '" + formattedTodayDate + "'");

            if (resultSet2.next()) {

                todayEarning = resultSet2.getString("today_earning");

                if (todayEarning == null) {
                    todayEarning = "0.0";
                }

            } else {

                todayEarning = "0.0";

            }

            ResultSet resultSet3 = MySQL.executeSearch("SELECT SUM(`total`) AS `monthly_earning` FROM `invoice` WHERE `date` BETWEEN '" + formattedBeforeDate + "' AND '" + formattedTodayDate + "'");

            if (resultSet3.next()) {

                monthlyEarning = resultSet3.getString("monthly_earning");

                if (monthlyEarning == null) {
                    monthlyEarning = "0.0";
                }

            } else {

                monthlyEarning = "0.0";

            }

            ResultSet resultSet4 = MySQL.executeSearch("SELECT `first_name`, `last_name`, `customer_mobile`, COUNT(*) AS `invoice_count` FROM `invoice` INNER JOIN `customer` ON `invoice`.`customer_mobile` = `customer`.`mobile` "
                    + "GROUP BY `customer_mobile` ORDER BY `invoice_count` DESC LIMIT 1");

            if (resultSet4.next()) {

                String firstName = resultSet4.getString("first_name");
                String lastName = resultSet4.getString("last_name");
                mostActiveCustomer = firstName + " " + lastName;

                if (mostActiveCustomer == null) {
                    mostActiveCustomer = "empty";
                }

            } else {

                mostActiveCustomer = "empty";

            }

            analyseDetails1 = new String[]{
                title1,
                title2,
                title3,
                title4,
                title5
            };

            analyseDetails2 = new String[]{
                todayMostSellingProduct,
                monthlyMostSellingProduct,
                "Rs. " + todayEarning,
                "Rs. " + monthlyEarning,
                mostActiveCustomer
            };

            jLabel7.setText(analyseDetails1[0]);
            jLabel8.setText(analyseDetails2[0]);

            Timer analyseTimer = new Timer(3500, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    analyseIndex1++;
                    analyseIndex2++;

                    if (analyseIndex1 >= analyseDetails1.length) {
                        analyseIndex1 = 0;
                    }

                    if (analyseIndex2 >= analyseDetails2.length) {
                        analyseIndex2 = 0;
                    }

                    jLabel7.setText(analyseDetails1[analyseIndex1]);
                    jLabel8.setText(analyseDetails2[analyseIndex2]);
                }
            });
            analyseTimer.start();

        } catch (SQLException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Analyse Generating Error!", e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Analyse Generating Error!", e);
        }

    }

    // set a bordarRadius to button
    private void setButtonBorderRadius() {
        menuButton1.putClientProperty("JButton.buttonType", "roundRect");
        menuButton2.putClientProperty("JButton.buttonType", "roundRect");
        menuButton3.putClientProperty("JButton.buttonType", "roundRect");
        menuButton4.putClientProperty("JButton.buttonType", "roundRect");
        menuButton5.putClientProperty("JButton.buttonType", "roundRect");
        menuButton6.putClientProperty("JButton.buttonType", "roundRect");
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
        FlatSVGIcon icon = new FlatSVGIcon(this.getClass().getResource("/resources/bubble_tea.svg"));
        logo.setIcon(icon);

        FlatSVGIcon UserIcon = new FlatSVGIcon(this.getClass().getResource("/resources/user.svg"));
        user_logo.setIcon(UserIcon);

        FlatSVGIcon summaryBand = new FlatSVGIcon(this.getClass().getResource("/resources/summary.svg"));
        jLabel5.setIcon(summaryBand);

        FlatSVGIcon LogOutIcon = new FlatSVGIcon(this.getClass().getResource("/resources/logout.svg"));
        logout_logo.setIcon(LogOutIcon);

        FlatSVGIcon menuIcon = new FlatSVGIcon(this.getClass().getResource("/resources/menu_logo.svg"));
        menu_logo.setIcon(menuIcon);

        FlatSVGIcon billToArrow = new FlatSVGIcon(this.getClass().getResource("/resources/arrow_right.svg"));
        billTo.setIcon(billToArrow);

        FlatSVGIcon r_arrow = new FlatSVGIcon(this.getClass().getResource("/resources/arrow_right.svg"));
        FlatSVGIcon l_arrow = new FlatSVGIcon(this.getClass().getResource("/resources/arrow_left.svg"));

        right_arrow.setIcon(r_arrow);
        left_arrow.setIcon(l_arrow);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
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
        jPanel8 = new javax.swing.JPanel();
        slider = new javax.swing.JLabel();
        right_arrow = new javax.swing.JButton();
        left_arrow = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        jPopupMenu1.setBackground(new java.awt.Color(215, 249, 204));
        jPopupMenu1.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 12)); // NOI18N
        jPopupMenu1.setForeground(new java.awt.Color(0, 0, 0));
        jPopupMenu1.setBorder(null);

        jMenuItem1.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 10)); // NOI18N
        jMenuItem1.setForeground(new java.awt.Color(0, 0, 0));
        jMenuItem1.setText("Register");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 10)); // NOI18N
        jMenuItem2.setForeground(new java.awt.Color(0, 0, 0));
        jMenuItem2.setText("Products & Stocks");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        jMenuItem3.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 10)); // NOI18N
        jMenuItem3.setForeground(new java.awt.Color(0, 0, 0));
        jMenuItem3.setText("Customers");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem3);

        jMenuItem4.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 10)); // NOI18N
        jMenuItem4.setForeground(new java.awt.Color(0, 0, 0));
        jMenuItem4.setText("Suppliers");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem4);

        jMenuItem5.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 10)); // NOI18N
        jMenuItem5.setForeground(new java.awt.Color(0, 0, 0));
        jMenuItem5.setText("GRN");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem5);

        jMenuItem6.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 10)); // NOI18N
        jMenuItem6.setForeground(new java.awt.Color(0, 0, 0));
        jMenuItem6.setText("Reports");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem6);

        jMenuItem7.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 10)); // NOI18N
        jMenuItem7.setForeground(new java.awt.Color(0, 0, 0));
        jMenuItem7.setText("Invoice");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem7);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Home");
        setFont(new java.awt.Font("JetBrains Mono", 0, 8)); // NOI18N
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(215, 249, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 229, 255), 6));

        jPanel5.setBackground(new java.awt.Color(204, 229, 255));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(121, 203, 96), 3, true));

        jLabel2.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 32)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("MENU");

        menu_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

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
        menuButton6.setText("Reports");
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
                .addGap(26, 26, 26))
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

        user_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        user_logo.setToolTipText("Register");
        user_logo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        user_logo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                user_logoMouseClicked(evt);
            }
        });

        logout_logo.setToolTipText("LogOut");
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

        slider.setBackground(new java.awt.Color(121, 203, 96));
        slider.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slider.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 2, true));
        slider.setOpaque(true);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slider, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
        );

        right_arrow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        right_arrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                right_arrowActionPerformed(evt);
            }
        });

        left_arrow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        left_arrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                left_arrowActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText(" ");
        jLabel4.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(215, 249, 204));
        jLabel3.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Copyright 2024 Bubble Bay All Right Reserved.");
        jLabel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(121, 203, 96), 3, true));
        jLabel3.setOpaque(true);

        jLabel6.setBackground(new java.awt.Color(204, 229, 255));
        jLabel6.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 26)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText(" SUMMARY");
        jLabel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(121, 203, 96), 3, true));
        jLabel6.setOpaque(true);

        jPanel9.setBackground(new java.awt.Color(121, 203, 96));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 229, 255), 3, true));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel7.setFont(new java.awt.Font("Retro Signed", 0, 45)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));

        jLabel8.setFont(new java.awt.Font("JetBrains Mono ExtraBold", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 349, Short.MAX_VALUE)
                        .addComponent(left_arrow, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(right_arrow, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(right_arrow, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        .addComponent(left_arrow, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 14, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                    .addComponent(panel_loader))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(panel_loader))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        // current JFrame
    }//GEN-LAST:event_menuButton1ActionPerformed

    private void menuButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton2ActionPerformed
        // products panel load
        getEmployeeRole();
        if (employeeType.equals("Admin")) {
            this.dispose();
            products.setVisible(true);
            products.highlitedTheButton();
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Only Admin can access this page!");
        }
    }//GEN-LAST:event_menuButton2ActionPerformed

    private void menuButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton3ActionPerformed
        // customer panel load
        this.dispose();
        customer_Registration.setVisible(true);
    }//GEN-LAST:event_menuButton3ActionPerformed

    private void menuButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton4ActionPerformed
        // supplier panel load
        getEmployeeRole();
        if (employeeType.equals("Admin")) {
            this.dispose();
            supplier_Registration.setVisible(true);
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Only Admin can access this page!");
        }
    }//GEN-LAST:event_menuButton4ActionPerformed

    private void menuButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton5ActionPerformed
        // GRN panel load
        getEmployeeRole();
        if (employeeType.equals("Admin")) {
            this.dispose();
            GRN grn = new GRN(products);
            grn.setVisible(true);
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Only Admin can access this page!");
        }
    }//GEN-LAST:event_menuButton5ActionPerformed

    private void menuButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButton6ActionPerformed
        // reports panel load
        getEmployeeRole();
        if (employeeType.equals("Admin")) {
            this.dispose();
            reports.setVisible(true);
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Only Admin can access this page!");
        }
    }//GEN-LAST:event_menuButton6ActionPerformed

    private void bill_btn_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bill_btn_txtActionPerformed
        // invoice panel load
        this.dispose();
        Invoice invoice = new Invoice(this, products);
        invoice.setVisible(true);
    }//GEN-LAST:event_bill_btn_txtActionPerformed

    private void billToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billToActionPerformed
        // invoice panel load
        this.dispose();
        Invoice invoice = new Invoice(this, products);
        invoice.setVisible(true);
    }//GEN-LAST:event_billToActionPerformed

    private void user_logoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_user_logoMouseClicked
        // go to register
        getEmployeeRole();
        if (employeeType.equals("Admin")) {
            this.dispose();
            register.setVisible(true);
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Only Admin can access this page!");
        }
    }//GEN-LAST:event_user_logoMouseClicked

    private void logout_logoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logout_logoMouseClicked
        // go to login
        this.dispose();
        Employee_Address.backToHomeChange = 0;
        jLabel4.setText("");
        signIn.setVisible(true);
    }//GEN-LAST:event_logout_logoMouseClicked

    private void left_arrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_left_arrowActionPerformed
        // get a previous slider
        currentIndex = (currentIndex - 1 + sliderImages.length) % sliderImages.length;
        slider.setIcon(sliderImages[currentIndex]);
    }//GEN-LAST:event_left_arrowActionPerformed

    private void right_arrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_right_arrowActionPerformed
        // get a next slider
        currentIndex = (currentIndex + 1) % sliderImages.length;
        slider.setIcon(sliderImages[currentIndex]);
    }//GEN-LAST:event_right_arrowActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // go to register
        getEmployeeRole();
        if (employeeType.equals("Admin")) {
            this.dispose();
            register.setVisible(true);
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Only Admin can access this page!");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // go to products & stock
        getEmployeeRole();
        if (employeeType.equals("Admin")) {
            this.dispose();
            products.setVisible(true);
            products.highlitedTheButton();
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Only Admin can access this page!");
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // go to customers
        this.dispose();
        customer_Registration.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // go to suppliers
        getEmployeeRole();
        if (employeeType.equals("Admin")) {
            this.dispose();
            supplier_Registration.setVisible(true);
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Only Admin can access this page!");
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // go to grn
        getEmployeeRole();
        if (employeeType.equals("Admin")) {
            this.dispose();
            GRN grn = new GRN(products);
            grn.setVisible(true);
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Only Admin can access this page!");
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased

        // right click and open popup menu
        if (evt.getButton() == MouseEvent.BUTTON3) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }

    }//GEN-LAST:event_formMouseReleased

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // reports panel load
        getEmployeeRole();
        if (employeeType.equals("Admin")) {
            this.dispose();
            reports.setVisible(true);
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, 3500l, "Only Admin can access this page!");
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // invoice panel load
        this.dispose();
        Invoice invoice = new Invoice(this, products);
        invoice.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton billTo;
    private javax.swing.JButton bill_btn_txt;
    private javax.swing.JLabel date_label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JButton left_arrow;
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
    private javax.swing.JButton right_arrow;
    private javax.swing.JLabel slider;
    private javax.swing.JLabel time_label;
    private javax.swing.JLabel user_logo;
    // End of variables declaration//GEN-END:variables

}
