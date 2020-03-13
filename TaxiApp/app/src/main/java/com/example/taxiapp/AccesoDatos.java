package com.example.taxiapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class AccesoDatos extends SQLiteOpenHelper {


    public AccesoDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Crear la tabla.
        // Cambiar para generar la tabla de users.
        String usu = "create table usuarios(" +
                "id_usu integer primary key autoincrement, " +
                "nom_usu varchar(30), " +
                "ape_usu varchar(30), " +
                "fnu_usu varchar(10), " +
                "ema_usu varchar(30) not null," +
                "tel_usu integer not null," +
                "dir_usu varchar(30)," +
                "pas_usu varchar(15) not null," +
                "tip_usu varchar(30))";
        sqLiteDatabase.execSQL(usu);

        // Crear la tabla.
        // Cambiar para generar la tabla de taxis.
        String tax = "create table taxis(" +
                "id_tax integer primary key autoincrement, " +
                "pat_tax varchar(30) not null, " +
                "cap_tax integer not null, " +
                "mod_tax varchar(30) not null, " +
                "tip_tax varchar(30) not null," +
                "col_tax varchar(30) not null," +
                "est_tax varchar(30) not null)";
        sqLiteDatabase.execSQL(tax);

        // Crear la tabla.
        // Cambiar para generar la tabla de viajes.
        String via = "create table viajes(" +
                "id_via integer primary key autoincrement, " +
                "ori_via varchar(30) not null, " +
                "des_via varchar(30) not null, " +
                "dis_via integer not null," +
                "tie_via integer not null, " +
                "tar_via integer not null," +
                //"id_tax_via integer references taxis(id_tax)," +
                "id_usu_via integer references usuarios(id_usu))";
        sqLiteDatabase.execSQL(via);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String usu = "drop table if exists usuarios";
        sqLiteDatabase.execSQL(usu);
        String via = "drop table if exists viajes";
        sqLiteDatabase.execSQL(via);
        String tax = "drop table if exists taxis";
        sqLiteDatabase.execSQL(tax);
    }

    // Metodo de registro usuarios.

    public long registrarUsuarios(User usu){
        SQLiteDatabase bd = this.getWritableDatabase(); // Abrir en modalidad escritura.
        ContentValues cv = new ContentValues();
        cv.put("id_usu",usu.getId());
        cv.put("nom_usu",usu.getNombre());
        cv.put("ape_usu",usu.getApellido());
        cv.put("fnu_usu",usu.getFechaNac());
        cv.put("ema_usu",usu.getEmail());
        cv.put("tel_usu",usu.getTelefono());
        cv.put("dir_usu",usu.getDireccion());
        cv.put("pas_usu",usu.getPass());
        cv.put("tip_usu",usu.getTipo());
        long res = bd.insert("usuarios",null,cv);
        bd.close();
        return res;
    }

    // Metodo para listar usuarios.

    public ArrayList<User> listarUsuarios(){
        SQLiteDatabase bd = this.getReadableDatabase();
        String sql = "select * from usuarios order by ema_usu ASC";
        ArrayList<User> lista = new ArrayList<>();
        Cursor rs = bd.rawQuery(sql,null);
        while(rs.moveToNext()){
            String id = rs.getString(0);
            String nom = rs.getString(1);
            String ape = rs.getString(2);
            String fnu = rs.getString(3);
            String ema = rs.getString(4);
            int tel = rs.getInt(5);
            String dir = rs.getString(6);
            String pas = rs.getString(7);
            String tip = rs.getString(8);
            User usu = new User(id,nom,ape,fnu,ema,tel,dir,pas,tip);
            lista.add(usu);
        }
        bd.close();
        return lista;
    }

    // Metodo consultar usuarios.

    public User consultarUser(String ema){
        SQLiteDatabase bd = this.getReadableDatabase();
        String sql = "select * from usuarios where ema_usu='"+ema+"'";
        Cursor rs = bd.rawQuery(sql,null);
        if (rs.moveToNext()){ // Si es true, es que lo encontró.
            String id = rs.getString(0);
            String nom = rs.getString(1);
            String ape = rs.getString(2);
            String fnu = rs.getString(3);
            ema = rs.getString(4);
            int tel = rs.getInt(5);
            String dir = rs.getString(6);
            String pas = rs.getString(7);
            String tip = rs.getString(8);
            User usu = new User(id,nom,ape,fnu,ema,tel,dir,pas,tip);
            bd.close();
            return usu;
        }else{
            bd.close();
            return null;
        }
    }

    // Metodo modificar usuarios.

    public long modificarUser(User usu){
        SQLiteDatabase bd = this.getWritableDatabase(); // Abrir en modalidad escritura.
        ContentValues cv = new ContentValues();
        cv.put("pas_usu",usu.getPass());
        cv.put("tel_usu",usu.getTelefono());
        cv.put("tip_usu",usu.getTipo());
        String args[] = new String[1];
        args[0] = usu.getEmail();
        long res = bd.update("usuarios", cv, "ema_usu=?", args);
        bd.close();
        return res;
    }

    //Metodo eliminar usuarios.

    public long eliminarUsuario(String usr){
        SQLiteDatabase bd = this.getWritableDatabase();
        String args[] = new String[1];
        args[0] = usr;
        long res = bd.delete("usuarios","ema_usu=?", args);
        bd.close();
        return res;
    }
}
