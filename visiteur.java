package javaBasics;
 
public class Visiteur {
 
	String ipAdress;
	
	public static void main(String[] args) {
				Visiteur v = new Visiteur("125.2.2.87"); 
		System.out.println(v.getIpAdress()); 	
 
	}
 
 	public String getIpAdress() {
		return ipAdress;
	}
 
	
}
