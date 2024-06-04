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
import modelo.vo.Mascota;
import org.hibernate.Session;

/**
 *
 * @author ciroi
 */
public class MascotaDAO {
    
    public Mascota getMascota(Session session, String DNI) {
        //OBTIENE MASCOTAS POR LA PK
        return session.get(Mascota.class, DNI);
    }
    
    public void insertar(Session session, String dni, String nombre, int edad, 
                        char sexo, String especie, String raza, Especialista especialista) throws Exception {
        
      Mascota mascota = new Mascota(dni, nombre, edad, sexo, especie, raza, especialista);
      
      session.save(mascota);
    }
    
    public void borrar(Session session, Mascota mascota) {
        session.delete(mascota);
    }
    
    
}
