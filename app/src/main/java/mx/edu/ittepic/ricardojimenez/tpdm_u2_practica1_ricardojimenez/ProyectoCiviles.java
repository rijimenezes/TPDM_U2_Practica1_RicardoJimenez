package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica1_ricardojimenez;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class ProyectoCiviles {
    private int idProyecto;
    private String descripcion,ubicacion,fecha;
    private float presupuesto;
    private BaseDatos bd;
    protected String error;

    public ProyectoCiviles(Activity activity){
        bd = new BaseDatos(activity,"PROYECTO",null,1);
    }
    public ProyectoCiviles(int idProyecto, String descripcion, String ubicacion, String fecha, float presupuesto) {
        this.idProyecto = idProyecto;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.presupuesto = presupuesto;
        this.error ="";
    }

    public boolean insertar(ProyectoCiviles pc){
        try{
            SQLiteDatabase transaccionInsertar = bd.getWritableDatabase();
            String sql = "INSERT INTO PROYECTOS VALUES(NULL,'%1','%2','%3'," +pc.getPresupuesto()+")";
            sql = sql.replace("%1",pc.getDescripcion());
            sql = sql.replace("%2",pc.getUbicacion());
            sql =  sql.replace("%3",pc.getFecha());
            transaccionInsertar.execSQL(sql);
            transaccionInsertar.close();
        }catch (SQLiteException e){
            error = e.getMessage();return false;
        }
        return true;
    }
    public ProyectoCiviles[] consultar(String col, String key){
        ProyectoCiviles[] datos =null;
        try {
            SQLiteDatabase transaccionConsulta = bd.getReadableDatabase();
            String consulta = "SELECT * FROM PROYECTOS WHERE idProyecto ="+key;
            int i = 0;
            if(col=="descripcion")
                consulta="SELECT * FROM PROYECTOS WHERE descripcion like '"+key+"%'";
            Cursor c = transaccionConsulta.rawQuery(consulta,null);
            if(c.moveToFirst()){
                datos = new ProyectoCiviles[c.getCount()];
                do{
                    datos[i]= new ProyectoCiviles(c.getInt(0),c.getString(1),c.getString(2),
                            c.getString(3),c.getFloat(4));
                    i++;
                }while (c.moveToNext());
            }else{
                error ="No se encontro el registro";
                return null;
            }
            transaccionConsulta.close();
        }catch (SQLiteException e){
            error=e.getMessage().toString();
            return null;
        }
        return datos;
    }
    public ProyectoCiviles[] consultar(){
        ProyectoCiviles[] datos =null;
        try {
            SQLiteDatabase transaccionConsulta = bd.getReadableDatabase();
            String consulta = "SELECT * FROM PROYECTOS";
            int i = 0;
            Cursor c = transaccionConsulta.rawQuery(consulta,null);
            if(c.moveToFirst()){
                datos = new ProyectoCiviles[c.getCount()];
                do{
                    datos[i]=new ProyectoCiviles(c.getInt(0),c.getString(1),c.getString(2),
                            c.getString(3),c.getFloat(4));
                    i++;
                }while (c.moveToNext());
            }else{
                error ="No se encontro el registro";
                return null;
            }
            transaccionConsulta.close();
        }catch (SQLiteException e){
            error=e.getMessage().toString();
            return null;
        }
        return datos;
    }
    public boolean eliminar(int ab){
        int resultado=0;
        try{
            SQLiteDatabase transaccionEliminar = bd.getWritableDatabase();
            String[] claves={ab+""};
            resultado = transaccionEliminar.delete("PROYECTOS","idProyecto=?",claves);
            transaccionEliminar.close();
        }catch(SQLiteException e){ error = e.getMessage().toString();return false;}
        return resultado>0;
    }
    public boolean actualizar(ProyectoCiviles pc){
        try{
            SQLiteDatabase transaccionActualizar = bd.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("descripcion",pc.descripcion);
            datos.put("ubicacion",pc.getUbicacion());
            datos.put("fecha",pc.getFecha());
            datos.put("presupuesto",pc.getPresupuesto()+"");
            String[] claves = {pc.getIdProyecto()+""};
            long resultado = transaccionActualizar.update("PROYECTOS",datos,"idProyecto=?",claves);
            transaccionActualizar.close();
            if(resultado<0)
                return false;
        }catch(SQLiteException e){ error = e.getMessage().toString();return false;}
        return true;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(float presupuesto) {
        this.presupuesto = presupuesto;
    }

    public BaseDatos getBd() {
        return bd;
    }

    public void setBd(BaseDatos bd) {
        this.bd = bd;
    }
}
