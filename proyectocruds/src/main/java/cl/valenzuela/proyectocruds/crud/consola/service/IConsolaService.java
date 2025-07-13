package cl.valenzuela.proyectocruds.crud.consola.service;

import java.util.List;
import cl.valenzuela.proyectocruds.crud.consola.dto.ConsolaDTO;
import cl.valenzuela.proyectocruds.crud.consola.dto.ConsolaResponseDTO;

public interface IConsolaService {

    ConsolaDTO createConsola(ConsolaDTO consola);

    List<ConsolaResponseDTO> getAllConsolasResponse();
    ConsolaResponseDTO getConsolaResponseById(Integer id);

    ConsolaDTO getConsolaById(Integer id);

    boolean deleteConsolaById(Integer id);

    ConsolaDTO updateConsola(ConsolaDTO consola);
}