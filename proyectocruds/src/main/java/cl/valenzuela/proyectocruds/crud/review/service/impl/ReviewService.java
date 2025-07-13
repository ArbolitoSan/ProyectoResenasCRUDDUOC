package cl.valenzuela.proyectocruds.crud.review.service.impl;

import cl.valenzuela.proyectocruds.crud.review.dto.ReviewDTO;
import cl.valenzuela.proyectocruds.crud.review.repository.IReviewRepository;
import cl.valenzuela.proyectocruds.crud.review.service.IReviewService;
import cl.valenzuela.proyectocruds.crud.videojuego.repository.IVideojuegoRepository;
import cl.valenzuela.proyectocruds.crud.user.repository.IUserRepository;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoDTO;
import cl.valenzuela.proyectocruds.crud.user.dto.UserDTO;

import cl.valenzuela.proyectocruds.crud.review.dto.ReviewResponseDTO;
import cl.valenzuela.proyectocruds.crud.review.dto.VideojuegoInReviewDTO;
import cl.valenzuela.proyectocruds.crud.review.dto.UserInReviewDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService implements IReviewService {

    @Autowired
    private IReviewRepository reviewRepository;

    @Autowired
    private IVideojuegoRepository videojuegoRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    @Transactional
    public ReviewDTO createReview(ReviewDTO review) {
        if (reviewRepository.existsById(review.getIdReview())) {
            System.out.println("Error al crear reseña: Ya existe una reseña con el ID " + review.getIdReview());
            return null;
        }

        if (review.getVideojuego() == null || review.getVideojuego().getIdVideojuego() == null) {
            System.out.println("Error al crear reseña: ID de Videojuego es nulo.");
            return null;
        }
        Optional<VideojuegoDTO> videojuegoOpt = videojuegoRepository.findById(review.getVideojuego().getIdVideojuego());
        if (videojuegoOpt.isEmpty()) {
            System.out.println("Error al crear reseña: Videojuego con ID " + review.getVideojuego().getIdVideojuego() + " no encontrado.");
            return null;
        }
        review.setVideojuego(videojuegoOpt.get());

        if (review.getUser() == null || review.getUser().getIdUsuario() == null) {
            System.out.println("Error al crear reseña: ID de Usuario es nulo.");
            return null;
        }
        Optional<UserDTO> userOpt = userRepository.findById(review.getUser().getIdUsuario());
        if (userOpt.isEmpty()) {
            System.out.println("Error al crear reseña: Usuario con ID " + review.getUser().getIdUsuario() + " no encontrado.");
            return null;
        }
        review.setUser(userOpt.get());
        
        return reviewRepository.save(review);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponseDTO getReviewById(Integer id) {
        Optional<ReviewDTO> reviewEncontrada = reviewRepository.findById(id);
        if (reviewEncontrada.isEmpty()) {
            System.out.println("Reseña con ID " + id + " no encontrada.");
            return null;
        }
        return mapToReviewResponseDTO(reviewEncontrada.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> getAllReviews() {
        List<ReviewDTO> reviews = (List<ReviewDTO>) reviewRepository.findAll();
        if (reviews.isEmpty()) {
            System.out.println("No se encontraron reseñas.");
        }
        return reviews.stream()
                      .map(this::mapToReviewResponseDTO)
                      .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> getReviewsByVideojuegoId(Integer idVideojuego) {
        List<ReviewDTO> reviews = reviewRepository.findByVideojuego_IdVideojuego(idVideojuego);
        if (reviews.isEmpty()) {
            System.out.println("No se encontraron reseñas para el videojuego con ID " + idVideojuego);
        }
        return reviews.stream()
                      .map(this::mapToReviewResponseDTO)
                      .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> getReviewsByUserId(Integer idUsuario) {
        List<ReviewDTO> reviews = reviewRepository.findByUser_IdUsuario(idUsuario);
        if (reviews.isEmpty()) {
            System.out.println("No se encontraron reseñas para el usuario con ID " + idUsuario);
        }
        return reviews.stream()
                      .map(this::mapToReviewResponseDTO)
                      .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewDTO updateReview(ReviewDTO review) {
        if (!reviewRepository.existsById(review.getIdReview())) {
            System.out.println("Error al actualizar reseña: Reseña con ID " + review.getIdReview() + " no encontrada.");
            return null;
        }

        if (review.getVideojuego() == null || review.getVideojuego().getIdVideojuego() == null) {
            System.out.println("Error al actualizar reseña: ID de Videojuego es nulo.");
            return null;
        }
        Optional<VideojuegoDTO> videojuegoOpt = videojuegoRepository.findById(review.getVideojuego().getIdVideojuego());
        if (videojuegoOpt.isEmpty()) {
            System.out.println("Error al actualizar reseña: Videojuego con ID " + review.getVideojuego().getIdVideojuego() + " no encontrado.");
            return null;
        }
        review.setVideojuego(videojuegoOpt.get());

        if (review.getUser() == null || review.getUser().getIdUsuario() == null) {
            System.out.println("Error al actualizar reseña: ID de Usuario es nulo.");
            return null;
        }
        Optional<UserDTO> userOpt = userRepository.findById(review.getUser().getIdUsuario());
        if (userOpt.isEmpty()) {
            System.out.println("Error al actualizar reseña: Usuario con ID " + review.getUser().getIdUsuario() + " no encontrado.");
            return null;
        }
        review.setUser(userOpt.get());
        
        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public boolean deleteReviewById(Integer id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            System.out.println("Reseña con ID " + id + " eliminada exitosamente.");
            return true;
        } else {
            System.out.println("No se pudo eliminar: Reseña con ID " + id + " no encontrada.");
            return false;
        }
    }

    private ReviewResponseDTO mapToReviewResponseDTO(ReviewDTO review) {
        ReviewResponseDTO responseDTO = new ReviewResponseDTO();
        responseDTO.setIdReview(review.getIdReview());
        responseDTO.setPuntuacion(review.getPuntuacion());
        responseDTO.setComentario(review.getComentario());
        responseDTO.setFechaReview(review.getFechaReview());

        System.out.println("--- Depuración de ReviewDTO (ID: " + review.getIdReview() + ") ---");
        if (review.getVideojuego() != null) {
            System.out.println("Videojuego cargado: ID=" + review.getVideojuego().getIdVideojuego() + ", Título=" + review.getVideojuego().getTitulo());
            VideojuegoInReviewDTO videojuegoInReview = new VideojuegoInReviewDTO();
            videojuegoInReview.setIdVideojuego(review.getVideojuego().getIdVideojuego());
            videojuegoInReview.setTitulo(review.getVideojuego().getTitulo());
            responseDTO.setVideojuego(videojuegoInReview);
        } else {
            System.out.println("Videojuego es NULL en ReviewDTO para Review ID: " + review.getIdReview());
        }

        if (review.getUser() != null) {
            System.out.println("Usuario cargado: ID=" + review.getUser().getIdUsuario() + ", Nombre=" + review.getUser().getNombreUsuario());
            UserInReviewDTO userInReview = new UserInReviewDTO();
            userInReview.setIdUsuario(review.getUser().getIdUsuario());
            userInReview.setNombreUsuario(review.getUser().getNombreUsuario());
            responseDTO.setUser(userInReview);
        } else {
            System.out.println("Usuario es NULL en ReviewDTO para Review ID: " + review.getIdReview());
        }
        System.out.println("--- Fin Depuración ---");
        return responseDTO;
    }
}