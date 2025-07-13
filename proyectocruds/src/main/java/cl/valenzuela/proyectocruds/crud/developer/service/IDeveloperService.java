package cl.valenzuela.proyectocruds.crud.developer.service;

import java.util.List;
import cl.valenzuela.proyectocruds.crud.developer.dto.DeveloperDTO;

public interface IDeveloperService {
    DeveloperDTO createDeveloper(DeveloperDTO developer);
    List<DeveloperDTO> getAllDevelopers();
    DeveloperDTO getDeveloperById(Integer id);
    boolean deleteDeveloperById(Integer id);
    DeveloperDTO updateDeveloper(DeveloperDTO developer);
}