package ar.edu.unju.fi.tp2.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter 
@Getter 
@NoArgsConstructor 
@AllArgsConstructor 
public class ExtraccionRequestDto {

    private BigDecimal monto;

    private UUID cuentaId;

    private UUID adherenteId;
}
