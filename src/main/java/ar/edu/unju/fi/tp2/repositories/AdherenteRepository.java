package ar.edu.unju.fi.tp2.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.tp2.models.Adherente;

public interface AdherenteRepository extends JpaRepository<Adherente, UUID>{

    List<Adherente> findByTitularId(UUID titularId);
} 
