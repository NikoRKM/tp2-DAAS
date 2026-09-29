package ar.edu.unju.fi.tp2.exceptions;

import java.util.UUID;

public class RecursoNoEncontradoException extends RuntimeException{

	public RecursoNoEncontradoException(UUID id, String recurso) {
        super("No se encontró el recurso: " + recurso + ", con el ID: " + id);
    }
	
	public RecursoNoEncontradoException(String atributo, String recurso) {
        super("No se encontró el recurso: " + recurso + ", con el atributo: " + atributo);
    }
	
	public RecursoNoEncontradoException(Long atributo, String recurso) {
        super("No se encontró el recurso: " + recurso + ", con el atributo: " + atributo);
    }

    public RecursoNoEncontradoException(Long cuil, String recurso) {
        super("No se encontró el recurso: " + recurso + ", con el cuil: " + cuil);
    }

}
