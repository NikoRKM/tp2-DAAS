package ar.edu.unju.fi.tp2.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.tp2.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

	public Optional<Cliente> findByCuil(Long cuil);
	
	public List<Cliente> findByTitular(Cliente cliente);
	
}
