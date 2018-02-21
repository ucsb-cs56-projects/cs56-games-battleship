package edu.ucsb.cs56.projects.games.battleship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class GameGridTest {

    GameGrid gui = new GameGrid();

    @Test
    public void test_reset_1() {
        gui.reset();
        assertEquals("0", gui.getLastMove());
        assertFalse(gui.getPlayersTurn());
        assertTrue(gui.getPlayerBoats().isEmpty());
    }

}

		
