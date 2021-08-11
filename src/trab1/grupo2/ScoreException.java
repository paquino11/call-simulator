package trab1.grupo2;

public class ScoreException extends Exception {

    public ScoreException ( String msg ) {
        super(msg);
    }

    public ScoreException ( ) {
        super( "pontuação inválida" );
    }

}
