package trab1.grupo1;

public class Friend {
    private String name, birth;
    public Friend ( String nm , int d , int m ) {
        this.name = nm;
        this.birth = String.format( "%2d\\%2d" , d , m );
    }

    public String getName ( ) {
        return name;
    }

    public String getBirth ( ) {
        return birth;
    }

    @Override
    public String toString ( ) {
        return name + " birth in " + birth;
    }

    @Override
    public boolean equals ( Object o ) {
        if ( this == o )
            return true;
        if ( o instanceof Friend ) {
            Friend f = (Friend) o;
            return name.equals(f.getName()) && birth.equals(f.getBirth());
        }
        return false;
    }
}
