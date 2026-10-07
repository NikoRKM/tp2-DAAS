package ar.edu.unju.fi.tp2.events;

import java.util.UUID;

public record ClienteRegistradoEvent(

        UUID clienteId,
        String nombre,
        String email,
        UUID tokenActivacion) {

}
