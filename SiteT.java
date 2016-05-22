import java.util.*;
import java.io.*;
import java.net.*;

public class SiteT extends Thread{

	static ServerSocket socketEcoute;
	static int port;
	static BufferCirculaire buffer;
	static ArrayList clients = new ArrayList();

	public SiteT(int n, int port){
		buffer=new BufferCirculaire(n);
		this.port = port;
		try{
			socketEcoute = new ServerSocket(port);
			System.out.println("Bienvenue ! \n Le serveur a bien été ouvert au port " +port+ "\n La taille du buffer est de "+buffer.tailleBuffer);
			System.out.println(buffer.toString());
		}catch(Exception e){
			System.out.println("Erreur lors de la creation du ServerSocket");
		}
		start();

	}

	public void run(){

		while(true){
			try{
				Service s = new Service(socketEcoute.accept());
			}catch(IOException e){
				System.out.println("Erreur lors de la connexion du client");
			}
		}
	}

	public static void main (String[]args){
		
		if(args.length>=2){
			//le premier argument détermine la taille du buffer
			//le deuxième argument détermine le port sur lequel ouvrir le serveur
			SiteT serveur = new SiteT(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		}else{
			SiteT serveur = new SiteT(5, 4505);
		}

		
	}



	public class Service extends Thread{


		Connexion c;
		String message;

		public Service(Socket s){
			c = new Connexion(s);
			start();
		}

		public void run(){
			while(true){
				Sur_reception_de();
			}
		}



		synchronized public void Sur_reception_de(){
			String[] messageAvecId = c.lecture().split(";",2);
			String id = messageAvecId[0];
			String message = messageAvecId[1];
			String messageConsomme;



			
			//Partie réservée au producteur
			if(message.equals("demande de production")){

				if(!buffer.est_Rempli()){
					c.ecriture("autorisation OK");
					System.out.println("J'autorise "+id+" à produire");
				}else{
					c.ecriture("interdiction de produire");
					System.out.println("J'interdis "+id+" de produire");
				}
			}else{
				//Partie réservée au consommateur
				if(message.equals("demande de consommation")){

					if(!buffer.est_Vide()){
						messageConsomme = buffer.retirer();
						System.out.println(id+" a consommé "+messageConsomme);
						
						c.ecriture(messageConsomme);
						System.out.println(buffer.toString());

					}else{
						c.ecriture("Interdiction de consommer");
						System.out.println("J'interdis "+id+" de consommer");
					}
				}else{	

					//Reservé à l'envoi d'un message du producteur au buffer
					System.out.println(id+" a produit le message "+message);
					buffer.ajouter(message);
					System.out.println(buffer.toString());


					
				}
			}
		}

	}
}