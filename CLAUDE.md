# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

LeetCode 75 problem solutions written in Kotlin. This is an IntelliJ IDEA project (not Gradle/Maven).

## Build & Run

This project uses IntelliJ IDEA's built-in build system with the Kotlin JVM runtime library.

- **Build**: Use IntelliJ's built-in build (Build > Build Project), or compile manually:
  ```
  kotlinc src/day1/Main.kt -include-runtime -d out/Main.jar
  ```
- **Run a solution**: Each day's solution has a `main` function. Run via IntelliJ or:
  ```
  kotlin -classpath out/production/LeetCode75 day1.Main
  ```

## Code Structure

- `src/` — Source files organized by day/topic (e.g., `src/day1/Main.kt`)
- Each solution is a Kotlin class with a `companion object` containing a `@JvmStatic main` entry point
- Solutions should follow the existing pattern: package per day, `Main.kt` with a `Main` class

## Problem Solving Guidance

- When the user asks about a coding problem, **only provide hints or approach guidance** — do not reveal the full solution
- Give the full solution only when the user explicitly says **"정답을 알려줘"**

## Study Plan Reference

- The user maintains a personal study plan at **`~/12-week-interview-prep-plan.md`** (`/Users/hyukjaechoi/12-week-interview-prep-plan.md`).
- When the user's message contains keywords related to studying, reviewing, or planning — including but not limited to **학습, 복습, 플랜, 계획, 스터디, study, review, plan, schedule** — you **MUST** read this file first and ground your answer in the guidelines, cadence, topics, and priorities it defines.
- Do not answer study/plan questions from general knowledge alone; always align recommendations (what to do next, how long to spend, which topic to pick, how to review) with this file.
- If the file is missing or unreadable, tell the user and ask how to proceed instead of guessing.

## Solution Code Immutability

- **NEVER modify the body of any solution function** (e.g., `longestOnes`, `longestSubarray`, or any problem-solving method) without the user's explicit instruction to do so.
- The `main` function (test runner) is the only exception — it may be freely added to or modified.
- If a bug is suspected in the solution code, **report it to the user** and wait for explicit approval before making any change.

## Code Comments

- All comments must be written in **KDoc format** (`/** ... */`) and in **English**
- Place the KDoc block at the **top of the file**, before the `package` declaration
- Do not use `@param` or `@return` tags
- Each solution must document Time Complexity and Space Complexity using this exact format:

```kotlin
/**
 * **Time Complexity: `O(...)`** where `n = ...`
 * - Explanation of dominant operations
 *
 * **Space Complexity: `O(...)`**
 * - Explanation of dominant memory usage
 */
package dayN
```
