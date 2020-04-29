package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BD.BD;

public class EquipeDeSaude {

    private int id;
    private String nome_equipe;
    private String telefone_equipe;
    private String endereco_equipe;
    private String usuario;
    private String senha;

    public EquipeDeSaude() {
    }

    public EquipeDeSaude(String nome_equipe, String telefone_equipe, String endereco_equipe, String usuario, String senha) {
        this.nome_equipe = nome_equipe;
        this.telefone_equipe = telefone_equipe;
        this.endereco_equipe = endereco_equipe;
        this.usuario = usuario;
        this.senha = senha;
    }

    public static EquipeDeSaude realizarLogin(String usuarioLogin, String senhaLogin) {
        // DECLARAÇÃO DE VARIÁVEIS
        Connection conn = null;
        PreparedStatement consultaEquipeDeSaude = null;
        ResultSet rs = null;
        EquipeDeSaude equipeDeSaude = null;

        // CONSULTA NO BANCO DE DADOS O USUÁRIO E A SENHA
        try {
            conn = BD.getConnection();
            consultaEquipeDeSaude = conn.prepareStatement("SELECT id, nome_equipe, telefone_equipe, endereco_equipe FROM EquipeDeSaude WHERE usuario = ? AND senha = ?");
            consultaEquipeDeSaude.setString(1, usuarioLogin);
            consultaEquipeDeSaude.setString(2, senhaLogin);

            rs = consultaEquipeDeSaude.executeQuery();
            // PREENCHE A CLASSE DE PACIENTE SE ENCONTROU O USUÁRIO
            if (rs.next()) {
                equipeDeSaude = new EquipeDeSaude();

                equipeDeSaude.setId(rs.getInt(1));
                equipeDeSaude.setNome_equipe(rs.getString(2));
                equipeDeSaude.setTelefone_equipe(rs.getString(3));
                equipeDeSaude.setEndereco_equipe(rs.getString(4));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            BD.closeResultSet(rs);
            BD.closePreparedStatement(consultaEquipeDeSaude);
            BD.closeConnection();
        }

        if (equipeDeSaude != null) {
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Usuário ou senha inválidos. Tente novamente!");
        }

        return equipeDeSaude;
    }

    public List<Paciente> visualizarPacientes() {
        // DECLARAÇÃO DE VARIÁVEIS
        Connection conn = null;
        PreparedStatement visualizarPaciente = null;
        ResultSet rs = null;
        List<Paciente> pacientes = new ArrayList<>();

        // CONSULTANDO OS PACIENTES NO BANCO DE DADOS
        try {
            conn = BD.getConnection();
            visualizarPaciente = conn.prepareStatement("SELECT nome_paciente,telefone_paciente,cpf_paciente,cidade_paciente,sintomas_paciente " +
                    "FROM paciente");

            rs = visualizarPaciente.executeQuery();

            // PREENCHENDO A LISTA COM TODAS OS PACIENTES QUE RETORNARAM DO BANCO DE DADOS
            while (rs.next()) {
                pacientes.add(new Paciente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

            // AVISA SE NÃO POSSUIR NENHUM PACIENTE
            if (pacientes.isEmpty()) {
                System.out.println("Não há nenhum paciente cadastrado!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            BD.closePreparedStatement(visualizarPaciente);
            BD.closeConnection();
        }

        return pacientes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Mensagem> visualizarMensagens(String tipoDestinatario) {
        // DECLARAÇÃO DE VARIÁVEIS
        Connection conn = null;
        PreparedStatement consultaMensagens = null;
        ResultSet rs = null;
        List<Mensagem> mensagens = new ArrayList<>();

        // CONSULTANDO AS MENSAGENS DESSA EQUIPE NO BANCO DE DADOS
        try {
            conn = BD.getConnection();
            consultaMensagens = conn.prepareStatement("SELECT M.ID, M.assunto, M.conteudo, E.nome_equipe, P.nome_paciente " +
                    "FROM mensagens M " +
                    "INNER JOIN equipedesaude E on M.idequipe = E.id " +
                    "INNER JOIN paciente P on M.cpfPaciente = P.cpf_paciente " +
                    "WHERE idEquipe = ? " +
                    "AND tipoDestinatario = ?" +
                    "ORDER BY M.ID");

            consultaMensagens.setInt(1, this.getId());
            consultaMensagens.setString(2, tipoDestinatario);
            rs = consultaMensagens.executeQuery();

            // PREENCHENDO A LISTA COM TODAS AS MENSAGENS QUE RETORNARAM DO BANCO DE DADOS
            while (rs.next()) {
                mensagens.add(new Mensagem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), "P"));
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
