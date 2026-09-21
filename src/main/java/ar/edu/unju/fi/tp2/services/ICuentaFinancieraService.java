package ar.edu.unju.fi.tp2.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ar.edu.unju.fi.tp2.models.CuentaFinanciera;

public interface ICuentaFinancieraService {

    public CuentaFinanciera saveCuentaFinanciera(CuentaFinanciera cuentaFinanciera);

    public Optional<CuentaFinanciera> findById(UUID id) throws Exception;

    public List<CuentaFinanciera> findAll();

    public Optional<CuentaFinanciera> updateCuentaFinanciera(
            UUID id,
            CuentaFinanciera cuentaFinancieraDetalle);

    public Optional<CuentaFinanciera> eliminarPorId(UUID id);

}