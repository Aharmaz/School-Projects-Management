package ma.gi.pfa;

public class ChefFiliere {

    private String nomC;
    private String prenomC;
    private String mdpC;
    private String loginC;

    public ChefFiliere() {
    }

    public ChefFiliere(String nomC, String prenomC, String mdpC, String loginC) {
        this.nomC = nomC;
        this.prenomC = prenomC;
        this.mdpC = mdpC;
        this.loginC = loginC;
    }

    public String getNomC() {
        return nomC;
    }

    public void setNomC(String nomC) {
        this.nomC = nomC;
    }

    public String getPrenomC() {
        return prenomC;
    }

    public void setPrenomC(String prenomC) {
        this.prenomC = prenomC;
    }

    public String getMdpC() {
        return mdpC;
    }

    public void setMdpC(String mdpC) {
        this.mdpC = mdpC;
    }

    public String getLoginC() {
        return loginC;
    }

    public void setLoginC(String loginC) {
        this.loginC = loginC;
    }
}
