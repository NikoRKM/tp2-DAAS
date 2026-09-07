package ar.edu.unju.fi.tp2.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.tp2.models.CuentaCorriente;

public interface CuentaCorrienteRepository extends JpaRepository<CuentaCorriente, Long> {

	public List<CuentaCorriente> findByTasaInteresAnualGreaterThan(BigDecimal tasaInteresAnual);
	
	public List<CuentaCorriente> findByCupoLimiteMensualLessThan(Integer cupoLimiteMensual);
	
}
