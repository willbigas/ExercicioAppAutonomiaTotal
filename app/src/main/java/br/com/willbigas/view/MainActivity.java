package br.com.willbigas.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.willbigas.R;
import br.com.willbigas.controller.CarroController;
import br.com.willbigas.controller.FrotaController;
import br.com.willbigas.model.Carro;
import br.com.willbigas.model.Frota;
import br.com.willbigas.util.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private CarroController carroController;
    private FrotaController frotaController;

    private Carro carro;
    private Frota frota;

    private EditText edtNomeCarro;
    private EditText edtKmPercorrida;
    private EditText edtQuantidadeCombustivel;

    private Button btnCalcular;
    private Button btnLimparDados;

    private TextView tvConsumoMedioFrota;

    private LinearLayout layoutTabelaCarros;

    private Integer indiceDeQuantidadeDeCarros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarControllers();
        inicializarComponentes();
        inicializarFrota();
        indiceDeQuantidadeDeCarros = 1;
    }

    private void inicializarControllers() {
        carroController = new CarroController();
        frotaController = new FrotaController();
    }

    private void inicializarFrota() {
        frota = new Frota(new ArrayList<Carro>(), 0.0);
    }

    private void inicializarComponentes() {
        edtNomeCarro = findViewById(R.id.edtNomeCarro);
        edtKmPercorrida = findViewById(R.id.edtKmPercorrida);
        edtQuantidadeCombustivel = findViewById(R.id.edtQtdCombustivel);

        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimparDados = findViewById(R.id.btnLimparDados);

        tvConsumoMedioFrota = findViewById(R.id.tvConsumoMedioFrota);

        layoutTabelaCarros = findViewById(R.id.layoutTabelaCarros);
    }

    public void calcular(View view) {
        if (validarDadosDaView()) {
            receberDadosDaView();
            carro = carroController.calcular(carro);
            frota = frotaController.calcular(frota);
            exibirDadosNaView();
            limparDados();
        }
    }

    public void limparDados() {
        edtNomeCarro.setText("");
        edtKmPercorrida.setText("");
        edtQuantidadeCombustivel.setText("");
        carro = new Carro();
    }

    public void limparDados(View view) {
        limparDados();
    }

    private boolean validarDadosDaView() {
        boolean foiValidado = true;

        if (edtNomeCarro.getText().toString() == null || edtNomeCarro.getText().toString().equals("")) {
            edtNomeCarro.setError(getString(R.string.required_nomeCarro));
            foiValidado = false;
        }

        if (edtKmPercorrida.getText().toString() == null || edtKmPercorrida.getText().toString().equals("")) {
            edtKmPercorrida.setError(getString(R.string.required_kmPercorrido));
            foiValidado = false;
        }

        if (edtQuantidadeCombustivel.getText().toString() == null || edtQuantidadeCombustivel.getText().toString().equals("")) {
            edtQuantidadeCombustivel.setError(getString(R.string.required_combustivelGasto));
            foiValidado = false;
        }
        return foiValidado;
    }

    private void receberDadosDaView() {
        carro = new Carro(indiceDeQuantidadeDeCarros, edtNomeCarro.getText().toString(), Double.valueOf(edtKmPercorrida.getText().toString()), Double.valueOf(edtQuantidadeCombustivel.getText().toString()));
        frota.getCarros().add(carro);
        indiceDeQuantidadeDeCarros++;
    }


    private void exibirDadosNaView() {
        TextView tvCarroDinamico = new TextView(this);
        tvCarroDinamico.setGravity(Gravity.CENTER);
        tvCarroDinamico.setText("Carro" + carro.getId() + " - " + DecimalFormat.deDecimalParaString(carro.getConsumoMedio()) + " km/L");
        layoutTabelaCarros.addView(tvCarroDinamico);
        tvConsumoMedioFrota.setText(DecimalFormat.deDecimalParaString(frota.getConsumoMedioDaFrota()) + " km/L");

    }


}
