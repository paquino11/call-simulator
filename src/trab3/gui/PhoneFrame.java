package trab3.gui;

import trab2.noteBook.*;
import trab2.noteBook.gui.ContactDialog;
import trab3.Call;
import trab3.Telephone;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class PhoneFrame extends JFrame {
    private final Telephone telephone;
    private final CallTable table;
    private final InitFrame initFrame;
    private final NoteBookFrame noteBookFrame;
    private final JButton callNumberButton = new JButton( "Call number" );
    private final JButton callContactButton = new JButton( "Call contact" );
    private final ContactPanel contactPanel;
    private CallFrame callFrame = null;
    private final RecordPanel recordPanel;

    public PhoneFrame ( InitFrame initFrame , String number ) throws IOException {
        super( String.format( "Mobile Phone [%s]" , number ) );
        this.initFrame = initFrame;
        telephone = new Telephone( number, InitFrame.RESOURCE_PATH );

        noteBookFrame = new NoteBookFrame( telephone.getNoteBook() );
        noteBookFrame.setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e ) {
                initFrame.exit( telephone.getNumber() );
            }
        });

        contactPanel = new ContactPanel(telephone.getNoteBook());

        table = new CallTable( telephone );

        recordPanel = new RecordPanel(this,telephone);
        add(recordPanel, BorderLayout.CENTER );

        FilterPanel filterPanel = new FilterPanel( telephone , recordPanel );

        add( filterPanel , BorderLayout.NORTH );

        JPanel buttonPanel = new JPanel();

        callNumberButton.addActionListener( this::startCall );
        buttonPanel.add(callNumberButton);

        callContactButton.addActionListener( this::startCall );
        buttonPanel.add(callContactButton);

        JButton noteBookButton = new JButton( "Notebook" );
        noteBookButton.addActionListener( this::notebook );
        buttonPanel.add(noteBookButton);

        add( buttonPanel , BorderLayout.SOUTH );

        setLocationRelativeTo(initFrame);

        pack();
    }

    public Telephone getTelephone() {
        return telephone;
    }

    private void startCall ( ActionEvent e ) {
        if ( e.getSource().equals(callNumberButton) ) {
            String destination = JOptionPane.showInputDialog(
                    this,
                    "Insert number",
                    "Make Call",
                    JOptionPane.QUESTION_MESSAGE
            );
            if ( destination == null || destination.equals("") )
                return;
            initFrame.startCall( telephone.getNumber() , destination );
        }
        else if ( e.getSource().equals(callContactButton) ){
            JPanel panel = new JPanel();
            panel.setLayout( new BorderLayout() );
            panel.add( new Label("Select the contact") , BorderLayout.NORTH );
            panel.add( contactPanel , BorderLayout.CENTER );
            int result = JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Make Call",
                    JOptionPane.YES_NO_OPTION
            );
            if ( result == JOptionPane.YES_OPTION )
                initFrame.startCall( telephone.getNumber() , contactPanel.getNumber() );
        }
    }

    public void startCall ( String destination ) {
        Call call = Call.answeredCall( getTelephone().getNumber() , destination );
        callFrame = new CallFrame( this , call );
    }

    public void ringCall ( String origin ) {
        RingFrame ringFrame = new RingFrame( this , origin , telephone.getNumber() );
        ringFrame.setVisible(true);
    }

    public void acceptCall ( String origin ) {
        initFrame.acceptCall( origin , telephone.getNumber() );
    }

    public void declineCall ( String origin ) {
        initFrame.declineCall( origin , getTelephone().getNumber() );
        recordPanel.refresh();
    }

    public void declineCall ( String origin , String destination ) {
        telephone.getRecord().add( Call.declinedCall( origin , destination ) );
        recordPanel.refresh();
    }

    public void missedCall ( String origin ) {
        initFrame.missedCall( origin , getTelephone().getNumber() );
        recordPanel.refresh();
    }

    public void missedCall ( String origin , String destination ) {
        telephone.getRecord().add( Call.missedCall( origin , destination ) );
        recordPanel.refresh();
    }

    public void endCall ( ) {
        callFrame.endCall();
        recordPanel.refresh();
    }

    public void endCall ( String target ) {
        initFrame.endCall(target);
        recordPanel.refresh();
    }

    private void notebook ( ActionEvent actionEvent ) {
        noteBookFrame.setVisible( true );
    }

    public void setCallFrame ( CallFrame cf ) {
        callFrame = cf;
        if ( cf != null ) {
            callFrame.setVisible( true );
        }
    }
}

