package ar.edu.unju.fi.tp2.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.unju.fi.tp2.models.Cliente;
import ar.edu.unju.fi.tp2.repositories.ClienteRepository;
import ar.edu.unju.fi.tp2.services.impl.ClienteServiceIMP;

@ExtendWith(MockitoExtension.class)
class IClienteServiceTest {
	
	@InjectMocks
	private ClienteServiceIMP clienteService;

	@Mock
	private ClienteRepository clienteRepository;
	
	private Cliente cliente1;
	private Cliente cliente2;
	
	@BeforeEach
	void setUp() throws Exception {
		cliente1 = Cliente.builder()
				.id(UUID.randomUUID())
				.cuil(111L)
				.nombre("test1")
				.email("test1@test1.com")
				.telefono(111)
				.direccion("test test test 111")
				.titular(null)
				.build();
		
		cliente2 = Cliente.builder()
				.id(UUID.randomUUID())
				.cuil(222L)
				.nombre("test2")
				.email("test2@test2.com")
				.telefono(222)
				.direccion("test test test 222")
				.titular(cliente1)
				.build();
	}
	
	@Test
	public void saveClienteSaved() {
		System.out.println("Test saveClienteSaved");
		
		when(clienteRepository.save(cliente1)).thenReturn(cliente1);
		
		Cliente saved = clienteService.saveCliente(cliente1);
		
		assertNotNull(saved);
		assertEquals(cliente1.getId(), saved.getId());
		assertEquals(cliente1.getCuil(), saved.getCuil());
		verify(clienteRepository, times(1)).save(cliente1);
		
		System.out.println("Cliente Guardado");
	}
	
	@Test
	public void findAllFound() {
		System.out.println("Test findAllFound");
		
		List<Cliente> clientes = List.of(cliente1, cliente2);
		
		when(clienteRepository.findAll()).thenReturn(clientes);
		
		List<Cliente> found = clienteService.findAll();
		
		assertNotNull(found);
		assertEquals(2, found.size());
		assertEquals(cliente1, found.get(0));
		assertEquals(cliente2, found.get(1));
		verify(clienteRepository, times(1)).findAll();
		
		System.out.println("Clientes Encontrados");
	}
	
	@Test
	public void updateClienteUpdated() {
		System.out.println("Test updateClienteUpdated");
		
		UUID id = cliente1.getId();
		
		Cliente clienteUpdated = Cliente.builder()
				.cuil(111L)
				.nombre("test1")
				.email("test1@test1.com")
				.telefono(111)
				.direccion("nueva direccion")
				.titular(null)
				.build();
		
		when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente1));
		when(clienteRepository.save(any(Cliente.class))).then(invocation -> invocation.getArgument(0));
		
		Optional<Cliente> updated = clienteService.updateCliente(id, clienteUpdated);
		
		assertTrue(updated.isPresent());
		assertEquals("test1", updated.get().getNombre());
		assertEquals("nueva direccion", updated.get().getDireccion());
		verify(clienteRepository, times(1)).findById(id);
		verify(clienteRepository, times(1)).save(cliente1);
		
		System.out.println("Cliente Actualizado");
	}
	
	@Test
	public void updateClienteNotUpdated() {
		System.out.println("Test updateClienteNotUpdated");
		
		UUID id = UUID.randomUUID();
		
		when(clienteRepository.findById(id)).thenReturn(Optional.empty());
		
		Optional<Cliente> updated = clienteService.updateCliente(id, cliente1);
		
		assertTrue(updated.isEmpty());
		verify(clienteRepository, times(1)).findById(id);
		verify(clienteRepository, never()).save(any(Cliente.class));
		
		System.out.println("Cliente No Actualizado");
	}
	
	@Test
	public void eliminarPorIdEliminado() {
		System.out.println("Test eliminarPorIdEliminado");
		
		UUID id = cliente1.getId();
		
		when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente1));
		
		Optional<Cliente> deleted = clienteService.eliminarPorId(id);
		
		assertTrue(deleted.isPresent());
		verify(clienteRepository, times(1)).findById(id);
		verify(clienteRepository, times(1)).delete(cliente1);
		
		System.out.println("Cliente Eliminado");
	}
	
	@Test
	public void eliminarPorIdNoEliminado() {
		System.out.println("Test updateClienteNotUpdated");
		
		UUID id = UUID.randomUUID();
		
		when(clienteRepository.findById(id)).thenReturn(Optional.empty());
		
		Optional<Cliente> deleted = clienteService.eliminarPorId(id);
		
		assertTrue(deleted.isEmpty());
		verify(clienteRepository, times(1)).findById(id);
		verify(clienteRepository, never()).delete(any(Cliente.class));
		
		System.out.println("Cliente No Eliminado");
	}
	
}
