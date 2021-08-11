package trab3.gui;

import trab3.Call;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;

public class CallFrame extends JFrame {
    private final PhoneFrame parent;
    private final Call call;
    private final Timer timer = new Timer ( 1000 , this::count );
    private LocalTime duration = LocalTime.MIN;

    public CallFrame ( PhoneFrame parent , Call call ) {
        super( "In call with " + parent.getTelephone().getNumber() );
        this.parent = parent;
        this.call = call;

        Container container = getContentPane();
        container.setLayout( new BorderLayout() );

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing ( WindowEvent e ) {
                endCall(e);
            }
        });

        JButton hangUpButton = new JButton( "Hang up" );
        hangUpButton.addActionListener( this::endCall );
        container.add( hangUpButton );

        setLocation( parent.getLocation() );
        container.setPreferredSize( parent.getSize() );

        setLocationRelativeTo(parent);

        pack();

        timer.start();
    }

    private void count ( AWTEvent e ) {
        duration = duration.plusSeconds(1);
    }

    public void endCall ( ) {
        dispose();
        timer.stop();
        call.setDuration( duration );
        parent.getTelephone().getRecord().add(call);
    }

    private void endCall ( AWTEvent e ) {
        endCall();
        String number = parent.getTelephone().getNumber();
        String target = number.equals( call.getOrigin() ) ? call.getDestination() : call.getOrigin();
        parent.endCall( target );
    }
}
