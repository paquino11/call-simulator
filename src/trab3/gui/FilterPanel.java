package trab3.gui;

import trab2.noteBook.NoteBook;
import trab3.Call;
import trab3.Telephone;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.function.Predicate;

public class FilterPanel extends JPanel implements ActionListener {
    private final Telephone telephone;
    private final RecordPanel recordPanel;

    private final JRadioButton listRadioButton = new JRadioButton("List");
    private final JRadioButton groupRadioButton = new JRadioButton("Group");

    private final JRadioButton allTypesRadioButton = new JRadioButton("All");
    private final JRadioButton answeredRadioButton = new JRadioButton("Answered");
    private final JRadioButton missedRadioButton = new JRadioButton("Missed");
    private final JRadioButton declinedRadioButton = new JRadioButton("Declined");

    private final JRadioButton allDirectionsRadioButton = new JRadioButton("All");
    private final JRadioButton sentRadioButton = new JRadioButton("Sent");
    private final JRadioButton receivedRadioButton = new JRadioButton("Received");

    private final JRadioButton allOriginRadioButton = new JRadioButton("All");
    private final JRadioButton originNumberRadioButton = new JRadioButton("Number");
    private final JTextField originNumberField = new JTextField(10);
    private final JRadioButton originContactRadioButton = new JRadioButton("Contact");
    private final ContactPanel originContactPanel;

    private final JRadioButton allDestinationRadioButton = new JRadioButton("All");
    private final JRadioButton destinationNumberRadioButton = new JRadioButton("Number");
    private final JTextField destinationNumberField = new JTextField(10);
    private final JRadioButton destinationContactRadioButton = new JRadioButton("Contact");
    private final ContactPanel destinationContactPanel;

    public FilterPanel ( Telephone telephone , RecordPanel recordPanel ) {
        this.telephone = telephone;
        this.recordPanel = recordPanel;

        setLayout( new BoxLayout( this , BoxLayout.X_AXIS ) );
        setBorder( new TitledBorder("Filter") );
        setMinimumSize( new Dimension(640,480) );

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout( new BoxLayout( displayPanel , BoxLayout.Y_AXIS ) );
        displayPanel.setBorder( new TitledBorder("Display") );
        add(displayPanel);

        JPanel typePanel = new JPanel();
        typePanel.setLayout( new BoxLayout( typePanel , BoxLayout.Y_AXIS ) );
        typePanel.setBorder( new TitledBorder("Type") );
        add(typePanel);

        JPanel directionPanel = new JPanel();
        directionPanel.setLayout( new BoxLayout( directionPanel , BoxLayout.Y_AXIS ) );
        directionPanel.setBorder( new TitledBorder("Direction") );
        add(directionPanel);

        JPanel originPanel = new JPanel();
        originPanel.setLayout( new BoxLayout( originPanel , BoxLayout.Y_AXIS ) );
        originPanel.setBorder( new TitledBorder("Origin") );
        add(originPanel);

        JPanel destinationPanel = new JPanel();
        destinationPanel.setLayout( new BoxLayout( destinationPanel , BoxLayout.Y_AXIS ) );
        destinationPanel.setBorder( new TitledBorder("Destination") );
        add(destinationPanel);

        /* Display filter options */
        ButtonGroup displayButtonGroup = new ButtonGroup();
        displayButtonGroup.add(listRadioButton);
        displayButtonGroup.add(groupRadioButton);

        listRadioButton.setMnemonic(KeyEvent.VK_L);
        groupRadioButton.setMnemonic(KeyEvent.VK_G);

        listRadioButton.addActionListener( e -> { recordPanel.setListDisplay(); recordPanel.refresh(); });
        groupRadioButton.addActionListener( e -> { recordPanel.setGroupDisplay(); recordPanel.refresh(); });

        listRadioButton.setSelected(true);

        displayPanel.add(listRadioButton);
        displayPanel.add(groupRadioButton);

        /* Type filter options */
        ButtonGroup typeButtonGroup = new ButtonGroup();
        typeButtonGroup.add(allTypesRadioButton);
        typeButtonGroup.add(answeredRadioButton);
        typeButtonGroup.add(missedRadioButton);
        typeButtonGroup.add(declinedRadioButton);

        answeredRadioButton.setMnemonic(KeyEvent.VK_A);
        missedRadioButton.setMnemonic(KeyEvent.VK_M);
        declinedRadioButton.setMnemonic(KeyEvent.VK_D);

        allTypesRadioButton.setSelected(true);

        allTypesRadioButton.addActionListener(this);
        answeredRadioButton.addActionListener(this);
        missedRadioButton.addActionListener(this);
        declinedRadioButton.addActionListener(this);

        typePanel.add(allTypesRadioButton);
        typePanel.add(answeredRadioButton);
        typePanel.add(missedRadioButton);
        typePanel.add(declinedRadioButton);

        /* Direction filter options */
        ButtonGroup directionButtonGroup = new ButtonGroup();
        directionButtonGroup.add(allDirectionsRadioButton);
        directionButtonGroup.add(sentRadioButton);
        directionButtonGroup.add(receivedRadioButton);

        sentRadioButton.setMnemonic(KeyEvent.VK_S);
        receivedRadioButton.setMnemonic(KeyEvent.VK_R);

        allDirectionsRadioButton.setSelected(true);

        allDirectionsRadioButton.addActionListener(this);
        sentRadioButton.addActionListener(this);
        receivedRadioButton.addActionListener(this);

        directionPanel.add(allDirectionsRadioButton);
        directionPanel.add(sentRadioButton);
        directionPanel.add(receivedRadioButton);

        /* Origin filter options */
        ButtonGroup originButtonGroup = new ButtonGroup();
        originButtonGroup.add(allOriginRadioButton);
        originButtonGroup.add(originNumberRadioButton);
        originButtonGroup.add(originContactRadioButton);

        allOriginRadioButton.setSelected(true);

        originNumberField.setBorder( new TitledBorder("Number") );
        originNumberField.setVisible(false);

        originContactPanel = new ContactPanel( telephone.getNoteBook() );
        originContactPanel.setVisible(false);

        allOriginRadioButton.addActionListener(this);
        originNumberRadioButton.addActionListener(this);
        originNumberField.addActionListener(this);
        originContactRadioButton.addActionListener(this);

        originPanel.add(allOriginRadioButton);
        originPanel.add(originNumberRadioButton);
        originPanel.add(originContactRadioButton);
        originPanel.add(originNumberField);
        originPanel.add(originContactPanel);

        /* Destination filter options */
        ButtonGroup destinationGroupButton = new ButtonGroup();
        destinationGroupButton.add(allDestinationRadioButton);
        destinationGroupButton.add(destinationNumberRadioButton);
        destinationGroupButton.add(destinationContactRadioButton);

        allDestinationRadioButton.setSelected(true);

        destinationNumberField.setBorder( new TitledBorder("Number") );
        destinationNumberField.setVisible(false);

        destinationContactPanel = new ContactPanel( telephone.getNoteBook() );
        destinationContactPanel.setVisible(false);

        allDestinationRadioButton.addActionListener(this);
        destinationNumberRadioButton.addActionListener(this);
        destinationNumberField.addActionListener(this);
        destinationContactRadioButton.addActionListener(this);

        destinationPanel.add(allDestinationRadioButton);
        destinationPanel.add(destinationNumberRadioButton);
        destinationPanel.add(destinationContactRadioButton);
        destinationPanel.add(destinationNumberField);
        destinationPanel.add(destinationContactPanel);

        filter();
    }

    public void actionPerformed ( ActionEvent e ) {
        if ( e.getSource().equals(allOriginRadioButton) ) {
            originNumberField.setVisible(false);
            originContactPanel.setVisible(false);
            updateUI();
        }
        else if ( e.getSource().equals(originNumberRadioButton) ) {
            originNumberField.setVisible(true);
            originContactPanel.setVisible(false);
            updateUI();
        }
        else if ( e.getSource().equals(originContactRadioButton) ) {
            originNumberField.setVisible(false);
            originContactPanel.setVisible(true);
            updateUI();
        }
        else if ( e.getSource().equals(allDestinationRadioButton) ) {
            destinationNumberField.setVisible(false);
            destinationContactPanel.setVisible(false);
            updateUI();
        }
        else if ( e.getSource().equals(destinationNumberRadioButton) ) {
            destinationNumberField.setVisible(true);
            destinationContactPanel.setVisible(false);
            updateUI();
        }
        else if ( e.getSource().equals(destinationContactRadioButton) ) {
            destinationNumberField.setVisible(false);
            destinationContactPanel.setVisible(true);
            updateUI();
        }
        filter();
    }

    public void filter ( ) {
        Predicate<Call> predicate = c -> true;

        if ( !allTypesRadioButton.isSelected() ) {
            if ( answeredRadioButton.isSelected() )
                predicate = predicate.and( c -> c.getType().equals("answered") );
            else if ( missedRadioButton.isSelected() )
                predicate = predicate.and( c -> c.getType().equals("missed") );
            else if ( declinedRadioButton.isSelected() )
                predicate = predicate.and( c -> c.getType().equals("declined") );
        }

        if ( !allDirectionsRadioButton.isSelected() ) {
            if ( sentRadioButton.isSelected() )
                predicate = predicate.and( c -> c.getOrigin().equals(telephone.getNumber()) );
            else if ( receivedRadioButton.isSelected() )
                predicate = predicate.and( c -> c.getDestination().equals(telephone.getNumber()) );
        }

        if ( !allOriginRadioButton.isSelected() ) {
            if ( originNumberRadioButton.isSelected() ) {
                String number = originNumberField.getText();
                if ( !number.isBlank() )
                    predicate = predicate.and( c -> c.getOrigin().equals( number ) );
            }
            else if ( originContactRadioButton.isSelected() ) {
                predicate = predicate.and( c -> c.getOrigin().equals( originContactPanel.getNumber() ) );
            }
        }

        if ( !allDestinationRadioButton.isSelected() ) {
            if ( destinationNumberRadioButton.isSelected() ) {
                String number = destinationNumberField.getText();
                if ( !number.isBlank() )
                    predicate = predicate.and( c -> c.getDestination().equals( number ) );
            }
            else if ( destinationContactRadioButton.isSelected() ) {
                predicate = predicate.and( c -> c.getDestination().equals( destinationContactPanel.getNumber() ) );
            }
        }
        recordPanel.refresh(predicate);
    }

}
