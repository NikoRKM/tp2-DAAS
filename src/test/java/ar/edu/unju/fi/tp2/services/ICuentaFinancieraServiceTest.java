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
import ar.edu.unju.fi.tp2.models.CuentaFinanciera;
import ar.edu.unju.fi.tp2.repositories.CuentaFinancieraRepository;
import ar.edu.unju.fi.tp2.services.impl.CuentaFinancieraServiceIMP;

@ExtendWith(MockitoExtension.class)
class ICuentaFinancieraServiceTest {
	
	@InjectMocks
	private CuentaFinancieraServiceIMP cuentaFinancieraService;

	@Mock
	private CuentaFinancieraRepository cuentaFinancieraRepository;
	
	private CuentaFinanciera cuentaFinanciera1;
	private CuentaFinanciera cuentaFinanciera2;

	@BeforeEach
	void setUp() throws Exception {
		cuentaFinanciera1 = CuentaFinanciera.builder()
				.id(UUID.randomUUID())
				.cbu(111L)
				.alias("test.111.test111")
				.saldo(new BigDecimal(111111))
				.estadoCuenta(EstadoCuenta.ACTIVA)
				.build();
		
		cuentaFinanciera2 = CuentaFinanciera.builder()
				.id(UUID.randomUUID())
				.cbu(222L)
				.alias("test.222.test222")
				.saldo(new BigDecimal(222222))
				.estadoCuenta(EstadoCuenta.ACTIVA)
				.build();
	}

	@Test
	public void saveCuentaFinancieraSaved() {
		System.out.println("Test saveCuentaFinancieraSaved");
		
		when(cuentaFinancieraRepository.save(cuentaFinanciera1)).thenReturn(cuentaFinanciera1);
		
		CuentaFinanciera saved = cuentaFinancieraService.saveCuentaFinanciera(cuentaFinanciera1);
		
		assertNotNull(saved);
		assertEquals(cuentaFinanciera1.getId(), saved.getId());
		assertEquals(cuentaFinanciera1.getCbu(), saved.getCbu());
		assertEquals(cuentaFinanciera1.getAlias(), saved.getAlias());
		verify(cuentaFinancieraRepository, times(1)).save(cuentaFinanciera1);
		
		System.out.println("Cuenta Financiera Guardada");
		System.out.println(saved.toString());
	}
	
	@Test
	public void findAllFound() {
		System.out.println("Test findAllFound");
		
		List<CuentaFinanciera> cuentaFinancieras = List.of(cuentaFinanciera1, cuentaFinanciera2);
		
		when(cuentaFinancieraRepository.findAll()).thenReturn(cuentaFinancieras);
		
		List<CuentaFinanciera> found = cuentaFinancieraService.findAll();
		
		assertNotNull(found);
		assertEquals(2, found.size());
		assertEquals(cuentaFinanciera1, found.get(0));
		assertEquals(cuentaFinanciera2, found.get(1));
		verify(cuentaFinancieraRepository, times(1)).findAll();
		
		System.out.println("Cuentas Financieras Encontradas");
		for (CuentaFinanciera cuentaFinanciera : found) {
			System.out.println(cuentaFinanciera.toString());
		}
	}
	
	@Test
	public void updateCuentaFinancieraUpdated() {
		System.out.println("Test updateCuentaFinancieraUpdated");
		
		UUID id = cuentaFinanciera1.getId();
		
		CuentaFinanciera cuentaFinancieraUpdated = CuentaFinanciera.builder()
				.cbu(111L)
				.alias("test.111.test111")
				.saldo(new BigDecimal(0))
				.estadoCuenta(EstadoCuenta.SUSPENDIDA)
				.build();
		
		when(cuentaFinancieraRepository.findById(id)).thenReturn(Optional.of(cuentaFinanciera1));
		when(cuentaFinancieraRepository.save(any(CuentaFinanciera.class))).then(invocation -> invocation.getArgument(0));
		
		Optional<CuentaFinanciera> updated = cuentaFinancieraService.updateCuentaFinanciera(id, cuentaFinancieraUpdated);
		
		assertTrue(updated.isPresent());
		assertEquals(new BigDecimal(0), updated.get().getSaldo());
		assertEquals(EstadoCuenta.SUSPENDIDA, updated.get().getEstadoCuenta());
		verify(cuentaFinancieraRepository, times(1)).findById(id);
		verify(cuentaFinancieraRepository, times(1)).save(cuentaFinanciera1);
		
		System.out.println("Cuenta Financiera Actualizada");
		System.out.println(updated.get().toString());
	}
	
	@Test
	public void updateCuentaFinancieraNotUpdated() {
		System.out.println("Test updateCuentaFinancieraNotUpdated");
		
		UUID id = UUID.randomUUID();
		
		when(cuentaFinancieraRepository.findById(id)).thenReturn(Optional.empty());
		
		Optional<CuentaFinanciera> updated = cuentaFinancieraService.updateCuentaFinanciera(id, cuentaFinanciera1);
		
		assertTrue(updated.isEmpty());
		verify(cuentaFinancieraRepository, times(1)).findById(id);
		verify(cuentaFinancieraRepository, never()).save(any(CuentaFinanciera.class));
		
		System.out.println("Cuenta Financiera No Actualizada");
	}
	
	@Test
	public void eliminarPorIdEliminada() {
		System.out.println("Test eliminarPorIdEliminada");
		
		UUID id = cuentaFinanciera1.getId();
		
		when(cuentaFinancieraRepository.findById(id)).thenReturn(Optional.of(cuentaFinanciera1));
		
		Optional<CuentaFinanciera> deleted = cuentaFinancieraService.eliminarPorId(id);
		
		assertTrue(deleted.isPresent());
		verify(cuentaFinancieraRepository, times(1)).findById(id);
		verify(cuentaFinancieraRepository, times(1)).delete(cuentaFinanciera1);
		
		System.out.println("Cuenta Financiera Eliminada");
		System.out.println(deleted.get().toString());
	}
	
	@Test
	public void eliminarPorIdNoEliminada() {
		System.out.println("Test updateCuentaFinancieraNoEliminada");
		
		UUID id = UUID.randomUUID();
		
		when(cuentaFinancieraRepository.findById(id)).thenReturn(Optional.empty());
		
		Optional<CuentaFinanciera> deleted = cuentaFinancieraService.eliminarPorId(id);
		
		assertTrue(deleted.isEmpty());
		verify(cuentaFinancieraRepository, times(1)).findById(id);
		verify(cuentaFinancieraRepository, never()).delete(any(CuentaFinanciera.class));
		
		System.out.println("Cuenta Financiera No Eliminada");
	}

}
