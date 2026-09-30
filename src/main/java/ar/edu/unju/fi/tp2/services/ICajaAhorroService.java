package ar.edu.unju.fi.tp2.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import ar.edu.unju.fi.tp2.dto.CajaAhorroRequestDto;
import ar.edu.unju.fi.tp2.dto.CajaAhorroResponseDto;
import ar.edu.unju.fi.tp2.enums.EstadoCuenta;

public interface ICajaAhorroService {

    public CajaAhorroResponseDto saveCajaAhorro(CajaAhorroRequestDto cajaAhorroDto);

    public CajaAhorroResponseDto findById(UUID id);

    public List<CajaAhorroResponseDto> findAll();

    public CajaAhorroResponseDto updateCajaAhorro(UUID id, CajaAhorroRequestDto cajaAhorroDto);

    public CajaAhorroResponseDto eliminarPorId(UUID id);

    public CajaAhorroResponseDto findByCbu(Long cbu);

    public List<CajaAhorroResponseDto> findByEstadoCuenta(EstadoCuenta estadoCuenta);
    
    public void ingresarSaldo(Long cbu, BigDecimal saldo);
    
    public void extraerSaldo(Long cbu, BigDecimal saldo);

    public List<CajaAhorroResponseDto> findByMargenDescuentoGreaterThan(BigDecimal margenDescuento);

    public List<CajaAhorroResponseDto> findByComisionMantenimientoMensualLessThan(BigDecimal comisionMantenimientoMensual);
}
