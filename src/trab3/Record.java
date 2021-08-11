package trab3;

import java.io.*;
import java.util.*;

public class Record {
    private final String number;
    /**
     * Associative container with the record of calls sorted by date and time.
     */
    private final Map<DateTime,Call> calls = new TreeMap<>( Comparator.reverseOrder() );

    /**
     * Associative container with the map of sent/received calls sharing the same destination/origin number
     * respectively.
     */
    private final Map<Call,Map<DateTime,Call>> groups = new TreeMap<>( (c1, c2) -> c2.getStart().compareTo(c1.getStart()) );

    public Record ( String number ) {
        this.number = number;
    }

    public void add ( Call call ) {
        calls.put( call.getStart() , call );
        /* If the call was sent, search for matching destination */
        if ( call.getOrigin().equals(number) ) {
            for ( Map.Entry<Call, Map<DateTime, Call>> entry : groups.entrySet() ) {
                Call c = entry.getKey();
                Map<DateTime, Call> m = entry.getValue();
                if ( c.getDestination().equals(call.getDestination()) ) {
                    m.put(call.getStart(), call);
                    groups.remove(c, m);
                    groups.put(call, m);
                    break;
                }
            }
        }
        /* If the call was received, search for matching origin */
        else {
            for ( Map.Entry<Call, Map<DateTime, Call>> entry : groups.entrySet() ) {
                Call c = entry.getKey();
                Map<DateTime, Call> m = entry.getValue();
                if (c.getOrigin().equals(call.getOrigin())) {
                    m.put(call.getStart(), call);
                    groups.remove(c, m);
                    groups.put(call, m);
                    break;
                }
            }
        }
        if ( !groups.containsKey( call ) ) {
            Map<DateTime,Call> entry = new TreeMap<>( Comparator.reverseOrder() );
            entry.put( call.getStart() , call );
            groups.put( call , entry );
        }
    }

    public Collection<Call> getCalls ( ) {
        return calls.values();
    }

    public Map<Call,Map<DateTime,Call>> getGroups( ) {
        return groups;
    }

    public void read ( File file ) throws IOException {
        BufferedReader in = new BufferedReader( new FileReader( file ) );
        for ( String line = in.readLine() ; line != null ; line = in.readLine() ) {
            Call call = new Call( line );
            add( call );
        }
        in.close();
    }

    public void write ( File file ) throws IOException {
        PrintWriter out = new PrintWriter( file );
        calls.forEach( (k,v) -> out.println(v) );
        out.close();
    }
}
