package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO implements ICursoDAO {
    private Connection connection;

    public CursoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Curso curso) {
        String sql = "INSERT INTO Curso (nome, sigla, area) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getSigla());
            stmt.setString(3, curso.getArea().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Curso curso) {
        String sql = "UPDATE Curso SET nome = ?, sigla = ?, area = ? WHERE codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getSigla());
            stmt.setString(3, curso.getArea().toString());
            stmt.setLong(4, curso.getCodigo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long codigo) {
        String sql = "DELETE FROM Curso WHERE codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, codigo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Curso> findAll() {
        String sql = "SELECT * FROM Curso";
        List<Curso> cursos = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getLong("codigo"),
                        rs.getString("nome"),
                        rs.getString("sigla"),
                        Curso.Area.valueOf(rs.getString("area"))
                );
                cursos.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    @Override
    public Curso findById(Long codigo) {
        String sql = "SELECT * FROM Curso WHERE codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Curso(
                            rs.getLong("codigo"),
                            rs.getString("nome"),
                            rs.getString("sigla"),
                            Curso.Area.valueOf(rs.getString("area"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Curso> findByArea(Curso.Area area) {
        String sql = "SELECT * FROM Curso WHERE area = ?";
        List<Curso> cursos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, area.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Curso curso = new Curso(
                            rs.getLong("codigo"),
                            rs.getString("nome"),
                            rs.getString("sigla"),
                            Curso.Area.valueOf(rs.getString("area"))
                    );
                    cursos.add(curso);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    @Override
    public Curso findBySigla(String sigla) {
        String sql = "SELECT * FROM Curso WHERE sigla = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sigla);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Curso(
                            rs.getLong("codigo"),
                            rs.getString("nome"),
                            rs.getString("sigla"),
                            Curso.Area.valueOf(rs.getString("area"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
