package br.edu.ifsul.modelo;

import java.util.ArrayList;
import java.util.List;

public class MaterialDisciplina {

    private String disciplina;
    private final List<Arquivo> arquivos = new ArrayList<>();

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public List<Arquivo> getArquivos() {
        return arquivos;
    }

    public void addArquivo(Arquivo a) {
        arquivos.add(a);
    }

}
