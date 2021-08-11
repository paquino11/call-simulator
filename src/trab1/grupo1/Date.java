package trab1.grupo1;

import java.time.Month;
import java.time.Year;
import java.util.*;

public class Date implements Comparable<Date> {
    public static final String FORMAT = "%02d-%02d-%04d";

    private final int day, month, year;

    public Date ( int day , int month , int year ) {
        this.day = day;
        this.month = month;
        this.year = year;
        validate();
    }

    public Date ( String date ) {
        String s = String.valueOf(date);

        /* Gets the day */
        int idx = s.indexOf( '-' );
        if ( idx != -1 )
            day = Integer.parseInt( s.substring(0,idx) );
        else
            throw new IllegalArgumentException( "\"dd-mm-yyyy\" is the expected format." );
        s = s.substring( idx+1 );

        /* Gets the month */
        idx = s.indexOf( '-' );
        if ( idx != -1 )
            month = Integer.parseInt( s.substring(0,idx) );
        else
            throw new IllegalArgumentException( "\"dd-mm-yyyy\" is the expected format." );

        /* Gets the year */
        s = s.substring( idx+1 );
        year = Integer.parseInt( s );

        validate();
    }

    public Date ( ) {
        Calendar c = Calendar.getInstance();
        day = c.get( Calendar.DAY_OF_MONTH );
        month = c.get( Calendar.MONTH ) + 1;
        year = c.get( Calendar.YEAR );
    }

    private void validate ( ) {
        /* Validates the year */
        if ( year < 0 )
            throw new IllegalArgumentException( "\"" + year + "\" is an invalid year."  );
        /* Validates the month */
        if ( month < 1 || month > 12 )
            throw new IllegalArgumentException( "\"" + month + "\" is an invalid month." );
        /* Validates the day of the month */
        if ( day < 1 || day > Month.of(month).length( Year.of(year).isLeap() ) ) {
            /* Validates the 29th day of February (leap years) */
            if ( month == 2 ) {
                if ( day == 29 && !Year.of(year).isLeap() )
                    throw new IllegalArgumentException( "\"" + year + "\" is not a leap year." );
                else
                    return;
            }
            throw new IllegalArgumentException( "\"" + day + "\" is an invalid day." );
        }
    }

    public int getDay ( ) {
        return day;
    }

    public int getMonth ( ) {
        return month;
    }

    public int getYear ( ) {
        return year;
    }

    @Override
    public String toString ( ) {
        return String.format( FORMAT , day , month , year );
    }

    @Override
    public boolean equals ( Object o ) {
        if ( this == o )
            return true;
        if ( o instanceof Date ) {
            Date d = (Date) o;
            return day == d.day && month == d.month && year == d.year;
        }
        return false;
    }

    @Override
    public int hashCode ( ) {
        Year y = Year.of(year);
        Month m = Month.of(month);
        /* Counts the days of the current month */
        int dayCounter = day;

        /* Counts the rest of days of the current year */
        while ( m.getValue() > 1 ) {
            m = m.minus(1);
            dayCounter += m.length(y.isLeap());
        }

        /* Counts the days of the years before the current year until reach Christ birth (year zero) */
        while ( y.getValue() > 0 ) {
            y = y.minusYears(1);
            dayCounter += y.length();
        }
        return dayCounter;
    }

    @Override
    public int compareTo ( Date d ) {
        int result = year - d.getYear();
        if ( result == 0 ) {
            result = month - d.getMonth();
            if ( result == 0 )
                return day - d.getDay();
            return result;
        }
        return result;
    }

    public Date nextDate ( int n ) {
        Year y = Year.of(year);
        Month m = Month.of(month);

        /* Adds the days of the current month */
        n += day;

        /* If the current year length fits the days, it increments the year and removes its days */
        for ( int d = y.length() ; n / (d+1) >= 1 ; d = y.length() ) {
            n -= d;
            y = y.plusYears(1);
        }

        /* If the current month length fits the days, it increments the month and removes its days */
        for ( int d = m.length(y.isLeap()) ; n / (d+1) >= 1 ; d = m.length(y.isLeap()) ) {
            n -= d;
            m = m.plus(1);
            /* If the month has restarted, it goes to the next year */
            if ( m.getValue() == 1 )
                y = y.plusYears(1);
        }

        return new Date( n , m.getValue() , y.getValue() );
    }

    public static int getDays ( Date d1 , Date d2 ) {
        return Math.abs( d1.hashCode() - d2.hashCode() );
    }

    public static Date today ( ) {
        return new Date();
    }

    public static Date[] lessDates ( Date ... dates ) {
        if ( dates.length == 0)
            return dates;
        Date today = new Date();
        Arrays.sort(dates);
        for ( int i=0 ; i < dates.length ; i++ ) {
            if ( today.compareTo(dates[i]) > 0 )
                continue;
            if ( i == 0 )
                return new Date[0];
            return Arrays.copyOf(dates,i);
        }
        return new Date[0];
    }
}
