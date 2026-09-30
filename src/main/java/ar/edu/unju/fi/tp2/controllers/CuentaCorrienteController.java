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

import ar.edu.unju.fi.tp2.dto.CuentaCorrienteRequestDto;
import ar.edu.unju.fi.tp2.dto.CuentaCorrienteResponseDto;
import ar.edu.unju.fi.tp2.services.ICuentaCorrienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/cuentas_corrientes")
@RequiredArgsConstructor
public class CuentaCorrienteController {

	private final ICuentaCorrienteService cuentaCorrienteService;
	
	@PostMapping
	public ResponseEntity<CuentaCorrienteResponseDto> saveCuentaCorriente(@Valid @RequestBody CuentaCorrienteRequestDto cuentaCorriente){
		CuentaCorrienteResponseDto savedCuentaCorriente = cuentaCorrienteService.saveCuentaCorriente(cuentaCorriente);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCuentaCorriente);
	}

	@GetMapping("/{cbu}")
	public ResponseEntity<CuentaCorrienteResponseDto> findByCbu(@PathVariable Long cbu) {
		return ResponseEntity.ok(cuentaCorrienteService.findByCbu(cbu));
	}
	
	@GetMapping
	public ResponseEntity<List<CuentaCorrienteResponseDto>> findAll() {
		return ResponseEntity.ok(cuentaCorrienteService.findAll());
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<CuentaCorrienteResponseDto> updateCuentaCorriente(@PathVariable UUID id, @Valid @RequestBody CuentaCorrienteRequestDto cuentaCorriente){
		return ResponseEntity.ok(cuentaCorrienteService.updateCuentaCorriente(id, cuentaCorriente));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CuentaCorrienteResponseDto> eliminarPorId(@PathVariable UUID id){
		return ResponseEntity.ok(cuentaCorrienteService.eliminarPorId(id));
	}
	
}
