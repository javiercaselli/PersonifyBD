package com.example.entidades;

import com.example.interfaces.CRUD;

import java.sql.*;
import java.util.ArrayList;

public class IndividuoService implements CRUD<Individuo> {
    private Connection con = null;
    // Contructor
    public IndividuoService(Connection con) {
        this.con = con;
    }

    @Override
    public ArrayList<Individuo> requestAll(String sortedBy, String direction) throws SQLException {
        // Valor de retorno
        ArrayList<Individuo> listaIndividuos = new ArrayList<>();

        // Consulta
        String sql = "SELECT * FROM individuos ORDER BY " + sortedBy + " " + direction;

        // Genera consulta precompilada (PreparedStatement)
        PreparedStatement ps = con.prepareStatement(sql);
        // Ejecuto colsulta
        ResultSet rs = ps.executeQuery();
        // Recorre resultado y va almacenando en el arraylist
        while (rs.next()) {
            Individuo individuo = new Individuo();
            individuo.setId(rs.getLong("id"));
            individuo.setNombre(rs.getString("nombre"));
            individuo.setApellido1(rs.getString("apellido1"));
            individuo.setApellido2(rs.getString("apellido2"));
            individuo.setProgenitor1(rs.getLong("progenitor1"));
            individuo.setProgenitor2(rs.getLong("progenitor2"));
            listaIndividuos.add(individuo);
        }
        return listaIndividuos;
    }

    @Override
    public Individuo requestById(long id) throws SQLException {
        Individuo individuo = null; // Valor de retorno
        String sql = "SELECT * FROM individuos WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            individuo = new Individuo();
            individuo.setId(rs.getLong("id"));
            individuo.setNombre(rs.getString("nombre"));
            individuo.setApellido1(rs.getString("apellido1"));
            individuo.setApellido2(rs.getString("apellido2"));
            individuo.setProgenitor1(rs.getLong("progenitor1"));
            individuo.setProgenitor2(rs.getLong("progenitor2"));
        }

        return individuo;
    }

    @Override
    public long create(Individuo model) throws SQLException {
        long id = 0; // Valor de retorno
        
        // sentencia de inserción
        String sql = "INSERT INTO individuos (nombre, apellido1, apellido2, progenitor1, progenitor2) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, model.getNombre());
        ps.setString(2, model.getApellido1());
        ps.setString(3, model.getApellido2());
        ps.setObject(4, model.getProgenitor1());
        ps.setObject(5, model.getProgenitor2());
        id = ps.executeUpdate();
        
        return id;
    }

    @Override
    public int update(Individuo object) throws SQLException {
        return 0;
    }

    @Override
    public boolean delete(long id) throws SQLException {
        return false;
    }

    public ArrayList<Individuo> requestByProgenitor(Individuo individuoPadre) throws SQLException {
        ArrayList<Individuo> listaIndividuos = new ArrayList<>();
        String sql = "SELECT * FROM individuos WHERE progenitor1 = ? OR progenitor2 = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, individuoPadre.getId());
        ps.setLong(2, individuoPadre.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Individuo individuoHijo = new Individuo();
            individuoHijo.setId(rs.getLong("id"));
            individuoHijo.setNombre(rs.getString("nombre"));
            individuoHijo.setApellido1(rs.getString("apellido1"));
            individuoHijo.setApellido2(rs.getString("apellido2"));
            individuoHijo.setProgenitor1(rs.getLong("progenitor1"));
            individuoHijo.setProgenitor2(rs.getLong("progenitor2"));
            listaIndividuos.add(individuoHijo);
        }
        return listaIndividuos;
    }
}
