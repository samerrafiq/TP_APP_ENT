package com.example.demo;

import com.example.demo.dto.CoursDTO;
import com.example.demo.entity.Cours;
import com.example.demo.entity.Membre;
import com.example.demo.repository.CoursRepository;
import com.example.demo.repository.MembreRepository;
import com.example.demo.services.CoursServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoursServiceTest {

    @Mock
    private CoursRepository coursRepository;

    @Mock
    private MembreRepository membreRepository;

    @InjectMocks
    private CoursServiceImpl coursService;

    @Test
    public void testCreerCours_Success() {
        // Arrange
        CoursDTO dto = new CoursDTO();
        dto.setTitre("Danse Contemporaine");
        dto.setNiveauCible(2);
        dto.setCreneauSemaine(LocalDateTime.now().plusDays(10));
        dto.setLieu("Studio A");
        dto.setDuree(60);
        dto.setEnseignantId(1L);

        Membre enseignant = new Membre();
        enseignant.setId(1L);
        enseignant.setNiveauExpertise(3);

        when(membreRepository.findById(1L)).thenReturn(Optional.of(enseignant));

        Cours coursToSave = new Cours();
        coursToSave.setId(1L);
        coursToSave.setTitre("Danse Contemporaine");
        coursToSave.setNiveauCible(2);
        coursToSave.setCreneauSemaine(LocalDateTime.now().plusDays(10));
        coursToSave.setLieu("Studio A");
        coursToSave.setDuree(60);
        coursToSave.setEnseignant(enseignant);

        when(coursRepository.save(any(Cours.class))).thenReturn(coursToSave);

        // Act
        Cours result = coursService.creerCours(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Danse Contemporaine", result.getTitre());
        assertEquals(2, result.getNiveauCible());
        verify(coursRepository, times(1)).save(any(Cours.class));
    }

    @Test
    public void testCreerCours_DateTropTot() {
        // Arrange
        CoursDTO dto = new CoursDTO();
        dto.setTitre("Danse Contemporaine");
        dto.setNiveauCible(2);
        dto.setCreneauSemaine(LocalDateTime.now().plusDays(3)); // Moins de 7 jours
        dto.setLieu("Studio A");
        dto.setDuree(60);
        dto.setEnseignantId(1L);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> coursService.creerCours(dto));
        assertEquals("Le cours doit être planifié au moins 7 jours à l'avance", exception.getMessage());
        verify(coursRepository, never()).save(any(Cours.class));
    }

    @Test
    public void testCreerCours_NiveauEnseignantInsuffisant() {
        // Arrange
        CoursDTO dto = new CoursDTO();
        dto.setTitre("Danse Contemporaine");
        dto.setNiveauCible(4);
        dto.setCreneauSemaine(LocalDateTime.now().plusDays(10));
        dto.setLieu("Studio A");
        dto.setDuree(60);
        dto.setEnseignantId(1L);

        Membre enseignant = new Membre();
        enseignant.setId(1L);
        enseignant.setNiveauExpertise(2); // Niveau trop bas

        when(membreRepository.findById(1L)).thenReturn(Optional.of(enseignant));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> coursService.creerCours(dto));
        assertEquals("L'enseignant n'est pas apte à ce niveau de cours", exception.getMessage());
        verify(coursRepository, never()).save(any(Cours.class));
    }

    @Test
    public void testGetCoursParNiveau() {
        // Arrange
        Cours cours = new Cours();
        cours.setId(1L);
        cours.setTitre("Danse Contemporaine");
        cours.setNiveauCible(2);

        when(coursRepository.findByNiveauCible(2)).thenReturn(java.util.List.of(cours));

        // Act
        java.util.List<Cours> result = coursService.getCoursParNiveau(2);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Danse Contemporaine", result.get(0).getTitre());
    }
}
