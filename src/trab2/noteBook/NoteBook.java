package trab2.noteBook;

import java.io.*;
import java.util.*;

/**
 * Agenda.
 */
public class NoteBook {
    /**
     * Contentor associativo, associa o nome ao contacto.
     * A chave é o nome do contacto.
     * Não podem existir dois contactos com o mesmo nome.
     */
    private final Map< String, Contact> contacts = new TreeMap<>();

    /**
     * Contentor associativo de número de telefones.
     * A chave é o número de telefone o valor associado são os contactos que têm o mesmo número de telefone.
     */
    private final Map< String, SortedSet<Contact> > telephones = new HashMap<>();

    /**
     * Contentor associativo ordenado por datas de nascimento de contactos cujo aniversário é no mesmo dia/mes.
     * A chave data de nascimento o valor associado são os contactos que fazem anos no mesmo dia.
     */
    private final SortedMap<Date, SortedSet<Contact> > birthdays = new TreeMap<>( (d1, d2) -> {
        int result = d1.getMonth() - d2.getMonth();
        if ( result == 0 )
            return d1.getDay() - d2.getDay();
        return result;
    });

    /**
     * Adiciona um contacto ao contentor associativo de contactos contact.
     * - Caso não exista um contacto com o mesmo nome adiciona-o.
     * - Caso já exista um contacto com o mesmo nome e data de nascimento,
     *   adiciona os números de telefone ao contacto já existente.
     * - Caso já exista um contacto com o mesmo nome e data de nascimento
     *   diferente não adiciona.
     * Actualiza o contentor de aniversários com os telefones deste contacto.
     * Actualiza o contentor de telefones com os telefones deste contacto.
     *
     * @param contact contacto a adicionar
     * @return true caso tenha adicionado ou atualizado as estruturas.
     */
    public boolean add ( Contact contact ) {
        boolean changed = Utils.actualize(
                contacts,
                contact::getName,
                () -> contact,
                (c) -> c.join( contact )
        );
        contact.getTelephones().forEach( s -> Utils.actualize(
                telephones,
                () -> s,
                ()-> {
                    TreeSet<Contact> c = new TreeSet<>();
                    c.add(contact);
                    return c;
                },
                (c) -> c.add(contact)
        ));
        Utils.actualize(
                birthdays,
                contact::getBirthDate,
                () -> {
                    TreeSet<Contact> c = new TreeSet<>();
                    c.add(contact);
                    return c;
                },
                (c) -> c.add(contact)
        );
        return changed;
    }

    /**
     * Adicionar todos os contactos doutra agenda.
     * Não adiciona caso já exista um contacto com o mesmo nome e data de nascimento, neste caso
     * adiciona os numeros de telefone ao contacto já existente.
     * @param nb agenda
     */
    public void add ( NoteBook nb ) {
        nb.contacts.forEach( (s,c) -> add(c) );
    }

    /**
     * Remove um contacto com determinado nome dos contactos,
     * dos telefones e das data de nascimento.
     * @param name nome do contacto
     */
    public boolean remove ( String name ) {
        Contact removed = contacts.remove( name );

        if ( removed == null )
            return false;

        Collection<String> tel = removed.getTelephones();
        tel.forEach( (s) -> telephones.get(s).remove( removed ) );
        birthdays.get( removed.getBirthDate() ).remove( removed );

        return true;
    }

    /**
     * Remove todos os contactos.
     */
    public void clear ( ) {
        contacts.clear();
        telephones.clear();
        birthdays.clear();
    }

    /**
     * Obter o contacto dado o nome.
     * @param name nome do contacto
     * @return o contacto ou null caso não exista.
     */
    public Contact getContact ( String name ) {
        return contacts.get( name );
    }

    /**
     * Obter os contactos.
     * @return uma coleção de contactos inalterável.
     */
    public Iterable<Contact> getAllContacts() {
        return contacts.values();
    }

    /**
     * Obter os contactos que fazem anos num determinado dia/mes.
     * @param day  dia
     * @param month  mês
     * @return sequencia de contactos ordenada por data.
     */
    public Iterable<Contact> getBirthdays ( int day , int month ) {
        SortedSet<Contact> contacts = new TreeSet<>();
        birthdays.forEach( (d,c) -> {
            if ( d.getDay() == day && d.getMonth() == month ) {
                contacts.addAll(c);
            }
        });
        return contacts;
    }

    /**
     * Obter os contactos que têm determinado numero de telefone.
     * @param phone número de telefone
     * @return sequencia de contactos.
     */
    public Iterable<Contact> getContactsOf ( String phone ) {
        return telephones.get( phone );
   }

    /**
     * Obter os contactos que fazem anos num determinado mes.
     * @param month  mês
     * @return sequencia de contactos ordenada por data.
     */
    public Iterable<Contact> getBirthdays ( int month ) {
        SortedSet<Contact> contacts = new TreeSet<>();
        birthdays.forEach( (d,c) -> {
            if ( d.getMonth() == month ) {
                contacts.addAll(c);
            }
        });
        return contacts;
    }

    /**
     * Lê os contactos do ficheiro de texto para a agenda.
     * @param file ficheiro de leitura.
     * @throws IOException ficheiro inválido.
     */
    public void read ( File file ) throws IOException {
        BufferedReader in = new BufferedReader( new FileReader( file ) );
        for ( String line = in.readLine() ; line != null ; line = in.readLine() ) {
            /* Realiza a leitura da data */
            int idx = line.indexOf(' ');
            if ( idx == -1 )
                throw new IOException( "File in wrong format" );
            Date date = new Date( line.substring( 0 , idx ) );
            line = line.substring( idx + 1 );

            /* Realiza a leitura do nome */
            idx = line.indexOf('[');
            String name;
            if ( idx == -1 ) {
                name = line.trim();
                Contact c = new Contact( name , date );
                add(c);
                continue;
            }
            name = line.substring( 0 , idx ).trim();
            Contact c = new Contact( name , date );
            line = line.substring( idx + 1 );

            /* Realiza a leitura de cada número */
            Collection<String> telephones = new LinkedList<>();
            for ( idx = line.indexOf(',') ; idx != -1 ; idx = line.indexOf(',') ) {
                telephones.add( line.substring( 0 , idx ).trim() );
                line = line.substring( idx + 1 );
            }

            /* Realiza a leitura do último número */
            idx = line.indexOf(']');
            if ( idx == -1 )
                throw new IOException( "File in wrong format" );
            telephones.add( line.substring( 0 , idx ).trim() );

            c.addTelephones( telephones );
            add(c);
        }
    }

    /**
     * Escreve todos os contactos da agenda num ficheiro de texto.
     * @param file ficheiro de escrita.
     * @throws IOException ficheiro inválido.
     */
    public void write ( File file ) throws IOException {
        PrintWriter out = new PrintWriter( file );
        contacts.forEach( (s,c) -> out.println( c.getBirthDate().toString() + " " + s + " " + c.getTelephones() ) );
        out.close();
    }

    /**
     * Obtem os telefones com maior número de contactos.
     */
    public Collection<String> greaterContacts ( ) {
        return Utils.greater( telephones , Comparator.comparingInt( Set::size ) );
    }

    /**
     * Métodos para obter os contactos com maior numero de telefones.
     */
    public Collection<Contact> greaterTelephones ( ) {
        Collection<String> name = Utils.greater( contacts , Comparator.comparingInt( c -> c.getTelephones().size() ) );
        Collection<Contact> greater = new TreeSet<>();
        name.forEach( (s) -> greater.add( contacts.get(s) ) );
        return greater;
    }

    /**
     * Obtem as datas que existem mais aniversários
     */
    public Collection<Date> greaterDates ( ) {
        return Utils.greater( birthdays , Comparator.comparingInt( Set::size ) );
    }

    /**
     * Produz um ficheiro com a junção dos contactos contidos nos ficheiros de texto de uma diretoria.
     * @param dir diretoria alvo.
     * @param filenameOut nome de ficheiro de destino.
     */
    public static void merge ( File dir , String filenameOut ) throws IOException {
        if ( !dir.isDirectory() )
            return;
        File[] files = dir.listFiles();
        NoteBook nb = new NoteBook();
        for ( File f : files ) {
            nb.read(f);
        }
        nb.write( new File( filenameOut ) );
    }
}
