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

import ar.edu.unju.fi.tp2.dto.CajaAhorroRequestDto;
import ar.edu.unju.fi.tp2.dto.CajaAhorroResponseDto;
import ar.edu.unju.fi.tp2.services.ICajaAhorroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/cajas_ahorros")
@RequiredArgsConstructor
public class CajaAhorroController {

	private final ICajaAhorroService cajaAhorroService;
	
	@PostMapping
	public ResponseEntity<CajaAhorroResponseDto> saveCajaAhorro(@Valid @RequestBody CajaAhorroRequestDto cajaAhorro){
		CajaAhorroResponseDto savedCajaAhorro = cajaAhorroService.saveCajaAhorro(cajaAhorro);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCajaAhorro);
	}

	@GetMapping("/{cbu}")
	public ResponseEntity<CajaAhorroResponseDto> findByCbu(@PathVariable Long cbu) {
		return ResponseEntity.ok(cajaAhorroService.findByCbu(cbu));
	}
	
	@GetMapping
	public ResponseEntity<List<CajaAhorroResponseDto>> findAll() {
		return ResponseEntity.ok(cajaAhorroService.findAll());
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<CajaAhorroResponseDto> updateCajaAhorro(@PathVariable UUID id, @Valid @RequestBody CajaAhorroRequestDto cajaAhorro){
		return ResponseEntity.ok(cajaAhorroService.updateCajaAhorro(id, cajaAhorro));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CajaAhorroResponseDto> eliminarPorId(@PathVariable UUID id){
		return ResponseEntity.ok(cajaAhorroService.eliminarPorId(id));
	}
	
}
