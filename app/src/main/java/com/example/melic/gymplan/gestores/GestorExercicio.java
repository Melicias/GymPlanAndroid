package com.example.melic.gymplan.gestores;

import com.example.melic.gymplan.classes.Exercicio;

import java.util.ArrayList;

public class GestorExercicio {

    /*
    * Exercicio
    * int id, String foto, String nome, String descricao, int repeticoes, int duracao
    * int id, String foto, String nome, String descricao, int repeticoes_duracao, boolean repeticoes
     */
    public static final int TESTE = 0;
    public static final int ONLINE = 1;
    public static final int OFFLINE = 2;

    private ArrayList<Exercicio> exercicios;

    public GestorExercicio(int escolha){
        if(escolha == ONLINE){
            //buscar api
        }else{
            //buscar a base de dados
        }
        if(escolha == 0) {
            this.exercicios = new ArrayList<>();
            adicionarDados();
        }
    }

    private void adicionarDados(){
        this.exercicios.add(new Exercicio(1,"https://revistashape.com.br/wp-content/uploads/2017/07/abdominal-cl%C3%A1ssica.jpg","Abdominal", "descricao123descrica o123descricao123desc ricao123descricao123descricao123descricao123 descricao123descricao123descricao123",20,true));
        this.exercicios.add(new Exercicio(2,"https://nit.pt/wp-content/uploads/2016/10/93cf40e8-6d65-43ed-aea8-f6e119cfd207-754x394.jpg","Saltar a corda","Saltar a corda e muito doloroso pimbas",480,false));
        this.exercicios.add(new Exercicio(3,"https://upload.wikimedia.org/wikipedia/commons/8/84/Muscleup.jpg","Aquele exercicio","Exte e um exercicio que ninguem sabia que existia",12,true));
        this.exercicios.add(new Exercicio(4,"https://cdn2.coachmag.co.uk/sites/coachmag/files/2017/05/bench-press_0.jpg","Peito","dalhe com a maquina",24,true));
        this.exercicios.add(new Exercicio(5,"https://weighttraining.guide/wp-content/uploads/2017/07/diamond-push-up-1.png","Push ups diamond","Forca estas quase a acabar, cuidado com a forma como o fazes",10,true));
        this.exercicios.add(new Exercicio(6,"https://i.ytimg.com/vi/_l3ySVKYVJ8/maxresdefault.jpg","Flexoes","BORA BORA, falta pouco",20,true));
        this.exercicios.add(new Exercicio(7,"https://images.vidaativa.pt/repo/abdominal-tesoura.jpg","Abdominal infernal","Agora para acabar vem o exercicio mais dificil",120,false));
    }

    public ArrayList<Exercicio> getExercicios(){
        return this.exercicios;
    }

    public Exercicio getExercicio(int index){
        return this.exercicios.get(index);
    }

    /*public void setExercicio(Exercicio ex){
        this.exercicios.add(ex);
    }*/
}
