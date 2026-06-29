public class HistorialMultas {

       private Nodomulta raiz;
       public HistorialMultas(){
           raiz=null;
       }

    private void  insertar(Nodomulta actual, Multas multa){
        if(multa.getId()<actual.getMulta().getId()){
            if(actual.getIzq()==null)//no hay rama
            {
                actual.setIzq(new Nodomulta(multa));
            }else{ //hay rama
                insertar(actual.getIzq(),multa);
            }
        }else{
            if(actual.getDer()==null){
                actual.setDer(new Nodomulta(multa));
            }else{
                insertar(actual.getDer(),multa);
            }


        }
    }

    public void insertar(Multas multa){
        if(raiz==null){
            raiz=new Nodomulta(multa);
        }else{
            insertar(raiz,multa);
        }
    }

    //generar reporte de listas
    public String inOrden(){
        if(raiz==null)
            return "No hay datos";
        else
            return inOrden(raiz);
    }

    public String inOrden(Nodomulta actual){
        if(actual!=null){
            return inOrden(actual.getIzq())+actual.getMulta().toString()
                    +inOrden(actual.getDer());
        }
        return " ";
    }

   public  boolean actualizar( int id,double nprecio,String nmotivo,boolean cambiarp,boolean cambiarm){
           return  actualizar(raiz,id,nprecio,nmotivo,cambiarp,cambiarm);

   }

   private boolean actualizar(Nodomulta actual,int id,double nprecio,String nmotivo,boolean cambiarp,boolean cambiarm){
           if (actual==null){
               return false;
           }
           if (id==actual.getMulta().getId()){
               if (cambiarp && cambiarm){
                   actual.getMulta().setPrecio(nprecio);
                   actual.getMulta().setMotivo(nmotivo);
               }
                else if (cambiarm){
                   actual.getMulta().setMotivo(nmotivo);
               }else  if(cambiarp){
                   actual.getMulta().setPrecio(nprecio);

               }


               return true;
           }

           if (id<actual.getMulta().getId()){
               return actualizar(actual.getIzq(),id,nprecio,nmotivo,cambiarp,cambiarm);
           }else {
               return actualizar(actual.getDer(),id,nprecio,nmotivo,cambiarp,cambiarm);
           }



   }

    //Eliminar un nodo de la lista
    public void eliminar( int id){
        raiz=eliminar(raiz,id);
    }

    private Nodomulta eliminar(Nodomulta actual, int id){
       if (actual==null){
           return null;
       }
       if (id<actual.getMulta().getId()){
           actual.setIzq(eliminar(actual.getIzq(),id));
       } else if (id>actual.getMulta().getId()) {
           actual.setDer(eliminar(actual.getDer(),id));

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

              Nodomulta suce=encontrarMinimo(actual.getDer());
              actual.getMulta().setPrecio(suce.getMulta().getPrecio());
              actual.getMulta().setMotivo(suce.getMulta().getMotivo());
              actual.getMulta().setId(suce.getMulta().getId());

              actual.setDer(eliminar(actual.getDer(),suce.getMulta().getId()));

       }
       return actual;


    }
    private Nodomulta encontrarMinimo(Nodomulta actual){

        while (actual.getIzq() != null) {
            actual = actual.getIzq();
        }

        return actual;
    }





}
