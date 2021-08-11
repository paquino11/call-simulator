package trab1.grupo3;

public class IndexAnswer implements Answer {
    private final int answer;

    public IndexAnswer ( int q ) {
        answer = q;
    }

    public boolean check ( String resp ) throws InvalidFormatException {
        try {
            return Integer.parseInt(resp) == answer;
        } catch ( NumberFormatException e ) {
            throw new InvalidFormatException( resp , "não é um número" );
        }
    }

    public int getIndex(){
        return answer;
    }
}
