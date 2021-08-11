package trab1.grupo3;

import java.util.Arrays;

public class SelectQuest extends Quest {

    private final Quest[] choices;

    public SelectQuest ( Answer a , Quest ... q ) {
        super( txt(q) , a );
        choices = q;
    }

    private static String txt ( Quest a[] ) {
        StringBuilder sb = new StringBuilder( "Qual das questões é verdadeira:\n" );
        for ( int i = 0 ; i< a.length ; i++ )
            sb.append(' ').append(i+1).append(" - ").append(a[i]).append('\n');
        return sb.toString();
    }

    public int getPoints ( ) {
        int mean = 0;

        for ( Quest q : choices ) {
            mean += q.getPoints();
        }
        return mean/choices.length;
    }

    public Quest[] getChoices(){
        return Arrays.copyOf( choices , choices.length );
    }
}
