package cl.valenzuela.proyectocruds.crud.consola.dto;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoDTO;
import cl.valenzuela.proyectocruds.crud.fabricante.dto.FabricanteDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "consola")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsolaDTO {
    @Id
    @Column(name = "id_consola")
    private Integer idConsola;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_salida")
    private String fechaSalida;

    @Column(name = "generacion_consola")
    private String generacionConsola;

    @ManyToOne
    @JoinColumn(name = "id_fabricante", nullable = false)
    @JsonBackReference("fabricante-consola")
    private FabricanteDTO fabricante;

    @OneToMany(mappedBy = "consola", cascade = CascadeType.ALL)
    @JsonManagedReference("consola-videojuego")
    private List<VideojuegoDTO> videojuegos;
}