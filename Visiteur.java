package javaBasics;
 
public class Visiteur {
 
	String ipAdress;
	int visitNumber;
	
	public Visiteur(String ipAdress, int visitNumber){
		this.ipAdress=ipAdress;
		this.visitNumber=visitNumber;
	}
	
	public static void main(String[] args) {
				Visiteur v = new Visiteur("125.2.2.87", 3); 
		System.out.println(v.getIpAdress()); 	
 
	}
 
 	public String getIpAdress() {
		return ipAdress;
	}
 	public int visitNumber() {
		return visitNumber;
	}
	
}
