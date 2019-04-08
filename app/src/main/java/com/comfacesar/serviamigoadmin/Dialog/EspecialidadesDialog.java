package com.comfacesar.serviamigoadmin.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.comfacesar.servimaigoadmin.R;

public class EspecialidadesDialog extends DialogFragment {
    private CheckBox saludSexualReproductivaCheckBox;
    private CheckBox identidadCheckBox;
    private CheckBox nutricionCheckBox;
    private CheckBox embarazadoCheckBox;
    private Button cancelarButton;
    private Button aceptarButton;
    private boolean saludSexualReproductivaSelecionada;
    private boolean identidadSelecionada;
    private boolean nutricionSelecionada;
    private boolean embarazoSelecionada;
    public EspecialidadesSelecionadas especialidadesSelecionadas;

    public static EspecialidadesDialog newIntancia(EspecialidadesSelecionadas especialidadesSelecionadas, boolean saludSexualReproductivaSelecionada, boolean identidadSelecionada, boolean nutricionSelecionada, boolean embarazoSelecionada)
    {
        EspecialidadesDialog especialidadesDialog = new EspecialidadesDialog();
        especialidadesDialog.setSaludSexualReproductivaSelecionada(saludSexualReproductivaSelecionada);
        especialidadesDialog.setIdentidadSelecionada(identidadSelecionada);
        especialidadesDialog.setNutricionSelecionada(nutricionSelecionada);
        especialidadesDialog.setEmbarazoSelecionada(embarazoSelecionada);
        especialidadesDialog.especialidadesSelecionadas = especialidadesSelecionadas;
        return especialidadesDialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogespecialidades, null);

        saludSexualReproductivaCheckBox = v.findViewById(R.id.salud_sexual_reprodutivaCheckBox);
        identidadCheckBox = v.findViewById(R.id.identidadCheckBox);
        nutricionCheckBox = v.findViewById(R.id.nutricionCheckBox);
        embarazadoCheckBox = v.findViewById(R.id.embarazoCheckBox);
        cancelarButton = v.findViewById(R.id.cancelarButton);
        aceptarButton = v.findViewById(R.id.aceptarButton);
        saludSexualReproductivaCheckBox.setChecked(saludSexualReproductivaSelecionada);
        identidadCheckBox.setChecked(identidadSelecionada);
        embarazadoCheckBox.setChecked(embarazoSelecionada);
        nutricionCheckBox.setChecked(nutricionSelecionada);
        saludSexualReproductivaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saludSexualReproductivaSelecionada = isChecked;
            }
        });
        identidadCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                identidadSelecionada = isChecked;
            }
        });
        embarazadoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                embarazoSelecionada = isChecked;
            }
        });
        nutricionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nutricionSelecionada = isChecked;
            }
        });
        aceptarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                especialidadesSelecionadas.especialidadesAceptadas(saludSexualReproductivaSelecionada, identidadSelecionada, nutricionSelecionada, embarazoSelecionada);
                dismiss();
            }
        });
        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(v);
        return builder.create();
    }
    public interface EspecialidadesSelecionadas {
        void especialidadesAceptadas(boolean saludSexual, boolean identidad, boolean nutricion, boolean embarazo);
    }

    public void setSaludSexualReproductivaSelecionada(boolean saludSexualReproductivaSelecionada) {
        this.saludSexualReproductivaSelecionada = saludSexualReproductivaSelecionada;
    }

    public void setIdentidadSelecionada(boolean identidadSelecionada) {
        this.identidadSelecionada = identidadSelecionada;
    }

    public void setNutricionSelecionada(boolean nutricionSelecionada) {
        this.nutricionSelecionada = nutricionSelecionada;
    }

    public void setEmbarazoSelecionada(boolean embarazoSelecionada) {
        this.embarazoSelecionada = embarazoSelecionada;
    }
}
