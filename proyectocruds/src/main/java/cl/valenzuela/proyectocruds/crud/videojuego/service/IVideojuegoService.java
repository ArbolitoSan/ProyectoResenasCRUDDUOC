package cl.valenzuela.proyectocruds.crud.videojuego.service;

import java.util.List;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoDTO;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoResponseDTO;

public interface IVideojuegoService {
    VideojuegoDTO createVideojuego(VideojuegoDTO videojuego);
    List<VideojuegoDTO> getAllVideojuegos();
    List<VideojuegoResponseDTO> getAllVideojuegosResponse();
    VideojuegoResponseDTO getVideojuegoResponseById(Integer id);
    VideojuegoDTO getVideojuegoById(Integer id);
    boolean deleteVideojuegoById(Integer id);
    VideojuegoDTO updateVideojuego(VideojuegoDTO videojuego);
}