package trab3.gui;

import trab3.Call;
import trab3.DateTime;
import trab3.Telephone;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Supplier;

public class PhoneMenuBar extends JMenuBar {
    private final Telephone telephone;
    private final CallTable table;

    private static class MenuItem extends AbstractMap.SimpleEntry<String, ActionListener> {
        public MenuItem ( String s , ActionListener l ) {
            super( s , l );
        }
    }

    private static class Menu extends JMenu {
        public Menu ( String name , MenuItem[] items ) {
            super( name );
            for ( MenuItem i : items ) {
                JMenuItem item = new JMenuItem( i.getKey() );
                item.addActionListener( i.getValue() );
                add( item );
            }
        }
    }

    public PhoneMenuBar ( Telephone telephone , CallTable table ) {
        this.telephone = telephone;
        this.table = table;

        MenuItem[] showMenuItems = {
                new MenuItem("Show all calls", this::showAll),
                new MenuItem("Show sent calls", this::showSent),
                new MenuItem("Show received calls", this::showReceived),
                new MenuItem("Show answered calls", this::showAnswered),
                new MenuItem("Show missed calls", this::showMissed),
                new MenuItem("Show declined calls", this::showDeclined),
                new MenuItem("Show calls sent to...", this::showCallsTo),
                new MenuItem("Show calls received from...", this::showCallsFrom)
        };
        add( new Menu ( "Show" , showMenuItems) );

        showAll( null );
    }

    public void showAll ( ActionEvent actionEvent ) {
        Supplier<Collection<Call>> supplier = () -> {
            Map<Call,Map<DateTime,Call>> groups = telephone.getRecord().getGroups();
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
        table.refresh( supplier , c -> true );
    }

    private void showSent ( ActionEvent actionEvent ) {
        table.refresh( telephone.getRecord()::getCalls , c -> c.getOrigin().equals(telephone.getNumber()) );
    }

    private void showReceived ( ActionEvent actionEvent ) {
        table.refresh( telephone.getRecord()::getCalls , c -> c.getDestination().equals(telephone.getNumber()) );
    }

    private void showAnswered ( ActionEvent actionEvent ) {
        table.refresh( telephone.getRecord()::getCalls , c -> c.getType().equals("answered") );
    }

    private void showMissed ( ActionEvent actionEvent ) {
        table.refresh( telephone.getRecord()::getCalls , c -> c.getType().equals("missed") );
    }

    private void showDeclined ( ActionEvent actionEvent ) {
        table.refresh( telephone.getRecord()::getCalls , c -> c.getType().equals("declined") );
    }

    private void showCallsTo ( ActionEvent actionEvent ) {
        String number = JOptionPane.showInputDialog(
                this ,
                "Calls sent to:",
                "Insert number",
                JOptionPane.QUESTION_MESSAGE
        );
        if ( number == null || number.length() == 0 )
            return;
        if ( number.length() != 9 ) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid number format",
                    "Invalid number",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        table.refresh( telephone.getRecord()::getCalls , c -> c.getDestination().equals(number) );
    }

    private void showCallsFrom ( ActionEvent actionEvent ) {
        String number = JOptionPane.showInputDialog(
                this ,
                "Calls received from:",
                "Insert number",
                JOptionPane.QUESTION_MESSAGE
        );
        if ( number == null || number.length() == 0 )
            return;
        if ( number.length() != 9 ) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid number format",
                    "Invalid number",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        table.refresh( telephone.getRecord()::getCalls , c -> c.getDestination().equals(number) );
    }
}
