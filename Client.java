package javaBasics;
 
public class Client {
 
	String nom;
	String prenom;
	String telephone
	
	public client(String nom,String prenom,String telephone){
		this.nom=nom;
		this.prenom=prenom;
		this.telephone=telephone;
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
	
	public String getTelephone() {
		return telephone;
	}
 
	public void setTelephone(String prenom) {
		this.telephone = telephone;
	}
}
