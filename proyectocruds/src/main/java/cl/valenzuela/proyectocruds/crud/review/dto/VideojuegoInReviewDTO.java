package cl.valenzuela.proyectocruds.crud.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideojuegoInReviewDTO {
    private Integer idVideojuego;
    private String titulo;
}