package cl.valenzuela.proyectocruds.crud.publisher.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import cl.valenzuela.proyectocruds.crud.publisher.dto.PublisherDTO;
import cl.valenzuela.proyectocruds.crud.publisher.dto.PublisherResponseDTO;
import cl.valenzuela.proyectocruds.crud.publisher.repository.IPublisherRepository;
import cl.valenzuela.proyectocruds.crud.publisher.service.impl.PublisherService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class PublisherServiceTest {

    @Mock
    private IPublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private PublisherDTO publisherDTO;
    private PublisherResponseDTO publisherResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        publisherDTO = new PublisherDTO();
        publisherDTO.setIdPublisher(1);
        publisherDTO.setNombre("Electronic Arts");
        publisherDTO.setFechaInicioOperaciones("1982-05-27");
        publisherDTO.setFechaFinOperaciones(null);
        publisherDTO.setVideojuegos(new ArrayList<>());

        publisherResponseDTO = new PublisherResponseDTO();
        publisherResponseDTO.setIdPublisher(publisherDTO.getIdPublisher());
        publisherResponseDTO.setNombre(publisherDTO.getNombre());
        publisherResponseDTO.setFechaInicioOperaciones(publisherDTO.getFechaInicioOperaciones());
        publisherResponseDTO.setFechaFinOperaciones(publisherDTO.getFechaFinOperaciones());
        publisherResponseDTO.setVideojuegos(List.of());
    }

    @Test
    void testCreatePublisher_Success() {
        when(publisherRepository.existsById(publisherDTO.getIdPublisher())).thenReturn(false);
        when(publisherRepository.save(any(PublisherDTO.class))).thenReturn(publisherDTO);

        PublisherDTO result = publisherService.createPublisher(publisherDTO);

        assertNotNull(result);
        assertEquals(publisherDTO.getIdPublisher(), result.getIdPublisher());
        assertEquals(publisherDTO.getNombre(), result.getNombre());
        verify(publisherRepository, times(1)).existsById(publisherDTO.getIdPublisher());
        verify(publisherRepository, times(1)).save(publisherDTO);
    }

    @Test
    void testCreatePublisher_AlreadyExists() {
        when(publisherRepository.existsById(publisherDTO.getIdPublisher())).thenReturn(true);

        PublisherDTO result = publisherService.createPublisher(publisherDTO);

        assertNull(result);
        verify(publisherRepository, times(1)).existsById(publisherDTO.getIdPublisher());
        verify(publisherRepository, never()).save(any(PublisherDTO.class));
    }

    @Test
    void testGetAllPublishersResponse_NotEmpty() {
        List<PublisherDTO> publisherList = Arrays.asList(publisherDTO, new PublisherDTO(2, "Ubisoft", "1986-03-28", null, new ArrayList<>()));
        when(publisherRepository.findAll()).thenReturn(publisherList);

        List<PublisherResponseDTO> results = publisherService.getAllPublishersResponse();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        assertEquals(publisherDTO.getNombre(), results.get(0).getNombre());
        verify(publisherRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPublishersResponse_Empty() {
        when(publisherRepository.findAll()).thenReturn(new ArrayList<>());

        List<PublisherResponseDTO> results = publisherService.getAllPublishersResponse();

        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(publisherRepository, times(1)).findAll();
    }

    @Test
    void testGetPublisherResponseById_Success() {
        when(publisherRepository.findById(publisherDTO.getIdPublisher())).thenReturn(Optional.of(publisherDTO));

        PublisherResponseDTO result = publisherService.getPublisherResponseById(publisherDTO.getIdPublisher());

        assertNotNull(result);
        assertEquals(publisherDTO.getIdPublisher(), result.getIdPublisher());
        assertEquals(publisherDTO.getNombre(), result.getNombre());
        verify(publisherRepository, times(1)).findById(publisherDTO.getIdPublisher());
    }

    @Test
    void testGetPublisherResponseById_NotFound() {
        when(publisherRepository.findById(publisherDTO.getIdPublisher())).thenReturn(Optional.empty());

        PublisherResponseDTO result = publisherService.getPublisherResponseById(publisherDTO.getIdPublisher());

        assertNull(result);
        verify(publisherRepository, times(1)).findById(publisherDTO.getIdPublisher());
    }

    @Test
    void testGetPublisherById_Success() {
        when(publisherRepository.findById(publisherDTO.getIdPublisher())).thenReturn(Optional.of(publisherDTO));

        PublisherDTO result = publisherService.getPublisherById(publisherDTO.getIdPublisher());

        assertNotNull(result);
        assertEquals(publisherDTO.getIdPublisher(), result.getIdPublisher());
        verify(publisherRepository, times(1)).findById(publisherDTO.getIdPublisher());
    }

    @Test
    void testGetPublisherById_NotFound() {
        when(publisherRepository.findById(publisherDTO.getIdPublisher())).thenReturn(Optional.empty());

        PublisherDTO result = publisherService.getPublisherById(publisherDTO.getIdPublisher());

        assertNull(result);
        verify(publisherRepository, times(1)).findById(publisherDTO.getIdPublisher());
    }

    @Test
    void testUpdatePublisher_Success() {
        when(publisherRepository.existsById(publisherDTO.getIdPublisher())).thenReturn(true);
        when(publisherRepository.save(any(PublisherDTO.class))).thenReturn(publisherDTO);

        PublisherDTO result = publisherService.updatePublisher(publisherDTO);

        assertNotNull(result);
        assertEquals(publisherDTO.getIdPublisher(), result.getIdPublisher());
        verify(publisherRepository, times(1)).existsById(publisherDTO.getIdPublisher());
        verify(publisherRepository, times(1)).save(publisherDTO);
    }

    @Test
    void testUpdatePublisher_NotFound() {
        when(publisherRepository.existsById(publisherDTO.getIdPublisher())).thenReturn(false);

        PublisherDTO result = publisherService.updatePublisher(publisherDTO);

        assertNull(result);
        verify(publisherRepository, times(1)).existsById(publisherDTO.getIdPublisher());
        verify(publisherRepository, never()).save(any(PublisherDTO.class));
    }

    @Test
    void testDeletePublisherById_Success() {
        when(publisherRepository.existsById(publisherDTO.getIdPublisher())).thenReturn(true);
        doNothing().when(publisherRepository).deleteById(publisherDTO.getIdPublisher());

        boolean result = publisherService.deletePublisherById(publisherDTO.getIdPublisher());

        assertTrue(result);
        verify(publisherRepository, times(1)).existsById(publisherDTO.getIdPublisher());
        verify(publisherRepository, times(1)).deleteById(publisherDTO.getIdPublisher());
    }

    @Test
    void testDeletePublisherById_NotFound() {
        when(publisherRepository.existsById(publisherDTO.getIdPublisher())).thenReturn(false);

        boolean result = publisherService.deletePublisherById(publisherDTO.getIdPublisher());

        assertFalse(result);
        verify(publisherRepository, times(1)).existsById(publisherDTO.getIdPublisher());
        verify(publisherRepository, never()).deleteById(any());
    }
}