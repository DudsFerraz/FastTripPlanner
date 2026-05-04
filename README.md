# FastTripPlanner

Aplicativo Android desenvolvido como exercício prático para a disciplina de Programação para Dispositivos Móveis - IFSP - Câmpus São Carlos.

## 📱 Sobre o Projeto

O FastTripPlanner permite aos usuários planejar os custos de uma viagem com base em multiplicadores e serviços extras. O fluxo é dividido em três telas:
1. **Dados da Viagem:** Coleta o destino, quantidade de dias e orçamento diário.
2. **Opções da Viagem:** Permite a escolha do padrão de hospedagem e a adição de serviços extras (transporte, alimentação, passeios).
3. **Resumo:** Exibe os dados consolidados e o custo total estimado, com a opção de reiniciar o planejamento.

## 🛠 Tecnologias e Arquitetura

*   **Linguagem:** Kotlin
*   **UI Toolkit:** Jetpack Compose (Programação Declarativa)
*   **Gerenciamento de Estado:** Uso de `rememberSaveable` para retenção de dados durante a rotação da tela.
*   **Navegação:** Uso de Intents explícitas para transição entre Activities.

## ✨ Boas Práticas Implementadas 

Além dos requisitos básicos da especificação, este projeto conta com:
*   **Single Source of Truth:** Utilização de um *Object* `Constants` para centralizar as chaves de Intents, evitando *magic strings* e prevenindo bugs em tempo de compilação.
*   **Princípio DRY (Don't Repeat Yourself):** Extração de componentes de interface repetitivos para funções `@Composable` privadas e reutilizáveis.

## 👨‍💻 Autor

Eduardo Oliveira Ferraz de Campos