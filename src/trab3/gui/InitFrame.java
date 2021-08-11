package trab3.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;

public class InitFrame extends JFrame {
    public static final String RESOURCE_PATH = "./rsc/trab3";
    private final HashMap<String,PhoneFrame> opened = new HashMap<>();

    private final JTextField numberField = new JTextField(9);

    public InitFrame ( ) {
        super( "Open telephone" );
        Container container = getContentPane();
        container.setLayout( new BorderLayout() );

        setDefaultCloseOperation( EXIT_ON_CLOSE );
        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing ( WindowEvent e ) {
                opened.forEach( (k,v) -> {
                    try {
                        v.getTelephone().save();
                    } catch ( IOException exception ) {
                        exception.printStackTrace();
                    }
                });
            }
        });

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout( new FlowLayout() );
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout( new FlowLayout() );
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new FlowLayout() );

        JLabel numberLabel = new JLabel( "Insert the telephone");
        labelPanel.add( numberLabel );

        fieldPanel.add( numberField );

        JButton openButton = new JButton( "Open" );
        openButton.addActionListener( this::open );
        buttonPanel.add( openButton );

        JButton removeButton = new JButton( "Remove" );
        removeButton.addActionListener( this::open );
        buttonPanel.add( removeButton );

        container.add( labelPanel , BorderLayout.NORTH );
        container.add( fieldPanel , BorderLayout.CENTER );
        container.add( buttonPanel , BorderLayout.SOUTH );

        setLocationRelativeTo(null);
        setSize(320,240);
    }

    public static void main( String[] args ) {
        new InitFrame().setVisible( true );
    }

    private void open ( ActionEvent actionEvent ) {
        String number = numberField.getText();
        if ( number.length() != 9 ) {
            JOptionPane.showMessageDialog(
                    this,
                    String.format( "The entered number \"%s\" is in invalid format" , number ),
                    "Invalid number",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        if ( opened.containsKey(number) ) {
            JOptionPane.showMessageDialog(
                    this,
                    String.format( "The entered number \"%s\" is already opened" , number ),
                    "Already opened",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        PhoneFrame phoneFrame;
        try {
            phoneFrame = new PhoneFrame( this , numberField.getText() );
        }
        catch ( IOException e ) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "IOException",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        opened.put( number , phoneFrame );
        phoneFrame.setVisible( true );
    }

    public void startCall ( String origin , String destination ) {
        PhoneFrame destinationFrame = opened.get(destination);
        if ( destinationFrame == null ) {
            JOptionPane.showMessageDialog(
                    opened.get(origin),
                    String.format( "The entered number \"%s\" is unavailable" , destination ),
                    "Number unavailable",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        destinationFrame.ringCall( origin );
    }

    public void acceptCall ( String origin , String destination ) {
        PhoneFrame originFrame = opened.get(origin);
        originFrame.startCall( destination );
    }

    public void declineCall ( String origin , String destination ) {
        PhoneFrame originFrame = opened.get(origin);
        originFrame.declineCall( origin , destination );
    }

    public void missedCall ( String origin , String destination ) {
        PhoneFrame originFrame = opened.get(origin);
        originFrame.missedCall( origin , destination );
    }

    public void endCall ( String target ) {
        PhoneFrame targetFrame = opened.get(target);
        targetFrame.endCall();
    }

    public void exit ( String number ) {
        PhoneFrame phoneFrame = opened.get( number );
        try {
            phoneFrame.getTelephone().save();
        } catch ( IOException e ) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "IOException",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        } finally {
            opened.remove( number );
        }
    }
}
