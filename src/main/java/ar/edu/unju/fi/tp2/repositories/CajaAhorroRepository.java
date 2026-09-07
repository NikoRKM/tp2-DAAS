package ar.edu.unju.fi.tp2.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.tp2.models.CajaAhorro;

public interface CajaAhorroRepository extends JpaRepository<CajaAhorro, Long> {

	public List<CajaAhorro> findByMargenDescuentoGreaterThan(BigDecimal margenDescuento);
	
	public List<CajaAhorro> findByComisionMantenimientoMensualLessThan(BigDecimal comisionMantenimientoMensual);
	
}
