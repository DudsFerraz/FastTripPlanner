package br.edu.ifsp.scl.sc3043959.fasttripplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class OpcoesViagemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // recebe os parametros passados pela intent da Activity origem
        val destino = intent.getStringExtra(Constants.EXTRA_DESTINO) ?: ""
        val dias = intent.getIntExtra(Constants.EXTRA_DIAS, 0)
        val orcamento = intent.getDoubleExtra(Constants.EXTRA_ORCAMENTO, 0.0)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Tela2OpcoesViagem(
                        destino = destino,
                        dias = dias,
                        orcamento = orcamento,
                        onVoltar = { finish() } // finish() encerra a Activity atual, retornando a anterior
                    )
                }
            }
        }
    }
}

// @Composable descreve a UI da Tela 2
@Composable
fun Tela2OpcoesViagem(destino: String, dias: Int, orcamento: Double, onVoltar: () -> Unit) {
    val context = LocalContext.current

    // estado para armazenar a escolha de hospedagem
    var opcoesHospedagem by rememberSaveable { mutableStateOf(listOf("Econômica", "Conforto", "Luxo")) }
    var hospedagemSelecionada by rememberSaveable { mutableStateOf("Econômica") }

    // estados para armazenar a selecao de servicos extras
    var transporte by rememberSaveable { mutableStateOf(false) }
    var alimentacao by rememberSaveable { mutableStateOf(false) }
    var passeios by rememberSaveable { mutableStateOf(false) }

    var modoEconomico by rememberSaveable { mutableStateOf(false) }

    // Column organiza os elementos na vertical
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Opções da Viagem", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        //Modo economico
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { modoEconomico = !modoEconomico }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = modoEconomico,
                onCheckedChange = {
                    modoEconomico = it
                    if (modoEconomico) {
                        passeios = false
                        hospedagemSelecionada = "Econômica"
                        opcoesHospedagem = listOf("Econômica")
                    } else {
                        opcoesHospedagem = listOf("Econômica", "Conforto", "Luxo")
                    }
                }
            )
            Text(text = "Modo econômico", modifier = Modifier.padding(start = 8.dp))
        }


        // secao de hospedagem
        Text(text = "Hospedagem", style = MaterialTheme.typography.titleMedium)
        opcoesHospedagem.forEach { opcao ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !modoEconomico || opcao == "Econômica") { hospedagemSelecionada = opcao } // torna a linha inteira clicavel
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (opcao == hospedagemSelecionada),
                    onClick = null, // click delegado para a Row
                    enabled = !modoEconomico || opcao == "Econômica"
                )
                Text(text = opcao, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // secao de servicos adicionais
        Text(text = "Serviços Adicionais", style = MaterialTheme.typography.titleMedium)
        ServicoExtraCheckbox("Transporte (+ R$ 300)", transporte, enabled = true) { transporte = it }
        ServicoExtraCheckbox("Alimentação (+ R$ 50/dia)", alimentacao, enabled = true) { alimentacao = it }
        ServicoExtraCheckbox("Passeios (+ R$ 120/dia)", passeios, enabled = !modoEconomico) { passeios = it }

        Spacer(modifier = Modifier.height(32.dp))

        // Row organiza os botoes lado a lado
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // botao voltar
            Button(onClick = onVoltar, modifier = Modifier.weight(1f)) {
                Text("Voltar")
            }

            Spacer(modifier = Modifier.width(16.dp))

            // botao calcular
            Button(
                onClick = {
                    val intent = Intent(context, ResumoViagemActivity::class.java).apply {
                        putExtra(Constants.EXTRA_DESTINO, destino)
                        putExtra(Constants.EXTRA_DIAS, dias)
                        putExtra(Constants.EXTRA_ORCAMENTO, orcamento)
                        putExtra(Constants.EXTRA_HOSPEDAGEM, hospedagemSelecionada)
                        putExtra(Constants.EXTRA_TRANSPORTE, transporte)
                        putExtra(Constants.EXTRA_ALIMENTACAO, alimentacao)
                        putExtra(Constants.EXTRA_PASSEIOS, passeios)
                        putExtra(Constants.EXTRA_MODO_ECONOMICO, modoEconomico)
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Calcular")
            }
        }
    }
}

// Componente customizado para evitar repeticao de codigo (DRY)
@Composable
private fun ServicoExtraCheckbox(texto: String, checked: Boolean, enabled: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onCheckedChange(!checked) } // torna a linha inteira clicavel
            .padding(vertical = 8.dp), // margem aumentada para melhorar a area de touch
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null, // click delegado para a Row
            enabled = enabled
        )
        Text(text = texto, modifier = Modifier.padding(start = 8.dp))
    }
}