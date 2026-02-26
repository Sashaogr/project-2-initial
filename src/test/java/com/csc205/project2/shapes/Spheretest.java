package com.csc205.project2.shapes;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for {@link Sphere}.
 *
 * <p>Design decisions documented:
 * <ul>
 *   <li>Zero radius → {@link IllegalArgumentException} (a zero-radius sphere is geometrically undefined)</li>
 *   <li>Negative radius → {@link IllegalArgumentException}</li>
 *   <li>Floating-point comparisons use a delta of 1e-4 to accommodate rounding</li>
 * </ul>
 * </p>
 */
@DisplayName("Sphere Tests")
class SphereTest {

    private static final double DELTA = 1e-4;
    private Sphere sphere;

    @BeforeEach
    void setUp() {
        sphere = new Sphere(5.0, "red");
    }

    // =========================================================
    // 1. BASIC FUNCTIONALITY
    // =========================================================

    @Nested
    @DisplayName("1. Basic Functionality")
    class BasicFunctionality {

        @Test
        @DisplayName("Constructor sets radius correctly")
        void constructorSetsRadius() {
            assertEquals(5.0, sphere.getRadius(), DELTA);
        }

        @Test
        @DisplayName("Constructor sets color correctly")
        void constructorSetsColor() {
            assertEquals("red", sphere.getColor());
        }

        @Test
        @DisplayName("Constructor sets name to 'Sphere'")
        void constructorSetsName() {
            assertEquals("Sphere", sphere.getName());
        }

        @Test
        @DisplayName("setRadius updates radius")
        void setRadiusUpdatesValue() {
            sphere.setRadius(10.0);
            assertEquals(10.0, sphere.getRadius(), DELTA);
        }

        @Test
        @DisplayName("setColor updates color")
        void setColorUpdatesValue() {
            sphere.setColor("blue");
            assertEquals("blue", sphere.getColor());
        }

        @Test
        @DisplayName("getDiameter returns 2 * radius")
        void getDiameterIsDoubleRadius() {
            assertEquals(10.0, sphere.getDiameter(), DELTA);
        }

        @Test
        @DisplayName("toString contains shape name")
        void toStringContainsName() {
            assertTrue(sphere.toString().contains("Sphere"));
        }

        @Test
        @DisplayName("toString contains color")
        void toStringContainsColor() {
            assertTrue(sphere.toString().contains("red"));
        }
    }

    // =========================================================
    // 2. CALCULATION ACCURACY
    // =========================================================

    @Nested
    @DisplayName("2. Calculation Accuracy")
    class CalculationAccuracy {

        @Test
        @DisplayName("Volume of radius-3 sphere = 4/3 * π * 27 ≈ 113.0973")
        void volumeRadiusThree() {
            Sphere s = new Sphere(3.0, "blue");
            assertEquals(113.0973, s.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Volume of radius-5 sphere ≈ 523.5988")
        void volumeRadiusFive() {
            assertEquals(523.5988, sphere.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Volume of radius-1 sphere = 4/3 * π ≈ 4.1888")
        void volumeUnitSphere() {
            Sphere s = new Sphere(1.0, "white");
            assertEquals(4.1888, s.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Surface area of radius-3 sphere = 4π*9 ≈ 113.0973")
        void surfaceAreaRadiusThree() {
            Sphere s = new Sphere(3.0, "blue");
            assertEquals(113.0973, s.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Surface area of radius-5 sphere ≈ 314.1593")
        void surfaceAreaRadiusFive() {
            assertEquals(314.1593, sphere.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Surface area of unit sphere = 4π ≈ 12.5664")
        void surfaceAreaUnitSphere() {
            Sphere s = new Sphere(1.0, "white");
            assertEquals(12.5664, s.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Volume formula: (4/3)*π*r³ verified manually")
        void volumeFormulaVerification() {
            double r = 7.0;
            Sphere s = new Sphere(r, "green");
            double expected = (4.0 / 3.0) * Math.PI * Math.pow(r, 3);
            assertEquals(expected, s.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Surface area formula: 4*π*r² verified manually")
        void surfaceAreaFormulaVerification() {
            double r = 7.0;
            Sphere s = new Sphere(r, "green");
            double expected = 4 * Math.PI * r * r;
            assertEquals(expected, s.getSurfaceArea(), DELTA);
        }
    }

    // =========================================================
    // 3. BOUNDARY TESTING
    // =========================================================

    @Nested
    @DisplayName("3. Boundary Testing")
    class BoundaryTesting {

        @Test
        @DisplayName("Zero radius throws IllegalArgumentException")
            // DECISION: Zero radius is undefined (no meaningful sphere exists); reject with exception.
        void zeroRadiusThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Sphere(0.0, "red"));
        }

        @Test
        @DisplayName("Very small radius (1e-9) produces very small but positive volume")
        void verySmallRadiusPositiveVolume() {
            Sphere s = new Sphere(1e-9, "red");
            assertTrue(s.getVolume() > 0);
        }

        @Test
        @DisplayName("Very large radius (1e6) does not overflow — remains finite")
        void veryLargeRadiusRemainsFinite() {
            Sphere s = new Sphere(1e6, "red");
            assertTrue(Double.isFinite(s.getVolume()));
            assertTrue(Double.isFinite(s.getSurfaceArea()));
        }

        @Test
        @DisplayName("Double.MAX_VALUE radius — volume is infinite (expected overflow behavior)")
            // DECISION: We document that extreme overflow produces Infinity; no exception is thrown.
        void maxDoubleRadiusOverflows() {
            Sphere s = new Sphere(Double.MAX_VALUE, "red");
            assertTrue(Double.isInfinite(s.getVolume()));
        }
    }

    // =========================================================
    // 4. INPUT VALIDATION
    // =========================================================

    @Nested
    @DisplayName("4. Input Validation")
    class InputValidation {

        @ParameterizedTest(name = "Negative radius {0} throws")
        @ValueSource(doubles = {-1.0, -0.001, -100.0, Double.NEGATIVE_INFINITY})
        @DisplayName("Negative radius values throw IllegalArgumentException")
        void negativeRadiusThrows(double badRadius) {
            assertThrows(IllegalArgumentException.class, () -> new Sphere(badRadius, "red"));
        }

        @Test
        @DisplayName("Null color throws IllegalArgumentException")
        void nullColorThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Sphere(5.0, null));
        }

        @Test
        @DisplayName("Blank color throws IllegalArgumentException")
        void blankColorThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Sphere(5.0, "   "));
        }

        @Test
        @DisplayName("Empty color throws IllegalArgumentException")
        void emptyColorThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Sphere(5.0, ""));
        }

        @Test
        @DisplayName("Null name via setName throws IllegalArgumentException")
        void nullNameThrows() {
            assertThrows(IllegalArgumentException.class, () -> sphere.setName(null));
        }

        @Test
        @DisplayName("setRadius to zero throws IllegalArgumentException")
        void setRadiusZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> sphere.setRadius(0.0));
        }

        @Test
        @DisplayName("setRadius to negative throws IllegalArgumentException")
        void setRadiusNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> sphere.setRadius(-5.0));
        }

        @Test
        @DisplayName("NaN radius throws IllegalArgumentException")
        void nanRadiusThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Sphere(Double.NaN, "red"));
        }
    }

    // =========================================================
    // 5. INHERITANCE / POLYMORPHISM TESTING
    // =========================================================

    @Nested
    @DisplayName("5. Inheritance & Polymorphism")
    class InheritanceTesting {

        @Test
        @DisplayName("Sphere is instance of Shape3D")
        void sphereIsInstanceOfShape3D() {
            assertInstanceOf(Shape3D.class, sphere);
        }

        @Test
        @DisplayName("Sphere is instance of ThreeDimensionalShape")
        void sphereIsInstanceOfInterface() {
            assertInstanceOf(ThreeDimensionalShape.class, sphere);
        }

        @Test
        @DisplayName("Sphere works via Shape3D reference — getSurfaceArea()")
        void sphereAsShape3DCallsSurfaceArea() {
            Shape3D shape = new Sphere(5.0, "red");
            assertEquals(314.1593, shape.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Sphere works via Shape3D reference — getVolume()")
        void sphereAsShape3DCallsVolume() {
            Shape3D shape = new Sphere(5.0, "red");
            assertEquals(523.5988, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Sphere works via ThreeDimensionalShape interface reference")
        void sphereViaInterface() {
            ThreeDimensionalShape shape = new Sphere(3.0, "blue");
            assertEquals(113.0973, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("toString called via Shape3D reference still contains 'Sphere'")
        void toStringPolymorphic() {
            Shape3D shape = new Sphere(5.0, "red");
            assertTrue(shape.toString().contains("Sphere"));
        }
    }
}