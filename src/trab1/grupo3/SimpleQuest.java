package trab1.grupo3;

public class SimpleQuest extends Quest {
    private final int points;

    public SimpleQuest ( String t , Answer a , int p ) {
        super( t , a );
        points = p;
    }

    public SimpleQuest ( String t ) {
        this( t , new YesNoAnswer(true) , 100 );
    }

    public int getPoints ( ) {
        return points;
    }
}
