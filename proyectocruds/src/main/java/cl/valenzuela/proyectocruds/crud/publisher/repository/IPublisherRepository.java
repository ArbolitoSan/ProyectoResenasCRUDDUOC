package cl.valenzuela.proyectocruds.crud.publisher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.valenzuela.proyectocruds.crud.publisher.dto.PublisherDTO;

@Repository
public interface IPublisherRepository extends CrudRepository<PublisherDTO, Integer> {
}