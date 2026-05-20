package com.example.demo.service;

import com.example.demo.Repository.TeamRepository;
import com.example.demo.entity.Batsman;
import com.example.demo.entity.Team;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTeam_shouldSaveSuccessfully_whenTeamNameIsUnique() {
        Team team = new Team();
        team.setTeamName("India");
        team.setPlayers(List.of(new Batsman()));

        when(teamRepository.existsByTeamName("India")).thenReturn(false);
        when(teamRepository.save(any(Team.class))).thenAnswer(i -> i.getArgument(0));

        Team saved = teamService.saveTeam(team);

        assertEquals("India", saved.getTeamName());
        assertEquals(saved, saved.getPlayers().get(0).getTeam()); // Bidirectional set
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    void saveTeam_shouldThrow_whenTeamNameExists() {
        Team team = new Team();
        team.setTeamName("India");

        when(teamRepository.existsByTeamName("India")).thenReturn(true);

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> teamService.saveTeam(team));

        assertEquals("Team with name 'India' already exists", exception.getMessage());
        verify(teamRepository, never()).save(any());
    }

    @Test
    void updateTeam_shouldUpdateSuccessfully_whenTeamExists() {
        Team existing = new Team();
        existing.setTeamId(1L);
        existing.setTeamName("India");

        Team updated = new Team();
        updated.setTeamName("Bharat");
        Batsman b1 = new Batsman();
        updated.setPlayers(List.of(b1));

        when(teamRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(teamRepository.save(any(Team.class))).thenAnswer(i -> i.getArgument(0));

        Team result = teamService.updateTeam(1L, updated);

        assertEquals("Bharat", result.getTeamName());
        assertEquals(result, b1.getTeam()); // check team set
        verify(teamRepository).save(existing);
    }

    @Test
    void updateTeam_shouldThrow_whenTeamNotFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> teamService.updateTeam(1L, new Team()));

        assertEquals("Team with id 1 not found", exception.getMessage());
    }

    @Test
    void deleteTeam_shouldDelete_whenTeamExists() {
        when(teamRepository.existsById(1L)).thenReturn(true);

        teamService.deleteTeam(1L);

        verify(teamRepository).deleteById(1L);
    }

    @Test
    void deleteTeam_shouldThrow_whenTeamNotFound() {
        when(teamRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> teamService.deleteTeam(1L));

        assertEquals("Team with id 1 not found", exception.getMessage());
        verify(teamRepository, never()).deleteById(any());
    }

    @Test
    void getAllTeams_shouldReturnList() {
        List<Team> teams = List.of(new Team(), new Team());
        when(teamRepository.findAll()).thenReturn(teams);

        List<Team> result = teamService.getAllTeams();

        assertEquals(2, result.size());
    }
}