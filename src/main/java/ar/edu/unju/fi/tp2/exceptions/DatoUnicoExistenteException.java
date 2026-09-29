package ar.edu.unju.fi.tp2.exceptions;

public class DatoUnicoExistenteException extends RuntimeException {

	public DatoUnicoExistenteException(String atributo, String recurso) {
        super("Ya existe el recurso: " + recurso + ", con el atributo: " + atributo);
    }
	
	public DatoUnicoExistenteException(Long atributo, String recurso) {
        super("Ya existe el recurso: " + recurso + ", con el atributo: " + atributo);
    }
	
}
