package TP3_BD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import oracle.jdbc.OracleDriver;
import java.sql.*;

/**
 * Created by Mélissa on 2015-04-18.
 */
public class RechercheLivre {
    private JTextField TB_Auteur;
    private JTextField TB_Titre;
    private JComboBox CB_Genre;
    private JButton BTN_RechercheGenre;
    private JButton BTN_RechercheAuteur;
    private JButton BTN_RechercheTitre;
    private JButton BTN_Previous;
    private JButton BTN_Next;
    private JPanel rootPanel;
    private JLabel LB_Titre;
    private JLabel LB_Auteur;
    private JLabel LB_Date;
    private JLabel LB_Edition;
    private JLabel LB_Genre;
    private JLabel LB_Numero;
    String sqlSel = "select livre.numlivre, livre.titre, livre.auteur, livre.dateparution, livre.maisonedition, genre from livre inner join genre on genre.codegenre = livre.codegenre order by livre.NumLivre";
    Statement SelectStm = null;
    ResultSet Resultset = null;
    String User ="BoucherM";
    String Password ="ORACLE2";
    String url = "jdbc:oracle:thin:@205.237.244.251:1521:orcl";
    int NumlivretSelect = 0;

    public RechercheLivre()
    {
        try
        {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            final Connection conn = DriverManager.getConnection(url,User,Password);
            SelectStm =  conn.createStatement(Resultset.TYPE_SCROLL_INSENSITIVE, Resultset.CONCUR_READ_ONLY);
            Resultset = SelectStm.executeQuery(sqlSel);
            SuivantLivre(conn);

            BTN_Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SuivantLivre(conn);
            }
        });

            BTN_Previous.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PrecedentLivre(conn);
                }
            });

            BTN_RechercheAuteur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RechercheAuteur(conn);
            }
        });


        }catch(SQLException SqlConn)
        {
            System.out.println("Connexion Impossible");
        }

    }

    private void RechercheAuteur(Connection conn)
    {


    }

    private void PrecedentLivre(Connection conn)
    {
        try
        {
            if(Resultset.previous())
            {
                NumlivretSelect = Resultset.getInt("NumLivre");
                LB_Auteur.setText(Resultset.getString("Auteur"));
                LB_Titre.setText(Resultset.getString("Titre"));
                LB_Date.setText(Resultset.getString("DateParution"));
                LB_Edition.setText(Resultset.getString("MaisonEdition"));
                LB_Genre.setText(Resultset.getString("Genre"));
                LB_Numero.setText(Resultset.getInt("NumLivre")+"");
            }
        }catch(SQLException sqlSuivantEx){
            System.out.println(sqlSuivantEx.getSQLState());

        }
    }
    private void SuivantLivre(Connection conn)
    {
        try
        {
            if(Resultset.next())
            {
                NumlivretSelect = Resultset.getInt("NumLivre");
                LB_Auteur.setText(Resultset.getString("Auteur"));
                LB_Titre.setText(Resultset.getString("Titre"));
                LB_Date.setText(Resultset.getString("DateParution"));
                LB_Edition.setText(Resultset.getString("MaisonEdition"));
                LB_Genre.setText(Resultset.getString("Genre"));
                LB_Numero.setText(Resultset.getInt("NumLivre")+"");
            }
        }catch(SQLException sqlSuivantEx){
            System.out.println(sqlSuivantEx.getSQLState());

        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RechercheLivre");
        frame.setContentPane(new RechercheLivre().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
