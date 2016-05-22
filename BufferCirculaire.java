public class BufferCirculaire {
	String[] messages;
	int tailleBuffer;
	int in;
	int out;

	
	public BufferCirculaire(int tailleBuffer){
		messages = new String[tailleBuffer];
		this.tailleBuffer=tailleBuffer;
		in = 0;
		out = 0;

	}

	//Ajoute un élément dans le buffer
	public void ajouter(String m){
		messages[in]=m;
		in=++in%tailleBuffer;

		System.out.println("J'ajoute le message "+m+" à l'index " + in);
		

	}

	//Retire un élément du buffer et le retourne
	public String retirer(){
		String m = messages[out];
		messages[out]= null;
		out=++out%tailleBuffer;

		System.out.println("Je retire le message "+m+" de l'index "+ out);
		return m;
	}

	//Renvoi vrai si le buffer est rempli
	public boolean est_Rempli(){
		if(in==out && messages[in]!=null){
			return true;
		}
		else
			return false;

	}

	//Renvoi vrai si le buffer est vide
	public boolean est_Vide(){

		if(in==out && messages[in]==null)
			return true;
		else
			return false;
	}

	//Renvoi l'état du buffer sous forme de string
	public String toString(){
		String buffer ="-------BUFFER-------\n";

		for(int i=0;i<tailleBuffer;i++){
			if(messages[i]==null)
				buffer+="vide\n";
			else
				buffer+= messages[i]+"\n";
		}
		buffer+="--------END/--------\n";
		return buffer;
	}

}
