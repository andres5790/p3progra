public class RegistroVehicular {

    private Nodovehiculo raiz;
    public RegistroVehicular(){
        raiz=null;
    }

    private boolean  insertar(Nodovehiculo actual, Vehiculo vehiculo){
        if (vehiculo.getPlaca().equals(actual.getVehiculo().getPlaca())) {
            return false;
        }

        if(vehiculo.getPlaca().compareTo(actual.getVehiculo().getPlaca())<0){
            if(actual.getIzq()==null)//no hay rama
            {
                actual.setIzq(new Nodovehiculo(vehiculo));
                return true;
            }else{ //hay rama
                return insertar(actual.getIzq(),vehiculo);
            }
        }else{
            if(actual.getDer()==null){
                actual.setDer(new Nodovehiculo(vehiculo));
                return true;
            }else{
                return insertar(actual.getDer(),vehiculo);
            }


        }
    }

    public void insertar(Vehiculo vehiculo){
        if(raiz==null){
            raiz=new Nodovehiculo(vehiculo);
        }else{
            insertar(raiz,vehiculo);
        }
    }

    //generar reporte de listas
    public String inOrden(){
        if(raiz==null)
            return "No hay datos";
        else
            return inOrden(raiz);
    }

    public String inOrden(Nodovehiculo actual){
        if(actual!=null){
            return inOrden(actual.getIzq())+actual.getVehiculo().toString()
                    +inOrden(actual.getDer());
        }
        return " ";
    }

    public  boolean actualizar( String placa, Ciudadano npropietario,int ntotalmultas,boolean cambiarpro,boolean cambiartm){
        return  actualizar(raiz,placa,npropietario,ntotalmultas,cambiarpro,cambiartm);

    }

    private boolean actualizar(Nodovehiculo actual,String placa, Ciudadano npropietario,int ntotalmultas,boolean cambiarpro,boolean cambiartm){
        if (actual==null){
            return false;
        }
        if (placa.compareTo(actual.getVehiculo().getPlaca())==0){
            if (cambiarpro && cambiartm){
                actual.getVehiculo().setPropietario(npropietario);
                actual.getVehiculo().setTotalmultas(ntotalmultas);
            }
            else if (cambiartm){
                actual.getVehiculo().setTotalmultas(ntotalmultas);
            }else  if(cambiarpro){
                actual.getVehiculo().setPropietario(npropietario);

            }
            if (!cambiarpro && !cambiartm) {
                return false;
            }


            return true;
        }

        if (placa.compareTo(actual.getVehiculo().getPlaca())<0){
            return actualizar(actual.getIzq(),placa,npropietario,ntotalmultas,cambiarpro,cambiartm);
        }else {
            return actualizar(actual.getDer(),placa,npropietario,ntotalmultas,cambiarpro,cambiartm);
        }



    }

    //Eliminar un nodo de la lista
    public void eliminar( String placa){
        raiz=eliminar(raiz,placa);
    }

    private Nodovehiculo eliminar(Nodovehiculo actual, String placa){
        if (actual==null){
            return null;
        }
        if (placa.compareTo(actual.getVehiculo().getPlaca())<0){
            actual.setIzq(eliminar(actual.getIzq(),placa));
        } else if (placa.compareTo(actual.getVehiculo().getPlaca())>0) {
            actual.setDer(eliminar(actual.getDer(),placa));

        }else {
            if (actual.getIzq()==null && actual.getDer()==null){
                return null;
            }

            if (actual.getDer()== null){
                return actual.getIzq();
            }
            if (actual.getIzq()==null){
                return actual.getDer();
            }

            Nodovehiculo suce=encontrarMinimo(actual.getDer());
            actual.getVehiculo().setPlaca(suce.getVehiculo().getPlaca());
            actual.getVehiculo().setPropietario(suce.getVehiculo().getPropietario());
            actual.getVehiculo().setTotalmultas(suce.getVehiculo().getTotalmultas());
            actual.getVehiculo().setAnio(suce.getVehiculo().getAnio());


            actual.setDer(eliminar(actual.getDer(),suce.getVehiculo().getPlaca()));

        }
        return actual;


    }
    private Nodovehiculo encontrarMinimo(Nodovehiculo actual){

        while (actual.getIzq() != null) {
            actual = actual.getIzq();
        }

        return actual;
    }





}

