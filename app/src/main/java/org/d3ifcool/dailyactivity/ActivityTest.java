package org.d3ifcool.dailyactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Tobing on 04/10/2017.
 */

public class ActivityTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		Button minggu = (Button) findViewById(R.id.btn_minggu);
		Button senin = (Button) findViewById(R.id.btn_senin);
		Button selasa = (Button) findViewById(R.id.btn_selasa);
		Button rabu = (Button) findViewById(R.id.btn_rabu);
		Button kamis = (Button) findViewById(R.id.btn_kamis);
		Button jumat = (Button) findViewById(R.id.btn_jumat);
		Button sabtu = (Button) findViewById(R.id.btn_sabtu);

		minggu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(ActivityTest.this, JadwalActivity.class);
				i.putExtra(Jadwal.EXTRA_HARI, Jadwal.MINGGU);
				startActivity(i);
			}
		});

		senin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(ActivityTest.this, JadwalActivity.class);
				i.putExtra(Jadwal.EXTRA_HARI, Jadwal.SENIN);
				startActivity(i);
			}
		});

		selasa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(ActivityTest.this, JadwalActivity.class);
				i.putExtra(Jadwal.EXTRA_HARI, Jadwal.SELASA);
				startActivity(i);
			}
		});

		rabu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(ActivityTest.this, JadwalActivity.class);
				i.putExtra(Jadwal.EXTRA_HARI, Jadwal.RABU);
				startActivity(i);
			}
		});

		kamis.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(ActivityTest.this, JadwalActivity.class);
				i.putExtra(Jadwal.EXTRA_HARI, Jadwal.KAMIS);
				startActivity(i);
			}
		});

		jumat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(ActivityTest.this, JadwalActivity.class);
				i.putExtra(Jadwal.EXTRA_HARI, Jadwal.JUMAT);
				startActivity(i);
			}
		});

		sabtu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(ActivityTest.this, JadwalActivity.class);
				i.putExtra(Jadwal.EXTRA_HARI, Jadwal.SABTU);
				startActivity(i);
			}
		});

	}
}
