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
public class GRNTest {
    
    private GRN instance;
    private SignIn signIn;
    private Products products;
    
    public GRNTest() {
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
        instance = new GRN(products);
        signIn = new SignIn();
    }
    
    @After
    public void tearDown() {
        products = null;
        instance = null;
        signIn = null;
    }

    /**
     * Test of setSupplierDetails method, of class GRN.
     */
    @Test
    public void testSetSupplierDetails() {
        System.out.println("setSupplierDetails");
        instance.setSupplierDetails();
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of setMaterialsDetails method, of class GRN.
     */
    @Test
    public void testSetMaterialsDetails() {
        System.out.println("setMaterialsDetails");
        instance.setMaterialsDetails();
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of getUserEmail method, of class GRN.
     */
    @Test
    public void testGetUserEmail() {
        System.out.println("getUserEmail");
        instance.getUserEmail(signIn);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of setVisible method, of class GRN.
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
