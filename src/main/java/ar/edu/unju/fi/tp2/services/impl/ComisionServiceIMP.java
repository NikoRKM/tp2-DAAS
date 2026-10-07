package ar.edu.unju.fi.tp2.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;
import ar.edu.unju.fi.tp2.enums.TipoTransaccion;
import ar.edu.unju.fi.tp2.models.CajaAhorro;
import ar.edu.unju.fi.tp2.models.CuentaCorriente;
import ar.edu.unju.fi.tp2.models.CuentaFinanciera;
import ar.edu.unju.fi.tp2.models.Transaccion;
import ar.edu.unju.fi.tp2.repositories.CajaAhorroRepository;
import ar.edu.unju.fi.tp2.repositories.CuentaCorrienteRepository;
import ar.edu.unju.fi.tp2.repositories.CuentaFinancieraRepository;
import ar.edu.unju.fi.tp2.repositories.TransaccionRepository;
import ar.edu.unju.fi.tp2.services.IComisionService;
import ar.edu.unju.fi.tp2.services.IConfiguracionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComisionServiceIMP implements IComisionService {
	
	private final TransaccionRepository transaccionRepository;
    
	private final CuentaFinancieraRepository cuentaFinancieraRepository;
	
    private final CuentaCorrienteRepository cuentaCorrienteRepository;
    
    private final CajaAhorroRepository cajaAhorroRepository;
    
    private final IConfiguracionService configuracionService;
    
    @Transactional
    @Override
    public void debitarCostoMantenimiento() {
    	BigDecimal montoCuentaCorriente = configuracionService.obtenerMontoComision("comision.cuenta-corriente");
    	log.info("Para la Comision de las Cuentas Corrientes se consiguio el monto: $" + montoCuentaCorriente);
    	BigDecimal montoCajaAhorro = configuracionService.obtenerMontoComision("comision.caja-ahorro");
    	log.info("Para la Comision de las Cajas de Ahorros se consiguio el monto: $" + montoCajaAhorro);
    	
    	List<CuentaCorriente> cuentasCorrientesActivas = cuentaCorrienteRepository.findByEstadoCuenta(EstadoCuenta.ACTIVA);
    	log.info("Hay " + cuentasCorrientesActivas.size() + " Cuentas Corrientes Activas");
    	List<CajaAhorro> cajasAhorrosActivas = cajaAhorroRepository.findByEstadoCuenta(EstadoCuenta.ACTIVA);
    	log.info("Hay " + cajasAhorrosActivas.size() + " Cajas de Ahorros Activas");

    	for (CuentaCorriente cuentaCorriente : cuentasCorrientesActivas) {
    		cobrarComision(cuentaCorriente, montoCajaAhorro);
    		generarTransaccion(cuentaCorriente, montoCuentaCorriente);
    	}
    	
    	for (CajaAhorro cajaAhorro : cajasAhorrosActivas) {
    		cobrarComision(cajaAhorro, montoCajaAhorro);
    		generarTransaccion(cajaAhorro, montoCuentaCorriente);
		}
    }
    
    private void cobrarComision(CuentaFinanciera cuenta, BigDecimal monto) {
    	if (cuenta.getSaldo().compareTo(monto) < 0) {
    		cuenta.setEstadoCuenta(EstadoCuenta.SUSPENDIDA);
    		log.warn("No hay suficiente saldo para realizar el cobro de la comision, se suspendera la cuenta");
		} else {			
			BigDecimal nuevoSaldo = cuenta.getSaldo().subtract(monto);
			cuenta.setSaldo(nuevoSaldo);
			log.info("El nuevo saldo de la Cuenta Financiera: " + cuenta.getCbu() + " es: $" + nuevoSaldo);
		}
    	
    	cuentaFinancieraRepository.save(cuenta);
    	log.info("Se ha actualizado Cuenta Financiera: " + cuenta.getCbu());
    }
    
    private void generarTransaccion(CuentaFinanciera cuenta, BigDecimal monto) {
    	Transaccion transaccion = Transaccion.builder()
				.fechaHora(LocalDateTime.now())
				.monto(monto)
				.tipoTransaccion(TipoTransaccion.DEBITO_COMISION)
				.cuentaFinanciera(cuenta)
				.build();
    	
    	if (cuenta.getEstadoCuenta().equals(EstadoCuenta.ACTIVA)) {
    		transaccion.setEstadoTransaccion(EstadoTransaccion.COMPLETADA);
    	} else {
    		transaccion.setEstadoTransaccion(EstadoTransaccion.RECHAZADA);
    	}
		
		transaccionRepository.save(transaccion);
		log.info("Transaccion Generada");
    }

}
