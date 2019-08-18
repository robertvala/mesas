package com.example.mesas;

public class Silla {
    int id_silla;
    boolean disponible;
    Mesa mesa;

    public Silla(int id_silla,boolean disponible){
        this.id_silla=id_silla;
        this.disponible=disponible;

    }

    public Silla(int id_silla,boolean disponible,Mesa mesa){
        this.id_silla=id_silla;
        this.disponible=disponible;
        this.mesa=mesa;
    }


    public void agregarAMesa(Mesa m){
        this.mesa=m;
    }

    public int getId_silla() {
        return id_silla;
    }

    public void setId_silla(int id_silla) {
        this.id_silla = id_silla;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    @Override
    public String toString() {
        return "Silla{" +
                "id_silla=" + id_silla +
                ", disponible=" + disponible +
                ", mesa=" + mesa.getId_mesa() +
                '}';
    }
}
