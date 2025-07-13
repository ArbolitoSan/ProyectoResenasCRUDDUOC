package cl.valenzuela.proyectocruds.crud.review.controller;

import cl.valenzuela.proyectocruds.crud.review.dto.ReviewDTO;
import cl.valenzuela.proyectocruds.crud.review.dto.ReviewResponseDTO;
import cl.valenzuela.proyectocruds.crud.review.service.IReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crud/review")
@Tag(name = "Controlador de Reseñas", description = "Operaciones relacionadas con reseñas")
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    @PostMapping
    @Operation(summary = "Crear una nueva reseña", description = "Crea una nueva reseña para un videojuego")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reseña creada exitosamente",
                    content = @Content(schema = @Schema(implementation = ReviewDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, la reseña no pudo ser creada")
    })
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO review) {
        ReviewDTO newReview = reviewService.createReview(review);
        if (newReview != null) {
            return new ResponseEntity<>(newReview, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las reseñas", description = "Recupera una lista de todas las reseñas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reseñas recuperada exitosamente",
                    content = @Content(schema = @Schema(implementation = ReviewResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron reseñas")
    })
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews() {
        List<ReviewResponseDTO> reviews = reviewService.getAllReviews();
        if (!reviews.isEmpty()) {
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reseña por ID", description = "Recupera una reseña por su ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña recuperada exitosamente por ID",
                    content = @Content(schema = @Schema(implementation = ReviewResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable Integer id) {
        ReviewResponseDTO review = reviewService.getReviewById(id);
        if (review != null) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/videojuego/{idVideojuego}")
    @Operation(summary = "Obtener reseñas por ID de videojuego", description = "Recupera una lista de reseñas para un videojuego específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseñas recuperadas exitosamente para el videojuego",
                    content = @Content(schema = @Schema(implementation = ReviewResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron reseñas para el videojuego especificado")
    })
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByVideojuegoId(@PathVariable Integer idVideojuego) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewsByVideojuegoId(idVideojuego);
        if (!reviews.isEmpty()) {
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{idUsuario}")
    @Operation(summary = "Obtener reseñas por ID de usuario", description = "Recupera una lista de reseñas realizadas por un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseñas recuperadas exitosamente por el usuario",
                    content = @Content(schema = @Schema(implementation = ReviewResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron reseñas para el usuario especificado")
    })
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByUserId(@PathVariable Integer idUsuario) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewsByUserId(idUsuario);
        if (!reviews.isEmpty()) {
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    @Operation(summary = "Actualizar una reseña existente", description = "Actualiza la información de una reseña existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = ReviewDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada para actualizar")
    })
    public ResponseEntity<ReviewDTO> updateReview(@RequestBody ReviewDTO review) {
        ReviewDTO updatedReview = reviewService.updateReview(review);
        if (updatedReview != null) {
            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una reseña por ID", description = "Elimina una reseña del sistema por su ID único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reseña eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada para eliminar")
    })
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        if (reviewService.deleteReviewById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}