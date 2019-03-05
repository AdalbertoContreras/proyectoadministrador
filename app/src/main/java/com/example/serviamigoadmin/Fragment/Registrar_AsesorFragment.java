package com.example.serviamigoadmin.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.modelo.Especialidad;
import com.example.serviamigoadmin.Dialog.DatePickerFragment;
import com.example.servimaigoadmin.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Registrar_AsesorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Registrar_AsesorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registrar_AsesorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText nombreAsesorEditText;
    private EditText apellidosEditText;
    private EditText direcionEditText;
    private EditText telefonoEditText;
    private EditText fechaNacimientoEditText;
    private EditText correoElectronicoEditText;
    private EditText especialidadesEditText;
    private EditText nombreCuentaEditText;
    private EditText contraseñaEditText;
    private RadioButton femeninoRadioButton;
    private RadioButton masculinoRadioButton;
    private Button registrarButton;
    private ArrayList<Especialidad> especialidadArrayList;
    private View view;
    public Registrar_AsesorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registrar_AsesorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Registrar_AsesorFragment newInstance(String param1, String param2) {
        Registrar_AsesorFragment fragment = new Registrar_AsesorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_registrar_administrador, container, false);
        nombreAsesorEditText = view.findViewById(R.id.nombresAsesorEditText);
        apellidosEditText = view.findViewById(R.id.apellidosAsesorEditText);
        direcionEditText = view.findViewById(R.id.direccionAsesorEditText);
        telefonoEditText = view.findViewById(R.id.numeroTelefonoAsesorEditText);
        fechaNacimientoEditText = view.findViewById(R.id.fechaNacimientoAsesorEditText);
        correoElectronicoEditText = view.findViewById(R.id.correoElectronioAsesorEditText);
        especialidadesEditText = view.findViewById(R.id.verEspecialidadesTextView);
        nombreAsesorEditText = view.findViewById(R.id.nombresAsesorEditText);
        nombreAsesorEditText = view.findViewById(R.id.nombresAsesorEditText);
        iniciarEventos();
        return view;
    }

    private void showDatePickerDialog()
    {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = year + "-" + (month+1)  + "-" + day ;
                fechaNacimientoEditText.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public void iniciarEventos()
    {
        fechaNacimientoEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        fechaNacimientoEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    showDatePickerDialog();
                }
            }
        });
        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombreAsesorEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Ingrese los nombres del asesor", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(apellidosEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Ingrese los apellidos del asesor", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fechaNacimientoEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Ingrese la fecha de nacimiento del asesor", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(nombreCuentaEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Ingrese el nombre de cuenta del asesor", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(contraseñaEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Ingrese contraseña de la cuenta del asesor", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
