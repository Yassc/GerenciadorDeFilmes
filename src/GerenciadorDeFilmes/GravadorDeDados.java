package Projetofinalpoo.GerenciadorDeFilmes;

import java.io.*;
import java.util.Map;

public class GravadorDeDados {
    private final String arquivoCatalogo = "catalogo.dat";
    private final String arquivoFavoritos = "favoritos.dat";

    public void salvar(Map<String, Filme> catalogo, Map<String, Filme> favoritos) throws IOException {
        try (ObjectOutputStream oosC = new ObjectOutputStream(new FileOutputStream(arquivoCatalogo));
             ObjectOutputStream oosF = new ObjectOutputStream(new FileOutputStream(arquivoFavoritos))) {

            oosC.writeObject(catalogo);
            oosF.writeObject(favoritos);
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
}