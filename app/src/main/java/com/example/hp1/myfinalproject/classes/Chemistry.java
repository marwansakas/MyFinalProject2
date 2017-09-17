package com.example.hp1.myfinalproject.classes;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp1.myfinalproject.R;

public class Chemistry extends Activity implements AdapterView.OnItemSelectedListener{

	TextView tvVEB;
	ImageView imb;
	Spinner spinner;
	String[] chemicalSymbols={"   ","H","He"
			,"Li","Be","B","C","N","O","F","Ne"
			,"Na","Mg","Al","Si","P","S","Ci","Ar"
			,"K","Ca","Se","Ti","V","Cr","Mn","Fe","Co","Ni","Cu","Zn","Ga","Ge","As","Se","Br","Kr"
			,"Rb","Sr","Y","Zr","Nb","Mo","Tc","Ru","Rh","pd","Ag","Cd","In","Sn","Sb","Te","I","Xe"
			,"Cs","Ba"
			,"La","Ce","Pr","Nd","Pm","Sm","Eu","Gd","Tb","Dy","Ho","Er","Tm","Yb"
			,"Lu","Hf","Ta","W","Re","OS","Ir","Pt","Au","Hg","Ti","Pb","Bi","Po","At","Rn"
			,"Fr","Ra"
			,"Ac","Th","Pa","U","Np","Pu","Am","Cm","Bk","Cf","Es","Fm","Md","No"
			,"Lr","Rf","Db","Sg","Bh","Hs","Mt","Uun","Uuu","uub"};
	String[] chemicalInformation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chemistry);
		chemicalInformation=getResources().getStringArray(R.array.chemicalInformation);
		tvVEB=(TextView)findViewById(R.id.tv_VEB_School);
		tvVEB.setClickable(true);
		tvVEB.setMovementMethod(LinkMovementMethod.getInstance());
		String text = "<a href='https://www.facebook.com/groups/VEBSchool.chemistry/'> VEB School </a>";
		tvVEB.setText(Html.fromHtml(text));
		imb=(ImageView)findViewById(R.id.table_of_elements);
		imb.setImageResource(R.drawable.table_of_elements);
		spinner=(Spinner)findViewById(R.id.spinner);
		ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chemicalSymbols);
		spinner.setAdapter(spinneradapter);
		spinner.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		if(0<i&&i<chemicalInformation.length)
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(chemicalSymbols[i]);
			alertDialog.setMessage(chemicalInformation[i-1]);
			alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
					new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
			alertDialog.show();
		}
		else
			Toast.makeText(this,"be sure to check the next update if they are added",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}
}
