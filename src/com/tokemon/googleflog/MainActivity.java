package com.tokemon.googleflog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	public httpHelper api = new httpHelper();
	private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View btnTraduce = findViewById(R.id.btnTraduce);
        btnTraduce.setOnClickListener(this);
        View btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Espera...");
        dialog.setTitle("Trabajando");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        final EditText mText = (EditText)findViewById(R.id.edtTraduce);
        mText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                	// Perform action on key press
                	InputMethodManager imm = (InputMethodManager)getSystemService(
                		      Context.INPUT_METHOD_SERVICE);
                		imm.hideSoftInputFromWindow(mText.getWindowToken(), 0);
                	sender();
                  return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Un switch para cada uno de los items de menu.
        switch (item.getItemId()) {
        case R.id.menu_about:
        	// Let's setup the dialog.
        	final Dialog dialog = new Dialog(MainActivity.this);
        	dialog.setContentView(R.layout.about_main);
        	dialog.setTitle("Acerca de...");
        	dialog.setCancelable(true);
        	// Chingos de opciones.
        		
        	// dismiss button
        	Button button = (Button) dialog.findViewById(R.id.btnOk);
        	button.setOnClickListener(new OnClickListener() {
        		public void onClick(View v) {
        			dialog.dismiss();
        		}
        	});
        	// show da money!
        	dialog.show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void onClick(View v) {
		// Traductor
		if (v.getId()==findViewById(R.id.btnTraduce).getId())
		{
			sender();

		}else if (v.getId()==findViewById(R.id.btnShare).getId()){
			// Share
			TextView txtTexto = (TextView)findViewById(R.id.txtTexto);
			String texto = txtTexto.getText().toString();
			Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, texto);
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, texto);
	        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
		}
	}
    
    public void sender()
    {
		TextView txtTraduce = (TextView)findViewById(R.id.edtTraduce);
		String texto = txtTraduce.getText().toString().replace(" ", "");
		if (texto.equals("")){
			Toast.makeText(this, "Escribe algo primero.", Toast.LENGTH_LONG).show();
		}else{
			// Llamamos al hilo encargado del proceso de traducción.
			new apiCaller().execute();
		}
    }
    
    private class apiCaller extends AsyncTask<String, Float, Boolean>{
    	
    	String resultado; // Variable en la que se almacena el resultado.
    	 
        protected void onPreExecute() {
            dialog.setProgress(0);
            dialog.setMax(100);
            dialog.show(); //Mostramos el diálogo antes de comenzar.
         }

        protected Boolean doInBackground(String... text) {
            
                   try {
                	   TextView txtTraduce = (TextView)findViewById(R.id.edtTraduce);
               		   String texto = txtTraduce.getText().toString().replace(" ", "%20");
                	   resultado = api.post("http://abarcarodriguez.com/googleflog/api.php", texto, "json"); 
                	   // Llamamos a la clase que se conecta.
                   }
                   catch (Exception e) {
                	   
                	   Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                	   
                   }

             return true;
         }
        
         protected void onPostExecute(Boolean bytes) {
        	 TextView txtTraducido = (TextView)findViewById(R.id.txtTexto);
      	   	 txtTraducido.setText(resultado);
             dialog.dismiss();
             Toast.makeText(MainActivity.this, "Finalizado.", Toast.LENGTH_SHORT).show();
         }
   }
}
