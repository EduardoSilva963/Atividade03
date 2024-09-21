package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            final Connection connection = DataBaseConnection.getConnection();
            System.out.println("Conectado ao banco de dados com sucesso!");

            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Gerenciamento de Cursos");
                FrmCurso frmCurso = new FrmCurso(connection);
                frame.setContentPane(frmCurso.getMainPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Falha ao conectar ao banco de dados!", "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    final Connection connection = DataBaseConnection.getConnection();
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                        System.out.println("Conexão com o banco de dados encerrada.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }));
        }
    }
}
