package ar.edu.unju.fi.tp2.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.tp2.dto.CajaAhorroRequestDto;
import ar.edu.unju.fi.tp2.dto.CajaAhorroResponseDto;
import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.exceptions.DatoUnicoExistenteException;
import ar.edu.unju.fi.tp2.exceptions.RecursoNoEncontradoException;
import ar.edu.unju.fi.tp2.exceptions.SaldoInsuficienteException;
import ar.edu.unju.fi.tp2.models.CajaAhorro;
import ar.edu.unju.fi.tp2.models.Cliente;
import ar.edu.unju.fi.tp2.repositories.CajaAhorroRepository;
import ar.edu.unju.fi.tp2.repositories.ClienteRepository;
import ar.edu.unju.fi.tp2.services.ICajaAhorroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CajaAhorroServiceIMP implements ICajaAhorroService {

	private final CajaAhorroRepository cajaAhorroRepository;
    
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public CajaAhorroResponseDto saveCajaAhorro(CajaAhorroRequestDto cajaAhorroDto) {
    	Cliente cliente = findCliente(cajaAhorroDto.getCliente());
    	
    	verificarAtributosUnicos(cajaAhorroDto);
    	
    	CajaAhorro cajaAhorro = new CajaAhorro();
    	cajaAhorro.setCbu(cajaAhorroDto.getCbu());
    	cajaAhorro.setAlias(cajaAhorroDto.getAlias());
    	cajaAhorro.setSaldo(cajaAhorroDto.getSaldo());
    	cajaAhorro.setEstadoCuenta(cajaAhorroDto.getEstadoCuenta());
    	cajaAhorro.setCliente(cliente);
    	cajaAhorro.setMargenDescuento(cajaAhorroDto.getMargenDescuento());
    	cajaAhorro.setComisionMantenimientoMensual(cajaAhorroDto.getComisionMantenimientoMensual());
    	
    	CajaAhorro savedCajaAhorro = cajaAhorroRepository.save(cajaAhorro);
    	log.info("Se ha creado la Caja Ahorro: " + savedCajaAhorro.getId());
        return mapToResponseDto(savedCajaAhorro);
    }

    @Override
    @Transactional(readOnly = true)
    public CajaAhorroResponseDto findById(UUID id){
    	CajaAhorro cajaAhorro = cajaAhorroRepository.findById(id)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Caja Ahorro: " + id);
    				return new RecursoNoEncontradoException(id, "Caja Ahorro");
    			});
    	log.info("Se ha encontrado la Caja Ahorro: " + cajaAhorro.getId());
    	return mapToResponseDto(cajaAhorro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CajaAhorroResponseDto> findAll() {
        return cajaAhorroRepository.findAll()
        		.stream()
        		.map(this::mapToResponseDto)
        		.toList();
    }

    @Override
    @Transactional
    public CajaAhorroResponseDto updateCajaAhorro(UUID id, CajaAhorroRequestDto cajaAhorroDto) {
    	CajaAhorro cajaAhorro = cajaAhorroRepository.findById(id)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Caja Ahorro: " + id);
    				return new RecursoNoEncontradoException(id, "Caja Ahorro");
    			});
    	log.info("Se ha encontrado la Caja Ahorro: " + cajaAhorro.getId());
   
//    	verificarAtributosUnicos(cajaAhorroDto);
    	
        cajaAhorro.setCbu(cajaAhorroDto.getCbu());
        cajaAhorro.setAlias(cajaAhorroDto.getAlias());
        cajaAhorro.setSaldo(cajaAhorroDto.getSaldo());
        cajaAhorro.setEstadoCuenta(cajaAhorroDto.getEstadoCuenta());
    	cajaAhorro.setMargenDescuento(cajaAhorroDto.getMargenDescuento());
    	cajaAhorro.setComisionMantenimientoMensual(cajaAhorroDto.getComisionMantenimientoMensual());

        CajaAhorro updatedTransaccion = cajaAhorroRepository.save(cajaAhorro);
    	log.info("Se ha actualizado la Caja Ahorro: " + id);
        return mapToResponseDto(updatedTransaccion);
    }

    @Override
    @Transactional
    public CajaAhorroResponseDto eliminarPorId(UUID id) {
    	CajaAhorro cajaAhorro = cajaAhorroRepository.findById(id)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Caja Ahorro: " + id);
    				return new RecursoNoEncontradoException(id, "Caja Ahorro");
    			});
    	log.info("Se ha encontrado la Caja Ahorro: " + cajaAhorro.getId());
    	
    	cajaAhorroRepository.delete(cajaAhorro);
    	
    	log.info("Se ha borrado la Caja Ahorro: " + id);
        return mapToResponseDto(cajaAhorro);
    }

    @Override
    public CajaAhorroResponseDto findByCbu(Long cbu) {
    	CajaAhorro cajaAhorro = cajaAhorroRepository.findByCbu(cbu)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Caja Ahorro: " + cbu);
    				return new RecursoNoEncontradoException(cbu, "Caja Ahorro");
    			});
    	log.info("Se ha encontrado la Caja Ahorro: " + cajaAhorro.getCbu());
    	return mapToResponseDto(cajaAhorro);
    }

    @Override
    public List<CajaAhorroResponseDto> findByEstadoCuenta(EstadoCuenta estadoCuenta) {
        return cajaAhorroRepository.findByEstadoCuenta(estadoCuenta)
        		.stream()
        		.map(this::mapToResponseDto)
        		.toList();
    }
    
    @Override
    @Transactional
    public void ingresarSaldo(Long cbu, BigDecimal saldo) {
    	CajaAhorro cajaAhorro = cajaAhorroRepository.findByCbu(cbu)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Caja Ahorro: " + cbu);
    				return new RecursoNoEncontradoException(cbu, "Caja Ahorro");
    			});
    	log.info("Se ha encontrado la Caja Ahorro: " + cajaAhorro.getCbu());
    	
    	BigDecimal nuevoSaldo = cajaAhorro.getSaldo().add(saldo);
    	cajaAhorro.setSaldo(nuevoSaldo);
    	log.info("El nuevo saldo de la Caja Ahorro: " + cbu + " es: $" + nuevoSaldo);
    	
    	cajaAhorroRepository.save(cajaAhorro);
    	log.info("Se ha actualizado el saldo de la Caja Ahorro: " + cbu);
    }
    
    @Override
    @Transactional
    public void extraerSaldo(Long cbu, BigDecimal saldo) {
    	CajaAhorro cajaAhorro = cajaAhorroRepository.findByCbu(cbu)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Caja Ahorro: " + cbu);
    				return new RecursoNoEncontradoException(cbu, "Caja Ahorro");
    			});
    	log.info("Se ha encontrado la Caja Ahorro: " + cajaAhorro.getCbu());
    	
    	if (cajaAhorro.getSaldo().compareTo(saldo) < 0) {			
    		throw new SaldoInsuficienteException();
		}
    	
    	BigDecimal nuevoSaldo = cajaAhorro.getSaldo().subtract(saldo);
    	cajaAhorro.setSaldo(nuevoSaldo);
    	log.info("El nuevo saldo de la Caja Ahorro: " + cbu + " es: $" + nuevoSaldo);
    	
    	cajaAhorroRepository.save(cajaAhorro);
    	log.info("Se ha actualizado el saldo de la Caja Ahorro: " + cbu);
    }
    
    private CajaAhorroResponseDto mapToResponseDto(CajaAhorro cajaAhorro) {
    	return CajaAhorroResponseDto.builder()
    			.id(cajaAhorro.getId())
    			.cbu(cajaAhorro.getCbu())
    			.alias(cajaAhorro.getAlias())
    			.saldo(cajaAhorro.getSaldo())
    			.estadoCuenta(cajaAhorro.getEstadoCuenta())
    			.cliente(cajaAhorro.getCliente().getId())
    			.margenDescuento(cajaAhorro.getMargenDescuento())
    			.comisionMantenimientoMensual(cajaAhorro.getComisionMantenimientoMensual())
    			.fechaCreacion(cajaAhorro.getFechaCreacion())
    			.fechaUltimaActualizacion(cajaAhorro.getFechaUltimaActualizacion())
    			.build();
    }
    
    private Cliente findCliente(UUID id) {
    	Cliente cliente = clienteRepository.findById(id)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado el Cliente: " + id);
    				return new RecursoNoEncontradoException(id, "Cliente");
    			});
    	log.info("Se ha encontrado el Cliente: " + cliente.getId());
    	return cliente;
    }
    
    private void verificarAtributosUnicos(CajaAhorroRequestDto cajaAhorroDto) {
    	if (cajaAhorroRepository.existsByCbu(cajaAhorroDto.getCbu())) {
			throw new DatoUnicoExistenteException(cajaAhorroDto.getCbu(), "Caja Ahorro");
		}
    	
    	if (cajaAhorroRepository.existsByAlias(cajaAhorroDto.getAlias())) {
			throw new DatoUnicoExistenteException(cajaAhorroDto.getAlias(), "Caja Ahorro");
		}
    }

    @Override
    public List<CajaAhorroResponseDto> findByMargenDescuentoGreaterThan(BigDecimal margenDescuento) {
        return cajaAhorroRepository.findByMargenDescuentoGreaterThan(margenDescuento)
        		.stream()
        		.map(this::mapToResponseDto)
        		.toList();
    }

    @Override
    public List<CajaAhorroResponseDto> findByComisionMantenimientoMensualLessThan(BigDecimal comisionMantenimientoMensual) {
        return cajaAhorroRepository.findByComisionMantenimientoMensualLessThan(comisionMantenimientoMensual)
        		.stream()
        		.map(this::mapToResponseDto)
        		.toList();
    }
}