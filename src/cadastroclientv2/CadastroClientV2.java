/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadastroclientv2;

/**
 *
 * @author abrah
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CadastroClientV2 {
    public static void main(String[] args) {
        try {
            // Instanciar um Socket apontando para localhost, na porta 4321
            Socket socket = new Socket("localhost", 4321);

            // Encapsular os canais de entrada e saída do Socket em objetos dos tipos
            // ObjectOutputStream (saída) e ObjectInputStream (entrada)
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            // Escrever o login e a senha na saída, utilizando os dados de algum dos registros
            // da tabela de usuários (op1/op1)
            String login = "op1";
            String senha = "op1";
            output.writeObject(login);
            output.writeObject(senha);

            // Encapsular a leitura do teclado em um BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Instanciar a janela para apresentação de mensagens
            System.out.println("Bem-vindo ao sistema de cadastro!");

            // Instanciar a Thread para preenchimento assíncrono
            // (not implemented in this example, as it's not clear what this thread should do)

            while (true) {
                // Apresentar um menu com as opções: L – Listar,  X – Finalizar, E – Entrada, S – Saída
                System.out.println("L - Listar | X - Finalizar | E - Entrada | S - Saída");

                // Receber o comando a partir do teclado
                String command = reader.readLine();

                // Tratar os comandos
                if (command.equals("L")) {
                    output.writeObject("L");
                    try {
                        System.out.println("Produtos");
                        String[] produtoNames = (String[]) input.readObject();
                        for (String nome : produtoNames) {
                            System.out.println(nome);
                        }
                    } catch (ClassNotFoundException e) {
                        System.err.println("Error ClassNotFoundException: " + e.getMessage());
                    }
                    
                } else if (command.equals("X")) {
                    break;
                } else if (command.equals("E") || command.equals("S")) {
                    output.writeObject(command);

                    // Obter o Id da pessoa via teclado e enviar para o servidor
                    System.out.print("Id da pessoa: ");
                    String pessoaId = reader.readLine();
                    output.writeObject(pessoaId);

                    // Obter o Id do produto via teclado e enviar para o servidor
                    System.out.print("Id do produto: ");
                    String produtoId = reader.readLine();
                    output.writeObject(produtoId);

                    // Obter a quantidade via teclado e enviar para o servidor
                    System.out.print("Quantidade: ");
                    String quantidade = reader.readLine();
                    output.writeObject(quantidade);

                    // Obter o valor unitário via teclado e enviar para o servidor
                    System.out.print("Valor unitário: ");
                    String valorUnitario = reader.readLine();
                    output.writeObject(valorUnitario);
                }
            }

            socket.close();
        } catch (IOException e) {
            System.err.println("Erro ao conectar ao servidor: " + e.getMessage());
        }
    }
}