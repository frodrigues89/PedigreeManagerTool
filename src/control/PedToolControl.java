package control;

/**
 *
 * @author felipe
 */

import dao.DaoMysql;
import entity.ElementosTela;
import entity.LoginMySql;
import entity.PED;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import view.PedToolView;

public class PedToolControl{

    static PedToolView ptv;
    private LoginMySql login;
    List<PED> lBanco = new ArrayList<>();
    
    static ImageIcon iconCheck;
    static ImageIcon iconRemove;

 
    public PedToolControl(){
            this.login = new LoginMySql();
        icones();
    }
    
    
    /**
     * Metodo que busca a imagem dos icones no repositorio
     */
    private void icones(){
        iconRemove = new javax.swing.ImageIcon(getClass().getResource(""
                + "/ImageRepository/remove.png"));
        iconCheck = new javax.swing.ImageIcon(getClass().getResource(""
                + "/ImageRepository/check.png"));
    }
    
    
    /**Metodo que identifica o tipo de seleção e chama o metodo de controle correto.
     * @param tela
     * @return 
    */
    public List<PED> btnPesquisar(ElementosTela tela) throws Exception {
        String banco;
        if (tela.isRadioBae()){
            banco = "baependi";
        }else{
            banco = "bioinfo";
        }
        //System.out.println(banco);
        if (tela.isRadioTodosInd()){
            /*Pesquisa TODOS os individuos*/
            return pesquisaTodos(tela);
            
        }else{
            /*Pesquisa individuos específicos*/
            if (tela.isRadioEspecifico()){
                if (tela.isRadioTexto()){
                    //Pesquisa apenas os itens selecionados no tfTexto
                    System.out.println(tela.getTfPed());
                    List<PED> lPed = new ArrayList();
                    return pesquisaTexto(tela.getTfPed());
                }else{
                    //pesquisa os itens selecionados no arquivo do FileChooser
                    if (tela.isRadioArquivo()){
                        /*teste*/
                        System.out.println("arquivoRBtn");
                        
                        List<PED> lPed = new ArrayList();
                        return lPed;
                    }else{
                        String[] msg = {"Selecione um botão "
                                + "antes de pesquisar.","ERRO!"};
                        ptv.jOptionPaneErro(msg);
                    }
                }
            }
        }        
        return null;
    }

    
    /**
     * Metodo que pesquisa itens específicos no banco de dados
     * @param <List>
     * @param codigo
     * @return 
     */
    private List <PED> pesquisaTexto(String texto) throws Exception{
        DaoMysql dao = new DaoMysql(login);
        List<PED> lPed = new ArrayList();
        String [] vetText = separaTexto(texto);
        
        for(int i=0; i < vetText.length;i++){
            System.out.println(vetText[i]);
        }
        
        return dao.lerBanco(login, vetText) ;
    }
    
    
    /**
     * Metodo que separa o texto e retorna um vetor de String
     * @param texto
     * @return 
     */
    public String[] separaTexto(String texto) throws Exception{
        return texto.split(separador(texto));
    }
    
    
    /**
	 * Adquire o separador da linha se este for uma, e apenas uma, das opções:
	 * {" ", "\t", ";", ","}
	 * @param linha
	 * @return separador padrao
	 * @throws Exception
	 */
    public static String separador(String linha) throws Exception{

        String separador = null;
        int contador = 0;
        String[] possiveisSeparadores = new String []{" ", "\t", ";",","};

        for (int i = 0; i < possiveisSeparadores.length; i++) 
                if (linha.contains(possiveisSeparadores[i])) {
                        separador = possiveisSeparadores[i];
                        contador++;
                }
        //System.out.println("separador"+separador+ "separador");
        if(contador != 1)
                throw new Exception( ( contador > 1 ) ? "There is more than one "
                        + "separator in the file." : "Separator not found.");

        if(separador == null)
                throw new Exception("Separator not found in file.");

        return separador;
    }
    
    
    /**
     * Metodo que faz a pesquisa de todos os indivíduos no Banco.
     * @param tela 
     */
    private List<PED>  pesquisaTodos(ElementosTela tela) {
        String[] msg = {"Deseja realizar a pesquisa completa no Banco de Dados?",
            "Pesquisar"};
        DaoMysql dao = new DaoMysql(login);
            if(PedToolView.jOptionPaneConfirmar(msg)){
                try {
                    lBanco = dao.lerBanco(login,null);
                    return lBanco;
                } catch (SQLException ex) {
                    Logger.getLogger(PedToolView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            List<PED> lPed = new ArrayList();
            return lPed;

    }
   

    /**
     * Metodo que salva os itens da tabela.
     * @param lPed
     * @throws java.sql.SQLException
     */
    public void salvarBanco(List<PED> lPed) throws SQLException {
        DaoMysql dao = new DaoMysql(login);
        List<PED> lPedAux = new ArrayList();
        for (int i=0; i<lPed.size();i++){
            
            //sessão que verifica a necessidade de criar ficticios e 
            //  insere o número deste ficticio na lista.
            if (lPed.get(i).isFict() && lPed.get(i).getNumPai().equals("")){
                lPed.get(i).setNumPai(dao.geraFicticio(login,true,lPed.get(i).getNumFami()));
            }
            if (lPed.get(i).isFict() && lPed.get(i).getNumMae().equals("")){
                lPed.get(i).setNumMae(dao.geraFicticio(login,false,lPed.get(i).getNumFami()));
            }
            
            
            //Sessão que salva as alterações no Banco
            if (changed(lPed.get(i), lBanco.get(i))){
                System.out.println("gravar Banco");
                lPedAux.add(lPed.get(i));
            }else{
                System.out.println("não gravar Banco");
            }
        }
        
        dao.updateBanco(login, lPedAux);
        
    }

    
    /**
     * Metodo que compara a lista da tabela com a lista de controle 
     *      e verifica se houve mudança
     * @param ped
     * @param cont
     * @return 
     */
    public boolean changed(PED ped, PED cont){
        return !(ped.getNumPart().equals(cont.getNumPart()) && 
                ped.getNumFami().equals(cont.getNumFami()) &&
                ped.getSexo().equals(cont.getSexo())&&
                ped.getNumPai().equals(cont.getNumPai())&&
                ped.getNumMae().equals(cont.getNumMae()));
    }
    
    
    /**
     * Metodo para testar a conexão do banco de dados.
     * @param lblBanco
     */    
    public void testeLoginMySql(JLabel lblBanco){
        
        login.setUser("root");
        login.setPass("1234");
        
        DaoMysql dao = new DaoMysql(login);
        
        if (dao.testeConection(login)){
            lblBanco.setIcon(iconCheck);
            lblBanco.setText("Banco de dados Conectado!");
        }else{
            lblBanco.setIcon(iconRemove);
            lblBanco.setText("Banco de dados não conectado");
        }
    }
}
