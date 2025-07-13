package cl.valenzuela.proyectocruds.crud.publisher.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoDTO;

@Entity
@Table(name = "publisher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "idPublisher"
)
public class PublisherDTO {

    @Id
    @Column(name = "id_publisher")
    private Integer idPublisher;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "fecha_inicio_operaciones")
    private String fechaInicioOperaciones;

    @Column(name = "fecha_fin_operaciones")
    private String fechaFinOperaciones;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("publisher-videojuego")
    private List<VideojuegoDTO> videojuegos;
}