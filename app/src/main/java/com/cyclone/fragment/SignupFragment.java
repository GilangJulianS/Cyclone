package com.cyclone.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cyclone.EmptyActivity;
import com.cyclone.R;

/**
 * Created by gilang on 22/10/2015.
 */
public class SignupFragment extends Fragment {

	private Button btnSignup;
	private TextView txtToc;
	private Spinner spinnerGender;
	private String[] spinnerGenderItems = {"Male", "Female", "Gender"};

	public SignupFragment(){}

	public static SignupFragment newInstance(){
		SignupFragment fragment = new SignupFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_signup, parent, false);

		btnSignup = (Button) v.findViewById(R.id.btn_signup);
		spinnerGender = (Spinner) v.findViewById(R.id.spinner_gender);
		txtToc = (TextView) v.findViewById(R.id.txt_toc);

		btnSignup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager manager = getActivity().getSupportFragmentManager();
				manager.popBackStack();
			}
		});


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, R.id.text, spinnerGenderItems){

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				View v = super.getView(position, convertView, parent);
				if (position == getCount()) {
					((TextView)v.findViewById(R.id.text)).setText(getItem(getCount()));
				}

				return v;
			}

			@Override
			public int getCount() {
				return super.getCount() - 1;
			}
		};
		spinnerGender.setAdapter(adapter);
		spinnerGender.setSelection(adapter.getCount());

		String tocString = txtToc.getText().toString();
		final int startIdx = tocString.indexOf("Term");
		int endIdx = tocString.indexOf("Conditions") + 10;
		SpannableString ss = new SpannableString(txtToc.getText());
		ClickableSpan span = new ClickableSpan() {
			@Override
			public void onClick(View widget) {
				Intent i = new Intent(getContext(), EmptyActivity.class);
				i.putExtra("fragmentType", EmptyActivity.FRAGMENT_TOC);
				getActivity().startActivity(i);
			}
		};
		ss.setSpan(span, startIdx, endIdx, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		txtToc.setText(ss);
		txtToc.setMovementMethod(LinkMovementMethod.getInstance());
		txtToc.setHighlightColor(Color.BLUE);

		return v;
	}
}
