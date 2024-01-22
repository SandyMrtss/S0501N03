package cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.exceptions;

public class DuplicatedNameException extends ClientExceptions{
    public DuplicatedNameException(){
        super("Ja existeix una flor amb aquest nom");
    }
}
