package tp3;
import tp3.Calcule;

public class CalculParallele {
    private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int MULTIPLICATEUR = 1000;
    private static Calcule[] threads = new Calcule[DONNEES.length];

    public static void main(String[] args) {
        int total = 0;
        //Création des threads
        for (int i = 0; i < DONNEES.length; i++) {
            threads[i]= new Calcule(DONNEES[i],MULTIPLICATEUR,i);
        }

        //Start tout les threads pour un calcule parallele (on peut aussi juste mettre le start dans la boucle d'avant)
        for (int i = 0 ; i<threads.length;i++){
            threads[i].start();
        }

        //Une fois que tout les threads sont start on les join et on Additionne le resultat.
        for (int i = 0 ; i<threads.length;i++){
            try {
                threads[i].join(); //Opération blocante et passive qui attend chaque résultat
            } catch (InterruptedException e) {
                System.out.print(e);
            }

            total += threads[i].getResultat(); //Réduction
        }

        System.out.println(total);
    }
}
