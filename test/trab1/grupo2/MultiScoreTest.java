package trab1.grupo2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MultiScoreTest {
    private static Score s;

    /* Exception variable for testing errors */
    private Exception e;

    @Test
    public void testConstruct() {
        e = assertThrows( ScoreException.class, () -> s = new MultiScore( new SingleScore(150) ) );
        assertEquals( "invalid number of scores" , e.getMessage() );


        e = assertThrows( ScoreException.class,
                () -> s = new MultiScore( new SingleScore(150) , new SingleScore(2, 100) , new SingleScore(80) )
        );
        assertEquals( "invalid sequence" , e.getMessage() );


        assertDoesNotThrow(
                () -> s = new MultiScore( new SingleScore(150) , new SingleScore(2, 100) , new SingleScore(5, 80) )
        );

        assertEquals( 330 , s.getPoints());
        assertEquals( 5 , s.getLevel());
    }

    @Test
    public void testToString( ) {
        assertDoesNotThrow(
                () -> s = new MultiScore( new SingleScore(150) , new SingleScore(2, 100) , new SingleScore(5, 80) )
        );

        assertEquals( "5: 330 (0: 150, 2: 100, 5: 80)", s.toString());
    }

    @Test
    public void testMultiLevel ( )	{
        assertDoesNotThrow(
                () -> s = new MultiScore( new SingleScore(150) , new SingleScore(2, 100) , new SingleScore(5, 80) )
        );

        assertTrue( s instanceof MultiLevel );
        MultiLevel ml = (MultiLevel) s;
        assertEquals( 5 , ml.getLevel() );
        assertEquals( 0 , ml.getLowerLevel() );
    }
}
