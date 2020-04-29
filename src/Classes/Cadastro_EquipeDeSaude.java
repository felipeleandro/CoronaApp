package Classes;

import java.sql.*;
import java.util.Scanner;

import BD.BD;

public class Cadastro_EquipeDeSaude {

    private String nome_equipe;
    private String telefone_equipe;
    private String endereco_equipe;
    private String usuario;
    private String senha;

    public Cadastro_EquipeDeSaude(String nome_equipe, String telefone_equipe, String endereco_equipe, String usuario, String senha) {
        this.nome_equipe = nome_equipe;
        this.telefone_equipe = telefone_equipe;
        this.endereco_equipe = endereco_equipe;
        this.usuario = usuario;
        this.senha = senha;
    }

    public static Boolean consultaEquipeDeSaudeBD(String usuarioLogin, String senhaLogin) {
        Connection conn = null;
        PreparedStatement consultaEquipeDeSaude = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);

        // CONSULTANDO SE JÁ EXISTE ESSA EQUIPE NO BANCO DE DADOS
        try {
            conn = BD.getConnection();
            consultaEquipeDeSaude = conn.prepareStatement("SELECT * FROM EquipeDeSaude WHERE usuario = ?");
            consultaEquipeDeSaude.setString(1, usuarioLogin);
            rs = consultaEquipeDeSaude.executeQuery();

            // SE RETORNOU DADOS, SIGNIFICA QUE ESSA EQUIPE DE SAÚDE JÁ ESTÁ CADASTRADO.
            if (rs.next()) {
                System.out.println("Esse usuário já está cadastrado no nosso aplicativo.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            BD.closeResultSet(rs);
            BD.closePreparedStatement(consultaEquipeDeSaude);
            BD.closeConnection();
        }

        return false;
    }

    private static void cadastraEquipeDeSaudeBD(String nomeEquipeDeSaude, String telefoneEquipeDeSaude, String enderecoEquipeDeSaude, String usuarioLogin, String senhaLogin) {
        Connection conn = null;
        PreparedStatement novaEquipeDeSaude = null;

        // INSERINDO A EQUIPE DE SAÚDE NO BANCO DE DADOS
        try {
            conn = BD.getConnection();
            novaEquipeDeSaude = conn.prepareStatement("INSERT INTO EquipeDeSaude"
                    + "(nome_equipe, telefone_equipe, endereco_equipe, usuario, senha)"
                    + "VALUES (?, ?, ?, ?, ?)");

            novaEquipeDeSaude.setString(1, nomeEquipeDeSaude);
            novaEquipeDeSaude.setString(2, telefoneEquipeDeSaude);
            novaEquipeDeSaude.setString(3, enderecoEquipeDeSaude);
            novaEquipeDeSaude.setString(4, usuarioLogin);
            novaEquipeDeSaude.setString(5, senhaLogin);
            novaEquipeDeSaude.executeUpdate();

            System.out.println("Equipe de saúde cadastrada com sucesso!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            BD.closePreparedStatement(novaEquipeDeSaude);
            BD.closeConnection();
        }
    }

    public String getNome_equipe() {
        return nome_equipe;
    }

    public void setNome_equipe(String nome_equipe) {
        this.nome_equipe = nome_equipe;
    }

    public String getTelefone_equipe() {
        return telefone_equipe;
    }

    public void setTelefone_equipe(String telefone_equipe) {
        this.telefone_equipe = telefone_equipe;
    }

    public String getEndereco_equipe() {
        return endereco_equipe;
    }

    public void setEndereco_equipe(String endereco_equipe) {
        this.endereco_equipe = endereco_equipe;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void criarNovaEquipe() {
        cadastraEquipeDeSaudeBD(this.nome_equipe, this.telefone_equipe, this.endereco_equipe, this.usuario, this.senha);
    }
}
