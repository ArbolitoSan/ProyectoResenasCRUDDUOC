package cl.valenzuela.proyectocruds.crud.videojuego.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoDTO;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoResponseDTO;
import cl.valenzuela.proyectocruds.crud.videojuego.repository.IVideojuegoRepository;
import cl.valenzuela.proyectocruds.crud.videojuego.service.IVideojuegoService;
import cl.valenzuela.proyectocruds.crud.consola.repository.IConsolaRepository;
import cl.valenzuela.proyectocruds.crud.publisher.repository.IPublisherRepository;
import cl.valenzuela.proyectocruds.crud.developer.dto.DeveloperDTO;
import cl.valenzuela.proyectocruds.crud.developer.repository.IDeveloperRepository;
import cl.valenzuela.proyectocruds.crud.developer.dto.DeveloperResponseDTO;


@Service
public class VideojuegoService implements IVideojuegoService {

    @Autowired
    private IVideojuegoRepository videojuegoRepository;

    @Autowired
    private IConsolaRepository consolaRepository;

    @Autowired
    private IPublisherRepository publisherRepository;

    @Autowired
    private IDeveloperRepository developerRepository;


    @Override
    @Transactional
    public VideojuegoDTO createVideojuego(VideojuegoDTO videojuego) {
        if (videojuegoRepository.existsById(videojuego.getIdVideojuego())) {
            System.out.println("Error al crear videojuego: Ya existe un videojuego con el ID " + videojuego.getIdVideojuego());
            return null;
        }

        if (videojuego.getConsola() == null || !consolaRepository.existsById(videojuego.getConsola().getIdConsola())) {
            System.out.println("Error al crear videojuego: Consola no válida o no encontrada.");
            return null;
        }
        if (videojuego.getPublisher() == null || !publisherRepository.existsById(videojuego.getPublisher().getIdPublisher())) {
            System.out.println("Error al crear videojuego: Publisher no válido o no encontrado.");
            return null;
        }

        if (videojuego.getDevelopers() != null && !videojuego.getDevelopers().isEmpty()) {
            List<DeveloperDTO> managedDevelopers = videojuego.getDevelopers().stream()
                .map(dev -> developerRepository.findById(dev.getIdDeveloper())
                                .orElseThrow(() -> new RuntimeException("Developer con ID " + dev.getIdDeveloper() + " no encontrado.")))
                .collect(Collectors.toList());
            videojuego.setDevelopers(managedDevelopers);
        }

        return videojuegoRepository.save(videojuego);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideojuegoDTO> getAllVideojuegos() {
        return (List<VideojuegoDTO>) videojuegoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideojuegoResponseDTO> getAllVideojuegosResponse() {
        List<VideojuegoDTO> videojuegos = (List<VideojuegoDTO>) videojuegoRepository.findAll();
        if (videojuegos.isEmpty()) {
            System.out.println("No se encontraron videojuegos.");
        }
        return videojuegos.stream().map(this::mapToVideojuegoResponseDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VideojuegoDTO getVideojuegoById(Integer id) {
        Optional<VideojuegoDTO> videojuegoEncontrado = videojuegoRepository.findById(id);
        if (videojuegoEncontrado.isEmpty()) {
            System.out.println("Videojuego con ID " + id + " no encontrado.");
        }
        return videojuegoEncontrado.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public VideojuegoResponseDTO getVideojuegoResponseById(Integer id) {
        Optional<VideojuegoDTO> videojuegoOptional = videojuegoRepository.findById(id);
        if (videojuegoOptional.isEmpty()) {
            System.out.println("Videojuego con ID " + id + " no encontrado.");
            return null;
        }
        return mapToVideojuegoResponseDTO(videojuegoOptional.get());
    }

    @Override
    @Transactional
    public boolean deleteVideojuegoById(Integer id) {
        if (videojuegoRepository.existsById(id)) {
            videojuegoRepository.deleteById(id);
            System.out.println("Videojuego con ID " + id + " eliminado exitosamente.");
            return true;
        } else {
            System.out.println("No se pudo eliminar: Videojuego con ID " + id + " no encontrado.");
            return false;
        }
    }

    @Override
    @Transactional
    public VideojuegoDTO updateVideojuego(VideojuegoDTO videojuego) {
        if (!videojuegoRepository.existsById(videojuego.getIdVideojuego())) {
            System.out.println("Error al actualizar videojuego: Videojuego con ID " + videojuego.getIdVideojuego() + " no encontrado.");
            return null;
        }
        if (videojuego.getConsola() == null || !consolaRepository.existsById(videojuego.getConsola().getIdConsola())) {
            System.out.println("Error al actualizar videojuego: Consola no válida o no encontrada.");
            return null;
        }
        if (videojuego.getPublisher() == null || !publisherRepository.existsById(videojuego.getPublisher().getIdPublisher())) {
            System.out.println("Error al actualizar videojuego: Publisher no válido o no encontrado.");
            return null;
        }

        if (videojuego.getDevelopers() != null && !videojuego.getDevelopers().isEmpty()) {
            List<DeveloperDTO> managedDevelopers = videojuego.getDevelopers().stream()
                .map(dev -> developerRepository.findById(dev.getIdDeveloper())
                                .orElseThrow(() -> new RuntimeException("Developer con ID " + dev.getIdDeveloper() + " no encontrado.")))
                .collect(Collectors.toList());
            videojuego.setDevelopers(managedDevelopers);
        } else {
             videojuego.setDevelopers(List.of());
        }

        System.out.println("Videojuego con ID " + videojuego.getIdVideojuego() + " actualizado exitosamente.");
        return videojuegoRepository.save(videojuego);
    }

    private VideojuegoResponseDTO mapToVideojuegoResponseDTO(VideojuegoDTO videojuego) {
        VideojuegoResponseDTO responseDTO = new VideojuegoResponseDTO();
        responseDTO.setIdVideojuego(videojuego.getIdVideojuego());
        responseDTO.setTitulo(videojuego.getTitulo());
        responseDTO.setGenero(videojuego.getGenero());
        responseDTO.setFechaLanzamiento(videojuego.getFechaLanzamiento());
        responseDTO.setPrecio(videojuego.getPrecio());

        if (videojuego.getConsola() != null) {
            responseDTO.setIdConsola(videojuego.getConsola().getIdConsola());
            responseDTO.setNombreConsola(videojuego.getConsola().getNombre());
        }
        if (videojuego.getPublisher() != null) {
            responseDTO.setIdPublisher(videojuego.getPublisher().getIdPublisher());
            responseDTO.setNombrePublisher(videojuego.getPublisher().getNombre());
        }

        if (videojuego.getDevelopers() != null && !videojuego.getDevelopers().isEmpty()) {
            responseDTO.setDevelopers(videojuego.getDevelopers().stream()
                .map(dev -> {
                    DeveloperResponseDTO devResponse = new DeveloperResponseDTO();
                    devResponse.setIdDeveloper(dev.getIdDeveloper());
                    devResponse.setNombre(dev.getNombre());
                    devResponse.setFechaFundacion(dev.getFechaFundacion());
                    devResponse.setPaisOrigen(dev.getPaisOrigen());
                    return devResponse;
                })
                .collect(Collectors.toList())
            );
        } else {
            responseDTO.setDevelopers(List.of());
        }
        return responseDTO;
    }
}