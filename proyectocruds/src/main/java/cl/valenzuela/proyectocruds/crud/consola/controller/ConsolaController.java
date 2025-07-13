package cl.valenzuela.proyectocruds.crud.consola.controller;

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

import cl.valenzuela.proyectocruds.crud.consola.dto.ConsolaDTO;
import cl.valenzuela.proyectocruds.crud.consola.dto.ConsolaResponseDTO;
import cl.valenzuela.proyectocruds.crud.consola.service.IConsolaService;

@RestController
@RequestMapping("/api/crud/consola")
@Tag(name = "Controlador de Consolas", description = "Operaciones relacionadas con consolas de videojuegos")
public class ConsolaController {

    @Autowired
    private IConsolaService consolaService;

    @PostMapping
    @Operation(summary = "Crear una nueva consola", description = "Agrega una nueva consola al sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consola creada exitosamente",
                    content = @Content(schema = @Schema(implementation = ConsolaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, la consola no pudo ser creada")
    })
    public ResponseEntity<ConsolaDTO> createConsola(@RequestBody ConsolaDTO consola) {
        ConsolaDTO nuevaConsola = consolaService.createConsola(consola);
        if (nuevaConsola != null) {
            return new ResponseEntity<>(nuevaConsola, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todas las consolas", description = "Recupera una lista de todas las consolas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consolas recuperada exitosamente",
                    content = @Content(schema = @Schema(implementation = ConsolaResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron consolas")
    })
    public ResponseEntity<List<ConsolaResponseDTO>> getAllConsolas() {
        List<ConsolaResponseDTO> consolas = consolaService.getAllConsolasResponse();
        if (consolas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(consolas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener consola por ID", description = "Recupera una consola por su ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consola recuperada exitosamente por ID",
                    content = @Content(schema = @Schema(implementation = ConsolaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Consola no encontrada")
    })
    public ResponseEntity<ConsolaResponseDTO> getConsolaById(@PathVariable Integer id) {
        ConsolaResponseDTO consola = consolaService.getConsolaResponseById(id);
        if (consola != null) {
            return new ResponseEntity<>(consola, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una consola existente", description = "Actualiza la información de una consola existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consola actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = ConsolaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, el ID de la consola no coincide o datos inválidos")
    })
    public ResponseEntity<ConsolaDTO> updateConsola(@PathVariable Integer id, @RequestBody ConsolaDTO consola) {
        if (!id.equals(consola.getIdConsola())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        ConsolaDTO consolaActualizada = consolaService.updateConsola(consola);
        if (consolaActualizada != null) {
            return new ResponseEntity<>(consolaActualizada, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una consola por ID", description = "Elimina una consola del sistema por su ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consola eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Consola no encontrada para eliminar")
    })
    public ResponseEntity<HttpStatus> deleteConsolaById(@PathVariable Integer id) {
        boolean deleted = consolaService.deleteConsolaById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}