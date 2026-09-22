package ar.edu.unju.fi.tp2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;
import ar.edu.unju.fi.tp2.enums.TipoTransaccion;
import ar.edu.unju.fi.tp2.models.CajaAhorro;
import ar.edu.unju.fi.tp2.models.Cliente;
import ar.edu.unju.fi.tp2.models.CuentaCorriente;
import ar.edu.unju.fi.tp2.models.CuentaFinanciera;
import ar.edu.unju.fi.tp2.models.Transaccion;
import ar.edu.unju.fi.tp2.repositories.CajaAhorroRepository;
import ar.edu.unju.fi.tp2.repositories.ClienteRepository;
import ar.edu.unju.fi.tp2.repositories.CuentaCorrienteRepository;
import ar.edu.unju.fi.tp2.repositories.CuentaFinancieraRepository;
import ar.edu.unju.fi.tp2.repositories.TransaccionRepository;
import ar.edu.unju.fi.tp2.services.ICajaAhorroService;
import ar.edu.unju.fi.tp2.services.IClienteService;
import ar.edu.unju.fi.tp2.services.ICuentaCorrienteService;
import ar.edu.unju.fi.tp2.services.ICuentaFinancieraService;
import ar.edu.unju.fi.tp2.services.ITransaccionService;

@SpringBootApplication
@EnableJpaAuditing
public class Tp2Application {

        public static void main(String[] args) {
                SpringApplication.run(Tp2Application.class, args);
        }

        @Bean
        CommandLineRunner probarServices(
                        IClienteService clienteService,
                        ICajaAhorroService cajaAhorroService,
                        ICuentaCorrienteService cuentaCorrienteService,
                        ICuentaFinancieraService cuentaFinancieraService,
                        ITransaccionService transaccionService) {

                return args -> {

                        System.out.println("\n==============================================");
                        System.out.println("       SUPER PRUEBA DEL SISTEMA");
                        System.out.println("==============================================");

                        // =====================================================
                        // 1. CREAR CLIENTE
                        // =====================================================

                        System.out.println("\n========== 1. CREAR CLIENTE ==========");

                        Cliente cliente = new Cliente();

                        cliente.setCuil(20301234567L);
                        cliente.setNombre("Maxi Flores");
                        cliente.setRazonSocial("Maxi Flores");
                        cliente.setEmail("maxi@gmail.com");
                        cliente.setTelefono(3884567);
                        cliente.setDireccion("San Salvador de Jujuy");

                        cliente = clienteService.saveCliente(cliente);

                        System.out.println("Cliente creado:");
                        System.out.println(cliente);

                        // =====================================================
                        // 2. BUSCAR CLIENTE POR ID
                        // =====================================================

                        System.out.println("\n========== 2. BUSCAR CLIENTE POR ID ==========");

                        Optional clienteEncontrado = clienteService.findById(cliente.getId());

                        System.out.println("Cliente encontrado:");
                        System.out.println(clienteEncontrado);

                        // =====================================================
                        // 3. BUSCAR CLIENTE POR CUIL
                        // =====================================================

                        System.out.println("\n========== 3. BUSCAR CLIENTE POR CUIL ==========");

                        Optional clientePorCuil = clienteService.findByCuil(cliente.getCuil());

                        System.out.println("Cliente encontrado por CUIL:");
                        System.out.println(clientePorCuil);

                        // =====================================================
                        // 4. BUSCAR TODOS LOS CLIENTES
                        // =====================================================

                        System.out.println("\n========== 4. BUSCAR TODOS LOS CLIENTES ==========");

                        clienteService.findAll()
                                        .forEach(System.out::println);

                        // =====================================================
                        // 5. ACTUALIZAR CLIENTE
                        // =====================================================

                        System.out.println("\n========== 5. ACTUALIZAR CLIENTE ==========");

                        cliente.setNombre("Maximiliano Flores");
                        cliente.setDireccion("Nueva dirección Jujuy");

                        Optional<Cliente> cliente3 = clienteService.updateCliente(cliente.getId(), cliente);

                        System.out.println("Cliente actualizado:");
                        System.out.println(cliente3);

                        // =====================================================
                        // 6. CREAR SEGUNDO CLIENTE
                        // RELACIÓN CLIENTE -> CLIENTE
                        // =====================================================

                        System.out.println("\n========== 6. RELACIÓN CLIENTE - CLIENTE ==========");

                        Cliente cliente2 = new Cliente();

                        cliente2.setCuil(20345678901L);
                        cliente2.setNombre("Juan Pérez");
                        cliente2.setRazonSocial("Juan Pérez");
                        cliente2.setEmail("juan.perez@gmail.com");
                        cliente2.setTelefono(3884567);
                        cliente2.setDireccion("Av. Belgrano 456");

                        // Juan tiene como titular a Maxi
                        cliente2.setTitular(cliente);

                        cliente2 = clienteService.saveCliente(cliente2);

                        System.out.println("Segundo cliente:");
                        System.out.println(cliente2);

                        System.out.println("\nTitular del segundo cliente:");
                        System.out.println(cliente2.getTitular());

                        // =====================================================
                        // 7. CREAR CAJA DE AHORRO
                        // =====================================================

                        System.out.println("\n========== 7. CREAR CAJA DE AHORRO ==========");

                        CajaAhorro cajaAhorro = new CajaAhorro();

                        cajaAhorro.setCbu(2850590940090412345L);
                        cajaAhorro.setAlias("maxi.ahorro");
                        cajaAhorro.setSaldo(new BigDecimal("150000"));
                        cajaAhorro.setEstadoCuenta(EstadoCuenta.ACTIVA);

                        cajaAhorro.setMargenDescuento(
                                        new BigDecimal("50000"));

                        cajaAhorro.setComisionMantenimientoMensual(
                                        new BigDecimal("2500"));

                        // Relación CuentaFinanciera -> Cliente
                        cajaAhorro.setCliente(cliente);

                        cajaAhorro = cajaAhorroService.saveCajaAhorro(cajaAhorro);

                        System.out.println("Caja de ahorro creada:");
                        System.out.println(cajaAhorro);

                        // =====================================================
                        // 8. CREAR CUENTA CORRIENTE
                        // =====================================================

                        System.out.println("\n========== 8. CREAR CUENTA CORRIENTE ==========");

                        CuentaCorriente cuentaCorriente = new CuentaCorriente();

                        cuentaCorriente.setCbu(2850590940090498765L);
                        cuentaCorriente.setAlias("maxi.corriente");
                        cuentaCorriente.setSaldo(new BigDecimal("80000"));
                        cuentaCorriente.setEstadoCuenta(EstadoCuenta.ACTIVA);

                        cuentaCorriente.setTasaInteresAnual(
                                        new BigDecimal("35.5"));

                        cuentaCorriente.setCupoLimiteMensual(50000);

                        // Relación CuentaFinanciera -> Cliente
                        cuentaCorriente.setCliente(cliente);

                        cuentaCorriente = cuentaCorrienteService.saveCuentaCorriente(cuentaCorriente);

                        System.out.println("Cuenta corriente creada:");
                        System.out.println(cuentaCorriente);

                        // =====================================================
                        // 9. BUSCAR CAJA DE AHORRO POR ID
                        // =====================================================

                        System.out.println("\n========== 9. BUSCAR CAJA DE AHORRO ==========");

                        Optional<CajaAhorro> cajaEncontrada = cajaAhorroService.findById(cajaAhorro.getId());

                        System.out.println(cajaEncontrada);

                        // =====================================================
                        // 10. BUSCAR TODAS LAS CAJAS DE AHORRO
                        // =====================================================

                        System.out.println("\n========== 10. TODAS LAS CAJAS DE AHORRO ==========");

                        cajaAhorroService.findAll()
                                        .forEach(System.out::println);

                        // =====================================================
                        // 11. ACTUALIZAR CAJA DE AHORRO
                        // =====================================================

                        System.out.println("\n========== 11. ACTUALIZAR CAJA DE AHORRO ==========");

                        cajaAhorro.setAlias("maxi.ahorro.nuevo");
                        cajaAhorro.setSaldo(new BigDecimal("175000"));

                        Optional<CajaAhorro> cajaAhorro2 = cajaAhorroService.updateCajaAhorro(cajaAhorro.getId(),cajaAhorro);

                        System.out.println(cajaAhorro2);

                        // =====================================================
                        // 12. BUSCAR CUENTA CORRIENTE POR ID
                        // =====================================================

                        System.out.println("\n========== 12. BUSCAR CUENTA CORRIENTE ==========");

                        Optional<CuentaCorriente> corrienteEncontrada = cuentaCorrienteService.findById(
                                        cuentaCorriente.getId());

                        System.out.println(corrienteEncontrada);

                        // =====================================================
                        // 13. BUSCAR TODAS LAS CUENTAS CORRIENTES
                        // =====================================================

                        System.out.println(
                                        "\n========== 13. TODAS LAS CUENTAS CORRIENTES ==========");

                        cuentaCorrienteService.findAll()
                                        .forEach(System.out::println);

                        // =====================================================
                        // 14. ACTUALIZAR CUENTA CORRIENTE
                        // =====================================================

                        System.out.println(
                                        "\n========== 14. ACTUALIZAR CUENTA CORRIENTE ==========");

                        cuentaCorriente.setAlias("maxi.corriente.nuevo");
                        cuentaCorriente.setSaldo(new BigDecimal("95000"));

                        Optional<CuentaCorriente> cuentaCorriente2 = cuentaCorrienteService.updateCuentaCorriente(cuentaCorriente.getId(),cuentaCorriente);

                        System.out.println(cuentaCorriente2);

                        // =====================================================
                        // 15. CARGAR CUENTAS DEL CLIENTE
                        // =====================================================

                        System.out.println(
                                        "\n========== 15. CUENTAS DEL CLIENTE ==========");

                        Cliente clienteConCuentas = clienteService.cargarCuentasDeCliente(
                                        cliente.getId());

                        System.out.println("Cliente:");
                        System.out.println(clienteConCuentas.getNombre());

                        System.out.println("\nCuentas:");

                        clienteConCuentas.getCuentas()
                                        .forEach(cuenta -> {

                                                System.out.println(
                                                                " - " +
                                                                                cuenta.getClass().getSimpleName() +
                                                                                " | Alias: " +
                                                                                cuenta.getAlias() +
                                                                                " | CBU: " +
                                                                                cuenta.getCbu() +
                                                                                " | Saldo: " +
                                                                                cuenta.getSaldo());
                                        });

                        // =====================================================
                        // 16. COMPROBAR RELACIÓN CUENTA -> CLIENTE
                        // =====================================================

                        System.out.println(
                                        "\n========== 16. CUENTA -> CLIENTE ==========");

                        System.out.println("Cliente de la Caja de Ahorro:");
                        System.out.println(
                                        cajaAhorro.getCliente());

                        System.out.println("\nCliente de la Cuenta Corriente:");
                        System.out.println(
                                        cuentaCorriente.getCliente());

                        // =====================================================
                        // 17. BUSCAR CUENTAS FINANCIERAS
                        // =====================================================

                        System.out.println(
                                        "\n========== 17. CUENTAS FINANCIERAS ==========");

                        System.out.println("Buscar por ID:");

                        Optional<CuentaFinanciera> cuentaEncontrada = cuentaFinancieraService.findById(
                                        cajaAhorro.getId());

                        System.out.println(cuentaEncontrada);

                        System.out.println("\nTodas las cuentas:");

                        cuentaFinancieraService.findAll()
                                        .forEach(cuenta -> System.out.println(
                                                        cuenta.getClass().getSimpleName()
                                                                        + " - "
                                                                        + cuenta.getAlias()));

                        // =====================================================
                        // 18. CREAR TRANSACCIÓN - DEPÓSITO
                        // =====================================================

                        System.out.println(
                                        "\n========== 18. CREAR TRANSACCIÓN ==========");

                        Transaccion deposito = new Transaccion();

                        deposito.setFechaHora(LocalDateTime.now());
                        deposito.setMonto(new BigDecimal("25000"));
                        deposito.setTipoTransaccion(
                                        TipoTransaccion.DEPOSITO);
                        deposito.setEstadoTransaccion(
                                        EstadoTransaccion.COMPLETADA);

                        // Relación Transaccion -> CuentaFinanciera
                        deposito.setCuentaFinanciera(cajaAhorro);

                        deposito = transaccionService.saveTransaccion(deposito);

                        System.out.println("Depósito creado:");
                        System.out.println(deposito);

                        // =====================================================
                        // 19. CREAR TRANSACCIÓN - EXTRACCIÓN
                        // =====================================================

                        System.out.println(
                                        "\n========== 19. CREAR EXTRACCIÓN ==========");

                        Transaccion extraccion = new Transaccion();

                        extraccion.setFechaHora(LocalDateTime.now());
                        extraccion.setMonto(new BigDecimal("10000"));
                        extraccion.setTipoTransaccion(
                                        TipoTransaccion.EXTRACCION);
                        extraccion.setEstadoTransaccion(
                                        EstadoTransaccion.COMPLETADA);

                        extraccion.setCuentaFinanciera(cajaAhorro);

                        extraccion = transaccionService.saveTransaccion(extraccion);

                        System.out.println("Extracción creada:");
                        System.out.println(extraccion);

                        // =====================================================
                        // 20. BUSCAR TRANSACCIÓN POR ID
                        // =====================================================

                        System.out.println(
                                        "\n========== 20. BUSCAR TRANSACCIÓN ==========");

                        Optional<Transaccion> transaccionEncontrada = transaccionService.findById(
                                        deposito.getId());

                        System.out.println(transaccionEncontrada);

                        // =====================================================
                        // 21. BUSCAR TODAS LAS TRANSACCIONES
                        // =====================================================

                        System.out.println(
                                        "\n========== 21. TODAS LAS TRANSACCIONES ==========");

                        transaccionService.findAll()
                                        .forEach(System.out::println);

                        // =====================================================
                        // 22. ACTUALIZAR TRANSACCIÓN
                        // =====================================================

                        System.out.println(
                                        "\n========== 22. ACTUALIZAR TRANSACCIÓN ==========");

                        deposito.setMonto(new BigDecimal("30000"));

                        Optional<Transaccion> deposito2 = transaccionService.updateTransaccion(deposito.getId(),deposito);

                        System.out.println(deposito2);

                        // =====================================================
                        // 23. CARGAR TRANSACCIONES DE LA CUENTA
                        // =====================================================

                        // =====================================================
                        // 24. RELACIÓN TRANSACCIÓN -> CUENTA
                        // =====================================================

                        System.out.println(
                                        "\n========== 24. TRANSACCIÓN -> CUENTA ==========");

                        System.out.println(
                                        "Cuenta asociada al depósito:");

                        System.out.println(
                                        deposito.getCuentaFinanciera());

                        // =====================================================
                        // 25. ELIMINAR TRANSACCIONES
                        // =====================================================

                        System.out.println(
                                        "\n========== 25. ELIMINAR TRANSACCIONES ==========");

                        transaccionService.eliminarPorId(
                                        extraccion.getId());

                        transaccionService.eliminarPorId(
                                        deposito.getId());

                        System.out.println(
                                        "Transacciones eliminadas.");

                        // =====================================================
                        // 26. ELIMINAR CUENTAS
                        // =====================================================

                        System.out.println(
                                        "\n========== 26. ELIMINAR CUENTAS ==========");

                        cuentaCorrienteService.eliminarPorId(
                                        cuentaCorriente.getId());

                        cajaAhorroService.eliminarPorId(
                                        cajaAhorro.getId());

                        System.out.println(
                                        "Cuentas eliminadas.");

                        // =====================================================
                        // 27. ELIMINAR CLIENTES
                        // =====================================================

                        System.out.println(
                                        "\n========== 27. ELIMINAR CLIENTES ==========");

                        clienteService.eliminarPorId(
                                        cliente2.getId());

                        clienteService.eliminarPorId(
                                        cliente.getId());

                        System.out.println(
                                        "Clientes eliminados.");

                        // =====================================================
                        // FINAL
                        // =====================================================

                        System.out.println("\n==============================================");
                        System.out.println("       PRUEBA COMPLETA FINALIZADA");
                        System.out.println("==============================================");
                };
        }

}
