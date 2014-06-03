package br.com.luke.cadastro;


import java.util.List;






import org.w3c.dom.ls.LSInput;

import br.com.luke.cadastro.adapter.ListaAlunosAdapter;
import br.com.luke.cadastro.dao.AlunoDAO;
import br.com.luke.cadastro.modelo.Aluno;
import br.com.luke.cadastro.util.AlunoConverter;
import br.com.luke.cadastro.util.WebClient;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ListaAlunos extends Activity {

	private ListView lista;
	
	private Aluno aluno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_alunos);

		lista = (ListView) findViewById(R.id.lista);
		
		registerForContextMenu(lista);
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				
				Aluno alunoClicado = (Aluno) adapter.getItemAtPosition(posicao);
				Intent editaAluno = new Intent(ListaAlunos.this, Formulario.class);
				editaAluno.putExtra("alunoSelecionado", alunoClicado);
				startActivity(editaAluno);
			}
		});

		lista.setOnItemLongClickListener(new OnItemLongClickListener() {


			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				aluno = (Aluno) adapter.getItemAtPosition(posicao);

				return false;
			}
		});
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

		MenuItem ligar = menu.add("Ligar");
		ligar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irParaTelaDeDiscagem = new Intent(Intent.ACTION_CALL);
				Toast.makeText(ListaAlunos.this, aluno.getTelefone(), Toast.LENGTH_LONG).show();
				Uri msisdn = Uri.parse("tel:"+aluno.getTelefone());
				irParaTelaDeDiscagem.setData(msisdn);
				startActivity(irParaTelaDeDiscagem);
				return false;
			}
		});
		menu.add("Enviar SMS");
		MenuItem navegarNoSite = menu.add("Navegar no site");
		navegarNoSite.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irParaSite = new Intent(Intent.ACTION_VIEW);
				Uri localSite = Uri.parse("http://"+aluno.getSite());
				irParaSite.setData(localSite);
				startActivity(irParaSite);
				return false;
			}
		});
		MenuItem deletar = menu.add("Deletar");
		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AlunoDAO dao = new AlunoDAO(ListaAlunos.this);
				dao.deletar(aluno);
				dao.close();
				
				carregaLista();
				
				return true;
			}
		});
		menu.add("Ver no mapa");
		menu.add("Enviar e-mail");

		
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		carregaLista();
	}

	private void carregaLista() {
		AlunoDAO dao = new AlunoDAO(this);
		List<Aluno> alunos = dao.getLista();
		dao.close();
//		int layout = R.layout.linha_listagem;
		ListaAlunosAdapter adapter = new ListaAlunosAdapter(alunos, ListaAlunos.this);
		lista.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.listagem_alunos, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemClicado = item.getItemId();

		switch (itemClicado) {
//		case R.id.novo:
//			Intent irParaFormulario = new Intent(this, Formulario.class);
//
//			startActivity(irParaFormulario);
//			break;
		case R.id.enviar_alunos:
			Thread tarefaPesada = new Thread(){
				@Override
				public void run() {
					String urlServer = "http://echo.jsontest.com";
					
					AlunoDAO dao = new AlunoDAO(ListaAlunos.this);
					List<Aluno> alunos = dao.getLista();
					dao.close();
					String dadosJSON = new AlunoConverter().toJSON(alunos);
					WebClient client = new WebClient(urlServer);
					
					String respostaJSON = client.post(dadosJSON);
					
					Log.i("Retorn da chamada:", respostaJSON);
					
//					Toast.makeText(ListaAlunos.this, respostaJSON, Toast.LENGTH_LONG);
				}
			};
			tarefaPesada.start();

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

}