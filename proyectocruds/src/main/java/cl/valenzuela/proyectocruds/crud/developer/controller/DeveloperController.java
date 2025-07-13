package cl.valenzuela.proyectocruds.crud.developer.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.valenzuela.proyectocruds.crud.developer.dto.DeveloperDTO;
import cl.valenzuela.proyectocruds.crud.developer.service.IDeveloperService;

@RestController
@RequestMapping("/api/crud/developer")
@Tag(name = "Controlador de Desarrolladores", description = "Operaciones relacionadas con desarrolladores de videojuegos")
public class DeveloperController {

    @Autowired
    private IDeveloperService developerService;

    @PostMapping
    @Operation(summary = "Crear un nuevo desarrollador", description = "Agrega un nuevo desarrollador de videojuegos al sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Desarrollador creado exitosamente",
                    content = @Content(schema = @Schema(implementation = DeveloperDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, el desarrollador no pudo ser creado")
    })
    public ResponseEntity<DeveloperDTO> createDeveloper(@RequestBody DeveloperDTO developer) {
        DeveloperDTO nuevoDeveloper = developerService.createDeveloper(developer);
        if (nuevoDeveloper != null) {
            return new ResponseEntity<>(nuevoDeveloper, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los desarrolladores", description = "Recupera una lista de todos los desarrolladores de videojuegos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de desarrolladores recuperada exitosamente",
                    content = @Content(schema = @Schema(implementation = DeveloperDTO.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron desarrolladores")
    })
    public ResponseEntity<List<DeveloperDTO>> getAllDevelopers() {
        List<DeveloperDTO> developers = developerService.getAllDevelopers();
        if (developers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(developers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener desarrollador por ID", description = "Recupera un desarrollador de videojuegos por su ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Desarrollador recuperado exitosamente por ID",
                    content = @Content(schema = @Schema(implementation = DeveloperDTO.class))),
            @ApiResponse(responseCode = "404", description = "Desarrollador no encontrado")
    })
    public ResponseEntity<DeveloperDTO> getDeveloperById(@PathVariable Integer id) {
        DeveloperDTO developer = developerService.getDeveloperById(id);
        if (developer != null) {
            return new ResponseEntity<>(developer, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un desarrollador existente", description = "Actualiza la información de un desarrollador de videojuegos existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Desarrollador actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = DeveloperDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, el ID del desarrollador no coincide o datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Desarrollador no encontrado para actualizar")
    })
    public ResponseEntity<DeveloperDTO> updateDeveloper(@PathVariable Integer id, @RequestBody DeveloperDTO developer) {
        if (!id.equals(developer.getIdDeveloper())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        DeveloperDTO developerActualizado = developerService.updateDeveloper(developer);
        if (developerActualizado != null) {
            return new ResponseEntity<>(developerActualizado, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un desarrollador por ID", description = "Elimina un desarrollador de videojuegos del sistema por su ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Desarrollador eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Desarrollador no encontrado para eliminar")
    })
    public ResponseEntity<HttpStatus> deleteDeveloperById(@PathVariable Integer id) {
        boolean deleted = developerService.deleteDeveloperById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}