package model;

import java.io.Serializable;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

//definindo o nome da tabela
@Table(name = "Curso")
public class Curso extends Model implements Serializable {
	
	//definindo nome das colunas
	@Column(name = "nomeCurso")
    public String nomeCurso;
	
	//definindo propriedades de ação junto com a definição da coluna
	@Column(name = "professor", onDelete = Column.ForeignKeyAction.CASCADE)
    public Professor professor;
	
	public Curso() {
        super();
    }

    public Curso(String nomeCurso, Professor professor) {
        super();
        this.nomeCurso = nomeCurso;
        this.professor = professor;
    }
    
    @Override
    public String toString() {
        return "Nome do curso: "
                + nomeCurso
                + " Professor: "
                + professor;
    }
}
