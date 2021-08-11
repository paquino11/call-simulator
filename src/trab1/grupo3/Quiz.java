package trab1.grupo3;

import java.util.Scanner;

public class Quiz {
    private final Quest[] quiz;

    public Quiz ( Quest ... quiz ) {
        this.quiz = quiz;
    }

    public void run ( Scanner input ) {
        int total=0, nq=0;

        while ( nq < quiz.length ) {
            Quest q = this.quiz[nq];
            try {
                System.out.print( q + "[" + q.getPoints() + "] ?" );
                total += q.checkPoints( input.nextLine() );
                ++nq;
            } catch ( InvalidFormatException e ) {
                System.out.println( e.getMessage() );
            }
            System.out.println( "Pontos= " + total );
        }

    }
}
