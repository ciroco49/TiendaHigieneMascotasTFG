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
import modelo.vo.Tener;
import org.hibernate.Session;

/**
 *
 * @author ciroi
 */
public class TenerDAO {
    
    public Tener getTener(Session session, Cliente cliente, Mascota mascota) {
        String consulta = "from Tener t where t.DNICliente =:cliente and t.DNIMascota =:mascota";
        
        Query q = session.createQuery(consulta);
        
        q.setParameter("cliente", cliente);
        q.setParameter("mascota", mascota);
        
        return (Tener) q.uniqueResult();
    }
    
    public void insertar(Session session, Cliente cliente, Mascota mascota) throws Exception {
        
      Tener tener = new Tener(cliente, mascota);
      
      session.save(tener);
    }
    
    public void borrar(Session session, Tener tener) {
        session.delete(tener);
    }
    
    
}
