package TP3_BD;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;

/**
 * Created by M�lissa on 2015-04-18.
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


    public void GenerateTableauEmprunt()
    {

    }
    public Emprunt()
    {
        GenerateTableauEmprunt();

        String User ="BoucherM";
        String Password ="ORACLE2";
        String url = "jdbc:oracle:thin:@205.237.244.251:1521:orcl";

        try
        {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            final Connection conn = DriverManager.getConnection(url,User,Password);

            try
            {
                SelectStm =  conn.createStatement(Resultset.TYPE_SCROLL_INSENSITIVE, Resultset.CONCUR_READ_ONLY);
                Resultset = SelectStm.executeQuery(sqlSel);

                while(Resultset.next())
                {

                }
            }
            catch(SQLException ioe)
            {

            }

        }catch(SQLException connEX)
        {
            System.out.println("Connexion Impossible");
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
