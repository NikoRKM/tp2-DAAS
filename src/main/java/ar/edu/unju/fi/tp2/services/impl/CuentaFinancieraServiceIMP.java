package ar.edu.unju.fi.tp2.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.tp2.dto.CuentaFinancieraRequestDto;
import ar.edu.unju.fi.tp2.dto.CuentaFinancieraResponseDto;
import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.exceptions.DatoUnicoExistenteException;
import ar.edu.unju.fi.tp2.exceptions.RecursoNoEncontradoException;
import ar.edu.unju.fi.tp2.exceptions.SaldoInsuficienteException;
import ar.edu.unju.fi.tp2.models.Cliente;
import ar.edu.unju.fi.tp2.models.CuentaFinanciera;
import ar.edu.unju.fi.tp2.repositories.ClienteRepository;
import ar.edu.unju.fi.tp2.repositories.CuentaFinancieraRepository;
import ar.edu.unju.fi.tp2.services.ICuentaFinancieraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaFinancieraServiceIMP implements ICuentaFinancieraService {

    private final CuentaFinancieraRepository cuentaFinancieraRepository;
    
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public CuentaFinancieraResponseDto saveCuentaFinanciera(CuentaFinancieraRequestDto cuentaFinancieraDto) {
    	Cliente cliente = findCliente(cuentaFinancieraDto.getCliente());
    	
    	verificarAtributosUnicos(cuentaFinancieraDto);
    	
    	CuentaFinanciera cuentaFinanciera = CuentaFinanciera.builder()
    			.cbu(cuentaFinancieraDto.getCbu())
    			.alias(cuentaFinancieraDto.getAlias())
    			.saldo(cuentaFinancieraDto.getSaldo())
    			.estadoCuenta(cuentaFinancieraDto.getEstadoCuenta())
    			.cliente(cliente)
    			.build();
    	
    	CuentaFinanciera savedCuentaFinanciera = cuentaFinancieraRepository.save(cuentaFinanciera);
    	log.info("Se ha creado la Cuenta Financiera: " + savedCuentaFinanciera.getId());
        return mapToResponseDto(savedCuentaFinanciera);
    }

    @Override
    @Transactional(readOnly = true)
    public CuentaFinancieraResponseDto findById(UUID id){
    	CuentaFinanciera cuentaFinanciera = cuentaFinancieraRepository.findById(id)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Cuenta Financiera: " + id);
    				return new RecursoNoEncontradoException(id, "Cuenta Financiera");
    			});
    	log.info("Se ha encontrado la Cuenta Financiera: " + cuentaFinanciera.getId());
    	return mapToResponseDto(cuentaFinanciera);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuentaFinancieraResponseDto> findAll() {
        return cuentaFinancieraRepository.findAll()
        		.stream()
        		.map(this::mapToResponseDto)
        		.toList();
    }

    @Override
    @Transactional
    public CuentaFinancieraResponseDto updateCuentaFinanciera(UUID id, CuentaFinancieraRequestDto cuentaFinancieraDto) {
    	CuentaFinanciera cuentaFinanciera = cuentaFinancieraRepository.findById(id)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Cuenta Financiera: " + id);
    				return new RecursoNoEncontradoException(id, "Cuenta Financiera");
    			});
    	log.info("Se ha encontrado la Cuenta Financiera: " + cuentaFinanciera.getId());
   
//    	verificarAtributosUnicos(cuentaFinancieraDto);
    	
        cuentaFinanciera.setCbu(cuentaFinancieraDto.getCbu());
        cuentaFinanciera.setAlias(cuentaFinancieraDto.getAlias());
        cuentaFinanciera.setSaldo(cuentaFinancieraDto.getSaldo());
        cuentaFinanciera.setEstadoCuenta(cuentaFinancieraDto.getEstadoCuenta());

        CuentaFinanciera updatedTransaccion = cuentaFinancieraRepository.save(cuentaFinanciera);
    	log.info("Se ha actualizado la Cuenta Financiera: " + id);
        return mapToResponseDto(updatedTransaccion);
    }

    @Override
    @Transactional
    public CuentaFinancieraResponseDto eliminarPorId(UUID id) {
    	CuentaFinanciera cuentaFinanciera = cuentaFinancieraRepository.findById(id)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Cuenta Financiera: " + id);
    				return new RecursoNoEncontradoException(id, "Cuenta Financiera");
    			});
    	log.info("Se ha encontrado la Cuenta Financiera: " + cuentaFinanciera.getId());
    	
    	cuentaFinancieraRepository.delete(cuentaFinanciera);
    	
    	log.info("Se ha borrado la Cuenta Financiera: " + id);
        return mapToResponseDto(cuentaFinanciera);
    }

    @Override
    public CuentaFinancieraResponseDto findByCbu(Long cbu) {
    	CuentaFinanciera cuentaFinanciera = cuentaFinancieraRepository.findByCbu(cbu)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Cuenta Financiera: " + cbu);
    				return new RecursoNoEncontradoException(cbu, "Cuenta Financiera");
    			});
    	log.info("Se ha encontrado la Cuenta Financiera: " + cuentaFinanciera.getCbu());
    	return mapToResponseDto(cuentaFinanciera);
    }

    @Override
    public List<CuentaFinancieraResponseDto> findByEstadoCuenta(EstadoCuenta estadoCuenta) {
        return cuentaFinancieraRepository.findByEstadoCuenta(estadoCuenta)
        		.stream()
        		.map(this::mapToResponseDto)
        		.toList();
    }
    
    @Override
    @Transactional
    public void ingresarSaldo(Long cbu, BigDecimal saldo) {
    	CuentaFinanciera cuentaFinanciera = cuentaFinancieraRepository.findByCbu(cbu)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Cuenta Financiera: " + cbu);
    				return new RecursoNoEncontradoException(cbu, "Cuenta Financiera");
    			});
    	log.info("Se ha encontrado la Cuenta Financiera: " + cuentaFinanciera.getCbu());
    	
    	BigDecimal nuevoSaldo = cuentaFinanciera.getSaldo().add(saldo);
    	cuentaFinanciera.setSaldo(nuevoSaldo);
    	log.info("El nuevo saldo de la Cuenta Financiera: " + cbu + " es: $" + nuevoSaldo);
    	
    	cuentaFinancieraRepository.save(cuentaFinanciera);
    	log.info("Se ha actualizado el saldo de la Cuenta Financiera: " + cbu);
    }
    
    @Override
    @Transactional
    public void extraerSaldo(Long cbu, BigDecimal saldo) {
    	CuentaFinanciera cuentaFinanciera = cuentaFinancieraRepository.findByCbu(cbu)
    			.orElseThrow(() -> {
    		    	log.info("NO se ha encontrado la Cuenta Financiera: " + cbu);
    				return new RecursoNoEncontradoException(cbu, "Cuenta Financiera");
    			});
    	log.info("Se ha encontrado la Cuenta Financiera: " + cuentaFinanciera.getCbu());
    	
    	if (cuentaFinanciera.getSaldo().compareTo(saldo) < 0) {			
    		throw new SaldoInsuficienteException();
		}
    	
    	BigDecimal nuevoSaldo = cuentaFinanciera.getSaldo().subtract(saldo);
    	cuentaFinanciera.setSaldo(nuevoSaldo);
    	log.info("El nuevo saldo de la Cuenta Financiera: " + cbu + " es: $" + nuevoSaldo);
    	
    	cuentaFinancieraRepository.save(cuentaFinanciera);
    	log.info("Se ha actualizado el saldo de la Cuenta Financiera: " + cbu);
    }
    
    private CuentaFinancieraResponseDto mapToResponseDto(CuentaFinanciera cuentaFinanciera) {
    	return CuentaFinancieraResponseDto.builder()
    			.id(cuentaFinanciera.getId())
    			.cbu(cuentaFinanciera.getCbu())
    			.alias(cuentaFinanciera.getAlias())
    			.saldo(cuentaFinanciera.getSaldo())
    			.estadoCuenta(cuentaFinanciera.getEstadoCuenta())
    			.cliente(cuentaFinanciera.getCliente().getId())
    			.fechaCreacion(cuentaFinanciera.getFechaCreacion())
    			.fechaUltimaActualizacion(cuentaFinanciera.getFechaUltimaActualizacion())
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
    
    private void verificarAtributosUnicos(CuentaFinancieraRequestDto cuentaFinancieraDto) {
    	if (cuentaFinancieraRepository.existsByCbu(cuentaFinancieraDto.getCbu())) {
			throw new DatoUnicoExistenteException(cuentaFinancieraDto.getCbu(), "Cuenta Financiera");
		}
    	
    	if (cuentaFinancieraRepository.existsByAlias(cuentaFinancieraDto.getAlias())) {
			throw new DatoUnicoExistenteException(cuentaFinancieraDto.getAlias(), "Cuenta Financiera");
		}
    }
    
}