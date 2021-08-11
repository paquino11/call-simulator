package trab2.grupo1;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;

public class Window extends JFrame {
    public Window ( String directory , int x , int y , int width , int height ) {
        super( "Team" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( x , y , width , height );

        Container contentPane = getContentPane();
        contentPane.setLayout( new BorderLayout() );

        JPanel panelNorth = new JPanel( new BorderLayout() );
        contentPane.add(panelNorth, BorderLayout.NORTH );
        JPanel panelCenter = new JPanel( new BorderLayout() );
        contentPane.add(panelCenter, BorderLayout.CENTER );
        JPanel panelSouth = new JPanel( new BorderLayout() );
        contentPane.add(panelSouth, BorderLayout.SOUTH );

        JTextField path = new JTextField();
        panelNorth.add( path , BorderLayout.CENTER );
        JTextField team = new JTextField(4);
        panelNorth.add( team , BorderLayout.EAST );

        JTextArea result = new JTextArea();
        panelCenter.add( result , BorderLayout.CENTER );

        JTextArea info = new JTextArea();
        panelSouth.add( info , BorderLayout.CENTER );
        JButton list = new JButton( "list" );
        panelSouth.add(list, BorderLayout.EAST );

        path.setBorder( new TitledBorder( "pathname" ) );
        team.setBorder( new TitledBorder( "turma" ) );
        result.setBorder( new TitledBorder( "\"nome da turma\"" ) );
        info.setBorder( new TitledBorder( "informação" ) );

        list.addActionListener( (e) -> {
            String target = directory + "/" + path.getText();
            try {
                StringBuffer sb = new StringBuffer();
                String teamId = team.getText();
                int n = Team.copyTeam( target , teamId , (i,s) -> sb.append(i).append('\t').append(s).append('\n') );
                if ( teamId.length() == 0 ) {
                    info.setText( "Introduza uma turma" );
                    return;
                }
                result.setText( sb.toString() );
                result.setBorder( new TitledBorder( teamId ) );
                info.setText( String.format( "A turma %s tem %d alunos" , teamId , n ) );
            } catch ( IOException exception ) {
                info.setText( "Nome de ficheiro inválido" );
            }
        });
    }
}
