package com.csc205.project2.shapes;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for {@link Cube}.
 *
 * <p>Design decisions documented:
 * <ul>
 *   <li>Zero side length → {@link IllegalArgumentException} (a zero-side cube has no physical meaning)</li>
 *   <li>Negative side length → {@link IllegalArgumentException}</li>
 *   <li>Floating-point comparisons use a delta of 1e-4</li>
 * </ul>
 * </p>
 */
@DisplayName("Cube Tests")
class CubeTest {

    private static final double DELTA = 1e-4;
    private Cube cube;

    @BeforeEach
    void setUp() {
        cube = new Cube(4.0, "blue");
    }

    // =========================================================
    // 1. BASIC FUNCTIONALITY
    // =========================================================

    @Nested
    @DisplayName("1. Basic Functionality")
    class BasicFunctionality {

        @Test
        @DisplayName("Constructor sets sideLength correctly")
        void constructorSetsSideLength() {
            assertEquals(4.0, cube.getSideLength(), DELTA);
        }

        @Test
        @DisplayName("Constructor sets color correctly")
        void constructorSetsColor() {
            assertEquals("blue", cube.getColor());
        }

        @Test
        @DisplayName("Constructor sets name to 'Cube'")
        void constructorSetsName() {
            assertEquals("Cube", cube.getName());
        }

        @Test
        @DisplayName("setSideLength updates value")
        void setSideLengthUpdatesValue() {
            cube.setSideLength(8.0);
            assertEquals(8.0, cube.getSideLength(), DELTA);
        }

        @Test
        @DisplayName("toString contains 'Cube'")
        void toStringContainsName() {
            assertTrue(cube.toString().contains("Cube"));
        }

        @Test
        @DisplayName("toString contains color")
        void toStringContainsColor() {
            assertTrue(cube.toString().contains("blue"));
        }

        @Test
        @DisplayName("toString contains side length")
        void toStringContainsSideLength() {
            assertTrue(cube.toString().contains("4.00"));
        }
    }

    // =========================================================
    // 2. CALCULATION ACCURACY
    // =========================================================

    @Nested
    @DisplayName("2. Calculation Accuracy")
    class CalculationAccuracy {

        @Test
        @DisplayName("Volume of side-3 cube = 27.0")
        void volumeSideThree() {
            Cube c = new Cube(3.0, "red");
            assertEquals(27.0, c.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Volume of side-4 cube = 64.0")
        void volumeSideFour() {
            assertEquals(64.0, cube.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Volume of unit cube (side=1) = 1.0")
        void volumeUnitCube() {
            Cube c = new Cube(1.0, "white");
            assertEquals(1.0, c.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Surface area of side-3 cube = 6*9 = 54.0")
        void surfaceAreaSideThree() {
            Cube c = new Cube(3.0, "red");
            assertEquals(54.0, c.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Surface area of side-4 cube = 6*16 = 96.0")
        void surfaceAreaSideFour() {
            assertEquals(96.0, cube.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Surface area of unit cube = 6.0")
        void surfaceAreaUnitCube() {
            Cube c = new Cube(1.0, "white");
            assertEquals(6.0, c.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Space diagonal of side-1 cube = √3 ≈ 1.7321")
        void spaceDiagonalUnitCube() {
            Cube c = new Cube(1.0, "white");
            assertEquals(Math.sqrt(3), c.getSpaceDiagonal(), DELTA);
        }

        @Test
        @DisplayName("Space diagonal of side-4 cube = 4√3 ≈ 6.9282")
        void spaceDiagonalSideFour() {
            assertEquals(4 * Math.sqrt(3), cube.getSpaceDiagonal(), DELTA);
        }
    }

    // =========================================================
    // 3. BOUNDARY TESTING
    // =========================================================

    @Nested
    @DisplayName("3. Boundary Testing")
    class BoundaryTesting {

        @Test
        @DisplayName("Zero side length throws IllegalArgumentException")
            // DECISION: A cube with side 0 collapses to a point — reject with exception.
        void zeroSideLengthThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cube(0.0, "red"));
        }

        @Test
        @DisplayName("Very small side (1e-9) produces positive volume and surface area")
        void verySmallSidePositiveResults() {
            Cube c = new Cube(1e-9, "red");
            assertTrue(c.getVolume() > 0);
            assertTrue(c.getSurfaceArea() > 0);
        }

        @Test
        @DisplayName("Very large side (1e6) remains finite")
        void veryLargeSideRemainsFinite() {
            Cube c = new Cube(1e6, "red");
            assertTrue(Double.isFinite(c.getVolume()));
            assertTrue(Double.isFinite(c.getSurfaceArea()));
        }
    }

    // =========================================================
    // 4. INPUT VALIDATION
    // =========================================================

    @Nested
    @DisplayName("4. Input Validation")
    class InputValidation {

        @ParameterizedTest(name = "Negative side {0} throws")
        @ValueSource(doubles = {-1.0, -0.001, -999.9})
        @DisplayName("Negative side lengths throw IllegalArgumentException")
        void negativeSideLengthThrows(double bad) {
            assertThrows(IllegalArgumentException.class, () -> new Cube(bad, "red"));
        }

        @Test
        @DisplayName("Null color throws IllegalArgumentException")
        void nullColorThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cube(4.0, null));
        }

        @Test
        @DisplayName("Blank color throws IllegalArgumentException")
        void blankColorThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cube(4.0, "  "));
        }

        @Test
        @DisplayName("setSideLength to zero throws")
        void setSideLengthZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> cube.setSideLength(0.0));
        }

        @Test
        @DisplayName("setSideLength to negative throws")
        void setSideLengthNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> cube.setSideLength(-3.0));
        }

        @Test
        @DisplayName("NaN side length throws IllegalArgumentException")
        void nanSideLengthThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cube(Double.NaN, "red"));
        }
    }

    // =========================================================
    // 5. INHERITANCE / POLYMORPHISM TESTING
    // =========================================================

    @Nested
    @DisplayName("5. Inheritance & Polymorphism")
    class InheritanceTesting {

        @Test
        @DisplayName("Cube is instance of Shape3D")
        void cubeIsShape3D() {
            assertInstanceOf(Shape3D.class, cube);
        }

        @Test
        @DisplayName("Cube is instance of ThreeDimensionalShape")
        void cubeIsInterface() {
            assertInstanceOf(ThreeDimensionalShape.class, cube);
        }

        @Test
        @DisplayName("Cube via Shape3D reference — getVolume()")
        void cubeAsShape3DVolume() {
            Shape3D shape = new Cube(3.0, "red");
            assertEquals(27.0, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Cube via Shape3D reference — getSurfaceArea()")
        void cubeAsShape3DSurfaceArea() {
            Shape3D shape = new Cube(3.0, "red");
            assertEquals(54.0, shape.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Cube via interface reference")
        void cubeViaInterface() {
            ThreeDimensionalShape shape = new Cube(4.0, "blue");
            assertEquals(64.0, shape.getVolume(), DELTA);
        }
    }
}