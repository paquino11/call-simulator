package trab1.grupo2;

public class SingleScore extends Score {
    private final int level;

    public SingleScore ( int l , int p ) {
        super(p);
        level = l;
    }

    public SingleScore ( int p ) {
        this(0,p);
    }

    @Override
    public int getLevel ( ) {
        return level;
    }
}
