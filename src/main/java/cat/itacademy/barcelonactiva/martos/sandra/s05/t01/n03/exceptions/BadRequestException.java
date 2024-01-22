package cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.exceptions;


public class BadRequestException extends ClientExceptions{
    public BadRequestException(){
        super("El cos de la petició és incorrecte");
    }
}
