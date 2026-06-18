package com.example.demo;

import com.example.demo.dto.CoursDTO;
import com.example.demo.entity.Cours;
import com.example.demo.entity.Membre;
import com.example.demo.repository.CoursRepository;
import com.example.demo.repository.MembreRepository;
import com.example.demo.services.CoursServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @Mock private CoursRepository coursRepository;
    @Mock private MembreRepository membreRepository;
    @InjectMocks private CoursServiceImpl coursService;

    private CoursDTO createDTO(int niveau, LocalDateTime date) {
        CoursDTO dto = new CoursDTO();
        dto.setTitre("Cours test");
        dto.setNiveauCible(niveau);
        dto.setCreneauSemaine(date);
        dto.setEnseignantId(1L);
        dto.setLieu("Salle A");
        dto.setDuree(60);
        return dto;
    }

    private Membre createEnseignant(int niveau) {
        Membre m = new Membre();
        m.setRole(Membre.Role.ENSEIGNANT);
        m.setNiveauExpertise(niveau);
        return m;
    }

    @Test
    public void testCreerCours_Success() {
        CoursDTO dto = createDTO(3, LocalDateTime.now().plusDays(10));
        Membre enseignant = createEnseignant(5);
        when(membreRepository.findById(1L)).thenReturn(Optional.of(enseignant));
        when(coursRepository.save(any(Cours.class))).thenReturn(new Cours());

        Cours result = coursService.creerCours(dto);
        assertNotNull(result);
        verify(coursRepository, times(1)).save(any(Cours.class));
    }

    @Test
    public void testCreerCours_DateTropTot() {
        CoursDTO dto = createDTO(3, LocalDateTime.now().plusDays(3));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> coursService.creerCours(dto));
        assertTrue(ex.getMessage().contains("7 jours"));
        verify(coursRepository, never()).save(any());
    }

    @Test
    public void testCreerCours_NiveauEnseignantInsuffisant() {
        CoursDTO dto = createDTO(5, LocalDateTime.now().plusDays(10));
        Membre enseignant = createEnseignant(2);
        when(membreRepository.findById(1L)).thenReturn(Optional.of(enseignant));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> coursService.creerCours(dto));
        assertTrue(ex.getMessage().contains("apte"));
        verify(coursRepository, never()).save(any());
    }
}