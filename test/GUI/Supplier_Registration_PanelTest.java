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
public class Supplier_Registration_PanelTest {
    
    public Supplier_Registration_PanelTest() {
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
     * Test of setCompanyId method, of class Supplier_Registration_Panel.
     */
    @Test
    public void testSetCompanyId() {
        System.out.println("setCompanyId");
        String companyId = "";
        Supplier_Registration_Panel instance = new Supplier_Registration_Panel();
        instance.setCompanyId(companyId);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of setCompanyName method, of class Supplier_Registration_Panel.
     */
    @Test
    public void testSetCompanyName() {
        System.out.println("setCompanyName");
        String companyName = "";
        Supplier_Registration_Panel instance = new Supplier_Registration_Panel();
        instance.setCompanyName(companyName);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of clearCompany method, of class Supplier_Registration_Panel.
     */
    @Test
    public void testClearCompany() {
        System.out.println("clearCompany");
        Supplier_Registration_Panel instance = new Supplier_Registration_Panel();
        instance.clearCompany();
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
    
}
