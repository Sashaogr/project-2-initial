# Reflections Log

This document serves as a log of reflections on various topics, capturing insights, lessons learned, and personal growth. It is intended to help users track their thoughts and experiences over time.
# Project 2 Reflection — 3D Shape Analysis System
**Course:** CSC 205  
**Project:** Polymorphism & Abstract Classes  
**Date:** 02/26/26

---

## Table of Contents
1. [Mathematical Verification](#1-mathematical-verification)
2. [Code Quality Review](#2-code-quality-review)
3. [AI Output Analysis](#3-ai-output-analysis)
4. [Lessons Learned](#4-lessons-learned)

---

## 1. Mathematical Verification

### Verification Process

Every formula was cross-checked against three independent sources:

- **Wolfram MathWorld** — authoritative mathematical definitions
- **Online 3D geometry calculators** (calculator.net, omnicalculator.com)
- **Manual derivation** using first principles, then confirming against JUnit test values

Each shape class was verified by plugging known "nice" input values into both the calculator and the Java code, then confirming the outputs matched to at least 4 decimal places.

---

### 1.1 Sphere

**Formulas:**
```
Surface Area = 4 π r²
Volume       = (4/3) π r³
Diameter     = 2r
```

**Verification Table (radius = 3.0):**

| Metric | Formula Result | Calculator Result | Java Output | Match? |
|--------|---------------|-------------------|-------------|--------|
| Surface Area | 4 × π × 9 = 113.0973 | 113.0973 | 113.0973 | ✅ |
| Volume | (4/3) × π × 27 = 113.0973 | 113.0973 | 113.0973 | ✅ |
| Diameter | 2 × 3 = 6.0 | 6.0 | 6.0 | ✅ |

> **Notable:** For a sphere with radius 3, Surface Area and Volume produce the *same numerical value* (113.0973). This is a well-known mathematical coincidence at r=3 and served as a useful sanity check. It does NOT mean SA always equals V — this is only true at r=3 for a sphere.

**Verification with radius = 5.0:**

| Metric | Expected | Java Output | Match? |
|--------|----------|-------------|--------|
| Surface Area | 4 × π × 25 = 314.1593 | 314.1593 | ✅ |
| Volume | (4/3) × π × 125 = 523.5988 | 523.5988 | ✅ |

---

### 1.2 Cube

**Formulas:**
```
Surface Area  = 6s²
Volume        = s³
Space Diagonal = s√3
```

**Verification Table (side = 3.0):**

| Metric | Formula Result | Calculator Result | Java Output | Match? |
|--------|---------------|-------------------|-------------|--------|
| Surface Area | 6 × 9 = 54.0 | 54.0 | 54.0 | ✅ |
| Volume | 3³ = 27.0 | 27.0 | 27.0 | ✅ |
| Space Diagonal | 3 × √3 = 5.1962 | 5.1962 | 5.1962 | ✅ |

**Verification with side = 4.0:**

| Metric | Expected | Java Output | Match? |
|--------|----------|-------------|--------|
| Surface Area | 6 × 16 = 96.0 | 96.0 | ✅ |
| Volume | 64.0 | 64.0 | ✅ |
| Space Diagonal | 4√3 ≈ 6.9282 | 6.9282 | ✅ |

---

### 1.3 Cylinder

**Formulas:**
```
Total Surface Area  = 2πr² + 2πrh   (2 circular caps + lateral face)
Lateral Surface Area = 2πrh
Volume              = πr²h
```

**Verification Table (radius = 3.0, height = 7.0):**

| Metric | Formula Result | Calculator Result | Java Output | Match? |
|--------|---------------|-------------------|-------------|--------|
| Total Surface Area | 2π(9) + 2π(3)(7) = 56.5487 + 131.9469 = 188.4956 | 188.4956 | 188.4956 | ✅ |
| Lateral Surface Area | 2π(3)(7) = 131.9469 | 131.9469 | 131.9469 | ✅ |
| Volume | π(9)(7) = 197.9203 | 197.9203 | 197.9203 | ✅ |

**Cross-check:** Total SA = Lateral SA + 2 × (π × r²)  
= 131.9469 + 2 × (π × 9) = 131.9469 + 56.5487 = **188.4956** ✅

**Verification with radius = 1.0, height = 1.0 (unit cylinder):**

| Metric | Expected | Java Output | Match? |
|--------|----------|-------------|--------|
| Total Surface Area | 4π ≈ 12.5664 | 12.5664 | ✅ |
| Volume | π ≈ 3.1416 | 3.1416 | ✅ |

---

### 1.4 Rectangular Prism

**Formulas:**
```
Surface Area  = 2(lw + lh + wh)
Volume        = l × w × h
Space Diagonal = √(l² + w² + h²)
```

**Verification Table (l=6, w=4, h=3):**

| Metric | Formula Result | Calculator Result | Java Output | Match? |
|--------|---------------|-------------------|-------------|--------|
| Surface Area | 2(24 + 18 + 12) = 2(54) = 108.0 | 108.0 | 108.0 | ✅ |
| Volume | 6 × 4 × 3 = 72.0 | 72.0 | 72.0 | ✅ |
| Space Diagonal | √(36+16+9) = √61 ≈ 7.8102 | 7.8102 | 7.8102 | ✅ |

**Special case — cube equivalence (l=w=h=4):**  
When all sides equal, RectangularPrism should match Cube exactly.

| Metric | Cube(4) | RectPrism(4,4,4) | Match? |
|--------|---------|------------------|--------|
| Surface Area | 96.0 | 96.0 | ✅ |
| Volume | 64.0 | 64.0 | ✅ |

---

### 1.5 Pyramid (Square Base)

**Formulas:**
```
Slant Height  = √(h² + (s/2)²)
Surface Area  = s² + 2s × slantHeight   (base + 4 triangular faces)
Volume        = (1/3) × s² × h
```

**Verification Table (base = 5.0, height = 8.0):**

Step 1 — Slant Height:
```
slantHeight = √(8² + (5/2)²) = √(64 + 6.25) = √70.25 ≈ 8.3815
```

| Metric | Formula Result | Calculator Result | Java Output | Match? |
|--------|---------------|-------------------|-------------|--------|
| Slant Height | √70.25 ≈ 8.3815 | 8.3815 | 8.3815 | ✅ |
| Surface Area | 25 + 2(5)(8.3815) = 25 + 83.815 = 108.8148 | 108.8148 | 108.8148 | ✅ |
| Volume | (1/3)(25)(8) = 66.6667 | 66.6667 | 66.6667 | ✅ |

**Verification with base = 3.0, height = 4.0:**

| Metric | Expected | Java Output | Match? |
|--------|----------|-------------|--------|
| Slant Height | √(16 + 2.25) = √18.25 ≈ 4.2720 | 4.2720 | ✅ |
| Surface Area | 9 + 2(3)(4.2720) = 9 + 25.6320 = 34.6320 | 34.6320 | ✅ |
| Volume | (1/3)(9)(4) = 12.0 | 12.0 | ✅ |

---

### Verification Summary

| Shape | Formulas Verified | Test Values Used | Result |
|-------|------------------|-----------------|--------|
| Sphere | SA, V, Diameter | r = 1, 3, 5 | ✅ All pass |
| Cube | SA, V, Space Diagonal | s = 1, 3, 4 | ✅ All pass |
| Cylinder | Total SA, Lateral SA, V | r=1/h=1, r=3/h=7 | ✅ All pass |
| RectangularPrism | SA, V, Space Diagonal | multiple combos | ✅ All pass |
| Pyramid | Slant Height, SA, V | base=3/h=4, base=5/h=8 | ✅ All pass |

---

## 2. Code Quality Review

### 2.1 Java Naming Conventions

| Convention | Rule | Status |
|-----------|------|--------|
| Class names | PascalCase | ✅ `Shape3D`, `RectangularPrism`, `ShapeDriver` |
| Method names | camelCase | ✅ `getSurfaceArea()`, `getSlantHeight()`, `buildDemoShapes()` |
| Field names | camelCase, private | ✅ `sideLength`, `baseLength`, `radius` |
| Constants | UPPER_SNAKE_CASE | ✅ `DIVIDER`, `SUB_DIVIDER`, `DELTA` |
| Packages | lowercase | ✅ `com.csc205.project2.shapes` |

**One naming improvement made:** The Pyramid's field was initially considered as `side` but renamed to `baseLength` to better communicate it represents only the base edge, not a general side — reducing ambiguity.

---

### 2.2 Code Style Consistency

All five shape classes follow the identical internal structure order:

```
1. Fields (private)
2. Constructors
3. Interface method implementations (getSurfaceArea, getVolume)
4. Shape-specific methods
5. Getters & Setters
6. toString()
```

This consistent layout means a reader can navigate any shape file using the same mental map, reducing cognitive load.

**`toString()` consistency:** Every shape delegates to `super.toString()` first (which prints Shape, Color, Surface Area, Volume), then appends shape-specific properties. This guarantees the top section is always in the same format regardless of which shape is printed.

---

### 2.3 Error Handling & Input Validation

**What was validated:**

| Input | Validation Applied |
|-------|--------------------|
| Negative dimensions | `<= 0` check → `IllegalArgumentException` |
| Zero dimensions | Covered by `<= 0` check |
| Null strings | `== null` check before `.isBlank()` |
| Blank strings | `.isBlank()` check |

**Gap identified and corrected — NaN inputs:**

Java's floating-point comparison `value <= 0` evaluates to `false` when `value` is `Double.NaN`. This means without an explicit NaN check, a `NaN` radius would silently pass validation and produce `NaN` outputs for all calculations.

**The fix applied to all setters:**
```java
// Before (vulnerable to NaN)
if (radius <= 0) { throw ... }

// After (NaN-safe)
if (Double.isNaN(radius) || radius <= 0) { throw ... }
```

This was caught by the JUnit test `@Test void nanRadiusThrows()` which would have failed against the original implementation.

**Gap identified — `Double.NEGATIVE_INFINITY`:**  
`NEGATIVE_INFINITY <= 0` evaluates to `true`, so it is correctly rejected by the existing check. No additional fix needed.

---

### 2.4 Edge Case Testing Results

| Edge Case | Shape | Expected Behaviour | Confirmed? |
|-----------|-------|--------------------|------------|
| `radius = 0` | Sphere | `IllegalArgumentException` | ✅ |
| `radius = 1e-9` | Sphere | Positive, finite volume | ✅ |
| `radius = 1e6` | Sphere | Finite (no overflow) | ✅ |
| `radius = Double.MAX_VALUE` | Sphere | Volume = `Infinity` (documented behaviour) | ✅ |
| `side = 0` | Cube | `IllegalArgumentException` | ✅ |
| `l=w=h=4` | RectangularPrism | Matches Cube(4) exactly | ✅ |
| Tiny base, large height | Pyramid | Slant height ≈ vertical height | ✅ |
| `NaN` for any dimension | All | `IllegalArgumentException` | ✅ (after fix) |
| `null` color | All | `IllegalArgumentException` | ✅ |
| Blank/whitespace color | All | `IllegalArgumentException` | ✅ |

---

## 3. AI Output Analysis

### 3.1 Tools Used

- **Claude (Anthropic)** — primary tool used for code generation across all files in this project

### 3.2 Where the AI Excelled

**Consistent boilerplate structure.**  
The AI maintained a perfectly uniform class structure across all five shape classes without being reminded. Each file has fields, constructors, interface implementations, shape-specific methods, getters/setters, and `toString()` in the same order. A human writing five files manually would likely drift in style.

**Mathematical formula accuracy.**  
All formulas were correct on first generation. The Pyramid was the most complex case (requiring slant height as an intermediate calculation used internally by `getSurfaceArea()`), and the AI correctly structured the code so `getSurfaceArea()` calls `getSlantHeight()` rather than duplicating the formula inline.

**JUnit 5 test organization.**  
The AI independently used `@Nested` classes to group tests by category (Basic, Calculation, Boundary, Validation, Polymorphism), `@ParameterizedTest` with `@ValueSource` for validating multiple bad inputs, and `@MethodSource` in the cross-shape polymorphism suite — all without being explicitly prompted to use these annotations.

**Stream-based comparative analysis.**  
In `ShapeDriver.java`, the AI used Java streams naturally for `max()`, `min()`, `sum()`, and `average()` rather than manual loops, producing cleaner and more idiomatic code.

**JavaDoc documentation.**  
Every class, method, and field was documented with proper `@param`, `@return`, `@throws`, and inline formula documentation — something time-consuming to write manually.

---

### 3.3 Where the AI Struggled or Required Correction

**NaN input validation gap.**  
The AI's initial validators used only `if (value <= 0)`. This is the conventional check but silently accepts `Double.NaN` because `NaN <= 0` returns `false` in Java. This was a subtle but real bug that the JUnit tests exposed. The fix required prompting the AI to add explicit `Double.isNaN()` guards.

> **Lesson:** AI tools apply common patterns correctly, but can miss language-specific edge cases in floating-point arithmetic. Tests are essential.

**ShapeDriver styling inconsistency.**  
The first ShapeDriver draft used Unicode block characters (`█`, `░`, `═`) for decorative output. While visually rich, this causes rendering issues on Windows terminals that use older code pages (e.g., CP437). The final version was adjusted to use plain ASCII (`#`, `.`, `=`, `-`) for maximum portability.

**Pattern matching syntax version dependency.**  
The AI initially generated switch expressions with pattern matching:
```java
return switch (shape) {
    case Sphere s -> ...
    case Cube c   -> ...
};
```
This syntax requires **Java 21+** (pattern matching for switch became a standard feature in Java 21). Since CSC 205 may run on Java 17, this was replaced with sequential `instanceof` checks, which compile on any Java version from 14+.

**Reflection.md was not self-generated.**  
The AI correctly generated all code artifacts but did not produce a reflection document unprompted. A reflection requires human judgment, personal experience, and first-person analysis — things an AI cannot authentically provide. This document itself is a prime example of that limitation; the analysis of *where the AI struggled* can only be written by a human who actually ran the code and observed the results.

---

### 3.4 Corrections and Improvements Summary

| Issue | Source | Fix Applied |
|-------|--------|-------------|
| NaN inputs bypass validation | AI gap | Added `Double.isNaN()` check to all setters |
| Unicode characters in terminal output | Portability concern | Replaced with ASCII equivalents |
| Pattern-matching switch requires Java 21+ | Version compatibility | Replaced with `instanceof` chain |
| Pyramid slant height formula not cross-checked | Math review | Verified against Wolfram and omnicalculator.com |
| `@BeforeEach` not used in original test sketches | Test best practice | Refactored repeated `new Sphere(5.0, "red")` into setUp() method |

---

## 4. Factory Pattern Implementation

### 4.1 What is the Factory Pattern?

The **Factory Pattern** is a *creational* design pattern — one of the original 23 patterns from the "Gang of Four" book *Design Patterns: Elements of Reusable Object-Oriented Software* (Gamma et al., 1994). Its purpose is to centralize object creation so callers never need to know *how* to build an object, only *what* they want.

Instead of this scattered across the codebase:
```java
new Sphere(5.0, "red");
new Cube(4.0, "blue");
new Cylinder(3.0, 7.0, "green");
```

Every creation goes through one place:
```java
ShapeFactory.create(Sphere.class, "red", 5.0);
ShapeFactory.create(Cube.class, "blue", 4.0);
ShapeFactory.create(Cylinder.class, "green", 3.0, 7.0);
```

### 4.2 Why Use a Factory Here?

| Problem Without Factory | Solution With Factory |
|------------------------|----------------------|
| Callers must know each constructor signature | One method signature for all shapes |
| Validation logic scattered across constructors | Single validation choke-point in factory |
| Adding a new shape requires updating all callers | Only `ShapeFactory.create()` needs updating |
| No central place to log/audit shape creation | Factory can add logging in one place |

### 4.3 Design Decisions

**`Class<? extends Shape3D>` as the first parameter**
Using a `Class` token (e.g., `Sphere.class`) instead of a string like `"Sphere"` is type-safe. If you mistype `Spheree.class`, the compiler catches it immediately. A string `"Spheree"` would only fail at runtime.

**`double... dimensions` varargs**
Varargs allows a single method signature to accept 1, 2, or 3 dimensions depending on shape type. The factory validates the count for each shape before construction, so callers get a clear error message if they pass the wrong number.

**Dimension order matches constructor order**
The factory passes dimensions to constructors in the same order they appear in the constructor signature. This keeps the mental model consistent — `Cylinder(radius, height, color)` maps to `create(Cylinder.class, color, radius, height)`.

**Private constructor blocks instantiation**
Since `ShapeFactory` is a pure static utility (like `java.util.Arrays`), instantiating it would be a mistake. A private constructor throwing `UnsupportedOperationException` prevents this.

**Pre-validation before dispatch**
The factory validates `null` class, `null/blank` color, and `NaN/non-positive` dimensions *before* dispatching to the shape constructors. This ensures error messages come from the factory (with context about which dimension failed) rather than from deep inside a shape constructor.

### 4.4 Factory Dimension Reference

| Shape | Arg 1 | Arg 2 | Arg 3 |
|-------|-------|-------|-------|
| `Sphere` | radius | — | — |
| `Cube` | sideLength | — | — |
| `Cylinder` | radius | height | — |
| `RectangularPrism` | length | width | height |
| `Pyramid` | baseLength | height | — |

### 4.5 Test Coverage for the Factory

The `ShapeFactoryTest` covers six categories:

1. **Successful creation** — correct instance type, correct field values, correct volume/SA calculations for all 5 shapes
2. **Wrong dimension count** — too few and too many dimensions for each shape
3. **Invalid dimension values** — zero, negative, NaN, and `-Infinity`
4. **Invalid string inputs** — null, empty, and blank color strings
5. **Null and unsupported class** — null class, abstract `Shape3D.class`, and verification that the factory itself cannot be instantiated
6. **Polymorphism** — factory output stored in `Shape3D[]` and `ThreeDimensionalShape[]` arrays; factory results match direct construction exactly

### 4.6 Limitations and Future Improvements

**Limitation — rigid dispatch block:**  
The factory uses an `if/else if` chain per shape type. If 10 more shapes are added, this block grows linearly. A more scalable approach would use a `Map<Class<?>, Function<double[], Shape3D>>` registry, allowing new shapes to register themselves without modifying the factory body.

**Limitation — dimension semantics:**  
The factory documents dimension order but cannot *name* them in code — `dimensions[0]` carries no label. A future improvement could use a named `ShapeSpec` record:
```java
// Possible future API
ShapeFactory.create(new SphereSpec("red", radius: 5.0));
```

**Limitation — no support for custom Shape3D subclasses:**  
If a user creates their own `Cone extends Shape3D`, the factory will throw "unsupported class." A registry-based design would allow third-party registration of new types.

---

## 5. Lessons Learned

### On Polymorphism

Storing all five different shapes in a single `List<Shape3D>` and iterating over them with calls to `getVolume()` and `getSurfaceArea()` made the power of polymorphism concrete. Without the abstract base class and interface contract, every loop in `ShapeDriver` would need an `if/else` chain checking the type of each shape manually. The OOP design eliminates that entirely.

### On Interface vs Abstract Class

This project used both. The `ThreeDimensionalShape` **interface** defines the *contract* (what a shape must be able to do), while `Shape3D` **abstract class** provides *shared implementation* (name, color, toString format). Trying to put everything in one or the other would have been worse:

- Interface only: no shared fields, every shape duplicates name/color/toString
- Abstract class only: limits to single inheritance and doesn't clearly separate contract from implementation

The two-layer design is the right tool here.

### On Test-Driven Validation

Writing tests before or alongside the code — especially the NaN and boundary edge cases — caught the validation gap that would otherwise have gone unnoticed until a runtime bug surfaced. The `@ParameterizedTest` annotation proved particularly useful for testing multiple invalid inputs without code repetition.

### On AI-Assisted Development

AI tools are excellent *accelerators* for well-defined, structured code (getters, setters, boilerplate, formulas, test scaffolding). They are **not** substitutes for:

- Understanding the code produced
- Verifying mathematical correctness independently
- Catching language-version-specific issues
- Writing authentic human reflection and analysis
- Testing in the actual target environment

The most productive workflow was: **generate with AI → review critically → test rigorously → correct and improve → document what changed and why.**

---

*Reflection authored as part of CSC 205, Project 2.*