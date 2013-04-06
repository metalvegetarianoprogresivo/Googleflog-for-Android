package com.tokemon.googleflog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class httpHelper {
	
	public String post(String urlGet){
		
		try{
			
			HttpClient clienteHttp = new DefaultHttpClient(); 
			HttpPost postHttp = new HttpPost(urlGet);
			
			HttpResponse respuesta = clienteHttp.execute(postHttp);
			HttpEntity entidad = respuesta.getEntity();
			
			String resultado = EntityUtils.toString(entidad);
			
			return resultado;
			
		}catch(Exception e){
			// IF ERROR
			return "** eErr0or Al c0oñeEcTArzheE **~ xD";
			// Errores, Errores Everywhere...
		}
		
		
	}
	
}
