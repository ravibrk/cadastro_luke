package br.com.luke.cadastro.util;

import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import br.com.luke.cadastro.modelo.Aluno;

public class AlunoConverter {

	public String toJSON(List<Aluno> alunos) {
			try {
				// {"list" : [{ "alunos" : [{ "nome" : "Chronos", "nota" : 9 },		{ "nome" : "Trigger", "nota" : 8 },		{ "nome" : "Snake", "nota" : 2 }] }]}
				JSONStringer js = new JSONStringer();
				js.object().key("list").array();
				js.object().key("aluno").array();
				for(Aluno aluno : alunos){
					js.object();
					js.key("nome").value(aluno.getNome());
					js.key("nota").value(aluno.getNota());
					js.endObject();
				}
				js.endArray().endObject();
				js.endArray().endObject();
				return js.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}

}
