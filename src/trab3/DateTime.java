package trab3;

import trab2.noteBook.Date;

import java.time.LocalTime;

public class DateTime implements Comparable<DateTime> {
    private final Date date;
    private final LocalTime time;

    public DateTime ( Date date , LocalTime time ) {
        this.date = date;
        this.time = time;
    }

    public DateTime ( ) {
        this( new Date() , LocalTime.now() );
    }

    public DateTime ( String s ) {
        int idx = s.indexOf(' ');
        date = new Date( s.substring(0,idx) );
        time = LocalTime.parse( s.substring(idx+1) );
    }

    public Date getDate ( ) {
        return date;
    }

    public LocalTime getTime ( ) {
        return time;
    }

    public String toString ( ) {
        return String.format( "%s %s" , date , time );
    }

    @Override
    public boolean equals ( Object o ) {
        if ( o instanceof DateTime ) {
            DateTime dateTime = (DateTime) o;
            return date.equals( dateTime.getDate() ) && time.equals( dateTime.getTime() );
        }
        return true;
    }

    @Override
    public int compareTo ( DateTime dateTime ) {
        int result = date.compareTo( dateTime.getDate() );
        if ( result == 0 )
            return time.compareTo( dateTime.getTime() );
        return result;
    }

    @Override
    public int hashCode ( ) {
        return date.hashCode()*31 + time.hashCode();
    }
}
