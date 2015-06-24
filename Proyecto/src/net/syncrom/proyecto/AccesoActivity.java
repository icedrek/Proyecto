package net.syncrom.proyecto;

import net.syncrom.proyecto.EmpresasProvider.Empresas;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AccesoActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acceso);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				
		//Columnas de la tabla a recuperar
		String[] projection = new String[]
				{
				Empresas._ID,
				Empresas.COL_EMPRESA,
				Empresas.COL_WEB 
				};			
		
		//Campos a visualizar en el XML del listado
		int[] item = new int[]
				{
				R.id.tvId,
				R.id.tvEmpresa,
				R.id.tvWeb
				};		

		//Definicion para ContentProvider
		Uri empresasUri =  EmpresasProvider.CONTENT_URI;
		ContentResolver cr = getContentResolver();		
							 
		//Hacemos la consulta
		Cursor cur = cr.query(empresasUri,				
					          projection, //Columnas a devolver
					          null,       //Condicion de la query
					          null,       //Argumentos variables de la query
					          null);      //Orden de los resultados		
								
		//Creamos CursorAdapter para la lista 
		SimpleCursorAdapter adapAcceso = 
				new SimpleCursorAdapter(this, R.layout.item_preconsulta,
						cur, projection, item, 0);					

		ListView lstAcceso = (ListView)findViewById(R.id.LstAcceso);	
		lstAcceso.setAdapter(adapAcceso);	
		
		lstAcceso.setOnItemClickListener(new OnItemClickListener() 
		{
			   @Override
			   public void onItemClick(AdapterView<?> lstAcceso, View view, 
			     int position, long id) 
			   {
				   // Se obtienen datos y su posicion en lista
				   Cursor cursor = (Cursor) lstAcceso.getItemAtPosition(position);
			 
				   // Recuperamos los datos del item seleccionado			   
				   String web = 
						   cursor.getString(cursor.getColumnIndexOrThrow("web"));
				   
				   //Se abre web en el navegador
				   Intent navegador = new Intent(Intent.ACTION_VIEW);
				   navegador.setData(Uri.parse("http://"+web));
				   startActivity(navegador);			 
			   }
		});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.acceso, menu);
		return true;
	}
}
