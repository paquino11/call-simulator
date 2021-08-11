package trab1.grupo4;

import trab1.grupo1.Date;

public class HotelRoom extends SimpleItem {
    public final String stars;
    private final Date end;

    public HotelRoom ( String nm , int pDay , int nights , Date checkIn , int stars ) {
        super ( "Hotel " + nm , pDay*nights , checkIn );
        this.stars = "*".repeat(stars);
        end = checkIn.nextDate(nights);
    }

    public HotelRoom ( String nm , int pDay , int stars ) {
        this ( nm , pDay , 1 , new Date() , stars );
    }

    @Override
    public Date getEndDate ( ) {
        return end;
    }

    @Override
    public String getDescription ( String prefix ) {
        return super.getDescription( prefix + stars + ' ' );
    }
}
