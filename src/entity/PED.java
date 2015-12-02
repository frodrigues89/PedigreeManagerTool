
package entity;

/**
 *
 * @author Felipe
 */
public class PED {
    private String numPart;
    private String numFami;
    private String numPai;
    private String numMae;
    private String sexo;
    private boolean fict;
    private int cad_cod;

    public boolean isFict() {
        return fict;
    }

    public void setFict(boolean fict) {
        this.fict = fict;
    }

    public String getNumPart() {
        return numPart;
    }

    public void setNumPart(String numPart) {
        this.numPart = numPart;
    }

    public String getNumFami() {
        return numFami;
    }

    public void setNumFami(String numFami) {
        this.numFami = numFami;
    }

    public String getNumPai() {
        return numPai;
    }

    public void setNumPai(String numPai) {
        this.numPai = numPai;
    }

    public String getNumMae() {
        return numMae;
    }

    public void setNumMae(String numMae) {
        this.numMae = numMae;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getCad_cod() {
        return cad_cod;
    }

    public void setCad_cod(int cad_cod) {
        this.cad_cod = cad_cod;
    }
    
    
}
