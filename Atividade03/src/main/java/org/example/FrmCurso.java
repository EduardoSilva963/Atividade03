package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class FrmCurso {
    public JTable TblCurso;
    public JPanel FrmPrincipal;
    public JTextField TxtNome;
    public JTextField TxtSigla;
    public JComboBox<Curso.Area> CmbArea;
    public JButton BtnIncluir;
    public JButton BtnAlterar;
    public JButton BtnExcluir;

    public CursoDAO cursoDAO;
    public DefaultTableModel tableModel;

    public FrmCurso(Connection connection) {
        // Inicializar DAO com a conexão ao banco de dados
        cursoDAO = new CursoDAO(connection);

        // Configurar modelo da tabela
        tableModel = new DefaultTableModel();
        TblCurso.setModel(tableModel);
        tableModel.addColumn("Código");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Sigla");
        tableModel.addColumn("Área");

        // Preencher a combo box com as áreas do enum
        for (Curso.Area area : Curso.Area.values()) {
            CmbArea.addItem(area);
        }

        // Carregar dados iniciais da tabela
        loadCursos();

        // Ação do botão Incluir
        BtnIncluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = TxtNome.getText();
                String sigla = TxtSigla.getText();
                Curso.Area area = (Curso.Area) CmbArea.getSelectedItem();

                Curso curso = new Curso(null, nome, sigla, area);
                cursoDAO.create(curso);  // Inserir no banco

                loadCursos();  // Atualizar a tabela
                clearFields();
            }
        });

        // Ação do botão Alterar
        BtnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = TblCurso.getSelectedRow();
                if (selectedRow >= 0) {
                    Long codigo = (Long) tableModel.getValueAt(selectedRow, 0);
                    String nome = TxtNome.getText();
                    String sigla = TxtSigla.getText();
                    Curso.Area area = (Curso.Area) CmbArea.getSelectedItem();

                    Curso curso = new Curso(codigo, nome, sigla, area);
                    cursoDAO.update(curso);  // Atualizar no banco

                    loadCursos();  // Atualizar a tabela
                    clearFields();
                }
            }
        });

        // Ação do botão Excluir
        BtnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = TblCurso.getSelectedRow();
                if (selectedRow >= 0) {
                    Long codigo = (Long) tableModel.getValueAt(selectedRow, 0);
                    cursoDAO.delete(codigo);  // Deletar no banco

                    loadCursos();  // Atualizar a tabela
                }
            }
        });

        // Evento de seleção de linha na tabela
        TblCurso.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = TblCurso.getSelectedRow();
            if (selectedRow >= 0) {
                TxtNome.setText((String) tableModel.getValueAt(selectedRow, 1));
                TxtSigla.setText((String) tableModel.getValueAt(selectedRow, 2));
                CmbArea.setSelectedItem(Curso.Area.valueOf((String) tableModel.getValueAt(selectedRow, 3)));
            }
        });
    }

    // Método para carregar cursos no JTable
    private void loadCursos() {
        List<Curso> cursos = cursoDAO.findAll();
        tableModel.setRowCount(0);  // Limpar a tabela

        for (Curso curso : cursos) {
            tableModel.addRow(new Object[]{
                    curso.getCodigo(),
                    curso.getNome(),
                    curso.getSigla(),
                    curso.getArea().toString()
            });
        }
    }

    // Método para limpar os campos de texto
    private void clearFields() {
        TxtNome.setText("");
        TxtSigla.setText("");
        CmbArea.setSelectedIndex(0);
    }

    // Método para obter o JPanel principal (usado no JFrame)
    public JPanel getMainPanel() {
        return FrmPrincipal;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
