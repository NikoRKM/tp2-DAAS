package ar.edu.unju.fi.tp2.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import ar.edu.unju.fi.tp2.enums.EstadoCuenta;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "cuenta_financiera")
@Inheritance(strategy = InheritanceType.JOINED)
public class CuentaFinanciera extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    private Long cbu;
    @Column(unique = true, nullable = false)
    private String alias;
    private BigDecimal saldo;
    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToMany(mappedBy = "cuentaFinanciera", fetch = FetchType.LAZY/*, cascade = CascadeType.REMOVE, orphanRemoval = true*/)
    @ToString.Exclude
    private List<Transaccion> transacciones;

}