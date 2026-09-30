package ar.edu.unju.fi.tp2.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.models.CajaAhorro;

public interface CajaAhorroRepository extends JpaRepository<CajaAhorro, UUID> {

	public Optional<CajaAhorro> findByCbu(Long cbu);
	
	public List<CajaAhorro> findByEstadoCuenta(EstadoCuenta estadoCuenta);
	
	public boolean existsByCbu(Long cbu);
	
	public boolean existsByAlias(String alias);
	
	public List<CajaAhorro> findByMargenDescuentoGreaterThan(BigDecimal margenDescuento);
	
	public List<CajaAhorro> findByComisionMantenimientoMensualLessThan(BigDecimal comisionMantenimientoMensual);
	
}
