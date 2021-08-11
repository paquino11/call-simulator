package trab3;

import trab2.noteBook.Contact;
import trab2.noteBook.NoteBook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Telephone {
    private final String number, pathname;
    private final NoteBook noteBook = new NoteBook();
    private final Record record;

    public Telephone ( String number , String pathname ) throws IOException {
        this.number = number;
        this.record = new Record( number );
        this.pathname = String.format( "%s/tel-%s" , pathname , number );
        if ( Files.exists( Paths.get( this.pathname ) ) ) {
            this.noteBook.read( new File( this.pathname + "/contacts.txt" ) );
            this.record.read( new File( this.pathname + "/calls.txt" ) );
        }
    }

    public String getNumber ( ) {
        return number;
    }

    public NoteBook getNoteBook ( ) {
        return noteBook;
    }

    public Record getRecord ( ) {
        return record;
    }

    public void save ( ) throws IOException {
        if ( Files.notExists( Paths.get( this.pathname ) ) )
            Files.createDirectory( Paths.get( this.pathname ) );
        this.noteBook.write( new File( pathname + "/contacts.txt" ) );
        this.record.write( new File( pathname + "/calls.txt" ) );
    }

    public String resolveNumber ( String number ) {
        if ( number.equals(this.number) )
            return "Me";
        Iterable<Contact> contacts = noteBook.getContactsOf( number );
        if ( contacts == null )
            return number;
        Iterator<Contact> iterator = contacts.iterator();
        if ( iterator.hasNext() ) {
            Contact contact = iterator.next();
            if ( iterator.hasNext() )
                return number;
            return contact.getName();
        }
        return number;
    }

    public Collection<String> resolveName ( String name ) {
        if ( name.equals("Me") ) {
            Collection<String> resolved = new ArrayList<>();
            resolved.add(number);
            return resolved;
        }
        Contact contact = noteBook.getContact( name );
        if ( contact == null )
            return new ArrayList<>();
        return contact.getTelephones();
    }
}
