package trab2.grupo1;
import java.io.*;
import java.util.function.BiConsumer;

public class Team {
    public static int copyTeam ( String pathnameIn , String teamId , BiConsumer<Integer,String> action )
            throws IOException {
        BufferedReader in = new BufferedReader( new FileReader(pathnameIn) );
        return Utils.filter( in , (s) -> teamId.length() > 0 && s.startsWith(teamId) , (s) -> {
            String[] fields = s.split( " " , 3 );
            action.accept( Integer.parseInt( fields[1] ) , fields[2] );
        });
    }

    public static int copyTeam ( String pathnameIn , String teamId ) throws IOException {
        PrintWriter out = new PrintWriter( new FileWriter( teamId + ".txt" ) );
        return copyTeam( pathnameIn , teamId , (i,s) -> out.println( i + " " + s ) );
    }

    public static void printTeam ( String pathnameIn , String teamId ) throws IOException {
        copyTeam( pathnameIn , teamId , (i,s) -> System.out.println( i + " " + s ) );
    }
}
