package ar.edu.unju.fi.tp2.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ar.edu.unju.fi.tp2.models.CajaAhorro;

public interface ICajaAhorroService {

    public CajaAhorro saveCajaAhorro(CajaAhorro cajaAhorro);

    public Optional<CajaAhorro> findById(UUID id) throws Exception;

    public List<CajaAhorro> findAll();

    public Optional<CajaAhorro> updateCajaAhorro(UUID id, CajaAhorro cajaAhorroDetalle);

    public Optional<CajaAhorro> eliminarPorId(UUID id);
}
