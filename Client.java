package javaBasics;
 
public class Client {
 
	String nom;
	String prenom;
	
	pyblic client(String nom,String prenom){
		this.nom=nom;
		this.prenom=prenom;
	}
	
	public static void main(String[] args) {
				Client c = new Client("Jean", "Dupond"); 
		System.out.println("Je m'appelle "+c.getNom()+" "+c.getPrenom()); 	
 
	}
 
 	public String getNom() {
		return nom;
	}
 
	public void setNom(String nom) {
		this.nom = nom;
	}
 
	public String getPrenom() {
		return prenom;
	}
 
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
}
