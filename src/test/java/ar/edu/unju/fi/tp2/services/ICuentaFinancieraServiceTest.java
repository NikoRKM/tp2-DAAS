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

import ar.edu.unju.fi.tp2.dto.CuentaFinancieraRequestDto;
import ar.edu.unju.fi.tp2.dto.CuentaFinancieraResponseDto;
import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.exceptions.RecursoNoEncontradoException;
import ar.edu.unju.fi.tp2.models.Cliente;
import ar.edu.unju.fi.tp2.models.CuentaFinanciera;
import ar.edu.unju.fi.tp2.repositories.ClienteRepository;
import ar.edu.unju.fi.tp2.repositories.CuentaFinancieraRepository;
import ar.edu.unju.fi.tp2.services.impl.CuentaFinancieraServiceIMP;

@ExtendWith(MockitoExtension.class)
class ICuentaFinancieraServiceTest {

    @InjectMocks
    private CuentaFinancieraServiceIMP cuentaFinancieraService;

    @Mock
    private CuentaFinancieraRepository cuentaFinancieraRepository;

    @Mock
    private ClienteRepository clienteRepository;

    private CuentaFinanciera cuentaFinanciera1;
    private CuentaFinanciera cuentaFinanciera2;

    private Cliente cliente;

    private CuentaFinancieraRequestDto cuentaFinancieraRequestDto;

    @BeforeEach
    void setUp() throws Exception {
        cliente = Cliente.builder()
                .id(UUID.randomUUID())
                .cuil(111L)
                .nombre("Cliente Test")
                .email("cliente@test.com")
                .telefono("111")
                .direccion("Direccion test")
                .build();

        cuentaFinanciera1 = CuentaFinanciera.builder()
                .id(UUID.randomUUID())
                .cbu(111L)
                .alias("test.111.test111")
                .saldo(new BigDecimal(111111))
                .estadoCuenta(EstadoCuenta.ACTIVA)
                .cliente(cliente)
                .build();

        cuentaFinanciera2 = CuentaFinanciera.builder()
                .id(UUID.randomUUID())
                .cbu(222L)
                .alias("test.222.test222")
                .saldo(new BigDecimal(222222))
                .estadoCuenta(EstadoCuenta.ACTIVA)
                .cliente(cliente)
                .build();

        cuentaFinancieraRequestDto = CuentaFinancieraRequestDto.builder()
                .cbu(cuentaFinanciera1.getCbu())
                .alias(cuentaFinanciera1.getAlias())
                .saldo(cuentaFinanciera1.getSaldo())
                .estadoCuenta(cuentaFinanciera1.getEstadoCuenta())
                .cliente(cliente.getId())
                .build();

    }

    @Test
    public void saveCuentaFinancieraSaved() {
        System.out.println("Test saveCuentaFinancieraSaved");

        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(cuentaFinancieraRepository.save(any(CuentaFinanciera.class))).thenReturn(cuentaFinanciera1);

        CuentaFinancieraResponseDto saved = cuentaFinancieraService.saveCuentaFinanciera(cuentaFinancieraRequestDto);

        assertNotNull(saved);
        assertEquals(cuentaFinanciera1.getId(), saved.getId());
        assertEquals(cuentaFinanciera1.getCbu(), saved.getCbu());
        assertEquals(cuentaFinanciera1.getAlias(), saved.getAlias());
        verify(cuentaFinancieraRepository, times(1)).save(any(CuentaFinanciera.class));

        System.out.println("Cuenta Financiera Guardada");
        System.out.println(saved.toString());
    }

    @Test
    public void findAllFound() {
        System.out.println("Test findAllFound");

        List<CuentaFinanciera> cuentaFinancieras = List.of(cuentaFinanciera1, cuentaFinanciera2);

        when(cuentaFinancieraRepository.findAll()).thenReturn(cuentaFinancieras);

        List<CuentaFinancieraResponseDto> found = cuentaFinancieraService.findAll();

        assertNotNull(found);
        assertEquals(2, found.size());
        assertEquals(cuentaFinanciera1.getId(), found.get(0).getId());
        assertEquals(cuentaFinanciera2.getId(), found.get(1).getId());
        verify(cuentaFinancieraRepository, times(1)).findAll();

        System.out.println("Cuentas Financieras Encontradas");
        for (CuentaFinancieraResponseDto cuentaFinanciera : found) {
            System.out.println(cuentaFinanciera.toString());
        }
    }

    @Test
    public void updateCuentaFinancieraUpdated() {
        System.out.println("Test updateCuentaFinancieraUpdated");

        UUID id = cuentaFinanciera1.getId();

        CuentaFinancieraRequestDto cuentaFinancieraUpdated = CuentaFinancieraRequestDto.builder()
                .cbu(cuentaFinanciera1.getCbu())
                .alias(cuentaFinanciera1.getAlias())
                .saldo(new BigDecimal(0))
                .estadoCuenta(EstadoCuenta.SUSPENDIDA)
                .build();

        when(cuentaFinancieraRepository.findById(id)).thenReturn(Optional.of(cuentaFinanciera1));
        when(cuentaFinancieraRepository.save(any(CuentaFinanciera.class)))
                .then(invocation -> invocation.getArgument(0));

        CuentaFinancieraResponseDto updated = cuentaFinancieraService.updateCuentaFinanciera(id,
                cuentaFinancieraUpdated);

        assertNotNull(updated);
        assertEquals(new BigDecimal(0), updated.getSaldo());
        assertEquals(EstadoCuenta.SUSPENDIDA, updated.getEstadoCuenta());
        verify(cuentaFinancieraRepository, times(1)).findById(id);
        verify(cuentaFinancieraRepository, times(1)).save(cuentaFinanciera1);

        System.out.println("Cuenta Financiera Actualizada");
        System.out.println(updated);
    }

    @Test
    public void updateCuentaFinancieraNotUpdated() {
        System.out.println("Test updateCuentaFinancieraNotUpdated");

        UUID id = UUID.randomUUID();

        when(cuentaFinancieraRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> cuentaFinancieraService.updateCuentaFinanciera(
                        id, cuentaFinancieraRequestDto));

        verify(cuentaFinancieraRepository, times(1)).findById(id);
        verify(cuentaFinancieraRepository, never()).save(any(CuentaFinanciera.class));

        System.out.println("Cuenta Financiera No Actualizada");
    }

    @Test
    public void eliminarPorIdEliminada() {
        System.out.println("Test eliminarPorIdEliminada");

        UUID id = cuentaFinanciera1.getId();

        when(cuentaFinancieraRepository.findById(id)).thenReturn(Optional.of(cuentaFinanciera1));

        CuentaFinancieraResponseDto deleted = cuentaFinancieraService.eliminarPorId(id);

        assertNotNull(deleted);
        verify(cuentaFinancieraRepository, times(1)).findById(id);
        verify(cuentaFinancieraRepository, times(1)).delete(cuentaFinanciera1);

        System.out.println("Cuenta Financiera Eliminada");
        System.out.println(deleted);
    }

    @Test
    public void eliminarPorIdNoEliminada() {
        System.out.println("Test updateCuentaFinancieraNoEliminada");

        UUID id = UUID.randomUUID();

        when(cuentaFinancieraRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                RecursoNoEncontradoException.class,
                () -> cuentaFinancieraService.eliminarPorId(id));


        verify(cuentaFinancieraRepository, times(1)).findById(id);
        verify(cuentaFinancieraRepository, never()).delete(any(CuentaFinanciera.class));

        System.out.println("Cuenta Financiera No Eliminada");
    }

}
