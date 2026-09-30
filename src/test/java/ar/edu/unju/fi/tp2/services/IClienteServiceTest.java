package ar.edu.unju.fi.tp2.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

import ar.edu.unju.fi.tp2.dto.ClienteRequestDto;
import ar.edu.unju.fi.tp2.dto.ClienteResponseDto;
import ar.edu.unju.fi.tp2.exceptions.RecursoNoEncontradoException;
import ar.edu.unju.fi.tp2.models.Cliente;
import ar.edu.unju.fi.tp2.repositories.ClienteRepository;
import ar.edu.unju.fi.tp2.services.impl.ClienteServiceIMP;

@ExtendWith(MockitoExtension.class)
class IClienteServiceTest {

    @InjectMocks
    private ClienteServiceIMP clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteRequestDto cliente1;
    private ClienteRequestDto cliente2;
    private Cliente cliente3;

    @BeforeEach
    void setUp() throws Exception {

        cliente3 = Cliente.builder()
                .id(UUID.randomUUID())
                .cuil(111L)
                .nombre("test1")
                .razonSocial("Empresa Test 1")
                .email("test1@test.com")
                .telefono("3881111111")
                .direccion("Direccion test 1")
                .titular(null)
                .build();

        cliente1 = ClienteRequestDto.builder()
                .cuil(111L)
                .nombre("test1")
                .email("test1@test1.com")
                .telefono("111")
                .direccion("test test test 111")
                .titularId(null)
                .build();

        cliente2 = ClienteRequestDto.builder()
                .cuil(222L)
                .nombre("test2")
                .email("test2@test2.com")
                .telefono("222")
                .direccion("test test test 222")
                .titularId(cliente3.getId())
                .build();
    }

    @Test
    public void saveClienteSaved() {
        System.out.println("Test saveClienteSaved");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente3);

        ClienteResponseDto saved = clienteService.saveCliente(cliente1);

        assertNotNull(saved);
        assertEquals(cliente3.getId(), saved.getId());
        assertEquals(cliente1.getCuil(), saved.getCuil());
        assertEquals(cliente1.getNombre(), saved.getNombre());
        verify(clienteRepository, times(1)).save(any(Cliente.class));

        System.out.println("Cliente Guardado");
        System.out.println(saved.toString());
    }

    @Test
    public void findAllFound() {
        System.out.println("Test findAllFound");

        List<Cliente> clientes = List.of(cliente3);

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<ClienteResponseDto> found = clienteService.findAll();

        assertNotNull(found);
        assertEquals(cliente3.getId(), found.get(0).getId());
        assertEquals(cliente3.getCuil(), found.get(0).getCuil());
        assertEquals(cliente3.getNombre(), found.get(0).getNombre());
        verify(clienteRepository, times(1)).findAll();

        System.out.println("Clientes Encontrados");
        for (ClienteResponseDto cliente : found) {
            System.out.println(cliente.toString());
        }
    }

    @Test
    public void updateClienteUpdated() {
        System.out.println("Test updateClienteUpdated");

        UUID id = cliente3.getId();

        ClienteRequestDto clienteActualizado = ClienteRequestDto.builder()
                .cuil(111L)
                .nombre("test1")
                .email("test1@test.com")
                .telefono("3881111111")
                .direccion("nueva direccion")
                .titularId(null)
                .build();

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente3));
        when(clienteRepository.save(any(Cliente.class))).then(invocation -> invocation.getArgument(0));

        ClienteResponseDto updated = clienteService.updateCliente(id, clienteActualizado);

        assertNotNull(updated);
        assertEquals("test1", updated.getNombre());
        assertEquals("nueva direccion", updated.getDireccion());
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).save(cliente3);

        System.out.println("Cliente Actualizado");
        System.out.println(updated);
    }

    @Test
    public void updateClienteNotUpdated() {
        System.out.println("Test updateClienteNotUpdated");

        UUID id = UUID.randomUUID();

        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> clienteService.updateCliente(id, cliente1));

        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, never()).save(any(Cliente.class));

        System.out.println("Cliente No Actualizado");
    }

    @Test
    public void eliminarPorIdEliminado() {
        System.out.println("Test eliminarPorIdEliminado");

        UUID id = cliente3.getId();

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente3));

        ClienteResponseDto deleted = clienteService.eliminarPorId(id);

        assertNotNull(deleted);
        assertEquals(cliente3.getId(), deleted.getId());
        assertEquals(cliente3.getCuil(), deleted.getCuil());
        assertEquals(cliente3.getNombre(), deleted.getNombre());

        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).delete(cliente3);

        System.out.println("Cliente Eliminado");
        System.out.println(deleted);
    }

    @Test
    public void eliminarPorIdNoEliminado() {
        System.out.println("Test updateClienteNotUpdated");

        UUID id = UUID.randomUUID();

        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> clienteService.eliminarPorId(id));

        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, never()).delete(any(Cliente.class));

        System.out.println("Cliente No Eliminado");
    }

}
