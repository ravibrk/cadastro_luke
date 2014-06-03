package br.com.luke.cadastro.adapter;

import java.util.List;

import br.com.luke.cadastro.R;
import br.com.luke.cadastro.modelo.Aluno;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaAlunosAdapter extends BaseAdapter {

	private List<Aluno> alunos;
	private Activity activity;

	public ListaAlunosAdapter(List<Aluno> alunos, Activity activity) {
		this.alunos = alunos;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return alunos.size();
	}

	@Override
	public Object getItem(int position) {
		return alunos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return alunos.get(position).getId();
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		Aluno aluno = alunos.get(position);
		
		LayoutInflater inflater = activity.getLayoutInflater();
		View linha = inflater.inflate(R.layout.linha_listagem, null);
		
		TextView nome = (TextView) linha.findViewById(R.id.nome);
		nome.setText(aluno.getNome());
		
		ImageView foto = (ImageView) linha.findViewById(R.id.foto);
		if(aluno.getFoto() != null){
			Bitmap fotoAluno = BitmapFactory.decodeFile(aluno.getFoto());
			Bitmap fotoReduzida = Bitmap.createScaledBitmap(fotoAluno, 100, 100, true);
			foto.setImageBitmap(fotoReduzida);
		} else {
			Drawable semFoto = activity.getResources().getDrawable(R.drawable.ic_action_user);
			foto.setImageDrawable(semFoto);
		}
		return linha;
	}

}
