package net.syncrom.proyecto;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

public class EmpresasProvider extends ContentProvider{
	//Definicion del CONTENT_URI
	public static final String uri = "content://net.syncrom.proyecto/empresasprovider";
	public static final Uri CONTENT_URI = Uri.parse(uri);
	
	//Clase interna para declarar las constantes de columna
	public static final class Empresas implements BaseColumns
	{
	    private Empresas() {}
	 
	    //Nombres de columnas
	    public static final String COL_EMPRESA   = "empresa";
	    public static final String COL_CATEGORIA = "categoria";
	    public static final String COL_DIRECCION = "direccion";
	    public static final String COL_CODPOSTAL = "codPostal";
	    public static final String COL_POBLACION = "poblacion";
	    public static final String COL_PROVINCIA = "provincia";
	    public static final String COL_EMAIL     = "eMail";
	    public static final String COL_WEB       = "web";
	}
	
	//Base de datos
	private EmpresasSQL empdbh;
	private static final String BD_NOMBRE      = "DBEmpresas";
	private static final int    BD_VERSION     = 1;
	private static final String TABLA_EMPRESAS = "Empresas";
	
	
	//UriMatcher
	private static final int EMPRESAS = 1;
	private static final int EMPRESAS_ID = 2;
	private static final UriMatcher uriMatcher;
	 
	//Inicializamos el UriMatcher
	static 
	{
	    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	    uriMatcher.addURI("net.syncrom.proyecto", "empresas", EMPRESAS);
	    uriMatcher.addURI("net.syncrom.proyecto", "empresas/#", EMPRESAS_ID);
	}
	
	@Override
	//Metodo para creacion de base de datos
	public boolean onCreate() 
	{
		empdbh = new EmpresasSQL(getContext(), BD_NOMBRE, null, BD_VERSION);
	 
	    return true;
	}
	
	//Consulta
	@Override
	public Cursor query(Uri uri, String[] projection,
	    String selection, String[] selectionArgs, String sortOrder) 
	{
	 
	    //Si es una consulta a un ID concreto construimos el WHERE
	    String where = selection;
	    if(uriMatcher.match(uri) == EMPRESAS_ID)
	    {	                
	    	where = "_id=" + uri.getLastPathSegment();	        
	    }
	 
	    SQLiteDatabase db = empdbh.getWritableDatabase();
	 
	    Cursor c = db.query(TABLA_EMPRESAS, projection, where, selectionArgs, null, null, sortOrder);
	 
	    return c;
	}
	
	//Actualizacion
	@Override
	public int update(Uri uri, ContentValues values,String selection, String[] selectionArgs) 
	{	 
	    int cont;
	 
	    //Si es una consulta a un ID concreto construimos el WHERE
	    String where = selection;
	    if(uriMatcher.match(uri) == EMPRESAS_ID)
	    {	            
	    	where = "_id=" + uri.getLastPathSegment();	        
	    }
	 
	    SQLiteDatabase db = empdbh.getWritableDatabase();
	 
	    cont = db.update(TABLA_EMPRESAS, values, where, selectionArgs);
	 
	    return cont;
	}
	 
	//Borrado
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) 
	{	 
	    int cont;
	 
	    //Si es una consulta a un ID concreto construimos el WHERE
	    String where = selection;
	    if(uriMatcher.match(uri) == EMPRESAS_ID)
	    {	    
	    	where = "_id=" + uri.getLastPathSegment();	        
	    }
	 
	    SQLiteDatabase db = empdbh.getWritableDatabase();
	 
	    cont = db.delete(TABLA_EMPRESAS, where, selectionArgs);
	 
	    return cont;
	}
	
	//Insertar
	@Override
	public Uri insert(Uri uri, ContentValues values) 
	{	 
	    long regId = 1;
	 
	    SQLiteDatabase db = empdbh.getWritableDatabase();
	 
	    regId = db.insert(TABLA_EMPRESAS, null, values);
	 
	    Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
	 
	    return newUri;
	}
	
	// Devuelve el tipo MIME a partir de un URI pasado como parámetro
	@Override
	public String getType(Uri uri) 
	{	 
	    int match = uriMatcher.match(uri);
	 
	    switch (match)
	    {
	        case EMPRESAS:
	            return "vnd.android.cursor.dir/vnd.net.syncrom.proyecto";
	        case EMPRESAS_ID:
	            return "vnd.android.cursor.item/vnd.net.syncrom.proyecto";
	        default:
	            return null;
	    }
	}
}
