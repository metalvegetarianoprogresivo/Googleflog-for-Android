package com.tokemon.googleflog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class httpHelper {
	
	public String post(String urlGet, String texto, String format){
		
		try{
			
			String fillURL = urlGet + "?s=" + texto + "&format=" + format;
			
			HttpClient clienteHttp = new DefaultHttpClient(); 
			HttpPost postHttp = new HttpPost(fillURL);
			
			HttpResponse respuesta = clienteHttp.execute(postHttp);
			HttpEntity entidad = respuesta.getEntity();
			
			String resultado = EntityUtils.toString(entidad);
			
			JSONObject json = new JSONObject(resultado);
			
			return json.getString("message");
			
		}catch(Exception e){
			// IF ERROR
			return e.getMessage();
			//return "** eErr0or Al c0oñeEcTArzheE **~ xD";
			// Errores, Errores Everywhere...
		}
		
		
	}
	
}
