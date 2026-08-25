package view;

import dao.TutorDAO;
import model.Tutor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaTutor extends JFrame {
    
    private JTextField txtId, txtNome, txtCpf, txtTelefone, txtEmail, txtEndereco;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private TutorDAO tutorDAO;

    public TelaTutor() {
        
    }
}