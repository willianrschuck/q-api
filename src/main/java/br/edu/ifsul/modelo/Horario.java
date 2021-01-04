package br.edu.ifsul.modelo;

import java.util.ArrayList;
import java.util.List;

public class Horario {

    private int indexDiaSemana = 0;

    private List<HorarioDisciplina> segunda = new ArrayList<>();
    private List<HorarioDisciplina> terca = new ArrayList<>();
    private List<HorarioDisciplina> quarta = new ArrayList<>();
    private List<HorarioDisciplina> quinta = new ArrayList<>();
    private List<HorarioDisciplina> sexta = new ArrayList<>();

    private List<List<HorarioDisciplina>> diasDaSemana = new ArrayList<>();

    public Horario() {
        diasDaSemana.add(segunda);
        diasDaSemana.add(terca);
        diasDaSemana.add(quarta);
        diasDaSemana.add(quinta);
        diasDaSemana.add(sexta);
    }

    public void addHorarioDisciplina(HorarioDisciplina hd) {
        diasDaSemana.get(indexDiaSemana).add(hd);
        indexDiaSemana = (indexDiaSemana + 1) % diasDaSemana.size();
    }

    public List<HorarioDisciplina> getSegunda() {
        return segunda;
    }

    public List<HorarioDisciplina> getTerca() {
        return terca;
    }

    public List<HorarioDisciplina> getQuarta() {
        return quarta;
    }

    public List<HorarioDisciplina> getQuinta() {
        return quinta;
    }

    public List<HorarioDisciplina> getSexta() {
        return sexta;
    }

}
