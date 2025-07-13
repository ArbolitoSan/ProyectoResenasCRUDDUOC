package cl.valenzuela.proyectocruds.crud.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Integer idUsuario;
    private String nombreUsuario;
    private String email;
    private String fechaRegistro;
    private List<ReviewInUserResponseDTO> reviews;
}