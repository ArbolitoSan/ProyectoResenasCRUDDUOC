package cl.valenzuela.proyectocruds.crud.developer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import cl.valenzuela.proyectocruds.crud.developer.dto.DeveloperDTO;
import cl.valenzuela.proyectocruds.crud.developer.repository.IDeveloperRepository;
import cl.valenzuela.proyectocruds.crud.developer.service.impl.DeveloperService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class DeveloperServiceTest {

    @Mock
    private IDeveloperRepository developerRepository;

    @InjectMocks
    private DeveloperService developerService;

    private DeveloperDTO developerDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        developerDTO = new DeveloperDTO();
        developerDTO.setIdDeveloper(1);
        developerDTO.setNombre("CD Projekt Red");
        developerDTO.setFechaFundacion("1994-05-01");
        developerDTO.setPaisOrigen("Polonia");
        developerDTO.setVideojuegosDesarrollados(new ArrayList<>());
    }

    @Test
    void testCreateDeveloper_Success() {
        when(developerRepository.existsById(developerDTO.getIdDeveloper())).thenReturn(false);
        when(developerRepository.save(any(DeveloperDTO.class))).thenReturn(developerDTO);

        DeveloperDTO result = developerService.createDeveloper(developerDTO);

        assertNotNull(result);
        assertEquals(developerDTO.getIdDeveloper(), result.getIdDeveloper());
        assertEquals(developerDTO.getNombre(), result.getNombre());
        verify(developerRepository, times(1)).existsById(developerDTO.getIdDeveloper());
        verify(developerRepository, times(1)).save(developerDTO);
    }

    @Test
    void testCreateDeveloper_AlreadyExists() {
        when(developerRepository.existsById(developerDTO.getIdDeveloper())).thenReturn(true);

        DeveloperDTO result = developerService.createDeveloper(developerDTO);

        assertNull(result);
        verify(developerRepository, times(1)).existsById(developerDTO.getIdDeveloper());
        verify(developerRepository, never()).save(any(DeveloperDTO.class));
    }

    @Test
    void testGetAllDevelopers_NotEmpty() {
        List<DeveloperDTO> developerList = Arrays.asList(developerDTO, new DeveloperDTO(2, "Naughty Dog", "1984-09-27", "Estados Unidos", new ArrayList<>()));
        when(developerRepository.findAll()).thenReturn(developerList);

        List<DeveloperDTO> results = developerService.getAllDevelopers();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        assertEquals(developerDTO.getNombre(), results.get(0).getNombre());
        verify(developerRepository, times(1)).findAll();
    }

    @Test
    void testGetAllDevelopers_Empty() {
        when(developerRepository.findAll()).thenReturn(new ArrayList<>());

        List<DeveloperDTO> results = developerService.getAllDevelopers();

        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(developerRepository, times(1)).findAll();
    }

    @Test
    void testGetDeveloperById_Success() {
        when(developerRepository.findById(developerDTO.getIdDeveloper())).thenReturn(Optional.of(developerDTO));

        DeveloperDTO result = developerService.getDeveloperById(developerDTO.getIdDeveloper());

        assertNotNull(result);
        assertEquals(developerDTO.getIdDeveloper(), result.getIdDeveloper());
        assertEquals(developerDTO.getNombre(), result.getNombre());
        verify(developerRepository, times(1)).findById(developerDTO.getIdDeveloper());
    }

    @Test
    void testGetDeveloperById_NotFound() {
        when(developerRepository.findById(developerDTO.getIdDeveloper())).thenReturn(Optional.empty());

        DeveloperDTO result = developerService.getDeveloperById(developerDTO.getIdDeveloper());

        assertNull(result);
        verify(developerRepository, times(1)).findById(developerDTO.getIdDeveloper());
    }

    @Test
    void testUpdateDeveloper_Success() {
        when(developerRepository.existsById(developerDTO.getIdDeveloper())).thenReturn(true);
        when(developerRepository.save(any(DeveloperDTO.class))).thenReturn(developerDTO);

        DeveloperDTO result = developerService.updateDeveloper(developerDTO);

        assertNotNull(result);
        assertEquals(developerDTO.getIdDeveloper(), result.getIdDeveloper());
        verify(developerRepository, times(1)).existsById(developerDTO.getIdDeveloper());
        verify(developerRepository, times(1)).save(developerDTO);
    }

    @Test
    void testUpdateDeveloper_NotFound() {
        when(developerRepository.existsById(developerDTO.getIdDeveloper())).thenReturn(false);

        DeveloperDTO result = developerService.updateDeveloper(developerDTO);

        assertNull(result);
        verify(developerRepository, times(1)).existsById(developerDTO.getIdDeveloper());
        verify(developerRepository, never()).save(any(DeveloperDTO.class));
    }

    @Test
    void testDeleteDeveloperById_Success() {
        when(developerRepository.existsById(developerDTO.getIdDeveloper())).thenReturn(true);
        doNothing().when(developerRepository).deleteById(developerDTO.getIdDeveloper());

        boolean result = developerService.deleteDeveloperById(developerDTO.getIdDeveloper());

        assertTrue(result);
        verify(developerRepository, times(1)).existsById(developerDTO.getIdDeveloper());
        verify(developerRepository, times(1)).deleteById(developerDTO.getIdDeveloper());
    }

    @Test
    void testDeleteDeveloperById_NotFound() {
        when(developerRepository.existsById(developerDTO.getIdDeveloper())).thenReturn(false);

        boolean result = developerService.deleteDeveloperById(developerDTO.getIdDeveloper());

        assertFalse(result);
        verify(developerRepository, times(1)).existsById(developerDTO.getIdDeveloper());
        verify(developerRepository, never()).deleteById(any());
    }
}