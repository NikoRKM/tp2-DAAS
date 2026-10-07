package ar.edu.unju.fi.tp2.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor  
@Builder 
@Entity
@Table (name = "adherentes") 
public class Adherente {


    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nombre;

    private String apellido;

    private String dni;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "titular_id", nullable = false)
    private Cliente titular;
    
}
