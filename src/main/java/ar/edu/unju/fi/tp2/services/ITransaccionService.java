package ar.edu.unju.fi.tp2.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;
import ar.edu.unju.fi.tp2.models.Transaccion;

public interface ITransaccionService {

    public Transaccion saveTransaccion(Transaccion transaccion);

    public Optional<Transaccion> findById(UUID id) throws Exception;

    public List<Transaccion> findAll();

    public Optional<Transaccion> updateTransaccion(
            UUID id,
            Transaccion transaccionDetalle);

    public Optional<Transaccion> eliminarPorId(UUID id);

    public List<Transaccion> findByFechaHoraBetween(LocalDateTime desde, LocalDateTime hasta);

    public List<Transaccion> findByEstadoTransaccion(EstadoTransaccion estadoTransaccion);

}