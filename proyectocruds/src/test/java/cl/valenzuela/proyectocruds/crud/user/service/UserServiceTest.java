package cl.valenzuela.proyectocruds.crud.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import cl.valenzuela.proyectocruds.crud.user.dto.UserDTO;
import cl.valenzuela.proyectocruds.crud.user.dto.UserResponseDTO;
import cl.valenzuela.proyectocruds.crud.user.dto.ReviewInUserResponseDTO;
import cl.valenzuela.proyectocruds.crud.user.dto.VideojuegoInReviewInUserDTO;
import cl.valenzuela.proyectocruds.crud.user.repository.IUserRepository;
import cl.valenzuela.proyectocruds.crud.user.service.impl.UserService;
import cl.valenzuela.proyectocruds.crud.review.dto.ReviewDTO;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private UserResponseDTO userResponseDTO;
    private ReviewDTO reviewDTO;
    private VideojuegoDTO videojuegoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO();
        userDTO.setIdUsuario(1);
        userDTO.setNombreUsuario("testuser");
        userDTO.setEmail("test@example.com");
        userDTO.setFechaRegistro(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        userDTO.setReviews(new ArrayList<>());

        videojuegoDTO = new VideojuegoDTO();
        videojuegoDTO.setIdVideojuego(101);
        videojuegoDTO.setTitulo("Juego de Prueba");

        reviewDTO = new ReviewDTO();
        reviewDTO.setIdReview(201);
        reviewDTO.setPuntuacion(4);
        reviewDTO.setComentario("Buena experiencia.");
        reviewDTO.setFechaReview("2023-01-15 10:00:00");
        reviewDTO.setVideojuego(videojuegoDTO);
        reviewDTO.setUser(userDTO);

        userDTO.getReviews().add(reviewDTO);

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setIdUsuario(userDTO.getIdUsuario());
        userResponseDTO.setNombreUsuario(userDTO.getNombreUsuario());
        userResponseDTO.setEmail(userDTO.getEmail());
        userResponseDTO.setFechaRegistro(userDTO.getFechaRegistro());

        ReviewInUserResponseDTO reviewInUserResponseDTO = new ReviewInUserResponseDTO();
        reviewInUserResponseDTO.setIdReview(reviewDTO.getIdReview());
        reviewInUserResponseDTO.setPuntuacion(reviewDTO.getPuntuacion());
        reviewInUserResponseDTO.setComentario(reviewDTO.getComentario());
        reviewInUserResponseDTO.setFechaReview(reviewDTO.getFechaReview());

        VideojuegoInReviewInUserDTO vjInReviewInUser = new VideojuegoInReviewInUserDTO();
        vjInReviewInUser.setIdVideojuego(videojuegoDTO.getIdVideojuego());
        vjInReviewInUser.setTitulo(videojuegoDTO.getTitulo());
        reviewInUserResponseDTO.setVideojuego(vjInReviewInUser);

        userResponseDTO.setReviews(Arrays.asList(reviewInUserResponseDTO));
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.existsById(userDTO.getIdUsuario())).thenReturn(false);
        when(userRepository.save(any(UserDTO.class))).thenReturn(userDTO);

        UserDTO result = userService.createUser(userDTO);

        assertNotNull(result);
        assertEquals(userDTO.getIdUsuario(), result.getIdUsuario());
        assertEquals(userDTO.getNombreUsuario(), result.getNombreUsuario());
        verify(userRepository, times(1)).existsById(userDTO.getIdUsuario());
        verify(userRepository, times(1)).save(userDTO);
    }

    @Test
    void testCreateUser_AlreadyExists() {
        when(userRepository.existsById(userDTO.getIdUsuario())).thenReturn(true);

        UserDTO result = userService.createUser(userDTO);

        assertNull(result);
        verify(userRepository, times(1)).existsById(userDTO.getIdUsuario());
        verify(userRepository, never()).save(any(UserDTO.class));
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(userDTO.getIdUsuario())).thenReturn(Optional.of(userDTO));

        UserResponseDTO result = userService.getUserById(userDTO.getIdUsuario());

        assertNotNull(result);
        assertEquals(userDTO.getIdUsuario(), result.getIdUsuario());
        assertEquals(userDTO.getNombreUsuario(), result.getNombreUsuario());
        assertFalse(result.getReviews().isEmpty());
        assertEquals(1, result.getReviews().size());
        assertEquals(reviewDTO.getIdReview(), result.getReviews().get(0).getIdReview());
        verify(userRepository, times(1)).findById(userDTO.getIdUsuario());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(userDTO.getIdUsuario())).thenReturn(Optional.empty());

        UserResponseDTO result = userService.getUserById(userDTO.getIdUsuario());

        assertNull(result);
        verify(userRepository, times(1)).findById(userDTO.getIdUsuario());
    }

    @Test
    void testGetAllUsers_NotEmpty() {
        List<UserDTO> userList = Arrays.asList(userDTO, new UserDTO(2, "anotheruser", "another@example.com", "2023-02-01 11:00:00", new ArrayList<>()));
        when(userRepository.findAll()).thenReturn(userList);

        List<UserResponseDTO> results = userService.getAllUsers();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        assertEquals(userDTO.getNombreUsuario(), results.get(0).getNombreUsuario());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetAllUsers_Empty() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<UserResponseDTO> results = userService.getAllUsers();

        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser_Success() {
        when(userRepository.existsById(userDTO.getIdUsuario())).thenReturn(true);
        when(userRepository.save(any(UserDTO.class))).thenReturn(userDTO);

        UserDTO result = userService.updateUser(userDTO);

        assertNotNull(result);
        assertEquals(userDTO.getIdUsuario(), result.getIdUsuario());
        verify(userRepository, times(1)).existsById(userDTO.getIdUsuario());
        verify(userRepository, times(1)).save(userDTO);
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userRepository.existsById(userDTO.getIdUsuario())).thenReturn(false);

        UserDTO result = userService.updateUser(userDTO);

        assertNull(result);
        verify(userRepository, times(1)).existsById(userDTO.getIdUsuario());
        verify(userRepository, never()).save(any(UserDTO.class));
    }

    @Test
    void testDeleteUserById_Success() {
        when(userRepository.existsById(userDTO.getIdUsuario())).thenReturn(true);
        doNothing().when(userRepository).deleteById(userDTO.getIdUsuario());

        boolean result = userService.deleteUserById(userDTO.getIdUsuario());

        assertTrue(result);
        verify(userRepository, times(1)).existsById(userDTO.getIdUsuario());
        verify(userRepository, times(1)).deleteById(userDTO.getIdUsuario());
    }

    @Test
    void testDeleteUserById_NotFound() {
        when(userRepository.existsById(userDTO.getIdUsuario())).thenReturn(false);

        boolean result = userService.deleteUserById(userDTO.getIdUsuario());

        assertFalse(result);
        verify(userRepository, times(1)).existsById(userDTO.getIdUsuario());
        verify(userRepository, never()).deleteById(any());
    }
}