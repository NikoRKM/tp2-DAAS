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
import ar.edu.unju.fi.tp2.models.CajaAhorro;
import ar.edu.unju.fi.tp2.repositories.CajaAhorroRepository;
import ar.edu.unju.fi.tp2.services.impl.CajaAhorroServiceIMP;

@ExtendWith(MockitoExtension.class)
class ICajaAhorroServiceTest {

	@InjectMocks
	private CajaAhorroServiceIMP cajaAhorroService;

	@Mock
	private CajaAhorroRepository cajaAhorroRepository;
	
	private CajaAhorro cajaAhorro1;
	private CajaAhorro cajaAhorro2;

	@BeforeEach
	void setUp() throws Exception {
		cajaAhorro1 = new CajaAhorro();
		cajaAhorro1.setId(UUID.randomUUID());
		cajaAhorro1.setCbu(111L);
		cajaAhorro1.setAlias("test.111.test111");
		cajaAhorro1.setSaldo(new BigDecimal(111111));
		cajaAhorro1.setEstadoCuenta(EstadoCuenta.ACTIVA);
		cajaAhorro1.setMargenDescuento(new BigDecimal(111));
		cajaAhorro1.setComisionMantenimientoMensual(new BigDecimal(111));
		
		cajaAhorro2 = new CajaAhorro();
		cajaAhorro2.setId(UUID.randomUUID());
		cajaAhorro2.setCbu(222L);
		cajaAhorro2.setAlias("test.222.test222");
		cajaAhorro2.setSaldo(new BigDecimal(222222));
		cajaAhorro2.setEstadoCuenta(EstadoCuenta.ACTIVA);
		cajaAhorro2.setMargenDescuento(new BigDecimal(222));
		cajaAhorro2.setComisionMantenimientoMensual(new BigDecimal(222));
	}

	@Test
	public void saveCajaAhorroSaved() {
		System.out.println("Test saveCajaAhorroSaved");
		
		when(cajaAhorroRepository.save(cajaAhorro1)).thenReturn(cajaAhorro1);
		
		CajaAhorro saved = cajaAhorroService.saveCajaAhorro(cajaAhorro1);
		
		assertNotNull(saved);
		assertEquals(cajaAhorro1.getId(), saved.getId());
		assertEquals(cajaAhorro1.getCbu(), saved.getCbu());
		assertEquals(cajaAhorro1.getAlias(), saved.getAlias());
		verify(cajaAhorroRepository, times(1)).save(cajaAhorro1);
		
		System.out.println("Caja Ahorro Guardada");
		System.out.println(saved.toString());
	}
	
	@Test
	public void findAllFound() {
		System.out.println("Test findAllFound");
		
		List<CajaAhorro> cajaAhorros = List.of(cajaAhorro1, cajaAhorro2);
		
		when(cajaAhorroRepository.findAll()).thenReturn(cajaAhorros);
		
		List<CajaAhorro> found = cajaAhorroService.findAll();
		
		assertNotNull(found);
		assertEquals(2, found.size());
		assertEquals(cajaAhorro1, found.get(0));
		assertEquals(cajaAhorro2, found.get(1));
		verify(cajaAhorroRepository, times(1)).findAll();
		
		System.out.println("Cuentas Corrientes Encontradas");
		for (CajaAhorro cajaAhorro : found) {
			System.out.println(cajaAhorro.toString());
		}
	}
	
	@Test
	public void updateCajaAhorroUpdated() {
		System.out.println("Test updateCajaAhorroUpdated");
		
		UUID id = cajaAhorro1.getId();
		
		CajaAhorro cajaAhorroUpdated = new CajaAhorro();
		cajaAhorroUpdated.setCbu(111L);
		cajaAhorroUpdated.setAlias("test.111.test111");
		cajaAhorroUpdated.setSaldo(new BigDecimal(0));
		cajaAhorroUpdated.setEstadoCuenta(EstadoCuenta.BLOQUEADA);
		cajaAhorroUpdated.setMargenDescuento(new BigDecimal(0));
		cajaAhorroUpdated.setComisionMantenimientoMensual(new BigDecimal(0));
		
		when(cajaAhorroRepository.findById(id)).thenReturn(Optional.of(cajaAhorro1));
		when(cajaAhorroRepository.save(any(CajaAhorro.class))).then(invocation -> invocation.getArgument(0));
		
		Optional<CajaAhorro> updated = cajaAhorroService.updateCajaAhorro(id, cajaAhorroUpdated);
		
		assertTrue(updated.isPresent());
		assertEquals(new BigDecimal(0), updated.get().getSaldo());
		assertEquals(EstadoCuenta.BLOQUEADA, updated.get().getEstadoCuenta());
		assertEquals(new BigDecimal(0), updated.get().getMargenDescuento());
		assertEquals(new BigDecimal(0), updated.get().getComisionMantenimientoMensual());
		verify(cajaAhorroRepository, times(1)).findById(id);
		verify(cajaAhorroRepository, times(1)).save(cajaAhorro1);
		
		System.out.println("Caja Ahorro Actualizada");
		System.out.println(updated.get().toString());
	}
	
	@Test
	public void updateCajaAhorroNotUpdated() {
		System.out.println("Test updateCajaAhorroNotUpdated");
		
		UUID id = UUID.randomUUID();
		
		when(cajaAhorroRepository.findById(id)).thenReturn(Optional.empty());
		
		Optional<CajaAhorro> updated = cajaAhorroService.updateCajaAhorro(id, cajaAhorro1);
		
		assertTrue(updated.isEmpty());
		verify(cajaAhorroRepository, times(1)).findById(id);
		verify(cajaAhorroRepository, never()).save(any(CajaAhorro.class));
		
		System.out.println("Caja Ahorro No Actualizada");
	}
	
	@Test
	public void eliminarPorIdEliminada() {
		System.out.println("Test eliminarPorIdEliminada");
		
		UUID id = cajaAhorro1.getId();
		
		when(cajaAhorroRepository.findById(id)).thenReturn(Optional.of(cajaAhorro1));
		
		Optional<CajaAhorro> deleted = cajaAhorroService.eliminarPorId(id);
		
		assertTrue(deleted.isPresent());
		verify(cajaAhorroRepository, times(1)).findById(id);
		verify(cajaAhorroRepository, times(1)).delete(cajaAhorro1);
		
		System.out.println("Caja Ahorro Eliminada");
		System.out.println(deleted.get().toString());
	}
	
	@Test
	public void eliminarPorIdNoEliminada() {
		System.out.println("Test updateCajaAhorroNoEliminada");
		
		UUID id = UUID.randomUUID();
		
		when(cajaAhorroRepository.findById(id)).thenReturn(Optional.empty());
		
		Optional<CajaAhorro> deleted = cajaAhorroService.eliminarPorId(id);
		
		assertTrue(deleted.isEmpty());
		verify(cajaAhorroRepository, times(1)).findById(id);
		verify(cajaAhorroRepository, never()).delete(any(CajaAhorro.class));
		
		System.out.println("Caja Ahorro No Eliminada");
	}

}
