package ar.edu.unju.fi.tp2.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.models.CajaAhorro;
import ar.edu.unju.fi.tp2.models.CajaAhorro;
import ar.edu.unju.fi.tp2.models.CajaAhorro;

@DataJpaTest
class CajaAhorroRepositoryTest {

	@Autowired
	private CajaAhorroRepository cajaAhorroRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	private CajaAhorro cajaAhorro1;
	private CajaAhorro cajaAhorro2;
	
	@BeforeEach
	void setUp() throws Exception {
		cajaAhorro1 = new CajaAhorro();
		cajaAhorro1.setCbu(111L);
		cajaAhorro1.setAlias("test.111.test111");
		cajaAhorro1.setSaldo(new BigDecimal(111111));
		cajaAhorro1.setEstadoCuenta(EstadoCuenta.ACTIVA);
		cajaAhorro1.setMargenDescuento(new BigDecimal(111));
		cajaAhorro1.setComisionMantenimientoMensual(new BigDecimal(111));
		testEntityManager.persist(cajaAhorro1);
		
		cajaAhorro2 = new CajaAhorro();
		cajaAhorro2.setCbu(222L);
		cajaAhorro2.setAlias("test.222.test222");
		cajaAhorro2.setSaldo(new BigDecimal(222222));
		cajaAhorro2.setEstadoCuenta(EstadoCuenta.ACTIVA);
		cajaAhorro2.setMargenDescuento(new BigDecimal(222));
		cajaAhorro2.setComisionMantenimientoMensual(new BigDecimal(222));
		testEntityManager.persist(cajaAhorro2);
	}

	@Test
	public void findByMargenDescuentoGreaterFound() {
		System.out.println("Test findByMargenDescuentoGreaterFound");
		
		List<CajaAhorro> found = cajaAhorroRepository.findByMargenDescuentoGreaterThan(new BigDecimal(10));
		
		assertThat(found)
			.isNotEmpty()
			.hasSize(2)
			.extracting(CajaAhorro::getAlias)
			.containsExactlyInAnyOrder("test.111.test111", "test.222.test222");
		
		System.out.println("Cuenta Encontrada");
		for (CajaAhorro cajaAhorro : found) {
			System.out.println(cajaAhorro.toString());
		}
	}
	
	@Test
	public void findByMargenDescuentoGreaterNotFound() {
		System.out.println("Test findByMargenDescuentoGreaterNotFound");
		
		List<CajaAhorro> found = cajaAhorroRepository.findByMargenDescuentoGreaterThan(new BigDecimal(9999999));
		
		assertThat(found)
			.isNotNull()
			.isEmpty();
		
		System.out.println("Cuenta No Encontrada");
	}
	
	@Test
	public void findByComisionMantenimientoMensualLessFound() {
		System.out.println("Test findByComisionMantenimientoMensualLessFound");
		
		List<CajaAhorro> found = cajaAhorroRepository.findByComisionMantenimientoMensualLessThan(new BigDecimal(9999999));
		
		assertThat(found)
			.isNotEmpty()
			.hasSize(2)
			.extracting(CajaAhorro::getAlias)
			.containsExactlyInAnyOrder("test.111.test111", "test.222.test222");
		
		System.out.println("Cuenta Encontrada");
		for (CajaAhorro cajaAhorro : found) {
			System.out.println(cajaAhorro.toString());
		}
	}
	
	@Test
	public void findByComisionMantenimientoMensualLessNotFound() {
		System.out.println("Test findByComisionMantenimientoMensualLessNotFound");
		
		List<CajaAhorro> found = cajaAhorroRepository.findByComisionMantenimientoMensualLessThan(new BigDecimal(10));
		
		assertThat(found)
			.isNotNull()
			.isEmpty();
		
		System.out.println("Cuenta No Encontrada");
	}

}
