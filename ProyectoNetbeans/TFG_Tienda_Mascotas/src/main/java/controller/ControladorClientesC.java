/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import AA_Main.Regex;
import controller.factory.HibernateUtil;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import modelo.dao.ClienteDAO;
import modelo.vo.Cliente;
import org.hibernate.Session;
import vista.ClientesC;
import vista.Inicio;

/**
 *
 * @author ciroi
 */
public class ControladorClientesC {
    public static Session session;
    public static Inicio inicio = new Inicio();
    public static ClienteDAO clienteDAO;
    JTextArea txtArea = ventana.getTxtAreaClientes();
    public static DefaultComboBoxModel modelocombo = new DefaultComboBoxModel();
    public static ClientesC ventana;
    
    
    public static void iniciar(JFrame parent) {
        ventana = new ClientesC(parent, true);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        clienteDAO = new ClienteDAO();
    }
    public static void cerrarSession() {
        session.close();       
    }
    
    public static void cargarClientes() {
        
        try {
            HibernateUtil.beginTx(session);
            
            modelocombo = clienteDAO.cargarCombo(session, modelocombo);
            ventana.getComboBox().setModel(modelocombo);
            
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTx(session);
        }
        
    }

    public static void cargarCliente() {
        
        try {
            HibernateUtil.beginTx(session);
            
            Cliente cliente_seleccionado = (Cliente) modelocombo.getSelectedItem();
            JTextArea txtArea = ventana.getTxtAreaClientes(); 
            
           clienteDAO.cargarTxtAreaCliente(session, cliente_seleccionado, txtArea);
            
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTx(session);
        }
        
    }

    public static void mostrarClientePorDNI() {
        //Comprobar que rellene todos los campos y que tengan valores válidos
        if(ventana.getInsertarDNI().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Es obligatorio rellenar el campo DNI");
            return;
        } else if(!Regex.validarDNI(ventana.getInsertarDNI().getText())) {
            JOptionPane.showMessageDialog(null, "El DNI no cumple el formato requerido");
            return;
        }
        
        try {
            HibernateUtil.beginTx(session);
            
            Cliente cliente = clienteDAO.getCliente(session, ventana.getInsertarDNI().getText());
            
            //Compruebo que el cliente existe antes de buscar
            if(cliente == null) {
                JOptionPane.showMessageDialog(null, "No existe el cliente proporcionado");
                return;
            }
            
            JTextArea txtArea = ventana.getTxtAreaCliente();
            
            clienteDAO.cargarTxtAreaCliente(session, cliente, txtArea);
            
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTx(session);
        } 
        
    }
    
}
