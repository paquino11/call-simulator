package trab1.grupo3;

public abstract class Quest {

    private final Answer correct;
    private final String txt;

    protected Quest ( String t , Answer a ) {
        txt = t;
        correct = a;
    }

    public int checkPoints ( String resp ) throws InvalidFormatException {
        if( correct.check(resp) ) {
            return getPoints();
        }
        return 0;
    }

    public abstract int getPoints();

    public final String toString ( ) {
        return txt;
    }



}
