import java.util.*;
import java.io.*;
import java.net.*;


public class Connexion {
	Socket s;
	BufferedReader bf;
	PrintWriter pf;


	public Connexion(Socket s){

		this.s=s;
		initEcriture();
		initLecture();

	}

	public Connexion(String host,int port){

		try{
			s = new Socket(host, port);
		}catch(IOException e){
			System.out.println("Erreur lors de la connexion producteur");
		}
		initEcriture();
		initLecture();
	}

	
	public void initLecture(){

		try{
			bf = new BufferedReader(new InputStreamReader (s.getInputStream()));
		}catch(IOException e){
			System.out.println(" erreur lors de l'initialisation de la lecture");
		}
	}

	public void initEcriture(){
		try{
			pf = new PrintWriter(s.getOutputStream(), true);
		}catch(IOException e){
			System.out.println("erreur lors de l'initialisation de l'ecriture");
		}

	}

	public String lecture()  {
		try {

			return bf.readLine();

		} catch (IOException e) {
			System.out.println("ERREUR DE LECTURE");
			e.printStackTrace();
		}
		return null;
	}

	public void ecriture(String string){
		pf.println(string);

	}



}