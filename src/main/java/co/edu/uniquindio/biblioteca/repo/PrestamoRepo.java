package co.edu.uniquindio.biblioteca.repo;

import co.edu.uniquindio.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PrestamoRepo extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByCliente(String cliente);

    List<Prestamo> findByFechaPrestamo(Date fechaPrestamo);

    @Query("SELECT p FROM Prestamo p where p.estado = :estado")
    List<Prestamo> findByEstado(boolean estado);

    @Query("SELECT COUNT(p) FROM Prestamo p JOIN p.libros l WHERE l.isbn = :isbn")
    Long countByIsbn(@Param("isbn") String isbn);
}
