package net.syncrom.proyecto;

import net.syncrom.proyecto.EmpresasProvider.Empresas;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AltaActivity extends Activity {
	private Spinner  spCategoria;
	private EditText etEmpresa, etDireccion, etCodPos, etPoblacion, etProvincia, etEmail, etWeb;
	
	//Columnas de la tabla a recuperar
	String[] projection = new String[] 
			{
			Empresas._ID,			
			Empresas.COL_EMPRESA,
			Empresas.COL_CATEGORIA,
			Empresas.COL_DIRECCION,
			Empresas.COL_CODPOSTAL,
			Empresas.COL_POBLACION,
			Empresas.COL_PROVINCIA,
			Empresas.COL_EMAIL,
			Empresas.COL_WEB 
			};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alta);	
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		
		spCategoria = (Spinner)findViewById(R.id.spCategoria);
		
		//Se define adapter para la lista desplegable de Categorias.
		//Se asocia a Textview_Spinner para darle formato
		ArrayAdapter<CharSequence> adapCategoria = 				
				ArrayAdapter.createFromResource(this, R.array.categoria, R.layout.textview_spinner);		
		adapCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);				
		spCategoria.setAdapter(adapCategoria);				
		
		Button btAlta = (Button)findViewById(R.id.btAlta2);
		//Se define accion para pulsacion de boton de Alta
		btAlta.setOnClickListener(new OnClickListener() 
		{			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View view) 
			{
				//Se recuperan los campos de la pantalla
				etEmpresa   = (EditText)findViewById(R.id.etEmpresa);
				spCategoria = (Spinner) findViewById(R.id.spCategoria);
				etDireccion = (EditText)findViewById(R.id.etDireccion);
				etCodPos    = (EditText)findViewById(R.id.etCodPos);
				etPoblacion = (EditText)findViewById(R.id.etPoblacion);
				etProvincia = (EditText)findViewById(R.id.etProvincia);
				etEmail     = (EditText)findViewById(R.id.etEmail);
				etWeb       = (EditText)findViewById(R.id.etWeb);
				
				
				//Se informan los datos del registro				
				ContentValues values = new ContentValues();
				 
				values.put(Empresas.COL_EMPRESA, etEmpresa.getText().toString());
				values.put(Empresas.COL_CATEGORIA, spCategoria.getSelectedItem().toString());
				values.put(Empresas.COL_DIRECCION, etDireccion.getText().toString());
				
				if (etCodPos.getText().toString().isEmpty())
				{				
					values.put(Empresas.COL_CODPOSTAL, 0);					
				} else {
					values.put(Empresas.COL_CODPOSTAL, 
							Integer.parseInt(etCodPos.getText().toString()));					
				}
				values.put(Empresas.COL_POBLACION, etPoblacion.getText().toString());
				values.put(Empresas.COL_PROVINCIA, etProvincia.getText().toString());
				values.put(Empresas.COL_EMAIL, etEmail.getText().toString());
				values.put(Empresas.COL_WEB, etWeb.getText().toString());
				 
				ContentResolver cr = getContentResolver();
				try
				{
					//Se insertaregistro en Base de Datos
					cr.insert(EmpresasProvider.CONTENT_URI, values);
					
					//Se inicializan datos en pantalla				
					etEmpresa.setText(null);
					spCategoria.setSelection(0);
					etDireccion.setText(null);
					etCodPos.setText(null);
					etPoblacion.setText(null);
					etProvincia.setText(null);
					etEmail.setText(null);
					etWeb.setText(null);
					
					Toast.makeText(AltaActivity.this,"Los datos fueron grabados", 
							Toast.LENGTH_LONG).show();
				
				} catch(Exception e){
					Toast.makeText(AltaActivity.this,"Error al grabar datos: "+ e.getMessage(), 
							Toast.LENGTH_LONG).show();
				}
			}				
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alta, menu);
		return true;
	}
}
