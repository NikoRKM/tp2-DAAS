package ar.edu.unju.fi.tp2.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import ar.edu.unju.fi.tp2.dto.CuentaCorrienteRequestDto;
import ar.edu.unju.fi.tp2.dto.CuentaCorrienteResponseDto;
import ar.edu.unju.fi.tp2.enums.EstadoCuenta;

public interface ICuentaCorrienteService {

	public CuentaCorrienteResponseDto saveCuentaCorriente(CuentaCorrienteRequestDto cuentaCorrienteDto);

    public CuentaCorrienteResponseDto findById(UUID id);

    public List<CuentaCorrienteResponseDto> findAll();

    public CuentaCorrienteResponseDto updateCuentaCorriente(UUID id, CuentaCorrienteRequestDto cuentaCorrienteDto);

    public CuentaCorrienteResponseDto eliminarPorId(UUID id);

    public CuentaCorrienteResponseDto findByCbu(Long cbu);

    public List<CuentaCorrienteResponseDto> findByEstadoCuenta(EstadoCuenta estadoCuenta);
    
    public void ingresarSaldo(Long cbu, BigDecimal saldo);
    
    public void extraerSaldo(Long cbu, BigDecimal saldo);

    public List<CuentaCorrienteResponseDto> findByTasaInteresAnualGreaterThan(BigDecimal tasaInteresAnual);

    public List<CuentaCorrienteResponseDto> findByCupoLimiteMensualLessThan(Integer cupoLimiteMensual);


}
