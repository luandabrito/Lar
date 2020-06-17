package com.lvc.servidorlvc;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class EpiController {

    private static int contadorId = 1;
    private Map<Integer, Epi> epiTreeMaps = new TreeMap<>();

    @GetMapping("/epi")
    List<Epi> all() {
        return new ArrayList<>(epiTreeMaps.values());
    }

    @PostMapping("/epi" + "")
    Epi newEpi(@RequestBody Epi novoEpi){
        novoEpi.setId(contadorId++);
        return epiTreeMaps.put(novoEpi.getId(), novoEpi);
    }

    @GetMapping("/epi/{id}")
    Epi one(@PathVariable Integer id) {
        return epiTreeMaps.get(id);
    }

    @PutMapping("/epi/{id}")
    Epi atualizar(@RequestBody Epi epi, @PathVariable Integer id) {
        return epiTreeMaps.put(id, epi);
    }

    @DeleteMapping("/epi/{id}")
    void excluir(@PathVariable Integer id) {
        epiTreeMaps.remove(id);
    }
}
