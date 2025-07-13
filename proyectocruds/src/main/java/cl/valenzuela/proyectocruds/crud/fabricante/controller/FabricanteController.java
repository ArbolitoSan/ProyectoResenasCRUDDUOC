package cl.valenzuela.proyectocruds.crud.fabricante.controller;

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

import cl.valenzuela.proyectocruds.crud.fabricante.dto.FabricanteDTO;
import cl.valenzuela.proyectocruds.crud.fabricante.service.IFabricanteService;

@RestController
@RequestMapping("/api/crud/fabricante")
@Tag(name = "Controlador de Fabricantes", description = "Operaciones relacionadas con fabricantes de consolas")
public class FabricanteController {

    @Autowired
    private IFabricanteService fabricanteService;

    @PostMapping
    @Operation(summary = "Crear un nuevo fabricante", description = "Agrega un nuevo fabricante de consolas al sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fabricante creado exitosamente",
                    content = @Content(schema = @Schema(implementation = FabricanteDTO.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto, el fabricante no pudo ser creado (ej. nombre duplicado)")
    })
    public ResponseEntity<FabricanteDTO> createFabricante(@RequestBody FabricanteDTO fabricante) {
        FabricanteDTO nuevoFabricante = fabricanteService.createFabricante(fabricante);
        if (nuevoFabricante != null) {
            return new ResponseEntity<>(nuevoFabricante, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los fabricantes", description = "Recupera una lista de todos los fabricantes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de fabricantes recuperada exitosamente",
                    content = @Content(schema = @Schema(implementation = FabricanteDTO.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron fabricantes")
    })
    public ResponseEntity<List<FabricanteDTO>> getAllFabricantes() {
        List<FabricanteDTO> fabricantes = fabricanteService.getAllFabricantes();
        if (fabricantes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(fabricantes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener fabricante por ID", description = "Recupera un fabricante por su ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fabricante recuperado exitosamente por ID",
                    content = @Content(schema = @Schema(implementation = FabricanteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Fabricante no encontrado")
    })
    public ResponseEntity<FabricanteDTO> getFabricanteById(@PathVariable Integer id) {
        FabricanteDTO fabricante = fabricanteService.getFabricanteById(id);
        if (fabricante != null) {
            return new ResponseEntity<>(fabricante, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un fabricante existente", description = "Actualiza la información de un fabricante existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fabricante actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = FabricanteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, el ID del fabricante no coincide"),
            @ApiResponse(responseCode = "404", description = "Fabricante no encontrado para actualizar")
    })
    public ResponseEntity<FabricanteDTO> updateFabricante(@PathVariable Integer id, @RequestBody FabricanteDTO fabricante) {
        if (!id.equals(fabricante.getIdFabricante())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        FabricanteDTO fabricanteActualizado = fabricanteService.updateFabricante(fabricante);
        if (fabricanteActualizado != null) {
            return new ResponseEntity<>(fabricanteActualizado/*Pinche Tabo*/, HttpStatus.OK);
        } else {
            return ResponseEntity.status/*Pinche Zuki*/(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un fabricante por ID", description = "Elimina un fabricante del sistema por su ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fabricante eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Fabricante no encontrado para eliminar")
    })
    public ResponseEntity<HttpStatus> deleteFabricanteById(@PathVariable Integer id) {
        boolean deleted = fabricanteService.deleteFabricanteById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}