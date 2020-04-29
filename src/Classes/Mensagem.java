package Classes;

import BD.BD;

import java.sql.*;

public class Mensagem {

    private int id;
    private String assunto;
    private String conteudo;
    private String nomeEquipe;
    private String nomePaciente;
    private String tipoDestinatario;

    public Mensagem(String assunto, String conteudo, String nomeEquipe, String nomePaciente, String tipoDestinatario) {
        this.assunto = assunto;
        this.conteudo = conteudo;
        this.nomeEquipe = nomeEquipe;
        this.nomePaciente = nomePaciente;
        this.tipoDestinatario = tipoDestinatario;
    }

    public Mensagem(int id, String assunto, String conteudo, String nomeEquipe, String nomePaciente, String tipoDestinatario) {
        this.id = id;
        this.assunto = assunto;
        this.conteudo = conteudo;
        this.nomeEquipe = nomeEquipe;
        this.nomePaciente = nomePaciente;
        this.tipoDestinatario = tipoDestinatario;
    }

    private int consultaCodigoEquipe() {
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement consultaEquipeDeSaude = null;
        int idEquipe = 0;

        // CONSULTANDO A EQUIPE PARA VER SE EXISTE
        try {
            conn = BD.getConnection();
            consultaEquipeDeSaude = conn.prepareStatement("SELECT ID FROM EquipeDeSaude WHERE nome_equipe = ?");
            consultaEquipeDeSaude.setString(1, this.nomeEquipe);

            rs = consultaEquipeDeSaude.executeQuery();

            // SE EXISTE, RETORNA O CÓDIGO DA EQUIPE, SENÃO CANCELA O ENVIO DA MENSAGEM
            if (rs.next()) {
                idEquipe = rs.getInt(1);
            } else {
                System.out.println("Não foi possível enviar a mensagem. Esse destinatário não existe.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            BD.closeResultSet(rs);
            BD.closePreparedStatement(consultaEquipeDeSaude);
            BD.closeConnection();
        }

        return idEquipe;
    }

    public String getTipoDestinatario() {
        return tipoDestinatario;
    }

    public void setTipoDestinatario(String tipoDestinatario) {
        this.tipoDestinatario = tipoDestinatario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toStringEnviadas() {
        return String.format("Mensagem nº%d - " +
                "Assunto: %s - " +
                "Conteúdo: %s - " +
                "Nome Destinatário: %s ", id, assunto, conteudo, tipoDestinatario == "E" ? nomeEquipe : nomePaciente);
    }

    public String toStringRecebidas() {
        return String.format("Mensagem nº%d - " +
                "Assunto: %s - " +
                "Conteúdo: %s - " +
                "Nome Remetente: %s ", id, assunto, conteudo, tipoDestinatario == "E" ? nomeEquipe : nomePaciente);
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public void enviarMensagem() {
        Connection conn = null;
        PreparedStatement novaMensagem = null;

        try {
            int idEquipe = consultaCodigoEquipe();
            String cpfPaciente = consultaCPFPaciente();

            if (idEquipe != 0 && cpfPaciente != null) {
                conn = BD.getConnection();
                novaMensagem = conn.prepareStatement("INSERT INTO Mensagens"
                        + "(assunto, conteudo, idEquipe, cpfPaciente, tipoDestinatario)"
                        + "VALUES (?, ?, ?, ?, ?)");

                novaMensagem.setString(1, this.assunto);
                novaMensagem.setString(2, this.conteudo);
                novaMensagem.setInt(3, idEquipe);
                novaMensagem.setString(4, cpfPaciente);
                novaMensagem.setString(5, tipoDestinatario);
                novaMensagem.executeUpdate();

                System.out.println("Mensagem enviada com sucesso!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            BD.closePreparedStatement(novaMensagem);
            BD.closeConnection();
        }
    }

    private String consultaCPFPaciente() {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement consultaPaciente = null;
        String cpfPaciente = null;

        // CONSULTANDO O PACIENTE PARA VER SE EXISTE
        try {
            conn = BD.getConnection();
            consultaPaciente = conn.prepareStatement("SELECT cpf_paciente FROM Paciente WHERE nome_paciente = ?");
            consultaPaciente.setString(1, this.nomePaciente);

            rs = consultaPaciente.executeQuery();

            // SE EXISTE, RETORNA O CPF DO PACIENTE, SENÃO CANCELA O ENVIO DA MENSAGEM
            if (rs.next()) {
                cpfPaciente = rs.getString(1);
            } else {
                System.out.println("Não foi possível enviar a mensagem. Esse destinatário não existe.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            BD.closeResultSet(rs);
            BD.closePreparedStatement(consultaPaciente);
            BD.closeConnection();
        }

        return cpfPaciente;
    }

}
