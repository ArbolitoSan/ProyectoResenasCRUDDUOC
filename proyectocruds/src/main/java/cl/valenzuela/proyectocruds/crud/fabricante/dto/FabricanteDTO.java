package cl.valenzuela.proyectocruds.crud.fabricante.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import cl.valenzuela.proyectocruds.crud.consola.dto.ConsolaDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "fabricante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa a un fabricante de consolas")
public class FabricanteDTO {

    @Id
    @Column(name = "id_fabricante")
    @Schema(description = "Identificador único del fabricante", example = "1")
    private Integer idFabricante;

    @Column(name = "nombre", nullable = false, unique = true)
    @Schema(description = "Nombre del fabricante", example = "Sony", required = true)
    private String nombre;

    @Column(name = "fecha_inicio_operaciones")
    @Schema(description = "Fecha en que el fabricante inició operaciones", example = "1946-05-07")
    private String fechaInicioOperaciones;

    @Column(name = "fecha_fin_operaciones")
    @Schema(description = "Fecha en que el fabricante cesó operaciones (si aplica)", example = "null")
    private String fechaFinOperaciones;

    @OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL)
    @JsonManagedReference("fabricante-consola")
    @Schema(description = "Lista de consolas producidas por este fabricante")
    private List<ConsolaDTO> consolas;
}