package cl.valenzuela.proyectocruds.crud.developer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.valenzuela.proyectocruds.crud.developer.dto.DeveloperDTO;
import cl.valenzuela.proyectocruds.crud.developer.repository.IDeveloperRepository;
import cl.valenzuela.proyectocruds.crud.developer.service.IDeveloperService;

@Service
public class DeveloperService implements IDeveloperService {

    @Autowired
    private IDeveloperRepository developerRepository;

    @Override
    public DeveloperDTO createDeveloper(DeveloperDTO developer) {
        if (developerRepository.existsById(developer.getIdDeveloper())) {
            System.out.println("Error al crear developer: Ya existe un developer con el ID " + developer.getIdDeveloper());
            return null;
        }
        return developerRepository.save(developer);
    }

    @Override
    public List<DeveloperDTO> getAllDevelopers() {
        List<DeveloperDTO> developers = (List<DeveloperDTO>) developerRepository.findAll();
        if (developers.isEmpty()) {
            System.out.println("No se encontraron developers.");
        }
        return developers;
    }

    @Override
    public DeveloperDTO getDeveloperById(Integer id) {
        Optional<DeveloperDTO> developerEncontrado = developerRepository.findById(id);
        if (developerEncontrado.isEmpty()) {
            System.out.println("Developer con ID " + id + " no encontrado.");
        }
        return developerEncontrado.orElse(null);
    }

    @Override
    public boolean deleteDeveloperById(Integer id) {
        if (developerRepository.existsById(id)) {
            developerRepository.deleteById(id);
            System.out.println("Developer con ID " + id + " eliminado exitosamente.");
            return true;
        } else {
            System.out.println("No se pudo eliminar: Developer con ID " + id + " no encontrado.");
            return false;
        }
    }

    @Override
    public DeveloperDTO updateDeveloper(DeveloperDTO developer) {
        if (!developerRepository.existsById(developer.getIdDeveloper())) {
            System.out.println("Error al actualizar developer: Developer con ID " + developer.getIdDeveloper() + " no encontrado.");
            return null;
        }
        System.out.println("Developer con ID " + developer.getIdDeveloper() + " actualizado exitosamente.");
        return developerRepository.save(developer);
    }
}