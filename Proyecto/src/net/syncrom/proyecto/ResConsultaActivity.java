package net.syncrom.proyecto;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.widget.TextView;

public class ResConsultaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resconsulta);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		//Se recuperan datos de empresa y se muestran en pantalla 
		Bundle datos=getIntent().getExtras();

		TextView tvEmpresa=(TextView)findViewById(R.id.tvEmpresa);
		tvEmpresa.setText(datos.getString("empresa"));
		
		TextView tvCategoria=(TextView)findViewById(R.id.tvCategoria);
		tvCategoria.setText(datos.getString("categoria"));
		
		TextView tvDireccion=(TextView)findViewById(R.id.tvDireccion);
		tvDireccion.setText(datos.getString("direccion"));
		
		TextView tvPoblacion =(TextView)findViewById(R.id.tvPoblacion);
		String poblacion = datos.getString("codPos") + ", " + 
						   datos.getString("poblacion") + " (" +
						   datos.getString("provincia") + ")";
		tvPoblacion.setText(poblacion);
		
		TextView tvEmail=(TextView)findViewById(R.id.tvEmail);
		tvEmail.setText(datos.getString("eMail"));
		
		TextView tvWeb=(TextView)findViewById(R.id.tvWeb);
		tvWeb.setText(datos.getString("web"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.res_consulta, menu);
		return true;
	}

}
