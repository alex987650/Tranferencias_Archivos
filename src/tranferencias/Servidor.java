package tranferencias;

import java.io.*;
import java.net.*;
import java.nio.file.Paths;

public class Servidor {

    public static void main(String[] args) {
        int puerto = 9877;

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Esperando conexión en el puerto " + puerto + "...");

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Cliente conectado desde " + socket.getInetAddress());

                    // Recibir el archivo en el escritorio
                    recibirArchivo(socket);

                    System.out.println("Transferencia completada");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void recibirArchivo(Socket socket) throws IOException {
    try (InputStream inputStream = socket.getInputStream()) {
      // Nombre del archivo y ruta de destino
      String nombreArchivo = "archivo_recibido.pdf";
      try (FileOutputStream fileOutputStream = new FileOutputStream(nombreArchivo)) {
        byte[] buffer = new byte[1024];
        int bytesRead;

        // Leer datos del socket y escribir en el archivo
        while ((bytesRead = inputStream.read(buffer)) != -1) {
          fileOutputStream.write(buffer, 0, bytesRead);
        }
        // Imprimir la ruta completa del archivo en el servidor
            String rutaCompleta = Paths.get(System.getProperty("user.dir"), nombreArchivo).toString();
            System.out.println("Archivo recibido correctamente: " + nombreArchivo);
            System.out.println("Archivo se guardará en: " + rutaCompleta);
      }

    }
  }
}

