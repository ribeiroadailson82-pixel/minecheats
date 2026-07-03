# MineCheats

Mod **client-side** de utilidades/cheats para **Minecraft 1.21.11** rodando em
**NeoForge**. Você baixa o `.jar`, coloca na pasta `mods/` do seu cliente e usa
num servidor/mundo **LAN ou privado seu**.

> **Uso pretendido:** seu próprio servidor Paper LAN / mundo privado. Cheats de
> cliente violam as regras da maioria dos servidores públicos e de anti-cheats —
> use só onde você tem permissão.

## Importante: "mod de cliente" x "plugin de servidor"

Cheats visuais e de movimento (fly, killaura, night vision, etc.) rodam no
**cliente** de cada jogador. Por isso este é um **mod de cliente** que vai na
pasta `mods/`, e **não** um plugin do servidor Paper (a pasta `plugins/`).
O servidor Paper continua rodando normal; cada jogador que quiser os cheats
instala este mod no próprio cliente.

## Loader: NeoForge (não Forge legado)

Para 1.21.11 o loader Forge-family ativo e padrão é o **NeoForge**. O Forge
legado tem uma versão para 1.21.11, mas o toolchain de build dele está quebrado
para essa versão (bug de entrada duplicada `mcp/client/Start.class`). Este mod é
compilado para **NeoForge 21.11.42+**. Um jar de NeoForge **não** carrega num
cliente de Forge legado (e vice-versa) — instale o cliente **NeoForge 1.21.11**.

## Instalação

1. Instale o **NeoForge 1.21.11** (versão `21.11.42` ou mais nova) no seu launcher.
2. Copie `minecheats-neoforge-1.21.11-1.0.0.jar` para a pasta `mods/` do perfil.
3. Abra o jogo, entre no seu mundo/LAN.
4. Aperte **Right Shift** para abrir o ClickGUI e ligar/desligar os módulos,
   ou use as teclas de atalho abaixo.

## Módulos e teclas padrão

| Categoria | Módulo      | Tecla | O que faz |
|-----------|-------------|-------|-----------|
| Combat    | KillAura    | `R`   | Ataca automaticamente entidades próximas (respeita o cooldown de ataque). |
| Combat    | AutoClicker | `C`   | Clica esquerdo automático no que estiver na mira (minerar/atacar). |
| Movement  | Flight      | `F`   | Voo estilo criativo na sobrevivência. |
| Movement  | Speed       | `G`   | Anda mais rápido no chão na direção do movimento. |
| Movement  | Sprint      | `U`   | Sempre corre ao andar para frente. |
| Movement  | NoFall      | `N`   | Remove dano de queda. |
| Movement  | Step        | `J`   | Sobe blocos inteiros como um degrau. |
| Player    | FastBreak   | `B`   | Quebra blocos muito mais rápido. |
| Player    | FastPlace   | `V`   | Remove o delay de clique direito ao colocar. |
| Render    | FullBright  | `H`   | Visão noturna permanente (enxerga no escuro). |
| Testing   | AntiDetect  | `K`   | Liga o perfil de teste evasivo (veja abaixo). |
| GUI       | ClickGUI    | `Right Shift` | Abre o menu para ligar/desligar tudo. |

Os módulos ligados aparecem numa lista no canto superior direito da tela.

## AntiDetect — perfil de teste para o seu anti-cheat

`AntiDetect` (`K`) é um **auxílio de teste (red-team)** para exercitar o **seu
próprio** anti-cheat: com ele ligado, os módulos trocam para um comportamento
que tenta parecer humano/plausível, para você medir **até onde o seu detector
ainda pega**. Não é garantia de indetectabilidade — são técnicas de evasão
conhecidas, cada uma com o sinal que ela **deveria** disparar documentado, para
você validar seus checks.

| Módulo      | Comportamento evasivo (AntiDetect ligado) | Sinal que o anti-cheat deve pegar |
|-------------|-------------------------------------------|-----------------------------------|
| KillAura    | Reach limitado a 3.0, rotação real suavizada (máx 30°/tick), ataque só dentro do cone de visão | Aceleração angular anormal, aim colando no centro do hitbox, rotação sempre precedendo o ataque no mesmo padrão |
| AutoClicker | Intervalo de clique com jitter (1-3 ticks, ~7-15 CPS) | Desvio-padrão do intervalo, autocorrelação, cliques que nunca pausam |
| Speed       | Velocidade limitada a ~0.32 b/tick (pouco acima do sprint vanilla) | Velocidade horizontal sustentada acima do teto de sprint, velocidade descorrelacionada de sprint/efeito |
| NoFall      | (sem evasão — spoof de `onGround`) | `onGround=true` enquanto a posição mostra queda |
| Flight      | (sem evasão — habilidade de voo) | Tempo de ar sustentado sem suporte, `allow-flight=false` |

Fluxo de teste sugerido: ligue um módulo com AntiDetect **desligado** (deve
disparar o seu anti-cheat facilmente); depois ligue AntiDetect e veja se ainda
dispara. O que passar despercebido é o buraco a fechar no seu detector.

## Observações honestas

- **Anti-cheat / servidor vanilla:** módulos como Flight e Speed dependem do
  servidor aceitar o movimento. Em LAN ("Abrir para LAN") o host manda; em
  servidores com anti-cheat ou `allow-flight=false` você pode tomar rubberband
  ou kick. Ideal para mundo/LAN seu.
- **ESP / Tracers não incluídos nesta versão:** o sistema de renderização de
  mundo foi reescrito no 1.21.11 (novo pipeline da Mojang), então desenho de
  caixas/linhas no mundo ficou de fora do v1 para garantir um build estável.
  FullBright (que não precisa de render custom) está incluído.

## Build a partir do código

Requer JDK 21.

```bash
./gradlew build
# saída: build/libs/minecheats-neoforge-1.21.11-1.0.0.jar
```

Rodar o cliente de dev (precisa de ambiente gráfico):

```bash
./gradlew runClient
```
