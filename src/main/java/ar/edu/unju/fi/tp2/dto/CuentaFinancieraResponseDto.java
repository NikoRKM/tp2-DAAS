package ar.edu.unju.fi.tp2.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
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
public class CuentaFinancieraResponseDto {

	private UUID id;
    private Long cbu;
    private String alias;
    private BigDecimal saldo;
    private EstadoCuenta estadoCuenta;
    private UUID cliente;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimaActualizacion;
	
}
