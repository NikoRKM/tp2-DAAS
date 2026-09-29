package ar.edu.unju.fi.tp2.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;
import ar.edu.unju.fi.tp2.enums.TipoTransaccion;
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
public class TransaccionResponseDto {

    private UUID id;
    private LocalDateTime fechaHora;
    private BigDecimal monto;
    private TipoTransaccion tipoTransaccion;
    private EstadoTransaccion estadoTransaccion;
    private UUID cuentaFinanciera;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimaActualizacion;
	
}
