import java.util.List;

public class Mesa {
    int id_mesa;
    List<Silla> sillas;

    public Mesa(int id_mesa,List<Silla> sillas){
        this.id_mesa=id_mesa;
        this.sillas=sillas;
    }

    public void agregarSilla(Silla silla){
        sillas.add(silla);
    }

}
