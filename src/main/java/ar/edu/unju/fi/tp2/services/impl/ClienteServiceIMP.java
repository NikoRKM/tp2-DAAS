package ar.edu.unju.fi.tp2.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.tp2.dto.ClienteRequestDto;
import ar.edu.unju.fi.tp2.dto.ClienteResponseDto;
import ar.edu.unju.fi.tp2.exceptions.RecursoNoEncontradoException;
import ar.edu.unju.fi.tp2.models.Cliente;
import ar.edu.unju.fi.tp2.repositories.ClienteRepository;
import ar.edu.unju.fi.tp2.services.IClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceIMP implements IClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public ClienteResponseDto saveCliente(ClienteRequestDto clienteDto) {
        Cliente cliente = Cliente.builder()
                .cuil(clienteDto.getCuil())
                .nombre(clienteDto.getNombre())
                .razonSocial(clienteDto.getRazonSocial())
                .email(clienteDto.getEmail())
                .telefono(clienteDto.getTelefono())
                .direccion(clienteDto.getDireccion())
                .build();

        Cliente savedCliente = clienteRepository.save(cliente);

        log.info("Se ha creado el Cliente: " + savedCliente.getId());

        return mapToResponseDto(savedCliente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDto findById(UUID id) {
        // TODO Auto-generated method stub
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("NO se ha encontrado el Cliente: " + id);
                    return new RecursoNoEncontradoException(id, "Cliente");
                });

        log.info("Se ha encontrado el Cliente: " + cliente.getId());

        return mapToResponseDto(cliente);
    }

    @Override
    public ClienteResponseDto findByCuil(Long cuil) {
        // TODO Auto-generated method stub

        Cliente cliente = clienteRepository.findByCuil(cuil)
                .orElseThrow(() -> {
                    log.info("NO se ha encontrado el Cliente con CUIL: " + cuil);
                    return new RecursoNoEncontradoException(cuil, "Cliente");
                });

        log.info("Se ha encontrado el Cliente: " + cliente.getId());

        return mapToResponseDto(cliente);
    }

    @Override
    public List<ClienteResponseDto> findByTitularId(UUID id) {
        // TODO Auto-generated method stub
        return clienteRepository.findByTitularId(id)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDto> findAll() {
        // TODO Auto-generated method stub
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public ClienteResponseDto updateCliente(UUID id, ClienteRequestDto clienteDto) {
        // TODO Auto-generated method stub
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("NO se ha encontrado el Cliente: " + id);
                    return new RecursoNoEncontradoException(id, "Cliente");
                });

        log.info("Se ha encontrado el Cliente: " + cliente.getId());

        cliente.setCuil(clienteDto.getCuil());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setNombre(clienteDto.getNombre());
        cliente.setRazonSocial(clienteDto.getRazonSocial());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setDireccion(clienteDto.getDireccion());

        Cliente updatedCliente = clienteRepository.save(cliente);

        log.info("Se ha actualizado el Cliente: " + id);

        return mapToResponseDto(updatedCliente);
    }

    @Override
    @Transactional
    public ClienteResponseDto eliminarPorId(UUID id) {
        // TODO Auto-generated method stub
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("NO se ha encontrado el Cliente: " + id);
                    return new RecursoNoEncontradoException(id, "Cliente");
                });

        log.info("Se ha encontrado el Cliente: " + cliente.getId());

        clienteRepository.delete(cliente);

        log.info("Se ha borrado el Cliente: " + id);

        return mapToResponseDto(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDto cargarCuentasDeCliente(UUID idCliente) {
        // TODO Auto-generated method stub
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> {
                    log.info("NO se ha encontrado el Cliente: " + idCliente);
                    return new RecursoNoEncontradoException(idCliente, "Cliente");
                });

        cliente.getCuentas().size();

        return mapToResponseDto(cliente);
    }

    private ClienteResponseDto mapToResponseDto(Cliente cliente) {

        return ClienteResponseDto.builder()
                .id(cliente.getId())
                .cuil(cliente.getCuil())
                .nombre(cliente.getNombre())
                .razonSocial(cliente.getRazonSocial())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())

                .titularId(
                        cliente.getTitular() != null
                                ? cliente.getTitular().getId()
                                : null)
                .titularNombre(
                        cliente.getTitular() != null
                                ? cliente.getTitular().getNombre()
                                : null)
                // .cuentas(
                // cliente.getCuentas() == null
                // ? List.of()
                // : cliente.getCuentas()
                // .stream()
                // .map(cuenta -> cuenta.getId())
                // .toList())

                .fechaCreacion(cliente.getFechaCreacion())
                .fechaUltimaActualizacion(cliente.getFechaUltimaActualizacion())
                .build();
    }
}
