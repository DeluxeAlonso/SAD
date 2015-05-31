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
    public static final String MESSAGE_NEW_PROFILE_CREATED="El perfil se ha registrado con éxito.";
    public static final String MESSAGE_PROFILE_EDITED="El perfil ha sido modificado con éxito.";
    public static final String MESSAGE_PROFILE_DELETED="El perfil se ha eliminado exitosamente.";        
    
    /*END*/
    
    
    /*USER MODULE MESSAGES*/
    public static final String MESSAGE_RECOVER_PASSWORD="Se ha enviado su nueva contraseña a su dirección de correo.";
    public static final String MESSAGE_RESTABLISH_PASSWORD="Se ha enviado la nueva contraseña al correo del usuario.";
    public static final String MESSAGE_CHANGE_PASSWORD="Se ha modificado su contraseña satisfactoriamente.";
    public static final String MESSAGE_NEW_USER_CREATED="El usuario se ha registrado con éxito.";
    public static final String MESSAGE_USER_UPDATED="El usuario ha sido actualizado con éxito.";
    public static final String ALERT_USER_INACTIVE="No tienes acceso a la aplicación.";
    public static final String ALERT_USER_DOES_NOT_EXIST="Correo o la contraseña incorrectos.";
    public static final String ERROR_NAME_REQUIRED="El campo nombre es requerido.";
    public static final String ERROR_FIRSTNAME_REQUIRED="El campo apellido paterno es requerido.";
    public static final String ERROR_SECONDNAME_REQUIRED="El campo apellido materno es requerido.";
    public static final String ERROR_EMAIL_REQUIRED="El campo correo es requerido.";
    /*END*/
    
    /*WAREHOUSE MODULE MESSAGES*/
    public static final String ERROR_PALLETS_MOVEMENT_TITLE="Error al mover pallets";
    public static final String ERROR_NO_PALLETS_SELECTED="Seleccione algún pallet.";
    public static final String ERROR_NO_SPOTS_SELECTED="Seleccione alguna ubicación de destino.";
    public static final String ERROR_PALLETS_DONT_MATCH_SPOTS="La cantidad de pallets y ubicaciones seleccionados deben ser iguales.";
    public static final String MESSAGE_WAREHOUSE_CREATED="El almacen se ha registrado con éxito.";
    public static final String ERROR_AREA_WAREHOUSE_REQUIRED= "El campo Area es obligatorio.";
    public static final String ERROR_AREA_WAREHOUSE_DOUBLE= "El campo Area tiene que ser un numero.";
    public static final String ERROR_CAPACITY_WAREHOUSE_REQUIRED= "El campo capacidad es obligatorio.";
    public static final String ERROR_CAPACITY_WAREHOUSE_INT= "El campo capacidad debe ser un numero entero.";
    public static final String ERROR_RACKS_WAREHOUSE_REQUIRED= "El campo racks es obligatorio.";
    public static final String ERROR_RACKS_WAREHOUSE_INT= "El campo rack debe ser un numero entero.";
    public static final String ERROR_CONDICION_WAREHOUSE_REQUIRED= "El campo condicion es obligatorio.";
    
    
    
    
    
    
    
    
    
    
    /*END*/
    
    /*CLIENT MODULE MESSAGES*/
    public static final String MESSAGE_NEW_CLIENT_TITLE="Mensaje de inserción de cliente";
    public static final String MESSAGE_NEW_CLIENT_CREATED="Se registró correctamente el cliente.";
    public static final String MESSAGE_DELETE_CLIENT_TITLE="Mensaje de eliminación de cliente";
    public static final String MESSAGE_DELETE_CLIENT="¿Está seguro de eliminar el cliente seleccionado?";
    public static final String ERROR_NEW_CLIENT_TITLE="Error al agregar nuevo cliente";
    public static final String ERROR_NAME_LESS_2="El campo Nombre debe tener por lo menos 2 caracteres.";
    public static final String ERROR_NAME_MORE_60="El campo Nombre no debe contener más de 60 caracteres.";
    public static final String ERROR_RUC_NOT_11="El campo RUC debe contener exactamente 11 caracteres.";
    public static final String ERROR_RUC_NOT_NUMERIC="El campo RUC debe contener caracteres numéricos solamente.";
    public static final String ERROR_NEW_LOCAL_TITLE="Error al agregar nuevo local";
    public static final String ERROR_NOT_CSV="El archivo seleccionado no es un archivo CSV.";
    public static final String ERROR_FILE_UPLOAD_TITLE="Error al cargar el archivo";
    public static final String MESSAGE_NEW_LOCAL_TITLE="Mensaje de inserción de local.";
    public static final String MESSAGE_NEW_LOCAL_CREATED="Se registró correctamente el local.";
    public static final String MESSAGE_DELETE_LOCAL_TITLE="Mensaje de eliminación de local";
    public static final String MESSAGE_DELETE_LOCAL="¿Está seguro de eliminar el local seleccionado?";
    public static final String ERROR_ADDRESS_REQUIRED="El campo Dirección es requerido.";
    public static final String ERROR_LATITUDE_REQUIRED="El campo Latitud es requerido.";
    public static final String ERROR_LONGITUDE_REQUIRED="El campo Longitud es requerido.";
    public static final String ERROR_LATITUDE_NOT_FLOAT="El campo Latitud debe ser un número de punto flotante.";
    public static final String ERROR_LONGITUDE_NOT_FLOAT="El campo Longitud debe ser un número de punto flotante.";
    /*END*/
    
    /*TRANSPORT UNIT MESSAGES*/
    public static final String MESSAGE_TRANSPORT_UNIT_TITLE="Mensaje de inserción de unidad de transporte";
    public static final String MESSAGE_NEW_TRANSPORT_UNIT_CREATED = "Se registró o modifico correctamente la unidad de transporte.";
    public static final String ERROR_MESSAGE_TRANSPORT_UNIT="Error  al agregar o modificar una nueva unidad de transporte";
    public static final String MESSAGE_DELETE_TRANSPORT_UNIT_TITLE="Mensaje de eliminacion de unidad de transporte";
    public static final String MESSAGE_DELETE_TRANSPORT_UNIT="Se elimino correctamente el cliente";
    public static final String MESSAGE_CONFIRM_DELETE_TRANSPORT_UNIT="¿Está seguro de eliminar la unidad de transporte seleccionada?";
    public static final String MESSAGE_FILE_TRANSPORT_UNIT_TITLE="Carga Masiva";
    public static final String MESSAGE_FILE_TRANSPORT_UNIT="Se realizo la carga de forma correcta";
    public static final String MESSAGE_FILE_ERROR_TRANSPORT_UNIT="No se pudo realizar la carga masiva";
    public static final String ERROR_PLATE_REQUIRED="El campo Placa es requerido.";
    public static final String ERROR_PLATE_NOT_6="El campo Placa debe contener exactamanete 6 caracteres.";
    public static final String ERROR_TRANSPORTIST_REQUIRED="El campo Transportista es requerido.";
    public static final String ERROR_TRANSPORTIST_MORE_60="El campo Transportista no debe contener mas de 60 caracteres.";
    public static final String ERROR_TRANSPORT_TYPE_REQUIRED="El campo Tipo es requerido.";
    
    
    /*END*/
    
    /*ORDERS MESSAGES*/
    public static final String MESSAGE_CONFIRM_DELETE_ORDER="¿Está seguro de eliminar el pedido seleccionado?";
    public static final String MESSAGE_DELETE_ORDER_TITLE="Mensaje de anulacion de Pedido";
    public static final String MESSAGE_DELETE_ORDER="El pedido ha sido anulado";
    public static final String MESSAGE_CREATE_ORDER="El pedido ha sido creado";
    public static final String MESSAGE_CREATE_ORDER_TITLE="Creacion de Pedido";
    public static final String ERROR_CREATE_ORDER_LESS_0="La cantidad seleccionada no puede ser menor a cero";
    public static final String ERROR_CREATE_ORDER_NO_CLIENTE="Debe seleccionar un cliente.";
    public static final String ERROR_CREATE_ORDER_NO_PRODUCTS="Debe seleccionar al menos un producto.";
    public static final String ERROR_CREATE_ORDER="Error al registrar nuevo Pedido.";
    public static final String LOAD_ORDER_TITLE="Carga Masiva de Pedidos";
    public static final String LOAD_ORDER_ERROR="No se puedo realizar la carga masiva de Pedidos";
    public static final String LOAD_ORDER_SUCCESS="Se realizo la carga masiva de Pedidos de forma correcta";
    /*END*/
    
    
    /*PRODUCT TYPE MESSAGES*/
    public static final String MESSAGE_TPRODUCT_TITLE="Mensaje de inserción de tipo de producto";
    public static final String MESSAGE_TPRODUCT_CREATED="Se registró correctamente el tipo de producto.";
    /*END*/

    /*PRODUCT MESSAGES*/
    public static final String ERROR_CANTIDAD_PROD_REQUIRED="El campo Cantidad es obligatorio.";
    public static final String ERROR_CANTIDAD_PROD_INT ="El campo cantidad debe ser un numero entero.";
    public static final String ERROR_CANTIDADXPALLET_REQUIRED="El campo de cantidad por pallet es obligatorio.";
    public static final String ERROR_CANTIDADXPALLET_INT ="El campo de cantidad por pallet debe ser un numero entero.";
    public static final String ERROR_PRODUCT_NAME_LESS_2 ="El campo Nombre de Producto debe tener por lo menos 2 caracteres.";
    public static final String ERROR_DESC_PROD_REQUIRED="El campo Descripcion del Producto es obligatorio.";
    public static final String ERROR_CONDITION_PROD_REQUIRED="El campo condicion es obligatorio";
    public static final String MESSAGE_NEW_PRODUCT_CREATED = "Se registró correctamente el producto.";
    public static final String MESSAGE_NEW_PRODUCT_TITLE ="Mensaje de inserción de producto";
    
    public static final String ERROR_PESO_PROD_REQUIRED="El campo peso es obligatorio.";
    public static final String ERROR_PESO_PROD_DOUBLE="El campo peso debe ser un numero.";
    
    
    /*END*/
    
    /*KARDEX MESSAGES*/
    public static final String ERROR_KARDEX_TITLE="Error en reporte de Kardex";
    public static final String ERROR_DATE_INI="Seleccione fecha inicial";
    public static final String ERROR_DATE_END="Seleccione fecha final";
    public static final String ERROR_DATE="La fecha inicial debe ser anterior a la final";
    /*END*/
    
}
