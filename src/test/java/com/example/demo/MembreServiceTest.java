package com.example.demo;

import com.example.demo.dto.MembreDTO;
import com.example.demo.entity.Membre;
import com.example.demo.repository.MembreRepository;
import com.example.demo.services.MembreServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MembreServiceTest {

    @Mock
    private MembreRepository membreRepository;

    @InjectMocks
    private MembreServiceImpl membreService;

    @Test
    public void testCreerMembre_Success() {
        // Arrange
        MembreDTO dto = new MembreDTO();
        dto.setNomFamille("Dupont");
        dto.setPrenom("Jean");
        dto.setAdresseMail("jean@example.com");
        dto.setIdentifiant("jean123");
        dto.setMotDePasse("password");
        dto.setVille("Paris");
        dto.setPays("France");
        dto.setNiveauExpertise(3);

        when(membreRepository.existsByIdentifiant("jean123")).thenReturn(false);
        when(membreRepository.existsByAdresseMail("jean@example.com")).thenReturn(false);

        Membre membreToSave = new Membre();
        membreToSave.setId(1L);
        membreToSave.setNomFamille("Dupont");
        membreToSave.setPrenom("Jean");
        membreToSave.setAdresseMail("jean@example.com");
        membreToSave.setIdentifiant("jean123");
        membreToSave.setMotDePasse("password");
        membreToSave.setVille("Paris");
        membreToSave.setPays("France");
        membreToSave.setNiveauExpertise(3);

        when(membreRepository.save(any(Membre.class))).thenReturn(membreToSave);

        // Act
        Membre result = membreService.creerMembre(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Dupont", result.getNomFamille());
        assertEquals("jean123", result.getIdentifiant());
        verify(membreRepository, times(1)).save(any(Membre.class));
    }

    @Test
    public void testCreerMembre_IdentifiantDuplique() {
        // Arrange
        MembreDTO dto = new MembreDTO();
        dto.setNomFamille("Dupont");
        dto.setPrenom("Jean");
        dto.setAdresseMail("jean@example.com");
        dto.setIdentifiant("jean123");
        dto.setMotDePasse("password");
        dto.setVille("Paris");
        dto.setPays("France");
        dto.setNiveauExpertise(3);

        when(membreRepository.existsByIdentifiant("jean123")).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> membreService.creerMembre(dto));
        assertEquals("Identifiant déjà utilisé", exception.getMessage());
        verify(membreRepository, never()).save(any(Membre.class));
    }

    @Test
    public void testCreerMembre_EmailDuplique() {
        // Arrange
        MembreDTO dto = new MembreDTO();
        dto.setNomFamille("Dupont");
        dto.setPrenom("Jean");
        dto.setAdresseMail("jean@example.com");
        dto.setIdentifiant("jean123");
        dto.setMotDePasse("password");
        dto.setVille("Paris");
        dto.setPays("France");
        dto.setNiveauExpertise(3);

        when(membreRepository.existsByIdentifiant("jean123")).thenReturn(false);
        when(membreRepository.existsByAdresseMail("jean@example.com")).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> membreService.creerMembre(dto));
        assertEquals("Email déjà utilisé", exception.getMessage());
        verify(membreRepository, never()).save(any(Membre.class));
    }

    @Test
    public void testGetMembre_Introuvable() {
        // Arrange
        when(membreRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> membreService.getMembre(999L));
        assertEquals("Membre introuvable", exception.getMessage());
    }
}
