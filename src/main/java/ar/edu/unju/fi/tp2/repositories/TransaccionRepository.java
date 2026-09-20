package ar.edu.unju.fi.tp2.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;
import ar.edu.unju.fi.tp2.models.Transaccion;

public interface TransaccionRepository extends JpaRepository<Transaccion, UUID> {

	public List<Transaccion> findByFechaHoraBetween(LocalDateTime desde, LocalDateTime hasta);
	
	public List<Transaccion> findByEstadoTransaccion(EstadoTransaccion estadoTransaccion);
	
}
