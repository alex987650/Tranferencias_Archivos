package tranferencias;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.*;
import java.net.*;
import javax.swing.JFileChooser;

public class Cliente {

    public static void main(String[] args) {
        String servidorIP = "192.168.1.66"; // Cambiar esto con la dirección IP del servidor
        int puerto = 9877;

        try (Socket socket = new Socket(servidorIP, puerto)) {
            socket.setSoTimeout(10000); // 10 segundos
            System.out.println("Conectado al servidor en " + servidorIP + ":" + puerto);

            // Enviar el archivo al servidor
            String rutaArchivo = seleccionarArchivo();
            if (rutaArchivo != null) {
                enviarArchivo(socket, rutaArchivo);
            } else {
                System.out.println("No se seleccionó ningún archivo.");
            }

            System.out.println("Transferencia completada");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void enviarArchivo(Socket socket, String nombreArchivo) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(nombreArchivo);
             OutputStream outputStream = socket.getOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {

            // Indicar al servidor que la ruta será proporcionada
            dataOutputStream.writeUTF("");

            byte[] buffer = new byte[1024];
            int bytesLeidos;

            while ((bytesLeidos = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesLeidos);
            }

            System.out.println("Archivo enviado correctamente");
            

        }
    }

    private static String seleccionarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }
}

