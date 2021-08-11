package trab1.grupo3;

public class InvalidFormatException extends Exception {
    private final String resp;

    public InvalidFormatException ( String resp , String msg ) {
        super( resp + ": " + msg );
        this.resp = resp;
    }

    public String getResponse ( ) {
        return resp;
    }
}
