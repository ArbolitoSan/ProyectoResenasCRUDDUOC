package cl.valenzuela.proyectocruds.crud.developer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.valenzuela.proyectocruds.crud.developer.dto.DeveloperDTO;

@Repository
public interface IDeveloperRepository extends CrudRepository<DeveloperDTO, Integer> {
}