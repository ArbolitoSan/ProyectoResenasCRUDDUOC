package cl.valenzuela.proyectocruds.crud.publisher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoMinDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublisherResponseDTO {

    private Integer idPublisher;
    private String nombre;
    private String fechaInicioOperaciones;
    private String fechaFinOperaciones;
    private List<VideojuegoMinDTO> videojuegos;
}