/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import AA_Main.Regex;
import controller.factory.HibernateUtil;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.dao.ClienteDAO;
import modelo.vo.Cliente;
import org.hibernate.Session;
import vista.ClientesIBM;

/**
 *
 * @author ciroi
 */
public class ControladorClientesIBM {
    public static Session session;
    public static ClienteDAO clienteDAO;
    public static ClientesIBM ventana;
    
    public static void iniciar(JFrame parent) {
        ventana = new ClientesIBM(parent, true);
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
        } else if(!Regex.datoEsEntero(ventana.getInsertarTelefono().getText()) 
                    || !Regex.datoTieneNueveCaracteres(ventana.getInsertarTelefono().getText())) {
            JOptionPane.showMessageDialog(null, "El telefono proporcionado debe estar conformado por 9 números enteros");
            return;
        } else if(!Regex.validarCorreo(ventana.getInsertarCorreo().getText())) {
            JOptionPane.showMessageDialog(null, "El correo proporcionado no cumple el formato requerido");
            return;
        } 
        
        try {
            HibernateUtil.beginTx(session);
            
            //Compruebo que el cliente no existe antes de insertar
            Cliente cliente = clienteDAO.getCliente(session, ventana.getInsertarDNI().getText());
            
            if(cliente != null) {
                JOptionPane.showMessageDialog(null, "El cliente proporcionado ya existe");
                return;
            }
            
            clienteDAO.insertar(session, ventana.getInsertarDNI().getText(), ventana.getInsertarNombre().getText(), 
                                ventana.getInsertarApellidos().getText(), ventana.getInsertarTelefono().getText(),
                                ventana.getInsertarCorreo().getText(), ventana.getInsertarResidencia().getText());
            
            JOptionPane.showMessageDialog(null, "Cliente insertado correctamente");
            
            HibernateUtil.commitTx(session);
        } catch(Exception ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTx(session);
        }
        
    }

    public static void borrar() {
        //Comprobar que rellene todos los campos y que tengan valores válidos
        if(ventana.getBorrarPorDNI().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Es obligatorio rellenar el campo DNI");
            return;
        } else if(!Regex.validarDNI(ventana.getBorrarPorDNI().getText())) {
            JOptionPane.showMessageDialog(null, "El dni proporcionado no cumple el formato requerido");
            return;
        }
        
        try {
            HibernateUtil.beginTx(session);
            
            //Compruebo que el cliente existe antes de borrar
            Cliente cliente = clienteDAO.getCliente(session, ventana.getBorrarPorDNI().getText());
            
            if(cliente == null) {
                JOptionPane.showMessageDialog(null, "No existe el cliente proporcionado");
                return;
            }
            
            clienteDAO.borrar(session, cliente);
            
            JOptionPane.showMessageDialog(null, "Cliente borrado correctamente");
            
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTx(session);
        }
        
    }

    public static void modificar() {
       //Comprobar que rellene todos los campos y que tengan valores válidos
        if(ventana.getModificarDNI().getText().isEmpty() || ventana.getModificarNombre().getText().isEmpty() ||
           ventana.getModificarApellidos().getText().isEmpty() || ventana.getModificarTelefono().getText().isEmpty() ||
           ventana.getModificarCorreo().getText().isEmpty() || ventana.getModificarResidencia().getText().isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "Es obligatorio rellenar todos los campos");
            return;
        } else if(!Regex.validarDNI(ventana.getModificarDNI().getText())) {
            JOptionPane.showMessageDialog(null, "El dni proporcionado no cumple el formato requerido");
            return;
        } else if(!Regex.datoEsEntero(ventana.getModificarTelefono().getText())) {
            JOptionPane.showMessageDialog(null, "El telefono proporcionado debe estar conformado por números enteros");
            return;
        } else if(!Regex.validarCorreo(ventana.getModificarCorreo().getText())) {
            JOptionPane.showMessageDialog(null, "El correo proporcionado no cumple el formato requerido");
            return;
        }
        
        try {
            HibernateUtil.beginTx(session);
            
            //Compruebo que el cliente existe antes de modificar
            Cliente cliente = clienteDAO.getCliente(session, ventana.getModificarDNI().getText());
            
            if(cliente == null) {
                JOptionPane.showMessageDialog(null, "No existe el cliente proporcionado");
                return;
            }
            
            clienteDAO.modificar(session, cliente, ventana.getModificarNombre().getText(), 
                                ventana.getModificarApellidos().getText(), ventana.getModificarTelefono().getText(),
                                ventana.getModificarCorreo().getText(), ventana.getModificarResidencia().getText());
            
            JOptionPane.showMessageDialog(null, "Cliente modificado correctamente");
            
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTx(session);
        }
        
    }
    
    
    
    
}
