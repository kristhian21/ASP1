package edu.eci.arsw.GuidFinderDesktop;

import java.util.UUID;

public class GuidFinderThread extends Thread{

    private int a;
    private int b;
    public static int cont;
    private UUID guidToFind;

    public GuidFinderThread(int a, int b, UUID gtf) {
        this.a = a;
        this.b = b;
        this.guidToFind = gtf;
    }

    public synchronized void count(){
        cont++;
    }

    public void esperar(){
        try {
            guidToFind.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            UUID[] listaGuids = GuidFinder.getGuids();
            System.out.println("Hilo en ejecucion: " + a + " " + b);
            for (int i = a; i < b; i++) {
                if (listaGuids[i].equals(guidToFind)){
                    count();
                }
            }
            System.out.println("Termine :)");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
