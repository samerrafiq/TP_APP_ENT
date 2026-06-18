package com.example.demo;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.services.BadgeServiceImpl;
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
public class BadgeServiceTest {

    @Mock private BadgeRepository badgeRepository;
    @Mock private MembreRepository membreRepository;
    @Mock private CoursRepository coursRepository;
    @Mock private PresenceRepository presenceRepository;
    @InjectMocks private BadgeServiceImpl badgeService;

    @Test
    public void testCreerBadge_Success() {
        when(badgeRepository.save(any(Badge.class))).thenReturn(new Badge());
        Badge result = badgeService.creerBadge();
        assertNotNull(result);
        verify(badgeRepository, times(1)).save(any(Badge.class));
    }

    @Test
    public void testAssocierBadge_NonSecretaire() {
        Membre membre = new Membre();
        membre.setRole(Membre.Role.MEMBRE);
        when(membreRepository.findById(1L)).thenReturn(Optional.of(membre));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> badgeService.associerBadge(1L, 1L, 2L));
        assertTrue(ex.getMessage().contains("secrétaire"));
        verify(badgeRepository, never()).save(any());
    }

    @Test
    public void testBadger_Success() {
        Membre membre = new Membre();
        Badge badge = new Badge();
        badge.setMembre(membre);
        Cours cours = new Cours();
        when(badgeRepository.findByNumero("uuid-123")).thenReturn(Optional.of(badge));
        when(coursRepository.findById(1L)).thenReturn(Optional.of(cours));
        when(presenceRepository.findByMembreAndCours(membre, cours)).thenReturn(Optional.empty());
        when(presenceRepository.save(any())).thenReturn(new Presence());

        Presence result = badgeService.badger("uuid-123", 1L);
        assertNotNull(result);
        verify(presenceRepository, times(1)).save(any());
    }

    @Test
    public void testBadger_DoublonPresence() {
        Membre membre = new Membre();
        Badge badge = new Badge();
        badge.setMembre(membre);
        Cours cours = new Cours();
        when(badgeRepository.findByNumero("uuid-123")).thenReturn(Optional.of(badge));
        when(coursRepository.findById(1L)).thenReturn(Optional.of(cours));
        when(presenceRepository.findByMembreAndCours(membre, cours))
                .thenReturn(Optional.of(new Presence()));

        assertThrows(RuntimeException.class, () -> badgeService.badger("uuid-123", 1L));
    }
}