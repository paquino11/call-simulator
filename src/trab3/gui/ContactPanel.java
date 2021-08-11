package trab3.gui;

import trab2.noteBook.NoteBook;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactPanel extends JPanel implements ActionListener {
    private final JComboBox<String> nameComboBox = new JComboBox<>();
    private final JComboBox<String> numberComboBox = new JComboBox<>();

    private final NoteBook noteBook;
    private String number;

    public ContactPanel ( NoteBook noteBook ) {
        this.noteBook = noteBook;
        setLayout( new BoxLayout( this , BoxLayout.Y_AXIS ) );
        nameComboBox.setBorder( new TitledBorder("Name") );
        numberComboBox.setBorder( new TitledBorder("Number") );
        nameComboBox.addActionListener(this);
        numberComboBox.addActionListener(this);
        add(nameComboBox);
        add(numberComboBox);
        refresh();
    }

    public String getNumber ( ) {
        return number;
    }

    public void actionPerformed ( ActionEvent e ) {
        if ( e.getSource().equals(nameComboBox) ) {
            numberComboBox.removeAllItems();
            String name = (String) nameComboBox.getSelectedItem();
            if ( name == null || name.equals("<empty>") )
                return;
            Collection<String> numbers = noteBook.getContact(name).getTelephones();
            if ( numbers == null || numbers.size() == 0 )
                return;
            numbers.forEach(numberComboBox::addItem);
            numberComboBox.setSelectedIndex(0);
        }
        else {
            number = (String) numberComboBox.getSelectedItem();
        }
    }

    public void refresh ( ) {
        nameComboBox.removeAllItems();
        numberComboBox.removeAllItems();
        List<String> names = new ArrayList<>();
        noteBook.getAllContacts().forEach( c -> names.add(c.getName()) );
        if ( names.isEmpty() ) {
            nameComboBox.addItem("<empty>");
            numberComboBox.addItem("<empty>");
            number = null;
        }
        else {
            names.forEach(nameComboBox::addItem);
            nameComboBox.setSelectedIndex(0);
        }
    }
}
