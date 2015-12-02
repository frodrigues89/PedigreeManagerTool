
package dao;

import java.sql.Statement;
import entity.LoginMySql;
import entity.PED;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felipe
 */
public class DaoMysql{
    private Connection connection;
    private int ultimoFicticio = 0; 
    //
    
    public DaoMysql(LoginMySql login){
    
        try {
            connect(login);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Não conectado");
        }
    }
    
    /**
     *Metodo para conectar
     * @param login
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void connect(LoginMySql login) 
            throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://"
                + "localhost/testeDB",login.getUser(),login.getPass());
        //System.out.println("Conectado com sucesso");
    }

    
    /**
     *Metodo para desconectar
     */
    public void disconnect(){
        
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo que testa a conexão
     * @param login
     * @return 
     */
    public boolean testeConection(LoginMySql login){
        
        Connection connect;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/"
                    + "control_art",login.getUser(),login.getPass());
            //System.out.println("Conect OK");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Problema ao conectar");
            Logger.getLogger(DaoMysql.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        } 
    }

    /**
     * Metodo que lê o banco e retorna uma Lista
     * @param login
     * @param condi
     * @return
     * @throws SQLException 
     */
    public List<PED> lerBanco(LoginMySql login, String[] condi) throws SQLException{
        List<PED> lPed = new ArrayList();
        PreparedStatement pstm;
        DaoMysql bd = new DaoMysql(login);
        ResultSet rs;
        String consultaInd;
        if (condi == null){
            consultaInd = "SELECT * FROM `PED_TESTE` WHERE 1";
            pstm = connection.prepareStatement(consultaInd);
        }else{
            consultaInd = "SELECT * FOR 'PED_TESTE' WHERE PED_NUM_PART = ? ";
           
            for (int i = 1; i < condi.length; i++){
                System.out.println(condi[i]);
                consultaInd += "OR `PED_NUM_PART` = ? ";
            }
            System.out.println(condi.length);
            pstm = connection.prepareStatement(consultaInd);
            for (int i = 0; i < condi.length; i++){
                int j = i+1;
                pstm.setString(j, condi[i]);
                System.out.println(condi.length + " - " + i);
            }
            pstm = connection.prepareStatement(consultaInd);
            System.out.println(consultaInd);
            
            
        }
        try{
            
            //pstm = bd.connect(PedToolView.login).prepareStatement(consultaInd);
            //pstm.setInt(1,1);
            rs = pstm.executeQuery(consultaInd);
            
            while (rs.next()){
                PED ped = new PED();
                ped.setCad_cod(rs.getInt("id"));
                ped.setNumPart(rs.getString("PED_NUM_PART"));
                ped.setNumFami(rs.getString("PED_NUM_FAMI"));
                ped.setSexo(rs.getString("PED_SEXO"));
                ped.setNumPai(rs.getString("PED_NUM_PAI"));
                ped.setNumMae(rs.getString("PED_NUM_MAE"));
                
                lPed.add(ped);
            }
        }catch(SQLException ex){
            System.out.println("Problema ao ler o banco");
            Logger.getLogger(DaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        return lPed;
    }
    
    /**
     * Metodo que executa a query de UPDATE no Banco.
     * @param login
     * @param lPed
     * @throws SQLException 
     */
    public void updateBanco(LoginMySql login, List<PED> lPed) 
            throws SQLException{
        DaoMysql bd = new DaoMysql(login);
        PreparedStatement pstm;
        pstm = connection.prepareStatement("UPDATE `PED_TESTE` SET "
                + "`PED_NUM_FAMI`=?,`PED_NUM_PART`=?,`PED_SEXO`=?,"
                + "`PED_NUM_PAI`=?,`PED_NUM_MAE`=? WHERE `id` = ?");
        
        
        for (int i = 0; i<lPed.size();i++){
            pstm.setString(1, lPed.get(i).getNumFami());
            pstm.setString(2, lPed.get(i).getNumPart());
            pstm.setString(3, lPed.get(i).getSexo());
            pstm.setString(4, lPed.get(i).getNumPai());
            pstm.setString(5, lPed.get(i).getNumMae());
            pstm.setString(6, Integer.toString(lPed.get(i).getCad_cod()));
            pstm.executeUpdate();
        }
        
    }

    /**
     * Metodo que gera os indivíduos fictícios no Banco e retorna seu ID.
     * @param login
     * @param sexo
     * @param fami
     * @return
     * @throws SQLException 
     */
    public String geraFicticio(LoginMySql login, boolean sexo, String fami) 
            throws SQLException{
        DaoMysql bd = new DaoMysql(login);
        
        ultimoFicticio = ultimofic(login);
        
        PreparedStatement pstm;
        String query = "INSERT INTO `FICTICIOS`(`idFICTICIOS`,`FIC_FAMI`,"
                + " `FIC_SEX`) VALUES (?,?,?)";
        pstm = connection.prepareStatement(query);
        
        if (sexo){
            pstm.setString(1, Integer.toString(ultimoFicticio+1));
            pstm.setString(2, fami);
            pstm.setString(3, "1");
            System.out.println("man");
            pstm.executeUpdate();
        }else{
            pstm.setString(1, Integer.toString(ultimoFicticio+1));
            pstm.setString(2, fami);
            pstm.setString(3, "2");
            System.out.println("woman");
            pstm.executeUpdate();
        }
        System.out.println(ultimofic(login));
        return Integer.toString(ultimofic(login));
    }
    
    
    /**
     * Modulo que busca descobre quem é o ultimo ficticio
     * @param login
     * @return
     * @throws SQLException 
     */
    private int ultimofic(LoginMySql login) throws SQLException {
        DaoMysql bd = new DaoMysql(login);
        PreparedStatement pstm;
        String query = "SELECT idFICTICIOS " +
                            "FROM FICTICIOS " +
                            "ORDER BY idFICTICIOS DESC";
        pstm = connection.prepareStatement(query);
        ResultSet rs;
        rs = pstm.executeQuery(query);
        rs.next();
        return rs.getInt(1);
    }
}
