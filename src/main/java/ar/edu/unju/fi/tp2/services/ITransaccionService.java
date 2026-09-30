package ar.edu.unju.fi.tp2.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import ar.edu.unju.fi.tp2.dto.TransaccionRequestDto;
import ar.edu.unju.fi.tp2.dto.TransaccionResponseDto;
import ar.edu.unju.fi.tp2.dto.TransferenciaRequestDto;
import ar.edu.unju.fi.tp2.dto.TransferenciaResponseDto;
import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;

public interface ITransaccionService {

    public TransaccionResponseDto saveTransaccion(TransaccionRequestDto transaccionDto);

    public TransaccionResponseDto findById(UUID id);

    public List<TransaccionResponseDto> findAll();

    public TransaccionResponseDto updateTransaccion(UUID id, TransaccionRequestDto transaccionDto);

    public TransaccionResponseDto eliminarPorId(UUID id);

    public List<TransaccionResponseDto> findByFechaHoraBetween(LocalDateTime desde, LocalDateTime hasta);

    public List<TransaccionResponseDto> findByEstadoTransaccion(EstadoTransaccion estadoTransaccion);
    
    public TransferenciaResponseDto realizarTransferenciaEntreCuentas(TransferenciaRequestDto transferenciaDto);

}