package edu.eci.arsw.GuidFinderDesktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class GuidFinder {
	
	private static UUID[] guids;
	public static long tInicio;
	private ArrayList<GuidFinderThread> coleccionHilos = new ArrayList<>();
	
	
	public GuidFinder() throws Exception {
		getGuids();
	}
	
	public static UUID[] getGuids() throws Exception 
	{
	
		if(guids==null){
			System.out.println("es nulo");
			FileInputStream fi;
		
			fi = new FileInputStream(new File("guids.eci"));
		
			ObjectInputStream oi = new ObjectInputStream(fi);


			guids= (UUID[]) oi.readObject();
			System.out.println("Termino de cargar los UUIDS... size: " + guids.length);
			oi.close();
			fi.close();
		}
		return guids;
		
	}
	
	public int countGuids(UUID guidToFind) 
	{
		boolean actividad = false;
		for (int i = 0; i < 4; i++) {
			coleccionHilos.add(new GuidFinderThread((int) i * guids.length / 4, (int) (i+1) * guids.length / 4, guidToFind));
		}

		tInicio = System.currentTimeMillis();

		while (true){
			Scanner input = new Scanner(System.in);
			String validar = input.nextLine();
			long tActual = System.currentTimeMillis();
			if (validar.isEmpty()){
				if ((int) tActual - tInicio < 10){
					for (GuidFinderThread gTh:
							coleccionHilos) {
						gTh.start();
						gTh.esperar();
					}

					for (GuidFinderThread gTh:
							coleccionHilos) {
						try {
							gTh.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			else {
				tInicio = System.currentTimeMillis();
			}

		}

		return GuidFinderThread.cont;
		
	}


}
