package ar.edu.unju.fi.tp2.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder 
@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor 
public class ClienteResponseDto {

    private UUID id;
    private Long cuil;
    private String nombre;
    private String razonSocial;
    private String email;
    private String telefono;
    private String direccion;
    //private List<UUID> cuentas;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimaActualizacion;
    
    
    private UUID titularId;
    private String titularNombre;
}

