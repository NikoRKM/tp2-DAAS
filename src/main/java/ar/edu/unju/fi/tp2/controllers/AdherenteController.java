package ar.edu.unju.fi.tp2.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unju.fi.tp2.dto.AdherenteRequestDto;
import ar.edu.unju.fi.tp2.dto.AdherenteResponseDto;
import ar.edu.unju.fi.tp2.services.impl.AdherenteServiceIMP;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/adherentes")
public class AdherenteController {

    private final AdherenteServiceIMP adherenteService;

    @PostMapping
    public ResponseEntity<AdherenteResponseDto> saveAdherente(
            @Valid @RequestBody AdherenteRequestDto adherenteDto) {

        AdherenteResponseDto savedAdherente = adherenteService.saveAdherente(adherenteDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedAdherente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdherenteResponseDto> findById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                adherenteService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AdherenteResponseDto>> findAll() {

        return ResponseEntity.ok(
                adherenteService.findAll());
    }

    @GetMapping("/titular/{titularId}")
    public ResponseEntity<List<AdherenteResponseDto>> findByTitularId(
            @PathVariable UUID titularId) {

        return ResponseEntity.ok(
                adherenteService.findByTitularId(titularId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdherenteResponseDto> updateAdherente(
            @PathVariable UUID id,
            @Valid @RequestBody AdherenteRequestDto adherenteDto) {

        return ResponseEntity.ok(
                adherenteService.updateAdherente(id, adherenteDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AdherenteResponseDto> eliminarPorId(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                adherenteService.eliminarPorId(id));
    }
}
