package com.comfacesar.serviamigoadmin.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.comfacesar.servimaigoadmin.R;

public class MensajeOpciones extends DialogFragment {
    public String mensaje;
    public FragmentManager fragmentManager;
    public static MensajeOpciones nuevaUbstancia(String mensaje, EscuchadorDialog escuchadorDialog)
    {
        MensajeOpciones mensajeInicioSesionDialog = new MensajeOpciones();
        mensajeInicioSesionDialog.mensaje = mensaje;
        mensajeInicioSesionDialog.escuchadorDialog = escuchadorDialog;
        return mensajeInicioSesionDialog;
    }

    public MensajeOpciones()
    {

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.mensaje_opciones, null);

        TextView mensajeTextView;
        mensajeTextView = v.findViewById(R.id.mensajeTextView);
        mensajeTextView.setText(mensaje);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.mensaje_opciones, null))
                // Add action buttons
                .setPositiveButton("Acepttar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        escuchadorDialog.positivo();
                        dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        escuchadorDialog.negativo();
                        dismiss();
                    }
                });
        builder.setView(v);
        return builder.create();
    }
    private EscuchadorDialog escuchadorDialog;
    public interface EscuchadorDialog
    {
        void positivo();
        void negativo();
    }
}
