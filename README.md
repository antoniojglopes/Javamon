# Javamon

A tile-based, Pokémon-inspired Java game prototype. Explore a 2D map, talk to NPCs, manage your team of “Javamons,” and use, buy and sell items from your bag - all built with plain Java.

**Goal**: a simple, readable codebase for learning/teaching core 2D game concepts in Java (tiles, collisions, entities, inventory/party systems).

## What’s done

- **World & Maps**
  - Tile map(s) and a basic **tile renderer**
  - **Collision** handling for solid tiles and objects

- **Core Entities**
  - **Player** character with movement and animation frames
  - **NPCs** (non-player characters) for interactions
  - **Javamon** (creature/monster) data model

- **Gameplay Systems**
  - **Team/Party** structure for Javamons
  - **Bag/Inventory** with **Items**
  - Early UI/front sprites integrated

- **Project Assets**
  - `lib/` for dependencies (if any)
  - (Jar present) `t02_4.jar` for quick launches
  - Resource folders expected under `res/` (images, maps, etc.)

## Why / What for

- **Learning**: Demonstrates classic 2D game architecture in Java.
- **Teaching**: Useful as a lab or course project foundation.
- **Prototyping**: A base to extend into a fuller creature-collection game (battles, wild encounters, saving, etc.).

## Getting started

### Prerequisites
- **Java 17+** (works with a modern JDK)
- Any Java-friendly IDE (IntelliJ IDEA, Eclipse, VS Code) or just the CLI.

### Run (Option A: IDE)
1. Clone the repo:
   ```bash
   git clone https://github.com/antoniojglopes/Javamon.git
   cd Javamon

2. Import as a Java project.

3. Ensure the res/ resource folders are included in the project’s classpath (as the project expects them to be present).

4. Run the main class (e.g., a Main/game launcher inside src/).

### Run (Option B: Jar)

If the packaged jar works on your machine:

```bash
java -jar t02_4.jar
```

If resources aren’t bundled, keep the res/ directory next to the jar so images/maps load.

## Project structure

    Javamon/
    ├─ src/ # Java source (player, NPC, Javamon, items, tiles, collisions, etc.)
    ├─ lib/ # Libraries (if used)
    ├─ res/ # Sprites, maps (e.g., map1.txt), and other assets (expected)
    ├─ t02_4.jar # Runnable build (prototype)
    └─ README.md

## Contributors

* António Lopes [@antoniojglopes](https://github.com/antoniojglopes)

* Francisco Camara [@FranciscoCamara](https://github.com/FranciscoCamara)

* Francisco Vilarinho [@franciscovilarinho](https://github.com/franciscovilarinho)