package net.syncrom.proyecto;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		/*
		 * Al pulsar boton de Alta se inicia un nuevo Activity
		 * con los campos necesarios para dar de alta una empresa.
		 */
		Button btAlta = (Button)findViewById(R.id.btAlta);
		Pulsa(btAlta, AltaActivity.class);
				
		/*
		 * Al pulsar boton de Baja se inicia un nuevo Activity que muestra
		 * un listado con el nombre de la empresa y su URl.
		 * Cuando seleccionamos una empresa se borra de la base de datos 
		 */
		
		Button btBaja = (Button)findViewById(R.id.btBaja);
		Pulsa(btBaja, BajaActivity.class);		
		
		/*
		 * Al pulsar boton de Consulta se inicia un nuevo Activity que muestra
		 * un listado con el nombre de la empresa y su URl.
		 * Pudiendo buscar por nombre(o parte del nombre) 
		 */
		Button btConsulta = (Button)findViewById(R.id.btConsulta);
		Pulsa(btConsulta, ConsultaActivity.class);
		
		/*
		 * Al pulsar boton de Acceso web se inicia un nuevo Activity que muestra
		 * un listado con el nombre de la empresa y su URl.
		 * Cuando seleccionamos una empresa se abr� su p�gina web en el navegador.
		 */
		Button btAcceso = (Button)findViewById(R.id.btAcceso);
		Pulsa(btAcceso, AccesoActivity.class);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//Se define la pulsacion de boton.
	private void Pulsa(Button boton, final Class<?> clase) {
		// TODO Auto-generated method stub
		boton.setOnClickListener(new OnClickListener() 
		{			
			@Override
			public void onClick(View view) 
			{						
				Intent intent = new Intent(MainActivity.this, clase);
				startActivity(intent);					
			}			
		});	
		
	}
}
