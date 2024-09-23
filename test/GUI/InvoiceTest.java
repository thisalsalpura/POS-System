/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package GUI;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class InvoiceTest {

    private Products products;
    private Home home;
    private Invoice instance;
    private SignIn signIn;

    public InvoiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        products = new Products();
        home = new Home();
        instance = new Invoice(home, products);
        signIn = new SignIn();
    }

    @After
    public void tearDown() {
        products = null;
        home = null;
        instance = null;
        signIn = null;
    }

    /**
     * Test of setCustomerDetails method, of class Invoice.
     */
    @Test
    public void testSetCustomerDetails() {
        System.out.println("setCustomerDetails");
        // instance.setCustomerDetails();
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of setProductDetails method, of class Invoice.
     */
    @Test
    public void testSetProductDetails() {
        System.out.println("setProductDetails");
        instance.setProductDetails();
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of getUserEmail method, of class Invoice.
     */
    @Test
    public void testGetUserEmail() {
        System.out.println("getUserEmail");
        instance.getUserEmail(signIn);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of setVisible method, of class Invoice.
     */
    @Test
    public void testSetVisible() {
        System.out.println("setVisible");
        boolean b = false;
        instance.setVisible(b);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

}
