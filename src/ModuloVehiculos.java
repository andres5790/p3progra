import java.util.ArrayList;

public class ModuloVehiculos {


        private ArrayList<Vehiculo> vehiculos;

        public ModuloVehiculos() {
            vehiculos = new ArrayList<>();
        }

    public boolean registrarVehiculo(Vehiculo vehiculo){

        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equals(vehiculo.getPlaca())) {
                return false;
            }
        }

        vehiculos.add(vehiculo);
        return true;
    }
    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }
    public Vehiculo buscarPlaca(String placa){

        for (Vehiculo v : vehiculos){
            if(v.getPlaca().equals(placa)){
                return v;
            }
        }

        return null;
    }

}
