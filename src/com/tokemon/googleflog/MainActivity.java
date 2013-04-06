package com.tokemon.googleflog;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	public httpHelper api = new httpHelper();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View btnTraduce = findViewById(R.id.btnTraduce);
        btnTraduce.setOnClickListener(this);
        View btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(this);
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
        		
        	// El boton de dismiss...
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
			TextView txtTexto = (TextView)findViewById(R.id.txtTexto);
			TextView txtTraduce = (TextView)findViewById(R.id.edtTraduce);
			String texto = txtTraduce.getText().toString().replace(" ", "");
			if (texto.equals("")){
				Toast.makeText(this, "Escribe algo primero.", Toast.LENGTH_LONG).show();
			}else{
				texto = txtTraduce.getText().toString().replace(" ", "%20");
				String resultado = api.post("http://abarcarodriguez.com/googleflog/api.php?s=" + texto);
				txtTexto.setText(resultado);
				Toast.makeText(this, "Finalizado.", Toast.LENGTH_LONG).show();
			}

		}else if (v.getId()==findViewById(R.id.btnShare).getId()){
			// Share
			TextView txtTexto = (TextView)findViewById(R.id.txtTexto);
			String texto = txtTexto.getText().toString();
			Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, texto);
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, texto);
	        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
		}
	}
}
