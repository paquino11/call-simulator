package trab1.grupo2;

public abstract class Score implements Comparable<Score> {
    private final int points;

    protected Score ( int points ) {
        this.points = points;
    }

    public final int getPoints ( ) {
        return points;
    }

    @Override
    public String toString ( ) {
        return getLevel() + ": " + getPoints();
    }

    @Override
    public int compareTo ( Score s ) {
        int result = getLevel() - s.getLevel();
        return result == 0 ? getPoints() - s.getPoints() : result;
    }

    abstract public int getLevel();

    public static int sumPoints ( Score ... s ) {
        int counter = 0;
        for ( Score score : s )
            counter += score.getPoints();
        return counter;
    }
}
