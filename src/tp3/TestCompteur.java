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

        //TODO FINIR
    }

    public static void main(String[] args) {
        for (int i = 0; i<50 ; i++){
            if (testCompteur()
        }

    }
}