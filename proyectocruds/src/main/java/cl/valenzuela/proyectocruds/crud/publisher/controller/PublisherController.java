package cl.valenzuela.proyectocruds.crud.publisher.controller;

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

import cl.valenzuela.proyectocruds.crud.publisher.dto.PublisherDTO;
import cl.valenzuela.proyectocruds.crud.publisher.dto.PublisherResponseDTO;
import cl.valenzuela.proyectocruds.crud.publisher.service.IPublisherService;

@RestController
@RequestMapping("/api/crud/publisher")
@Tag(name = "Controlador de Publicadores", description = "Operaciones relacionadas con publicadores")
public class PublisherController {

    @Autowired
    private IPublisherService publisherService;

    @PostMapping
    @Operation(summary = "Crear un nuevo publicador", description = "Agrega un nuevo publicador al sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Publicador creado exitosamente",
                    content = @Content(schema = @Schema(implementation = PublisherDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, el publicador no pudo ser creado")
    })
    public ResponseEntity<PublisherDTO> createPublisher(@RequestBody PublisherDTO publisher) {
        PublisherDTO nuevoPublisher = publisherService.createPublisher(publisher);
        if (nuevoPublisher != null) {
            return new ResponseEntity<>(nuevoPublisher, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todos los publicadores", description = "Recupera una lista de todos los publicadores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de publicadores recuperada exitosamente",
                    content = @Content(schema = @Schema(implementation = PublisherResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron publicadores")
    })
    public ResponseEntity<List<PublisherResponseDTO>> getAllPublishers() {
        List<PublisherResponseDTO> publishers = publisherService.getAllPublishersResponse();
        if (publishers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener publicador por ID", description = "Recupera un publicador por su ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicador recuperado exitosamente por ID",
                    content = @Content(schema = @Schema(implementation = PublisherResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Publicador no encontrado")
    })
    public ResponseEntity<PublisherResponseDTO> getPublisherById(@PathVariable Integer id) {
        PublisherResponseDTO publisher = publisherService.getPublisherResponseById(id);
        if (publisher != null) {
            return new ResponseEntity<>(publisher, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un publicador existente", description = "Actualiza la información de un publicador existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicador actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = PublisherDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, el ID del publicador no coincide o datos inválidos")
    })
    public ResponseEntity<PublisherDTO> updatePublisher(@PathVariable Integer id, @RequestBody PublisherDTO publisher) {
        if (!id.equals(publisher.getIdPublisher())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        PublisherDTO publisherActualizado = publisherService.updatePublisher(publisher);
        if (publisherActualizado != null) {
            return new ResponseEntity<>(publisherActualizado, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un publicador por ID", description = "Elimina un publicador del sistema por su ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Publicador eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Publicador no encontrado para eliminar")
    })
    public ResponseEntity<HttpStatus> deletePublisherById(@PathVariable Integer id) {
        boolean deleted = publisherService.deletePublisherById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}