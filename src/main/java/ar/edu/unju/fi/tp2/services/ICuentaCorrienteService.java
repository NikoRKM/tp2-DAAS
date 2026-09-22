package ar.edu.unju.fi.tp2.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ar.edu.unju.fi.tp2.models.CuentaCorriente;
import ar.edu.unju.fi.tp2.models.CuentaFinanciera;

public interface ICuentaCorrienteService {

    public CuentaCorriente saveCuentaCorriente(CuentaCorriente cuentaCorriente);

    public Optional<CuentaCorriente> findById(UUID id) throws Exception;

    public List<CuentaCorriente> findAll();

    public Optional<CuentaCorriente> updateCuentaCorriente(UUID id, CuentaCorriente cuentaCorrienteDetalle);

    public Optional<CuentaCorriente> eliminarPorId(UUID id);

    public List<CuentaCorriente> findByTasaInteresAnualGreaterThan(BigDecimal tasaInteresAnual);

    public List<CuentaCorriente> findByCupoLimiteMensualLessThan(Integer cupoLimiteMensual);

    public Optional<CuentaFinanciera> findByCbu(Long cbu);

}
