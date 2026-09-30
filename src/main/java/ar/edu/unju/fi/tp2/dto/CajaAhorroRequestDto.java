package ar.edu.unju.fi.tp2.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CajaAhorroRequestDto extends CuentaFinancieraRequestDto {

	@NotNull
	@PositiveOrZero
	private BigDecimal margenDescuento;
	@NotNull
	@PositiveOrZero
	private BigDecimal comisionMantenimientoMensual;
	
}
