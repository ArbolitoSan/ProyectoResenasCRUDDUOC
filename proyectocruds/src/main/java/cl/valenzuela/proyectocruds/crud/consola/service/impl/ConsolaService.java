package cl.valenzuela.proyectocruds.crud.consola.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.valenzuela.proyectocruds.crud.consola.dto.ConsolaDTO;
import cl.valenzuela.proyectocruds.crud.consola.dto.ConsolaResponseDTO;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoMinDTO;
import cl.valenzuela.proyectocruds.crud.consola.repository.IConsolaRepository;
import cl.valenzuela.proyectocruds.crud.fabricante.repository.IFabricanteRepository;
import cl.valenzuela.proyectocruds.crud.consola.service.IConsolaService;

@Service
public class ConsolaService implements IConsolaService {

    @Autowired
    private IConsolaRepository consolaRepository;

    @Autowired
    private IFabricanteRepository fabricanteRepository;

    @Override
    public ConsolaDTO createConsola(ConsolaDTO consola) {
        if (consolaRepository.existsById(consola.getIdConsola())) {
            System.out.println("Error al crear consola: Ya existe una consola con el ID " + consola.getIdConsola());
            return null;
        }
        if (consola.getFabricante() == null || !fabricanteRepository.existsById(consola.getFabricante().getIdFabricante())) {
            System.out.println("Error al crear consola: Fabricante no válido o no encontrado.");
            return null;
        }
        return consolaRepository.save(consola);
    }

    @Override
    public List<ConsolaResponseDTO> getAllConsolasResponse() {
        List<ConsolaDTO> consolas = (List<ConsolaDTO>) consolaRepository.findAll();
        if (consolas.isEmpty()) {
            System.out.println("No se encontraron consolas.");
        }
        return consolas.stream()
                       .map(this::mapToConsolaResponseDTO)
                       .collect(Collectors.toList());
    }

    @Override
    public ConsolaResponseDTO getConsolaResponseById(Integer id) {
        Optional<ConsolaDTO> consolaOptional = consolaRepository.findById(id);
        if (consolaOptional.isEmpty()) {
            System.out.println("Consola con ID " + id + " no encontrada.");
            return null;
        }
        return mapToConsolaResponseDTO(consolaOptional.get());
    }

    @Override
    public ConsolaDTO getConsolaById(Integer id) {
        return consolaRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteConsolaById(Integer id) {
        if (consolaRepository.existsById(id)) {
            consolaRepository.deleteById(id);
            System.out.println("Consola con ID " + id + " eliminada exitosamente.");
            return true;
        } else {
            System.out.println("No se pudo eliminar: Consola con ID " + id + " no encontrada.");
            return false;
        }
    }

    @Override
    public ConsolaDTO updateConsola(ConsolaDTO consola) {
        if (!consolaRepository.existsById(consola.getIdConsola())) {
            System.out.println("Error al actualizar consola: Consola con ID " + consola.getIdConsola() + " no encontrada.");
            return null;
        }
        if (consola.getFabricante() == null || !fabricanteRepository.existsById(consola.getFabricante().getIdFabricante())) {
            System.out.println("Error al actualizar consola: Fabricante no válido o no encontrado.");
            return null;
        }
        System.out.println("Consola con ID " + consola.getIdConsola() + " actualizada exitosamente.");
        return consolaRepository.save(consola);
    }

    private ConsolaResponseDTO mapToConsolaResponseDTO(ConsolaDTO consola) {
        ConsolaResponseDTO responseDTO = new ConsolaResponseDTO();
        responseDTO.setIdConsola(consola.getIdConsola());
        responseDTO.setNombre(consola.getNombre());
        responseDTO.setFechaSalida(consola.getFechaSalida());
        responseDTO.setGeneracionConsola(consola.getGeneracionConsola());

        if (consola.getFabricante() != null) {
            responseDTO.setIdFabricante(consola.getFabricante().getIdFabricante());
            responseDTO.setNombreFabricante(consola.getFabricante().getNombre());
        }

        if (consola.getVideojuegos() != null && !consola.getVideojuegos().isEmpty()) {
            responseDTO.setVideojuegos(
                consola.getVideojuegos().stream()
                       .map(v -> new VideojuegoMinDTO(v.getIdVideojuego(), v.getTitulo()))
                       .collect(Collectors.toList())
            );
        } else {
            responseDTO.setVideojuegos(List.of());
        }
        return responseDTO;
    }
}