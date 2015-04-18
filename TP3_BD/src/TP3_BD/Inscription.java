package TP3_BD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import oracle.jdbc.OracleDriver;
import javax.swing.*;

/**
 * Created by Charlie on 2015-04-18.
 */
public class Inscription {
    private JButton supprimerButton;
    private JButton ajouterButton;
    private JTextField TB_Prenom;
    private JTextField TB_Adresse;
    private JTextField TB_Telephone;
    private JTextField TB_Nom;
    private JButton modifierButton;
    private JButton precedentButton;
    private JButton suivantButton;
    private JPanel RootInscription;


    public Inscription() {

        String User ="BoucherM";
        String Password ="ORACLE2";
        String url = "jdbc:oracle:thin:@205.237.244.251:1521:orcl";

        try
        {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            final Connection conn = DriverManager.getConnection(url,User,Password);


            ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AjouterPersonne(conn);
            }

        });

        }catch(SQLException connEX)
        {
            System.out.println("Connexion Impossible");
        }

    }

    private void AjouterPersonne(Connection conn)
    {
        try
        {
            String SqlIns = "insert into adherent values('"+TB_Nom.getText()+"','"+TB_Prenom.getText()+"','"+TB_Adresse.getText()+"','"+TB_Telephone.getText()+"')";
            Statement InsertStm = conn.createStatement();
            int n = InsertStm.executeUpdate(SqlIns);

            System.out.println("nb de lignes ajoutée" + n);



        }catch(SQLException sqlInsertEx)
        {

        }


    }


    public JButton getSupprimerButton() {
        return supprimerButton;
    }

    public void setSupprimerButton(JButton supprimerButton) {
        this.supprimerButton = supprimerButton;
    }

    public JButton getAjouterButton() {
        return ajouterButton;
    }

    public void setAjouterButton(JButton ajouterButton) {
        this.ajouterButton = ajouterButton;
    }

    public JTextField getTB_Prenom() {
        return TB_Prenom;
    }

    public void setTB_Prenom(JTextField TB_Prenom) {
        this.TB_Prenom = TB_Prenom;
    }

    public JTextField getTB_Adresse() {
        return TB_Adresse;
    }

    public void setTB_Adresse(JTextField TB_Adresse) {
        this.TB_Adresse = TB_Adresse;
    }

    public JTextField getTB_Telephone() {
        return TB_Telephone;
    }

    public void setTB_Telephone(JTextField TB_Telephone) {
        this.TB_Telephone = TB_Telephone;
    }

    public JTextField getTB_Nom() {
        return TB_Nom;
    }

    public void setTB_Nom(JTextField TB_Nom) {
        this.TB_Nom = TB_Nom;
    }

    public JButton getModifierButton() {
        return modifierButton;
    }

    public void setModifierButton(JButton modifierButton) {
        this.modifierButton = modifierButton;
    }

    public JButton getPrecedentButton() {
        return precedentButton;
    }

    public void setPrecedentButton(JButton precedentButton) {
        this.precedentButton = precedentButton;
    }

    public JButton getSuivantButton() {
        return suivantButton;
    }

    public void setSuivantButton(JButton suivantButton) {
        this.suivantButton = suivantButton;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Inscription");
        frame.setContentPane(new Inscription().RootInscription);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
