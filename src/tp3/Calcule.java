package tp3;

public class Calcule extends Thread {

    private int valeurTh;
    private int multiplicateurTh;
    private  int indexTh;
    private int resultat = 0;

    public Calcule(int valeur , int multiplicateur , int index){
        this.valeurTh = valeur;
        this.multiplicateurTh = multiplicateur;
        this.indexTh = index;
    }

    @Override
    //Execute le calcule voulue.
    public void run() {
        resultat = 0;
        for (int j = 0; j < this.multiplicateurTh; j++) {
            resultat += this.valeurTh * this.valeurTh + this.valeurTh;
        }
    }
    //Renvoie le resultat du calcule du thread.
    public int getResultat(){
        return resultat;
    }
}