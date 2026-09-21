package ar.edu.unju.fi.tp2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

            System.out.println("\n========================================");
            System.out.println("       PRUEBA DE TODOS LOS SERVICES");
            System.out.println("========================================\n");

            // =====================================================
            // 1. CLIENTE
            // =====================================================

            System.out.println("========== CLIENTE ==========");

            Cliente cliente = new Cliente();

            cliente.setCuil(20301234567L);
            cliente.setNombre("Maxi Flores");
            cliente.setRazonSocial("Maxi Flores");
            cliente.setEmail("maxi@gmail.com");
            cliente.setTelefono(38812345);
            cliente.setDireccion("Jujuy");

            // SAVE
            cliente = clienteService.saveCliente(cliente);

            System.out.println("SAVE:");
            System.out.println(cliente);

            // FIND BY ID
            System.out.println("\nFIND BY ID:");
            clienteService.findById(cliente.getId())
                    .ifPresent(System.out::println);

            // FIND BY CUIL
            System.out.println("\nFIND BY CUIL:");
            clienteService.findByCuil(cliente.getCuil())
                    .ifPresent(System.out::println);

            // FIND ALL
            System.out.println("\nFIND ALL:");
            clienteService.findAll()
                    .forEach(System.out::println);

            // UPDATE
            Cliente clienteModificado = new Cliente();

            clienteModificado.setCuil(cliente.getCuil());
            clienteModificado.setNombre("Maximiliano Flores");
            clienteModificado.setRazonSocial("Maximiliano Flores");
            clienteModificado.setEmail("maximiliano@gmail.com");
            clienteModificado.setTelefono(38999999);
            clienteModificado.setDireccion("San Salvador de Jujuy");

            System.out.println("\nUPDATE:");

            clienteService.updateCliente(
                    cliente.getId(),
                    clienteModificado
            ).ifPresent(System.out::println);


            // =====================================================
            // 2. CAJA DE AHORRO
            // =====================================================

            System.out.println("\n========== CAJA DE AHORRO ==========");

            CajaAhorro cajaAhorro = new CajaAhorro();

            cajaAhorro.setCbu(2850590940090412345L);
            cajaAhorro.setAlias("maxi.ahorro");
            cajaAhorro.setSaldo(new BigDecimal("150000"));
            cajaAhorro.setEstadoCuenta(EstadoCuenta.ACTIVA);
            cajaAhorro.setCliente(cliente);

            cajaAhorro.setMargenDescuento(
                    new BigDecimal("50000")
            );

            cajaAhorro.setComisionMantenimientoMensual(
                    new BigDecimal("2500")
            );

            // SAVE
            cajaAhorro = cajaAhorroService.saveCajaAhorro(cajaAhorro);

            System.out.println("SAVE:");
            System.out.println(cajaAhorro);

            // FIND BY ID
            System.out.println("\nFIND BY ID:");

            cajaAhorroService.findById(cajaAhorro.getId())
                    .ifPresent(System.out::println);

            // FIND ALL
            System.out.println("\nFIND ALL:");

            cajaAhorroService.findAll()
                    .forEach(System.out::println);

            // UPDATE
            CajaAhorro cajaAhorroModificada = new CajaAhorro();

            cajaAhorroModificada.setCbu(
                    cajaAhorro.getCbu()
            );

            cajaAhorroModificada.setAlias(
                    "maxi.ahorro.nuevo"
            );

            cajaAhorroModificada.setSaldo(
                    new BigDecimal("200000")
            );

            cajaAhorroModificada.setEstadoCuenta(
                    EstadoCuenta.ACTIVA
            );

            cajaAhorroModificada.setCliente(cliente);

            cajaAhorroModificada.setMargenDescuento(
                    new BigDecimal("60000")
            );

            cajaAhorroModificada.setComisionMantenimientoMensual(
                    new BigDecimal("3000")
            );

            System.out.println("\nUPDATE:");

            cajaAhorroService.updateCajaAhorro(
                    cajaAhorro.getId(),
                    cajaAhorroModificada
            ).ifPresent(System.out::println);


            // =====================================================
            // 3. CUENTA CORRIENTE
            // =====================================================

            System.out.println("\n========== CUENTA CORRIENTE ==========");

            CuentaCorriente cuentaCorriente = new CuentaCorriente();

            cuentaCorriente.setCbu(2850590940090498765L);
            cuentaCorriente.setAlias("maxi.corriente");
            cuentaCorriente.setSaldo(new BigDecimal("80000"));
            cuentaCorriente.setEstadoCuenta(EstadoCuenta.ACTIVA);
            cuentaCorriente.setCliente(cliente);

            cuentaCorriente.setTasaInteresAnual(
                    new BigDecimal("35.5")
            );

            cuentaCorriente.setCupoLimiteMensual(50000);

            // SAVE
            cuentaCorriente =
                    cuentaCorrienteService.saveCuentaCorriente(
                            cuentaCorriente
                    );

            System.out.println("SAVE:");
            System.out.println(cuentaCorriente);

            // FIND BY ID
            System.out.println("\nFIND BY ID:");

            cuentaCorrienteService.findById(
                    cuentaCorriente.getId()
            ).ifPresent(System.out::println);

            // FIND ALL
            System.out.println("\nFIND ALL:");

            cuentaCorrienteService.findAll()
                    .forEach(System.out::println);

            // UPDATE
            CuentaCorriente cuentaCorrienteModificada =
                    new CuentaCorriente();

            cuentaCorrienteModificada.setCbu(
                    cuentaCorriente.getCbu()
            );

            cuentaCorrienteModificada.setAlias(
                    "maxi.corriente.nuevo"
            );

            cuentaCorrienteModificada.setSaldo(
                    new BigDecimal("100000")
            );

            cuentaCorrienteModificada.setEstadoCuenta(
                    EstadoCuenta.ACTIVA
            );

            cuentaCorrienteModificada.setCliente(cliente);

            cuentaCorrienteModificada.setTasaInteresAnual(
                    new BigDecimal("40")
            );

            cuentaCorrienteModificada.setCupoLimiteMensual(
                    60000
            );

            System.out.println("\nUPDATE:");

            cuentaCorrienteService.updateCuentaCorriente(
                    cuentaCorriente.getId(),
                    cuentaCorrienteModificada
            ).ifPresent(System.out::println);


            // =====================================================
            // 4. CUENTA FINANCIERA
            // =====================================================

            System.out.println("\n========== CUENTA FINANCIERA ==========");

            // FIND BY ID
            System.out.println("\nFIND BY ID:");

            cuentaFinancieraService.findById(
                    cajaAhorro.getId()
            ).ifPresent(System.out::println);

            // FIND ALL
            System.out.println("\nFIND ALL:");

            cuentaFinancieraService.findAll()
                    .forEach(System.out::println);


            // =====================================================
            // 5. TRANSACCION
            // =====================================================

            System.out.println("\n========== TRANSACCION ==========");

            Transaccion deposito = new Transaccion();

            deposito.setFechaHora(LocalDateTime.now());
            deposito.setMonto(new BigDecimal("50000"));
            deposito.setTipoTransaccion(
                    TipoTransaccion.DEPOSITO
            );
            deposito.setEstadoTransaccion(
                    EstadoTransaccion.COMPLETADA
            );
            deposito.setCuentaFinanciera(cajaAhorro);

            // SAVE
            deposito = transaccionService.saveTransaccion(deposito);

            System.out.println("SAVE:");
            System.out.println(deposito);

            // FIND BY ID
            System.out.println("\nFIND BY ID:");

            transaccionService.findById(
                    deposito.getId()
            ).ifPresent(System.out::println);

            // FIND ALL
            System.out.println("\nFIND ALL:");

            transaccionService.findAll()
                    .forEach(System.out::println);

            // UPDATE
            Transaccion transaccionModificada =
                    new Transaccion();

            transaccionModificada.setFechaHora(
                    deposito.getFechaHora()
            );

            transaccionModificada.setMonto(
                    new BigDecimal("75000")
            );

            transaccionModificada.setTipoTransaccion(
                    TipoTransaccion.DEPOSITO
            );

            transaccionModificada.setEstadoTransaccion(
                    EstadoTransaccion.COMPLETADA
            );

            transaccionModificada.setCuentaFinanciera(
                    cajaAhorro
            );

            System.out.println("\nUPDATE:");

            transaccionService.updateTransaccion(
                    deposito.getId(),
                    transaccionModificada
            ).ifPresent(System.out::println);


            // =====================================================
            // 6. ELIMINACIONES
            // =====================================================

            System.out.println("\n========== ELIMINACIONES ==========");

            System.out.println("\nELIMINAR TRANSACCION:");

            transaccionService.eliminarPorId(
                    deposito.getId()
            ).ifPresent(System.out::println);


            System.out.println("\nELIMINAR CUENTA CORRIENTE:");

            cuentaCorrienteService.eliminarPorId(
                    cuentaCorriente.getId()
            ).ifPresent(System.out::println);


            System.out.println("\nELIMINAR CAJA DE AHORRO:");

            cajaAhorroService.eliminarPorId(
                    cajaAhorro.getId()
            ).ifPresent(System.out::println);


            System.out.println("\nELIMINAR CLIENTE:");

            clienteService.eliminarPorId(
                    cliente.getId()
            ).ifPresent(System.out::println);


            // =====================================================
            // FIN
            // =====================================================

            System.out.println("\n========================================");
            System.out.println("       PRUEBA FINALIZADA");
            System.out.println("========================================");
        };
    }    

}
