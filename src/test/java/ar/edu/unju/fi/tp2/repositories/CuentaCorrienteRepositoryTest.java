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
import ar.edu.unju.fi.tp2.models.CuentaCorriente;

@DataJpaTest
class CuentaCorrienteRepositoryTest {

	@Autowired
	private CuentaCorrienteRepository cuentaCorrienteRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	private CuentaCorriente cuentaCorriente1;
	private CuentaCorriente cuentaCorriente2;
	
	@BeforeEach
	void setUp() throws Exception {
		cuentaCorriente1 = new CuentaCorriente();
		cuentaCorriente1.setCbu(111L);
		cuentaCorriente1.setAlias("test.111.test111");
		cuentaCorriente1.setSaldo(new BigDecimal(111111));
		cuentaCorriente1.setEstadoCuenta(EstadoCuenta.ACTIVA);
		cuentaCorriente1.setTasaInteresAnual(new BigDecimal(111));
		cuentaCorriente1.setCupoLimiteMensual(111);
		testEntityManager.persist(cuentaCorriente1);
		
		cuentaCorriente2 = new CuentaCorriente();
		cuentaCorriente2.setCbu(222L);
		cuentaCorriente2.setAlias("test.222.test222");
		cuentaCorriente2.setSaldo(new BigDecimal(222222));
		cuentaCorriente2.setEstadoCuenta(EstadoCuenta.ACTIVA);
		cuentaCorriente2.setTasaInteresAnual(new BigDecimal(222));
		cuentaCorriente2.setCupoLimiteMensual(222);
		testEntityManager.persist(cuentaCorriente2);
	}

	@Test
	public void findByTasaInteresAnualGreaterThanFound() {
		System.out.println("Test findByTasaInteresAnualGreaterThanFound");
		
		List<CuentaCorriente> found = cuentaCorrienteRepository.findByTasaInteresAnualGreaterThan(new BigDecimal(10));
		
		assertThat(found)
			.isNotEmpty()
			.hasSize(2)
			.extracting(CuentaCorriente::getAlias)
			.containsExactlyInAnyOrder("test.111.test111", "test.222.test222");
		
		System.out.println("Cuenta Encontrada");
		for (CuentaCorriente cuentaCorriente : found) {
			System.out.println(cuentaCorriente.toString());
		}
	}
	
	@Test
	public void findByTasaInteresAnualGreaterThanNotFound() {
		System.out.println("Test findByTasaInteresAnualGreaterThanNotFound");
		
		List<CuentaCorriente> found = cuentaCorrienteRepository.findByTasaInteresAnualGreaterThan(new BigDecimal(9999999));
		
		assertThat(found)
			.isNotNull()
			.isEmpty();
		
		System.out.println("Cuenta No Encontrada");
	}
	
	@Test
	public void findByCupoLimiteMensualLessThanFound() {
		System.out.println("Test findByCupoLimiteMensualLessThanFound");
		
		List<CuentaCorriente> found = cuentaCorrienteRepository.findByCupoLimiteMensualLessThan(9999999);
		
		assertThat(found)
			.isNotEmpty()
			.hasSize(2)
			.extracting(CuentaCorriente::getAlias)
			.containsExactlyInAnyOrder("test.111.test111", "test.222.test222");
		
		System.out.println("Cuenta Encontrada");
		for (CuentaCorriente cuentaCorriente : found) {
			System.out.println(cuentaCorriente.toString());
		}
	}
	
	@Test
	public void findByCupoLimiteMensualLessThanNotFound() {
		System.out.println("Test findByCupoLimiteMensualLessThanNotFound");
		
		List<CuentaCorriente> found = cuentaCorrienteRepository.findByCupoLimiteMensualLessThan(10);
		
		assertThat(found)
			.isNotNull()
			.isEmpty();
		
		System.out.println("Cuenta No Encontrada");
	}

}
