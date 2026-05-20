package com.example.demo.controller;

import com.example.demo.entity.Batsman;
import com.example.demo.service.BatsmanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batsmen")
public class BatsmanController {

    @Autowired
    private BatsmanService batsmanService;

    @PostMapping
    public Batsman add(@Valid @RequestBody Batsman batsman) {

        return batsmanService.saveBatsman(batsman);
    }

    @GetMapping
    public List<Batsman> all() {
        return batsmanService.getAllBatsmen();
    }

    @PutMapping("/{id}")
    public Batsman update(@PathVariable Long id, @Valid @RequestBody Batsman batsman) {
        return batsmanService.updateBatsman(id, batsman);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        batsmanService.deleteBatsman(id);
    }

    @PatchMapping("/{id}")
    public Batsman patch(@PathVariable Long id, @RequestBody Batsman partialBatsman) {
    return batsmanService.patchBatsman(id, partialBatsman);
    }

}
