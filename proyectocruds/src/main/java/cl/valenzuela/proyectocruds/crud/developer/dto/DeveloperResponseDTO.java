package cl.valenzuela.proyectocruds.crud.developer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperResponseDTO {
    private Integer idDeveloper;
    private String nombre;
    private String fechaFundacion;
    private String paisOrigen;
}   