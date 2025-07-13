package cl.valenzuela.proyectocruds.crud.review.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import cl.valenzuela.proyectocruds.crud.user.dto.UserDTO;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoDTO;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "idReview"
)
public class ReviewDTO {

    @Id
    @Column(name = "id_review")
    private Integer idReview;

    @Column(name = "puntuacion")
    private Integer puntuacion;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "fecha_review")
    private String fechaReview;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_videojuego")
    @JsonBackReference("videojuego-review")
    private VideojuegoDTO videojuego;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    @JsonBackReference("user-review")
    private UserDTO user;
}