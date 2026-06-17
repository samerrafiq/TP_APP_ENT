package com.example.demo;

import com.example.demo.dto.CompetitionDTO;
import com.example.demo.dto.ResultatDTO;
import com.example.demo.entity.Competition;
import com.example.demo.entity.Membre;
import com.example.demo.entity.ResultatCompetition;
import com.example.demo.repository.CompetitionRepository;
import com.example.demo.repository.ResultatCompetitionRepository;
import com.example.demo.repository.MembreRepository;
import com.example.demo.services.CompetitionServiceImpl;
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
public class CompetitionServiceTest {

    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private ResultatCompetitionRepository resultatRepository;

    @Mock
    private MembreRepository membreRepository;

    @InjectMocks
    private CompetitionServiceImpl competitionService;

    @Test
    public void testCreerCompetition_Success() {
        // Arrange
        CompetitionDTO dto = new CompetitionDTO();
        dto.setTitre("Concours National");
        dto.setNiveauCible(3);
        dto.setCreneauSemaine(LocalDateTime.now().plusDays(10));
        dto.setLieu("Salle Grande");
        dto.setDuree(120);
        dto.setEnseignantId(1L);

        Membre enseignant = new Membre();
        enseignant.setId(1L);
        enseignant.setNiveauExpertise(4);

        when(membreRepository.findById(1L)).thenReturn(Optional.of(enseignant));

        Competition competitionToSave = new Competition();
        competitionToSave.setId(1L);
        competitionToSave.setTitre("Concours National");
        competitionToSave.setNiveauCible(3);
        competitionToSave.setCreneauSemaine(LocalDateTime.now().plusDays(10));
        competitionToSave.setLieu("Salle Grande");
        competitionToSave.setDuree(120);
        competitionToSave.setEnseignant(enseignant);

        when(competitionRepository.save(any(Competition.class))).thenReturn(competitionToSave);

        // Act
        Competition result = competitionService.creerCompetition(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Concours National", result.getTitre());
        assertEquals(3, result.getNiveauCible());
        verify(competitionRepository, times(1)).save(any(Competition.class));
    }

    @Test
    public void testAjouterResultat_Success() {
        // Arrange
        ResultatDTO dto = new ResultatDTO();
        dto.setMembreId(1L);
        dto.setCompetitionId(1L);
        dto.setNote(8.5);

        Membre membre = new Membre();
        membre.setId(1L);

        Competition competition = new Competition();
        competition.setId(1L);

        when(membreRepository.findById(1L)).thenReturn(Optional.of(membre));
        when(competitionRepository.findById(1L)).thenReturn(Optional.of(competition));

        ResultatCompetition resultatToSave = new ResultatCompetition();
        resultatToSave.setId(1L);
        resultatToSave.setMembre(membre);
        resultatToSave.setCompetition(competition);
        resultatToSave.setNote(8.5);

        when(resultatRepository.save(any(ResultatCompetition.class))).thenReturn(resultatToSave);

        // Act
        ResultatCompetition result = competitionService.ajouterResultat(dto);

        // Assert
        assertNotNull(result);
        assertEquals(8.5, result.getNote());
        verify(resultatRepository, times(1)).save(any(ResultatCompetition.class));
    }

    @Test
    public void testAjouterResultat_NoteTropElevee() {
        // Arrange
        ResultatDTO dto = new ResultatDTO();
        dto.setMembreId(1L);
        dto.setCompetitionId(1L);
        dto.setNote(10.5); // Note > 10

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> competitionService.ajouterResultat(dto));
        assertEquals("La note doit être entre 0 et 10", exception.getMessage());
        verify(resultatRepository, never()).save(any(ResultatCompetition.class));
    }
}
