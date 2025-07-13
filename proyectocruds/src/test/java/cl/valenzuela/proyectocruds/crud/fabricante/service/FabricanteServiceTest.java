package cl.valenzuela.proyectocruds.crud.fabricante.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import cl.valenzuela.proyectocruds.crud.fabricante.dto.FabricanteDTO;
import cl.valenzuela.proyectocruds.crud.fabricante.repository.IFabricanteRepository;
import cl.valenzuela.proyectocruds.crud.fabricante.service.impl.FabricanteService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class FabricanteServiceTest {

    @Mock
    private IFabricanteRepository fabricanteRepository;

    @InjectMocks
    private FabricanteService fabricanteService;

    private FabricanteDTO fabricanteDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        fabricanteDTO = new FabricanteDTO();
        fabricanteDTO.setIdFabricante(1);
        fabricanteDTO.setNombre("Nintendo");
        fabricanteDTO.setFechaInicioOperaciones("1889-09-23");
        fabricanteDTO.setFechaFinOperaciones(null);
        fabricanteDTO.setConsolas(new ArrayList<>());
    }

    @Test
    void testCreateFabricante_Success() {
        when(fabricanteRepository.existsById(fabricanteDTO.getIdFabricante())).thenReturn(false);
        when(fabricanteRepository.save(any(FabricanteDTO.class))).thenReturn(fabricanteDTO);

        FabricanteDTO result = fabricanteService.createFabricante(fabricanteDTO);

        assertNotNull(result);
        assertEquals(fabricanteDTO.getIdFabricante(), result.getIdFabricante());
        assertEquals(fabricanteDTO.getNombre(), result.getNombre());
        verify(fabricanteRepository, times(1)).existsById(fabricanteDTO.getIdFabricante());
        verify(fabricanteRepository, times(1)).save(fabricanteDTO);
    }

    @Test
    void testCreateFabricante_AlreadyExists() {
        when(fabricanteRepository.existsById(fabricanteDTO.getIdFabricante())).thenReturn(true);

        FabricanteDTO result = fabricanteService.createFabricante(fabricanteDTO);

        assertNull(result);
        verify(fabricanteRepository, times(1)).existsById(fabricanteDTO.getIdFabricante());
        verify(fabricanteRepository, never()).save(any(FabricanteDTO.class));
    }

    @Test
    void testGetAllFabricantes_NotEmpty() {
        List<FabricanteDTO> fabricanteList = Arrays.asList(fabricanteDTO, new FabricanteDTO(2, "Microsoft", "1975-04-04", null, new ArrayList<>()));
        when(fabricanteRepository.findAll()).thenReturn(fabricanteList);

        List<FabricanteDTO> results = fabricanteService.getAllFabricantes();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        assertEquals(fabricanteDTO.getNombre(), results.get(0).getNombre());
        verify(fabricanteRepository, times(1)).findAll();
    }

    @Test
    void testGetAllFabricantes_Empty() {
        when(fabricanteRepository.findAll()).thenReturn(new ArrayList<>());

        List<FabricanteDTO> results = fabricanteService.getAllFabricantes();

        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(fabricanteRepository, times(1)).findAll();
    }

    @Test
    void testGetFabricanteById_Success() {
        when(fabricanteRepository.findById(fabricanteDTO.getIdFabricante())).thenReturn(Optional.of(fabricanteDTO));

        FabricanteDTO result = fabricanteService.getFabricanteById(fabricanteDTO.getIdFabricante());

        assertNotNull(result);
        assertEquals(fabricanteDTO.getIdFabricante(), result.getIdFabricante());
        assertEquals(fabricanteDTO.getNombre(), result.getNombre());
        verify(fabricanteRepository, times(1)).findById(fabricanteDTO.getIdFabricante());
    }

    @Test
    void testGetFabricanteById_NotFound() {
        when(fabricanteRepository.findById(fabricanteDTO.getIdFabricante())).thenReturn(Optional.empty());

        FabricanteDTO result = fabricanteService.getFabricanteById(fabricanteDTO.getIdFabricante());

        assertNull(result);
        verify(fabricanteRepository, times(1)).findById(fabricanteDTO.getIdFabricante());
    }

    @Test
    void testUpdateFabricante_Success() {
        when(fabricanteRepository.existsById(fabricanteDTO.getIdFabricante())).thenReturn(true);
        when(fabricanteRepository.save(any(FabricanteDTO.class))).thenReturn(fabricanteDTO);

        FabricanteDTO result = fabricanteService.updateFabricante(fabricanteDTO);

        assertNotNull(result);
        assertEquals(fabricanteDTO.getIdFabricante(), result.getIdFabricante());
        verify(fabricanteRepository, times(1)).existsById(fabricanteDTO.getIdFabricante());
        verify(fabricanteRepository, times(1)).save(fabricanteDTO);
    }

    @Test
    void testUpdateFabricante_NotFound() {
        when(fabricanteRepository.existsById(fabricanteDTO.getIdFabricante())).thenReturn(false);

        FabricanteDTO result = fabricanteService.updateFabricante(fabricanteDTO);

        assertNull(result);
        verify(fabricanteRepository, times(1)).existsById(fabricanteDTO.getIdFabricante());
        verify(fabricanteRepository, never()).save(any(FabricanteDTO.class));
    }

    @Test
    void testDeleteFabricanteById_Success() {
        when(fabricanteRepository.existsById(fabricanteDTO.getIdFabricante())).thenReturn(true);
        doNothing().when(fabricanteRepository).deleteById(fabricanteDTO.getIdFabricante());

        boolean result = fabricanteService.deleteFabricanteById(fabricanteDTO.getIdFabricante());

        assertTrue(result);
        verify(fabricanteRepository, times(1)).existsById(fabricanteDTO.getIdFabricante());
        verify(fabricanteRepository, times(1)).deleteById(fabricanteDTO.getIdFabricante());
    }

    @Test
    void testDeleteFabricanteById_NotFound() {
        when(fabricanteRepository.existsById(fabricanteDTO.getIdFabricante())).thenReturn(false);

        boolean result = fabricanteService.deleteFabricanteById(fabricanteDTO.getIdFabricante());

        assertFalse(result);
        verify(fabricanteRepository, times(1)).existsById(fabricanteDTO.getIdFabricante());
        verify(fabricanteRepository, never()).deleteById(any());
    }
}