package cl.valenzuela.proyectocruds.crud.videojuego.dto;

import cl.valenzuela.proyectocruds.crud.developer.dto.DeveloperResponseDTO;
import cl.valenzuela.proyectocruds.crud.review.dto.ReviewDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideojuegoResponseDTO {
    private Integer idVideojuego;
    private String titulo;
    private String genero;
    private String fechaLanzamiento;
    private Double precio;
    private Integer idConsola;
    private String nombreConsola;
    private Integer idPublisher;
    private String nombrePublisher;
    
    private List<DeveloperResponseDTO> developers;
    
    private List<ReviewDTO> reviews;
}