package cl.valenzuela.proyectocruds.crud.consola.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import cl.valenzuela.proyectocruds.crud.consola.dto.ConsolaDTO;
import cl.valenzuela.proyectocruds.crud.consola.dto.ConsolaResponseDTO;
import cl.valenzuela.proyectocruds.crud.consola.repository.IConsolaRepository;
import cl.valenzuela.proyectocruds.crud.fabricante.dto.FabricanteDTO;
import cl.valenzuela.proyectocruds.crud.fabricante.repository.IFabricanteRepository;
import cl.valenzuela.proyectocruds.crud.consola.service.impl.ConsolaService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class ConsolaServiceTest {

    @Mock
    private IConsolaRepository consolaRepository;

    @Mock
    private IFabricanteRepository fabricanteRepository;

    @InjectMocks
    private ConsolaService consolaService;

    private ConsolaDTO consolaDTO;
    private FabricanteDTO fabricanteDTO;
    private ConsolaResponseDTO consolaResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        fabricanteDTO = new FabricanteDTO();
        fabricanteDTO.setIdFabricante(1);
        fabricanteDTO.setNombre("Sony");

        consolaDTO = new ConsolaDTO();
        consolaDTO.setIdConsola(1);
        consolaDTO.setNombre("PlayStation 5");
        consolaDTO.setFechaSalida("2020-11-12");
        consolaDTO.setGeneracionConsola("Novena");
        consolaDTO.setFabricante(fabricanteDTO);
        consolaDTO.setVideojuegos(new ArrayList<>());

        consolaResponseDTO = new ConsolaResponseDTO();
        consolaResponseDTO.setIdConsola(consolaDTO.getIdConsola());
        consolaResponseDTO.setNombre(consolaDTO.getNombre());
        consolaResponseDTO.setFechaSalida(consolaDTO.getFechaSalida());
        consolaResponseDTO.setGeneracionConsola(consolaDTO.getGeneracionConsola());
        consolaResponseDTO.setIdFabricante(fabricanteDTO.getIdFabricante());
        consolaResponseDTO.setNombreFabricante(fabricanteDTO.getNombre());
        consolaResponseDTO.setVideojuegos(List.of());
    }

    @Test
    void testCreateConsola_Success() {
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(false);
        when(fabricanteRepository.existsById(fabricanteDTO.getIdFabricante())).thenReturn(true);
        when(consolaRepository.save(any(ConsolaDTO.class))).thenReturn(consolaDTO);

        ConsolaDTO result = consolaService.createConsola(consolaDTO);

        assertNotNull(result);
        assertEquals(consolaDTO.getIdConsola(), result.getIdConsola());
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(fabricanteRepository, times(1)).existsById(fabricanteDTO.getIdFabricante());
        verify(consolaRepository, times(1)).save(consolaDTO);
    }

    @Test
    void testCreateConsola_AlreadyExists() {
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(true);

        ConsolaDTO result = consolaService.createConsola(consolaDTO);

        assertNull(result);
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(consolaRepository, never()).save(any(ConsolaDTO.class));
    }

    @Test
    void testCreateConsola_FabricanteNotFound() {
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(false);
        when(fabricanteRepository.existsById(fabricanteDTO.getIdFabricante())).thenReturn(false);

        ConsolaDTO result = consolaService.createConsola(consolaDTO);

        assertNull(result);
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(fabricanteRepository, times(1)).existsById(fabricanteDTO.getIdFabricante());
        verify(consolaRepository, never()).save(any(ConsolaDTO.class));
    }

    @Test
    void testGetAllConsolasResponse_NotEmpty() {
        List<ConsolaDTO> consolaList = Arrays.asList(consolaDTO, new ConsolaDTO(2, "Xbox Series X", "2020-11-10", "Novena", fabricanteDTO, new ArrayList<>()));
        when(consolaRepository.findAll()).thenReturn(consolaList);
        when(fabricanteRepository.findById(fabricanteDTO.getIdFabricante())).thenReturn(Optional.of(fabricanteDTO));

        List<ConsolaResponseDTO> results = consolaService.getAllConsolasResponse();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        assertEquals(consolaDTO.getNombre(), results.get(0).getNombre());
        verify(consolaRepository, times(1)).findAll();
    }

    @Test
    void testGetAllConsolasResponse_Empty() {
        when(consolaRepository.findAll()).thenReturn(new ArrayList<>());

        List<ConsolaResponseDTO> results = consolaService.getAllConsolasResponse();

        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(consolaRepository, times(1)).findAll();
    }

    @Test
    void testGetConsolaResponseById_Success() {
        when(consolaRepository.findById(consolaDTO.getIdConsola())).thenReturn(Optional.of(consolaDTO));

        ConsolaResponseDTO result = consolaService.getConsolaResponseById(consolaDTO.getIdConsola());

        assertNotNull(result);
        assertEquals(consolaDTO.getIdConsola(), result.getIdConsola());
        assertEquals(consolaDTO.getNombre(), result.getNombre());
        verify(consolaRepository, times(1)).findById(consolaDTO.getIdConsola());
    }

    @Test
    void testGetConsolaResponseById_NotFound() {
        when(consolaRepository.findById(consolaDTO.getIdConsola())).thenReturn(Optional.empty());

        ConsolaResponseDTO result = consolaService.getConsolaResponseById(consolaDTO.getIdConsola());

        assertNull(result);
        verify(consolaRepository, times(1)).findById(consolaDTO.getIdConsola());
    }

    @Test
    void testGetConsolaById_Success() {
        when(consolaRepository.findById(consolaDTO.getIdConsola())).thenReturn(Optional.of(consolaDTO));

        ConsolaDTO result = consolaService.getConsolaById(consolaDTO.getIdConsola());

        assertNotNull(result);
        assertEquals(consolaDTO.getIdConsola(), result.getIdConsola());
        verify(consolaRepository, times(1)).findById(consolaDTO.getIdConsola());
    }

    @Test
    void testGetConsolaById_NotFound() {
        when(consolaRepository.findById(consolaDTO.getIdConsola())).thenReturn(Optional.empty());

        ConsolaDTO result = consolaService.getConsolaById(consolaDTO.getIdConsola());

        assertNull(result);
        verify(consolaRepository, times(1)).findById(consolaDTO.getIdConsola());
    }

    @Test
    void testUpdateConsola_Success() {
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(true);
        when(fabricanteRepository.existsById(fabricanteDTO.getIdFabricante())).thenReturn(true);
        when(consolaRepository.save(any(ConsolaDTO.class))).thenReturn(consolaDTO);

        ConsolaDTO result = consolaService.updateConsola(consolaDTO);

        assertNotNull(result);
        assertEquals(consolaDTO.getIdConsola(), result.getIdConsola());
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(fabricanteRepository, times(1)).existsById(fabricanteDTO.getIdFabricante());
        verify(consolaRepository, times(1)).save(consolaDTO);
    }

    @Test
    void testUpdateConsola_NotFound() {
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(false);

        ConsolaDTO result = consolaService.updateConsola(consolaDTO);

        assertNull(result);
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(consolaRepository, never()).save(any(ConsolaDTO.class));
    }

    @Test
    void testUpdateConsola_FabricanteNotFound() {
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(true);
        when(fabricanteRepository.existsById(fabricanteDTO.getIdFabricante())).thenReturn(false);

        ConsolaDTO result = consolaService.updateConsola(consolaDTO);

        assertNull(result);
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(fabricanteRepository, times(1)).existsById(fabricanteDTO.getIdFabricante());
        verify(consolaRepository, never()).save(any(ConsolaDTO.class));
    }

    @Test
    void testDeleteConsolaById_Success() {
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(true);
        doNothing().when(consolaRepository).deleteById(consolaDTO.getIdConsola());

        boolean result = consolaService.deleteConsolaById(consolaDTO.getIdConsola());

        assertTrue(result);
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(consolaRepository, times(1)).deleteById(consolaDTO.getIdConsola());
    }

    @Test
    void testDeleteConsolaById_NotFound() {
        when(consolaRepository.existsById(consolaDTO.getIdConsola())).thenReturn(false);

        boolean result = consolaService.deleteConsolaById(consolaDTO.getIdConsola());

        assertFalse(result);
        verify(consolaRepository, times(1)).existsById(consolaDTO.getIdConsola());
        verify(consolaRepository, never()).deleteById(any());
    }
}