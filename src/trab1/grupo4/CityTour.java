package trab1.grupo4;

import trab1.grupo1.Date;

public class CityTour extends SimpleItem {
    private final String origin, destination;

    public CityTour ( String nm , String ori , String dest , int p , String date ) {
        super( nm + " from " + ori + " to " + dest , p , new Date(date) );
        origin = ori;
        destination = dest;
    }

    @Override
    public Date getEndDate ( ) {
        return getStartDate();
    }

    public String getOrigin ( ) {
        return origin;
    }

    public String getDestination ( ) {
        return destination;
    }
}
