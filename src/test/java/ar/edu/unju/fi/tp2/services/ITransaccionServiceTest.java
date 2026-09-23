package ar.edu.unju.fi.tp2.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;
import ar.edu.unju.fi.tp2.enums.TipoTransaccion;
import ar.edu.unju.fi.tp2.models.Transaccion;
import ar.edu.unju.fi.tp2.repositories.TransaccionRepository;
import ar.edu.unju.fi.tp2.services.impl.TransaccionServiceIMP;

@ExtendWith(MockitoExtension.class)
class ITransaccionServiceTest {
	
	@InjectMocks
	private TransaccionServiceIMP transaccionService;

	@Mock
	private TransaccionRepository transaccionRepository;
	
	private Transaccion transaccion1;
	private Transaccion transaccion2;
	private Transaccion transaccion3;
	private Transaccion transaccion4;

	@BeforeEach
	void setUp() throws Exception {
		transaccion1 = Transaccion.builder()
				.id(UUID.randomUUID())
				.fechaHora(LocalDateTime.now().minusHours(1))
				.monto(new BigDecimal(10000))
				.tipoTransaccion(TipoTransaccion.DEPOSITO)
				.estadoTransaccion(EstadoTransaccion.COMPLETADA)
				.build();

		transaccion2 = Transaccion.builder()
				.id(UUID.randomUUID())
				.fechaHora(LocalDateTime.now().minusHours(2))
				.monto(new BigDecimal(20000))
				.tipoTransaccion(TipoTransaccion.DEPOSITO)
				.estadoTransaccion(EstadoTransaccion.RECHAZADA)
				.build();
		
		transaccion3 = Transaccion.builder()
				.id(UUID.randomUUID())
				.fechaHora(LocalDateTime.now().minusDays(1))
				.monto(new BigDecimal(30000))
				.tipoTransaccion(TipoTransaccion.EXTRACCION)
				.estadoTransaccion(EstadoTransaccion.COMPLETADA)
				.build();
		
		transaccion4 = Transaccion.builder()
				.id(UUID.randomUUID())
				.fechaHora(LocalDateTime.now().minusDays(2))
				.monto(new BigDecimal(40000))
				.tipoTransaccion(TipoTransaccion.EXTRACCION)
				.estadoTransaccion(EstadoTransaccion.RECHAZADA)
				.build();
	}

	@Test
	public void saveTransaccionSaved() {
		System.out.println("Test saveTransaccionSaved");
		
		when(transaccionRepository.save(transaccion1)).thenReturn(transaccion1);
		
		Transaccion saved = transaccionService.saveTransaccion(transaccion1);
		
		assertNotNull(saved);
		assertEquals(transaccion1.getId(), saved.getId());
		assertEquals(transaccion1.getMonto(), saved.getMonto());
		verify(transaccionRepository, times(1)).save(transaccion1);
		
		System.out.println("Transaccion Guardada");
		System.out.println(saved.toString());
	}
	
	@Test
	public void findAllFound() {
		System.out.println("Test findAllFound");
		
		List<Transaccion> transacciones = List.of(transaccion1, transaccion2, transaccion3, transaccion4);
		
		when(transaccionRepository.findAll()).thenReturn(transacciones);
		
		List<Transaccion> found = transaccionService.findAll();
		
		assertNotNull(found);
		assertEquals(4, found.size());
		assertEquals(transaccion1, found.get(0));
		assertEquals(transaccion2, found.get(1));
		assertEquals(transaccion3, found.get(2));
		assertEquals(transaccion4, found.get(3));
		verify(transaccionRepository, times(1)).findAll();
		
		System.out.println("Transacciones Encontradas");
		for (Transaccion transaccion : found) {
			System.out.println(transaccion.toString());
		}
	}
	
	@Test
	public void updateTransaccionUpdated() {
		System.out.println("Test updateTransaccionUpdated");
		
		UUID id = transaccion1.getId();
		
		Transaccion transaccionUpdated = Transaccion.builder()
				.id(UUID.randomUUID())
				.fechaHora(LocalDateTime.now().minusHours(1))
				.monto(new BigDecimal(10000))
				.tipoTransaccion(TipoTransaccion.DEPOSITO)
				.estadoTransaccion(EstadoTransaccion.REVERTIDA)
				.build();
		
		when(transaccionRepository.findById(id)).thenReturn(Optional.of(transaccion1));
		when(transaccionRepository.save(any(Transaccion.class))).then(invocation -> invocation.getArgument(0));
		
		Optional<Transaccion> updated = transaccionService.updateTransaccion(id, transaccionUpdated);
		
		assertTrue(updated.isPresent());
		assertEquals(EstadoTransaccion.REVERTIDA, updated.get().getEstadoTransaccion());
		verify(transaccionRepository, times(1)).findById(id);
		verify(transaccionRepository, times(1)).save(transaccion1);
		
		System.out.println("Transaccion Actualizada");
		System.out.println(updated.get().toString());
	}
	
	@Test
	public void updateTransaccionNotUpdated() {
		System.out.println("Test updateTransaccionNotUpdated");
		
		UUID id = UUID.randomUUID();
		
		when(transaccionRepository.findById(id)).thenReturn(Optional.empty());
		
		Optional<Transaccion> updated = transaccionService.updateTransaccion(id, transaccion1);
		
		assertTrue(updated.isEmpty());
		verify(transaccionRepository, times(1)).findById(id);
		verify(transaccionRepository, never()).save(any(Transaccion.class));
		
		System.out.println("Transaccion No Actualizada");
	}
	
	@Test
	public void eliminarPorIdEliminada() {
		System.out.println("Test eliminarPorIdEliminada");
		
		UUID id = transaccion1.getId();
		
		when(transaccionRepository.findById(id)).thenReturn(Optional.of(transaccion1));
		
		Optional<Transaccion> deleted = transaccionService.eliminarPorId(id);
		
		assertTrue(deleted.isPresent());
		verify(transaccionRepository, times(1)).findById(id);
		verify(transaccionRepository, times(1)).delete(transaccion1);
		
		System.out.println("Transaccion Eliminada");
		System.out.println(deleted.get().toString());
	}
	
	@Test
	public void eliminarPorIdNoEliminada() {
		System.out.println("Test eliminarPorIdNoEliminada");
		
		UUID id = UUID.randomUUID();
		
		when(transaccionRepository.findById(id)).thenReturn(Optional.empty());
		
		Optional<Transaccion> deleted = transaccionService.eliminarPorId(id);
		
		assertTrue(deleted.isEmpty());
		verify(transaccionRepository, times(1)).findById(id);
		verify(transaccionRepository, never()).delete(any(Transaccion.class));
		
		System.out.println("Transaccion No Eliminada");
	}

}
