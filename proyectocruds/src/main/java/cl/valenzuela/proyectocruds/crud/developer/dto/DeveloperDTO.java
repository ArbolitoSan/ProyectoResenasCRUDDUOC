package cl.valenzuela.proyectocruds.crud.developer.dto;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "developer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "idDeveloper"
)
public class DeveloperDTO {
    @Id
    @Column(name = "id_developer")
    private Integer idDeveloper;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "fecha_fundacion")
    private String fechaFundacion;

    @Column(name = "pais_origen")
    private String paisOrigen;

    @ManyToMany(mappedBy = "developers", fetch = FetchType.EAGER)
    private List<VideojuegoDTO> videojuegosDesarrollados;
}