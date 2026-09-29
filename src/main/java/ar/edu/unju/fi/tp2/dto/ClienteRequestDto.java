package ar.edu.unju.fi.tp2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ClienteRequestDto {

    @NotNull 
    @Positive 
    private Long cuil;

    @NotBlank
    private String nombre;

    private String razonSocial;

    @Email
    @NotBlank
    private String email;

    @NotBlank 
    private String telefono;

    @NotBlank
    private String direccion;
}
