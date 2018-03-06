package edu.ucsb.cs56.projects.games.battleship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class BattleshipGUITest {

    BattleshipGUI gui = new BattleshipGUI();


    @Test
    public void test_setTitle_1() {
        gui.setTitle("Battleship test 1");
        assertEquals("Battleship test 1", gui.getTitle());
    }

    @Test
    public void test_setTitle_2() {
        gui.setTitle("Battleship test 2");
        System.out.println(gui.getTitle());
        assertEquals("Battleship test 1", gui.getTitle());
    }

    @Test
    public void test_setTitle_3() {
        gui.setTitle("Battleship test 3");
        assertEquals("Battleship test", gui.getTitle());
    }

    @Test
    public void test_setMessage_1() {
        gui.setMessage("This is test message");
        assertEquals("This is test message", gui.getMessage());
    }

    @Test
    public void test_setMessage_2() {
        gui.setMessage("this is also a test");
        assertEquals("this should fail lol", gui.getMessage());
    }

    @Test
    public void test_reset_1() {
        gui.reset();
        assertEquals("null", gui.getDifficulty());
        assertEquals("0", gui.getGameType());
        assertTrue(gui.getPrompt());

    }

    @Test
    public void test_reset_2() {
        gui.reset();
        assertEquals("null", gui.getDifficulty());
        assertEquals("1", gui.getGameType());
        assertTrue(gui.getPrompt());
    }

    @Test
    public void test_resetPlace_1() {
        gui.reset();
        assertEquals("null", gui.getDifficulty());
        assertEquals("3", gui.getGameType());
        assertTrue(gui.getPrompt());
    }

    @Test
    public void test_resetPlace_2() {
        gui.reset();
        assertEquals("null", gui.getDifficulty());
        assertEquals("3", gui.getGameType());
        assertTrue(gui.getPrompt());
    }


}
