package cl.valenzuela.proyectocruds.crud.videojuego.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference; // Asegúrate de que este import esté
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import cl.valenzuela.proyectocruds.crud.developer.dto.DeveloperDTO;
import cl.valenzuela.proyectocruds.crud.consola.dto.ConsolaDTO;
import cl.valenzuela.proyectocruds.crud.publisher.dto.PublisherDTO;
import cl.valenzuela.proyectocruds.crud.review.dto.ReviewDTO;


@Entity
@Table(name = "videojuego")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "idVideojuego"
)
public class VideojuegoDTO {

    @Id
    @Column(name = "id_videojuego")
    private Integer idVideojuego;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "genero")
    private String genero;

    @Column(name = "fecha_lanzamiento")
    private String fechaLanzamiento;

    @Column(name = "precio")
    private Double precio;

    // *** ¡MODIFICACIÓN AQUÍ! Añadir @JsonBackReference ***
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_consola")
    @JsonBackReference("consola-videojuego") // <--- ¡AÑADIR ESTA LÍNEA!
    private ConsolaDTO consola;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_publisher")
    @JsonBackReference("publisher-videojuego")
    private PublisherDTO publisher;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name = "videojuego_developer",
        joinColumns = @JoinColumn(name = "id_videojuego"),
        inverseJoinColumns = @JoinColumn(name = "id_developer")
    )
    private List<DeveloperDTO> developers;

    @OneToMany(mappedBy = "videojuego", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("videojuego-review")
    private List<ReviewDTO> reviews;
}