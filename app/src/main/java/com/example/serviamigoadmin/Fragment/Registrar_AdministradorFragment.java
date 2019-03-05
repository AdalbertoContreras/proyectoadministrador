package com.example.serviamigoadmin.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.modelo.Administrador;
import com.example.serviamigoadmin.Dialog.EspecialidadesDialog;
import com.example.servimaigoadmin.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Registrar_AdministradorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Registrar_AdministradorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registrar_AdministradorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText nombresEditText;
    private EditText apellidosEditText;
    private EditText direccionEditText;
    private EditText telefonoEditText;
    private EditText correoElectronicoEditText;
    private EditText nombreCuentaEditText;
    private EditText contraseñaEditText;
    private Button especialidadesEditText;
    private RadioButton femeninoRadioButton;
    private RadioButton masculinoRadioButton;
    private Button registrarButton;
    private View view;
    private boolean sexualidarReproductivaSelecionada = false;
    private boolean identidadSelecionada = false;
    private boolean nutricionSelecionada = false;
    private boolean embarazoSelecionada = false;
    public Registrar_AdministradorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registrar_AdministradorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Registrar_AdministradorFragment newInstance(String param1, String param2) {
        Registrar_AdministradorFragment fragment = new Registrar_AdministradorFragment();
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
        nombresEditText = view.findViewById(R.id.nombre_usuarioTextView);
        apellidosEditText = view.findViewById(R.id.apellidosAsesorEditText);
        correoElectronicoEditText = view.findViewById(R.id.correoElectronioAsesorEditText);
        telefonoEditText = view.findViewById(R.id.numeroTelefonoAsesorEditText);
        direccionEditText = view.findViewById(R.id.direccionAsesorEditText);
        especialidadesEditText = view.findViewById(R.id.verEspecialidadesTextView);
        nombreCuentaEditText = view.findViewById(R.id.nombreCuentaAsesorEditText);
        contraseñaEditText = view.findViewById(R.id.contraseñaAsesorEditText);
        registrarButton = view.findViewById(R.id.registrarAsesorButton);
        masculinoRadioButton = view.findViewById(R.id.masculinoAsesorRadioButton);
        femeninoRadioButton = view.findViewById(R.id.femeninoAsesorRadioButton);
        especialidadesEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEspecialidades();
            }
        });
        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombresEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view.getContext(), "Ingrese los nombres del asesor", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(apellidosEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view.getContext(), "Ingrese los apellidos del asesor", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!sexualidarReproductivaSelecionada && !identidadSelecionada && !nutricionSelecionada && !embarazoSelecionada)
                {
                    Toast.makeText(view.getContext(), "Escoja una o varias especialidades para el asesor", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(nombreCuentaEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view.getContext(), "Ingrese el nombre de la cuenta del asesor", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(contraseñaEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view.getContext(), "Ingrese la contraseña de la cuenta del asesor", Toast.LENGTH_SHORT).show();
                    return;
                }
                Administrador administrador = new Administrador();
                administrador.nombres_administrador = nombresEditText.getText().toString();
                administrador.apellidos_administrador = apellidosEditText.getText().toString();
                administrador.numero_telefono_administrador = telefonoEditText.getText().toString();
                administrador.direccion_administrador = direccionEditText.getText().toString();
                administrador.correo_electronico_administrador = correoElectronicoEditText.getText().toString();
                if(masculinoRadioButton.isChecked())
                {
                    administrador.sexo_administrador = 0;
                }
                else
                {
                    administrador.sexo_administrador = 1;
                }
                administrador.nombre_cuenta_administrador = nombreCuentaEditText.getText().toString();
                administrador.contrasena_administrador = contraseñaEditText.getText().toString();
            }
        });
        return view;
    }

    private void showEspecialidades()
    {
        EspecialidadesDialog newFragment = EspecialidadesDialog.newIntancia(new EspecialidadesDialog.EspecialidadesSelecionadas() {
            @Override
            public void especialidadesAceptadas(boolean saludSexual, boolean identidad, boolean nutricion, boolean embarazo) {
                sexualidarReproductivaSelecionada = saludSexual;
                identidadSelecionada = identidad;
                nutricionSelecionada = nutricion;
                embarazoSelecionada = embarazo;
            }
        }, sexualidarReproductivaSelecionada, identidadSelecionada, nutricionSelecionada, embarazoSelecionada);
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
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

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.create();
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
