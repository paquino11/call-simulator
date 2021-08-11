package trab1.grupo3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSelectQuestion {
    private static final Quest select = new SelectQuest(new IndexAnswer(1),
            new SimpleQuest("PGI é do 1ºsemestre"),
            new SimpleQuest("PGI é do 2ºsemestre", new YesNoAnswer(false), 150)
    );

    @Test
    public void testGetPoints() {
        assertEquals(125, select.getPoints());
    }

    @Test
    public void testToString() {
        assertEquals("Qual das questões é verdadeira:\n" +
                " 1 - PGI é do 1ºsemestre\n" +
                " 2 - PGI é do 2ºsemestre\n", select.toString());
    }

    @Test
    public void testCheckPoints() {
        try {
            assertEquals(select.getPoints(), select.checkPoints("1"));
            assertEquals(0, select.checkPoints("2"));
        } catch (InvalidFormatException ex) {
            Assertions.fail(ex.getMessage());
        }
    }

    @Test
    public void testCheckException() {

        InvalidFormatException ex = assertThrows(
                InvalidFormatException.class,
                () -> select.checkPoints("dois")
        );
        assertTrue(ex.getMessage().contains("dois: não é um número"));

    }
}
