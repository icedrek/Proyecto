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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ConsultaActivity extends Activity {	
	private Cursor cur;
	private String empresa;
	private String web;
	private String categoria;
	private String direccion;
	private String codPos;
	private String poblacion;
	private String provincia;
	private String eMail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta);	
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		// Se recupera elemento Button
		ImageButton btBusca = (ImageButton)findViewById(R.id.btBusca);
		
		// Se define accion onClick
		btBusca.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{	
				
				//Columnas de la tabla a recuperar
				 String[] projection = new String[]
						{
						Empresas._ID,
						Empresas.COL_EMPRESA,
						Empresas.COL_WEB,
						Empresas.COL_CATEGORIA,
						Empresas.COL_DIRECCION,
						Empresas.COL_CODPOSTAL,
						Empresas.COL_POBLACION,
						Empresas.COL_PROVINCIA,
						Empresas.COL_EMAIL						 
						};
								
				//Campos a visualizar en el XML del listado
				 int[] item = new int[]
						{
						R.id.tvId,
						R.id.tvEmpresa,
						R.id.tvWeb,
						};

				//Definicion para ContentProvider
				Uri empresasUri =  EmpresasProvider.CONTENT_URI;
				ContentResolver cr = getContentResolver();
				
				//Se recuperan datos de element EditText para consulta
				EditText etBusca = (EditText)findViewById(R.id.etBusca);				
				String where = "empresa LIKE '%"+etBusca.getText().toString()+"%'";
				
				//Hacemos la consulta
				cur = cr.query(empresasUri,
							   projection,  //Columnas a devolver
							   where,       //Condicion de la query
							   null,       //Argumentos variables de la query
							   null);      //Orden de los resultados		
				
				//Nos aseguramos de que existe al menos un registro
				if (cur.moveToFirst()) 
				{
				     //Recorremos el cursor hasta que no haya mas registros
				     do {				    	 
				         empresa = cur.getString(1);
				         web= cur.getString(2);
				         categoria = cur.getString(3);
				         direccion = cur.getString(4);
				         codPos = cur.getString(5);
				         poblacion = cur.getString(6);
				         provincia = cur.getString(7);
				         eMail = cur.getString(8);
				     } while(cur.moveToNext());
				}
															
				//Creamos CursorAdapter para la lista 
				 SimpleCursorAdapter adapConsulta = 
						 new SimpleCursorAdapter(ConsultaActivity.this, R.layout.item_preconsulta,
						                		 cur, projection, item, 0);
				
				ListView lstConsulta = (ListView)findViewById(R.id.LstConsulta);	
				lstConsulta.setAdapter(adapConsulta);	
				
				//Se define accion al pulsar sobre un item				
				lstConsulta.setOnItemClickListener(new OnItemClickListener()
				{
					public void onItemClick(AdapterView<?> lstBaja, View view,
							int position, long id)
					{
						Intent intent = new Intent(ConsultaActivity.this, ResConsultaActivity.class);
						Bundle datos=new Bundle();
						datos.putString("empresa", empresa);
						datos.putString("categoria", categoria);
						datos.putString("direccion", direccion);
						datos.putString("codPos", codPos);
						datos.putString("poblacion", poblacion);
						datos.putString("provincia", provincia);
						datos.putString("eMail", eMail);
						datos.putString("web", web);
						intent.putExtras(datos);																		
						startActivity(intent);				
					}	
					
				});				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consulta, menu);
		return true;
	}
}
