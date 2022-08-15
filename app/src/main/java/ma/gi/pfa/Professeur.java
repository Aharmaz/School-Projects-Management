package ma.gi.pfa;

public class Professeur {
    private String nomP;
    private String prenomP;
    private String mdpP;
    private String loginP;

    public  Professeur() {
    }

    public  Professeur(String nomP, String prenomP, String mdpP, String loginP) {
        this.nomP= nomP;
        this.prenomP = prenomP;
        this.mdpP = mdpP;
        this.loginP = loginP;
    }

    public String getNomP() {
        return nomP;
    }

    public void setNomP(String nomP) {
        this.nomP = nomP;
    }

    public String getPrenomP() {
        return prenomP;
    }

    public void setPrenomP(String prenomP) {
        this.prenomP = prenomP;
    }

    public String getMdpP() {
        return mdpP;
    }

    public void setMdpP(String mdpP) {
        this.mdpP = mdpP;
    }

    public String getLoginP() {
        return loginP;
    }

    public void setLoginP(String loginP) {
        this.loginP = loginP;
    }
}
