package Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Classes.*;


public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("*************************************************************************************************************************");
        System.out.println("| Seja bem-vindo ao CoronaApp!                                                                                          |");
        System.out.println("|                                                                                                                       |");
        System.out.println("| A ideia desse aplicativo é fornecer informações sobre o COVID-19 para a população e auxiliar no atendimento do mesmo. |");
        System.out.println("| Abaixo mostramos uma lista dos sintomas causados pelo COVID-19.                                                       |");
        System.out.println("|                                                                                                                       |");
        System.out.println("| 1 - Febre                                                                                                             |");
        System.out.println("| 2 - Dor de garganta                                                                                                   |");
        System.out.println("| 3 - Tosse (Seca ou Secretiva)                                                                                         |");
        System.out.println("| 4 - Falta de ar                                                                                                       |");
        System.out.println("| 5 - Cansaço                                                                                                           |");
        System.out.println("| 6 - Coriza                                                                                                            |");
        System.out.println("| 7 - Dores de cabeça de náuseas                                                                                        |");
        System.out.println("| 8 - Vômito e diarréia                                                                                                 |");
        System.out.println("|                                                                                                                       |");
        System.out.println("| Caso você esteja com algum desses sintomas, realize o cadastro no nosso aplicativo para que possamos te atender!      |");
        System.out.println("| Caso você já esteja cadastrado no nosso aplicativo, realize o login para continuar.                                   |");
        System.out.println("*************************************************************************************************************************");

        int opcaoDigitada = -1;

        while (opcaoDigitada != 3) {
            System.out.println("1 - Realizar cadastro  ");
            System.out.println("2 - Efetuar login      ");
            System.out.println("3 - Sair do aplicativo ");
            System.out.println("-----------------------------------------");
            System.out.println("4 - Realizar cadastro (Equipe de Saúde) ");
            System.out.println("5 - Efetuar login (Equipe de Saúde)     ");

            opcaoDigitada = sc.nextInt();

            switch (opcaoDigitada) {
                // REALIZAR CADASTRO PACIENTE
                case 1: {
                    // CADASTRO
                    sc.nextLine();
                    System.out.println("CADASTRO");
                    System.out.println("Digite o seu usuário de login: ");
                    String usuarioLogin = sc.nextLine();
                    System.out.println("Digite a sua senha: ");
                    String senhaLogin = sc.nextLine();

                    // CONSULTAR SE JÁ EXISTE ESSE USUARIO NO BANCO DE DADOS
                    Boolean bUsuarioExiste = Cadastro_Paciente.consultaPacienteBD(usuarioLogin);

                    // CADASTRA SE NÃO EXISTIR
                    if (!bUsuarioExiste) {
                        System.out.println("Digite o seu nome completo: ");
                        String nomePaciente = sc.nextLine();
                        System.out.println("Digite o seu telefone: ");
                        String telefonePaciente = sc.nextLine();
                        System.out.println("Digite CPF (Somente números): ");
                        String cpfPaciente = sc.nextLine();
                        System.out.println("Digite a sua cidade: ");
                        String cidadePaciente = sc.nextLine();
                        System.out.println("Digite o seu estado: ");
                        String estadoPaciente = sc.nextLine();
                        System.out.println("Digite quais sintomas você está sentindo digitando os respectivos códigos separados por vírgula: ");
                        System.out.println("1 - Febre                     ");
                        System.out.println("2 - Dor de garganta           ");
                        System.out.println("3 - Tosse (Seca ou Secretiva) ");
                        System.out.println("4 - Falta de ar               ");
                        System.out.println("5 - Cansaço                   ");
                        System.out.println("6 - Coriza                    ");
                        System.out.println("7 - Dores de cabeça de náuseas");
                        System.out.println("8 - Vômito e diarréia         ");
                        String codigosSintomas = sc.nextLine();

                        Cadastro_Paciente novoPaciente = new Cadastro_Paciente(nomePaciente, telefonePaciente, usuarioLogin, senhaLogin, cpfPaciente, cidadePaciente, estadoPaciente, codigosSintomas);
                        novoPaciente.criaNovoPaciente();
                    }
                    break;
                }
                // EFETUAR LOGIN
                case 2: {
                    // LOGIN
                    sc.nextLine();
                    System.out.println("LOGIN");
                    System.out.println("Digite o seu usuário de login: ");
                    String usuarioLogin = sc.nextLine();
                    System.out.println("Digite a sua senha: ");
                    String senhaLogin = sc.nextLine();

                    Paciente paciente = Paciente.realizarLogin(usuarioLogin, senhaLogin);
                    if (paciente != null) {
                        int opcaoDigitadaLogin = -1;
                        while (opcaoDigitadaLogin != 3) {
                            System.out.println("MENU DE OPÇÕES");
                            System.out.println("1 - Enviar mensagem       ");
                            System.out.println("2 - Visualizar mensagens  ");
                            System.out.println("3 - Realizar logoff       ");

                            opcaoDigitadaLogin = sc.nextInt();

                            switch (opcaoDigitadaLogin) {
                                // ENVIAR MENSAGEM
                                case 1: {
                                    sc.nextLine();
                                    System.out.println("Digite o assunto da mensagem: ");
                                    String assunto = sc.nextLine();
                                    System.out.println("Digite o conteúdo da mensagem: ");
                                    String conteudo = sc.nextLine();
                                    System.out.println("Digite o nome do destinatário:");
                                    String nomeDestinatario = sc.nextLine();

                                    Mensagem mensagem = new Mensagem(assunto, conteudo, nomeDestinatario, paciente.getNome_paciente(), "E");
                                    mensagem.enviarMensagem();
                                    break;
                                }
                                // VISUALIZAR MENSAGENS
                                case 2: {
                                    // MENSAGENS ENVIADAS
                                    System.out.println("MENSAGENS ENVIADAS");
                                    List<Mensagem> mensagensEnviadas = paciente.visualizarMensagens("E");

                                    for (Mensagem mensagem : mensagensEnviadas) {
                                        System.out.println(mensagem.toStringEnviadas());
                                    }

                                    System.out.println();

                                    // MENSAGENS RECEBIDAS
                                    System.out.println("MENSAGENS RECEBIDAS");
                                    List<Mensagem> mensagensRecebidas = paciente.visualizarMensagens("P");

                                    for (Mensagem mensagem : mensagensRecebidas) {
                                        System.out.println(mensagem.toStringRecebidas());
                                    }

                                    break;
                                }
                                // REALIZAR LOGOFF
                                case 3: {
                                    paciente = null;
                                    break;
                                }
                                default:
                                    System.out.println("Opção inválida!");
                            }
                        }
                    }
                }
                // SAIR DO APLICATIVO
                case 3: {
                    break;
                }
                // CADASTRAR EQUIPE DE SAÚDE
                case 4: {
                    // CADASTRO
                    sc.nextLine();
                    System.out.println("CADASTRO");
                    System.out.println("Digite o seu usuário de login: ");
                    String usuarioLogin = sc.nextLine();
                    System.out.println("Digite a sua senha: ");
                    String senhaLogin = sc.nextLine();

                    // CONSULTAR SE JÁ EXISTE ESSA EQUIPE DE SAUDE NO BANCO DE DADOS
                    Boolean bEquipeDeSaudeExiste = Cadastro_EquipeDeSaude.consultaEquipeDeSaudeBD(usuarioLogin, senhaLogin);

                    // SÓ CADASTRA SE AINDA NÃO EXISTIR ESSA EQUIPE DE SAUDE
                    if (!bEquipeDeSaudeExiste) {
                        System.out.println("Digite o seu nome completo: ");
                        String nomeEquipeDeSaude = sc.nextLine();
                        System.out.println("Digite o seu telefone: ");
                        String telefoneEquipeDeSaude = sc.nextLine();
                        System.out.println("Digite o seu endereço: ");
                        String enderecoEquipeDeSaude = sc.nextLine();

                        Cadastro_EquipeDeSaude novaEquipeDeSaude = new Cadastro_EquipeDeSaude(nomeEquipeDeSaude, telefoneEquipeDeSaude, enderecoEquipeDeSaude, usuarioLogin, senhaLogin);
                        novaEquipeDeSaude.criarNovaEquipe();
                    }

                    break;
                }
                // REALIZAR LOGIN (EQUIPE DE SAÚDE)
                case 5: {
                    // LOGIN
                    sc.nextLine();
                    System.out.println("LOGIN");
                    System.out.println("Digite o seu usuário de login: ");
                    String usuarioLogin = sc.nextLine();
                    System.out.println("Digite a sua senha: ");
                    String senhaLogin = sc.nextLine();

                    EquipeDeSaude equipeDeSaude = EquipeDeSaude.realizarLogin(usuarioLogin, senhaLogin);
                    if (equipeDeSaude != null) {
                        int opcaoDigitadaLogin = -1;
                        while (opcaoDigitadaLogin != 5) {
                            System.out.println("MENU DE OPÇÕES");
                            System.out.println("1 - Visualizar pacientes ");
                            System.out.println("2 - Agendar visita       ");
                            System.out.println("3 - Enviar mensagem      ");
                            System.out.println("4 - Visualizar mensagens ");
                            System.out.println("5 - Realizar logoff      ");

                            opcaoDigitadaLogin = sc.nextInt();

                            switch (opcaoDigitadaLogin) {
                                // VISUALIZAR PACIENTES
                                case 1: {
                                    System.out.println("Pacientes cadastrados:");
                                    List<Paciente> pacientesCadastrados = equipeDeSaude.visualizarPacientes();

                                    for (Paciente paciente : pacientesCadastrados) {
                                        System.out.println(paciente);
                                    }

                                    break;
                                }
                                // AGENDAR VISITA
                                case 2: {
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                                    Date dDataVisita = null;

                                    System.out.println("Insira o CPF do paciente: ");
                                    sc.nextLine();
                                    String cpf = sc.nextLine();
                                    Paciente paciente = Paciente.buscaPaciente(cpf);

                                    if (paciente != null) {
                                        System.out.println("Insira a data da visita (DD/MM/YYYY hh:mm): ");
                                        String sDataVisita = sc.nextLine();
                                        try {
                                            dDataVisita  = sdf.parse(sDataVisita);
                                        }
                                        catch (Exception e)
                                        {
                                            System.out.println("Data inválida!");
                                            break;
                                        }
                                        System.out.println("Insira o endereço da visita");
                                        String endereco = sc.nextLine();

                                        Visita visita = new Visita(dDataVisita, endereco, cpf, equipeDeSaude, paciente);
                                        visita.cadastraVisita();
                                    }
                                    break;
                                }
                                // ENVIAR MENSAGEM
                                case 3: {
                                    sc.nextLine();
                                    System.out.println("Digite o assunto da mensagem: ");
                                    String assunto = sc.nextLine();
                                    System.out.println("Digite o conteúdo da mensagem: ");
                                    String conteudo = sc.nextLine();
                                    System.out.println("Digite o nome do destinatário:");
                                    String nomeDestinatario = sc.nextLine();

                                    Mensagem mensagem = new Mensagem(assunto, conteudo, equipeDeSaude.getNome_equipe(), nomeDestinatario, "P");
                                    mensagem.enviarMensagem();
                                    break;
                                }
                                // VISUALIZAR MENSAGENS
                                case 4: {
                                    // MENSAGENS ENVIADAS
                                    System.out.println("MENSAGENS ENVIADAS");
                                    List<Mensagem> mensagensEnviadas = equipeDeSaude.visualizarMensagens("P");

                                    for (Mensagem mensagem : mensagensEnviadas) {
                                        System.out.println(mensagem.toStringEnviadas());
                                    }

                                    System.out.println();

                                    // MENSAGENS RECEBIDAS
                                    System.out.println("MENSAGENS RECEBIDAS");
                                    List<Mensagem> mensagensRecebidas = equipeDeSaude.visualizarMensagens("E");

                                    for (Mensagem mensagem : mensagensRecebidas) {
                                        System.out.println(mensagem.toStringRecebidas());
                                    }

                                    break;
                                }
                                // REALIZAR LOGOFF
                                case 5: {
                                    equipeDeSaude = null;
                                    break;
                                }
                                default:
                                    System.out.println("Opção inválida!");
                                    break;
                            }
                        }
                    }
                }
                default: {
                    System.out.println("Opção inválida!");
                }
            }
        }

        sc.close();
    }
}