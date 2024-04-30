package br.edu.fateczl.projetocalcularidade;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText edtDia;
    private EditText edtMes;
    private EditText edtAno;
    private TextView tvIdade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtDia = findViewById(R.id.edtDia);
        edtDia.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        edtMes = findViewById(R.id.edtMes);
        edtMes.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        edtAno = findViewById(R.id.edtAno);
        edtAno.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tvIdade = findViewById(R.id.tvIdade);
        tvIdade.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        Button btnCalc = findViewById(R.id.btnCalc);


        int maxLength = 2;
        edtDia.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        edtMes.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        maxLength = 4; // para o ano
        edtAno.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});


        btnCalc.setOnClickListener(op -> calcularIdade());


    }
    private void calcularIdade(){

        if (edtDia.getText().toString().isEmpty() || edtMes.getText().toString().isEmpty() || edtAno.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        int dia = Integer.parseInt(edtDia.getText().toString());
        int mes = Integer.parseInt(edtMes.getText().toString());
        int ano = Integer.parseInt(edtAno.getText().toString());

        if (mes < 1 || mes > 12) {
            Toast.makeText(this, "Mês inválido. Por favor, insira entre 1 e 12.", Toast.LENGTH_SHORT).show();
            return;
        }

        int maxDiasNoMes = diasNoMes  (mes, ano);
        if (dia < 1 || dia > maxDiasNoMes) {
            Toast.makeText(this, "Dia inválido. Por favor, insira um valor entre 1 e " + maxDiasNoMes + " para o mês selecionado.", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar hoje = Calendar.getInstance();
        int anoAtual = hoje.get(Calendar.YEAR);
        int mesAtual = hoje.get(Calendar.MONTH) + 1;
        int diaAtual = hoje.get(Calendar.DAY_OF_MONTH);

        int idadeAnos = anoAtual - ano;
        int idadeMeses = mesAtual - mes;
        int idadeDias = diaAtual - dia;

        if (idadeDias < 0) {
            idadeMeses--;
            idadeDias += maxDiasNoMes;
        }
        if (idadeMeses < 0) {
            idadeAnos--;
            idadeMeses += 12;
        }

        if (idadeAnos < 0 || (idadeAnos == 0 && idadeMeses < 0)) {
            Toast.makeText(this, "Por favor, insira uma data de nascimento válida.", Toast.LENGTH_SHORT).show();
            return;
        }
              boolean bissexto = (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);

        tvIdade.setText("Idade: " + idadeAnos + " anos, " + idadeMeses + " meses e " + idadeDias + " dias.");
    }
    private int diasNoMes(int mes, int ano) {
        switch (mes) {
            case 2:
                return (bissexto(ano) ? 29 : 28);
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    private boolean bissexto(int ano) {
        return (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);
    }
}
