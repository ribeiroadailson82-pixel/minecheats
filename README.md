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
| GUI       | ClickGUI    | `Right Shift` | Abre o menu para ligar/desligar tudo. |

Os módulos ligados aparecem numa lista no canto superior direito da tela.

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
