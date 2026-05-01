package br.edu.ifsp.scl.sc3043959.fasttripplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale

class ResumoViagemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // recupera todos os dados passados pelas intents anteriores
        val destino = intent.getStringExtra(Constants.EXTRA_DESTINO) ?: ""
        val dias = intent.getIntExtra(Constants.EXTRA_DIAS, 0)
        val orcamento = intent.getDoubleExtra(Constants.EXTRA_ORCAMENTO, 0.0)
        val hospedagem = intent.getStringExtra(Constants.EXTRA_HOSPEDAGEM) ?: "Econômica"
        val transporte = intent.getBooleanExtra(Constants.EXTRA_TRANSPORTE, false)
        val alimentacao = intent.getBooleanExtra(Constants.EXTRA_ALIMENTACAO, false)
        val passeios = intent.getBooleanExtra(Constants.EXTRA_PASSEIOS, false)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Tela3ResumoViagem(
                        destino = destino,
                        dias = dias,
                        orcamento = orcamento,
                        hospedagem = hospedagem,
                        transporte = transporte,
                        alimentacao = alimentacao,
                        passeios = passeios
                    )
                }
            }
        }
    }
}

@Composable
fun Tela3ResumoViagem(
    destino: String, dias: Int, orcamento: Double, hospedagem: String,
    transporte: Boolean, alimentacao: Boolean, passeios: Boolean
) {
    val context = LocalContext.current

    val custoBase = dias * orcamento

    // define multiplicador baseado na escolha da hospedagem
    val multiplicador = when (hospedagem) {
        "Conforto" -> 1.5
        "Luxo" -> 2.2
        else -> 1.0 // Econômica
    }

    // calcula extras somando valores fixos e diários
    var totalExtras = 0.0
    if (transporte) totalExtras += 300.0
    if (alimentacao) totalExtras += (50.0 * dias)
    if (passeios) totalExtras += (120.0 * dias)

    val custoTotal = (custoBase * multiplicador) + totalExtras

    // Formatador de moeda padrao Brasil (pt-BR)
    val formatadorMoeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Resumo da Viagem", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // exibe os dados consolidados formatados
        Text(text = "Destino: $destino")
        Text(text = "Dias: $dias")
        Text(text = "Orçamento Diário: ${formatadorMoeda.format(orcamento)}")
        Text(text = "Hospedagem: $hospedagem")

        Text(text = "Serviços Contratados:")
        if (transporte) Text(text = "- Transporte")
        if (alimentacao) Text(text = "- Alimentação")
        if (passeios) Text(text = "- Passeios")
        if (!transporte && !alimentacao && !passeios) Text(text = "- Nenhum")

        Spacer(modifier = Modifier.height(32.dp))

        // exibe valor final formatado corretamente
        Text(
            text = "Total Estimado: ${formatadorMoeda.format(custoTotal)}",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // botao reiniciar volta para a tela inicial
        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java).apply {
                    // flags limpam a pilha de navegação, impedindo que o botao "voltar" do android retorne a esta tela
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reiniciar Planejamento")
        }
    }
}