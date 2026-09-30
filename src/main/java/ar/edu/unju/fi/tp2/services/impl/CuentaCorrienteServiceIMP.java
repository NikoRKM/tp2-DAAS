package ar.edu.unju.fi.tp2.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.tp2.dto.CuentaCorrienteRequestDto;
import ar.edu.unju.fi.tp2.dto.CuentaCorrienteResponseDto;
import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.exceptions.DatoUnicoExistenteException;
import ar.edu.unju.fi.tp2.exceptions.RecursoNoEncontradoException;
import ar.edu.unju.fi.tp2.exceptions.SaldoInsuficienteException;
import ar.edu.unju.fi.tp2.models.Cliente;
import ar.edu.unju.fi.tp2.models.CuentaCorriente;
import ar.edu.unju.fi.tp2.repositories.ClienteRepository;
import ar.edu.unju.fi.tp2.repositories.CuentaCorrienteRepository;
import ar.edu.unju.fi.tp2.services.ICuentaCorrienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaCorrienteServiceIMP implements ICuentaCorrienteService {

	private final CuentaCorrienteRepository cuentaCorrienteRepository;
    
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public CuentaCorrienteResponseDto saveCuentaCorriente(CuentaCorrienteRequestDto cuentaCorrienteDto) {
    	Cliente cliente = findCliente(cuentaCorrienteDto.getCliente());
    	
    	verificarAtributosUnicos(cuentaCorrienteDto);
    	
    	CuentaCorriente cuentaCorriente = new CuentaCorriente();
    	cuentaCorriente.setCbu(cuentaCorrienteDto.getCbu());
    	cuentaCorriente.setAlias(cuentaCorrienteDto.getAlias());
    	cuentaCorriente.setSaldo(cuentaCorrienteDto.getSaldo());
    	cuentaCorriente.setEstadoCuenta(cuentaCorrienteDto.getEstadoCuenta());
    	cuentaCorriente.setCliente(cliente);
    	cuentaCorriente.setTasaInteresAnual(cuentaCorrienteDto.getTasaInteresAnual());
    	cuentaCorriente.setCupoLimiteMensual(cuentaCorrienteDto.getCupoLimiteMensual());
    	
    	CuentaCorriente savedCuentaCorriente = cuentaCorrienteRepository.save(cuentaCorriente);
    	log.info("Se ha creado la Cuenta Corriente: " + savedCuentaCorriente.getId());
        return mapToResponseDto(savedCuentaCorriente);
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaCorrienteResponseDto findById(UUID id){
    	CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findById(id)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Cuenta Corriente: " + id);
    				return new RecursoNoEncontradoException(id, "Cuenta Corriente");
    			});
    	log.info("Se ha encontrado la Cuenta Corriente: " + cuentaCorriente.getId());
    	return mapToResponseDto(cuentaCorriente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentaCorrienteResponseDto> findAll() {
        return cuentaCorrienteRepository.findAll()
        		.stream()
        		.map(this::mapToResponseDto)
        		.toList();
    }

    @Override
    @Transactional
    public CuentaCorrienteResponseDto updateCuentaCorriente(UUID id, CuentaCorrienteRequestDto cuentaCorrienteDto) {
    	CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findById(id)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Cuenta Corriente: " + id);
    				return new RecursoNoEncontradoException(id, "Cuenta Corriente");
    			});
    	log.info("Se ha encontrado la Cuenta Corriente: " + cuentaCorriente.getId());
   
//    	verificarAtributosUnicos(cuentaCorrienteDto);
    	
        cuentaCorriente.setCbu(cuentaCorrienteDto.getCbu());
        cuentaCorriente.setAlias(cuentaCorrienteDto.getAlias());
        cuentaCorriente.setSaldo(cuentaCorrienteDto.getSaldo());
        cuentaCorriente.setEstadoCuenta(cuentaCorrienteDto.getEstadoCuenta());
    	cuentaCorriente.setTasaInteresAnual(cuentaCorrienteDto.getTasaInteresAnual());
    	cuentaCorriente.setCupoLimiteMensual(cuentaCorrienteDto.getCupoLimiteMensual());

        CuentaCorriente updatedTransaccion = cuentaCorrienteRepository.save(cuentaCorriente);
    	log.info("Se ha actualizado la Cuenta Corriente: " + id);
        return mapToResponseDto(updatedTransaccion);
    }

    @Override
    @Transactional
    public CuentaCorrienteResponseDto eliminarPorId(UUID id) {
    	CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findById(id)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Cuenta Corriente: " + id);
    				return new RecursoNoEncontradoException(id, "Cuenta Corriente");
    			});
    	log.info("Se ha encontrado la Cuenta Corriente: " + cuentaCorriente.getId());
    	
    	cuentaCorrienteRepository.delete(cuentaCorriente);
    	
    	log.info("Se ha borrado la Cuenta Corriente: " + id);
        return mapToResponseDto(cuentaCorriente);
    }

    @Override
    public CuentaCorrienteResponseDto findByCbu(Long cbu) {
    	CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findByCbu(cbu)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Cuenta Corriente: " + cbu);
    				return new RecursoNoEncontradoException(cbu, "Cuenta Corriente");
    			});
    	log.info("Se ha encontrado la Cuenta Corriente: " + cuentaCorriente.getCbu());
    	return mapToResponseDto(cuentaCorriente);
    }

    @Override
    public List<CuentaCorrienteResponseDto> findByEstadoCuenta(EstadoCuenta estadoCuenta) {
        return cuentaCorrienteRepository.findByEstadoCuenta(estadoCuenta)
        		.stream()
        		.map(this::mapToResponseDto)
        		.toList();
    }
    
    @Override
    @Transactional
    public void ingresarSaldo(Long cbu, BigDecimal saldo) {
    	CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findByCbu(cbu)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Cuenta Corriente: " + cbu);
    				return new RecursoNoEncontradoException(cbu, "Cuenta Corriente");
    			});
    	log.info("Se ha encontrado la Cuenta Corriente: " + cuentaCorriente.getCbu());
    	
    	BigDecimal nuevoSaldo = cuentaCorriente.getSaldo().add(saldo);
    	cuentaCorriente.setSaldo(nuevoSaldo);
    	log.info("El nuevo saldo de la Cuenta Corriente: " + cbu + " es: $" + nuevoSaldo);
    	
    	cuentaCorrienteRepository.save(cuentaCorriente);
    	log.info("Se ha actualizado el saldo de la Cuenta Corriente: " + cbu);
    }
    
    @Override
    @Transactional
    public void extraerSaldo(Long cbu, BigDecimal saldo) {
    	CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findByCbu(cbu)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Cuenta Corriente: " + cbu);
    				return new RecursoNoEncontradoException(cbu, "Cuenta Corriente");
    			});
    	log.info("Se ha encontrado la Cuenta Corriente: " + cuentaCorriente.getCbu());
    	
    	if (cuentaCorriente.getSaldo().compareTo(saldo) < 0) {			
    		throw new SaldoInsuficienteException();
		}
    	
    	BigDecimal nuevoSaldo = cuentaCorriente.getSaldo().subtract(saldo);
    	cuentaCorriente.setSaldo(nuevoSaldo);
    	log.info("El nuevo saldo de la Cuenta Corriente: " + cbu + " es: $" + nuevoSaldo);
    	
    	cuentaCorrienteRepository.save(cuentaCorriente);
    	log.info("Se ha actualizado el saldo de la Cuenta Corriente: " + cbu);
    }
    
    private CuentaCorrienteResponseDto mapToResponseDto(CuentaCorriente cuentaCorriente) {
    	return CuentaCorrienteResponseDto.builder()
    			.id(cuentaCorriente.getId())
    			.cbu(cuentaCorriente.getCbu())
    			.alias(cuentaCorriente.getAlias())
    			.saldo(cuentaCorriente.getSaldo())
    			.estadoCuenta(cuentaCorriente.getEstadoCuenta())
    			.cliente(cuentaCorriente.getCliente().getId())
    			.tasaInteresAnual(cuentaCorriente.getTasaInteresAnual())
    			.cupoLimiteMensual(cuentaCorriente.getCupoLimiteMensual())
    			.fechaCreacion(cuentaCorriente.getFechaCreacion())
    			.fechaUltimaActualizacion(cuentaCorriente.getFechaUltimaActualizacion())
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
    
    private void verificarAtributosUnicos(CuentaCorrienteRequestDto cuentaCorrienteDto) {
    	if (cuentaCorrienteRepository.existsByCbu(cuentaCorrienteDto.getCbu())) {
			throw new DatoUnicoExistenteException(cuentaCorrienteDto.getCbu(), "Cuenta Corriente");
		}
    	
    	if (cuentaCorrienteRepository.existsByAlias(cuentaCorrienteDto.getAlias())) {
			throw new DatoUnicoExistenteException(cuentaCorrienteDto.getAlias(), "Cuenta Corriente");
		}
    }

    @Override
    public List<CuentaCorrienteResponseDto> findByTasaInteresAnualGreaterThan(BigDecimal tasaInteresAnual) {
        return cuentaCorrienteRepository.findByTasaInteresAnualGreaterThan(tasaInteresAnual)
        		.stream()
        		.map(this::mapToResponseDto)
        		.toList();
    }

    @Override
    public List<CuentaCorrienteResponseDto> findByCupoLimiteMensualLessThan(Integer cupoLimiteMensual) {
        return cuentaCorrienteRepository.findByCupoLimiteMensualLessThan(cupoLimiteMensual)
        		.stream()
        		.map(this::mapToResponseDto)
        		.toList();
    }

}
