package ar.edu.unju.fi.tp2.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;
import ar.edu.unju.fi.tp2.enums.TipoTransaccion;
import ar.edu.unju.fi.tp2.models.Transaccion;

@DataJpaTest
class TransaccionRepositoryTest {

	@Autowired
	private TransaccionRepository transaccionRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	private Transaccion transaccion1;
	private Transaccion transaccion2;
	private Transaccion transaccion3;
	private Transaccion transaccion4;
	
	@BeforeEach
	void setUp() throws Exception {
		transaccion1 = Transaccion.builder()
				.fechaHora(LocalDateTime.now().minusHours(1))
				.monto(new BigDecimal(10000))
				.tipoTransaccion(TipoTransaccion.DEPOSITO)
				.estadoTransaccion(EstadoTransaccion.COMPLETADA)
				.build();
		testEntityManager.persist(transaccion1);
		

		transaccion2 = Transaccion.builder()
				.fechaHora(LocalDateTime.now().minusHours(2))
				.monto(new BigDecimal(20000))
				.tipoTransaccion(TipoTransaccion.DEPOSITO)
				.estadoTransaccion(EstadoTransaccion.RECHAZADA)
				.build();
		testEntityManager.persist(transaccion2);
		

		transaccion3 = Transaccion.builder()
				.fechaHora(LocalDateTime.now().minusDays(1))
				.monto(new BigDecimal(30000))
				.tipoTransaccion(TipoTransaccion.EXTRACCION)
				.estadoTransaccion(EstadoTransaccion.COMPLETADA)
				.build();
		testEntityManager.persist(transaccion3);
		

		transaccion4 = Transaccion.builder()
				.fechaHora(LocalDateTime.now().minusDays(2))
				.monto(new BigDecimal(40000))
				.tipoTransaccion(TipoTransaccion.EXTRACCION)
				.estadoTransaccion(EstadoTransaccion.RECHAZADA)
				.build();
		testEntityManager.persist(transaccion4);		
	}

	@Test
	public void findByFechaHoraBetweenFound() {
		System.out.println("Test findByFechaHoraBetweenFound");
		
		List<Transaccion> found = transaccionRepository.findByFechaHoraBetween(LocalDateTime.now().minusHours(12), LocalDateTime.now());
		
		assertThat(found)
			.isNotEmpty()
			.hasSize(2)
			.extracting(Transaccion::getMonto)
			.containsExactlyInAnyOrder(new BigDecimal(10000), new BigDecimal(20000));
		
		System.out.println("Transaccion Encontrada");
		for (Transaccion transaccion : found) {
			System.out.println(transaccion.toString());
		}
	}
	
	@Test
	public void findByFechaHoraBetweenNotFound() {
		System.out.println("Test findByFechaHoraBetweenNotFound");
		
		List<Transaccion> found = transaccionRepository.findByFechaHoraBetween(LocalDateTime.now(), LocalDateTime.now().plusHours(12));
		
		assertThat(found)
			.isNotNull()
			.isEmpty();
		
		System.out.println("Transaccion No Encontrada");
	}
	
	@Test
	public void findByEstadoTransaccionFound() {
		System.out.println("Test findByEstadoTransaccionFound");
		
		List<Transaccion> found = transaccionRepository.findByEstadoTransaccion(EstadoTransaccion.COMPLETADA);
		
		assertThat(found)
			.isNotEmpty()
			.hasSize(2)
			.extracting(Transaccion::getMonto)
			.containsExactlyInAnyOrder(new BigDecimal(10000), new BigDecimal(30000));
		
		System.out.println("Transaccion Encontrada");
		for (Transaccion transaccion : found) {
			System.out.println(transaccion.toString());
		}
	}
	
	@Test
	public void findByEstadoTransaccionNotFound() {
		System.out.println("Test findByEstadoTransaccionNotFound");
		
		List<Transaccion> found = transaccionRepository.findByEstadoTransaccion(EstadoTransaccion.PENDIENTE);
		
		assertThat(found)
			.isNotNull()
			.isEmpty();
		
		System.out.println("Transaccion No Encontrada");
	}

}
