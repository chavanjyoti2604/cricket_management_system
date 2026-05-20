package com.example.demo.service;

import com.example.demo.entity.Batsman;
import com.example.demo.entity.Team;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.Repository.BatsmanRepository;
import com.example.demo.Repository.TeamRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatsmanService {
    @Autowired
    private BatsmanRepository batsmanRepository;

    @Autowired
    private TeamRepository teamRepository;

    public Batsman saveBatsman(Batsman batsman) {
        if (batsmanRepository.existsByBatsmanName(batsman.getBatsmanName())) {
            throw new DuplicateResourceException("Batsman with name '" + batsman.getBatsmanName() + "' already exists");
        }
        System.out.println("Received: " + batsman.getBattingPosition());

        if (batsman.getTeam() != null) {
            String teamName = batsman.getTeam().getTeamName();
            Team team = teamRepository.findByTeamNameIgnoreCase(teamName.trim())
                    .orElseThrow(() -> new ResourceNotFoundException("Team '" + teamName + "' not found"));
            batsman.setTeam(team);
        }
        return batsmanRepository.save(batsman);
    }

    public Batsman updateBatsman(Long id, Batsman updated) {
        Batsman existing = batsmanRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Batsman with id " + id + " not found"));

        existing.setBatsmanName(updated.getBatsmanName());
        existing.setBattingPosition(updated.getBattingPosition());

        if (updated.getTeam() != null) {
            String teamName = updated.getTeam().getTeamName();
            Team team = teamRepository.findAll().stream()
                    .filter(t -> t.getTeamName().equalsIgnoreCase(teamName))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Team '" + teamName + "' not found"));
            existing.setTeam(team);
        } else {
            existing.setTeam(null);
        }
        return batsmanRepository.save(existing);
    }

    public List<Batsman> getAllBatsmen() {
        return batsmanRepository.findAll();
    }

    public void deleteBatsman(Long id) {
        if (!batsmanRepository.existsById(id)) {
            throw new ResourceNotFoundException("Batsman with id " + id + " not found");
        }
        batsmanRepository.deleteById(id);
    }

    public Batsman patchBatsman(Long id, Batsman partialBatsman) {
        Batsman existing = batsmanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batsman with id " + id + " not found"));
    
        if (partialBatsman.getBatsmanName() != null) {
            existing.setBatsmanName(partialBatsman.getBatsmanName());
        }
    
        if (partialBatsman.getBattingPosition() != null) {
            existing.setBattingPosition(partialBatsman.getBattingPosition());
        }
    
        if (partialBatsman.getTeam() != null && partialBatsman.getTeam().getTeamName() != null) {
            String teamName = partialBatsman.getTeam().getTeamName();
            Team team = teamRepository.findByTeamNameIgnoreCase(teamName.trim())
                    .orElseThrow(() -> new ResourceNotFoundException("Team '" + teamName + "' not found"));
            existing.setTeam(team);
        }
    
        return batsmanRepository.save(existing);
    }

}
