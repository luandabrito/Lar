package com.lvc.servidorlvc;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

    @RestController
public class MedicamentosController {


        private static int contadorId = 1;
        private Map<Integer, Medicamento> medicamentos = new TreeMap<>();

        @GetMapping("/medicamentos")
        List<Medicamento> all() {
            return new ArrayList<>(medicamentos.values());
        }

        @PostMapping("/medicamentos" + "")
        Medicamento medicamentos(@RequestBody Medicamento novosMedicamento){
            novosMedicamento.setId(contadorId++);
            return medicamentos.put(novosMedicamento.getId(), novosMedicamento);
        }

        @GetMapping("/medicamentos/{id}")
        Medicamento one(@PathVariable Integer id) {
            return medicamentos.get(id);
        }

        @PutMapping("/medicamentos/{id}")
        Medicamento atualizar(@RequestBody Medicamento medicamento, @PathVariable Integer id) {
            return medicamentos.put(id, medicamento);
        }

        @DeleteMapping("/medicamentos/{id}")
        void excluir(@PathVariable Integer id) {
            medicamentos.remove(id);
        }
    }



