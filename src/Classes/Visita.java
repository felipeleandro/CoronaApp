package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import BD.BD;

public class Visita {

    private Date dataVisita;
    private String endereco;
    private String cpf;
    private EquipeDeSaude equipeDeSaude;
    private Paciente paciente;

    public  Visita(Date dataVisita, String endereco, String cpf, EquipeDeSaude equipeDeSaude, Paciente paciente) {
        this.dataVisita = dataVisita;
        this.endereco = endereco;
        this.cpf = cpf;
        this.equipeDeSaude = equipeDeSaude;
        this.paciente = paciente;
    }

    public Date getDataVisita() {
        return dataVisita;
    }

    public void setDataVisita(Date dataVisita) {
        this.dataVisita = dataVisita;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void cadastraVisita() {
        // DECLARAÇÃO DE VARIÁVEIS
        Connection conn = null;
        PreparedStatement novaVisita = null;
        java.sql.Date sqlDate = new java.sql.Date(this.dataVisita.getTime());

        // INSERINDO A VISITA NO BANCO DE DADOS
        try {
            conn = BD.getConnection();
            novaVisita = conn.prepareStatement("INSERT INTO visita"
                    + "(data_visita,endereco_consulta,cpf_paciente )"
                    + "VALUES (?, ?, ?)");

            novaVisita.setDate(1, sqlDate);
            novaVisita.setString(2, this.endereco);
            novaVisita.setString(3, this.cpf);
            novaVisita.executeUpdate();

            System.out.println("Visita cadastrada com sucesso!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            BD.closePreparedStatement(novaVisita);
            BD.closeConnection();
        }

        enviaNotificacao_Paciente();
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void enviaNotificacao_Paciente()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        String assuntoVisita = "Agendamento da visita Coronavírus";
        String conteudoVisita = "Sua visita foi marcada para o dia " + sdf.format(this.getDataVisita()) + " no endereço " + this.getEndereco();

        Mensagem mensagem = new Mensagem(assuntoVisita, conteudoVisita, equipeDeSaude.getNome_equipe(), paciente.getNome_paciente(), "P");
        mensagem.enviarMensagem();
    }
}
