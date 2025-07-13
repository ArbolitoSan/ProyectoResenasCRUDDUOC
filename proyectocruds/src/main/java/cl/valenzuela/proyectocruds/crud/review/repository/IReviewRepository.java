package cl.valenzuela.proyectocruds.crud.review.repository;

import cl.valenzuela.proyectocruds.crud.review.dto.ReviewDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IReviewRepository extends CrudRepository<ReviewDTO, Integer> {
    List<ReviewDTO> findByVideojuego_IdVideojuego(Integer idVideojuego);
    List<ReviewDTO> findByUser_IdUsuario(Integer idUsuario);
}