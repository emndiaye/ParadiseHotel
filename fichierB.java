package com.sewa.sewapersonal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;

import com.sewa.sewapersonal.R;

import calculs.packadge.Calcul;
import calculs.packadge.Chryptage;
import calculs.packadge.KeyGenerator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CarteToWallet1 extends Activity {
	public static final String DONNEES_PARTAGEES = "donneesEnregistre";
	String numTel,montant,ret,line,msg,message;
	TextView txtNotif,textHaut,enTete,textBas,textNumtel1,textNumtel,textMontant1,textMontant;
	Button btnterminer;
	Typeface ma_police;	
	Chryptage chryptage;
	Calcul calcul;
	KeyGenerator keyGenerator;
	String keyServer,idcarte,idapplication,url;
	boolean b = false, isConnected;
	int nmb_recu;
	public PendingIntent sentPI;
	public BroadcastReceiver messagerie;
	public String ENVOIE = "SMS_ENVOYE",myKey,text;
	ConnectivityManager connectivityManager;
	NetworkInfo networkInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carte_to_wallet1);
		int SDK_INT = android.os.Build.VERSION.SDK_INT;
		if (SDK_INT > 8) 
		{
		  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
	                  StrictMode.setThreadPolicy(policy);
	        //your codes here
	    }
		ma_police=Typeface.createFromAsset(getAssets(), "ma_police.otf");
		textHaut = (TextView) findViewById(R.id.carte_to_wallet1_text_haut);textHaut.setTypeface(ma_police);
		textBas = (TextView) findViewById(R.id.carte_to_wallet1_text_bas);textBas.setTypeface(ma_police);
		enTete = (TextView) findViewById(R.id.carte_to_wallet1_text_enTete);enTete.setTypeface(ma_police);
		txtNotif = (TextView) findViewById(R.id.carte_to_wallet1_notif_text);txtNotif.setTypeface(ma_police);
		
		textNumtel1 = (TextView) findViewById(R.id.carte_to_wallet1_text_numwal1);textHaut.setTypeface(ma_police);
		textNumtel = (TextView) findViewById(R.id.carte_to_wallet1_text_numwal);textBas.setTypeface(ma_police);
		textMontant1 = (TextView) findViewById(R.id.carte_to_wallet1_text_montant1);enTete.setTypeface(ma_police);
		textMontant = (TextView) findViewById(R.id.carte_to_wallet1_text_montant);txtNotif.setTypeface(ma_police);
		btnterminer =(Button)findViewById(R.id.carte_to_wallet1_bouton);btnterminer.setTypeface(ma_police);
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			this.finish();
			return;
		}
		calcul= new Calcul();
		url=calcul.getAdresseProd();
		SharedPreferences getData = getSharedPreferences(DONNEES_PARTAGEES, 0);	
		idcarte = getData.getString("identicarte", "");	
		numTel = getData.getString("wallet_nume", "");
		idapplication = getData.getString("codeConfirmation", "");
		textNumtel1.setText(numTel);
		montant = getData.getString("montant_wallet", "");
		textMontant1.setText(montant);
		message=extras.getString("infoswallet");
		keyGenerator = new KeyGenerator();
		chryptage = new Chryptage();
		keyServer = keyGenerator.getKey();
		message = chryptage.crypter(message, keyServer);
	}
	
	public void terminer(View v) {
		connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		networkInfo = connectivityManager.getActiveNetworkInfo();
		isConnected = networkInfo != null && networkInfo.isAvailable()
				&& networkInfo.isConnected();
		if (isConnected) {
			//envoiHTML();
			if(addDemandeCarteToWallet())
			{
				chryptage=new Chryptage();
            	keyGenerator=new KeyGenerator();
            	myKey=keyGenerator.getLocalKey(idapplication);
            	ret=chryptage.decrypter(ret, myKey);
            	if(ret.equalsIgnoreCase("solde insuffisant"))
            	{
            		showMessage("solde insuffisant");
            	}            	            		
            	else
            	{           	
            		showMessage("SERVICE NON DISPONIBLE");
            		//showMessage(ret);
                    //Intent retourIntent = new Intent(CarteToWallet1.this, OperationsurCarte.class);
                    //retourIntent.putExtra("infos", ret);            
                    //startActivity(retourIntent); 
                    //CarteToWallet1.this.finish();
                    
            	}
				
			}			
		} else {
			//envoiSMS();
			showMessage("Merci de vérifier votre connexion GPRS ou WIFI");
		}
	}
	
	private boolean addDemandeCarteToWallet()
    {
    	// 127.0.0.1 => 10.0.2.2
		ret="";
        //HttpClient httpclient = new DefaultHttpClient();
		HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
		SchemeRegistry registry = new SchemeRegistry();
		DefaultHttpClient httpclient = new DefaultHttpClient();
		SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
		socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
		registry.register(new Scheme("https", socketFactory, 443));
		SingleClientConnManager mgr = new SingleClientConnManager(httpclient.getParams(), registry);
		DefaultHttpClient httpClient = new DefaultHttpClient(mgr, httpclient.getParams());
		// Set verifier     
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
        //HttpPost httppost = new HttpPost(url_serveur+enTete_url);
		HttpPost httppost = new HttpPost(url);
		//HttpPost httppost = new HttpPost("https://www.sewafs.com/mobile/index.php?c=porteur&m=cardProcess&message=");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();				
		nameValuePairs.add(new BasicNameValuePair("message", message));		
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//httpclient.execute(httppost);
			HttpResponse response;
			response = httpclient.execute(httppost);
			BufferedReader reader;
			reader = new BufferedReader(new InputStreamReader(
			response.getEntity().getContent()));
			//StringBuffer buffer = new StringBuffer();
			line = null;
			while ((line = reader.readLine()) != null) {
				if(line.equals("non")){ 
					return false;
				}
				ret +=line;

			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return true;
    }
	@SuppressLint("InflateParams")
	public void showMessage(String param){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(R.layout.show_message, null);
		alertDialogBuilder.setView(alertDialogView);
		alertDialogBuilder.setCancelable(true);
		TextView text=(TextView)alertDialogView.findViewById(R.id.notif_text);
		text.setText(param);
		alertDialogBuilder.setPositiveButton(getResources().getString(R.string.ok_label),new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				finish();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
	}

}
