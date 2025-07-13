package cl.valenzuela.proyectocruds.crud.videojuego.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoDTO;

@Repository
public interface IVideojuegoRepository extends CrudRepository<VideojuegoDTO, Integer> {
}