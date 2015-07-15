package com.example.activeandroidtutorial;

import java.util.ArrayList;

import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import model.Curso;
import model.Professor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final EditText nomeCurso = (EditText) findViewById(R.id.edtNomeCurso);
		final EditText nomeProfessor = (EditText) findViewById(R.id.edtNomeProfessor);

		Button salvar = (Button) findViewById(R.id.btnSalvar);
		Button listarTodos = (Button) findViewById(R.id.btnListarTodos);
		Button listarPorProfessor = (Button) findViewById(R.id.btnListarPorProfessor);
		Button deletar = (Button) findViewById(R.id.btnDeletar);
		Button atualizarProfessorDeCurso = (Button) findViewById(R.id.btnAttProf);
		Button deletarTudo = (Button) findViewById(R.id.btnDeletarTudo);

		//clique para salvar novo professor e curso
		salvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//criando instâncias das tabelas do banco e setando seus atributos
				//posteriormente, salvando no banco de dados
				Professor professor = new Professor();
				professor.nomeProfessor = nomeProfessor.getText().toString();

				if(!professor.nomeProfessor.equals("")){
					professor.save();

					Curso curso = new Curso();
					curso.nomeCurso = nomeCurso.getText().toString();
					curso.professor = professor;

					if(!curso.nomeCurso.equals("")){
						curso.save();
						Toast.makeText(getApplicationContext(), "Curso inserido: " + curso.toString(), Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "Insira o nome do curso!", Toast.LENGTH_LONG).show();
					}


				}else{
					Toast.makeText(getApplicationContext(), "Insira o nome do professor!", Toast.LENGTH_LONG).show();
				}

			}
		});


		//clique para listar todos os cursos e seus professores
		listarTodos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//fazendo consulta que retorna todos os cursos 
				ArrayList<Curso> cursos = new Select().from(Curso.class).orderBy("nomeCurso ASC").execute();
				String listaDeCursos = "Lista de cursos: \n";

				for (int i = 0; i < cursos.size(); i++) {
					listaDeCursos = listaDeCursos + cursos.get(i).toString() + "\n";
				}

				Toast.makeText(getApplicationContext(), listaDeCursos, Toast.LENGTH_LONG).show();
			}
		});

		//clique para listar por professor
		listarPorProfessor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//consultando o professor pelo nome para pegar o objeto com ID
				Professor professor = new Select().from(Professor.class).where("nomeProfessor = ?", nomeProfessor.getText().toString()).executeSingle();

				if(professor != null){
					//fazendo consulta que retorna todos os cursos pelo objeto professor (comparando pelo ID)
					//como novos professores e cursos são sempre inseridos no save(), e o listar por professor foi feito buscando pelo ID para
					//exemplificar este caso, apenas um resultado será retornado.
					ArrayList<Curso> cursos = new Select().from(Curso.class).where("Professor = ?", professor.getId()).orderBy("nomeCurso ASC").execute();
					String listaDeCursos = "Lista de cursos: \n";

					for (int i = 0; i < cursos.size(); i++) {
						listaDeCursos = listaDeCursos + cursos.get(i).toString() + "\n";
					}

					Toast.makeText(getApplicationContext(), listaDeCursos, Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "Não há cursos com esse professor", Toast.LENGTH_LONG).show();
				}


			}
		});

		//clique para deletar curso
		deletar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Curso curso = new Select().from(Curso.class).where("nomeCurso = ?", nomeCurso.getText().toString()).executeSingle();
				
				//vale notar que o professor é deletado pelo uso do Column.ForeignKeyAction.CASCADE na classe Curso
				if(curso != null){
					Toast.makeText(getApplicationContext(), "Curso "+ curso.nomeCurso + " e professor relacionado "+ curso.professor.nomeProfessor + " deletados!", Toast.LENGTH_LONG).show();
					curso.delete();
				}else{
					Toast.makeText(getApplicationContext(), "Não existe curso com este nome", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		//clique para deletar todos os dados
		deletarTudo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//apenas para pegar a quantidade de cursos já inseridos
				ArrayList<Curso> cursos = new Select().from(Curso.class).orderBy("nomeCurso ASC").execute();
				
				//pegando o primeiro ID da lista, para saber a partir de onde começar a deletar
				long primeiroID = cursos.get(0).getId();
				
				for (int i = (int) primeiroID; i < primeiroID + cursos.size(); i++) {
					//aqui apresentamos uma nova forma de carregar o objeto do banco, via ID
					Curso curso = Curso.load(Curso.class, i);
					curso.delete();

				}
			}
		});
		
		//clique para atualizar o professor de um curso
		atualizarProfessorDeCurso.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//buscando o curso que será atualizado
				Curso curso = new Select().from(Curso.class).where("nomeCurso = ?", nomeCurso.getText().toString()).executeSingle();
				
				if(curso != null){
					Professor professor = curso.professor;
					professor.nomeProfessor = nomeProfessor.getText().toString();
					professor.save();
					Toast.makeText(getApplicationContext(), "Professor do curso " + curso.nomeCurso + " atualizado para " + professor.nomeProfessor, Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "Não existe curso com este nome", Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
