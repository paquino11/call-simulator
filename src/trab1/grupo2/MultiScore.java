package trab1.grupo2;

import java.util.Arrays;

public class MultiScore extends Score implements MultiLevel {
    protected final SingleScore[] ss;

    public MultiScore ( SingleScore ... ss ) throws ScoreException {
        super( sumPoints(ss) );
        if ( ss.length < 2 )
            throw new ScoreException( "invalid number of scores" );
        Arrays.sort(ss);
        for ( int i=1 ; i < ss.length ; i++ ) {
            if ( ss[i].getLevel() == ss[i-1].getLevel() )
                throw new ScoreException( "invalid sequence" );
        }
        this.ss = Arrays.copyOf( ss , ss.length );
    }

    @Override
    public int getLevel() {
        return ss[ss.length-1].getLevel();
    }

    @Override
    public int getLowerLevel() {
        return ss[0].getLevel();
    }

    @Override
    public String toString ( ) {
        StringBuilder s = new StringBuilder();
        for ( int i=0 ; i < ss.length-1 ; i++ )
            s.append(ss[i]).append(", ");
        s.append(ss[ss.length-1]);
        return super.toString() + " (" + s + ")";
    }
}
