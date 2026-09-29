package ar.edu.unju.fi.tp2.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.tp2.dto.TransaccionRequestDto;
import ar.edu.unju.fi.tp2.dto.TransaccionResponseDto;
import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;
import ar.edu.unju.fi.tp2.enums.TipoTransaccion;
import ar.edu.unju.fi.tp2.exceptions.RecursoNoEncontradoException;
import ar.edu.unju.fi.tp2.exceptions.SaldoInsuficienteException;
import ar.edu.unju.fi.tp2.models.CuentaFinanciera;
import ar.edu.unju.fi.tp2.models.Transaccion;
import ar.edu.unju.fi.tp2.repositories.TransaccionRepository;
import ar.edu.unju.fi.tp2.services.ICuentaFinancieraService;
import ar.edu.unju.fi.tp2.services.ITransaccionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransaccionServiceIMP implements ITransaccionService {

    private final TransaccionRepository transaccionRepository;
    
    private final ICuentaFinancieraService cuentaFinancieraService;

    @Override
    @Transactional
    public TransaccionResponseDto saveTransaccion(TransaccionRequestDto transaccionDto) {
    	CuentaFinanciera cuentaFinanciera = findCuentaFinanciera(transaccionDto.getCuentaFinanciera());
    	
    	if (transaccionDto.getTipoTransaccion().equals(TipoTransaccion.EXTRACCION)) {
			if (cuentaFinanciera.getSaldo().compareTo(transaccionDto.getMonto()) < 0) {
				log.warn("No hay suficiente saldo para completar la transaccion");
				throw new SaldoInsuficienteException();
			}
		}
    	
    	Transaccion transaccion = Transaccion.builder()
    			.fechaHora(LocalDateTime.now())
    			.monto(transaccionDto.getMonto())
    			.tipoTransaccion(transaccionDto.getTipoTransaccion())
    			.estadoTransaccion(transaccionDto.getEstadoTransaccion())
    			.cuentaFinanciera(cuentaFinanciera)
    			.build();
    	
    	Transaccion savedTransaccion = transaccionRepository.save(transaccion);
    	log.info("Se ha creado la Transaccion: " + savedTransaccion.getId());
        return mapToResponseDto(savedTransaccion);
    }

    @Override
    @Transactional(readOnly = true)
    public TransaccionResponseDto findById(UUID id) {
    	Transaccion transaccion = transaccionRepository.findById(id)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Transaccion: " + id);
    				return new RecursoNoEncontradoException(id, "Transaccion");
    			});
    	log.info("Se ha encontrado la Transaccion: " + transaccion.getId());
    	return mapToResponseDto(transaccion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionResponseDto> findAll() {
        return transaccionRepository.findAll()
        		.stream()
        		.map(this::mapToResponseDto)
        		.toList();
    }

    @Override
    @Transactional
    public TransaccionResponseDto updateTransaccion(UUID id, TransaccionRequestDto transaccionDto) {
    	Transaccion transaccion = transaccionRepository.findById(id)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Transaccion: " + id);
    				return new RecursoNoEncontradoException(id, "Transaccion");
    			});
    	log.info("Se ha encontrado la Transaccion: " + transaccion.getId());
   
        transaccion.setFechaHora(LocalDateTime.now());
        transaccion.setMonto(transaccionDto.getMonto());
        transaccion.setTipoTransaccion(transaccionDto.getTipoTransaccion());
        transaccion.setEstadoTransaccion(transaccionDto.getEstadoTransaccion());

        Transaccion updatedTransaccion = transaccionRepository.save(transaccion);
    	log.info("Se ha actualizado la Transaccion: " + id);
        return mapToResponseDto(updatedTransaccion);
    }

    @Override
    @Transactional
    public TransaccionResponseDto eliminarPorId(UUID id) {
    	Transaccion transaccion = transaccionRepository.findById(id)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Transaccion: " + id);
    				return new RecursoNoEncontradoException(id, "Transaccion");
    			});
    	log.info("Se ha encontrado la Transaccion: " + transaccion.getId());

    	transaccionRepository.delete(transaccion);

    	log.info("Se ha borrado la Transaccion: " + id);
        return mapToResponseDto(transaccion);
    }

    @Override
    public List<TransaccionResponseDto> findByFechaHoraBetween(LocalDateTime desde, LocalDateTime hasta) {
        return transaccionRepository.findByFechaHoraBetween(desde, hasta)
        		.stream()
        		.map(this::mapToResponseDto)
        		.toList();
    }

    @Override
    public List<TransaccionResponseDto> findByEstadoTransaccion(EstadoTransaccion estadoTransaccion) {
        return transaccionRepository.findByEstadoTransaccion(estadoTransaccion)
        		.stream()
        		.map(this::mapToResponseDto)
        		.toList();
    }
    
    private TransaccionResponseDto mapToResponseDto(Transaccion transaccion) {
    	return TransaccionResponseDto.builder()
    			.id(transaccion.getId())
    			.fechaHora(transaccion.getFechaHora())
    			.monto(transaccion.getMonto())
    			.tipoTransaccion(transaccion.getTipoTransaccion())
    			.estadoTransaccion(transaccion.getEstadoTransaccion())
    			.cuentaFinanciera(transaccion.getCuentaFinanciera().getId())
    			.fechaCreacion(transaccion.getFechaCreacion())
    			.fechaUltimaActualizacion(transaccion.getFechaUltimaActualizacion())
    			.build();
    }
    
    private CuentaFinanciera findCuentaFinanciera(UUID id) {
    	return cuentaFinancieraService.findById(id);
    }
    
}