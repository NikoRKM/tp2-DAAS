package ar.edu.unju.fi.tp2.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unju.fi.tp2.dto.AdherenteRequestDto;
import ar.edu.unju.fi.tp2.dto.AdherenteResponseDto;
import ar.edu.unju.fi.tp2.exceptions.RecursoNoEncontradoException;
import ar.edu.unju.fi.tp2.models.Adherente;
import ar.edu.unju.fi.tp2.models.Cliente;
import ar.edu.unju.fi.tp2.repositories.AdherenteRepository;
import ar.edu.unju.fi.tp2.repositories.ClienteRepository;
import ar.edu.unju.fi.tp2.services.IAdherenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdherenteServiceIMP implements IAdherenteService {

    private final AdherenteRepository adherenteRepository;
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public AdherenteResponseDto saveAdherente(AdherenteRequestDto adherenteDto) {
        // TODO Auto-generated method stub
        Cliente titular = clienteRepository.findById(adherenteDto.getTitularId())
                .orElseThrow(() -> {
                    log.info("NO se ha encontrado el Titular: "
                            + adherenteDto.getTitularId());

                    return new RecursoNoEncontradoException(
                            adherenteDto.getTitularId(),
                            "Titular");
                });

        Adherente adherente = Adherente.builder()
                .nombre(adherenteDto.getNombre())
                .apellido(adherenteDto.getApellido())
                .dni(adherenteDto.getDni())
                .titular(titular)
                .build();

        Adherente savedAdherente = adherenteRepository.save(adherente);

        log.info("Se ha creado el Adherente: " + savedAdherente.getId());

        return mapToResponseDto(savedAdherente);
    }

    @Override
    @Transactional(readOnly = true)
    public AdherenteResponseDto findById(UUID id) {
        // TODO Auto-generated method stub
        Adherente adherente = adherenteRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("NO se ha encontrado el Adherente: " + id);

                    return new RecursoNoEncontradoException(
                            id,
                            "Adherente");
                });

        return mapToResponseDto(adherente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdherenteResponseDto> findByTitularId(UUID titularId) {
        // TODO Auto-generated method stub
        clienteRepository.findById(titularId)
                .orElseThrow(() -> {
                    log.info("NO se ha encontrado el Titular: " + titularId);

                    return new RecursoNoEncontradoException(
                            titularId,
                            "Titular");
                });

        return adherenteRepository.findByTitularId(titularId)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdherenteResponseDto> findAll() {
        // TODO Auto-generated method stub
        return adherenteRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public AdherenteResponseDto updateAdherente(UUID id, AdherenteRequestDto adherenteDto) {
        // TODO Auto-generated method stub
        Adherente adherente = adherenteRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("NO se ha encontrado el Adherente: " + id);

                    return new RecursoNoEncontradoException(
                            id,
                            "Adherente");
                });

        Cliente titular = clienteRepository.findById(adherenteDto.getTitularId())
                .orElseThrow(() -> {
                    log.info("NO se ha encontrado el Titular: "
                            + adherenteDto.getTitularId());

                    return new RecursoNoEncontradoException(
                            adherenteDto.getTitularId(),
                            "Titular");
                });

        adherente.setNombre(adherenteDto.getNombre());
        adherente.setApellido(adherenteDto.getApellido());
        adherente.setDni(adherenteDto.getDni());
        adherente.setTitular(titular);

        Adherente updatedAdherente = adherenteRepository.save(adherente);

        log.info("Se ha actualizado el Adherente: " + id);

        return mapToResponseDto(updatedAdherente);
    }

    @Override
    @Transactional
    public AdherenteResponseDto eliminarPorId(UUID id) {
        // TODO Auto-generated method stub
        Adherente adherente = adherenteRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("NO se ha encontrado el Adherente: " + id);

                    return new RecursoNoEncontradoException(
                            id,
                            "Adherente");
                });

        adherenteRepository.delete(adherente);

        log.info("Se ha borrado el Adherente: " + id);

        return mapToResponseDto(adherente);
    }

    private AdherenteResponseDto mapToResponseDto(Adherente adherente) {

        return AdherenteResponseDto.builder()
                .id(adherente.getId())
                .nombre(adherente.getNombre())
                .apellido(adherente.getApellido())
                .dni(adherente.getDni())
                .titularId(
                        adherente.getTitular() != null
                                ? adherente.getTitular().getId()
                                : null)
                .titularNombre(
                        adherente.getTitular() != null
                                ? adherente.getTitular().getNombre()
                                : null)
                .build();
    }

}
