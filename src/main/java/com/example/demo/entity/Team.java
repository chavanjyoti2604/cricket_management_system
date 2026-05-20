package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "teams", uniqueConstraints = @UniqueConstraint(columnNames = "teamName"))
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @NotBlank(message = "Team name cannot be blank")
    private String teamName;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Batsman> players;

    // ===== Constructors =====
    public Team() {}

    public Team(Long teamId, String teamName, List<Batsman> players) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.players = players;
    }

    // ===== Getters and Setters =====
    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Batsman> getPlayers() {
        return players;
    }

    public void setPlayers(List<Batsman> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", players=" + (players != null ? players.size() : 0) +
                '}';
    }

    // ===== Manual Builder =====
    public static class Builder {
        private Long teamId;
        private String teamName;
        private List<Batsman> players;

        public Builder teamId(Long teamId) {
            this.teamId = teamId;
            return this;
        }

        public Builder teamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public Builder players(List<Batsman> players) {
            this.players = players;
            return this;
        }

        public Team build() {
            return new Team(teamId, teamName, players);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
