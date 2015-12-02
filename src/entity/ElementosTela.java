package entity;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author felipe
 */
public class ElementosTela {
    public JButton btnPesquisar;
    public JButton btnFileChooser;
    public JButton btnSalvar;
    public JButton btnLimpar;
    public JButton btnSair;
    
    public JLabel lblBanco;
    public JTable tabela;
    
    
    private String tfPed;
    
    private boolean radioTodosInd;
    private boolean radioEspecifico;
    private boolean radioTexto;
    private boolean radioArquivo;
    private boolean radioBae;
    private boolean radioBioinfo;

    public boolean isRadioTodosInd() {
        return radioTodosInd;
    }

    public void setRadioTodosInd(boolean radioTodosInd) {
        this.radioTodosInd = radioTodosInd;
    }

    public boolean isRadioEspecifico() {
        return radioEspecifico;
    }

    public void setRadioEspecifico(boolean radioEspecifico) {
        this.radioEspecifico = radioEspecifico;
    }

    public boolean isRadioTexto() {
        return radioTexto;
    }

    public void setRadioTexto(boolean radioTexto) {
        this.radioTexto = radioTexto;
    }

    public boolean isRadioArquivo() {
        return radioArquivo;
    }

    public void setRadioArquivo(boolean radioArquivo) {
        this.radioArquivo = radioArquivo;
    }

    public boolean isRadioBae() {
        return radioBae;
    }

    public void setRadioBae(boolean radioBae) {
        this.radioBae = radioBae;
    }

    public boolean isRadioBioinfo() {
        return radioBioinfo;
    }

    public void setRadioBioinfo(boolean radioBioinfo) {
        this.radioBioinfo = radioBioinfo;
    }

    public JLabel getLblBanco() {
        return lblBanco;
    }

    public void setLblBanco(JLabel lblBanco) {
        this.lblBanco = lblBanco;
    }

    public JButton getBtnPesquisar() {
        return btnPesquisar;
    }

    public void setBtnPesquisar(JButton btnPesquisar) {
        this.btnPesquisar = btnPesquisar;
    }

    public JButton getBtnFileChooser() {
        return btnFileChooser;
    }

    public void setBtnFileChooser(JButton btnFileChooser) {
        this.btnFileChooser = btnFileChooser;
    }

    public JButton getBtnSalvar() {
        return btnSalvar;
    }

    public void setBtnSalvar(JButton btnSalvar) {
        this.btnSalvar = btnSalvar;
    }

    public JButton getBtnLimpar() {
        return btnLimpar;
    }

    public void setBtnLimpar(JButton btnLimpar) {
        this.btnLimpar = btnLimpar;
    }

    public JButton getBtnSair() {
        return btnSair;
    }

    public void setBtnSair(JButton btnSair) {
        this.btnSair = btnSair;
    }

    public String getTfPed() {
        return tfPed;
    }

    public void setTfPed(String tfPed) {
        this.tfPed = tfPed;
    }

    public JTable getTabela() {
        return tabela;
    }

    public void setTabela(JTable tabela) {
        this.tabela = tabela;
    }
    
    
    
}
