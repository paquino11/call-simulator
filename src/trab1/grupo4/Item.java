package trab1.grupo4;

import trab1.grupo1.Date;

public abstract class Item {
    private final String name;
    private final int price;

    protected Item ( String name , int price ) {
        this.name = name;
        this.price = price;
    }

    public final String getName ( ) {
        return name;
    }

    public int getPrice ( ) {
        return price;
    }

    public abstract Date getEndDate ( );

    public abstract Date getStartDate ( );

    public String getDescription ( String prefix ) {
        Date s = getStartDate(), e = getEndDate();
        return prefix + name + ( s.equals(e) ? " in " + s : " from " + s + " to " + e ) + " (€" + price + ')';
    }

    @Override
    public final String toString ( ) {
        return getDescription("");
    }

    public static int priceSum ( Item ... items ) {
        int sum = 0;
        for ( Item i : items )
            sum += i.getPrice();
        return sum;
    }
}
