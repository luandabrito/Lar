package com.lvc.servidorlvc;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AlimentoController {

    private static int contadorId = 1;
    private Map<Integer, Alimento> alimentos = new TreeMap<>();

    @GetMapping("/alimento")
    List<Alimento> all() {
        return new ArrayList<>(alimentos.values());
    }

    @PostMapping("/alimento" + "")
    Alimento newAlimento(@RequestBody Alimento novoAlimento){
        novoAlimento.setId(contadorId++);
        return alimentos.put(novoAlimento.getId(), novoAlimento);
    }

    @GetMapping("/alimento/{id}")
    Alimento one(@PathVariable Integer id) {
        return alimentos.get(id);
    }

    @PutMapping("/alimento/{id}")
    Alimento atualizar(@RequestBody Alimento alimento, @PathVariable Integer id) {
        return alimentos.put(id, alimento);
    }

    @DeleteMapping("/alimento/{id}")
    void excluir(@PathVariable Integer id) {
        alimentos.remove(id);
    }
}
