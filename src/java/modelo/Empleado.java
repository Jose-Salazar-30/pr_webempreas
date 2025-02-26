package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

public class Empleado extends Persona{
    private String codigo;
    private int id_puesto;
    private Conexion cn;
    
    public Empleado() {
    }

    public Empleado(String codigo, int id_puesto, int id, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(id, nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.codigo = codigo;
        this.id_puesto = id_puesto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getId_puesto() {
        return id_puesto;
    }

    public void setId_puesto(int id_puesto) {
        this.id_puesto = id_puesto;
    }
    public DefaultTableModel leer(){
        DefaultTableModel tabla = new DefaultTableModel();
        try {
            cn = new Conexion();
            cn.abrirCoenxion();
            String query = "SELECT e.id_empleado as id,e.codigo,e.nombres,e.apellidos,e.direccion,e.telefono,e.fecha_nacimiento, p.puesto,p.id_puesto FROM empleado as e inner join puesto as p on e.id_puesto = p.id_puesto;";
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
            String encabezado[] = {"id","codigo","nombres","apellidos","direccion","telefono","nacimiento","puesto","id_puesto"};
            tabla.setColumnIdentifiers(encabezado);
            String datos[] = new String[9];
            while (consulta.next()) {
                //System.out.println("Id: " + consulta.getString("id"));
                datos[0] = consulta.getString("id");
                datos[1] = consulta.getString("codigo");
                datos[2] = consulta.getString("nombres");
                datos[3] = consulta.getString("apellidos");
                datos[4] = consulta.getString("direccion");
                datos[5] = consulta.getString("telefono");
                datos[6] = consulta.getString("fecha_nacimiento");
                datos[7] = consulta.getString("puesto");
                datos[8] = consulta.getString("id_puesto");
                tabla.addRow(datos);
            }
            cn.cerrarConexion();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return tabla;
    }
    
    @Override
    public int agregar(){
        int retorno = 0;
        try {
            cn = new Conexion();
            PreparedStatement parametro;
            String query = "INSERT INTO empleado(codigo,nombres,apellidos,direccion,telefono,fecha_nacimiento,id_puesto) VALUES(?,?,?,?,?,?,?);";
            cn.abrirCoenxion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getCodigo());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, this.getFecha_nacimiento());
            parametro.setInt(7, getId_puesto());
            
            retorno = parametro.executeUpdate();
            
            cn.cerrarConexion();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            retorno = 0;
        }
        return retorno;
    }
    
    @Override
    public int modificar(){
        int retorno = 0;
        try {
            cn = new Conexion();
            PreparedStatement parametro;
            String query = "update empleado set codigo=?,nombres=?,apellidos=?,direccion=?,telefono=?,fecha_nacimiento=?,id_puesto=? where id_empleado=?;";
            cn.abrirCoenxion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getCodigo());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, this.getFecha_nacimiento());
            parametro.setInt(7, getId_puesto());
            parametro.setInt(8, getId());
            
            retorno = parametro.executeUpdate();
            
            cn.cerrarConexion();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            retorno = 0;
        }
        return retorno;
    }
    
    @Override
    public int eliminar(){
        int retorno = 0;
        try {
            cn = new Conexion();
            PreparedStatement parametro;
            String query = "delete from empleado where id_empleado=?;";
            cn.abrirCoenxion();
            parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());
            
            retorno = parametro.executeUpdate();
            
            cn.cerrarConexion();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            retorno = 0;
        }
        return retorno;
    }
}
