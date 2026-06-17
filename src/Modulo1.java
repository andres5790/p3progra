import java.util.Stack;

public class Modulo1 {
    private Stack<Pago> pagos;

    public Modulo1(Stack<Pago> pagos) {
        this.pagos = pagos;
    }

    public boolean registrarpagos(Pago pago){
        if (pagos.isEmpty()){
            pagos.push(pago);
            return true;
        }else{
            if (pago.getIdp()>pagos.getLast().getIdp()){
                pagos.push(pago);
                return true;
            }
            return false;
        }
    }

    public Pago consulta(){
        return pagos.peek();
    }

    public  void estadodeuda(){
         Pago pago=pagos.peek();
         Ciudadano ciudadano=pago.getCiudadano();
         ciudadano.setDeudat(ciudadano.getDeudat()- pago.getCantidad());
    }

    public  void anularpago(){
        Pago pago=pagos.peek();
        Ciudadano ciudadano=pago.getCiudadano();
        ciudadano.setDeudat(ciudadano.getDeudat()+ pago.getCantidad());
        pagos.pop();
    }
}
