package cl.valenzuela.proyectocruds.crud.fabricante.service;

import java.util.List;
import cl.valenzuela.proyectocruds.crud.fabricante.dto.FabricanteDTO;

public interface IFabricanteService {
    FabricanteDTO createFabricante(FabricanteDTO fabricante);
    List<FabricanteDTO> getAllFabricantes();
    FabricanteDTO getFabricanteById(Integer id);
    boolean deleteFabricanteById(Integer id);
    FabricanteDTO updateFabricante(FabricanteDTO fabricante);
}