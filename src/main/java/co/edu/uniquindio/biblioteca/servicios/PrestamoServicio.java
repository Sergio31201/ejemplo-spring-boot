package co.edu.uniquindio.biblioteca.servicios;

import co.edu.uniquindio.biblioteca.dto.PrestamoDTO;
import co.edu.uniquindio.biblioteca.entity.Cliente;
import co.edu.uniquindio.biblioteca.entity.Libro;
import co.edu.uniquindio.biblioteca.entity.Prestamo;
import co.edu.uniquindio.biblioteca.repo.ClienteRepo;
import co.edu.uniquindio.biblioteca.repo.LibroRepo;
import co.edu.uniquindio.biblioteca.repo.PrestamoRepo;
import co.edu.uniquindio.biblioteca.servicios.excepciones.ClienteNoEncontradoException;
import co.edu.uniquindio.biblioteca.servicios.excepciones.LibroNoEncontradoException;
import co.edu.uniquindio.biblioteca.servicios.excepciones.PrestamoNoEncontradoException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrestamoServicio {

    private final PrestamoRepo prestamoRepo;
    private final ClienteRepo clienteRepo;

    private final LibroRepo libroRepo;

    public Prestamo save(PrestamoDTO prestamoDTO){

        long codigoCliente = prestamoDTO.clienteID();
        Optional<Cliente> consulta = clienteRepo.findById(codigoCliente);

        if(consulta.isEmpty()){
            throw new ClienteNoEncontradoException("No existe");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setCliente(consulta.get());
        prestamo.setFechaPrestamo(LocalDateTime.now());

        List<String> codigosLibros = prestamoDTO.isbnLibros();
        List<Libro> libros = new ArrayList<>();

        Optional<Libro> buscado = libroRepo.findById(codigosLibros.toString());

        if(buscado.isEmpty()){
            new LibroNoEncontradoException("Libro no encontrado");
        }

        prestamo.setLibros(libros);
        prestamo.setFechaDevolucion(prestamoDTO.fechaDevolucion());
        prestamo.setEstado(true);

        return prestamoRepo.save(prestamo);
    }


    public List<Prestamo> findByEstadoPrestamo(Boolean estado){
        if(estado == true){
            return prestamoRepo.findByEstado(true);
        }
        return (List<Prestamo>) new PrestamoNoEncontradoException("No se pueden buscar con estado en false");
    }


    public List<PrestamoDTO> prestamosPorCodigoCliente(long codigoCliente){
        return (List<PrestamoDTO>) clienteRepo.findById(codigoCliente).orElseThrow(()-> new PrestamoNoEncontradoException("No tiene prestamos"));
    }


    public List<PrestamoDTO> findById(long codigoPrestamo){
        return (List<PrestamoDTO>) prestamoRepo.findById(codigoPrestamo).orElseThrow(()-> new PrestamoNoEncontradoException("No existe"));
    }

    public Prestamo eliminarPrestamo(Long codigo) {
        Optional<Prestamo> optionalPrestamo = prestamoRepo.findById(codigo);
        if (optionalPrestamo.isPresent()) {
            Prestamo prestamo = optionalPrestamo.get();
            prestamo.setEstado(false);
            prestamoRepo.save(prestamo);
            return prestamo;
        }
        return null;
    }

    public List<Prestamo> listaPrestamosPorFecha(Date fechaPrestamo){
        return prestamoRepo.findByFechaPrestamo(fechaPrestamo);
    }

    public long obtenerCantidadPrestamos(String isbn){
        return prestamoRepo.countByIsbn(isbn);
    }

}
