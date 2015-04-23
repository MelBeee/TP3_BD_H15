package TP3_BD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import oracle.jdbc.OracleDriver;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.sql.*;

/**
 * Created by M�lissa on 2015-04-18.
 */
public class RechercheLivre {
    private JTextField TB_Auteur;
    private JTextField TB_Titre;
    private JComboBox CB_Genre;
    private JButton BTN_RechercheAuteur;
    private JButton BTN_RechercheTitre;
    private JButton BTN_Previous;
    private JButton BTN_Next;
    public JPanel rootPanel;
    private JLabel LB_Titre;
    private JLabel LB_Auteur;
    private JLabel LB_Date;
    private JLabel LB_Edition;
    private JLabel LB_Genre;
    private JLabel LB_Numero;
    private JRadioButton inscriptionRadioButton;
    private JRadioButton empruntRadioButton;
    private JScrollBar scrollBar1;
    String sqlSel = "select livre.numlivre, livre.titre, livre.auteur, livre.dateparution, livre.maisonedition, genre from livre inner join genre on genre.codegenre = livre.codegenre";
    Statement SelectStm = null;
    ResultSet Resultset = null;
    String User ="BoucherM";
    String Password ="ORACLE2";
    String url = "jdbc:oracle:thin:@205.237.244.251:1521:orcl";
    int NumlivretSelect = 0;

    public RechercheLivre(final Connection conn)
    {
        try
        {
            CB_Genre.addItem("");
            CB_Genre.addItem("Sciences");
            CB_Genre.addItem("Informatique");
            CB_Genre.addItem("Divertissement");
            CB_Genre.addItem("Droit commercial");
            CB_Genre.addItem("Histoire");
            CB_Genre.addItem("Littérature");

            SelectStm =  conn.createStatement(Resultset.TYPE_SCROLL_INSENSITIVE, Resultset.CONCUR_READ_ONLY);
            Resultset = SelectStm.executeQuery(sqlSel + " order by livre.NumLivre");
            SuivantLivre();

            BTN_Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SuivantLivre();
            }
        });

            BTN_Previous.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    PrecedentLivre();
                }
            });

            BTN_RechercheAuteur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                RechercheAuteur();
            }
        });

            BTN_RechercheTitre.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    RechercheUnLivre();
                }
            });

            CB_Genre.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    RechercheGenre();
                }

            });

        }catch(SQLException SqlConn)
        {
            System.out.println(SqlConn.getMessage());
        }
    }

    private void RechercheGenre()
   {
       try
       {
           if(CB_Genre.getSelectedItem()!="")
           {
               TB_Auteur.setText("");
               TB_Titre.setText("");
               Resultset = SelectStm.executeQuery(sqlSel + " where Genre.Genre like '%" + CB_Genre.getSelectedItem().toString() + "%' order by livre.NumLivre");
               SuivantLivre();
           }
       }
       catch(SQLException ex)
       {

       }
   }
    private void RechercheUnLivre()
    {
        try
        {
            Resultset = SelectStm.executeQuery(sqlSel + " where livre.Titre like '%" + TB_Titre.getText() + "%' order by livre.NumLivre");
            CB_Genre.setSelectedItem("");
            TB_Auteur.setText("");
            SuivantLivre();

        }catch(SQLException ex)
        {

        }
    }
    private void RechercheAuteur()
    {
       try
       {
           Resultset = SelectStm.executeQuery(sqlSel + " where livre.Auteur like '%" + TB_Auteur.getText() + "%' order by livre.NumLivre");
           CB_Genre.setSelectedItem("");
           TB_Titre.setText("");
           SuivantLivre();

       }catch(SQLException ex)
       {

       }
    }

    private void PrecedentLivre()
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
    private void SuivantLivre()
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

    }
}
