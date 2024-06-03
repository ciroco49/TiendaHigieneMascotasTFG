/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import AA_Main.Regex;
import controller.factory.HibernateUtil;
import javax.swing.JOptionPane;
import modelo.dao.ClienteDAO;
import modelo.vo.Cliente;
import org.hibernate.Session;
import vista.ClientesIBM;
import vista.Inicio;

/**
 *
 * @author ciroi
 */
public class ControladorClientesIBM {
    public static Session session;
    public static Inicio inicio = new Inicio();
    public static ClienteDAO clienteDAO;
    public static ClientesIBM ventana = new ClientesIBM(inicio, true);
    
    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
//        ventana.getCmbDepartamento().setModel(modelocombo);
//        modelotabla=(DefaultTableModel) ventana.getTblEmpleados().getModel();
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        clienteDAO = new ClienteDAO();
    }
    public static void cerrarSession() {
        session.close();       
    }

    public static void insertar() {
        //Comprobar que rellene todos los campos y que tengan valores válidos
        if(ventana.getInsertarDNI().getText().isEmpty() || ventana.getInsertarNombre().getText().isEmpty() ||
           ventana.getInsertarApellidos().getText().isEmpty() || ventana.getInsertarTelefono().getText().isEmpty() ||
           ventana.getInsertarCorreo().getText().isEmpty() || ventana.getInsertarResidencia().getText().isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "Es obligatorio rellenar todos los campos");
            return;
        } else if(!Regex.validarDNI(ventana.getInsertarDNI().getText())) {
            JOptionPane.showMessageDialog(null, "El dni proporcionado no cumple el formato requerido");
            return;
        } else if(!Regex.datoEsEntero(ventana.getInsertarTelefono().getText())) {
            JOptionPane.showMessageDialog(null, "El telefono proporcionado debe estar conformado por números enteros");
            return;
        } else if(!Regex.validarCorreo(ventana.getInsertarCorreo().getText())) {
            JOptionPane.showMessageDialog(null, "El correo proporcionado no cumple el formato requerido");
            return;
        }
        
        try {
            session.beginTransaction();
            
            Cliente c = clienteDAO.getCliente(session, ventana.getInsertarDNI().getText());
            
            if(c != null) {
                JOptionPane.showMessageDialog(null, "El cliente proporcionado ya existe");
                return;
            }
            
            clienteDAO.insertar(session, ventana.getInsertarDNI().getText(), ventana.getInsertarNombre().getText(), 
                                ventana.getInsertarApellidos().getText(), ventana.getInsertarTelefono().getText(),
                                ventana.getInsertarCorreo().getText(), ventana.getInsertarResidencia().getText());
            
            JOptionPane.showMessageDialog(null, "Cliente insertado correctamente");
            
            session.getTransaction().commit();
        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        
    }
    
    
    
    
}
