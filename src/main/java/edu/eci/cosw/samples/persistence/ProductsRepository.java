/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cosw.samples.persistence;

import edu.eci.cosw.samples.model.Producto;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author User
 */
public interface ProductsRepository extends CrudRepository<Producto, Integer>{
    
}
