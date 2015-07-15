package model;

import java.io.Serializable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

//definindo o nome da tabela
@Table(name = "Professor")
public class Professor extends Model implements Serializable{
	
	//definindo nome das colunas
		@Column(name = "nomeProfessor")
	    public String nomeProfessor;
		
		public Professor() {
	        super();
	    }

	    public Professor(String nomeProfessor) {
	        super();
	        this.nomeProfessor = nomeProfessor;
	    }
	    
	    @Override
	    public String toString() {
	        return nomeProfessor;
	    }

}
