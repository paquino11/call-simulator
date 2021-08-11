package trab1.grupo4;

import org.junit.jupiter.api.Test;
import trab1.grupo1.Date;
import trab1.grupo3.InvalidFormatException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestHotelRoom {
    private static final HotelRoom altis = new HotelRoom("Altis", 125, 2, new Date(12,4,2021), 5),
                              ibis = new HotelRoom("Ibis",60, 4);
    private static final Date today = new Date();
    @Test
    public void testGetters() {
        assertEquals( 250, altis.getPrice());
        assertEquals( "Hotel Altis", altis.getName());
        assertEquals( new Date(12,4,2021), altis.getStartDate());
        assertEquals( new Date(14,4,2021), altis.getEndDate());

        assertEquals( 60, ibis.getPrice());
        assertEquals( "Hotel Ibis", ibis.getName());
        assertEquals( today, ibis.getStartDate());
        assertEquals( today.nextDate(1), ibis.getEndDate());
    }

    @Test
    public void testStars() {
        assertEquals( "****", ibis.stars);
        assertEquals( "*****", altis.stars);
    }

    @Test
    public void testToStringAndDescription(){
        assertEquals( "***** Hotel Altis from 12-04-2021 to 14-04-2021 (€250)", altis.getDescription(""));
        assertEquals( "\t- **** Hotel Ibis from "+today+" to " +today.nextDate(1)+" (€60)", ibis.getDescription("\t- "));
        assertEquals( "***** Hotel Altis from 12-04-2021 to 14-04-2021 (€250)", altis.toString());
    }

}
