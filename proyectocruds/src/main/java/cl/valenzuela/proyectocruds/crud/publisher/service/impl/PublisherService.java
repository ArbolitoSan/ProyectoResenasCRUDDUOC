package cl.valenzuela.proyectocruds.crud.publisher.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.valenzuela.proyectocruds.crud.publisher.dto.PublisherDTO;
import cl.valenzuela.proyectocruds.crud.publisher.dto.PublisherResponseDTO;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoMinDTO;
import cl.valenzuela.proyectocruds.crud.publisher.repository.IPublisherRepository;
import cl.valenzuela.proyectocruds.crud.publisher.service.IPublisherService;

@Service
public class PublisherService implements IPublisherService {

    @Autowired
    private IPublisherRepository publisherRepository;

    @Override
    public PublisherDTO createPublisher(PublisherDTO publisher) {
        if (publisherRepository.existsById(publisher.getIdPublisher())) {
            System.out.println("Error al crear publisher: Ya existe un publisher con el ID " + publisher.getIdPublisher());
            return null;
        }
        return publisherRepository.save(publisher);
    }

    @Override
    public List<PublisherResponseDTO> getAllPublishersResponse() {
        List<PublisherDTO> publishers = (List<PublisherDTO>) publisherRepository.findAll();
        if (publishers.isEmpty()) {
            System.out.println("No se encontraron publishers.");
        }
        return publishers.stream()
                         .map(this::mapToPublisherResponseDTO)
                         .collect(Collectors.toList());
    }

    @Override
    public PublisherResponseDTO getPublisherResponseById(Integer id) {
        Optional<PublisherDTO> publisherOptional = publisherRepository.findById(id);
        if (publisherOptional.isEmpty()) {
            System.out.println("Publisher con ID " + id + " no encontrado.");
            return null;
        }
        return mapToPublisherResponseDTO(publisherOptional.get());
    }

    @Override
    public PublisherDTO getPublisherById(Integer id) {
        return publisherRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deletePublisherById(Integer id) {
        if (publisherRepository.existsById(id)) {
            publisherRepository.deleteById(id);
            System.out.println("Publisher con ID " + id + " eliminado exitosamente.");
            return true;
        } else {
            System.out.println("No se pudo eliminar: Publisher con ID " + id + " no encontrado.");
            return false;
        }
    }

    @Override
    public PublisherDTO updatePublisher(PublisherDTO publisher) {
        if (!publisherRepository.existsById(publisher.getIdPublisher())) {
            System.out.println("Error al actualizar publisher: Publisher con ID " + publisher.getIdPublisher() + " no encontrado.");
            return null;
        }
        System.out.println("Publisher con ID " + publisher.getIdPublisher() + " actualizado exitosamente.");
        return publisherRepository.save(publisher);
    }

    private PublisherResponseDTO mapToPublisherResponseDTO(PublisherDTO publisher) {
        PublisherResponseDTO responseDTO = new PublisherResponseDTO();
        responseDTO.setIdPublisher(publisher.getIdPublisher());
        responseDTO.setNombre(publisher.getNombre());
        responseDTO.setFechaInicioOperaciones(publisher.getFechaInicioOperaciones());
        responseDTO.setFechaFinOperaciones(publisher.getFechaFinOperaciones());

        if (publisher.getVideojuegos() != null && !publisher.getVideojuegos().isEmpty()) {
            responseDTO.setVideojuegos(
                publisher.getVideojuegos().stream()
                         .map(v -> new VideojuegoMinDTO(v.getIdVideojuego(), v.getTitulo()))
                         .collect(Collectors.toList())
            );
        } else {
            responseDTO.setVideojuegos(List.of());
        }
        return responseDTO;
    }
}