package cl.valenzuela.proyectocruds.crud.review.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import cl.valenzuela.proyectocruds.crud.review.dto.ReviewDTO;
import cl.valenzuela.proyectocruds.crud.review.dto.ReviewResponseDTO;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoDTO;
import cl.valenzuela.proyectocruds.crud.user.dto.UserDTO;
import cl.valenzuela.proyectocruds.crud.review.repository.IReviewRepository;
import cl.valenzuela.proyectocruds.crud.videojuego.repository.IVideojuegoRepository;
import cl.valenzuela.proyectocruds.crud.user.repository.IUserRepository;
import cl.valenzuela.proyectocruds.crud.review.service.impl.ReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@ActiveProfiles("test")
public class ReviewServiceTest {

    @Mock
    private IReviewRepository reviewRepository;

    @Mock
    private IVideojuegoRepository videojuegoRepository;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private ReviewService reviewService;

    private ReviewDTO reviewDTO;
    private VideojuegoDTO videojuegoDTO;
    private UserDTO userDTO;
    private ReviewResponseDTO reviewResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        videojuegoDTO = new VideojuegoDTO();
        videojuegoDTO.setIdVideojuego(1);
        videojuegoDTO.setTitulo("The Witcher 3");

        userDTO = new UserDTO();
        userDTO.setIdUsuario(10);
        userDTO.setNombreUsuario("TesterUser ");

        reviewDTO = new ReviewDTO();
        reviewDTO.setIdReview(1);
        reviewDTO.setPuntuacion(5);
        reviewDTO.setComentario("Excelente juego y historia!");
        reviewDTO.setFechaReview(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        reviewDTO.setVideojuego(videojuegoDTO);
        reviewDTO.setUser (userDTO);

        reviewResponseDTO = new ReviewResponseDTO();
        reviewResponseDTO.setIdReview(reviewDTO.getIdReview());
        reviewResponseDTO.setPuntuacion(reviewDTO.getPuntuacion());
        reviewResponseDTO.setComentario(reviewDTO.getComentario());
        reviewResponseDTO.setFechaReview(reviewDTO.getFechaReview());
    }

    @Test
    void testCreateReview_Success() {
        when(reviewRepository.existsById(reviewDTO.getIdReview())).thenReturn(false);
        when(videojuegoRepository.findById(videojuegoDTO.getIdVideojuego())).thenReturn(Optional.of(videojuegoDTO));
        when(userRepository.findById(userDTO.getIdUsuario())).thenReturn(Optional.of(userDTO));
        when(reviewRepository.save(any(ReviewDTO.class))).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.createReview(reviewDTO);

        System.out.println("La prueba 'testCreateReview_Success' se ejecutó y completó.");
        assertNotNull(result);
        assertEquals(reviewDTO.getIdReview(), result.getIdReview());
        assertEquals(reviewDTO.getComentario(), result.getComentario());
        verify(reviewRepository, times(1)).existsById(reviewDTO.getIdReview());
        verify(videojuegoRepository, times(1)).findById(videojuegoDTO.getIdVideojuego());
        verify(userRepository, times(1)).findById(userDTO.getIdUsuario());
        verify(reviewRepository, times(1)).save(reviewDTO);
    }

    @Test
    void testCreateReview_AlreadyExists() {
        when(reviewRepository.existsById(reviewDTO.getIdReview())).thenReturn(true);

        ReviewDTO result = reviewService.createReview(reviewDTO);

        assertNull(result);
        System.out.println("La prueba 'testCreateReview_AlreadyExists' se ejecutó y completó.");
        verify(reviewRepository, times(1)).existsById(reviewDTO.getIdReview());
        verify(reviewRepository, never()).save(any(ReviewDTO.class));
    }

    @Test
    void testCreateReview_VideojuegoNotFound() {
        when(reviewRepository.existsById(reviewDTO.getIdReview())).thenReturn(false);
        when(videojuegoRepository.findById(videojuegoDTO.getIdVideojuego())).thenReturn(Optional.empty());

        ReviewDTO result = reviewService.createReview(reviewDTO);

        assertNull(result);
        System.out.println("La prueba 'testCreateReview_VideojuegoNotFound' se ejecutó y completó.");
        verify(reviewRepository, times(1)).existsById(reviewDTO.getIdReview());
        verify(videojuegoRepository, times(1)).findById(videojuegoDTO.getIdVideojuego());
        verify(userRepository, never()).findById(any());
        verify(reviewRepository, never()).save(any(ReviewDTO.class));
    }

    @Test
    void testCreateReview_UserNotFound() {
        when(reviewRepository.existsById(reviewDTO.getIdReview())).thenReturn(false);
        when(videojuegoRepository.findById(videojuegoDTO.getIdVideojuego())).thenReturn(Optional.of(videojuegoDTO));
        when(userRepository.findById(userDTO.getIdUsuario())).thenReturn(Optional.empty());

        ReviewDTO result = reviewService.createReview(reviewDTO);

        assertNull(result);
        System.out.println("La prueba 'testCreateReview_UserNotFound' se ejecutó y completó.");
        verify(reviewRepository, times(1)).existsById(reviewDTO.getIdReview());
        verify(videojuegoRepository, times(1)).findById(videojuegoDTO.getIdVideojuego());
        verify(userRepository, times(1)).findById(userDTO.getIdUsuario());
        verify(reviewRepository, never()).save(any(ReviewDTO.class));
    }

    @Test
    void testGetReviewById_Success() {
        when(reviewRepository.findById(reviewDTO.getIdReview())).thenReturn(Optional.of(reviewDTO));

        ReviewResponseDTO result = reviewService.getReviewById(reviewDTO.getIdReview());

        assertNotNull(result);
        assertEquals(reviewDTO.getIdReview(), result.getIdReview());
        assertEquals(reviewDTO.getComentario(), result.getComentario());
        assertEquals(reviewDTO.getPuntuacion(), result.getPuntuacion());
        System.out.println("La prueba 'testGetReviewById_Success' se ejecutó y completó.");
        verify(reviewRepository, times(1)).findById(reviewDTO.getIdReview());
    }

    @Test
    void testGetReviewById_NotFound() {
        when(reviewRepository.findById(reviewDTO.getIdReview())).thenReturn(Optional.empty());

        ReviewResponseDTO result = reviewService.getReviewById(reviewDTO.getIdReview());

        assertNull(result);
        System.out.println("La prueba 'testGetReviewById_NotFound' se ejecutó y completó.");
        verify(reviewRepository, times(1)).findById(reviewDTO.getIdReview());
    }

    @Test
    void testGetAllReviews_NotEmpty() {
        List<ReviewDTO> reviewList = new ArrayList<>();
        reviewList.add(reviewDTO);
        reviewList.add(new ReviewDTO(2, 4, "Buen juego", "2024-01-01", videojuegoDTO, userDTO));

        when(reviewRepository.findAll()).thenReturn(reviewList);

        List<ReviewResponseDTO> results = reviewService.getAllReviews();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        assertEquals(reviewDTO.getComentario(), results.get(0).getComentario());
        System.out.println("La prueba 'testGetAllReviews_NotEmpty' se ejecutó y completó.");
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testGetAllReviews_Empty() {
        when(reviewRepository.findAll()).thenReturn(new ArrayList<>());

        List<ReviewResponseDTO> results = reviewService.getAllReviews();

        assertNotNull(results);
        assertTrue(results.isEmpty());
        System.out.println("La prueba 'testGetAllReviews_Empty' se ejecutó y completó.");
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testGetReviewsByVideojuegoId_NotEmpty() {
        List<ReviewDTO> reviewList = new ArrayList<>();
        reviewList.add(reviewDTO);

        when(reviewRepository.findByVideojuego_IdVideojuego(videojuegoDTO.getIdVideojuego())).thenReturn(reviewList);

        List<ReviewResponseDTO> results = reviewService.getReviewsByVideojuegoId(videojuegoDTO.getIdVideojuego());

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(reviewDTO.getComentario(), results.get(0).getComentario());
        System.out.println("La prueba 'testGetReviewsByVideojuegoId_NotEmpty' se ejecutó y completó.");
        verify(reviewRepository, times(1)).findByVideojuego_IdVideojuego(videojuegoDTO.getIdVideojuego());
    }

    @Test
    void testGetReviewsByVideojuegoId_Empty() {
        when(reviewRepository.findByVideojuego_IdVideojuego(videojuegoDTO.getIdVideojuego())).thenReturn(new ArrayList<>());

        List<ReviewResponseDTO> results = reviewService.getReviewsByVideojuegoId(videojuegoDTO.getIdVideojuego());

        assertNotNull(results);
        assertTrue(results.isEmpty());
        System.out.println("La prueba 'testGetReviewsByVideojuegoId_Empty' se ejecutó y completó.");
        verify(reviewRepository, times(1)).findByVideojuego_IdVideojuego(videojuegoDTO.getIdVideojuego());
    }

    @Test
    void testGetReviewsByUserId_NotEmpty() {
        List<ReviewDTO> reviewList = new ArrayList<>();
        reviewList.add(reviewDTO);

        when(reviewRepository.findByUser_IdUsuario(userDTO.getIdUsuario())).thenReturn(reviewList);

        List<ReviewResponseDTO> results = reviewService.getReviewsByUserId(userDTO.getIdUsuario());

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(reviewDTO.getComentario(), results.get(0).getComentario());
        System.out.println("La prueba 'testGetReviewsByUser Id_NotEmpty' se ejecutó y completó.");
        verify(reviewRepository, times(1)).findByUser_IdUsuario(userDTO.getIdUsuario());
    }

    @Test
    void testGetReviewsByUserId_Empty() {
        when(reviewRepository.findByUser_IdUsuario(userDTO.getIdUsuario())).thenReturn(new ArrayList<>());

        List<ReviewResponseDTO> results = reviewService.getReviewsByUserId(userDTO.getIdUsuario());

        assertNotNull(results);
        assertTrue(results.isEmpty());
        System.out.println("La prueba 'testGetReviewsByUser Id_Empty' se ejecutó y completó.");
        verify(reviewRepository, times(1)).findByUser_IdUsuario(userDTO.getIdUsuario());
    }

    @Test
    void testUpdateReview_Success() {
        when(reviewRepository.existsById(reviewDTO.getIdReview())).thenReturn(true);
        when(videojuegoRepository.findById(videojuegoDTO.getIdVideojuego())).thenReturn(Optional.of(videojuegoDTO));
        when(userRepository.findById(userDTO.getIdUsuario())).thenReturn(Optional.of(userDTO));
        when(reviewRepository.save(any(ReviewDTO.class))).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.updateReview(reviewDTO);

        assertNotNull(result);
        assertEquals(reviewDTO.getIdReview(), result.getIdReview());
        System.out.println("La prueba 'testUpdateReview_Success' se ejecutó y completó.");
        verify(reviewRepository, times(1)).existsById(reviewDTO.getIdReview());
        verify(videojuegoRepository, times(1)).findById(videojuegoDTO.getIdVideojuego());
        verify(userRepository, times(1)).findById(userDTO.getIdUsuario());
        verify(reviewRepository, times(1)).save(reviewDTO);
    }

    @Test
    void testUpdateReview_NotFound() {
        when(reviewRepository.existsById(reviewDTO.getIdReview())).thenReturn(false);

        ReviewDTO result = reviewService.updateReview(reviewDTO);

        assertNull(result);
        System.out.println("La prueba 'testUpdateReview_NotFound' se ejecutó y completó.");
        verify(reviewRepository, times(1)).existsById(reviewDTO.getIdReview());
        verify(reviewRepository, never()).save(any(ReviewDTO.class));
    }

    @Test
    void testUpdateReview_VideojuegoNotFound() {
        when(reviewRepository.existsById(reviewDTO.getIdReview())).thenReturn(true);
        when(videojuegoRepository.findById(videojuegoDTO.getIdVideojuego())).thenReturn(Optional.empty());

        ReviewDTO result = reviewService.updateReview(reviewDTO);

        assertNull(result);
        System.out.println("La prueba 'testUpdateReview_VideojuegoNotFound' se ejecutó y completó.");
        verify(reviewRepository, times(1)).existsById(reviewDTO.getIdReview());
        verify(videojuegoRepository, times(1)).findById(videojuegoDTO.getIdVideojuego());
        verify(userRepository, never()).findById(any());
        verify(reviewRepository, never()).save(any(ReviewDTO.class));
    }

    @Test
    void testUpdateReview_UserNotFound() {
        when(reviewRepository.existsById(reviewDTO.getIdReview())).thenReturn(true);
        when(videojuegoRepository.findById(videojuegoDTO.getIdVideojuego())).thenReturn(Optional.of(videojuegoDTO));
        when(userRepository.findById(userDTO.getIdUsuario())).thenReturn(Optional.empty());

        ReviewDTO result = reviewService.updateReview(reviewDTO);

        assertNull(result);
        System.out.println("La prueba 'testUpdateReview_UserNotFound' se ejecutó y completó.");
        verify(reviewRepository, times(1)).existsById(reviewDTO.getIdReview());
        verify(videojuegoRepository, times(1)).findById(videojuegoDTO.getIdVideojuego());
        verify(userRepository, times(1)).findById(userDTO.getIdUsuario());
        verify(reviewRepository, never()).save(any(ReviewDTO.class));
    }

    @Test
    void testDeleteReviewById_Success() {
        when(reviewRepository.existsById(reviewDTO.getIdReview())).thenReturn(true);
        doNothing().when(reviewRepository).deleteById(reviewDTO.getIdReview());

        boolean result = reviewService.deleteReviewById(reviewDTO.getIdReview());

        assertTrue(result);
        System.out.println("La prueba 'testDeleteReviewById_Success' se ejecutó y completó.");
        verify(reviewRepository, times(1)).existsById(reviewDTO.getIdReview());
        verify(reviewRepository, times(1)).deleteById(reviewDTO.getIdReview());
    }

    @Test
    void testDeleteReviewById_NotFound() {
        when(reviewRepository.existsById(reviewDTO.getIdReview())).thenReturn(false);

        boolean result = reviewService.deleteReviewById(reviewDTO.getIdReview());

        assertFalse(result);
        System.out.println("La prueba 'testDeleteReviewById_NotFound' se ejecutó y completó.");
        verify(reviewRepository, times(1)).existsById(reviewDTO.getIdReview());
        verify(reviewRepository, never()).deleteById(any());
    }
}