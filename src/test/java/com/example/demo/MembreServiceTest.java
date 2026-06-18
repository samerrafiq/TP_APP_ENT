package com.example.demo;

import com.example.demo.dto.MembreDTO;
import com.example.demo.entity.Membre;
import com.example.demo.repository.MembreRepository;
import com.example.demo.services.MembreServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MembreServiceTest {

    @Mock private MembreRepository membreRepository;
    @InjectMocks private MembreServiceImpl membreService;

    private MembreDTO createDTO() {
        MembreDTO dto = new MembreDTO();
        dto.setNomFamille("Dupont");
        dto.setPrenom("Jean");
        dto.setAdresseMail("jean@test.com");
        dto.setIdentifiant("jean123");
        dto.setMotDePasse("pass");
        dto.setVille("Paris");
        dto.setPays("France");
        dto.setNiveauExpertise(3);
        return dto;
    }

    @Test
    public void testCreerMembre_Success() {
        MembreDTO dto = createDTO();
        when(membreRepository.existsByIdentifiant("jean123")).thenReturn(false);
        when(membreRepository.existsByAdresseMail("jean@test.com")).thenReturn(false);
        Membre membre = new Membre();
        membre.setNomFamille("Dupont");
        when(membreRepository.save(any(Membre.class))).thenReturn(membre);

        Membre result = membreService.creerMembre(dto);
        assertNotNull(result);
        assertEquals("Dupont", result.getNomFamille());
        verify(membreRepository, times(1)).save(any(Membre.class));
    }

    @Test
    public void testCreerMembre_IdentifiantDuplique() {
        MembreDTO dto = createDTO();
        when(membreRepository.existsByIdentifiant("jean123")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> membreService.creerMembre(dto));
        verify(membreRepository, never()).save(any());
    }

    @Test
    public void testCreerMembre_EmailDuplique() {
        MembreDTO dto = createDTO();
        when(membreRepository.existsByIdentifiant("jean123")).thenReturn(false);
        when(membreRepository.existsByAdresseMail("jean@test.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> membreService.creerMembre(dto));
        verify(membreRepository, never()).save(any());
    }
}