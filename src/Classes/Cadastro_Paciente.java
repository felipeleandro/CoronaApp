package Classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

import BD.BD;

public class Cadastro_Paciente {

    private String nome_paciente;
    private String telefone_paciente;
    private String usuario;
    private String senha;
    private String cpf;
    private String cidade_paciente;
    private String estado_paciente;
    private String sintomas_paciente;

    public Cadastro_Paciente(String nome_paciente, String telefone_paciente, String usuario, String senha, String cpf, String cidade_paciente, String estado_paciente, String sintomas_paciente) {
        this.nome_paciente = nome_paciente;
        this.telefone_paciente = telefone_paciente;
        this.usuario = usuario;
        this.senha = senha;
        this.cpf = cpf;
        this.cidade_paciente = cidade_paciente;
        this.estado_paciente = estado_paciente;
        this.sintomas_paciente = sintomas_paciente;
    }

    public static Boolean consultaPacienteBD(String usuarioLogin) {
        Connection conn = null;
        PreparedStatement consultaPaciente = null;
        ResultSet rs = null;

        // CONSULTANDO SE JÁ EXISTE ESSE USUARIO NO BANCO DE DADOS
        try {
            conn = BD.getConnection();
            consultaPaciente = conn.prepareStatement("SELECT * FROM Paciente WHERE usuario = ?");
            consultaPaciente.setString(1, usuarioLogin);
            rs = consultaPaciente.executeQuery();

            // SE RETORNOU DADOS, SIGNIFICA QUE O USUARIO JÁ ESTÁ CADASTRADO.
            if (rs.next()) {
                System.out.println("Esse usuário já está cadastrado no nosso aplicativo.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            BD.closeResultSet(rs);
            BD.closePreparedStatement(consultaPaciente);
            BD.closeConnection();
        }

        return false;
    }

    private static void cadastraPacienteBD(String nomePaciente, String telefonePaciente, String cpfPaciente,
                                           String cidadePaciente, String estadoPaciente, String codigosSintomas, String usuarioLogin,
                                           String senhaLogin) {
        Connection conn = null;
        PreparedStatement novoUsuario = null;

        // INSERINDO O USUÁRIO NO BANCO DE DADOS
        try {
            conn = BD.getConnection();
            novoUsuario = conn.prepareStatement("INSERT INTO Paciente"
                    + "(nome_paciente, telefone_paciente, cpf_paciente, cidade_paciente, estado_paciente, sintomas_paciente, usuario, senha)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            novoUsuario.setString(1, nomePaciente);
            novoUsuario.setString(2, telefonePaciente);
            novoUsuario.setString(3, cpfPaciente);
            novoUsuario.setString(4, cidadePaciente);
            novoUsuario.setString(5, estadoPaciente);
            novoUsuario.setString(6, codigosSintomas);
            novoUsuario.setString(7, usuarioLogin);
            novoUsuario.setString(8, senhaLogin);
            novoUsuario.executeUpdate();

            System.out.println("Usuário cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            BD.closePreparedStatement(novoUsuario);
            BD.closeConnection();
        }
    }

    public void criaNovoPaciente() {
        cadastraPacienteBD(this.nome_paciente, this.telefone_paciente, this.cpf, this.cidade_paciente, this.estado_paciente, this.sintomas_paciente, this.usuario, this.senha);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNome_paciente() {
        return nome_paciente;
    }

    public void setNome_paciente(String nome_paciente) {
        this.nome_paciente = nome_paciente;
    }

    public String getTelefone_paciente() {
        return telefone_paciente;
    }

    public void setTelefone_paciente(String telefone_paciente) {
        this.telefone_paciente = telefone_paciente;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCidade_paciente() {
        return cidade_paciente;
    }

    public void setCidade_paciente(String cidade_paciente) {
        this.cidade_paciente = cidade_paciente;
    }

    public String getEstado_paciente() {
        return estado_paciente;
    }

    public void setEstado_paciente(String estado_paciente) {
        this.estado_paciente = estado_paciente;
    }

    public String getSintomas_paciente() {
        return sintomas_paciente;
    }

    public void setSintomas_paciente(String sintomas_paciente) {
        this.sintomas_paciente = sintomas_paciente;
    }

    public void mostrarListaSintomas() {

    }
}
