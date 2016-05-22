import java.util.*;
import java.io.*;
import java.net.*;



public class Producteur extends Thread{

	String id;
	Connexion c;



	
	public Producteur(String id, String host, int port){
		this.id = id;
		c = new Connexion(host,port);
		System.out.println("*** Le producteur "+id+" vient d'arriver dans la simulation");
		start();
	}
	


	public void run(){


		while(true){


			produire();


			try{
				sleep(5000 + (int)Math.random()*10000);
			}catch(InterruptedException e){
				System.out.println("erreur lors du sleep");
			}
		}
	}

	synchronized public void produire(){


		if(demande_Autorisation_Production()){

			String s = id+";"+entreeClavier();
			c.ecriture(s);
			System.out.println(id+ " vient de produire : " + s.split(";",2)[1]);
		}
		else
			return;


	}

	public String entreeClavier(){

		System.out.println("Entrez un message à produire : ");

		String message;

		Scanner scanIn = new Scanner(System.in);
		message = scanIn.nextLine();

		scanIn.close();            
		return message;
	}


	public boolean demande_Autorisation_Production(){
		
		c.ecriture(id+";demande de production");
		System.out.println(id +" demande à produire un message");
		if(c.lecture().equals("autorisation OK"))

			return true;
		else{
			System.out.println(id+" ne peut pas produire");
			return false;
		}
	}


	//1er argument l'adresse du serveur
	//2eme argument le port auquel se connecter
	//3eme argument le nombre de Producteur à créer

	public static void main(String[] args){
		//Integer.parseInt(args[2]) nombre de producteurs à créer
		for (int i=0; i<Integer.parseInt(args[2]);i++){
			new Producteur("P"+Integer.toString(i+1) , args[0], Integer.parseInt(args[1]));

			try{sleep(5000);}
			catch(InterruptedException e){}
		}
	}   
}