package com.nennashi.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.nennashi.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;

public class DateConditionPreference extends DialogPreference {

	private String preferenceValue = "";
	private DatePicker datepicker;
	private RadioButton rdobAfter;
	private RadioButton rdobBefor;

	public DateConditionPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DateConditionPreference(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected Object onGetDefaultValue(TypedArray a, int index) {
		return a.getString(index);
	}

	@Override
	protected void onSetInitialValue(boolean restorePersistedvalue,
			Object defaultValue) {
		if (restorePersistedvalue) {
			preferenceValue = getPersistedString(preferenceValue);
		} else {
			preferenceValue = (String) defaultValue;
			persistString(preferenceValue);
		}
	}

	@Override
	protected View onCreateDialogView() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dateconditions, null);
		datepicker = (DatePicker) view.findViewById(R.id.dateConditionPicker);
		rdobAfter = (RadioButton) view.findViewById(R.id.radioAfterButton);
		rdobBefor = (RadioButton) view.findViewById(R.id.radioBeforeButton);

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Date date;
		try {
			date = df.parse(preferenceValue.split(" ")[0]);
			if (preferenceValue.split(" ")[1].equals("以前")) {
				rdobBefor.setChecked(true);
			} else {
				rdobAfter.setChecked(true);
			}
			datepicker.updateDate(date.getYear() + 1900, date.getMonth(),
					date.getDate());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return view;
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		if (positiveResult) {
			if (datepicker != null) {
				Date date = new Date(datepicker.getYear() - 1900,
						datepicker.getMonth(), datepicker.getDayOfMonth());
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				String newValue = df.format(date);

				if (rdobAfter.isChecked()) {
					newValue += " " + rdobAfter.getText();
				} else {
					newValue += " " + rdobBefor.getText();
				}

				if (callChangeListener(newValue)) {
					preferenceValue = newValue;
					persistString(preferenceValue);
				}
			}
		}
		super.onDialogClosed(positiveResult);
	}

}
