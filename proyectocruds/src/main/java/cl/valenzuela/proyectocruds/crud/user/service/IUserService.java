package cl.valenzuela.proyectocruds.crud.user.service;

import cl.valenzuela.proyectocruds.crud.user.dto.UserDTO;
import cl.valenzuela.proyectocruds.crud.user.dto.UserResponseDTO;
import java.util.List;

public interface IUserService {
    UserDTO createUser(UserDTO user);
    UserResponseDTO getUserById(Integer id);
    List<UserResponseDTO> getAllUsers();
    UserDTO updateUser(UserDTO user);
    boolean deleteUserById(Integer id);
}