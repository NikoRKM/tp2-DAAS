package ar.edu.unju.fi.tp2.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import ar.edu.unju.fi.tp2.enums.EstadoTransaccion;
import ar.edu.unju.fi.tp2.enums.TipoTransaccion;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaccion")
public class Transaccion extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDateTime fechaHora;
    private BigDecimal monto;
    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipoTransaccion;
    @Enumerated(EnumType.STRING)
    private EstadoTransaccion estadoTransaccion;
    @ManyToOne()
    @JoinColumn(name = "cuentaFinanciera_id")
    private CuentaFinanciera cuentaFinanciera;

}
