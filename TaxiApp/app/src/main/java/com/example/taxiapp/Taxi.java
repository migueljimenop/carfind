package com.example.taxiapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Taxi implements Parcelable {
    private String id;
    private String patente;
    private int capacidad;
    private String modelo;
    private String tipo;
    private String color;
    private String estado;

    public Taxi() {
    }

    public Taxi(String patente, int capacidad, String modelo, String tipo, String color, String estado) {
        this.patente = patente;
        this.capacidad = capacidad;
        this.modelo = modelo;
        this.tipo = tipo;
        this.color = color;
        this.estado = estado;
    }

    public Taxi(String id, String patente, int capacidad, String modelo, String tipo, String color, String estado) {
        this.id = id;
        this.patente = patente;
        this.capacidad = capacidad;
        this.modelo = modelo;
        this.tipo = tipo;
        this.color = color;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.patente);
        dest.writeInt(this.capacidad);
        dest.writeString(this.modelo);
        dest.writeString(this.tipo);
        dest.writeString(this.color);
        dest.writeString(this.estado);
    }

    protected Taxi(Parcel in) {
        this.id = in.readString();
        this.patente = in.readString();
        this.capacidad = in.readInt();
        this.modelo = in.readString();
        this.tipo = in.readString();
        this.color = in.readString();
        this.estado = in.readString();
    }

    public static final Parcelable.Creator<Taxi> CREATOR = new Parcelable.Creator<Taxi>() {
        @Override
        public Taxi createFromParcel(Parcel source) {
            return new Taxi(source);
        }

        @Override
        public Taxi[] newArray(int size) {
            return new Taxi[size];
        }
    };

    @Override
    public String toString() {
        return "Taxi{" +
                "id='" + id + '\'' +
                ", patente='" + patente + '\'' +
                ", capacidad=" + capacidad +
                ", modelo='" + modelo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", color='" + color + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
