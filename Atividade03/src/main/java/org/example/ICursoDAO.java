package org.example;

import java.util.List;

public interface ICursoDAO {
    void create(Curso curso);
    void update(Curso curso);
    void delete(Long codigo);
    List<Curso> findAll();
    Curso findById(Long codigo);
    List<Curso> findByArea(Curso.Area area);
    Curso findBySigla(String sigla);
}
