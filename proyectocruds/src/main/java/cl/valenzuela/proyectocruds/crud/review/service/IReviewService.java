package cl.valenzuela.proyectocruds.crud.review.service;

import cl.valenzuela.proyectocruds.crud.review.dto.ReviewDTO;
import cl.valenzuela.proyectocruds.crud.review.dto.ReviewResponseDTO;

import java.util.List;

public interface IReviewService {
    ReviewDTO createReview(ReviewDTO review);
    ReviewResponseDTO getReviewById(Integer id);
    List<ReviewResponseDTO> getAllReviews();
    List<ReviewResponseDTO> getReviewsByVideojuegoId(Integer idVideojuego);
    List<ReviewResponseDTO> getReviewsByUserId(Integer idUsuario);
    ReviewDTO updateReview(ReviewDTO review);
    boolean deleteReviewById(Integer id);
}