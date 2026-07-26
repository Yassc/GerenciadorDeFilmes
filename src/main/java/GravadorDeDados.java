import java.io.*;
import java.util.Map;

public class GravadorDeDados {
    private final String arquivoCatalogo = "catalogo.dat";
    private final String arquivoFavoritos = "favoritos.dat";
    private final String arquivoDesejos = "desejos.dat"; // Novo arquivo para a Lista de Desejos

    public void salvar(Map<String, Filme> catalogo, Map<String, Filme> favoritos, Map<String, Filme> desejos) throws IOException {
        try (ObjectOutputStream oosC = new ObjectOutputStream(new FileOutputStream(arquivoCatalogo));
             ObjectOutputStream oosF = new ObjectOutputStream(new FileOutputStream(arquivoFavoritos));
             ObjectOutputStream oosD = new ObjectOutputStream(new FileOutputStream(arquivoDesejos))) {

            oosC.writeObject(catalogo);
            oosF.writeObject(favoritos);
            oosD.writeObject(desejos); // Gravando o mapa de desejos em disco
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Filme> recuperarCatalogo() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivoCatalogo))) {
            return (Map<String, Filme>) ois.readObject();
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Filme> recuperarFavoritos() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivoFavoritos))) {
            return (Map<String, Filme>) ois.readObject();
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Filme> recuperarDesejos() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivoDesejos))) {
            return (Map<String, Filme>) ois.readObject();
        }
    }
}