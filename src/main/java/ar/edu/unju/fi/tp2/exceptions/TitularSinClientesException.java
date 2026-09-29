package ar.edu.unju.fi.tp2.exceptions;

public class TitularSinClientesException extends RuntimeException {

    public TitularSinClientesException() {
        super("El titular no tiene clientes asociados.");
    }
}