/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import java.util.List;
import org.hibernate.query.Query;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import modelo.vo.Cliente;
import modelo.vo.Especialista;
import org.hibernate.Session;

/**
 *
 * @author ciroi
 */
public class EspecialistaDAO {
    
    public Especialista getEspecialista(Session session, String DNI) {
        //OBTIENE ESPECIALISTAS POR LA PK
        return session.get(Especialista.class, DNI);
    }
    
    public void insertar(Session session, String dni, String nombre, String apellidos, 
                        String telefono, String correo, String residencia, double sueldo) throws Exception {
        
      Especialista especialista = new Especialista(dni, nombre, apellidos, telefono, correo, residencia, sueldo);
      
      session.save(especialista);
    }
    
    public void borrar(Session session, Especialista especialista) {
        session.delete(especialista);
    }
    
    
}
