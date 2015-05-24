/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Nevermade
 */
public class Strings {
    private Strings(){
        
    }
    /*
    Los mensajes deben iniciar con una palabra de la siguiente manera
    ALERT_  para alertas
    MESSAGE_ para mensajes de confirmacion
    ERROR_  para errores
    (si falta algún caso documentenlo aquí)
    */
    
    
    /*GENERAL MESSAGES*/
    
    /*END*/
    
    /*PROFILE MESSAGES*/
    public static final String MESSAGE_NEW_PROFILE_CREATED="El perfil se ha registrado con éxito";
    public static final String MESSAGE_PROFILE_EDITED="El perfil ha sido modificado con éxito";
            
    
    /*END*/
    
    
    /*USER MODULE MESSAGES*/
    public static final String MESSAGE_RECOVER_PASSWORD="Se ha enviado su nueva contraseña a su dirección de correo.";
    public static final String MESSAGE_RESTABLISH_PASSWORD="Se ha enviado la nueva contraseña al correo del usuario.";
    public static final String MESSAGE_CHANGE_PASSWORD="Se ha modificado su contraseña satisfactoriamente.";
    public static final String MESSAGE_NEW_USER_CREATED="El usuario se ha registrado con éxito.";
    public static final String MESSAGE_USER_UPDATED="El usuario ha sido actualizado con éxito.";
    /*END*/
    
    /*WAREHOUSE MODULE MESSAGES*/
    public static final String ERROR_PALLETS_MOVEMENT_TITLE="Error al mover pallets";
    public static final String ERROR_NO_PALLETS_SELECTED="Seleccione algún pallet.";
    public static final String ERROR_NO_SPOTS_SELECTED="Seleccione alguna ubicación de destino.";
    public static final String ERROR_PALLETS_DONT_MATCH_SPOTS="La cantidad de pallets y ubicaciones seleccionados deben ser iguales.";
    /*END*/
    
    /*CLIENT MODULE MESSAGES*/
    public static final String MESSAGE_NEW_CLIENT_TITLE="Mensaje de inserción de cliente";
    public static final String MESSAGE_NEW_CLIENT_CREATED="Se registró correctamente el cliente.";
    public static final String MESSAGE_DELETE_CLIENT_TITLE="Mensaje de eliminación de cliente";
    public static final String MESSAGE_DELETE_CLIENT="¿Está seguro de eliminar el usuario seleccionado?";
    public static final String ERROR_NEW_CLIENT_TITLE="Error al agregar nuevo cliente";
    public static final String ERROR_NAME_LESS_2="El campo nombre debe tener por lo menos 2 caracteres.";
    public static final String ERROR_NAME_MORE_60="El campo nombre no debe contener más de 60 caracteres.";
    public static final String ERROR_RUC_NOT_11="El campo RUC debe contener exactamente 11 caracteres.";
    public static final String ERROR_RUC_NOT_NUMERIC="El campo RUC debe contener caracteres numéricos solamente.";
    /*END*/
    
}
