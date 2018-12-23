package javaBasics;;

import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Initialiser extends ActionBarActivity {

	public static final String DONNEES_PARTAGEES = "donneesEnregistre";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_initialiser);
		SharedPreferences posData = getSharedPreferences(DONNEES_PARTAGEES, 0);
		SharedPreferences.Editor editor = posData.edit();
		editor.putBoolean("bloquer", false);
		editor.putBoolean("first_launching", false);
		editor.putString("num_gateway", "770236818");
		editor.putString("url_serveur", "");
		editor.putString("enTete_url", "");
		editor.putString("customer_id", "1055");
		editor.commit();
	}
	
	public int afficher(){
		
	return 1;
	}
	
	public int retourner(){
	return 2;
	}
	
}
