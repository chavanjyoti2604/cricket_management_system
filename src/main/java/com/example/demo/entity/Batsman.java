package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "batsmen", uniqueConstraints = @UniqueConstraint(columnNames = "batsmanName"))
public class Batsman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batsmanId;

    @NotBlank(message = "Batsman name cannot be blank")
    private String batsmanName;

    @Min(value = 1, message = "Batting position must be at least 1")
    @Max(value = 11, message = "Batting position must be at most 11")
    private Integer battingPosition;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;

    // Constructors
    public Batsman() {}

    public Batsman(Long batsmanId, String batsmanName, Integer battingPosition, Team team) {
        this.batsmanId = batsmanId;
        this.batsmanName = batsmanName;
        this.battingPosition = battingPosition;
        this.team = team;
    }

    // Getters and Setters
    public Long getBatsmanId() {
        return batsmanId;
    }

    public void setBatsmanId(Long batsmanId) {
        this.batsmanId = batsmanId;
    }

    public String getBatsmanName() {
        return batsmanName;
    }

    public void setBatsmanName(String batsmanName) {
        this.batsmanName = batsmanName;
    }

    public Integer getBattingPosition() {
        return battingPosition;
    }

    public void setBattingPosition(Integer battingPosition) {
        this.battingPosition = battingPosition;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Batsman{" +
                "batsmanId=" + batsmanId +
                ", batsmanName='" + batsmanName + '\'' +
                ", battingPosition=" + battingPosition +
                ", team=" + (team != null ? team.getTeamId() : null) +
                '}';
    }

    // ===== Manual Builder Class =====
    public static class Builder {
        private Long batsmanId;
        private String batsmanName;
        private Integer battingPosition;
        private Team team;

        public Builder batsmanId(Long batsmanId) {
            this.batsmanId = batsmanId;
            return this;
        }

        public Builder batsmanName(String batsmanName) {
            this.batsmanName = batsmanName;
            return this;
        }

        public Builder battingPosition(Integer battingPosition) {
            this.battingPosition = battingPosition;
            return this;
        }

        public Builder team(Team team) {
            this.team = team;
            return this;
        }

        public Batsman build() {
            return new Batsman(batsmanId, batsmanName, battingPosition, team);
        }
    }

    // Builder entry point
    public static Builder builder() {
        return new Builder();
    }
}
