# Pacific War - A Retro Vertical Shooter Game

**Project 1 - Vertical Shooter**  
**CSX4515 Game Design and Development**  
**Team CtrlZ**  
- Htoo Kyaw Kyaw Sint (6530129)  
- Yan Lin Tun (6530092)

[Watch Gameplay on YouTube](https://youtu.be/0G15Z9DVjdI)

---

## Game Overview

**Pacific War** is a retro-style 2D vertical shooter built with Java Swing. The game features two enemy types, a multi-stage progression system, power-ups, and intense boss battles over the ocean. Players must shoot enemies, dodge bombs, and collect upgrades to survive and reach the final stage.

---

## Features

- Player-controlled aircraft with upgradeable weapons
- Two enemy types: Normal and Zig-Zag enemies
- Enemy bomb attacks and boss projectiles
- Power-up system:
  - Speed Boost
  - Multi-Shot upgrade
- Smart spawning logic and stage-based progression
- Dynamic background music and sound effects
- HUD shows score, speed level, and shot level
- Scrolling ocean background
- Final victory screen after defeating Boss 3

---

## Controls

- **Left Arrow** – Move left  
- **Right Arrow** – Move right
- **Up Arrow** – Move forward (up)
- **Down Arrow** – Move downward  
- **Spacebar** – Shoot

---

## Game Structure

1. **Title Screen** – Press Space to Start
2. **Stage 1**
   - Enemies: Alien1(normal enemies)
   - Goal: Defeat 50 enemies
   - Boss: Mini Boss (Boss1 - 20 HP)
3. **Stage 2**
   - Enemies: Alien1 and ZigZagEnemy
   - Same enemies with higher spawn rate
   - Goal: Defeat 70 enemies
   - Boss: Mini Boss (Boss2 - 40 HP)
4. **Stage 3**
   - Final Boss: Boss3 (100 HP)
   - Victory screen upon defeating the boss

---

## Technical Details

- Built using **Java Swing**
- Object-oriented structure (`Scene`, `Player`, `Enemy`, `PowerUp`, etc.)
- Audio powered by Java Sound API
- Image and sprite management with `BufferedImage`
- Timer-based game loop and `repaint()` method
- Manual collision detection
- Game state management between stages

---

## How to Run

1. Ensure you have **Java JDK 11+** installed.
2. Clone this repository or download the ZIP.
3. Open the project in an IDE (like VS Code or IntelliJ).
4. Compile and run the `Main.java` file inside the `src/gdd` folder.

## File Structure

Pacific War/
├── src/
│   └── gdd/
│       ├── scene/
│       ├── sprite/
│       ├── powerup/
│       └── Global.java, Main.java, etc.
├── resources/
│   ├── images/
│   └── audio/
└── README.md

## Gameplay Demo

Watch the full gameplay video on YouTube:
https://youtu.be/0G15Z9DVjdI

