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
public class HomeTest {

    private SignIn signIn;

    public HomeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() {
        signIn = new SignIn();
    }

    @After
    public void tearDown() {
        signIn = null;
    }

    /**
     * Test of highlitedTheButton method, of class Home.
     */
    @Test
    public void testHighlitedTheButton() {
        System.out.println("highlitedTheButton");
        Home instance = new Home();
        instance.highlitedTheButton();
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of getUserEmail method, of class Home.
     */
    @Test
    public void testGetUserEmail() {
        System.out.println("getUserEmail");
        Home instance = new Home();
        instance.getUserEmail(signIn);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of setVisible method, of class Home.
     */
    @Test
    public void testSetVisible() {
        System.out.println("setVisible");
        boolean b = false;
        Home instance = new Home();
        instance.setVisible(b);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of summaryUpdate method, of class Home.
     */
    @Test
    public void testSummaryUpdate() {
        System.out.println("summaryUpdate");
        Home instance = new Home();
        instance.summaryUpdate();
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

}
