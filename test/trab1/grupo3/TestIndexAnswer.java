package trab1.grupo3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestIndexAnswer {
    @Test
    public void testCheckIndex() {
      Answer yes= new IndexAnswer( 2 );
      try {
          assertTrue( yes.check("2") );
          assertFalse( yes.check("1") );
          InvalidFormatException ex = assertThrows (
                    InvalidFormatException.class,
                    () -> yes.check("zero")
          );
          assertTrue(ex.getMessage().contains("zero: não é um número"));
        }
        catch ( InvalidFormatException ex ) {
            Assertions.fail(ex.getMessage());
        }
    }

}
