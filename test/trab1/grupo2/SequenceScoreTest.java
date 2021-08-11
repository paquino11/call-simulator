package trab1.grupo2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SequenceScoreTest {
    private final Score s = new SequenceScore( new SequenceScore( new SingleScore(150) , 100 ) , 80 );
    @Test
    public void testGetters() {
        assertEquals( 330, s.getPoints());
        assertEquals( 2, s.getLevel());
    }

    @Test
    public void testToString( )	{
        assertEquals( "2: 330, 1: 250, 0: 150", s.toString());
    }

    @Test
    public void testMultiLevel()	{
        assertTrue(  s instanceof  MultiLevel );
        MultiLevel  ml = (MultiLevel) s;
        assertEquals(  2, ml.getLevel()  );
        assertEquals(  0, ml.getLowerLevel() );
    }
}
