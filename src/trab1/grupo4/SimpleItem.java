package trab1.grupo4;

import trab1.grupo1.Date;

public abstract class SimpleItem extends Item {
    protected final Date start;

    public SimpleItem ( String name , int price , Date dt ) {
        super( name , price );
        start = dt;
    }

    @Override
    public Date getStartDate ( ) {
        return start;
    }
}
