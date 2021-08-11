package trab1.grupo1;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class TestDate {
    public static final int ACTUAL_DAY = Calendar.getInstance().get( Calendar.DAY_OF_MONTH );
    public static final int ACTUAL_MONTH = Calendar.getInstance().get( Calendar.MONTH ) + 1;
    public static final int ACTUAL_YEAR = Calendar.getInstance().get( Calendar.YEAR );

    /* Date variables for testing */
    private Date d, d1, d2;
    private Date[] dates1, dates2;

    /* Exception variable for testing errors */
    private Exception e;

    @Test
    public void testConstructInteger ( ) {
        d = new Date( 24 , 10 , 2020 );
        assertEquals( 24 , d.getDay() );
        assertEquals( 10 , d.getMonth() );
        assertEquals( 2020 , d.getYear() );
    }

    @Test
    public void testConstructString ( ) {
        e = assertThrows( IllegalArgumentException.class , ()-> d = new Date( "wrongFormat" ) );
        assertEquals( "\"dd-mm-yyyy\" is the expected format." , e.getMessage() );

        e = assertThrows( IllegalArgumentException.class , ()-> d = new Date( "1-wrongFormat" ) );
        assertEquals( "\"dd-mm-yyyy\" is the expected format." , e.getMessage() );

        e = assertThrows( IllegalArgumentException.class , ()-> d = new Date( "30-10--2020" ) );
        assertEquals( "\"-2020\" is an invalid year." , e.getMessage() );

        e = assertThrows( IllegalArgumentException.class , ()-> d = new Date( "30-13-2020" ) );
        assertEquals( "\"13\" is an invalid month." , e.getMessage() );

        e = assertThrows( IllegalArgumentException.class , ()-> d = new Date( "31-11-2020" ) );
        assertEquals( "\"31\" is an invalid day." , e.getMessage() );

        e = assertThrows( IllegalArgumentException.class , ()-> d = new Date( "29-02-2019" ) );
        assertEquals( "\"2019\" is not a leap year." , e.getMessage() );

        assertDoesNotThrow( ()-> d = new Date( "29-02-2020" ) );

        d = new Date( "24-10-2020" );
        assertEquals( 24 , d.getDay() );
        assertEquals( 10 , d.getMonth() );
        assertEquals( 2020 , d.getYear() );
    }

    @Test
    public void testConstructEmpty ( ) {
        d = new Date( );
        assertEquals( ACTUAL_DAY , d.getDay() );
        assertEquals( ACTUAL_MONTH , d.getMonth() );
        assertEquals( ACTUAL_YEAR , d.getYear() );
    }

    @Test
    public void testToString ( ) {
        d = new Date( 4 , 5 , 500 );
        assertEquals( "04-05-0500" , d.toString() );
    }

    @Test
    public void testEquals ( ) {
        d1 = new Date( 24 , 10 , 2020 );
        d2 = new Date( 24 , 10 , 2020 );
        assertNotEquals( d1 , null );
        assertEquals( d1 , d2 );

        d2 = new Date( 24 , 10 , 2019);
        assertNotEquals( d2 , d1 );

        d2 = new Date( 24 , 9 , 2020);
        assertNotEquals( d2 , d1 );

        d2 = new Date( 23 , 10 , 2020);
        assertNotEquals( d2 , d1 );
    }

    @Test
    public void testCompareTo ( ) {
        d1 = new Date( 24 , 10 , 2020 );

        d2 = new Date( 24 , 10 , 2020 );
        assertEquals( 0 , d1.compareTo(d2) );

        d2 = new Date( 23 , 10 , 2020 );
        assertTrue( d1.compareTo(d2) > 0 );

        d2 = new Date( 25 , 10 , 2020 );
        assertTrue( d1.compareTo(d2) < 0 );

        d2 = new Date( 24 , 9 , 2020 );
        assertTrue( d1.compareTo(d2) > 0 );

        d2 = new Date( 24 , 11 , 2020 );
        assertTrue( d1.compareTo(d2) < 0 );

        d2 = new Date( 24 , 10 , 2019 );
        assertTrue( d1.compareTo(d2) > 0 );

        d2 = new Date( 24 , 10 , 2021 );
        assertTrue( d1.compareTo(d2) < 0 );
    }

    @Test
    public void testNextDate ( ) {
        d = new Date( 24 , 10 , 2020 );

        /* Tests normal month transition */
        d = d.nextDate(8);
        assertEquals( new Date(1,11,2020) , d );

        /* Tests normal year transition */
        d = d.nextDate(30+31);
        assertEquals( new Date(1,1,2021) , d );

        /* Tests February transition (non-leap year) */
        d = d.nextDate(31+28);
        assertEquals( new Date(1,3,2021) , d );

        /* Tests February transition (leap year) */
        d = new Date(27,2,2020).nextDate(3);
        assertEquals( new Date(1,3,2020) , d );

        /* Used the following date calculator: https://www.timeanddate.com/date/duration.html */
        d = new Date(1,1,2000).nextDate(12345);
        assertEquals( new Date(19,10,2033) , d );
    }

    @Test
    public void testGetDays ( ) {
        d1 = new Date( 24 , 10 , 2020 );

        d2 = new Date( 24 , 10 , 2020 );
        assertEquals( 0 , Date.getDays(d1,d2) );
        assertEquals( 0 , Date.getDays(d2,d1) );

        d2 = new Date( 23 , 10 , 2020 );
        assertEquals( 1 , Date.getDays(d1,d2) );
        assertEquals( 1 , Date.getDays(d2,d1) );

        d2 = new Date( 25 , 10 , 2020 );
        assertEquals( 1 , Date.getDays(d1,d2) );
        assertEquals( 1 , Date.getDays(d2,d1) );

        d2 = new Date( 24 , 9 , 2020 );
        assertEquals( 30 , Date.getDays(d1,d2) );
        assertEquals( 30 , Date.getDays(d2,d1) );

        d2 = new Date( 24 , 11 , 2020 );
        assertEquals( 31 , Date.getDays(d1,d2) );
        assertEquals( 31 , Date.getDays(d2,d1) );

        d2 = new Date( 24 , 10 , 2019 );
        assertEquals( 366 , Date.getDays(d1,d2) );
        assertEquals( 366 , Date.getDays(d2,d1) );

        d2 = new Date( 24 , 10 , 2021 );
        assertEquals( 365 , Date.getDays(d1,d2) );
        assertEquals( 365 , Date.getDays(d2,d1) );

        /* Used the following date calculator: https://www.timeanddate.com/date/duration.html */
        d2 = new Date( 24 , 10 , 1582 );
        assertEquals( 159977 , Date.getDays(d1,d2) );
    }

    @Test
    public void testToday ( ) {
        d = Date.today();
        assertEquals( ACTUAL_DAY , d.getDay() );
        assertEquals( ACTUAL_MONTH , d.getMonth() );
        assertEquals( ACTUAL_YEAR , d.getYear() );
    }

    @Test
    public void testLessDates ( ) {
        assertTrue( Arrays.equals( new Date[0] , Date.lessDates() ) );

        dates1 = new Date[] { new Date(1,1,2030) , new Date(2,3,2023) , new Date(5,7,2040) , new Date(9,11,2022) };
        assertTrue( Arrays.equals( new Date[0] , Date.lessDates(dates1) ) );

        dates1 = new Date[] { new Date(1,1,2019) , new Date(2,3,2023) , new Date(5,7,2017) , new Date(9,11,2022) };
        dates2 = new Date[] { new Date(5,7,2017) , new Date(1,1,2019) };
        assertTrue( Arrays.equals( dates2 , Date.lessDates(dates1) ) );
    }
}