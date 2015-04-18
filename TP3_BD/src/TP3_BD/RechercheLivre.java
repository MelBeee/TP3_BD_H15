package TP3_BD;

import javax.swing.*;

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
    private JButton BTN_Last;
    private JButton BTN_Next;
    private JPanel rootPanel;
    private JLabel LB_Titre;
    private JLabel LB_Auteur;
    private JLabel LB_Date;
    private JLabel LB_Edition;
    private JLabel LB_Genre;
    private JLabel LB_Numero;

    public RechercheLivre()
    {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RechercheLivre");
        frame.setContentPane(new RechercheLivre().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
