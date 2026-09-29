package ar.edu.unju.fi.tp2.dto;

import java.math.BigDecimal;
import java.util.UUID;

import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;
import ar.edu.unju.fi.tp2.enums.TipoTransaccion;
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
public class TransaccionRequestDto {
	
	@NotNull
	@Positive
	private BigDecimal monto;
	@NotNull
	private TipoTransaccion tipoTransaccion;
	@NotNull
	private EstadoTransaccion estadoTransaccion;
	@NotNull
	private UUID cuentaFinanciera;

}
