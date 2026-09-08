# Zombie Doom

A top-down survival shooter built with Java Swing. You are a lone survivor pinned
at the center of the map while zombies pour in from every edge. Aim with the
mouse, fire, and clear five escalating waves before your health runs out.

## Gameplay

- **The player** stands in the middle of the arena and rotates to face the mouse
  cursor.
- **Shooting** fires a bullet from the center toward the point you clicked.
- **Zombies** spawn from a random screen edge and walk straight at you, playing a
  two-frame walk animation. Touching the player drains health.
- **Score** goes up by one for every zombie killed.
- **Waves** advance every 10 kills. Each wave makes zombies faster and spawn more
  frequently:

  | Wave | Zombie speed | Spawn interval |
  |------|--------------|----------------|
  | 1    | 1.5          | 1000 ms        |
  | 2    | 2.0          | 800 ms         |
  | 3    | 2.5          | 600 ms         |
  | 4    | 3.0          | 400 ms         |
  | 5    | — clear it to win — |         |

- Reaching wave 5 shows **"You Won!"**. Losing all health shows **"Game Over!"**.

## Controls

| Input        | Action                        |
|--------------|-------------------------------|
| Mouse move   | Aim                           |
| Mouse click  | Shoot toward the cursor       |

## Requirements

- JDK 17 or newer
- Maven 3.6+

## Build & Run

Compile the project:

```bash
mvn clean compile
```

Run the game (the entry point is `davutcagri.Main`):

```bash
java -cp target/classes davutcagri.Main
```

Alternatively, open the project in IntelliJ IDEA (or any IDE with Maven support)
and run the `main` method in `src/main/java/davutcagri/Main.java`.

## Project Structure

```
src/main/java/davutcagri/
├── Main.java              # Sets up the JFrame and shows the menu
├── panel/
│   ├── MenuPanel.java     # Title screen with a Play button
│   └── GamePanel.java     # Game loop: spawning, updates, collisions, HUD
└── entity/
    ├── Player.java        # Fixed-position player, aims at the mouse
    ├── Zombie.java        # Homing movement, walk animation, hit box
    └── Bullet.java        # Straight-line projectile

src/main/resources/assets/  # background, player, and zombie sprites
```

### How it fits together

- `Main` creates the window and adds a `MenuPanel`.
- `MenuPanel`'s **Play** button swaps in a `GamePanel`.
- `GamePanel` drives three `javax.swing.Timer`s:
  - **game timer** (~10 ms) – moves entities, resolves bullet/zombie collisions,
    repaints, and checks win/lose state
  - **player timer** (500 ms) – applies contact damage from zombies touching the
    player
  - **zombie spawner** – creates a new zombie from a random edge on the current
    wave's interval

## Notes

- The game window is a fixed 800×600 and is not resizable.
- Sprites are loaded from the classpath (`/assets/...`), so run from the compiled
  output rather than pointing directly at source files.

## License

No license file is currently included; all rights reserved by the author.
