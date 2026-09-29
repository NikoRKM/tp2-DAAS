package ar.edu.unju.fi.tp2.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class TransferenciaResponseDto {

	private UUID cuentaFinancieraOrigen;
	private Long cbuOrigen;
	private UUID cuentaFinancieraDestino;
	private Long cbuDestino;
	private BigDecimal monto;
    private LocalDateTime fechaHora;
    private BigDecimal saldoCuentaOrigen;
    private BigDecimal saldoCuentaDestino;
	
}
