# 💣 Campo Minado — Java Swing

Implementação do clássico jogo **Campo Minado** em Java com interface gráfica desenvolvida com **Swing**, criado como trabalho prático da disciplina de Programação Orientada a Objetos.

---

## Sobre o projeto 🎮

O jogo replica o comportamento do Campo Minado original do Windows: o jogador deve identificar todas as minas escondidas no tabuleiro usando pistas numéricas, sem acionar nenhuma delas.

---

## Funcionalidades

- **3 níveis de dificuldade** selecionáveis pelo menu:

  | Nível | Tabuleiro | Minas |
  |-------|-----------|-------|
  | Fácil | 9 × 9 | 10 |
  | Médio | 16 × 16 | 40 |
  | Difícil | 30 × 16 | 99 |

- **Clique esquerdo** → abre a célula
- **Clique direito** → marca / desmarca com bandeira 🚩
- **Abertura em cascata** — ao clicar em uma célula vazia, todas as vizinhas vazias são abertas automaticamente
- **Contador de minas** com display estilo LCD
- **Botão de reset** com emoji que muda de estado (🙂 → 😎 ganhou / 😵 perdeu)
- **Revelação do resultado** ao fim da partida com cores indicando acertos e erros

---

## 🗂️ Estrutura das classes

```
CampoMinado/
├── Celula.java          # Representa cada célula do tabuleiro
│                        # Atributos: mina, aberta, marcada, valor
│
├── Posicao.java         # Guarda coordenadas (linha, coluna)
│                        # Usada para sortear posições das minas
│
├── CampoMinado.java     # Lógica principal do jogo
│                        # Inicializa campo, sorteia minas,
│                        # calcula números e verifica vitória
│
└── TelaJogo.java        # Interface gráfica (Swing)
                         # Tabuleiro, painel superior, menu,
                         # eventos de mouse e abertura em cascata
```

---

## 📸 Screenshots

**Imagens do jogo no modo fácil** 

![Jogo iniciado] <img width="1106" height="1116" alt="image" src="https://github.com/user-attachments/assets/dba487f7-3ac7-4efa-a472-f92b52955046" />

![Jogo em andamento] <img width="1102" height="1140" alt="image" src="https://github.com/user-attachments/assets/ea9b4dbc-4410-4ea4-8892-d26d7c72f808" />

![Ganhando o jogo] <img width="1103" height="1112" alt="image" src="https://github.com/user-attachments/assets/9dcd341c-4631-44d0-9895-9a3f767d5fd5" />

![Game Over !] <img width="1108" height="1107" alt="image" src="https://github.com/user-attachments/assets/d8a91121-d793-4dd7-8053-5df4c48cc65b" />

---

## ▶️ Como rodar

### Pré-requisitos

- **Java JDK 8** ou superior instalado
- **IntelliJ IDEA** (recomendado) ou qualquer IDE Java

### Pelo IntelliJ IDEA

1. Clone ou baixe o repositório
2. Abra o IntelliJ → **File → Open** → selecione a pasta do projeto
3. Aguarde o IntelliJ indexar o projeto
4. Abra `TelaJogo.java` e clique no botão ▶️ ao lado do método `main`

### Pela linha de comando

```bash
# Compilar
javac *.java

# Rodar
java TelaJogo
```

---

## 🛠️ Tecnologias

- **Java** — linguagem principal
- **Swing** — biblioteca gráfica para a interface
- **POO** — herança, encapsulamento e organização em classes

---

## 📚 Contexto acadêmico

Trabalho desenvolvido para a disciplina de **Programação Orientada a Objetos** — implementação completa do Campo Minado seguindo os requisitos do exercício proposto.
