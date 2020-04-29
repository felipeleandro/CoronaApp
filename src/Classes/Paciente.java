package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BD.BD;

public class Paciente {

    private String nome_paciente;
    private String telefone_paciente;
    private String usuario;
    private String senha;
    private String cpf;
    private String cidade_paciente;
    private String estado_paciente;
    private String sintomas_paciente;

    public Paciente() {

    }

    public Paciente(String nome_paciente, String telefone_paciente, String cpf, String cidade_paciente, String sintomas_paciente) {
        this.nome_paciente = nome_paciente;
        this.telefone_paciente = telefone_paciente;
        this.cpf = cpf;
        this.cidade_paciente = cidade_paciente;
        this.sintomas_paciente = sintomas_paciente;
    }

    public static Paciente realizarLogin(String usuarioLogin, String senhaLogin) {
        // DECLARAÇÃO DE VARIÁVEIS
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement consultaPaciente = null;
        Paciente paciente = null;

        // CONSULTA NO BANCO DE DADOS O USUÁRIO E A SENHA
        try {
            conn = BD.getConnection();
            consultaPaciente = conn.prepareStatement("SELECT * FROM Paciente WHERE usuario = ? AND senha = ?");
            consultaPaciente.setString(1, usuarioLogin);
            consultaPaciente.setString(2, senhaLogin);

            rs = consultaPaciente.executeQuery();
            // PREENCHE A CLASSE DE PACIENTE SE ENCONTROU O USUÁRIO
            if (rs.next()) {
                paciente = new Paciente();

                paciente.setNome_paciente(rs.getString(1));
                paciente.setTelefone_paciente(rs.getString(2));
                paciente.setCpf(rs.getString(4));
                paciente.setCidade_paciente(rs.getString(5));
                paciente.setEstado_paciente(rs.getString(6));
                paciente.setSintomas_paciente(rs.getString(7));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            BD.closeResultSet(rs);
            BD.closePreparedStatement(consultaPaciente);
            BD.closeConnection();
        }

        if (paciente != null) {
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Usuário ou senha inválidos. Tente novamente!");
        }

        return paciente;
    }

    public static Paciente buscaPaciente(String cpf) {
        // DECLARAÇÃO DE VARIÁVEIS
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement consultaPaciente = null;
        Paciente paciente = null;

        // CONSULTA NO BANCO DE DADOS O CPF
        try {
            conn = BD.getConnection();
            consultaPaciente = conn.prepareStatement("SELECT * FROM Paciente WHERE cpf_paciente = ? ");
            consultaPaciente.setString(1, cpf);

            rs = consultaPaciente.executeQuery();

            if (rs.next()) {
                paciente = new Paciente();

                paciente.setNome_paciente(rs.getString(1));
            }else {
                System.out.println("Paciente não encontrado");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            BD.closeResultSet(rs);
            BD.closePreparedStatement(consultaPaciente);
            BD.closeConnection();
        }
        return paciente;
    }

    public String toString() {
        return "Nome: " + nome_paciente + " Telefone: " + telefone_paciente + " CPF: " + cpf
                + " Cidade: " + cidade_paciente + " Sintomas: " + sintomas_paciente;
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

    public List<Mensagem> visualizarMensagens(String tipoDestinatario) {
        // DECLARAÇÃO DE VARIÁVEIS
        Connection conn = null;
        PreparedStatement consultaMensagens = null;
        ResultSet rs = null;
        List<Mensagem> mensagens = new ArrayList<>();

        // CONSULTANDO AS MENSAGENS DESSE PACIENTE NO BANCO DE DADOS
        try {
            conn = BD.getConnection();
            consultaMensagens = conn.prepareStatement("SELECT M.ID, M.assunto, M.conteudo, E.nome_equipe, P.nome_paciente " +
                    "FROM mensagens M " +
                    "INNER JOIN equipedesaude E on M.idequipe = E.id " +
                    "INNER JOIN paciente P on M.cpfPaciente = P.cpf_paciente " +
                    "WHERE M.cpfPaciente = ? " +
                    "AND M.tipoDestinatario = ?" +
                    "ORDER BY M.ID");

            consultaMensagens.setString(1, this.getCpf());
            consultaMensagens.setString(2, tipoDestinatario);
            rs = consultaMensagens.executeQuery();

            // PREENCHENDO A LISTA COM TODAS AS MENSAGENS QUE RETORNARAM DO BANCO DE DADOS
            while (rs.next()) {
                mensagens.add(new Mensagem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), "E"));
            }

            // AVISA SE NÃO TIVER NENHUMA MENSAGEM
            if (mensagens.isEmpty()) {
                System.out.println("Você não possui nenhuma mensagem!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            BD.closePreparedStatement(consultaMensagens);
            BD.closeConnection();
        }

        return mensagens;
    }

}
