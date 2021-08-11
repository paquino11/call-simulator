package trab1.grupo4;

import org.junit.jupiter.api.Test;
import trab1.grupo1.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestProgram {
    private static final CityTour tour = new CityTour("Lisbon Tour", "Alcântara", "Castelo", 50, "13-4-2021"),
            tourNight = new CityTour("Lisbon Tour at night", "Restauradores", "Belém", 75, "12-4-2021");
    private static final HotelRoom altis = new HotelRoom("Altis", 125, 2, new Date(12, 4, 2021), 5);

    @Test
    public void testWithSimpleItemProgram() {
        try {
            Item lisbon = new Program("Lisbon", 2, tour, altis);
            assertEquals(300, lisbon.getPrice());
            assertEquals("Lisbon", lisbon.getName());
            assertEquals("Travel Program: Lisbon from 12-04-2021 to 14-04-2021 (€300)\n" +
                    "\t***** Hotel Altis from 12-04-2021 to 14-04-2021 (€250)\n" +
                    "\tLisbon Tour from Alcântara to Castelo in 13-04-2021 (€50)", lisbon.toString());
            assertEquals("\tTravel Program: Lisbon from 12-04-2021 to 14-04-2021 (€300)\n" +
                    "\t\t***** Hotel Altis from 12-04-2021 to 14-04-2021 (€250)\n" +
                    "\t\tLisbon Tour from Alcântara to Castelo in 13-04-2021 (€50)", lisbon.getDescription("\t"));
            assertEquals(new Date(12, 4, 2021), lisbon.getStartDate());
            assertEquals(new Date(14, 4, 2021), lisbon.getEndDate());

        } catch (ItemsException ex) {
            fail("Unexpected exception: " + ex.getMessage());
        }
    }

    @Test
    public void testWithProgram() {
        try {
            Item lisbonDayNight = new Program("Lisbon day and night", 2,
                    new Program("Lisbon", 2, tour, altis),
                    tourNight);
            assertEquals(375, lisbonDayNight.getPrice());
            assertEquals("Lisbon day and night", lisbonDayNight.getName());
            assertEquals("Travel Program: Lisbon day and night from 12-04-2021 to 14-04-2021 (€375)\n" +
                    "\tTravel Program: Lisbon from 12-04-2021 to 14-04-2021 (€300)\n" +
                    "\t\t***** Hotel Altis from 12-04-2021 to 14-04-2021 (€250)\n" +
                    "\t\tLisbon Tour from Alcântara to Castelo in 13-04-2021 (€50)\n" +
                    "\tLisbon Tour at night from Restauradores to Belém in 12-04-2021 (€75)", lisbonDayNight.toString());
            assertEquals(new Date(12, 4, 2021), lisbonDayNight.getStartDate());
            assertEquals(new Date(14, 4, 2021), lisbonDayNight.getEndDate());
        } catch (ItemsException ex) {
            fail("Unexpected exception: " + ex.getMessage());
        }
    }

    @Test
    public void testException() {
        ItemsException ex = assertThrows(
                ItemsException.class,
                () -> new Program("Lisbon", 1, tour, altis)
        );
        assertEquals("Hotel Altis - invalid item", ex.getMessage());
    }
}
