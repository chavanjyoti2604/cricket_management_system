package com.example.demo.service;

import com.example.demo.entity.Batsman;
import com.example.demo.entity.Team;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public Team saveTeam(Team team) {
        if (teamRepository.existsByTeamName(team.getTeamName())) {
            throw new DuplicateResourceException("Team with name '" + team.getTeamName() + "' already exists");
        }
        if (team.getPlayers() != null) {
            for (Batsman player : team.getPlayers()) {
                player.setTeam(team);
            }
        }
        return teamRepository.save(team);
    }

    public Team updateTeam(Long id, Team updated) {
        Team existing = teamRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Team with id " + id + " not found"));

        existing.setTeamName(updated.getTeamName());
        existing.setPlayers(updated.getPlayers());
        if (updated.getPlayers() != null) {
            for (Batsman player : updated.getPlayers()) {
                player.setTeam(existing);
            }
        }
        return teamRepository.save(existing);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team with id " + id + " not found");
        }
        teamRepository.deleteById(id);
    }
}