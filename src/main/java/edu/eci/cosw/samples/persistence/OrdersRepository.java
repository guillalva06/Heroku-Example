/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cosw.samples.persistence;

import edu.eci.cosw.samples.model.Pedido;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author User
 */
public interface OrdersRepository extends CrudRepository<Pedido, Integer>{
    
    @Query("from Pedido p where p.cliente.idcliente = :idCliente and p.fechaRadicacion = :fecha")
    public List<Pedido> search(@Param("idCliente")int idCliente, @Param("fecha")Date fecha);
    
    
}
