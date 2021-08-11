package trab2.noteBook;

import trab2.noteBook.gui.ContactDialog;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * Interface gráfica para visualizar e modificar a agenda.
 */
public class NoteBookFrame extends JFrame {
    public static final String RESOURCE_PATH = "./rsc/trab2/noteBook";

    private final JFileChooser fileChooser = new JFileChooser();
    private final NoteBook noteBook;
    private final ContactDialog contactDialog = new ContactDialog( this , this::addContact );
    private final JTextArea listArea = new JTextArea( 15, 40 );

    public static class ItemsMenu extends AbstractMap.SimpleEntry< String , ActionListener > {
        public ItemsMenu ( String s , ActionListener l ) {
            super( s , l );
        }
    }

    public ItemsMenu[] fileMenus = {
            new ItemsMenu( "load" , this::load ),
            new ItemsMenu( "save" , this::save ),
            new ItemsMenu( "exit" , this::exit )
    };

    public ItemsMenu[] editMenus = {
            new ItemsMenu( "add contact" , this::addContact ),
            new ItemsMenu( "add phone" , this::addPhone ),
            new ItemsMenu( "delete contact" , this::removeContact )
    };

    public ItemsMenu[] listMenus = {
            new ItemsMenu( "all contacts" , this::listAll ),
            new ItemsMenu( "names with this phone" , this::listNamesWithThisPhone ),
            new ItemsMenu( "today birthdays" , this::listTodayBirthdays ),
            new ItemsMenu( "this month birthdays" , this::listMonthBirthdays )
    };

    public ItemsMenu[] withMoreMenus = {
            new ItemsMenu( "contacts with more phones" , this::contactsWithMorePhone ),
            new ItemsMenu( "phones with more contacts" , this::phonesWithMoreContacts ),
            new ItemsMenu( "dates with more birthdays" , this::datesWithMoreBirthdays )
    };

    public NoteBookFrame ( NoteBook noteBook ) {

        super( "NoteBook" );
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

        this.noteBook = noteBook == null ? new NoteBook() : noteBook;

        // Adicionar a TextArea para a listagem, com barra de scroll
        listArea.setBorder( new TitledBorder("list") );
        JScrollPane sp = new JScrollPane( listArea );
        sp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        add( sp );

        // Adicionar o butão para adicionar contacto
        JPanel buttons = new JPanel();
        ((FlowLayout) buttons.getLayout()).setAlignment( FlowLayout.RIGHT );
        JButton b = new JButton( "add contact" );
        b.addActionListener( this::addContact );
        buttons.add( b );
        b = new JButton( "add phone" );
        b.addActionListener( this::addPhone );
        buttons.add( b );
        add( buttons , BorderLayout.SOUTH );

        // Adicionar os menus
        JMenuBar menuBar = new JMenuBar();
        menuBar.add( createJMenu( "File" , fileMenus ) );
        setJMenuBar( menuBar );
        menuBar.add( createJMenu( "Edit" , editMenus ) );
        setJMenuBar( menuBar );
        menuBar.add( createJMenu( "List" , listMenus ) );
        setJMenuBar( menuBar );
        menuBar.add( createJMenu( "With More" , withMoreMenus ) );
        setJMenuBar( menuBar );

        pack();
    }

    /**
     * Instancia um menu e adiciona os itens descritos no array de itens
     * @param name nome do menu
     * @param items array contendo a descrição dos itens (nome e ação a efetuar).
     * @return o menu instanciado
     */
    protected static JMenu createJMenu ( String name , ItemsMenu[] items ) {
        JMenu menu = new JMenu( name );
        for ( ItemsMenu i : items ) {
            JMenuItem mi = new JMenuItem( i.getKey() );
            mi.addActionListener( i.getValue() );
            menu.add( mi );
        }
        return menu;
    }

    /**
     * Método chamado quando é premido o botão "add contact".
     * Coloca visivel uma janela de dialogo correspondente à inserção dos dados
     * do contacto, quando é premido o botão submit é chamada o método apply
     * do Consumer passado por parâmetro no construtor.
     * @param actionEvent evento do action listener.
     */
    private void addContact ( ActionEvent actionEvent ) {
        contactDialog.setValue( null );
        contactDialog.setVisible( true );
    }

    /**
     * Método chamado quando é premido o botão "submit" da janela de diálogo
     * para introdução dos dados do contacto.
     * Adiciona o contacto na agenda e lista os contactos.
     * @param c contacto a adicionar
     */
    private void addContact ( Contact c ) {
        if ( noteBook.add( c ) )
            list( "Contact List" , noteBook.getAllContacts() , Contact::toString );
    }
    /**
     * Método chamado quando é premido o botão "add phone".
     * Obtém o nome do contacto e caso exista um contacto com o nome, atualiza e
     * coloca visivel uma janela de dialogo correspondente à inserção dos dados.
     * @param actionEvent evento do action listener.
     */
    private void addPhone ( ActionEvent actionEvent ) {
        String name = JOptionPane.showInputDialog( this , "Contact Name" , "Add phone" , JOptionPane.QUESTION_MESSAGE );
        if ( name != null ) {
            Contact c = noteBook.getContact( name );
            if ( c != null ) {
                contactDialog.setValue( c );
                contactDialog.setVisible( true );
            }
            else
                JOptionPane.showMessageDialog( this , "Contact not exist" , "Add phone" , JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Remov da agenda o contacto com .
     * Obtém o nome do contacto a remover e remove-o da agenda.
     * @param actionEvent evento do action listener.
     */
    private void removeContact ( ActionEvent actionEvent ) {
        String name = JOptionPane.showInputDialog( this , "Contact Name" , "Delete" , JOptionPane.QUESTION_MESSAGE );
        if ( name != null ) {
            if ( !noteBook.remove( name ) ) {
                JOptionPane.showMessageDialog( this , "Contact not exist" , "Delete" , JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    /**
     * Limpa a area de texto e lista uma sequencia de contactos, um por linha.
     * @param title titulo a colocar na cercadura da area de texto
     * @param seq sequência de Elementos.
     * @param toList Função para obter o valor a listar
     * @param <E> Tipo do contacto a listar
     * @param <V> Tipo do retorno da função para obter o valor a listar
     */
    private < E , V > void list ( String title , Iterable<E> seq , Function< E , V > toList ) {
       ((TitledBorder) listArea.getBorder()).setTitle( title );
       listArea.setText( "" );
       if ( seq == null )
           listArea.append( "Not exist contacts \n" );
       else for( E e : seq )
           listArea.append( toList.apply( e ) + "\n" );
    }

    /**
     * Método chamado quando é selecionado o item "exit".
     * Coloca visível uma janela de confirmação de saída com opção de gravar o resultado num ficheiro.
     * @param actionEvent ação a realizar
     */
    private void exit ( ActionEvent actionEvent ) {
        int res = JOptionPane.showConfirmDialog( this , "Save notebook" , "save" , JOptionPane.YES_NO_CANCEL_OPTION );
        if ( res != JOptionPane.CANCEL_OPTION ) {
            if ( res == JOptionPane.YES_OPTION )
                save( actionEvent );
            System.exit(0);
        }
    }

    /**
     * Método chamado quando é selecionado o item "save".
     * Colova visível uma janela de explorador de ficheiros para gravar o resultado.
     * @param actionEvent ação a realizar
     */
    private void save ( ActionEvent actionEvent ) {
        fileChooser.setCurrentDirectory( new File( RESOURCE_PATH ) );
        if ( JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog( this ) ) {
            try {
                noteBook.write( fileChooser.getSelectedFile() );
            } catch ( IOException e ) {
                JOptionPane.showMessageDialog( this , "Error file: " + e.getMessage() );
            }
        }
    }

    /**
     * Método chamado quando é selecionado o item "save".
     * Colova visível uma janela de explorador de ficheiros para abrir um ficheiro com dados.
     * @param actionEvent ação a realizar
     */
    private void load ( ActionEvent actionEvent ) {
        fileChooser.setCurrentDirectory( new File( RESOURCE_PATH ) );
        if ( JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog( this ) ) {
            noteBook.clear();
            try {
                noteBook.read( fileChooser.getSelectedFile() );
            } catch ( IOException e ) {
                JOptionPane.showMessageDialog( this , "Error file: " + e.getMessage() );
            }
        }
        listAll( actionEvent );
    }

    /**
     * Método chamado quando é selecionado o item "all contacts".
     * Lista todos os contactos.
     * @param actionEvent ação a realizar
     */
    private void listAll ( ActionEvent actionEvent ) {
        list( "Contact List" , noteBook.getAllContacts() , Contact::toString );
    }

    /**
     * Método chamado quando é selecionado o item "month birthdays".
     * Coloca visivel uma janela de dialogo para a inserção do mês.
     * Após ter a idade lista os contactos com a idade pretendida.
     * @param actionEvent ação a realizar
     */
    private void listMonthBirthdays ( ActionEvent actionEvent ) {
        String month = JOptionPane.showInputDialog( this , "Month" , "Insert Month" , JOptionPane.QUESTION_MESSAGE );
        list( "Birthday (month): " + month , noteBook.getBirthdays( Integer.parseInt( month ) ) , Contact::toString );
    }

    /**
     * Método chamado quando é selecionado o item "names with this phone".
     * Coloca visivel uma janela de dialogo para a inserção do número de telefone.
     * Após ter o nome lista os nomes que contêm este número de telefone.
     * @param actionEvent ação a realizar
     */
    private void listNamesWithThisPhone ( ActionEvent actionEvent ) {
        String phone = JOptionPane.showInputDialog( this , "Phone" , "Insert phone" , JOptionPane.QUESTION_MESSAGE );
        list( "Telephone: " , noteBook.getContactsOf( phone ) , Contact::toString );
    }

    /**
     * Método chamado quando é selecionado o item "today birthdays".
     * @param actionEvent ação a realizar
     */
    private void listTodayBirthdays ( ActionEvent actionEvent ) {
        String month = JOptionPane.showInputDialog( this , "Month" , "Insert month" , JOptionPane.QUESTION_MESSAGE );
        String day = JOptionPane.showInputDialog( this , "Day" , "Insert day" , JOptionPane.QUESTION_MESSAGE );
        list(
                "Birthday (day/month): " + day + "/" + month,
                noteBook.getBirthdays( Integer.parseInt( day ), Integer.parseInt( month ) ),
                Contact::toString
        );
    }

    /**
     * Método chamado quando é selecionado o item "contacts with more phones".
     * @param actionEvent ação a realizar
     */
    private void contactsWithMorePhone ( ActionEvent actionEvent ) {
        list( "Contacts with more telephones" , noteBook.greaterTelephones() , Contact::toString );
    }

    /**
     * Método chamado quando é selecionado o item "phones with more contacts".
     * @param actionEvent ação a realizar
     */
    private void phonesWithMoreContacts ( ActionEvent actionEvent ) {
        list( "Telephones with more contacts" , noteBook.greaterContacts() , String::toString );
    }

    /**
     * Método chamado quando é selecionado o item "dates with more birthdates".
     * @param actionEvent ação a realizar
     */
    private void datesWithMoreBirthdays ( ActionEvent actionEvent ) {
        list( "Dates with more birthdays" , noteBook.greaterDates() , Date::toString );
    }

    public static void main ( String[] args ) {
        new NoteBookFrame(null).setVisible( true );
    }
}
