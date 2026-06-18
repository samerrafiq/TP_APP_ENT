package com.example.demo;

import com.example.demo.entity.Competition;
import com.example.demo.entity.Membre;
import com.example.demo.entity.ResultatCompetition;
import com.example.demo.repository.CompetitionRepository;
import com.example.demo.repository.MembreRepository;
import com.example.demo.repository.ResultatCompetitionRepository;
import com.example.demo.services.CompetitionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompetitionServiceTest {

    @Mock private CompetitionRepository competitionRepository;
    @Mock private MembreRepository membreRepository;
    @Mock private ResultatCompetitionRepository resultatRepository;
    @InjectMocks private CompetitionServiceImpl competitionService;

    @Test
    public void testAjouterResultat_Success() {
        Competition comp = new Competition();
        Membre eleve = new Membre();
        Membre enseignant = new Membre();
        enseignant.setRole(Membre.Role.ENSEIGNANT);

        when(membreRepository.findById(2L)).thenReturn(Optional.of(enseignant));
        when(competitionRepository.findById(1L)).thenReturn(Optional.of(comp));
        when(membreRepository.findById(3L)).thenReturn(Optional.of(eleve));
        when(resultatRepository.save(any())).thenReturn(new ResultatCompetition());

        ResultatCompetition r = competitionService.ajouterResultat(1L, 3L, 8.5, 2L);
        assertNotNull(r);
        verify(resultatRepository, times(1)).save(any());
    }

    @Test
    public void testAjouterResultat_NoteInvalide() {
        Membre enseignant = new Membre();
        enseignant.setRole(Membre.Role.ENSEIGNANT);
        when(membreRepository.findById(2L)).thenReturn(Optional.of(enseignant));

        assertThrows(RuntimeException.class,
                () -> competitionService.ajouterResultat(1L, 3L, 15.0, 2L));
        verify(resultatRepository, never()).save(any());
    }

    @Test
    public void testAjouterResultat_NonEnseignant() {
        Membre membre = new Membre();
        membre.setRole(Membre.Role.MEMBRE);
        when(membreRepository.findById(2L)).thenReturn(Optional.of(membre));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> competitionService.ajouterResultat(1L, 3L, 8.0, 2L));
        assertTrue(ex.getMessage().contains("enseignant"));
    }
}