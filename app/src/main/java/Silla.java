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
}
