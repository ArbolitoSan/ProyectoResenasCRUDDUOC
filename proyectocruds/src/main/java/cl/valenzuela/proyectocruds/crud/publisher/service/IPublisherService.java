package cl.valenzuela.proyectocruds.crud.publisher.service;

import java.util.List;
import cl.valenzuela.proyectocruds.crud.publisher.dto.PublisherDTO;
import cl.valenzuela.proyectocruds.crud.publisher.dto.PublisherResponseDTO;

public interface IPublisherService {

    PublisherDTO createPublisher(PublisherDTO publisher);
    List<PublisherResponseDTO> getAllPublishersResponse();
    PublisherResponseDTO getPublisherResponseById(Integer id);
    PublisherDTO getPublisherById(Integer id);
    boolean deletePublisherById(Integer id);
    PublisherDTO updatePublisher(PublisherDTO publisher);
}