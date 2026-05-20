package com.example.demo.Repository;

import com.example.demo.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByTeamName(String teamName);
    Optional<Team> findByTeamNameIgnoreCase(String teamName);

}