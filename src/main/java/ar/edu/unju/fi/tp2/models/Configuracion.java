package ar.edu.unju.fi.tp2.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "configuraciones")
@Getter
@Setter
public class Configuracion {
	
	@Id
	@Column(nullable = false, unique = true)
	private String clave;
	@Column(nullable = false)
	private String valor;

}
