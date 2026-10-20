package tp3;

public class TestCompteur {

    public static boolean testCompteur (int nbThreads, int nbIncrements) {
        CompteurDangereux.resetCompteur();
        int resultat = 0;

        Thread[] threads = new Thread[nbThreads];
        for (int i = 0; i < nbThreads; i++) {
            threads[i] = new Thread(new CompteurDangereux.Incrementeur("Thread-" + i, nbIncrements));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.print(e);
            }
        }

        resultat = CompteurDangereux.getCompteur();
        System.out.println(CompteurDangereux.getCompteur());

        if (resultat == nbIncrements*nbThreads){
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        int correct = 0;
        int faux = 0;
        final int NB_ESSAI = 50;
        int[] tabTest = new int[NB_ESSAI];

        for (int i = 0; i<NB_ESSAI ; i++){

            if(testCompteur(9 , 11111111)){
                correct ++ ;
            } else {
                faux ++;
            }
        }

        double pourErr = faux*NB_ESSAI/100 ;

        System.out.println("---------------");
        System.out.println("Correcte : " + correct + " Faux : " + faux);
        System.out.println("Pourcentage d'erreur " + pourErr + "%");
        //TODO valeur max et min
        //TODO Changez le nombre de threads (1, 2, 5, 10) - Impact sur la fréquence des race conditions ? : relation contention/erreur
        //TODO Changez le nombre d’incréments par thread - Plus d’opérations = plus de problèmes ? : volume/erreur
    }

    //Question clé : compteurGlobal n'est pas atomique puisque plusieurs thread peuvent le modifier ne meme temps.
}