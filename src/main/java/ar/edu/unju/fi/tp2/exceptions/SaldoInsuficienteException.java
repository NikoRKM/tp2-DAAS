package ar.edu.unju.fi.tp2.exceptions;

public class SaldoInsuficienteException extends RuntimeException {

	public SaldoInsuficienteException() {
        super("La cuenta no posee saldo suficiente para realizar la operación.");
    }
	
}
