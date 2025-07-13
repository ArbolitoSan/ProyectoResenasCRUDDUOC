package cl.valenzuela.proyectocruds.crud.videojuego.assembler;

import cl.valenzuela.proyectocruds.crud.videojuego.controller.VideojuegoController;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VideojuegoAssembler implements RepresentationModelAssembler<VideojuegoDTO, EntityModel<VideojuegoDTO>> {

    @Override
    public @NonNull EntityModel<VideojuegoDTO> toModel(@NonNull VideojuegoDTO videojuego) {
        return EntityModel.of(videojuego,
            linkTo(methodOn(VideojuegoController.class).getVideojuegoById(videojuego.getIdVideojuego())).withSelfRel(),
            linkTo(methodOn(VideojuegoController.class).getAllVideojuegos()).withRel("videojuegos"));
    }
}