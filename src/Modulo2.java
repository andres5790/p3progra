import java.util.Queue;

public class Modulo2 {
    private Queue<Tramite> tramites;

    public Modulo2(Queue<Tramite> tramites) {
        this.tramites = tramites;
    }

    public boolean registrartramite(Tramite tramite){
        if (tramites.isEmpty()){
            tramites.add(tramite);
            return true;
        }
        for (Tramite t : tramites) {
            if (t.getCiudadano().getId()==tramite.getCiudadano().getId()
                    && t.getTipo()==tramite.getTipo()
                    && t.getEstado() != Estado.FINALIZADO) {


                return false;
            }

        }
        tramites.add(tramite);
        return true;


    }
     //en la ventana le llamas a ciudadano para que se imprima los datos del vehiculo
    public  Tramite consulta(){
        return tramites.peek();
    }

    public  void estadotramite(){
        if(!tramites.isEmpty()){

            Tramite tramite = tramites.peek();
            tramite.setEstado(Estado.ENPROCESO);

        }
    }

    public  void  finalizartramite(){
        Tramite tramite=tramites.poll();
        if(tramite != null){
            tramite.setEstado(Estado.FINALIZADO);
        }


    }
}
