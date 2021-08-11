package trab3.gui;

import trab3.Call;

import javax.swing.*;
import java.awt.*;

public class RingFrame extends JFrame {
    private final PhoneFrame parent;
    private final String origin, destination;
    private final Timer timer = new Timer( 2000 , this::timeOut );

    public RingFrame ( PhoneFrame parent , String origin , String destination ) {
        super( "Ringing" );
        this.parent = parent;
        this.origin = origin;
        this.destination = destination;

        setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        Container container = getContentPane();

        JPanel centerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        JLabel label = new JLabel( origin + " is calling. Answer?" );
        centerPanel.add( label , BorderLayout.CENTER );

        JButton acceptButton = new JButton( "Accept" );
        acceptButton.addActionListener( this::acceptCall );
        buttonPanel.add( acceptButton );

        JButton declineButton = new JButton( "Decline" );
        declineButton.addActionListener( this::declineCall );
        buttonPanel.add( declineButton );

        container.add( centerPanel , BorderLayout.CENTER );
        container.add( buttonPanel , BorderLayout.SOUTH );

        container.setPreferredSize( parent.getSize() );

        setLocationRelativeTo(parent);

        pack();

        timer.setRepeats(false);
        timer.start();
    }

    private void acceptCall ( AWTEvent e ) {
        Call call = Call.answeredCall( origin , destination );
        parent.setCallFrame( new CallFrame( parent , call ) );
        parent.acceptCall( origin );
        dispose();
        timer.stop();
    }

    private void declineCall ( AWTEvent e ) {
        Call call = Call.declinedCall( origin , destination );
        parent.getTelephone().getRecord().add(call);
        parent.declineCall( origin );
        dispose();
        timer.stop();
    }

    private void timeOut ( AWTEvent e ) {
        Call call = Call.missedCall( origin , destination );
        parent.getTelephone().getRecord().add(call);
        parent.missedCall( origin );
        dispose();
    }
}
