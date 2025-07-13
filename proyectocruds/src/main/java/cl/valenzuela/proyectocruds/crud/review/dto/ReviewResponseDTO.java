package cl.valenzuela.proyectocruds.crud.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {
    private Integer idReview;
    private Integer puntuacion;
    private String comentario;
    private String fechaReview;
    private VideojuegoInReviewDTO videojuego;
    private UserInReviewDTO user;
}