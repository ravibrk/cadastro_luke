package br.com.luke.cadastro;

import br.com.luke.cadastro.dao.AlunoDAO;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] mensagens = (Object[]) intent.getExtras().get("pdus");
		
		byte[] primeira = (byte[]) mensagens[0];
		
		SmsMessage sms = SmsMessage.createFromPdu(primeira);
		
		String telefone = sms.getDisplayOriginatingAddress();
		
		Toast.makeText(context, "SMS do telefone: "+telefone, Toast.LENGTH_LONG).show();
		
		AlunoDAO dao = new AlunoDAO(context);
		
		boolean ehAluno = dao.isAluno(telefone);
		
		dao.close();
		
		if(ehAluno){
			MediaPlayer player = MediaPlayer.create(context, R.raw.awakeningprelude);
			player.start();
			Toast.makeText(context, "Tocando musica...", Toast.LENGTH_LONG).show();
		}
	}

}
