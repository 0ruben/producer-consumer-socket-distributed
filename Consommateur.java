import java.net.*;
import java.io.*;
import java.util.*;

public class Consommateur extends Thread{

	String id;
	Connexion c;


	Consommateur( String id, String host, int port){
		c = new Connexion(host, port);
		this.id = id;
		System.out.println("Le consommateur "+id+" vient d'arriver dans la simulation");
		start();



	}



	public void run(){
		
		while(true){		
			consommer();
			try {
				sleep(5000 + (int)Math.random()*15000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
	}


	synchronized public void consommer(){
		String temp;
		c.ecriture(id+";demande de consommation");
		System.out.println(id+" demande à consommer");
		temp = c.lecture();
		if(!temp.equals("Interdiction de consommer")){

			System.out.println(id+ " consomme : "+temp);
		}else{
			System.out.println(id+ " ne peut pas consommer");
		}
	}


	//1er argument l'adresse du serveu
	//2eme argument le port auquel se connecter
	//3eme argument le nombre de Consommateur à créer

	public static void main(String[] args){
		for (int i=0; i<Integer.parseInt(args[2]);i++){
			new Consommateur("C"+Integer.toString(i+1) , args[0], Integer.parseInt(args[1]));
			try{sleep(5000);}
			catch(InterruptedException e){}
		}

	}

}