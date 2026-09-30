package ar.edu.unju.fi.tp2.dto;

import java.math.BigDecimal;

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
public class CajaAhorroResponseDto extends CuentaFinancieraResponseDto {

	private BigDecimal margenDescuento;
    private BigDecimal comisionMantenimientoMensual;
	
}
