import java.util.ArrayList;

public class ModuloCiudadanos {

        private ArrayList<Ciudadano> ciudadanos;

        public ModuloCiudadanos() {
            ciudadanos = new ArrayList<>();
        }

    public boolean registrarCiudadano(Ciudadano ciudadano){

        for (Ciudadano c : ciudadanos) {
            if (c.getCedula()==ciudadano.getCedula()) {
                return false;
            }
        }

        ciudadanos.add(ciudadano);
        return true;
    }

    public ArrayList<Ciudadano> getCiudadanos() {
        return ciudadanos;
    }
    public Ciudadano buscarCedula(int id){

        for (Ciudadano c : ciudadanos){
            if(c.getCedula()==id){
                return c;
            }
        }

        return null;
    }

}
