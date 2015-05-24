/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.transportunit;

import base.IRepository;
import entity.UnidadTransporte;

/**
 *
 * @author Alonso
 */
public interface ITransportUnitRepository extends IRepository<UnidadTransporte>{
    Boolean createTransportUnit(UnidadTransporte transportUnit);
    Boolean updateTransportUnit(UnidadTransporte transportUnit);
}
