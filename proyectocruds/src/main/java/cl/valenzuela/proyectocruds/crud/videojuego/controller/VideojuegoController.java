package cl.valenzuela.proyectocruds.crud.videojuego.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoDTO;
import cl.valenzuela.proyectocruds.crud.videojuego.service.IVideojuegoService;
import cl.valenzuela.proyectocruds.crud.videojuego.assembler.VideojuegoAssembler;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/crud/videojuego")
@Tag(name = "Videojuegos", description = "CRUD de videojuegos")
public class VideojuegoController {

    @Autowired
    private IVideojuegoService videojuegoService;

    @Autowired
    private VideojuegoAssembler videojuegoAssembler;

    @PostMapping
    @Operation(summary = "Crear un nuevo videojuego")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Videojuego creado exitosamente",
                    content = @Content(schema = @Schema(implementation = VideojuegoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<EntityModel<VideojuegoDTO>> createVideojuego(@RequestBody VideojuegoDTO videojuego) {
        VideojuegoDTO nuevoVideojuego = videojuegoService.createVideojuego(videojuego);
        if (nuevoVideojuego != null) {
            EntityModel<VideojuegoDTO> entityModel = videojuegoAssembler.toModel(nuevoVideojuego);
            return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los videojuegos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Videojuegos encontrados",
                    content = @Content(schema = @Schema(implementation = CollectionModel.class))),
            @ApiResponse(responseCode = "204", description = "No hay videojuegos")
    })
    public ResponseEntity<CollectionModel<EntityModel<VideojuegoDTO>>> getAllVideojuegos() {
        List<VideojuegoDTO> videojuegos = videojuegoService.getAllVideojuegos();

        if (videojuegos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<EntityModel<VideojuegoDTO>> entities = videojuegos.stream()
                .map(videojuegoAssembler::toModel)
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                CollectionModel.of(entities, linkTo(methodOn(VideojuegoController.class).getAllVideojuegos()).withSelfRel()),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un videojuego por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Videojuego encontrado",
                    content = @Content(schema = @Schema(implementation = VideojuegoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Videojuego no encontrado")
    })
    public ResponseEntity<EntityModel<VideojuegoDTO>> getVideojuegoById(
            @Parameter(description = "ID del videojuego a obtener") @PathVariable Integer id) {

        VideojuegoDTO videojuego = videojuegoService.getVideojuegoById(id);

        if (videojuego != null) {
            EntityModel<VideojuegoDTO> entityModel = videojuegoAssembler.toModel(videojuego);
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un videojuego existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Videojuego actualizado",
                    content = @Content(schema = @Schema(implementation = VideojuegoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<EntityModel<VideojuegoDTO>> updateVideojuego(
            @Parameter(description = "ID del videojuego a actualizar") @PathVariable Integer id,
            @RequestBody VideojuegoDTO videojuego) {

        if (!id.equals(videojuego.getIdVideojuego())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        VideojuegoDTO videojuegoActualizado = videojuegoService.updateVideojuego(videojuego);
        if (videojuegoActualizado != null) {
            EntityModel<VideojuegoDTO> entityModel = videojuegoAssembler.toModel(videojuegoActualizado);
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un videojuego por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Videojuego eliminado"),
            @ApiResponse(responseCode = "404", description = "Videojuego no encontrado")
    })
    public ResponseEntity<HttpStatus> deleteVideojuegoById(
            @Parameter(description = "ID del videojuego a eliminar") @PathVariable Integer id) {

        boolean deleted = videojuegoService.deleteVideojuegoById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}