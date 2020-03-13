package com.example.taxiapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Viaje implements Parcelable {
    private String id;
    private String origen;
    private String destino;
    private int distancia;
    private int tiempo;
    private int tarifa;

    public Viaje() {
    }

    public Viaje(String origen, String destino, int distancia, int tiempo, int tarifa) {
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
        this.tiempo = tiempo;
        this.tarifa = tarifa;
    }

    public Viaje(String id, String origen, String destino, int distancia, int tiempo, int tarifa) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
        this.tiempo = tiempo;
        this.tarifa = tarifa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getTarifa() {
        return tarifa;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.origen);
        dest.writeString(this.destino);
        dest.writeInt(this.distancia);
        dest.writeInt(this.tiempo);
        dest.writeInt(this.tarifa);
    }

    protected Viaje(Parcel in) {
        this.id = in.readString();
        this.origen = in.readString();
        this.destino = in.readString();
        this.distancia = in.readInt();
        this.tiempo = in.readInt();
        this.tarifa = in.readInt();
    }

    public static final Creator<Viaje> CREATOR = new Creator<Viaje>() {
        @Override
        public Viaje createFromParcel(Parcel source) {
            return new Viaje(source);
        }

        @Override
        public Viaje[] newArray(int size) {
            return new Viaje[size];
        }
    };

    @Override
    public String toString() {
        return "Id: " + id +
                "\n" +
                "Origen: " + origen +
                "\n" +
                "Destino: " + destino +
                "\n" +
                "Tarifa: " + tarifa;
    }
}
