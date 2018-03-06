package edu.ucsb.cs56.projects.games.battleship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.Before;

public class SizeSetUpFrameTest {

    SizeSetUpFrame frame = new SizeSetUpFrame();

    @Test
    public void test_isValid_Empty() {

        assertFalse(frame.isValid(""));
    }

    @Test
    public void test_isValid_4() {

        assertTrue(frame.isValid("4"));
    }

    @Test
    public void test_isValid_10() {
        assertFalse(frame.isValid("10"));
    }

    @Test
    public void test_isValid_1() {
        assertFalse(frame.isValid("1"));
    }

}
