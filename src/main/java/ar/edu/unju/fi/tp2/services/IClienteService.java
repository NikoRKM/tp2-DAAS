package ar.edu.unju.fi.tp2.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ar.edu.unju.fi.tp2.dto.ClienteRequestDto;
import ar.edu.unju.fi.tp2.dto.ClienteResponseDto;
import ar.edu.unju.fi.tp2.models.Cliente;

public interface IClienteService {

    public ClienteResponseDto saveCliente(ClienteRequestDto clienteDto);

    public ClienteResponseDto findById(UUID id);

    public ClienteResponseDto findByCuil(Long cuil);

    public List<ClienteResponseDto> findByTitularId(UUID id);

    public List<ClienteResponseDto> findAll();

    public ClienteResponseDto updateCliente(UUID id, ClienteRequestDto clienteDto);

    public ClienteResponseDto eliminarPorId(UUID id);

    public ClienteResponseDto cargarCuentasDeCliente(UUID idCliente);

}
