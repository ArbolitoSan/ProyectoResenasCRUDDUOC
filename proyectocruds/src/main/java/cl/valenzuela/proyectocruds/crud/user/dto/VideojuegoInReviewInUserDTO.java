package cl.valenzuela.proyectocruds.crud.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideojuegoInReviewInUserDTO {
    private Integer idVideojuego;
    private String titulo;
}