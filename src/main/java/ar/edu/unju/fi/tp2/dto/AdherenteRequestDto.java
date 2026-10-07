package ar.edu.unju.fi.tp2.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AdherenteRequestDto {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    private String dni;

    @NotNull
    private UUID titularId;
}