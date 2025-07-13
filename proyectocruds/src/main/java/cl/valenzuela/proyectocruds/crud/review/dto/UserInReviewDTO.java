package cl.valenzuela.proyectocruds.crud.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInReviewDTO {
    private Integer idUsuario;
    private String nombreUsuario;
}