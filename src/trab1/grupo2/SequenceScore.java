package trab1.grupo2;

public class SequenceScore extends Score implements MultiLevel {
    private final Score previous;

    public SequenceScore ( Score prev , int p ) {
        super( prev.getPoints() + p );
        previous = prev;
    }

    @Override
    public int getLevel ( ) {
        return previous.getLevel() + 1;
    }

    @Override
    public int getLowerLevel ( ) {
        if ( previous instanceof MultiLevel )
            return ((MultiLevel) previous).getLowerLevel();
        return previous.getLevel();
    }

    public Score getPreviousScore ( ) {
        return previous;
    }

    @Override
    public String toString ( ) {
        return super.toString() + ", " + previous.toString();
    }
}
