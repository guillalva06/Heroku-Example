/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cosw.samples.persistence;

import edu.eci.cosw.samples.model.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author 3079945
 */
public interface ClientsRepository extends CrudRepository<Cliente, Integer>{
    
    @Query("from Cliente c where c.nombre like :ln")
    public List<Cliente> search (@Param("ln") String searchTerm);
    
    
}
