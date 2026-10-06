package ar.edu.unju.fi.tp2.dto;

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
public class AdherenteResponseDto {

    private UUID id;

    private String nombre;

    private String apellido;

    private String dni;

    private UUID titularId;

    private String titularNombre;
}