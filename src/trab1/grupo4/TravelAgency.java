package trab1.grupo4;

import trab1.grupo1.Date;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TravelAgency {
    private final List<Item> items = new ArrayList<>();

    public TravelAgency append ( Item p ) throws ItemsException {
        if ( !getItems( (i) -> i.getName().equals(p.getName()) ).isEmpty() )
            throw new ItemsException( p.getName() + " - this name already exists" );
        items.add(p);
        return this;
    }

    public List<Item> getItems ( Predicate<Item> filter ) {
        List<Item> items = new ArrayList<>();
        this.items.forEach( item -> {
            if ( filter.test(item) )
                items.add(item);
        });
        return items;
    }

    public List<Item> getBetween ( int min , int max ) {
        return getItems( (i) -> i.getPrice() >= min && i.getPrice() <= max );
    }

    public List<Item> getBetween ( Date start , Date end ) {
        return getItems( (i) -> i.getStartDate().equals(start) && i.getEndDate().compareTo(end) < 0 );
    }

    public int getUpperPrice ( Comparator<Item> cmp ) {
        Item itemMax = items.get(0);
        for ( int i=1 ; i < items.size() ; i++ ) {
            if ( cmp.compare( itemMax , items.get(i) ) < 0 )
                itemMax = items.get(i);
        }
        return itemMax.getPrice();
    }

    public int getLowerPrice ( Comparator<Item> cmp ) {
        return getUpperPrice( (i1,i2) -> i2.getPrice() - i1.getPrice() );
    }

    public void forEachPerDate ( Consumer<Item> action ) {
        List<Item> items = new ArrayList<>(this.items);
        items.sort( Comparator.comparing( Item::getStartDate ).thenComparing( Item::getName ) );
        items.forEach( action );
    }

    public void forEachByPrice ( Consumer<Item> action ) {
        List<Item> items = new ArrayList<>(this.items);
        items.sort( Comparator.comparingInt( Item::getPrice ) );
        items.forEach( action );
    }

    public List<Date> getStartDates ( ) {
        List<Date> dates = new ArrayList<>();
        Consumer<Item> action = i -> {
            Date d = i.getStartDate();
            if (dates.get(dates.size()-1).equals(d))
                dates.add(d);
        };
        forEachPerDate( action );
        return dates;
    }
}
