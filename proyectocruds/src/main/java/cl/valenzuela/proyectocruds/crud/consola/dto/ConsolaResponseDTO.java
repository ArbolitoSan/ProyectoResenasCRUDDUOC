package cl.valenzuela.proyectocruds.crud.consola.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "Representación de los datos de respuesta de una Consola, incluyendo el nombre del fabricante")
public class ConsolaResponseDTO {

    @Schema(description = "Identificador único de la Consola", example = "1")
    private int idConsola;

    @Schema(description = "Nombre de la Consola", example = "PlayStation 5")
    private String nombre;

    @Schema(description = "Fecha de lanzamiento de la Consola (YYYY-MM-DD)", example = "2020-11-12")
    private String fechaSalida;

    @Schema(description = "Generación de la Consola", example = "Novena")
    private String generacionConsola;

    @Schema(description = "ID del Fabricante de la Consola", example = "1")
    private int idFabricante;

    @Schema(description = "Nombre del Fabricante de la Consola", example = "Sony")
    private String nombreFabricante;

    @Schema(description = "Lista de videojuegos disponibles para la Consola", accessMode = Schema.AccessMode.READ_ONLY)
    private List<Object> videojuegos;

    public ConsolaResponseDTO() {
    }

    public ConsolaResponseDTO(int idConsola, String nombre, String fechaSalida, String generacionConsola, int idFabricante, String nombreFabricante, List<Object> videojuegos) {
        this.idConsola = idConsola;
        this.nombre = nombre;
        this.fechaSalida = fechaSalida;
        this.generacionConsola = generacionConsola;
        this.idFabricante = idFabricante;
        this.nombreFabricante = nombreFabricante;
        this.videojuegos = videojuegos;
    }
}