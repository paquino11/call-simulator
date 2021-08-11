package trab2.grupo1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Utils {
    public static int replaceTabs ( String pathnameIn , PrintWriter out , int n ) throws IOException {
        int counter = 0;
        BufferedReader in = new BufferedReader( new FileReader( pathnameIn ) );
        for ( int c = in.read() ; c != -1 ; c = in.read() ) {
            if ( c == '\t' ) {
                out.print( " ".repeat(n) );
                counter++;
                continue;
            }
            out.print(c);
        }
        in.close();
        return counter;
    }

    public static int filter ( BufferedReader in , Predicate<String> criteria , Consumer<String> action )
            throws IOException {
        int counter = 0;
        for ( String line = in.readLine() ; line != null ; line = in.readLine() ) {
            if ( criteria.test(line) ) {
                counter++;
                action.accept(line);
            }
        }
        return counter;
    }
}
