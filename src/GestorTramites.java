import java.util.Queue;

public class GestorTramites {
    private Queue<Tramite> tramites;

    public GestorTramites(Queue<Tramite> tramites) {
        this.tramites = tramites;
    }

    public boolean registrartramite(Tramite tramite) {

        int tamaño = tramites.size();
        boolean existe = false;

        for (int i = 0; i < tamaño; i++) {

            Tramite t = tramites.poll(); // saco el primero

            if (t.getCiudadano().getCedula() == tramite.getCiudadano().getCedula()
                    && t.getTipo() == tramite.getTipo()
                    && t.getEstado() != Estado.FINALIZADO) {

                existe = true;
            }

            tramites.offer(t); // lo vuelvo a poner al final
        }

        if (existe) {
            return false;
        }

        tramites.offer(tramite);
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
