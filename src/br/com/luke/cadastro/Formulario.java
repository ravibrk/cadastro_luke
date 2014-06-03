package br.com.luke.cadastro;

import java.io.File;

import br.com.luke.cadastro.dao.AlunoDAO;
import br.com.luke.cadastro.modelo.Aluno;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

public class Formulario extends Activity{

	private FormularioHelper helper;
	
	private String caminhoArquivo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
	
		helper = new FormularioHelper(this);

		Intent intent = getIntent();
		final Aluno alunoParaSerAlterado = (Aluno) intent.getSerializableExtra("alunoSelecionado");
		
		Button botao = (Button) findViewById(R.id.botao);
		
		if (alunoParaSerAlterado != null){
			botao.setText("Alterar");
			helper.colocaAlunoNoFormulario(alunoParaSerAlterado);
		}
		
		botao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Aluno aluno = helper.pegaAlunoDoFormulario();
				
				AlunoDAO dao = new AlunoDAO(Formulario.this);
				if(alunoParaSerAlterado == null){
					dao.salva(aluno);
				} else {
					aluno.setId(alunoParaSerAlterado.getId());
					dao.altera(aluno);
				}

				dao.close();
				
				finish();
			}
		});
		
		ImageView foto = helper.getFoto();
		
		foto.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View v) {
				Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				caminhoArquivo = Environment.getExternalStorageDirectory().toString()+"/"+System.currentTimeMillis()+".png";
				File arquivo = new File(caminhoArquivo);
				Uri localImagem = Uri.fromFile(arquivo);
				irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localImagem);				
				startActivityForResult(irParaCamera, 123);
			}
		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 123){
			if(resultCode == Activity.RESULT_OK){
				helper.carregaImagem(caminhoArquivo);
			} else {
				caminhoArquivo = null;
			}
		}
	}
}
