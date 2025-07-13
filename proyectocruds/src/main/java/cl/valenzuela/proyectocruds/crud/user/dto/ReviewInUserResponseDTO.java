package cl.valenzuela.proyectocruds.crud.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInUserResponseDTO {
    private Integer idReview;
    private Integer puntuacion;
    private String comentario;
    private String fechaReview;

    private VideojuegoInReviewInUserDTO videojuego;
}