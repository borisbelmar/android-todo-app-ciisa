package cl.ciisa.todoapp.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {
    private DatePickerDialog.OnDateSetListener listener;
    private Date initialDate;

    private DatePickerFragment(DatePickerDialog.OnDateSetListener listener, @Nullable Date initialDate) {
        this.listener = listener;
        this.initialDate = initialDate;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(initialDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

    public static void showDatePickerDialog(AppCompatActivity activity, final TextInputLayout til, Date initialDate) {
        DatePickerDialog.OnDateSetListener listener = (datePicker, year, month, day) -> {
            final String selectedDate = String.format("%d-%02d-%02d", year, (month + 1), day);
            til.getEditText().setText(selectedDate);
        };
        DatePickerFragment newFragment = new DatePickerFragment(listener, initialDate);
        newFragment.show(activity.getSupportFragmentManager(), "datePicker");
    }
}
