package cl.valenzuela.proyectocruds.crud.fabricante.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.valenzuela.proyectocruds.crud.fabricante.dto.FabricanteDTO;

@Repository
public interface IFabricanteRepository extends CrudRepository<FabricanteDTO, Integer> {
}