package ar.edu.unju.fi.tp2.dto;

import java.math.BigDecimal;
import java.util.UUID;

import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
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
public class CuentaFinancieraRequestDto {

	@NotNull
    private Long cbu;
    @NotBlank
	private String alias;
    @NotNull
    private BigDecimal saldo;
    @NotNull
    private EstadoCuenta estadoCuenta;
    @NotNull
    private UUID cliente;
	
}
