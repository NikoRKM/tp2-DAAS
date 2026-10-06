package ar.edu.unju.fi.tp2.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;
import ar.edu.unju.fi.tp2.enums.TipoTransaccion;
import ar.edu.unju.fi.tp2.models.Transaccion;

public interface TransaccionRepository extends JpaRepository<Transaccion, UUID> {

	public List<Transaccion> findByFechaHoraBetween(LocalDateTime desde, LocalDateTime hasta);

	public List<Transaccion> findByEstadoTransaccion(EstadoTransaccion estadoTransaccion);

	List<Transaccion> findByCuentaFinancieraClienteIdAndTipoTransaccionAndFechaHoraBetween(
			UUID clienteId,
			TipoTransaccion tipoTransaccion,
			LocalDateTime inicio,
			LocalDateTime fin);

	List<Transaccion> findByAdherenteIdAndTipoTransaccionAndFechaHoraBetween(
			UUID adherenteId,
			TipoTransaccion tipoTransaccion,
			LocalDateTime inicio,
			LocalDateTime fin);
}
