package ar.edu.unju.fi.tp2.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ar.edu.unju.fi.tp2.models.Cliente;

public interface IClienteService {

    public Cliente saveCliente(Cliente cliente);

    public Optional<Cliente> findById(UUID id) throws Exception;

    public Optional<Cliente> findByCuil(Long cuil) throws Exception;

    public List<Cliente> findAll();

    public Optional<Cliente> updateCliente(UUID id, Cliente clienteDetalle);

    public Optional<Cliente> eliminarPorId(UUID id);

}
