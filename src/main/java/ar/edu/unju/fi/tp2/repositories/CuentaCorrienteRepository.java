package ar.edu.unju.fi.tp2.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.tp2.models.CuentaCorriente;
import ar.edu.unju.fi.tp2.models.CuentaFinanciera;

public interface CuentaCorrienteRepository extends JpaRepository<CuentaCorriente, UUID> {

	public List<CuentaCorriente> findByTasaInteresAnualGreaterThan(BigDecimal tasaInteresAnual);

	public List<CuentaCorriente> findByCupoLimiteMensualLessThan(Integer cupoLimiteMensual);

	public Optional<CuentaFinanciera> findByCbu(Long cbu);

}
