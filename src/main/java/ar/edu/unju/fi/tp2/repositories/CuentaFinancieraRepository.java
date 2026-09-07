package ar.edu.unju.fi.tp2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.models.CuentaFinanciera;

public interface CuentaFinancieraRepository extends JpaRepository<CuentaFinanciera, Long> {

	public Optional<CuentaFinanciera> findByCbu(Long cbu);
	
	public List<CuentaFinanciera> findByEstadoCuenta(EstadoCuenta estadoCuenta);
	
}
