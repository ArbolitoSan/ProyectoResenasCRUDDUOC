package cl.valenzuela.proyectocruds.crud.fabricante.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.valenzuela.proyectocruds.crud.fabricante.dto.FabricanteDTO;
import cl.valenzuela.proyectocruds.crud.fabricante.repository.IFabricanteRepository;
import cl.valenzuela.proyectocruds.crud.fabricante.service.IFabricanteService;

@Service
public class FabricanteService implements IFabricanteService {

    @Autowired
    private IFabricanteRepository fabricanteRepository;

    @Override
    public FabricanteDTO createFabricante(FabricanteDTO fabricante) {
        if (fabricanteRepository.existsById(fabricante.getIdFabricante())) {
            System.out.println("Error al crear fabricante: Ya existe un fabricante con el ID " + fabricante.getIdFabricante());
            return null;
        }
        return fabricanteRepository.save(fabricante);
    }

    @Override
    public List<FabricanteDTO> getAllFabricantes() {
        List<FabricanteDTO> fabricantes = (List<FabricanteDTO>) fabricanteRepository.findAll();
        if (fabricantes.isEmpty()) {
            System.out.println("No se encontraron fabricantes.");
        }
        return fabricantes;
    }

    @Override
    public FabricanteDTO getFabricanteById(Integer id) {
        Optional<FabricanteDTO> fabricanteEncontrado = fabricanteRepository.findById(id);
        if (fabricanteEncontrado.isEmpty()) {
            System.out.println("Fabricante con ID " + id + " no encontrado.");
        }
        return fabricanteEncontrado.orElse(null);
    }

    @Override
    public boolean deleteFabricanteById(Integer id) {
        if (fabricanteRepository.existsById(id)) {
            fabricanteRepository.deleteById(id);
            System.out.println("Fabricante con ID " + id + " eliminado exitosamente.");
            return true;
        } else {
            System.out.println("No se pudo eliminar: Fabricante con ID " + id + " no encontrado.");
            return false;
        }
    }

    @Override
    public FabricanteDTO updateFabricante(FabricanteDTO fabricante) {
        if (fabricanteRepository.existsById(fabricante.getIdFabricante())) {
            System.out.println("Fabricante con ID " + fabricante.getIdFabricante() + " actualizado exitosamente.");
            return fabricanteRepository.save(fabricante);
        } else {
            System.out.println("Error al actualizar fabricante: Fabricante con ID " + fabricante.getIdFabricante() + " no encontrado.");
            return null;
        }
    }
}