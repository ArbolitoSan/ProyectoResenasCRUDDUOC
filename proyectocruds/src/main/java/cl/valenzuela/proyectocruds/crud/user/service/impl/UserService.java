package cl.valenzuela.proyectocruds.crud.user.service.impl;

import cl.valenzuela.proyectocruds.crud.user.dto.UserDTO;
import cl.valenzuela.proyectocruds.crud.user.repository.IUserRepository;
import cl.valenzuela.proyectocruds.crud.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cl.valenzuela.proyectocruds.crud.user.dto.UserResponseDTO;
import cl.valenzuela.proyectocruds.crud.user.dto.ReviewInUserResponseDTO;
import cl.valenzuela.proyectocruds.crud.user.dto.VideojuegoInReviewInUserDTO;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    @Transactional
    public UserDTO createUser(UserDTO user) {
        if (userRepository.existsById(user.getIdUsuario())) {
            System.out.println("Error al crear usuario: Ya existe un usuario con el ID " + user.getIdUsuario());
            return null;
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Integer id) {
        Optional<UserDTO> userEncontrado = userRepository.findById(id);
        if (userEncontrado.isEmpty()) {
            System.out.println("Usuario con ID " + id + " no encontrado.");
            return null;
        }
        return mapToUserResponseDTO(userEncontrado.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        List<UserDTO> users = (List<UserDTO>) userRepository.findAll();
        if (users.isEmpty()) {
            System.out.println("No se encontraron usuarios.");
        }
        return users.stream()
                    .map(this::mapToUserResponseDTO)
                    .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO updateUser(UserDTO user) {
        if (!userRepository.existsById(user.getIdUsuario())) {
            System.out.println("Error al actualizar usuario: Usuario con ID " + user.getIdUsuario() + " no encontrado.");
            return null;
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean deleteUserById(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            System.out.println("Usuario con ID " + id + " eliminado exitosamente.");
            return true;
        } else {
            System.out.println("No se pudo eliminar: Usuario con ID " + id + " no encontrado.");
            return false;
        }
    }

    private UserResponseDTO mapToUserResponseDTO(UserDTO user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setIdUsuario(user.getIdUsuario());
        responseDTO.setNombreUsuario(user.getNombreUsuario());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setFechaRegistro(user.getFechaRegistro());

        if (user.getReviews() != null && !user.getReviews().isEmpty()) {
            responseDTO.setReviews(user.getReviews().stream()
                .map(review -> {
                    ReviewInUserResponseDTO reviewInUser = new ReviewInUserResponseDTO();
                    reviewInUser.setIdReview(review.getIdReview());
                    reviewInUser.setPuntuacion(review.getPuntuacion());
                    reviewInUser.setComentario(review.getComentario());
                    reviewInUser.setFechaReview(review.getFechaReview());

                    if (review.getVideojuego() != null) {
                        VideojuegoInReviewInUserDTO vjInReviewInUser = new VideojuegoInReviewInUserDTO();
                        vjInReviewInUser.setIdVideojuego(review.getVideojuego().getIdVideojuego());
                        vjInReviewInUser.setTitulo(review.getVideojuego().getTitulo());
                        reviewInUser.setVideojuego(vjInReviewInUser);
                    }
                    return reviewInUser;
                })
                .collect(Collectors.toList())
            );
        } else {
            responseDTO.setReviews(List.of());
        }
        return responseDTO;
    }
}