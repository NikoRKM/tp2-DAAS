package ar.edu.unju.fi.tp2.dto;

import java.math.BigDecimal;

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
public class TransferenciaRequestDto {

	@NotNull
	private Long cbuOrigen;
	@NotNull
	private Long cbuDestino;
	@NotNull
	@Positive
	private BigDecimal monto;
	
}
