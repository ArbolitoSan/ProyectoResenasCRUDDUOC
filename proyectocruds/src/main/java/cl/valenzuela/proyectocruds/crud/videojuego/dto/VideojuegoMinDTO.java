package cl.valenzuela.proyectocruds.crud.videojuego.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideojuegoMinDTO {

    private Integer idVideojuego;
    private String titulo;
}