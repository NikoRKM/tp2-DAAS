package ar.edu.unju.fi.tp2.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unju.fi.tp2.models.Cliente;
import ar.edu.unju.fi.tp2.services.impl.ClienteServiceIMP;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteServiceIMP clienteService;

    @GetMapping
    public List<Cliente> list() {
        
         List<Cliente> clientes = clienteService.findAll();

        System.out.println("Cantidad: " + clientes.size());
        return clienteService.findAll();
    }
    
    
    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody Cliente cliente, BindingResult result) {

        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.saveCliente(cliente));
    }

    private ResponseEntity<Map<String, String>> validation(BindingResult result) {
        // TODO Auto-generated method stub
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El Campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

    
}
