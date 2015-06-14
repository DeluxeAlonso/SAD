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

    private Strings() {

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
    public static final String MESSAGE_NEW_PROFILE_CREATED = "El perfil se ha registrado con éxito.";
    public static final String MESSAGE_PROFILE_EDITED = "El perfil ha sido modificado con éxito.";
    public static final String MESSAGE_PROFILE_DELETED = "El perfil se ha eliminado exitosamente.";

    /*END*/
    /*USER MODULE MESSAGES*/
    public static final String MESSAGE_RECOVER_PASSWORD = "Se ha enviado su nueva contraseña a su dirección de correo.";
    public static final String MESSAGE_RESTABLISH_PASSWORD = "Se ha enviado la nueva contraseña al correo del usuario.";
    public static final String MESSAGE_CHANGE_PASSWORD = "Se ha modificado su contraseña satisfactoriamente.";
    public static final String MESSAGE_NEW_USER_CREATED = "El usuario se ha registrado con éxito.";
    public static final String MESSAGE_USER_UPDATED = "El usuario ha sido actualizado con éxito.";
    public static final String ALERT_USER_INACTIVE = "No tienes acceso a la aplicación.";
    public static final String ALERT_USER_DOES_NOT_EXIST = "Correo o la contraseña incorrectos.";
    public static final String ALERT_PASSWORD_REQUIRED = "El campo contraseña es requerido.";
    public static final String ALERT_USER_REQUIRED = "El campo usuario es requerido.";
    public static final String ERROR_NAME_REQUIRED = "El campo nombre es requerido.";
    public static final String ERROR_FIRSTNAME_REQUIRED = "El campo apellido paterno es requerido.";
    public static final String ERROR_SECONDNAME_REQUIRED = "El campo apellido materno es requerido.";
    public static final String ERROR_EMAIL_REQUIRED = "El campo correo es requerido.";
    public static final String ERROR_NAME_ONLY_LETTERS = "El campo nombre solo permite letras.";
    public static final String ERROR_FIRSTNAME_ONLY_LETTERS = "El apellido paterno nombre solo permite letras.";
    public static final String ERROR_SECONDNAME_ONLY_LETTERS = "El apellido materno nombre solo permite letras.";
    public static final String ERROR_EMAIL_INVALID = "Ingrese un correo válido.";
    public static final String ERROR_NAME_LENGTH = "El campo nombre solo permite 40 caracteres.";
    public static final String ERROR_FIRSTNAME_LENGTH = "El campo apellido paterno solo permite 40 caracteres.";
    public static final String ERROR_SECONDNAME_LENGTH = "El campo apellido materno solo permite 40 caracteres.";
    public static final String ERROR_EMAIL_LENGTH = "El campo correo solo permite 40 caracteres.";
    public static final String ERROR_USER_STATUS_REQUIRED = "El campo estado es requerido.";
    public static final String ERROR_USER_PROFILE_REQUIRED = "El campo perfil es requerido.";
    public static final String ERROR_PASSWORD_MIN_LENGTH = "El password debe contener al menos 6 caracteres";
    public static final String ERROR_USER_SAME_EMAIL = "Ya existe un usuario con el mismo correo.";
    public static final String ERROR_PASSWORD_REQUIRED = "El campo contraseña es requerido";
    public static final String ERROR_PASSWORD_LENGTH = "El campo contraseña solo permite 40 caracteres.";
    public static final String ERROR_PROFILE_LENGTH = "El campo perfil solo permite 40 caracteres.";
    public static final String ERROR_PROFILE_NUMBERS_AND_LETTERS = "El campo perfil solo permite letras y números.";
    public static final String ERROR_PROFILE_HAS_USERS = "Hay usuarios que usan este perfil. No se puede eliminar.";
    /*END*/

    /*WAREHOUSE MODULE MESSAGES*/
    public static final String ERROR_PALLETS_MOVEMENT_TITLE = "Error al mover pallets";
    public static final String MESSAGE_PALLETS_MOVEMENT_TITLE = "Movimiento de pallets";
    public static final String MESSAGE_PALLETS_MOVEMENT = "Se realizó correctamente el movimiento de pallets.";
    public static final String ERROR_NO_PALLETS_SELECTED = "Seleccione algún pallet.";
    public static final String ERROR_NO_SPOTS_SELECTED = "Seleccione alguna ubicación de destino.";
    public static final String ERROR_PALLETS_DONT_MATCH_SPOTS = "La cantidad de pallets y ubicaciones seleccionados deben ser iguales.";
    public static final String MESSAGE_WAREHOUSE_CREATED = "El almacen se ha registrado con éxito.";
    public static final String ERROR_AREA_WAREHOUSE_REQUIRED = "El campo Area es obligatorio.";
    public static final String ERROR_AREA_WAREHOUSE_DOUBLE = "El campo Area tiene que ser un numero.";
    public static final String ERROR_CAPACITY_WAREHOUSE_REQUIRED = "El campo capacidad es obligatorio.";
    public static final String ERROR_CAPACITY_WAREHOUSE_INT = "El campo capacidad debe ser un numero entero.";
    public static final String ERROR_RACKS_WAREHOUSE_REQUIRED = "El campo racks es obligatorio.";
    public static final String ERROR_RACKS_WAREHOUSE_INT = "El campo rack debe ser un numero entero.";
    public static final String ERROR_CONDICION_WAREHOUSE_REQUIRED = "El campo condicion es obligatorio.";
    public static final String ERROR_RACKS_FIL_REQUIRED = "El campo de numero de filas del rack es obligatorio";
    public static final String ERROR_RACKS_FIL_INT = "El campo de numero de filas del rack debe ser un numero entero";
    public static final String ERROR_RACKS_COL_REQUIRED = "El campo de numero de columnas del rack es obligatorio";
    public static final String ERROR_RACKS_COL_INT = "El campo de numero de columnas del rack debe ser un numero entero";
    public static final String ERROR_DESC_WAREHOUSE_REQUIRED = "El campo descripcion es obligatorio.";

    /*END*/
    /*CLIENT MODULE MESSAGES*/
    public static final String MESSAGE_NEW_CLIENT_TITLE = "Mensaje de inserción de cliente";
    public static final String MESSAGE_LOAD_NEW_CLIENTS = "Se realizó la carga masiva de clientes correctamente.";
    public static final String MESSAGE_NEW_CLIENT_CREATED = "Se registró correctamente el cliente.";
    public static final String MESSAGE_DELETE_CLIENT_TITLE = "Mensaje de eliminación de cliente";
    public static final String MESSAGE_DELETE_CLIENT = "¿Está seguro de eliminar el cliente seleccionado?";
    public static final String ERROR_NEW_CLIENT_TITLE = "Error al agregar nuevo cliente";
    public static final String ERROR_NAME_LESS_2 = "El campo Nombre debe tener por lo menos 2 caracteres.";
    public static final String ERROR_NAME_MORE_60 = "El campo Nombre no debe contener más de 60 caracteres.";
    public static final String ERROR_RUC_NOT_11 = "El campo RUC debe contener exactamente 11 caracteres.";
    public static final String ERROR_RUC_NOT_NUMERIC = "El campo RUC debe contener caracteres numéricos solamente.";
    public static final String ERROR_NEW_LOCAL_TITLE = "Error al agregar nuevo local";
    public static final String ERROR_NOT_CSV = "El archivo seleccionado no es un archivo CSV.";
    public static final String ERROR_FILE_UPLOAD_TITLE = "Error al cargar el archivo";
    public static final String MESSAGE_NEW_LOCAL_TITLE = "Mensaje de inserción de local.";
    public static final String MESSAGE_NEW_LOCAL_CREATED = "Se registró correctamente el local.";
    public static final String MESSAGE_DELETE_LOCAL_TITLE = "Mensaje de eliminación de local";
    public static final String MESSAGE_DELETE_LOCAL = "¿Está seguro de eliminar el local seleccionado?";
    public static final String ERROR_ADDRESS_REQUIRED = "El campo Dirección es requerido.";
    public static final String ERROR_LATITUDE_REQUIRED = "El campo Latitud es requerido.";
    public static final String ERROR_LATITUDE_OUT_LIMITS = "El campo Latitud debe estar entre -11.5 y -12.5 grados.";
    public static final String ERROR_LONGITUDE_REQUIRED = "El campo Longitud es requerido.";
    public static final String ERROR_LONGITUDE_OUT_LIMITS = "El campo Longitud debe estar entre -76.8 y -77.2 grados.";
    public static final String ERROR_LATITUDE_NOT_FLOAT = "El campo Latitud debe ser un número de punto flotante.";
    public static final String ERROR_LONGITUDE_NOT_FLOAT = "El campo Longitud debe ser un número de punto flotante.";
    /*END*/

    /*TRANSPORT UNIT MESSAGES*/
    public static final String MESSAGE_TRANSPORT_UNIT_TITLE = "Mensaje de inserción de unidad de transporte";
    public static final String MESSAGE_NEW_TRANSPORT_UNIT_CREATED = "Se registró o modifico correctamente la unidad de transporte.";
    public static final String ERROR_MESSAGE_TRANSPORT_UNIT = "Error  al agregar o modificar una nueva unidad de transporte";
    public static final String MESSAGE_DELETE_TRANSPORT_UNIT_TITLE = "Mensaje de eliminacion de unidad de transporte";
    public static final String MESSAGE_DELETE_TRANSPORT_UNIT = "Se elimino correctamente el cliente";
    public static final String MESSAGE_CONFIRM_DELETE_TRANSPORT_UNIT = "¿Está seguro de eliminar la unidad de transporte seleccionada?";
    public static final String MESSAGE_FILE_TRANSPORT_UNIT_TITLE = "Carga Masiva";
    public static final String MESSAGE_FILE_TRANSPORT_UNIT = "Se realizo la carga de forma correcta";
    public static final String MESSAGE_FILE_ERROR_TRANSPORT_UNIT = "No se pudo realizar la carga masiva";
    public static final String ERROR_PLATE_REQUIRED = "El campo Placa es requerido.";
    public static final String ERROR_PLATE_NOT_6 = "El campo Placa debe contener exactamanete 6 caracteres.";
    public static final String ERROR_TRANSPORTIST_REQUIRED = "El campo Transportista es requerido.";
    public static final String ERROR_TRANSPORTIST_MORE_60 = "El campo Transportista no debe contener mas de 60 caracteres.";
    public static final String ERROR_TRANSPORT_TYPE_REQUIRED = "El campo Tipo es requerido.";
    public static final String ERROR_NEW_TRANSPORT_UNIT_TITLE = "Unidad de Transporte";

    /*END*/
    /*ALGORITHM EXECUTION MESSAGES*/
    public static final String BAD_PARAMETERS="Debe ingresar solo números en ambos campos horas y minutos.";
    public static final String BAD_PARAMETERS_TITLE="Parámetros del cálculo de rutas";   
    public static final String ALGORITHM_TITLE="Generación de rutas de despacho";
    public static final String ALGORITHM_ERROR="Asegúrese de tener pallets, unidades de transporte y pedidos registrados antes de generar las rutas.";
    public static final String ALGORITHM_ERROR_TITLE="Error en la ejecución";
    public static final String ALGORITHM_SUCCESS="La generación de rutas de despacho ha terminado.";
    public static final String ALGORITHM_SUCCESS_TITLE="Generación de rutas de despacho";
    public static final String ALGORITHM_NO_ROUTES_FOUND="No se han encontrado rutas que cumplan con las restricciones dadas.";
    public static final String ALGORITHM_NO_ROUTES_FOUND_TITLE="Fin de la ejecución";
    public static final String PARAMETERS_OUT_OF_BOUNDS="Solo puede ingresar entre 0 y 24 horas y entre 0 y 60 minutos";
    public static final String PARAMETERS_OUT_OF_BOUNDS_TITLE="Parámetros del cálculo de rutas";
    public static final String ALGORITHM_RUN_MESSAGE="Genere al menos un conjunto de rutas antes de hacer los despachos.";
    public static final String ALGORITHM_RUN_MESSAGE_TITLE="Generación de rutas de despacho";
    public static final String ALGORITHM_ZERO_TIME="Debe ingresar un tiempo mayor que cero.";
    public static final String ALGORITHM_ZERO_TIME_TITLE="Parámetros del cálculo de rutas";
    
    
    
    
    
    /*END*/
    /*ORDERS MESSAGES*/
    public static final String MESSAGE_CONFIRM_DELETE_ORDER = "¿Está seguro de eliminar el pedido seleccionado?";
    public static final String MESSAGE_DELETE_ORDER_TITLE = "Mensaje de anulacion de Pedido";
    public static final String MESSAGE_DELETE_ORDER = "El pedido ha sido anulado";
    public static final String MESSAGE_CREATE_ORDER = "El pedido ha sido creado";
    public static final String MESSAGE_CREATE_ORDER_TITLE = "Creacion de Pedido";
    public static final String ERROR_CREATE_ORDER_LESS_0 = "La cantidad seleccionada no puede ser menor a cero";
    public static final String ERROR_CREATE_ORDER_NO_CLIENTE = "Debe seleccionar un cliente.";
    public static final String ERROR_CREATE_ORDER_NO_PRODUCTS = "Debe seleccionar al menos un producto.";
    public static final String ERROR_CREATE_ORDER = "Error al registrar nuevo Pedido.";
    public static final String LOAD_ORDER_TITLE = "Carga Masiva de Pedidos";
    public static final String LOAD_ORDER_ERROR = "No se puedo realizar la carga masiva de Pedidos";
    public static final String LOAD_ORDER_SUCCESS = "Se realizo la carga masiva de Pedidos de forma correcta";
    public static final String DEVOLUTION_ORDER_SUCCESS = "Se realizo la devolucion con exito.";
    public static final String DEVOLUTION_ORDER_ERROR = "No se pudo realizar la devolucion del pedido parcial.";
    public static final String DEVOLUTION_ORDER_TITLE = "Devolucion de Orden Parcial.";
    /*END*/

    /*PRODUCT TYPE MESSAGES*/
    public static final String MESSAGE_TPRODUCT_TITLE = "Mensaje de inserción de tipo de producto";
    public static final String MESSAGE_TPRODUCT_CREATED = "Se registró correctamente el tipo de producto.";
    /*END*/

    /*PRODUCT MESSAGES*/
    public static final String ERROR_CANTIDAD_PROD_REQUIRED = "El campo Cantidad es obligatorio.";
    public static final String ERROR_CANTIDAD_PROD_INT = "El campo cantidad debe ser un numero entero.";
    public static final String ERROR_CANTIDADXPALLET_REQUIRED = "El campo de cantidad por pallet es obligatorio.";
    public static final String ERROR_CANTIDADXPALLET_INT = "El campo de cantidad por pallet debe ser un numero entero.";
    public static final String ERROR_PRODUCT_NAME_LESS_2 = "El campo Nombre de Producto debe tener por lo menos 2 caracteres.";
    public static final String ERROR_DESC_PROD_REQUIRED = "El campo Descripcion del Producto es obligatorio.";
    public static final String ERROR_CONDITION_PROD_REQUIRED = "El campo condicion es obligatorio";
    public static final String MESSAGE_NEW_PRODUCT_CREATED = "Se registró correctamente el producto.";
    public static final String MESSAGE_NEW_PRODUCT_TITLE = "Mensaje de inserción de producto";

    public static final String ERROR_PESO_PROD_REQUIRED = "El campo peso es obligatorio.";
    public static final String ERROR_PESO_PROD_DOUBLE = "El campo peso debe ser un numero.";

    /*END*/
    /*KARDEX MESSAGES*/
    public static final String ERROR_KARDEX_TITLE = "Error en reporte de Kardex";
    public static final String ERROR_DATE_INI = "Seleccione fecha inicial";
    public static final String ERROR_DATE_END = "Seleccione fecha final";
    public static final String ERROR_DATE = "La fecha inicial debe ser anterior a la final";
    /*END*/

    /*DELIVERY MESSAGES*/
    public static final String DELIVERY_TITLE = "Despacho";
    public static final String DELIVERY_SUCCESS = "Se realizo el despacho de manera correcta.";
    public static final String DELIVERY_ERROR = "Hubo un error al realizar el despacho.";
    /*END*/

    /*Security log*/
    public static final String ERROR_NO_INITIAL_DATE = "Se necesita la fecha inicial";
    public static final String ERROR_NO_FINAL_DATE = "Se necesita la fecha final";
    public static final String ERROR_DATE_PROVIDED="La fecha inicial no puede ser mayor a la fecha final.";
    /*END*/
    
    /* Kardex States */
    public static final String MESSAGE_KARDEX_IN_INTERNMENT = "Ingreso por internamiento";
    public static final String MESSAGE_KARDEX_IN_MOVEMENT = "Ingreso por movimiento";
    public static final String MESSAGE_KARDEX_IN_ADJUST = "Ingreso por ajuste";
    public static final String MESSAGE_KARDEX_IN_RETURN = "Ingreso por devolucion";
    public static final String MESSAGE_KARDEX_OUT_DELIVERY = "Salida por despacho";
    public static final String MESSAGE_KARDEX_OUT_SMASH = "Salida por rotura";
    public static final String MESSAGE_KARDEX_OUT_ADJUST = "Salida por ajuste";
    public static final String MESSAGE_KARDEX_OUT_MOVEMENT = "Salida por movimiento";
    /*END*/
}
