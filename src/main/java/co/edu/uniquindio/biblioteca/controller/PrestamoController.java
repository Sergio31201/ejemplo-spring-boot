package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.dto.*;
import co.edu.uniquindio.biblioteca.entity.Libro;
import co.edu.uniquindio.biblioteca.entity.Prestamo;
import co.edu.uniquindio.biblioteca.servicios.PrestamoServicio;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamo")
@AllArgsConstructor
public class PrestamoController {

    private final PrestamoServicio prestamoServicio;
    @PostMapping
    public ResponseEntity<Respuesta<Prestamo>> save(@RequestBody PrestamoDTO prestamoDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body( new Respuesta<>("Prestamo creado correctamente", prestamoServicio.save(prestamoDTO)) );
    }
    @GetMapping("/{estado}")
    public ResponseEntity<Respuesta<List<Prestamo>>> findAll(@PathVariable Boolean estado){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("", prestamoServicio.findByEstadoPrestamo(estado)) );
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<Respuesta<Prestamo>> delete(@PathVariable long codigo){
        return ResponseEntity.status(HttpStatus.OK).body( new Respuesta<>("Se cambió el estado del prestamo correctamente", prestamoServicio.eliminarPrestamo(codigo)));
    }

}
