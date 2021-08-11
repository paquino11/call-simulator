package trab1.grupo4;

import trab1.grupo1.Date;

import java.util.Arrays;
import java.util.Comparator;

public class Program extends Item {
    private final Item[] items;
    private final int days;

    public Program ( String nm , int days , Item ... items ) throws ItemsException {
        super( nm , priceSum( items ) );
        this.days = days;
        Arrays.sort( items , Comparator.comparing(Item::getStartDate) );
        this.items = Arrays.copyOf( items , items.length );
        Arrays.sort( items , Comparator.comparing(Item::getEndDate) );
        if ( items[items.length-1].getEndDate().compareTo(getEndDate()) > 0 )
            throw new ItemsException( items[items.length-1].getName() + " - invalid item" );
    }

    @Override
    public Date getStartDate ( ) {
        return items[0].getStartDate();
    }

    @Override
    public Date getEndDate ( ) {
        return getStartDate().nextDate(days);
    }

    @Override
    public String getDescription ( String prefix ) {
        StringBuilder s = new StringBuilder( super.getDescription( prefix + "Travel Program: " ) + '\n' );
        for ( Item i : items )
            s.append( i.getDescription( "\t" + prefix ) ).append('\n');
        return s.substring( 0 , s.length()-1 );
    }
}
