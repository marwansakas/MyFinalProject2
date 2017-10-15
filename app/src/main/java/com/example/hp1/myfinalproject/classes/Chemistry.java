package com.example.hp1.myfinalproject.classes;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
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
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

public class Chemistry extends Activity implements AdapterView.OnItemSelectedListener{

	TextView tvVEB;
	PhotoView photoView;
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
			,"Lr","Rf","Db","Sg","Bh","Hs","Mt","Uun","Uuu","uub"};//an array filled with the element symbols
	String[] chemicalInformation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chemistry);
		chemicalInformation=getResources().getStringArray(R.array.chemicalInformation);//get the String array in res/Strings/chemicalInformation
		tvVEB=(TextView)findViewById(R.id.tv_VEB_School);//initialize tvVEB
		photoView=(PhotoView)findViewById(R.id.table_of_elements);//initialize photoView
		spinner=(Spinner)findViewById(R.id.spinner);//initialize spinner
		tvVEB.setClickable(true);//make tvVEB clickable
		tvVEB.setMovementMethod(LinkMovementMethod.getInstance());
		String text = "<a href='https://www.facebook.com/groups/VEBSchool.chemistry/'> VEB School </a>";//the web location
		tvVEB.setText(Html.fromHtml(text));//set the Html
		photoView.setImageResource(R.drawable.table_of_elements);//set the picture
		ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chemicalSymbols);//add the item to the spinner from String array chemicalSymbols
		spinner.setAdapter(spinneradapter);//set the spinner adapter
		spinner.setOnItemSelectedListener(this);//make spinner clickAable
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		if(0<i&&i<chemicalInformation.length)//if the item was between 0 and chemicalInformation.length
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();//create alertDialog
			alertDialog.setTitle(chemicalSymbols[i]);//set the Title
			alertDialog.setMessage(chemicalInformation[i-1]);//set the Messege
			alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
					new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});//set the buuton
			alertDialog.show();//show aletDialog
		}
		else
			Toast.makeText(this,"be sure to check the next update if they are added",Toast.LENGTH_SHORT).show();//show toast that that information has not been added yet
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}
}
