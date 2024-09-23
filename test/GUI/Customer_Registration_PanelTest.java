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
public class Customer_Registration_PanelTest {
    
    public Customer_Registration_PanelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadProfiles method, of class Customer_Registration_Panel.
     */
    @Test
    public void testLoadProfiles() {
        System.out.println("loadProfiles");
        String column = "first_name";
        String orderby = "";
        String email = "";
        Customer_Registration_Panel instance = new Customer_Registration_Panel();
        instance.loadProfiles(column, orderby, email);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of resetField method, of class Customer_Registration_Panel.
     */
    @Test
    public void testResetField() {
        System.out.println("resetField");
        Customer_Registration_Panel instance = new Customer_Registration_Panel();
        instance.resetField();
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
    
}
