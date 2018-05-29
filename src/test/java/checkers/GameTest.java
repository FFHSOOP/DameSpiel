package checkers;

import static org.junit.Assert.assertTrue;

import org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

public class GameTest {
    
    //Testfixture
    private Game game;
    private Stage primaryStage;
    
    
      @BeforeClass
      public static void setUpBeforeClass() throws Exception {
	  JFXPanel dummy = new JFXPanel(); // Nötig damit Test funktioniert 
      }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void initTestFixtures(){
	game = new Game();
	
	primaryStage = new Stage();
//	game.multiplayer(primaryStage);

    }
    
    @Test
    public void testSpielbrettInitialisierung() {
	assertTrue(true);
    }


    @Test
    public void testCheckKill() throws Exception {
	
	assertTrue(true);
    }

    @Test
    public void testCanKill() {
	assertTrue(true);
    }

}
