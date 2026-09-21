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

@SpringBootApplication
@EnableJpaAuditing
public class Tp2Application {

	public static void main(String[] args) {
		SpringApplication.run(Tp2Application.class, args);
	}
	
	@Bean
    CommandLineRunner cargarDatos(
            ClienteRepository clienteRepository,
            CuentaFinancieraRepository cuentaFinancieraRepository,
            CuentaCorrienteRepository cuentaCorrienteRepository,
            CajaAhorroRepository cajaAhorroRepository,
            TransaccionRepository transaccionRepository) {

        return args -> {

//            // =====================================================
//            // CLIENTES
//            // =====================================================
//
//            Cliente cliente1 = new Cliente();
//
//            cliente1.setCuil(20304050607L);
//            cliente1.setNombre("Nicolas Velasquez");
//            cliente1.setEmail("nicolas@gmail.com");
//            cliente1.setTelefono(388123456);
//            cliente1.setDireccion("San Salvador de Jujuy");
//            cliente1.setTitular(null);
//
//            clienteRepository.save(cliente1);
//
//
//            Cliente cliente2 = new Cliente();
//
//            cliente2.setCuil(27405060708L);
//            cliente2.setNombre("Maria Gonzalez");
//            cliente2.setEmail("maria@gmail.com");
//            cliente2.setTelefono(388987654);
//            cliente2.setDireccion("San Salvador de Jujuy");
//            cliente2.setTitular(cliente1);
//
//            clienteRepository.save(cliente2);
//
//
//            Cliente cliente3 = new Cliente();
//
//            cliente3.setCuil(23506070809L);
//            cliente3.setNombre("Juan Perez");
//            cliente3.setEmail("juan@gmail.com");
//            cliente3.setTelefono(388555555);
//            cliente3.setDireccion("Palpala");
//            cliente3.setTitular(cliente1);
//
//            clienteRepository.save(cliente3);
//
//
//            // =====================================================
//            // CUENTAS FINANCIERAS
//            // =====================================================
//
//            CuentaFinanciera financiera1 = new CuentaFinanciera();
//
//            financiera1.setCbu(1111111111L);
//            financiera1.setAlias("nicolas.cuenta.financiera");
//            financiera1.setSaldo(new BigDecimal("1111111111"));
//            financiera1.setEstadoCuenta(EstadoCuenta.ACTIVA);
//
//            financiera1.setCliente(cliente1);
//
//            cuentaFinancieraRepository.save(financiera1);
//
//
//            CuentaFinanciera financiera2 = new CuentaFinanciera();
//
//            financiera2.setCbu(2222222222L);
//            financiera2.setAlias("maria.cuenta.financiera");
//            financiera2.setSaldo(new BigDecimal("2222222222"));
//            financiera2.setEstadoCuenta(EstadoCuenta.SUSPENDIDA);
//
//            financiera2.setCliente(cliente2);
//
//            cuentaFinancieraRepository.save(financiera2);
//
//
//            CuentaFinanciera financiera3 = new CuentaFinanciera();
//
//            financiera3.setCbu(3333333333L);
//            financiera3.setAlias("juan.cuenta.financiera");
//            financiera3.setSaldo(new BigDecimal("3333333333"));
//            financiera3.setEstadoCuenta(EstadoCuenta.BLOQUEADA);
//
//            financiera3.setCliente(cliente3);
//
//            cuentaFinancieraRepository.save(financiera3);
//
//
//            // =====================================================
//            // CUENTAS CORRIENTES
//            // =====================================================
//
//            CuentaCorriente corriente1 = new CuentaCorriente();
//
//            corriente1.setCbu(285059094009041234L);
//            corriente1.setAlias("nicolas.cuenta");
//            corriente1.setSaldo(new BigDecimal("150000"));
//            corriente1.setEstadoCuenta(EstadoCuenta.ACTIVA);
//
//            corriente1.setTasaInteresAnual(new BigDecimal("35.50"));
//            corriente1.setCupoLimiteMensual(500000);
//
//            corriente1.setCliente(cliente1);
//
//            cuentaCorrienteRepository.save(corriente1);
//
//
//            CuentaCorriente corriente2 = new CuentaCorriente();
//
//            corriente2.setCbu(285059094009041235L);
//            corriente2.setAlias("maria.cuenta");
//            corriente2.setSaldo(new BigDecimal("250000"));
//            corriente2.setEstadoCuenta(EstadoCuenta.SUSPENDIDA);
//
//            corriente2.setTasaInteresAnual(new BigDecimal("42.00"));
//            corriente2.setCupoLimiteMensual(300000);
//
//            corriente2.setCliente(cliente2);
//
//            cuentaCorrienteRepository.save(corriente2);
//
//
//            CuentaCorriente corriente3 = new CuentaCorriente();
//
//            corriente3.setCbu(285059094009041236L);
//            corriente3.setAlias("juan.cuenta");
//            corriente3.setSaldo(new BigDecimal("50000"));
//            corriente3.setEstadoCuenta(EstadoCuenta.ACTIVA);
//
//            corriente3.setTasaInteresAnual(new BigDecimal("28.00"));
//            corriente3.setCupoLimiteMensual(100000);
//
//            corriente3.setCliente(cliente3);
//
//            cuentaCorrienteRepository.save(corriente3);
//
//
//            // =====================================================
//            // CAJAS DE AHORRO
//            // =====================================================
//
//            CajaAhorro ahorro1 = new CajaAhorro();
//
//            ahorro1.setCbu(285059094009041237L);
//            ahorro1.setAlias("nicolas.ahorro");
//            ahorro1.setSaldo(new BigDecimal("500000"));
//            ahorro1.setEstadoCuenta(EstadoCuenta.ACTIVA);
//
//            ahorro1.setMargenDescuento(new BigDecimal("15.00"));
//            ahorro1.setComisionMantenimientoMensual(
//                new BigDecimal("1500")
//            );
//
//            ahorro1.setCliente(cliente1);
//
//            cajaAhorroRepository.save(ahorro1);
//
//
//            CajaAhorro ahorro2 = new CajaAhorro();
//
//            ahorro2.setCbu(285059094009041238L);
//            ahorro2.setAlias("maria.ahorro");
//            ahorro2.setSaldo(new BigDecimal("300000"));
//            ahorro2.setEstadoCuenta(EstadoCuenta.ACTIVA);
//
//            ahorro2.setMargenDescuento(new BigDecimal("8.00"));
//            ahorro2.setComisionMantenimientoMensual(
//                new BigDecimal("2500")
//            );
//
//            ahorro2.setCliente(cliente2);
//
//            cajaAhorroRepository.save(ahorro2);
//
//
//            CajaAhorro ahorro3 = new CajaAhorro();
//
//            ahorro3.setCbu(285059094009041239L);
//            ahorro3.setAlias("juan.ahorro");
//            ahorro3.setSaldo(new BigDecimal("100000"));
//            ahorro3.setEstadoCuenta(EstadoCuenta.BLOQUEADA);
//
//            ahorro3.setMargenDescuento(new BigDecimal("20.00"));
//            ahorro3.setComisionMantenimientoMensual(
//                new BigDecimal("1000")
//            );
//
//            ahorro3.setCliente(cliente3);
//
//            cajaAhorroRepository.save(ahorro3);
//
//
//            // =====================================================
//            // TRANSACCIONES
//            // =====================================================
//
//            Transaccion t1 = new Transaccion();
//
//            t1.setFechaHora(
//                LocalDateTime.now().minusDays(5)
//            );
//
//            t1.setMonto(new BigDecimal("50000"));
//
//            t1.setTipoTransaccion(
//                TipoTransaccion.DEPOSITO
//            );
//
//            t1.setEstadoTransaccion(
//                EstadoTransaccion.COMPLETADA
//            );
//
//            t1.setCuentaFinanciera(corriente1);
//
//            transaccionRepository.save(t1);
//
//
//            Transaccion t2 = new Transaccion();
//
//            t2.setFechaHora(
//                LocalDateTime.now().minusDays(3)
//            );
//
//            t2.setMonto(new BigDecimal("10000"));
//
//            t2.setTipoTransaccion(
//                TipoTransaccion.EXTRACCION
//            );
//
//            t2.setEstadoTransaccion(
//                EstadoTransaccion.COMPLETADA
//            );
//
//            t2.setCuentaFinanciera(corriente1);
//
//            transaccionRepository.save(t2);
//
//
//            Transaccion t3 = new Transaccion();
//
//            t3.setFechaHora(
//                LocalDateTime.now().minusDays(2)
//            );
//
//            t3.setMonto(new BigDecimal("75000"));
//
//            t3.setTipoTransaccion(
//                TipoTransaccion.TRANSFERENCIA_ENVIADA
//            );
//
//            t3.setEstadoTransaccion(
//                EstadoTransaccion.PENDIENTE
//            );
//
//            t3.setCuentaFinanciera(corriente2);
//
//            transaccionRepository.save(t3);
//
//
//            Transaccion t4 = new Transaccion();
//
//            t4.setFechaHora(
//                LocalDateTime.now().minusHours(10)
//            );
//
//            t4.setMonto(new BigDecimal("100000"));
//
//            t4.setTipoTransaccion(
//                TipoTransaccion.TRANSFERENCIA_RECIBIDA
//            );
//
//            t4.setEstadoTransaccion(
//                EstadoTransaccion.COMPLETADA
//            );
//
//            t4.setCuentaFinanciera(ahorro1);
//
//            transaccionRepository.save(t4);
//
//
//            Transaccion t5 = new Transaccion();
//
//            t5.setFechaHora(
//                LocalDateTime.now().minusHours(2)
//            );
//
//            t5.setMonto(new BigDecimal("25000"));
//
//            t5.setTipoTransaccion(
//                TipoTransaccion.EXTRACCION
//            );
//
//            t5.setEstadoTransaccion(
//                EstadoTransaccion.RECHAZADA
//            );
//
//            t5.setCuentaFinanciera(ahorro2);
//
//            transaccionRepository.save(t5);
//

            // =====================================================
            // PROBAR QUERY METHODS
            // =====================================================

            System.out.println("\n========================================");
            System.out.println("     PRUEBA DE QUERY METHODS");
            System.out.println("========================================");

            System.out.println("\n--- findAll ---");

            clienteRepository
                .findAll()
                .forEach(cliente ->
                    System.out.println(
                        "Datos: "
                        + "ID: " + cliente.getId()
                        + " Nombre: " + cliente.getNombre()
                    )
                );

            // -----------------------------------------------------
            // Cliente: findByCuil
            // -----------------------------------------------------

            System.out.println("\n--- findByCuil ---");

            clienteRepository
                .findByCuil(20304050607L)
                .ifPresent(cliente ->
                    System.out.println(
                        "Encontrado: "
                        + cliente.getNombre()
                    )
                );


            // -----------------------------------------------------
            // Cliente: findByTitular
            // -----------------------------------------------------

            System.out.println("\n--- findByTitular ---");

            clienteRepository
                .findByTitular(null)
                .forEach(cliente ->
                    System.out.println(
                        "Titular: "
                        + cliente.getNombre()
                    )
                );


            // -----------------------------------------------------
            // CajaAhorro:
            // findByMargenDescuentoGreaterThan
            // -----------------------------------------------------

            System.out.println(
                "\n--- findByMargenDescuentoGreaterThan ---"
            );

            cajaAhorroRepository
                .findByMargenDescuentoGreaterThan(
                    new BigDecimal("10")
                )
                .forEach(caja ->
                    System.out.println(
                        "Caja: "
                        + caja.getAlias()
                        + " | Margen: "
                        + caja.getMargenDescuento()
                    )
                );


            // -----------------------------------------------------
            // CajaAhorro:
            // findByComisionMantenimientoMensualLessThan
            // -----------------------------------------------------

            System.out.println(
                "\n--- findByComisionMantenimientoMensualLessThan ---"
            );

            cajaAhorroRepository
                .findByComisionMantenimientoMensualLessThan(
                    new BigDecimal("2000")
                )
                .forEach(caja ->
                    System.out.println(
                        "Caja: "
                        + caja.getAlias()
                        + " | Comisión: "
                        + caja.getComisionMantenimientoMensual()
                    )
                );


            // -----------------------------------------------------
            // CuentaCorriente:
            // findByTasaInteresAnualGreaterThan
            // -----------------------------------------------------

            System.out.println(
                "\n--- findByTasaInteresAnualGreaterThan ---"
            );

            cuentaCorrienteRepository
                .findByTasaInteresAnualGreaterThan(
                    new BigDecimal("30")
                )
                .forEach(cuenta ->
                    System.out.println(
                        "Cuenta: "
                        + cuenta.getAlias()
                        + " | Tasa: "
                        + cuenta.getTasaInteresAnual()
                    )
                );


            // -----------------------------------------------------
            // CuentaCorriente:
            // findByCupoLimiteMensualLessThan
            // -----------------------------------------------------

            System.out.println(
                "\n--- findByCupoLimiteMensualLessThan ---"
            );

            cuentaCorrienteRepository
                .findByCupoLimiteMensualLessThan(400000)
                .forEach(cuenta ->
                    System.out.println(
                        "Cuenta: "
                        + cuenta.getAlias()
                        + " | Cupo: "
                        + cuenta.getCupoLimiteMensual()
                    )
                );


            // -----------------------------------------------------
            // CuentaFinanciera:
            // findByCbu
            // -----------------------------------------------------

            System.out.println("\n--- findByCbu ---");

            cuentaFinancieraRepository
                .findByCbu(285059094009041234L)
                .ifPresent(cuenta ->
                    System.out.println(
                        "Encontrada: "
                        + cuenta.getAlias()
                    )
                );


            // -----------------------------------------------------
            // CuentaFinanciera:
            // findByEstadoCuenta
            // -----------------------------------------------------

            System.out.println(
                "\n--- findByEstadoCuenta ---"
            );

            cuentaFinancieraRepository
                .findByEstadoCuenta(EstadoCuenta.ACTIVA)
                .forEach(cuenta ->
                    System.out.println(
                        "Cuenta activa: "
                        + cuenta.getAlias()
                    )
                );


            // -----------------------------------------------------
            // Transaccion:
            // findByFechaHoraBetween
            // -----------------------------------------------------

            System.out.println(
                "\n--- findByFechaHoraBetween ---"
            );

            LocalDateTime desde =
                LocalDateTime.now().minusDays(7);

            LocalDateTime hasta =
                LocalDateTime.now();

            transaccionRepository
                .findByFechaHoraBetween(desde, hasta)
                .forEach(transaccion ->
                    System.out.println(
                        "Transacción: "
                        + transaccion.getId()
                        + " | Fecha: "
                        + transaccion.getFechaHora()
                        + " | Monto: "
                        + transaccion.getMonto()
                    )
                );


            // -----------------------------------------------------
            // Transaccion:
            // findByEstadoTransaccion
            // -----------------------------------------------------

            System.out.println(
                "\n--- findByEstadoTransaccion ---"
            );

            transaccionRepository
                .findByEstadoTransaccion(
                    EstadoTransaccion.COMPLETADA
                )
                .forEach(transaccion ->
                    System.out.println(
                        "Transacción completada: "
                        + transaccion.getId()
                        + " | Monto: "
                        + transaccion.getMonto()
                    )
                );
            
//          Cliente clienteTest = new Cliente();
//
//          clienteTest.setCuil(1111111L);
//          clienteTest.setNombre("Nicolas Velasquez");
//          clienteTest.setEmail("nicolas@gmail.com");
//          clienteTest.setTelefono(388123456);
//          clienteTest.setDireccion("San Salvador de Jujuy");
//          clienteTest.setTitular(true);
//
//          clienteRepository.save(clienteTest);
            
//            clienteRepository.deleteById(4L);


            System.out.println(
                "\n========================================"
            );

            System.out.println(
                "       PRUEBAS FINALIZADAS"
            );

            System.out.println(
                "========================================\n"
            );
        };
    }

}
