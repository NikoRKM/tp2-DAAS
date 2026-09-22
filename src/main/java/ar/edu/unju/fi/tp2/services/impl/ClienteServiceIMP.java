package ar.edu.unju.fi.tp2.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.tp2.models.Cliente;
import ar.edu.unju.fi.tp2.repositories.ClienteRepository;
import ar.edu.unju.fi.tp2.services.IClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceIMP implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Cliente saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(UUID id) throws Exception {
        // TODO Auto-generated method stub
        return clienteRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findByCuil(Long cuil) throws Exception {
        // TODO Auto-generated method stub
        return clienteRepository.findByCuil(cuil);
    }

    @Override
    public List<Cliente> findAll() {
        // TODO Auto-generated method stub
        return clienteRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Cliente> updateCliente(UUID id, Cliente clienteDetalle) {
        // TODO Auto-generated method stub
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente clienteDb = clienteOptional.orElseThrow();

            clienteDb.setCuil(clienteDetalle.getCuil());
            clienteDb.setEmail(clienteDetalle.getEmail());
            clienteDb.setNombre(clienteDetalle.getNombre());
            clienteDb.setRazonSocial(clienteDetalle.getRazonSocial());
            clienteDb.setTelefono(clienteDetalle.getTelefono());
            clienteDb.setDireccion(clienteDetalle.getDireccion());

            return Optional.of(clienteRepository.save(clienteDb));
        }

        return clienteOptional;
    }

    @Transactional
    @Override
    public Cliente cargarCuentasDeCliente(UUID idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.getCuentas().size();

        return cliente;
    }

    @Override
    @Transactional
    public Optional<Cliente> eliminarPorId(UUID id) {
        // TODO Auto-generated method stub
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        clienteOptional.ifPresent(clienteDb -> {
            clienteRepository.delete(clienteDb);
        });

        return clienteOptional;
    }

}
