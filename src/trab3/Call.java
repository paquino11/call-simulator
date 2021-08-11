package trab3;

import java.time.LocalTime;

public class Call {
    private final String type;
    private final String origin, destination;
    private final DateTime start;
    private LocalTime duration;

    private Call ( String type , String origin , String destination , DateTime start ) {
        this.type = type;
        this.origin = origin;
        this.destination = destination;
        this.start = start;
        this.duration = LocalTime.MIN;
    }

    public Call ( Call call ) {
        this.type = call.getType();
        this.origin = call.getOrigin();
        this.destination = call.getDestination();
        this.start = call.getStart();
        this.duration = call.duration;
    }

    public static Call answeredCall ( String origin , String destination ) {
        return new Call ( "answered" , origin , destination , new DateTime() );
    }

    public static Call declinedCall ( String origin , String destination ) {
        return new Call ( "declined" , origin , destination , new DateTime() );
    }

    public static Call missedCall ( String origin , String destination ) {
        return new Call ( "missed" , origin , destination , new DateTime() );
    }

    public Call ( String record ) throws CallException {
        int idx = record.indexOf( ';' );
        if ( idx == -1 )
            throw new CallException( "Missing type" );
        type = record.substring( 0 , idx );
        record = record.substring( idx + 1 );
        idx = record.indexOf( ';' );
        if ( idx == -1 )
            throw new CallException( "Missing origin" );
        origin = record.substring( 0 , idx );
        record = record.substring( idx + 1 );
        idx = record.indexOf( ';' );
        if ( idx == -1 )
            throw new CallException( "Missing destination" );
        destination = record.substring( 0 , idx );
        record = record.substring( idx + 1 );
        idx = record.indexOf( ';' );
        if ( idx == -1 )
            throw new CallException( "Missing start date and time" );
        start = new DateTime( record.substring( 0 , idx ) );
        record = record.substring( idx + 1 );
        duration = LocalTime.parse( record );
    }

    public String getType ( ) {
        return type;
    }

    public String getOrigin ( ) {
        return origin;
    }

    public String getDestination ( ) {
        return destination;
    }

    public DateTime getStart ( ) {
        return start;
    }

    public LocalTime getDuration( ) {
        return duration;
    }

    public void setDuration ( LocalTime duration ) {
        this.duration = duration;
    }

    @Override
    public String toString ( ) {
        return String.format( "%s;%s;%s;%s;%s" , type , origin , destination , start , duration == null ? "null" : duration);
    }

}