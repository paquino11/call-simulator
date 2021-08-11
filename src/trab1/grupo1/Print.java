package trab1.grupo1;

public class Print {
    public static void main ( String[] args ) {
        Friend s1 = new Friend( "Maria" , 23 , 10 );
        System.out.println( s1.toString() );
        Friend s2 = new Friend( "Maria" , 23 , 10 );
        System.out.println( s1 == s2 );
        System.out.println( s1.equals(s2) );

        Friend s3 = s1;
        System.out.println( s1.equals(s3) );
        if ( s3 != null )
            System.out.println( s1 == s3 );
    }
}
