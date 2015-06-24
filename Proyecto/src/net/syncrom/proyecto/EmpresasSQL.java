package net.syncrom.proyecto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class EmpresasSQL extends SQLiteOpenHelper{	
	//Sentencia SQL para crear la tabla de Empresas	
    String sqlCreate = "CREATE TABLE Empresas ("  
	                 + "_id INTEGER PRIMARY KEY AUTOINCREMENT," 
	                 + "empresa TEXT, "           
	                 + "categoria TEXT, "
	                 + "direccion TEXT, "         
	                 + "codPostal INT, "          
	                 + "poblacion TEXT, "         
	                 + "provincia TEXT, "         
	                 + "eMail TEXT, "             	                 
	                 + "web TEXT)";
 
    //Constructor para la clase
    public EmpresasSQL(Context contexto, String nombreDB,
                               CursorFactory factory, int version) {
        super(contexto, nombreDB, factory, version);
    }
    
    //Creacion de la tabla
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }
 
/*
 * NOTA: En este caso solo eliminamos la tabla anterior y creamos una nueva
 *       que estara vacia y tendra el nuevo formato.
 */     
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //Se elimina la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Empresas");
 
        //Se crea la nueva version de la tabla
        db.execSQL(sqlCreate);
    }

}
