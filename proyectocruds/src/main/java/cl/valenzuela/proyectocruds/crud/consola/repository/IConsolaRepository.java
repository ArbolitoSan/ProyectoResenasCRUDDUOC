package cl.valenzuela.proyectocruds.crud.consola.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.valenzuela.proyectocruds.crud.consola.dto.ConsolaDTO;

@Repository
public interface IConsolaRepository extends CrudRepository<ConsolaDTO, Integer> {
}