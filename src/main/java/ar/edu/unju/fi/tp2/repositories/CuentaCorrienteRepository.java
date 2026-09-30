package ar.edu.unju.fi.tp2.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.models.CuentaCorriente;

public interface CuentaCorrienteRepository extends JpaRepository<CuentaCorriente, UUID> {

	public Optional<CuentaCorriente> findByCbu(Long cbu);
	
	public List<CuentaCorriente> findByEstadoCuenta(EstadoCuenta estadoCuenta);
	
	public boolean existsByCbu(Long cbu);
	
	public boolean existsByAlias(String alias);
	
	public List<CuentaCorriente> findByTasaInteresAnualGreaterThan(BigDecimal tasaInteresAnual);

	public List<CuentaCorriente> findByCupoLimiteMensualLessThan(Integer cupoLimiteMensual);
	
}
