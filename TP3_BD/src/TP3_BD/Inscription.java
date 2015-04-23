package TP3_BD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.sql.*;

import com.sun.deploy.panel.DeleteFilesDialog;
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
    public JPanel RootInscription;
    private JButton effacerButton;
    private JButton BTN_Quitter;
    int NumAdherentSelect =0;
    String sqlSel = "Select * from Adherent";
    Statement SelectStm = null;
    ResultSet Resultset = null;

    public Inscription(final Connection conn) {
        try
        {
            SelectStm =  conn.createStatement(Resultset.TYPE_SCROLL_INSENSITIVE, Resultset.CONCUR_READ_ONLY);
            Resultset = SelectStm.executeQuery(sqlSel);
            SuivantPersonne(conn);

            ajouterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AjouterPersonne(conn);
                    ajouterButton.setEnabled(false);
                    modifierButton.setEnabled(true);
                    supprimerButton.setEnabled(true);
                    suivantButton.setEnabled(true);
                    precedentButton.setEnabled(true);
                    effacerButton.setEnabled(true);
                    SuivantPersonne(conn);

                }
            });

            modifierButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ModifierPersonne(conn);
                }
            });
            supprimerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SupprimerPersonne(conn);
                }
            });
            suivantButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SuivantPersonne(conn);
                }
            });
            precedentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PrecedentPersonne(conn);
                }
            });
            BTN_Quitter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try
                    {
                        conn.close();
                        System.exit(1);
                    }
                    catch(SQLException connEX)
                    {

                    }
                }
            });

        }catch(SQLException connEX)
        {
            System.out.println("Connexion Impossible");

        }

        effacerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EffacerPersonne();
                ajouterButton.setEnabled(true);
                modifierButton.setEnabled(false);
                supprimerButton.setEnabled(false);
                suivantButton.setEnabled(false);
                precedentButton.setEnabled(false);
                effacerButton.setEnabled(false);
            }
        });


    }

    private void EffacerPersonne()
    {
        TB_Adresse.setText("");
        TB_Nom.setText("");
        TB_Prenom.setText("");
        TB_Telephone.setText("");
    }

    private void SuivantPersonne(Connection conn)
    {
        try
        {
           if(Resultset.next())
           {
               NumAdherentSelect = Resultset.getInt("NumAdherent");
               TB_Nom.setText(Resultset.getString("Nom"));
               TB_Prenom.setText(Resultset.getString("Prenom"));
               TB_Adresse.setText(Resultset.getString("Adresse"));
               TB_Telephone.setText(Resultset.getString("Telephone"));
           }
        }catch(SQLException sqlSuivantEx){
            System.out.println(sqlSuivantEx.getSQLState());
        }
    }

    private void PrecedentPersonne(Connection conn)
    {
        try
        {
            if(Resultset.previous())
            {
                NumAdherentSelect = Resultset.getInt("NumAdherent");
                TB_Nom.setText(Resultset.getString("Nom"));
                TB_Prenom.setText(Resultset.getString("Prenom"));
                TB_Adresse.setText(Resultset.getString("Adresse"));
                TB_Telephone.setText(Resultset.getString("Telephone"));
            }
        }catch(SQLException sqlSuivantEx){
            System.out.println(sqlSuivantEx.getSQLState());
        }
    }

   private void SupprimerPersonne(Connection conn)
   {
       try {
      String SqlDel = "Delete from Adherent where NumAdherent ="+NumAdherentSelect;
      Statement DeleteStm = conn.createStatement();
           int n = DeleteStm.executeUpdate(SqlDel);
       conn.commit();
       EffacerPersonne();
       System.out.println("nb de lignes supprimer  " + n);
       Resultset = SelectStm.executeQuery(sqlSel);
       SuivantPersonne(conn);
       }catch(SQLException sqlUpdateEx){
           JOptionPane.showMessageDialog(RootInscription,
                   "Impossible de supprimer un usager qui a emprunter des livres", "Attention !",
                   JOptionPane.WARNING_MESSAGE);
       }
   }

    private void ModifierPersonne(Connection conn)
   {
       if(!TB_Adresse.getText().isEmpty() && !TB_Nom.getText().isEmpty() && !TB_Prenom.getText().isEmpty() && !TB_Telephone.getText().isEmpty()) {
           try {
               String SqlUpd = "Update Adherent set Nom ='" + TB_Nom.getText() + "', Prenom = '" + TB_Prenom.getText() + "', Adresse ='" + TB_Adresse.getText() + "', Telephone ='" + TB_Telephone.getText() + "' where NumAdherent =" + NumAdherentSelect;
               Statement UpdateStm = conn.createStatement();
               int n = UpdateStm.executeUpdate(SqlUpd);
               conn.commit();
               EffacerPersonne();
               System.out.println("nb de lignes modifier " + n);
               Resultset = SelectStm.executeQuery(sqlSel);
               SuivantPersonne(conn);
           } catch (SQLException sqlUpdateEx) {
               System.out.println(sqlUpdateEx.getSQLState());
           }
       }
       else
       {
           JOptionPane.showMessageDialog(RootInscription,
                   "Information obligatoire", "Attention !",
                   JOptionPane.WARNING_MESSAGE);
       }
   }

    private void AjouterPersonne(Connection conn)
    {
        if(!TB_Adresse.getText().isEmpty() && !TB_Nom.getText().isEmpty() && !TB_Prenom.getText().isEmpty() && !TB_Telephone.getText().isEmpty()) {
            try {
                String SqlIns = "insert into adherent(nom,prenom,adresse,telephone) values('" + TB_Nom.getText() + "','" + TB_Prenom.getText() + "','" + TB_Adresse.getText() + "','" + TB_Telephone.getText() + "')";
                Statement InsertStm = conn.createStatement();
                int n = InsertStm.executeUpdate(SqlIns);
                conn.commit();
                EffacerPersonne();
                System.out.println("nb de lignes ajoute " + n);
                Resultset = SelectStm.executeQuery(sqlSel);
                SuivantPersonne(conn);
            } catch (SQLException sqlInsertEx) {
                System.out.println(sqlInsertEx.getSQLState());
            }
        }
        else
        {
            JOptionPane.showMessageDialog(RootInscription,
                    "Information obligatoire", "Attention !",
                    JOptionPane.WARNING_MESSAGE);
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
        try
        {
            String User ="BoucherM";
            String Password ="ORACLE2";
            String url = "jdbc:oracle:thin:@205.237.244.251:1521:orcl";
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            final Connection conn = DriverManager.getConnection(url,User,Password);

            JFrame frame = new JFrame("RechercheLivre");
            frame.setContentPane(new RechercheLivre(conn).rootPanel);
            // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


            JFrame frame2 = new JFrame("Emprunt");
            frame2.setContentPane(new Emprunt(conn).RootEmprunt);
            // frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame2.pack();
            frame2.setVisible(true);


            JFrame frame3 = new JFrame("Inscription");
            frame3.setContentPane(new Inscription(conn).RootInscription);
            // frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame3.pack();
            frame3.setVisible(true);
        }
        catch(SQLException ex)
        {

        }
    }
}
