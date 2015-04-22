package TP3_BD;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import java.util.Date;

/**
 * Created by Mï¿½lissa on 2015-04-18.
 */
public class Emprunt {
    public JPanel RootEmprunt;
    private JList JL_LivreEmprunt;
    private JTextField TB_Adherant;
    private JTextField TB_NumLivre;
    private JComboBox CB_Exemplaire;
    private JButton BTN_Emprunter;
    private JButton BTN_Rechercher;
    private JTable table1;
    private JPanel PN_TopLivre;
    String sqlSel = " select l.titre, g.genre, e.dateemprunt, e.dateretour, ad.nom, ad.prenom " +
                    " from EMPRUNT e " +
                    " inner join adherent ad on ad.NUMADHERENT = e.NUMADHERENT " +
                    " inner join exemplaire ex on ex.NUMEXEMPLAIRE = e.NUMEXEMPLAIRE " +
                    " inner join livre l on l.NUMLIVRE = ex.NUMLIVRE " +
                    " inner join genre g on g.CODEGENRE = l.CODEGENRE ";

    Statement SelectStm = null;
    ResultSet Resultset = null;
    String User ="BoucherM";
    String Password ="ORACLE2";
    String url = "jdbc:oracle:thin:@205.237.244.251:1521:orcl";
    String numlivre = "";
    String numadherent = "";


    public Emprunt()
    {
        try
        {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            final Connection conn = DriverManager.getConnection(url, User, Password);

            try
            {
                SelectStm =  conn.createStatement(Resultset.TYPE_SCROLL_INSENSITIVE, Resultset.CONCUR_READ_ONLY);
                Resultset = SelectStm.executeQuery(sqlSel);

                while(Resultset.next())
                {
                    // add au tableau des livres empruntés
                }
            }
            catch(SQLException ioe)
            {

            }


            BTN_Emprunter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    EmprunterUnLivre(conn);
                }
            });

            BTN_Rechercher.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    VerifierLivre(conn);
                }
            });

        }catch(SQLException connEX)
        {
            System.out.println("Connexion Impossible");
        }
    }

    private void EmprunterUnLivre(Connection conn)
    {
        try
        {
            String SqlIns = "insert into emprunt values(" + CB_Exemplaire.getSelectedItem().toString() + "," + numadherent + ", '2015-01-02', '2015-01-02', 1)";
            Statement InsertStm = conn.createStatement();
            int n = InsertStm.executeUpdate(SqlIns);
        }
        catch(SQLException sqlInsertEx)
        {
            System.out.println(sqlInsertEx.getMessage());
        }
    }

    private void VerifierLivre(Connection conn)
    {
        CB_Exemplaire.removeAllItems();
        if(TB_Adherant.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(RootEmprunt,
                    "Veuillez entrer un numero d'adherent", "Attention !",
                    JOptionPane.WARNING_MESSAGE);
        }
        else if(TB_NumLivre.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(RootEmprunt,
                    "Veuillez entrer un numero de livre", "Attention !",
                    JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            numadherent = TB_Adherant.getText();
            numlivre = TB_NumLivre.getText();
            try
            {
                boolean resultat = false;
                SelectStm =  conn.createStatement();
                Resultset = SelectStm.executeQuery( " select exemplaire.numexemplaire " +
                                                    " from exemplaire " +
                                                    " left join emprunt on emprunt.numexemplaire = exemplaire.numexemplaire " +
                                                    " where exemplaire.numexemplaire not in " +
                                                    " (select numexemplaire from emprunt where deretour = '1') " +
                                                    " and numlivre = " + numlivre );

                while(Resultset.next())
                {
                    resultat = true;
                    CB_Exemplaire.addItem(Resultset.getInt(1));
                    BTN_Emprunter.setEnabled(true);
                }

                if(!resultat) {
                    JOptionPane.showMessageDialog(RootEmprunt,
                            "Aucun exemplaire disponible pour emprunt ou livre innexistant", "Attention !",
                            JOptionPane.WARNING_MESSAGE);
                }

            }catch(SQLException sqlInsertEx)
            {
                System.out.println("Erreur lors de la recherche d'un livre");
                System.out.println(sqlInsertEx.getErrorCode());
                System.out.println(sqlInsertEx.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Emprunt");
        frame.setContentPane(new Emprunt().RootEmprunt);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
