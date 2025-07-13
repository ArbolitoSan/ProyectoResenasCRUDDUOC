package cl.valenzuela.proyectocruds.crud.videojuego.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoDTO;
import cl.valenzuela.proyectocruds.crud.videojuego.dto.VideojuegoResponseDTO;
import cl.valenzuela.proyectocruds.crud.videojuego.repository.IVideojuegoRepository;
import cl.valenzuela.proyectocruds.crud.videojuego.service.impl.VideojuegoService;
import cl.valenzuela.proyectocruds.crud.consola.dto.ConsolaDTO;
import cl.valenzuela.proyectocruds.crud.consola.repository.IConsolaRepository;
import cl.valenzuela.proyectocruds.crud.publisher.dto.PublisherDTO;
import cl.valenzuela.proyectocruds.crud.publisher.repository.IPublisherRepository;
import cl.valenzuela.proyectocruds.crud.developer.dto.DeveloperDTO;
import cl.valenzuela.proyectocruds.crud.developer.repository.IDeveloperRepository;
import cl.valenzuela.proyectocruds.crud.developer.dto.DeveloperResponseDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class VideojuegoServiceTest {

    @Mock
    private IVideojuegoRepository videojuegoRepository;

    @Mock
    private IConsolaRepository consolaRepository;

    @Mock
    private IPublisherRepository publisherRepository;

    @Mock
    private IDeveloperRepository developerRepository;

    @InjectMocks
    private VideojuegoService videojuegoService;

    private VideojuegoDTO videojuegoDTO;
    private ConsolaDTO consolaDTO;
    private PublisherDTO publisherDTO;
    private DeveloperDTO developerDTO;
    private VideojuegoResponseDTO videojuegoResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        consolaDTO = new ConsolaDTO();
        consolaDTO.setIdConsola(1);
        consolaDTO.setNombre("PlayStation 5");

        publisherDTO = new PublisherDTO();
        publisherDTO.setIdPublisher(1);
        publisherDTO.setNombre("Sony Interactive Entertainment");

        developerDTO = new DeveloperDTO();
        developerDTO.setIdDeveloper(1);
        developerDTO.setNombre("Insomniac Games");
        developerDTO.setFechaFundacion("1994-02-28");
        developerDTO.setPaisOrigen("USA");

        videojuegoDTO = new VideojuegoDTO();
        videojuegoDTO.setIdVideojuego(1);
        videojuegoDTO.setTitulo("Marvel's Spider-Man 2");
        videojuegoDTO.setGenero("Acción-Aventura");
        videojuegoDTO.setFechaLanzamiento("2023-10-20");
        videojuegoDTO.setPrecio(79.99);
        videojuegoDTO.setConsola(consolaDTO);
        videojuegoDTO.setPublisher(publisherDTO);
        videojuegoDTO.setDevelopers(Arrays.asList(developerDTO));
        videojuegoDTO.setReviews(new ArrayList<>());

        videojuegoResponseDTO = new VideojuegoResponseDTO();
        videojuegoResponseDTO.setIdVideojuego(videojuegoDTO.getIdVideojuego());
        videojuegoResponseDTO.setTitulo(videojuegoDTO.getTitulo());
        videojuegoResponseDTO.setGenero(videojuegoDTO.getGenero());
        videojuegoResponseDTO.setFechaLanzamiento(videojuegoDTO.getFechaLanzamiento());
        videojuegoResponseDTO.setPrecio(videojuegoDTO.getPrecio());
        videojuegoResponseDTO.setIdConsola(consolaDTO.getIdConsola());
        videojuegoResponseDTO.setNombreConsola(consolaDTO.getNombre());
        videojuegoResponseDTO.setIdPublisher(publisherDTO.getIdPublisher());
        videojuegoResponseDTO.setNombrePublisher(publisherDTO.getNombre());

        DeveloperResponseDTO devResponse = new DeveloperResponseDTO();
        devResponse.setIdDeveloper(developerDTO.getIdDeveloper());
        devResponse.setNombre(developerDTO.getNombre());
        devResponse.setFechaFundacion(developerDTO.getFechaFundacion());
        devResponse.setPaisOrigen(developerDTO.getPaisOrigen());
        videojuegoResponseDTO.setDevelopers(Arrays.asList(devResponse));
        videojuegoResponseDTO.setReviews(new ArrayList<>());
    }

    @Test
    void testCreateVideojuego_Success() {
        when(videojuegoRepository.existsById(videojuegoDTO.getIdVideojuego())).thenReturn(false);
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(true);
        when(publisherRepository.existsById(publisherDTO.getIdPublisher())).thenReturn(true);
        when(developerRepository.findById(developerDTO.getIdDeveloper())).thenReturn(Optional.of(developerDTO));
        when(videojuegoRepository.save(any(VideojuegoDTO.class))).thenReturn(videojuegoDTO);

        VideojuegoDTO result = videojuegoService.createVideojuego(videojuegoDTO);

        assertNotNull(result);
        assertEquals(videojuegoDTO.getIdVideojuego(), result.getIdVideojuego());
        verify(videojuegoRepository, times(1)).existsById(videojuegoDTO.getIdVideojuego());
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(publisherRepository, times(1)).existsById(publisherDTO.getIdPublisher());
        verify(developerRepository, times(1)).findById(developerDTO.getIdDeveloper());
        verify(videojuegoRepository, times(1)).save(videojuegoDTO);
    }

    @Test
    void testCreateVideojuego_AlreadyExists() {
        when(videojuegoRepository.existsById(videojuegoDTO.getIdVideojuego())).thenReturn(true);

        VideojuegoDTO result = videojuegoService.createVideojuego(videojuegoDTO);

        assertNull(result);
        verify(videojuegoRepository, times(1)).existsById(videojuegoDTO.getIdVideojuego());
        verify(videojuegoRepository, never()).save(any(VideojuegoDTO.class));
    }

    @Test
    void testCreateVideojuego_ConsolaNotFound() {
        when(videojuegoRepository.existsById(videojuegoDTO.getIdVideojuego())).thenReturn(false);
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(false);

        VideojuegoDTO result = videojuegoService.createVideojuego(videojuegoDTO);

        assertNull(result);
        verify(videojuegoRepository, times(1)).existsById(videojuegoDTO.getIdVideojuego());
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(publisherRepository, never()).existsById(any());
        verify(videojuegoRepository, never()).save(any(VideojuegoDTO.class));
    }

    @Test
    void testCreateVideojuego_PublisherNotFound() {
        when(videojuegoRepository.existsById(videojuegoDTO.getIdVideojuego())).thenReturn(false);
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(true);
        when(publisherRepository.existsById(publisherDTO.getIdPublisher())).thenReturn(false);

        VideojuegoDTO result = videojuegoService.createVideojuego(videojuegoDTO);

        assertNull(result);
        verify(videojuegoRepository, times(1)).existsById(videojuegoDTO.getIdVideojuego());
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(publisherRepository, times(1)).existsById(publisherDTO.getIdPublisher());
        verify(developerRepository, never()).findById(any());
        verify(videojuegoRepository, never()).save(any(VideojuegoDTO.class));
    }

    @Test
    void testCreateVideojuego_DeveloperNotFound() {
        when(videojuegoRepository.existsById(videojuegoDTO.getIdVideojuego())).thenReturn(false);
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(true);
        when(publisherRepository.existsById(publisherDTO.getIdPublisher())).thenReturn(true);
        when(developerRepository.findById(developerDTO.getIdDeveloper())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            videojuegoService.createVideojuego(videojuegoDTO);
        });

        assertTrue(thrown.getMessage().contains("Developer con ID " + developerDTO.getIdDeveloper() + " no encontrado."));
        verify(videojuegoRepository, times(1)).existsById(videojuegoDTO.getIdVideojuego());
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(publisherRepository, times(1)).existsById(publisherDTO.getIdPublisher());
        verify(developerRepository, times(1)).findById(developerDTO.getIdDeveloper());
        verify(videojuegoRepository, never()).save(any(VideojuegoDTO.class));
    }

    @Test
    void testGetAllVideojuegosResponse_NotEmpty() {
        List<VideojuegoDTO> videojuegoList = Arrays.asList(videojuegoDTO, new VideojuegoDTO(2, "God of War Ragnarök", "Acción-Aventura", "2022-11-09", 69.99, consolaDTO, publisherDTO, Arrays.asList(developerDTO), new ArrayList<>()));
        when(videojuegoRepository.findAll()).thenReturn(videojuegoList);
        when(consolaRepository.findById(consolaDTO.getIdConsola())).thenReturn(Optional.of(consolaDTO));
        when(publisherRepository.findById(publisherDTO.getIdPublisher())).thenReturn(Optional.of(publisherDTO));
        when(developerRepository.findById(developerDTO.getIdDeveloper())).thenReturn(Optional.of(developerDTO));

        List<VideojuegoResponseDTO> results = videojuegoService.getAllVideojuegosResponse();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        assertEquals(videojuegoDTO.getTitulo(), results.get(0).getTitulo());
        verify(videojuegoRepository, times(1)).findAll();
    }

    @Test
    void testGetAllVideojuegosResponse_Empty() {
        when(videojuegoRepository.findAll()).thenReturn(new ArrayList<>());

        List<VideojuegoResponseDTO> results = videojuegoService.getAllVideojuegosResponse();

        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(videojuegoRepository, times(1)).findAll();
    }

    @Test
    void testGetVideojuegoResponseById_Success() {
        when(videojuegoRepository.findById(videojuegoDTO.getIdVideojuego())).thenReturn(Optional.of(videojuegoDTO));

        VideojuegoResponseDTO result = videojuegoService.getVideojuegoResponseById(videojuegoDTO.getIdVideojuego());

        assertNotNull(result);
        assertEquals(videojuegoDTO.getIdVideojuego(), result.getIdVideojuego());
        assertEquals(videojuegoDTO.getTitulo(), result.getTitulo());
        assertEquals(consolaDTO.getNombre(), result.getNombreConsola());
        assertEquals(publisherDTO.getNombre(), result.getNombrePublisher());
        assertFalse(result.getDevelopers().isEmpty());
        assertEquals(developerDTO.getNombre(), result.getDevelopers().get(0).getNombre());
        verify(videojuegoRepository, times(1)).findById(videojuegoDTO.getIdVideojuego());
    }

    @Test
    void testGetVideojuegoResponseById_NotFound() {
        when(videojuegoRepository.findById(videojuegoDTO.getIdVideojuego())).thenReturn(Optional.empty());

        VideojuegoResponseDTO result = videojuegoService.getVideojuegoResponseById(videojuegoDTO.getIdVideojuego());

        assertNull(result);
        verify(videojuegoRepository, times(1)).findById(videojuegoDTO.getIdVideojuego());
    }

    @Test
    void testGetVideojuegoById_Success() {
        when(videojuegoRepository.findById(videojuegoDTO.getIdVideojuego())).thenReturn(Optional.of(videojuegoDTO));

        VideojuegoDTO result = videojuegoService.getVideojuegoById(videojuegoDTO.getIdVideojuego());

        assertNotNull(result);
        assertEquals(videojuegoDTO.getIdVideojuego(), result.getIdVideojuego());
        verify(videojuegoRepository, times(1)).findById(videojuegoDTO.getIdVideojuego());
    }

    @Test
    void testGetVideojuegoById_NotFound() {
        when(videojuegoRepository.findById(videojuegoDTO.getIdVideojuego())).thenReturn(Optional.empty());

        VideojuegoDTO result = videojuegoService.getVideojuegoById(videojuegoDTO.getIdVideojuego());

        assertNull(result);
        verify(videojuegoRepository, times(1)).findById(videojuegoDTO.getIdVideojuego());
    }

    @Test
    void testUpdateVideojuego_Success() {
        when(videojuegoRepository.existsById(videojuegoDTO.getIdVideojuego())).thenReturn(true);
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(true);
        when(publisherRepository.existsById(publisherDTO.getIdPublisher())).thenReturn(true);
        when(developerRepository.findById(developerDTO.getIdDeveloper())).thenReturn(Optional.of(developerDTO));
        when(videojuegoRepository.save(any(VideojuegoDTO.class))).thenReturn(videojuegoDTO);

        VideojuegoDTO result = videojuegoService.updateVideojuego(videojuegoDTO);

        assertNotNull(result);
        assertEquals(videojuegoDTO.getIdVideojuego(), result.getIdVideojuego());
        verify(videojuegoRepository, times(1)).existsById(videojuegoDTO.getIdVideojuego());
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(publisherRepository, times(1)).existsById(publisherDTO.getIdPublisher());
        verify(developerRepository, times(1)).findById(developerDTO.getIdDeveloper());
        verify(videojuegoRepository, times(1)).save(videojuegoDTO);
    }

    @Test
    void testUpdateVideojuego_NotFound() {
        when(videojuegoRepository.existsById(videojuegoDTO.getIdVideojuego())).thenReturn(false);

        VideojuegoDTO result = videojuegoService.updateVideojuego(videojuegoDTO);

        assertNull(result);
        verify(videojuegoRepository, times(1)).existsById(videojuegoDTO.getIdVideojuego());
        verify(videojuegoRepository, never()).save(any(VideojuegoDTO.class));
    }

    @Test
    void testUpdateVideojuego_ConsolaNotFound() {
        when(videojuegoRepository.existsById(videojuegoDTO.getIdVideojuego())).thenReturn(true);
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(false);

        VideojuegoDTO result = videojuegoService.updateVideojuego(videojuegoDTO);

        assertNull(result);
        verify(videojuegoRepository, times(1)).existsById(videojuegoDTO.getIdVideojuego());
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(publisherRepository, never()).existsById(any());
        verify(videojuegoRepository, never()).save(any(VideojuegoDTO.class));
    }

    @Test
    void testUpdateVideojuego_PublisherNotFound() {
        when(videojuegoRepository.existsById(videojuegoDTO.getIdVideojuego())).thenReturn(true);
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(true);
        when(publisherRepository.existsById(publisherDTO.getIdPublisher())).thenReturn(false);

        VideojuegoDTO result = videojuegoService.updateVideojuego(videojuegoDTO);

        assertNull(result);
        verify(videojuegoRepository, times(1)).existsById(videojuegoDTO.getIdVideojuego());
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(publisherRepository, times(1)).existsById(publisherDTO.getIdPublisher());
        verify(developerRepository, never()).findById(any());
        verify(videojuegoRepository, never()).save(any(VideojuegoDTO.class));
    }

    @Test
    void testUpdateVideojuego_DeveloperNotFound() {
        when(videojuegoRepository.existsById(videojuegoDTO.getIdVideojuego())).thenReturn(true);
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(true);
        when(publisherRepository.existsById(publisherDTO.getIdPublisher())).thenReturn(true);
        when(developerRepository.findById(developerDTO.getIdDeveloper())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            videojuegoService.updateVideojuego(videojuegoDTO);
        });

        assertTrue(thrown.getMessage().contains("Developer con ID " + developerDTO.getIdDeveloper() + " no encontrado."));
        verify(videojuegoRepository, times(1)).existsById(videojuegoDTO.getIdVideojuego());
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(publisherRepository, times(1)).existsById(publisherDTO.getIdPublisher());
        verify(developerRepository, times(1)).findById(developerDTO.getIdDeveloper());
        verify(videojuegoRepository, never()).save(any(VideojuegoDTO.class));
    }

    @Test
    void testDeleteVideojuegoById_Success() {
        when(videojuegoRepository.existsById(videojuegoDTO.getIdVideojuego())).thenReturn(true);
        doNothing().when(videojuegoRepository).deleteById(videojuegoDTO.getIdVideojuego());

        boolean result = videojuegoService.deleteVideojuegoById(videojuegoDTO.getIdVideojuego());

        assertTrue(result);
        verify(videojuegoRepository, times(1)).existsById(videojuegoDTO.getIdVideojuego());
        verify(videojuegoRepository, times(1)).deleteById(videojuegoDTO.getIdVideojuego());
    }

    @Test
    void testDeleteVideojuegoById_NotFound() {
        when(videojuegoRepository.existsById(videojuegoDTO.getIdVideojuego())).thenReturn(false);

        boolean result = videojuegoService.deleteVideojuegoById(videojuegoDTO.getIdVideojuego());

        assertFalse(result);
        verify(videojuegoRepository, times(1)).existsById(videojuegoDTO.getIdVideojuego());
        verify(videojuegoRepository, never()).deleteById(any());
    }
}