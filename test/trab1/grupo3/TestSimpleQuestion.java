package trab1.grupo3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSimpleQuestion {
    private static final SimpleQuest questYes = new SimpleQuest( "O ISEL pertence ao IPL", new YesNoAnswer( true ), 200 ),
                                     questNo = new SimpleQuest( "PGI é do 2ºsemestre", new YesNoAnswer( false ), 150 ),
                                     questDefault = new SimpleQuest( "PGI é do 1ºsemestre" );
    @Test
    public void testGetPoints()	{
        assertEquals( 200, questYes.getPoints());
        assertEquals( 150, questNo.getPoints());
        assertEquals( 100, questDefault.getPoints());
    }

    @Test
    public void testToString( )	{
        assertEquals( "O ISEL pertence ao IPL", questYes.toString());
        assertEquals( "PGI é do 2ºsemestre", questNo.toString());
        assertEquals( "PGI é do 1ºsemestre", questDefault.toString());
    }

    @Test
    public void testCheckPointsYes() {
        try {
            assertEquals(questYes.getPoints(), questYes.checkPoints("sim"));
            assertEquals(0, questYes.checkPoints("não"));
        }
        catch ( InvalidFormatException ex ) {
            Assertions.fail(ex.getMessage());
        }
    }



    @Test
    public void testCheckPointsNo() {
        try {
            assertEquals(questNo.getPoints(), questNo.checkPoints("não"));
            assertEquals(0, questNo.checkPoints("sim"));
        }
        catch ( InvalidFormatException ex ) {
            Assertions.fail(ex.getMessage());
        }
    }

    @Test
    public void testCheckPointsDefault() {
        try {
            assertEquals(questDefault.getPoints(), questDefault.checkPoints("sim"));
            assertEquals(0, questDefault.checkPoints("não"));
        }
        catch ( InvalidFormatException ex ) {
            Assertions.fail(ex.getMessage());
        }
    }

    @Test
    public void testCheckException() {
        InvalidFormatException ex = assertThrows(
                InvalidFormatException.class,
                () -> questYes.checkPoints("ok")
        );
        assertTrue(ex.getMessage().contains("ok: nem é sim nem é não"));
    }

}
