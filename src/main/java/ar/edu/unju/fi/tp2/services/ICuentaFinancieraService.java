package ar.edu.unju.fi.tp2.services;

import java.util.List;
import java.util.UUID;

import ar.edu.unju.fi.tp2.dto.CuentaFinancieraRequestDto;
import ar.edu.unju.fi.tp2.dto.CuentaFinancieraResponseDto;
import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.models.CuentaFinanciera;

public interface ICuentaFinancieraService {

    public CuentaFinancieraResponseDto saveCuentaFinanciera(CuentaFinancieraRequestDto cuentaFinancieraDto);

    public CuentaFinanciera findById(UUID id);

    public List<CuentaFinancieraResponseDto> findAll();

    public CuentaFinancieraResponseDto updateCuentaFinanciera(UUID id, CuentaFinancieraRequestDto cuentaFinancieraDto);

    public CuentaFinancieraResponseDto eliminarPorId(UUID id);

    public CuentaFinancieraResponseDto findByCbu(Long cbu);

    public List<CuentaFinancieraResponseDto> findByEstadoCuenta(EstadoCuenta estadoCuenta);

}