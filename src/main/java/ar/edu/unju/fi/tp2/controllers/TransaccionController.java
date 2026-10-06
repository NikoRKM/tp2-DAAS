package ar.edu.unju.fi.tp2.controllers;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unju.fi.tp2.dto.TransaccionRequestDto;
import ar.edu.unju.fi.tp2.dto.TransaccionResponseDto;
import ar.edu.unju.fi.tp2.dto.TransferenciaRequestDto;
import ar.edu.unju.fi.tp2.dto.TransferenciaResponseDto;
import ar.edu.unju.fi.tp2.models.Transaccion;
import ar.edu.unju.fi.tp2.services.ITransaccionService;
import ar.edu.unju.fi.tp2.services.impl.TransaccionServiceIMP;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

	private final ITransaccionService transaccionService;

	private final TransaccionServiceIMP transaccionServiceImp;

	@PostMapping
	public ResponseEntity<TransaccionResponseDto> saveTransaccion(
			@Valid @RequestBody TransaccionRequestDto transaccion) {
		TransaccionResponseDto savedTransaccion = transaccionService.saveTransaccion(transaccion);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaccion);
	}

	@PostMapping("/transferir")
	public ResponseEntity<TransferenciaResponseDto> realizarTransferenciaEntreCuentas(
			@Valid @RequestBody TransferenciaRequestDto transferenciaDto) {
		TransferenciaResponseDto savedTransferencia = transaccionService
				.realizarTransferenciaEntreCuentas(transferenciaDto);
		return ResponseEntity.ok(savedTransferencia);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TransaccionResponseDto> findById(@PathVariable UUID id) {
		return ResponseEntity.ok(transaccionService.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<TransaccionResponseDto>> findAll() {
		return ResponseEntity.ok(transaccionService.findAll());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<TransaccionResponseDto> updateTransaccion(@PathVariable UUID id,
			@Valid @RequestBody TransaccionRequestDto transaccion) {
		return ResponseEntity.ok(transaccionService.updateTransaccion(id, transaccion));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<TransaccionResponseDto> eliminarPorId(@PathVariable UUID id) {
		return ResponseEntity.ok(transaccionService.eliminarPorId(id));
	}

	@PostMapping("/extraccion")
	public ResponseEntity<TransaccionResponseDto> realizarExtraccion(
			@RequestParam UUID cuentaId,
			@RequestParam BigDecimal monto,
			@RequestParam(required = false) UUID adherenteId) {

		return ResponseEntity.ok(
				transaccionServiceImp.realizarExtraccion(
						monto,
						cuentaId,
						adherenteId));
	}

}
