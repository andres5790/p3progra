import java.util.ArrayList;

public class ModuloEmpleados {


        private ArrayList<Empleado> empleados;

        public ModuloEmpleados() {
            empleados = new ArrayList<>();
        }


    public boolean registrarEmpleado(Empleado empleado){

        for (Empleado e : empleados) {
            if (e.getId().equals(empleado.getId())) {
                return false;
            }
        }

        empleados.add(empleado);
        return true;
    }


    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }
    public Empleado buscarCodigo(int id){

        for (Empleado e : empleados){
            if(e.getId().equals(id)){
                return e;
            }
        }

        return null;
    }

}
