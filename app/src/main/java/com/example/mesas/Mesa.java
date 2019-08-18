package com.example.mesas;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Mesa {
    int id_mesa;
    ArrayList<Silla> sillas;

    public Mesa(int id_mesa,ArrayList<Silla> sillas){
        this.id_mesa=id_mesa;
        this.sillas=sillas;
    }

    public void agregarSilla(Silla silla){
        sillas.add(silla);
    }

    public int getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(int id_mesa) {
        this.id_mesa = id_mesa;
    }

    public ArrayList<Silla> getSillas() {
        return sillas;
    }

    public void setSillas(ArrayList<Silla> sillas) {
        this.sillas = sillas;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "id_mesa=" + id_mesa +
                ", sillas=" + sillas +
                '}';
    }
}
