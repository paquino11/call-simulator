package trab3.gui;

import trab3.Call;
import trab3.Telephone;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CallTable extends JTable {
    private final Telephone telephone;
    private Supplier<Collection<Call>> supplier = null;
    private Predicate<Call> predicate = null;

    public CallTable ( Telephone telephone ) {
        this.telephone = telephone;
    }

    public void refresh ( Supplier<Collection<Call>> supplier , Predicate<Call> predicate ) {
        String[] header = { "Type" , "Origin" , "Destination" , "Date" , "Duration" };
        List<String[]> data = new ArrayList<>();

        if ( supplier != null ) {
            supplier.get().forEach( c-> {
                if ( predicate == null || predicate.test(c) ) {
                    String[] row = new String[5];
                    LocalTime localTime = c.getStart().getTime();
                    row[0] = c.getType();
                    row[1] = telephone.resolveNumber( c.getOrigin() );
                    row[2] = telephone.resolveNumber( c.getDestination() );
                    row[3] = String.format( "%s %02d:%02d" , c.getStart().getDate() , localTime.getHour() , localTime.getMinute() );
                    row[4] = c.getDuration().format( DateTimeFormatter.ISO_TIME );
                    data.add(row);
                }
            });
        }

        DefaultTableModel model = new DefaultTableModel( data.toArray(new String[0][0]) , header );
        setModel(model);
        model.fireTableDataChanged();

        this.supplier = supplier;
        this.predicate = predicate;
    }

    public void refresh ( ) {
        refresh( supplier , predicate );
    }
}
