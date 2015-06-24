package net.syncrom.proyecto;

import net.syncrom.proyecto.EmpresasProvider.Empresas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BajaActivity extends Activity {	
	private SimpleCursorAdapter adapBaja;
	private Cursor cur;
	private ContentResolver cr;
	private Uri empresasUri;
	private String[] projection;
	private int[] item;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baja);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		//Columnas de la tabla a recuperar
		 projection = new String[]
				{
				Empresas._ID,
				Empresas.COL_EMPRESA,
				Empresas.COL_WEB 
				};
						
		//Campos a visualizar en el XML del listado
		 item = new int[]
				{
				R.id.tvId,
				R.id.tvEmpresa,
				R.id.tvWeb
				};

		//Definicion para ContentProvider
		empresasUri =  EmpresasProvider.CONTENT_URI;
		cr = getContentResolver();
								 
		//Hacemos la consulta
		cur = cr.query(empresasUri,
						      projection, //Columnas a devolver
						      null,       //Condicion de la query
						      null,       //Argumentos variables de la query
						      null);      //Orden de los resultados								

		//Creamos CursorAdapter para la lista 
		 adapBaja = new SimpleCursorAdapter(this, R.layout.item_preconsulta,
				 cur, projection, item, 0);
		
		//Se recupera ListView con su adapter
		ListView lstBaja = (ListView)findViewById(R.id.LstBaja);		
		lstBaja.setAdapter(adapBaja);	

		//Se define accion al pulsar sobre un item
		lstBaja.setOnItemClickListener(new OnItemClickListener()
		{
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> lstBaja, View view,
					int position, long id)
			{
				//Se muestra mensaje para confirmar borrado
				FragmentManager fragmentManager = getFragmentManager();
		        DialogoAlerta dialogo = new DialogoAlerta(lstBaja, position);
		        dialogo.show(fragmentManager, "tagAlerta");				
			}	
			
		});				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.baja, menu);
		return true;
	}

	@SuppressLint({ "NewApi", "ValidFragment" })
	public class DialogoAlerta extends DialogFragment 
	{
		AdapterView<?> lstBaja;
		int position;
		
	    @SuppressLint("ValidFragment")
		public DialogoAlerta(AdapterView<?> lstBaja, int position) 
	    {
			this.lstBaja = lstBaja;
			this.position= position;
		}

		@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) 
		{
	        AlertDialog.Builder builder =
	                new AlertDialog.Builder(getActivity());
	 
	        builder.setMessage("Estas seguro de Borrar el registro?")
	               .setTitle("CUIDADO!!")
	               .setNegativeButton("NO", new DialogInterface.OnClickListener() 
	               {
	            	   public void onClick(DialogInterface dialog, int id) 
	            	   {
	            		   dialog.cancel();
	                   }
	               })
	               .setPositiveButton("SI", new DialogInterface.OnClickListener() 
	               {
	                   public void onClick(DialogInterface dialog, int id) 
	                   {
	                	   Baja(lstBaja, position);
	                       dialog.cancel();
	                   }
	               });
	        return builder.create();
	    }
	}
	
	public void Baja(AdapterView<?> lstBaja, int position)
	{
		// Se obtiene cursor posicionado en la fila seleccionada
		Cursor cursor = (Cursor) lstBaja.getItemAtPosition(position);
						 					
		// Se obtienen los datos correspondientes de la base de datos
		String id_empresa = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
		String empresa = cursor.getString(cursor.getColumnIndexOrThrow("empresa"));					   				
									
		// Se elimina registro de la base de datos
		ContentResolver cr = getContentResolver();
		try
		{ 				
			cr.delete(EmpresasProvider.CONTENT_URI,
					Empresas._ID + " = "+ id_empresa , null);
			
			Toast.makeText(getApplicationContext(),
				     "Empresa: " + empresa + " eliminada", Toast.LENGTH_SHORT).show();	
			
			//Se refresca el cursor y se cambia en el Adapter para actualizar el ListView
			cur = cr.query(empresasUri, projection, null, null, null);		
			adapBaja.changeCursor(cur);
			

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
				     "Error al borrar empresa: " + empresa, Toast.LENGTH_SHORT).show();				
		}	
	}    	
}
