package ar.edu.unju.fi.tp2.services;

import java.util.List;
import java.util.UUID;

import ar.edu.unju.fi.tp2.dto.AdherenteRequestDto;
import ar.edu.unju.fi.tp2.dto.AdherenteResponseDto;

public interface IAdherenteService {

    AdherenteResponseDto saveAdherente(AdherenteRequestDto adherenteDto);

    AdherenteResponseDto findById(UUID id);

    List<AdherenteResponseDto> findByTitularId(UUID titularId);

    List<AdherenteResponseDto> findAll();

    AdherenteResponseDto updateAdherente(UUID id, AdherenteRequestDto adherenteDto);

    AdherenteResponseDto eliminarPorId(UUID id);
}