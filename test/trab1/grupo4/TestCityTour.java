package trab1.grupo4;

import org.junit.jupiter.api.Test;
import trab1.grupo1.Date;
import trab1.grupo2.Score;
import trab1.grupo2.SingleScore;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCityTour {
    private static final CityTour tour = new CityTour("Lisbon Tour", "Alcântara", "Castelo", 50, "13-4-2021"),
                                  tourNight = new CityTour("Lisbon Tour at night", "Restauradores", "Belém", 75, "12-4-2021");

    private void testCityTour(CityTour tour, int p, Date d, String ori, String dest ) {
        assertEquals( p, tour.getPrice());
        assertEquals( d, tour.getStartDate());
        assertEquals( d, tour.getEndDate());
        assertEquals( tour.getStartDate(), tour.getEndDate());
        assertEquals( ori, tour.getOrigin());
        assertEquals( dest, tour.getDestination());
    }

    @Test
    public void testGetName() {
        assertEquals( "Lisbon Tour from Alcântara to Castelo", tour.getName());
        assertEquals( "Lisbon Tour at night from Restauradores to Belém", tourNight.getName());
    }

    @Test
    public void testGetters() {
        testCityTour( tour, 50, new Date(13,4,2021), "Alcântara", "Castelo" );
        testCityTour( tourNight, 75, new Date(12,4,2021), "Restauradores", "Belém" );
     }

    @Test
    public void testToStringAndDescription(){
        assertEquals( "Lisbon Tour from Alcântara to Castelo in 13-04-2021 (€50)", tour.getDescription(""));
        assertEquals( "Lisbon Tour from Alcântara to Castelo in 13-04-2021 (€50)", tour.toString());
        assertEquals( "  - Lisbon Tour at night from Restauradores to Belém in 12-04-2021 (€75)", tourNight.getDescription("  - "));
    }

    @Test
    public void testSumPrice( ){
        assertEquals( 0, Item.priceSum(new Item[0]) );
        assertEquals( tour.getPrice(), Item.priceSum(tour));
        assertEquals( tour.getPrice()+tourNight.getPrice(), Item.priceSum(tour, tourNight));
        assertEquals( tour.getPrice()+tourNight.getPrice()*2, Item.priceSum(tourNight, tour, tourNight));
    }

}
