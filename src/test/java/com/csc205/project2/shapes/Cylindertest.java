package com.csc205.project2.shapes;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for {@link Cylinder}.
 *
 * <p>Design decisions documented:
 * <ul>
 *   <li>Zero radius or zero height → {@link IllegalArgumentException}</li>
 *   <li>Negative radius or height → {@link IllegalArgumentException}</li>
 *   <li>Floating-point comparisons use a delta of 1e-4</li>
 * </ul>
 * </p>
 */
@DisplayName("Cylinder Tests")
class CylinderTest {

    private static final double DELTA = 1e-4;
    private Cylinder cylinder;

    @BeforeEach
    void setUp() {
        cylinder = new Cylinder(3.0, 7.0, "green");
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
            assertEquals(3.0, cylinder.getRadius(), DELTA);
        }

        @Test
        @DisplayName("Constructor sets height correctly")
        void constructorSetsHeight() {
            assertEquals(7.0, cylinder.getHeight(), DELTA);
        }

        @Test
        @DisplayName("Constructor sets color correctly")
        void constructorSetsColor() {
            assertEquals("green", cylinder.getColor());
        }

        @Test
        @DisplayName("Constructor sets name to 'Cylinder'")
        void constructorSetsName() {
            assertEquals("Cylinder", cylinder.getName());
        }

        @Test
        @DisplayName("setRadius updates value")
        void setRadiusUpdates() {
            cylinder.setRadius(5.0);
            assertEquals(5.0, cylinder.getRadius(), DELTA);
        }

        @Test
        @DisplayName("setHeight updates value")
        void setHeightUpdates() {
            cylinder.setHeight(10.0);
            assertEquals(10.0, cylinder.getHeight(), DELTA);
        }

        @Test
        @DisplayName("toString contains 'Cylinder'")
        void toStringContainsName() {
            assertTrue(cylinder.toString().contains("Cylinder"));
        }

        @Test
        @DisplayName("toString contains color")
        void toStringContainsColor() {
            assertTrue(cylinder.toString().contains("green"));
        }
    }

    // =========================================================
    // 2. CALCULATION ACCURACY
    // =========================================================

    @Nested
    @DisplayName("2. Calculation Accuracy")
    class CalculationAccuracy {

        @Test
        @DisplayName("Volume of r=3, h=7 cylinder = π*9*7 ≈ 197.9203")
        void volumeR3H7() {
            assertEquals(197.9203, cylinder.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Volume of r=1, h=1 cylinder = π ≈ 3.1416")
        void volumeUnitCylinder() {
            Cylinder c = new Cylinder(1.0, 1.0, "red");
            assertEquals(Math.PI, c.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Volume of r=2, h=5 cylinder = π*4*5 ≈ 62.8318")
        void volumeR2H5() {
            Cylinder c = new Cylinder(2.0, 5.0, "red");
            assertEquals(62.8318, c.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Surface area of r=3, h=7 cylinder = 2π*9 + 2π*3*7 ≈ 188.4956")
        void surfaceAreaR3H7() {
            assertEquals(188.4956, cylinder.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Surface area of unit cylinder (r=1, h=1) = 4π ≈ 12.5664")
        void surfaceAreaUnitCylinder() {
            Cylinder c = new Cylinder(1.0, 1.0, "red");
            assertEquals(4 * Math.PI, c.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Lateral surface area of r=3, h=7 = 2π*3*7 ≈ 131.9469")
        void lateralSurfaceArea() {
            assertEquals(131.9469, cylinder.getLateralSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Total SA = Lateral SA + 2 * base circle area")
        void totalSAEqualsLateralPlusBases() {
            double lateral = cylinder.getLateralSurfaceArea();
            double bases = 2 * Math.PI * Math.pow(cylinder.getRadius(), 2);
            assertEquals(cylinder.getSurfaceArea(), lateral + bases, DELTA);
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
            // DECISION: A cylinder with radius 0 degrades to a line segment — reject.
        void zeroRadiusThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cylinder(0.0, 5.0, "red"));
        }

        @Test
        @DisplayName("Zero height throws IllegalArgumentException")
            // DECISION: A cylinder with height 0 degrades to a flat disk — reject.
        void zeroHeightThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cylinder(3.0, 0.0, "red"));
        }

        @Test
        @DisplayName("Very small dimensions produce positive, finite results")
        void verySmallDimensionsPositive() {
            Cylinder c = new Cylinder(1e-9, 1e-9, "red");
            assertTrue(c.getVolume() > 0);
            assertTrue(c.getSurfaceArea() > 0);
            assertTrue(Double.isFinite(c.getVolume()));
        }

        @Test
        @DisplayName("Very large dimensions remain finite")
        void veryLargeDimensionsFinite() {
            Cylinder c = new Cylinder(1e6, 1e6, "red");
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

        @ParameterizedTest(name = "Negative radius {0} throws")
        @ValueSource(doubles = {-1.0, -0.5, -100.0})
        @DisplayName("Negative radius throws IllegalArgumentException")
        void negativeRadiusThrows(double bad) {
            assertThrows(IllegalArgumentException.class, () -> new Cylinder(bad, 5.0, "red"));
        }

        @ParameterizedTest(name = "Negative height {0} throws")
        @ValueSource(doubles = {-1.0, -0.5, -100.0})
        @DisplayName("Negative height throws IllegalArgumentException")
        void negativeHeightThrows(double bad) {
            assertThrows(IllegalArgumentException.class, () -> new Cylinder(3.0, bad, "red"));
        }

        @Test
        @DisplayName("Null color throws IllegalArgumentException")
        void nullColorThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cylinder(3.0, 5.0, null));
        }

        @Test
        @DisplayName("Empty color throws IllegalArgumentException")
        void emptyColorThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cylinder(3.0, 5.0, ""));
        }

        @Test
        @DisplayName("NaN radius throws IllegalArgumentException")
        void nanRadiusThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cylinder(Double.NaN, 5.0, "red"));
        }

        @Test
        @DisplayName("NaN height throws IllegalArgumentException")
        void nanHeightThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Cylinder(3.0, Double.NaN, "red"));
        }

        @Test
        @DisplayName("setRadius to negative throws")
        void setRadiusNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> cylinder.setRadius(-1.0));
        }

        @Test
        @DisplayName("setHeight to zero throws")
        void setHeightZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> cylinder.setHeight(0.0));
        }
    }

    // =========================================================
    // 5. INHERITANCE / POLYMORPHISM TESTING
    // =========================================================

    @Nested
    @DisplayName("5. Inheritance & Polymorphism")
    class InheritanceTesting {

        @Test
        @DisplayName("Cylinder is instance of Shape3D")
        void cylinderIsShape3D() {
            assertInstanceOf(Shape3D.class, cylinder);
        }

        @Test
        @DisplayName("Cylinder is instance of ThreeDimensionalShape")
        void cylinderIsInterface() {
            assertInstanceOf(ThreeDimensionalShape.class, cylinder);
        }

        @Test
        @DisplayName("Cylinder via Shape3D reference — getVolume()")
        void cylinderAsShape3DVolume() {
            Shape3D shape = new Cylinder(3.0, 7.0, "green");
            assertEquals(197.9203, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Cylinder via Shape3D reference — getSurfaceArea()")
        void cylinderAsShape3DSurfaceArea() {
            Shape3D shape = new Cylinder(3.0, 7.0, "green");
            assertEquals(188.4956, shape.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Cylinder via interface reference")
        void cylinderViaInterface() {
            ThreeDimensionalShape shape = new Cylinder(1.0, 1.0, "red");
            assertEquals(Math.PI, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("toString via Shape3D reference contains 'Cylinder'")
        void toStringPolymorphic() {
            Shape3D shape = new Cylinder(3.0, 7.0, "green");
            assertTrue(shape.toString().contains("Cylinder"));
        }
    }
}