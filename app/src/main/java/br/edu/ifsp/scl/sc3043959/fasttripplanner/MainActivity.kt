package br.edu.ifsp.scl.sc3043959.fasttripplanner

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Surface atua como um conteiner base que aplica as cores de fundo do tema.
                Surface(modifier = Modifier.fillMaxSize()) {
                    Tela1DadosViagem()
                }
            }
        }
    }
}

// @OptIn suprime avisos sobre APIs experimentais
// @Composable indica que a função descreve parte da UI. A interface é gerada de forma declarativa e reage automaticamente a mudanças de estado.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tela1DadosViagem() {
    // LocalContext necessário para acessar recursos do Android, como o Toast.
    val context = LocalContext.current

    // rememberSaveable salva o estado em um Bundle, garantindo que os dados não sejam perdidos quando a Activity for destruída e recriada (ex: ao girar a tela).
    var destino by rememberSaveable { mutableStateOf("") }
    var dias by rememberSaveable { mutableStateOf("") }
    var orcamento by rememberSaveable { mutableStateOf("") }

    // Column organiza os elementos de forma vertical na tela.
    Column(
        // Modifiers alteram a aparência e o comportamento do componente.
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center, // Centraliza os filhos verticalmente.
        horizontalAlignment = Alignment.CenterHorizontally // Centraliza os filhos horizontalmente.
    ) {
        Text(
            text = "Fast Trip Planner",
            style = MaterialTheme.typography.headlineMedium
        )

        // Spacer cria um espaço vazio na interface.
        Spacer(modifier = Modifier.height(32.dp))

        // captura o destino da viagem
        OutlinedTextField(
            value = destino,
            onValueChange = { destino = it },
            label = { Text("Destino") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // captura a qtd de dias
        OutlinedTextField(
            value = dias,
            onValueChange = { dias = it },
            label = { Text("Número de dias") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // captura o orcamento
        OutlinedTextField(
            value = orcamento,
            onValueChange = { orcamento = it },
            label = { Text("Orçamento diário (R$)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // valida os dados e avançar para a tela 2
        Button(
            onClick = {
                val diasInt = dias.toIntOrNull()
                val orcamentoDouble = orcamento.toDoubleOrNull()

                if (destino.isNotBlank() && diasInt != null && diasInt > 0 && orcamentoDouble != null && orcamentoDouble > 0) {

                    // cria um Intent para iniciar a OpcoesViagemActivity, passando os dados como extras
                    val intent = Intent(context, OpcoesViagemActivity::class.java).apply {
                        putExtra("destino", destino)
                        putExtra("dias", diasInt)
                        putExtra("orcamento", orcamentoDouble)
                    }
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "Por favor, preencha todos os campos com valores válidos.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Avançar")
        }
    }
}