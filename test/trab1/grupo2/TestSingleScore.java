package trab1.grupo2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSingleScore {
    private static final SingleScore testScore = new SingleScore(3, 1000);
    @Test
    public void testGetters()	{
        assertEquals( 1000, testScore.getPoints());
        assertEquals( 3, testScore.getLevel());
        SingleScore s = new SingleScore(1000);
        assertEquals( 1000, s.getPoints());
        assertEquals( 0, s.getLevel());
    }

    @Test
    public void testToString( )	{
        assertEquals( "3: 1000", testScore.toString());
    }

    @Test
    public void testCompareTo() {
        assertEquals( 0, testScore.compareTo(testScore) );
        assertEquals( 0, testScore.compareTo(new SingleScore(testScore.getLevel(), testScore.getPoints())) );
        assertTrue( testScore.compareTo(new SingleScore(testScore.getLevel()-1, testScore.getPoints())) > 0 );
        assertTrue( testScore.compareTo(new SingleScore(testScore.getLevel()-1, testScore.getPoints()+1)) > 0 );
        assertTrue( testScore.compareTo(new SingleScore(testScore.getLevel()-1, testScore.getPoints()-1)) > 0 );
        assertTrue( testScore.compareTo(new SingleScore(testScore.getLevel(), testScore.getPoints()-1)) > 0 );

        assertTrue( testScore.compareTo(new SingleScore(testScore.getLevel()+1, testScore.getPoints())) < 0 );
        assertTrue( testScore.compareTo(new SingleScore(testScore.getLevel()+1, testScore.getPoints()+1)) < 0 );
        assertTrue( testScore.compareTo(new SingleScore(testScore.getLevel()+1, testScore.getPoints()-1)) < 0 );
        assertTrue( testScore.compareTo(new SingleScore(testScore.getLevel(), testScore.getPoints()+1)) < 0 );
    }

    @Test
    public void testSumPoints(  ){
        assertEquals( 0, Score.sumPoints(new Score[0]) );
        assertEquals( 1000, Score.sumPoints(testScore));
        SingleScore s2 = new SingleScore(500);
        assertEquals( 1500, Score.sumPoints(testScore, s2) );
        SingleScore s3 = new SingleScore(250);
        assertEquals( 1750, Score.sumPoints(testScore, s2, s3));
    }

}
