package trab3.gui;

import trab2.noteBook.Contact;
import trab2.noteBook.gui.ContactDialog;
import trab3.Call;
import trab3.DateTime;
import trab3.Telephone;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class RecordPanel extends JPanel {
    private final Telephone telephone;
    private final JPanel optionsPanel = new JPanel( new FlowLayout(FlowLayout.LEFT) );
    private final ContactDialog contactDialog;
    private final CallTable callTable;
    private final CallTable groupTable;
    private final CallTable expandedTable;
    private final JScrollPane callScrollPane;
    private final JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    private Call selectedCall = null;
    private Contact selectedContact = null;
    private Supplier<Collection<Call>> supplier = null;
    private Predicate<Call> predicate = null;

    public RecordPanel ( PhoneFrame phoneFrame ,  Telephone telephone ) {
        this.telephone = telephone;
        callTable = new CallTable(telephone);
        groupTable = new CallTable(telephone);
        expandedTable = new CallTable(telephone);

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        optionsPanel.setBorder(new TitledBorder("Options"));
        optionsPanel.setVisible(false);
        add(optionsPanel);

        contactDialog = new ContactDialog( phoneFrame , telephone.getNoteBook()::add);

        JButton addOrEditButton = new JButton("Add/Edit contact");
        addOrEditButton.addActionListener( e -> {
            if ( selectedContact == null )
                contactDialog.setValue( null );
            else
                contactDialog.setValue(selectedContact);
            contactDialog.setVisible( true );
            repaint();
        });

        optionsPanel.add(addOrEditButton);

        add(optionsPanel);

        JScrollPane groupScrollPane = new JScrollPane(groupTable);
        groupScrollPane.setBorder( new TitledBorder("Group") );

        JScrollPane expandedScrollPane = new JScrollPane(expandedTable);
        expandedScrollPane.setBorder( new TitledBorder( "Select a group to expand calls" ) );

        groupTable.getSelectionModel().addListSelectionListener( e -> {
            int row = groupTable.getSelectedRow();
            if ( row == -1 ) {
                optionsPanel.setVisible(false);
                repaint();
                return;
            }
            for ( Map.Entry<Call, Map<DateTime, Call>> entry : telephone.getRecord().getGroups().entrySet() ) {
                selectedCall = entry.getKey();
                Map<DateTime, Call> expandedCalls = entry.getValue();
                String origin = telephone.resolveNumber(selectedCall.getOrigin());
                String destination = telephone.resolveNumber(selectedCall.getDestination());
                if (origin.equals(groupTable.getValueAt(row, 1)) && destination.equals(groupTable.getValueAt(row, 2))) {
                    String title, target;
                    if (telephone.getNumber().equals(selectedCall.getOrigin())) {
                        title = "Expanded calls to " + groupTable.getValueAt(row, 2);
                        target = destination;
                    } else {
                        title = "Expanded calls from " + groupTable.getValueAt(row, 1);
                        target = origin;
                    }
                    expandedScrollPane.setBorder(new TitledBorder(title));
                    supplier = expandedCalls::values;
                    expandedTable.refresh(expandedCalls::values, predicate);
                    selectedContact = telephone.getNoteBook().getContact(target);
                    optionsPanel.setVisible(true);
                    repaint();
                    break;
                }
            }
        });

        callScrollPane = new JScrollPane(callTable);
        callScrollPane.setBorder( new TitledBorder("List") );
        callScrollPane.setVisible(true);
        add(callScrollPane);

        JPanel groupPanel = new JPanel( );
        groupPanel.setLayout( new BoxLayout( groupPanel , BoxLayout.Y_AXIS ) );
        groupPanel.add(splitPane);
        splitPane.setLeftComponent( groupScrollPane );
        splitPane.setRightComponent( expandedScrollPane );
        splitPane.setVisible(false);
        add(groupPanel);
    }

    public void setListDisplay ( ) {
        callScrollPane.setVisible(true);
        splitPane.setVisible(false);
        updateUI();
    }

    public void setGroupDisplay ( ) {
        callScrollPane.setVisible(false);
        splitPane.setVisible(true);
        updateUI();
    }

    public void refresh ( Predicate<Call> predicate ) {
        this.predicate = predicate;
        if ( callScrollPane.isVisible() ) {
            callTable.refresh( telephone.getRecord()::getCalls , predicate );
        }
        else {
            Supplier<Collection<Call>> supplier = () -> {
                Map<Call, Map<DateTime,Call>> groups = telephone.getRecord().getGroups();
                Collection<Call> calls = new ArrayList<>();
                groups.forEach( (k,v) -> {
                    Call call = new Call(k);
                    LocalTime sum = LocalTime.MIN;
                    for ( Map.Entry<DateTime,Call> entry : v.entrySet() ) {
                        LocalTime duration = entry.getValue().getDuration();
                        sum = sum.plusHours( duration.getHour() );
                        sum = sum.plusMinutes( duration.getMinute() );
                        sum = sum.plusSeconds( duration.getSecond() );
                    }
                    call.setDuration(sum);
                    calls.add(call);
                });
                return calls;
            };
            groupTable.refresh( supplier , null );
            expandedTable.refresh( this.supplier , predicate );
        }
    }

    public void refresh ( ) {
        refresh(predicate);
    }
}
