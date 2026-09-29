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

import ar.edu.unju.fi.tp2.dto.CuentaFinancieraRequestDto;
import ar.edu.unju.fi.tp2.dto.CuentaFinancieraResponseDto;
import ar.edu.unju.fi.tp2.services.ICuentaFinancieraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/cuentas_financieras")
@RequiredArgsConstructor
public class CuentaFinancieraController {

private final ICuentaFinancieraService cuentaFinancieraService;
	
	@PostMapping
	public ResponseEntity<CuentaFinancieraResponseDto> saveCuentaFinanciera(@Valid @RequestBody CuentaFinancieraRequestDto cuentaFinanciera){
		CuentaFinancieraResponseDto savedCuentaFinanciera = cuentaFinancieraService.saveCuentaFinanciera(cuentaFinanciera);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCuentaFinanciera);
	}

	@GetMapping("/{cbu}")
	public ResponseEntity<CuentaFinancieraResponseDto> findByCbu(@PathVariable Long cbu) {
		return ResponseEntity.ok(cuentaFinancieraService.findByCbu(cbu));
	}
	
	@GetMapping
	public ResponseEntity<List<CuentaFinancieraResponseDto>> findAll() {
		return ResponseEntity.ok(cuentaFinancieraService.findAll());
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<CuentaFinancieraResponseDto> updateCuentaFinanciera(@PathVariable UUID id, @Valid @RequestBody CuentaFinancieraRequestDto cuentaFinanciera){
		return ResponseEntity.ok(cuentaFinancieraService.updateCuentaFinanciera(id, cuentaFinanciera));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CuentaFinancieraResponseDto> eliminarPorId(@PathVariable UUID id){
		return ResponseEntity.ok(cuentaFinancieraService.eliminarPorId(id));
	}
	
}
