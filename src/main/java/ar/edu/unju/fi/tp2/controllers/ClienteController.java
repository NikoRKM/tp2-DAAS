package ar.edu.unju.fi.tp2.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unju.fi.tp2.dto.ClienteRequestDto;
import ar.edu.unju.fi.tp2.dto.ClienteResponseDto;
import ar.edu.unju.fi.tp2.models.Cliente;
import ar.edu.unju.fi.tp2.services.impl.ClienteServiceIMP;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteServiceIMP clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDto> saveCliente(@Valid @RequestBody ClienteRequestDto clienteDto) {

        ClienteResponseDto savedCliente = clienteService.saveCliente(clienteDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> findById(@PathVariable UUID id) {

        return ResponseEntity.ok(clienteService.findById(id));
    }

    @GetMapping("/cuil/{cuil}")
    public ResponseEntity<ClienteResponseDto> findByCuil(@PathVariable Long cuil) {

        return ResponseEntity.ok(clienteService.findByCuil(cuil));
    }

    @GetMapping("/titular/{id}")
    public ResponseEntity<List<ClienteResponseDto>> findByTitularId(@PathVariable UUID id) {

        return ResponseEntity.ok(clienteService.findByTitularId(id));
    }

    // @GetMapping("/{id}/cuentas")
    // public ResponseEntity<ClienteResponseDto> cargarCuentasDeCliente(
    // @PathVariable UUID id) {

    // return ResponseEntity.ok(clienteService.cargarCuentasDeCliente(id));
    // }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> findAll() {

        return ResponseEntity.ok(clienteService.findAll());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> updateCliente(@PathVariable UUID id,
            @Valid @RequestBody ClienteRequestDto clienteDto) {

        return ResponseEntity.ok(clienteService.updateCliente(id, clienteDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> eliminarPorId(@PathVariable UUID id) {

        return ResponseEntity.ok(clienteService.eliminarPorId(id));
    }

    @GetMapping("/activar")
    public ResponseEntity<String> activarCliente(
            @RequestParam UUID token) {

        clienteService.activarCliente(token);

        return ResponseEntity.ok(
                "Cliente activado correctamente");
    }

}
