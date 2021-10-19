package cl.ciisa.todoapp.lib;

import android.util.Patterns;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import cl.ciisa.todoapp.utils.DateUtils;

public class TilValidator {
    private TextInputLayout til;
    private String value;
    private boolean required = false;
    private boolean invalid =  false;
    private SimpleDateFormat formatter = new SimpleDateFormat(DateUtils.DATE_PATTERN);

    public TilValidator(TextInputLayout til) {
        this.til = til;
        this.value = til.getEditText().getText().toString();
    }

    private void setError(boolean invalid, String message) {
        if (invalid) {
            this.invalid = true;
            til.setError(message);
        } else {
            til.setError(null);
            til.setErrorEnabled(false);
        }
    }

    private boolean mustValidate() {
        return (!required && !value.isEmpty()) || !invalid;
    }

    public TilValidator required() {
        this.required = true;
        boolean invalidField = value.isEmpty();
        this.setError(invalidField, "Campo requerido");
        return this;
    }

    public TilValidator date() {
        if (mustValidate()) {
            boolean invalidField = false;
            try {
                formatter.parse(value);
            } catch (Exception e) {
                invalidField = true;
            }
            this.setError(invalidField, String.format("La fecha es inválida. Formato esperado: %s", DateUtils.DATE_PATTERN));
        }
        return this;
    }

    public TilValidator number() {
        if (mustValidate()) {
            boolean invalidField = false;
            try {
                Double.parseDouble(value);
            } catch (Exception e) {
                invalidField = true;
            }
            this.setError(invalidField, "El valor debe ser un número");
        }
        return this;
    }

    public TilValidator strMax(int maxLength) {
        if (mustValidate()) {
            boolean invalidField = value.length() > maxLength;
            this.setError(invalidField, String.format("El valor no puede tener más de %d caracteres", maxLength));
        }
        return this;
    }

    public TilValidator strMin(int minLength) {
        if (mustValidate()) {
            boolean invalidField = value.length() < minLength;
            this.setError(invalidField, String.format("El valor no puede tener menos de %d caracteres", minLength));
        }
        return this;
    }

    public TilValidator numberMin(double min) {
        if (mustValidate()) {
            double doubleValue = Double.parseDouble(value);
            boolean invalidField = doubleValue < min;
            this.setError(invalidField, String.format("El valor no puede ser menor a %d", min));
        }
        return this;
    }

    public TilValidator numberMax(double max) {
        if (mustValidate()) {
            double doubleValue = Double.parseDouble(value);
            boolean invalidField = doubleValue > max;
            this.setError(invalidField, String.format("El valor no puede ser mayor a %d", max));
        }
        return this;
    }

    public TilValidator email() {
        if (mustValidate()) {
            boolean invalidField = !Patterns.EMAIL_ADDRESS.matcher(value).matches();
            this.setError(invalidField, String.format("El valor debe ser un email válido"));
        }
        return this;
    }

    public TilValidator dateAfter(Date date) {
        if (mustValidate()) {
            boolean invalidField;
            try {
                Date dateValue = formatter.parse(value);
                invalidField = !dateValue.after(date);
            } catch (Exception e) {
                invalidField = true;
            }
            this.setError(invalidField, String.format("La fecha no puede ser posterior a %s", formatter.format(date)));
        }
        return this;
    }

    public TilValidator dateBefore(Date date) {
        if (mustValidate()) {
            boolean invalidField;
            try {
                Date dateValue = formatter.parse(value);
                invalidField = !dateValue.before(date);
            } catch (Exception e) {
                invalidField = true;
            }
            this.setError(invalidField, String.format("La fecha no puede ser anterior a %s", formatter.format(date)));
        }
        return this;
    }

    public boolean isValid() {
        return !this.invalid;
    }
}
