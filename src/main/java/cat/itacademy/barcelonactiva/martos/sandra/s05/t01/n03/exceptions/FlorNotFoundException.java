package cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.exceptions;

public class FlorNotFoundException extends ClientExceptions{
    public FlorNotFoundException(){
        super("No s'ha trobat cap flor amb l'id indicat");
    }
}
