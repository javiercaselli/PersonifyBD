package com.example.entidades;

import com.example.interfaces.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return null;
    }

    @Override
    public long create(Individuo model) throws SQLException {
        return 0;
    }

    @Override
    public int update(Individuo object) throws SQLException {
        return 0;
    }

    @Override
    public boolean delete(long id) throws SQLException {
        return false;
    }
}
