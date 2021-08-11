package trab2.grupo1;

public class Main {
    public static final int SIZE_WIDTH = 640;
    public static final int SIZE_HEIGHT = 480;
    public static final int LOCATION_X = 450;
    public static final int LOCATION_Y = 200;

    public static final String RESOURCE_PATH = "./rsc/trab2/grupo1";

    public static void main ( String[] args ) {
        Window window = new Window( RESOURCE_PATH , LOCATION_X , LOCATION_Y , SIZE_WIDTH , SIZE_HEIGHT );
        window.setVisible( true );
    }
}
