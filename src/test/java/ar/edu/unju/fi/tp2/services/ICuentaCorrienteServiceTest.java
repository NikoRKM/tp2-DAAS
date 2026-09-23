package ar.edu.unju.fi.tp2.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.models.CuentaCorriente;
import ar.edu.unju.fi.tp2.repositories.CuentaCorrienteRepository;
import ar.edu.unju.fi.tp2.services.impl.CuentaCorrienteServiceIMP;

@ExtendWith(MockitoExtension.class)
class ICuentaCorrienteServiceTest {

	@InjectMocks
	private CuentaCorrienteServiceIMP cuentaCorrienteService;

	@Mock
	private CuentaCorrienteRepository cuentaCorrienteRepository;
	
	private CuentaCorriente cuentaCorriente1;
	private CuentaCorriente cuentaCorriente2;

	@BeforeEach
	void setUp() throws Exception {
		cuentaCorriente1 = new CuentaCorriente();
		cuentaCorriente1.setId(UUID.randomUUID());
		cuentaCorriente1.setCbu(111L);
		cuentaCorriente1.setAlias("test.111.test111");
		cuentaCorriente1.setSaldo(new BigDecimal(111111));
		cuentaCorriente1.setEstadoCuenta(EstadoCuenta.ACTIVA);
		cuentaCorriente1.setTasaInteresAnual(new BigDecimal(111));
		cuentaCorriente1.setCupoLimiteMensual(111);
		
		cuentaCorriente2 = new CuentaCorriente();
		cuentaCorriente2.setId(UUID.randomUUID());
		cuentaCorriente2.setCbu(222L);
		cuentaCorriente2.setAlias("test.222.test222");
		cuentaCorriente2.setSaldo(new BigDecimal(222222));
		cuentaCorriente2.setEstadoCuenta(EstadoCuenta.ACTIVA);
		cuentaCorriente2.setTasaInteresAnual(new BigDecimal(222));
		cuentaCorriente2.setCupoLimiteMensual(222);
	}

	@Test
	public void saveCuentaCorrienteSaved() {
		System.out.println("Test saveCuentaCorrienteSaved");
		
		when(cuentaCorrienteRepository.save(cuentaCorriente1)).thenReturn(cuentaCorriente1);
		
		CuentaCorriente saved = cuentaCorrienteService.saveCuentaCorriente(cuentaCorriente1);
		
		assertNotNull(saved);
		assertEquals(cuentaCorriente1.getId(), saved.getId());
		assertEquals(cuentaCorriente1.getCbu(), saved.getCbu());
		assertEquals(cuentaCorriente1.getAlias(), saved.getAlias());
		verify(cuentaCorrienteRepository, times(1)).save(cuentaCorriente1);
		
		System.out.println("Cuenta Corriente Guardada");
		System.out.println(saved.toString());
	}
	
	@Test
	public void findAllFound() {
		System.out.println("Test findAllFound");
		
		List<CuentaCorriente> cuentaCorrientes = List.of(cuentaCorriente1, cuentaCorriente2);
		
		when(cuentaCorrienteRepository.findAll()).thenReturn(cuentaCorrientes);
		
		List<CuentaCorriente> found = cuentaCorrienteService.findAll();
		
		assertNotNull(found);
		assertEquals(2, found.size());
		assertEquals(cuentaCorriente1, found.get(0));
		assertEquals(cuentaCorriente2, found.get(1));
		verify(cuentaCorrienteRepository, times(1)).findAll();
		
		System.out.println("Cuentas Corrientes Encontradas");
		for (CuentaCorriente cuentaCorriente : found) {
			System.out.println(cuentaCorriente.toString());
		}
	}
	
	@Test
	public void updateCuentaCorrienteUpdated() {
		System.out.println("Test updateCuentaCorrienteUpdated");
		
		UUID id = cuentaCorriente1.getId();
		
		CuentaCorriente cuentaCorrienteUpdated = new CuentaCorriente();
		cuentaCorrienteUpdated.setCbu(111L);
		cuentaCorrienteUpdated.setAlias("test.111.test111");
		cuentaCorrienteUpdated.setSaldo(new BigDecimal(0));
		cuentaCorrienteUpdated.setEstadoCuenta(EstadoCuenta.BLOQUEADA);
		cuentaCorrienteUpdated.setTasaInteresAnual(new BigDecimal(0));
		cuentaCorrienteUpdated.setCupoLimiteMensual(0);
		
		when(cuentaCorrienteRepository.findById(id)).thenReturn(Optional.of(cuentaCorriente1));
		when(cuentaCorrienteRepository.save(any(CuentaCorriente.class))).then(invocation -> invocation.getArgument(0));
		
		Optional<CuentaCorriente> updated = cuentaCorrienteService.updateCuentaCorriente(id, cuentaCorrienteUpdated);
		
		assertTrue(updated.isPresent());
		assertEquals(new BigDecimal(0), updated.get().getSaldo());
		assertEquals(EstadoCuenta.BLOQUEADA, updated.get().getEstadoCuenta());
		assertEquals(new BigDecimal(0), updated.get().getTasaInteresAnual());
		assertEquals(0, updated.get().getCupoLimiteMensual());
		verify(cuentaCorrienteRepository, times(1)).findById(id);
		verify(cuentaCorrienteRepository, times(1)).save(cuentaCorriente1);
		
		System.out.println("Cuenta Corriente Actualizada");
		System.out.println(updated.get().toString());
	}
	
	@Test
	public void updateCuentaCorrienteNotUpdated() {
		System.out.println("Test updateCuentaCorrienteNotUpdated");
		
		UUID id = UUID.randomUUID();
		
		when(cuentaCorrienteRepository.findById(id)).thenReturn(Optional.empty());
		
		Optional<CuentaCorriente> updated = cuentaCorrienteService.updateCuentaCorriente(id, cuentaCorriente1);
		
		assertTrue(updated.isEmpty());
		verify(cuentaCorrienteRepository, times(1)).findById(id);
		verify(cuentaCorrienteRepository, never()).save(any(CuentaCorriente.class));
		
		System.out.println("Cuenta Corriente No Actualizada");
	}
	
	@Test
	public void eliminarPorIdEliminada() {
		System.out.println("Test eliminarPorIdEliminada");
		
		UUID id = cuentaCorriente1.getId();
		
		when(cuentaCorrienteRepository.findById(id)).thenReturn(Optional.of(cuentaCorriente1));
		
		Optional<CuentaCorriente> deleted = cuentaCorrienteService.eliminarPorId(id);
		
		assertTrue(deleted.isPresent());
		verify(cuentaCorrienteRepository, times(1)).findById(id);
		verify(cuentaCorrienteRepository, times(1)).delete(cuentaCorriente1);
		
		System.out.println("Cuenta Corriente Eliminada");
		System.out.println(deleted.get().toString());
	}
	
	@Test
	public void eliminarPorIdNoEliminada() {
		System.out.println("Test updateCuentaCorrienteNoEliminada");
		
		UUID id = UUID.randomUUID();
		
		when(cuentaCorrienteRepository.findById(id)).thenReturn(Optional.empty());
		
		Optional<CuentaCorriente> deleted = cuentaCorrienteService.eliminarPorId(id);
		
		assertTrue(deleted.isEmpty());
		verify(cuentaCorrienteRepository, times(1)).findById(id);
		verify(cuentaCorrienteRepository, never()).delete(any(CuentaCorriente.class));
		
		System.out.println("Cuenta Corriente No Eliminada");
	}

}
