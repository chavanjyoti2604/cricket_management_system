package com.example.demo.service;

import com.example.demo.Repository.BatsmanRepository;
import com.example.demo.Repository.TeamRepository;
import com.example.demo.entity.Batsman;
import com.example.demo.entity.Team;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.BatsmanService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatsmanServiceTest {

    @InjectMocks
    private BatsmanService batsmanService;

    @Mock
    private BatsmanRepository batsmanRepository;

    @Mock
    private TeamRepository teamRepository;

    @Test
    void testSaveBatsman_WithValidData_ShouldSaveSuccessfully() {
        Batsman batsman = Batsman.builder()
                .batsmanName("Sachin")
                .battingPosition(3)
                .build();

        when(batsmanRepository.existsByBatsmanName("Sachin")).thenReturn(false);
        when(batsmanRepository.save(any(Batsman.class))).thenAnswer(i -> i.getArgument(0));

        Batsman result = batsmanService.saveBatsman(batsman);

        assertEquals("Sachin", result.getBatsmanName());
        assertEquals(3, result.getBattingPosition());
    }

    @Test
    void testSaveBatsman_WithDuplicateName_ShouldThrowException() {
        Batsman batsman = Batsman.builder()
                .batsmanName("Sachin")
                .battingPosition(1)
                .build();

        when(batsmanRepository.existsByBatsmanName("Sachin")).thenReturn(true);

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
                () -> batsmanService.saveBatsman(batsman));

        assertEquals("Batsman with name 'Sachin' already exists", exception.getMessage());
    }


    @Test
    void testSaveBatsman_WithInvalidBattingPosition_ShouldThrowValidationError() {
        Batsman batsman = Batsman.builder()
                .batsmanName("Dummy")
                .battingPosition(13)
                .build();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Batsman>> violations = validator.validate(batsman);

        assertFalse(violations.isEmpty());

        ConstraintViolation<Batsman> violation = violations.iterator().next();
        assertEquals("Batting position must be at most 11", violation.getMessage());
    }

    @Test
    void testSaveBatsman_WithTeam_ShouldAssignTeam() {
        Batsman batsman = new Batsman();
        batsman.setBatsmanName("asas");
        batsman.setBattingPosition(10);

        Team team = new Team();
        team.setTeamName("MI");
        batsman.setTeam(team);

        when(teamRepository.findByTeamNameIgnoreCase("MI")).thenReturn(Optional.of(team));
        when(batsmanRepository.existsByBatsmanName("asas")).thenReturn(false);
        when(batsmanRepository.save(any(Batsman.class))).thenReturn(batsman);

        Batsman savedBatsman = batsmanService.saveBatsman(batsman);

        assertEquals("asas", savedBatsman.getBatsmanName());
        assertEquals(10, savedBatsman.getBattingPosition());
        assertEquals("MI", savedBatsman.getTeam().getTeamName());
    }

    // ✅ Test: Update existing batsman
    @Test
    void updateBatsman_whenBatsmanExists_shouldUpdateSuccessfully() {
        Long id = 1L;
        Batsman existing = Batsman.builder()
                .batsmanName("Sachin")
                .battingPosition(1)
                .team(Team.builder().teamName("India").build())
                .build();

        Batsman updated = Batsman.builder()
                .batsmanName("Dravid")
                .battingPosition(2)
                .team(Team.builder().teamName("India").build())
                .build();

        Team team = Team.builder().teamName("India").build();

        when(batsmanRepository.findById(id)).thenReturn(Optional.of(existing));
        when(teamRepository.findAll()).thenReturn(List.of(team));
        when(batsmanRepository.save(any(Batsman.class))).thenAnswer(i -> i.getArgument(0));

        Batsman result = batsmanService.updateBatsman(id, updated);

        assertEquals("Dravid", result.getBatsmanName());
        assertEquals(2, result.getBattingPosition());
        verify(batsmanRepository).save(existing);
    }

    // ❌ Test: Update non-existing batsman
    @Test
    void updateBatsman_whenBatsmanNotFound_shouldThrowException() {
        Long id = 99L;
        when(batsmanRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                batsmanService.updateBatsman(id, new Batsman()));

        assertEquals("Batsman with id 99 not found", ex.getMessage());
    }

    // ✅ Test: Delete existing batsman
    @Test
    void deleteBatsman_whenExists_shouldDelete() {
        Long id = 10L;
        when(batsmanRepository.existsById(id)).thenReturn(true);

        batsmanService.deleteBatsman(id);

        verify(batsmanRepository).deleteById(id);
    }

    // ❌ Test: Delete non-existing batsman
    @Test
    void deleteBatsman_whenNotFound_shouldThrowException() {
        Long id = 404L;
        when(batsmanRepository.existsById(id)).thenReturn(false);

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                batsmanService.deleteBatsman(id));

        assertEquals("Batsman with id 404 not found", ex.getMessage());
    }

}