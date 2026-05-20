package com.example.demo.controller;

import com.example.demo.entity.Team;
import com.example.demo.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public Team add(@Valid @RequestBody Team team) {
        return teamService.saveTeam(team);
    }

    @GetMapping
    public List<Team> all() {
        return teamService.getAllTeams();
    }

    @PutMapping("/{id}")
    public Team update(@PathVariable Long id, @Valid @RequestBody Team team) {
        return teamService.updateTeam(id, team);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }
}