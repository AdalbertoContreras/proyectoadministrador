package com.example.serviamigoadmin.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_administrador;
import com.example.modelo.Administrador;
import com.example.serviamigoadmin.Dialog.DatePickerFragment;
import com.example.serviamigoadmin.Dialog.EspecialidadesDialog;
import com.example.servimaigoadmin.R;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Actualizar_AdministradorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Actualizar_AdministradorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Actualizar_AdministradorFragment extends Fragment {
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
    private EditText fechaNacimientoEditText;
    private RadioButton femeninoRadioButton;
    private RadioButton masculinoRadioButton;
    private Button registrarButton;
    private View view;
    public Actualizar_AdministradorFragment() {
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
    public static Actualizar_AdministradorFragment newInstance(String param1, String param2) {
        Actualizar_AdministradorFragment fragment = new Actualizar_AdministradorFragment();
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
        view =  inflater.inflate(R.layout.actualizar_mis_datos, container, false);
        nombresEditText = view.findViewById(R.id.nombresAsesorEditText);
        apellidosEditText = view.findViewById(R.id.apellidosAsesorEditText);
        fechaNacimientoEditText = view.findViewById(R.id.fechaNacimientoAsesorEditText);
        correoElectronicoEditText = view.findViewById(R.id.correoElectronioAsesorEditText);
        telefonoEditText = view.findViewById(R.id.numeroTelefonoAsesorEditText);
        direccionEditText = view.findViewById(R.id.direccionAsesorEditText);
        registrarButton = view.findViewById(R.id.registrarAsesorButton);
        masculinoRadioButton = view.findViewById(R.id.masculinoAsesorRadioButton);
        femeninoRadioButton = view.findViewById(R.id.femeninoAsesorRadioButton);
        fechaNacimientoEditText.setFocusable(false);
        fechaNacimientoEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    showDatePickerDialog();
                }
            }
        });
        fechaNacimientoEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombresEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view.getContext(), "Ingrese sus nombres", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(apellidosEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view.getContext(), "Ingrese sus apellidos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fechaNacimientoEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(view.getContext(), "Ingrese su fecha de nacimiento", Toast.LENGTH_SHORT).show();
                    return;
                }
                Administrador administrador = new Administrador();
                administrador.id_administrador = Gestion_administrador.getAdministrador_actual().id_administrador;
                administrador.nombres_administrador = nombresEditText.getText().toString();
                administrador.apellidos_administrador = apellidosEditText.getText().toString();
                administrador.numero_telefono_administrador = telefonoEditText.getText().toString();
                administrador.direccion_administrador = direccionEditText.getText().toString();
                administrador.correo_electronico_administrador = correoElectronicoEditText.getText().toString();
                administrador.fecha_nacimiento_administrador = fechaNacimientoEditText.getText().toString();
                if(masculinoRadioButton.isChecked())
                {
                    administrador.sexo_administrador = 0;
                }
                else
                {
                    administrador.sexo_administrador = 1;
                }
                registrar_administrador(administrador);
            }
        });
        cargar_datos_administrador();
        return view;
    }

    private void cargar_datos_administrador()
    {
        Administrador administrador = Gestion_administrador.getAdministrador_actual();
        nombresEditText.setText(administrador.nombres_administrador);
        apellidosEditText.setText(administrador.apellidos_administrador);
        direccionEditText.setText(administrador.direccion_administrador);
        telefonoEditText.setText(administrador.numero_telefono_administrador);
        correoElectronicoEditText.setText(administrador.correo_electronico_administrador);
        fechaNacimientoEditText.setText(administrador.fecha_nacimiento_administrador);
        if(administrador.sexo_administrador == 0)
        {
            masculinoRadioButton.setChecked(true);
        }
        else
        {
            femeninoRadioButton.setChecked(true);
        }
    }

    private void registrar_administrador(final Administrador administrador)
    {
        final HashMap<String, String> hashMap = new Gestion_administrador().actualizar_datos(administrador);
        Log.d("parametros", hashMap.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    int val = Integer.parseInt(response);
                    if(val > 0)
                    {
                        Toast.makeText(getContext(), "Cuenta actualizada con exito", Toast.LENGTH_SHORT).show();
                        Gestion_administrador.setAdministrador_actual(administrador);
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Cuenta no actualizada", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (NumberFormatException exc)
                {
                    Toast.makeText(getContext(), "Ha ocurrido un error en el servidor", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Ha ocurrido un error en el servidor", Toast.LENGTH_SHORT).show();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view.getContext()).addToRequestQueue(stringRequest);
    }

    private void showDatePickerDialog()
    {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                fechaNacimientoEditText.setText(year + "-" + (month+1)  + "-" + day );
            }
        });
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
