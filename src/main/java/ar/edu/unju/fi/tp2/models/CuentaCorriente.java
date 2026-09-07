package ar.edu.unju.fi.tp2.models;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
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
@Table(name = "cuenta_corriente")
public class CuentaCorriente extends CuentaFinanciera {

    private BigDecimal tasaInteresAnual;
    private Integer cupoLimiteMensual;

}
