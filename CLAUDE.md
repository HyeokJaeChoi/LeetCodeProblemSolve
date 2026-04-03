# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

LeetCode 75 problem solutions written in Kotlin. This is an IntelliJ IDEA project (not Gradle/Maven).

## Build & Run

This project uses IntelliJ IDEA's built-in build system with the Kotlin JVM runtime library.

- **Build**: Use IntelliJ's built-in build (Build > Build Project), or compile manually:
  ```
  kotlinc src/Day1/Main.kt -include-runtime -d out/Main.jar
  ```
- **Run a solution**: Each day's solution has a `main` function. Run via IntelliJ or:
  ```
  kotlin -classpath out/production/LeetCode75 Day1.Main
  ```

## Code Structure

- `src/` — Source files organized by day/topic (e.g., `src/Day1/Main.kt`)
- Each solution is a Kotlin class with a `companion object` containing a `@JvmStatic main` entry point
- Solutions should follow the existing pattern: package per day, `Main.kt` with a `Main` class

## Code Comments

- All comments in solution code must be written in **English**
