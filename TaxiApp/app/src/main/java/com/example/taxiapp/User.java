package com.example.taxiapp;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String id;
    private String nombre;
    private String apellido;
    private String fechaNac;
    private String email;
    private int telefono;
    private String direccion;
    private String pass;
    private String cpass;
    private String tipo;

    public User() {
    }

    public User(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public User(int telefono, String pass) {
        this.telefono = telefono;
        this.pass = pass;
    }

    public User(String nombre, String apellido, String fechaNac, String email, int telefono,
                String direccion, String pass, String tipo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.pass = pass;
        this.tipo = tipo;
    }

    public User(String id, String nombre, String apellido, String fechaNac, String email,
                int telefono, String direccion, String pass, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.pass = pass;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCpass() {
        return cpass;
    }

    public void setCpass(String cpass) {
        this.cpass = cpass;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.apellido);
        dest.writeString(this.fechaNac);
        dest.writeString(this.email);
        dest.writeInt(this.telefono);
        dest.writeString(this.direccion);
        dest.writeString(this.pass);
        dest.writeString(this.cpass);
        dest.writeString(this.tipo);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.nombre = in.readString();
        this.apellido = in.readString();
        this.fechaNac = in.readString();
        this.email = in.readString();
        this.telefono = in.readInt();
        this.direccion = in.readString();
        this.pass = in.readString();
        this.cpass = in.readString();
        this.tipo = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return  "Id: " + id +
                "\n" +
                "Nombre: " + nombre + " " + apellido +
                "\n" +
                "Email: " + email +
                "\n" +
                "Telefono: " + telefono;
    }
}
