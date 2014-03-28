package br.com.luke.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ListaAlunos extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_alunos);
		
		final String[] nomes = {"Ana", "Jose", "Felipe"};
		
		int layout = android.R.layout.simple_list_item_1;
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, layout, nomes);
		
		ListView lista = (ListView) findViewById(R.id.lista);
		
		lista.setAdapter(adapter);
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int posicao,
					long id) {
				Toast.makeText(ListaAlunos.this, "Clique na posicao " +posicao, Toast.LENGTH_SHORT).show();
			}
		});
		
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao,
					long id) {
				Toast.makeText(ListaAlunos.this, "Clique longo em " +adapter.getItemAtPosition(posicao), Toast.LENGTH_SHORT).show();
				
				return true;
			}
		});
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
		
		switch(itemClicado){
		case R.id.novo:
			Intent irParaFormulario = new Intent(this, Formulario.class);
		
			startActivity(irParaFormulario);
			break;
			
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

}