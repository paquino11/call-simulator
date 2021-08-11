package trab1.grupo3;

public class YesNoAnswer implements Answer{
    private final boolean answer;

    public YesNoAnswer ( boolean answer ) {
        this.answer = answer;
    }

    public boolean check ( String resp ) throws InvalidFormatException {
        if( resp.equals( "sim" ) )
            return answer;
        if( resp.equals( "não" ) )
            return !answer;
        throw new InvalidFormatException( resp , "nem é sim nem é não" );
    }
}
