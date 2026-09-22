package ar.edu.unju.fi.tp2.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import ar.edu.unju.fi.tp2.models.CuentaFinanciera;

@DataJpaTest
class CuentaFinancieraRepositoryTest {

	@Autowired
	private CuentaFinancieraRepository cuentaFinancieraRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	private CuentaFinanciera cuentaFinanciera1;
	private CuentaFinanciera cuentaFinanciera2;
	
	@BeforeEach
	void setUp() throws Exception {
		cuentaFinanciera1 = CuentaFinanciera.builder()
				.cbu(111L)
				.alias("test.111.test111")
				.saldo(new BigDecimal(111111))
				.estadoCuenta(EstadoCuenta.ACTIVA)
				.build();
		testEntityManager.persist(cuentaFinanciera1);
		
		cuentaFinanciera2 = CuentaFinanciera.builder()
				.cbu(222L)
				.alias("test.222.test222")
				.saldo(new BigDecimal(222222))
				.estadoCuenta(EstadoCuenta.ACTIVA)
				.build();
		testEntityManager.persist(cuentaFinanciera2);
	}

	@Test
	public void findByCbuFound() {
		System.out.println("Test findByCbuFound");
		
		Optional<CuentaFinanciera> found = cuentaFinancieraRepository.findByCbu(111L);
		
		assertThat(found).isPresent();
		assertThat(found.get().getAlias()).isEqualTo("test.111.test111");
		
		System.out.println("Cuenta Encontrada");
		System.out.println(found.get().toString());
	}
	
	@Test
	public void findByCbuNotFound() {
		System.out.println("Test findByCbuNotFound");
		
		Optional<CuentaFinanciera> found = cuentaFinancieraRepository.findByCbu(333L);
		
		assertThat(found).isEmpty();
		
		System.out.println("Cuenta No Encontrada");
	}
	
	@Test
	public void findByEstadoCuentaFound() {
		System.out.println("Test findByEstadoCuentaFound");
		
		List<CuentaFinanciera> found = cuentaFinancieraRepository.findByEstadoCuenta(EstadoCuenta.ACTIVA);
		
		assertThat(found)
			.isNotEmpty()
			.hasSize(2)
			.extracting(CuentaFinanciera::getAlias)
			.containsExactlyInAnyOrder("test.111.test111", "test.222.test222");
		
		System.out.println("Cuenta Encontrada");
		for (CuentaFinanciera cuentaFinanciera : found) {
			System.out.println(cuentaFinanciera.toString());
		}
	}
	
	@Test
	public void findByEstadoCuentaNotFound() {
		System.out.println("Test findByEstadoCuentaNotFound");
		
		List<CuentaFinanciera> found = cuentaFinancieraRepository.findByEstadoCuenta(EstadoCuenta.SUSPENDIDA);
		
		assertThat(found)
			.isNotNull()
			.isEmpty();
		
		System.out.println("Cuenta No Encontrada");
	}

}
