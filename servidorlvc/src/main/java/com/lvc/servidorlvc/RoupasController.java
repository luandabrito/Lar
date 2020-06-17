package com.lvc.servidorlvc;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RoupasController {

    private static int contadorId = 1;
    private Map<Integer, Roupas> roupas = new TreeMap<>();

    @GetMapping("/roupas")
    List<Roupas> all() {
        return new ArrayList<>(roupas.values());
    }

    @PostMapping("/roupas" + "")
    Roupas newRoupas(@RequestBody Roupas novasRoupas){
        novasRoupas.setId(contadorId++);
        return roupas.put(novasRoupas.getId(), novasRoupas);
    }

    @GetMapping("/roupas/{id}")
    Roupas one(@PathVariable Integer id) {
        return roupas.get(id);
    }

    @PutMapping("/roupas/{id}")
    Roupas atualizar(@RequestBody Roupas roupa, @PathVariable Integer id) {
        return roupas.put(id, roupa);
    }

    @DeleteMapping("/roupas/{id}")
    void excluir(@PathVariable Integer id) {
        roupas.remove(id);
    }
}
