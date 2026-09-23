package ar.edu.unju.fi.tp2.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import ar.edu.unju.fi.tp2.models.Cliente;

@DataJpaTest
class ClienteRepositoryTest {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;

	private Cliente cliente1;
	private Cliente cliente2;
	
	@BeforeEach
	void setUp() throws Exception {
		cliente1 = Cliente.builder()
				.cuil(111L)
				.nombre("test1")
				.email("test1@test1.com")
				.telefono(111)
				.direccion("test test test 111")
				.titular(null)
				.build();
		testEntityManager.persist(cliente1);
		
		cliente2 = Cliente.builder()
				.cuil(222L)
				.nombre("test2")
				.email("test2@test2.com")
				.telefono(222)
				.direccion("test test test 222")
				.titular(cliente1)
				.build();
		testEntityManager.persist(cliente2);
	}
	
	@Test
	public void findByCuilFound() {
		System.out.println("Test findByCuilFound");
		
		Optional<Cliente> found = clienteRepository.findByCuil(111L);
		
		assertThat(found).isPresent();
		assertThat(found.get().getNombre()).isEqualTo("test1");
		
		System.out.println("Cliente Encontrado");
		System.out.println(found.get().toString());
	}
	
	@Test
	public void findByCuilNotFound() {
		System.out.println("Test findByCuilNotFound");
		
		Optional<Cliente> found = clienteRepository.findByCuil(333L);
		
		assertThat(found).isEmpty();
		
		System.out.println("Cliente no encontrado");
	}
	
	@Test
	public void findByTitularIdFoundTitular() {
		System.out.println("Test findByTitularIdFoundTitular");
		
		List<Cliente> found = clienteRepository.findByTitularId(null);
		
		assertThat(found)
			.isNotEmpty()
			.hasSize(1)
			.extracting(Cliente::getNombre)
			.containsExactlyInAnyOrder("test1");

		System.out.println("Clientes Encontrado");
		for (Cliente cliente : found) {
			System.out.println(cliente.toString());
		}
	}
	
	@Test
	public void findByTitularIdFoundCoTitular() {
		System.out.println("Test findByTitularIdFoundCoTitular");
		
		List<Cliente> found = clienteRepository.findByTitularId(cliente1.getId());
		
		assertThat(found)
			.isNotEmpty()
			.hasSize(1)
			.extracting(Cliente::getNombre)
			.containsExactlyInAnyOrder("test2");

		System.out.println("Clientes Encontrado");
		for (Cliente cliente : found) {
			System.out.println(cliente.toString());
		}
	}
	
	@Test
	public void findByTitularIdNotFound() {
		System.out.println("Test findByTitularIdNotFound");
		
		List<Cliente> found = clienteRepository.findByTitularId(cliente2.getId());
		
		assertThat(found)
			.isNotNull()
			.isEmpty();;

		System.out.println("Clientes No Encontrado");
	}

}
