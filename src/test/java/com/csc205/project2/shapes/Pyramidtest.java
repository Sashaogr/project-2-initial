package com.csc205.project2.shapes;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for {@link Pyramid} (square-base).
 *
 * <p>Design decisions documented:
 * <ul>
 *   <li>Zero baseLength or height → {@link IllegalArgumentException}</li>
 *   <li>Negative dimensions → {@link IllegalArgumentException}</li>
 *   <li>Slant height is a derived value; tested independently and via surfaceArea</li>
 *   <li>Floating-point comparisons use a delta of 1e-4</li>
 * </ul>
 * </p>
 */
@DisplayName("Pyramid Tests")
class PyramidTest {

    private static final double DELTA = 1e-4;
    private Pyramid pyramid;

    @BeforeEach
    void setUp() {
        pyramid = new Pyramid(5.0, 8.0, "orange");
    }

    // =========================================================
    // 1. BASIC FUNCTIONALITY
    // =========================================================

    @Nested
    @DisplayName("1. Basic Functionality")
    class BasicFunctionality {

        @Test
        @DisplayName("Constructor sets baseLength correctly")
        void constructorSetsBaseLength() {
            assertEquals(5.0, pyramid.getBaseLength(), DELTA);
        }

        @Test
        @DisplayName("Constructor sets height correctly")
        void constructorSetsHeight() {
            assertEquals(8.0, pyramid.getHeight(), DELTA);
        }

        @Test
        @DisplayName("Constructor sets color correctly")
        void constructorSetsColor() {
            assertEquals("orange", pyramid.getColor());
        }

        @Test
        @DisplayName("Constructor sets name to 'Pyramid'")
        void constructorSetsName() {
            assertEquals("Pyramid", pyramid.getName());
        }

        @Test
        @DisplayName("setBaseLength updates value")
        void setBaseLengthUpdates() {
            pyramid.setBaseLength(10.0);
            assertEquals(10.0, pyramid.getBaseLength(), DELTA);
        }

        @Test
        @DisplayName("setHeight updates value")
        void setHeightUpdates() {
            pyramid.setHeight(12.0);
            assertEquals(12.0, pyramid.getHeight(), DELTA);
        }

        @Test
        @DisplayName("toString contains 'Pyramid'")
        void toStringContainsName() {
            assertTrue(pyramid.toString().contains("Pyramid"));
        }

        @Test
        @DisplayName("toString contains color")
        void toStringContainsColor() {
            assertTrue(pyramid.toString().contains("orange"));
        }
    }

    // =========================================================
    // 2. CALCULATION ACCURACY
    // =========================================================

    @Nested
    @DisplayName("2. Calculation Accuracy")
    class CalculationAccuracy {

        @Test
        @DisplayName("Volume of base=5, h=8: (1/3)*25*8 ≈ 66.6667")
        void volumeBase5H8() {
            assertEquals(66.6667, pyramid.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Volume of base=3, h=4: (1/3)*9*4 = 12.0")
        void volumeBase3H4() {
            Pyramid p = new Pyramid(3.0, 4.0, "red");
            assertEquals(12.0, p.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Volume of base=6, h=9: (1/3)*36*9 = 108.0")
        void volumeBase6H9() {
            Pyramid p = new Pyramid(6.0, 9.0, "blue");
            assertEquals(108.0, p.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Slant height of base=5, h=8 = √(64 + 6.25) ≈ 8.4853")
        void slantHeightBase5H8() {
            // slant = √(8² + (5/2)²) = √(64 + 6.25) = √70.25 ≈ 8.3815
            double expected = Math.sqrt(64 + 6.25);
            assertEquals(expected, pyramid.getSlantHeight(), DELTA);
        }

        @Test
        @DisplayName("Slant height of base=4, h=3 = √(9+4) = √13 ≈ 3.6056")
        void slantHeightBase4H3() {
            Pyramid p = new Pyramid(4.0, 3.0, "red");
            double expected = Math.sqrt(9 + 4);
            assertEquals(expected, p.getSlantHeight(), DELTA);
        }

        @Test
        @DisplayName("Surface area of base=5, h=8 = 25 + 2*5*slant")
        void surfaceAreaBase5H8() {
            double slant = pyramid.getSlantHeight();
            double expected = 25 + (2 * 5 * slant);
            assertEquals(expected, pyramid.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Surface area of base=4, h=3 = 16 + 2*4*√13 ≈ 44.8888")
        void surfaceAreaBase4H3() {
            Pyramid p = new Pyramid(4.0, 3.0, "red");
            double slant = Math.sqrt(9 + 4);
            double expected = 16 + (2 * 4 * slant);
            assertEquals(expected, p.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("getSurfaceArea() uses getSlantHeight() internally — consistent")
        void surfaceAreaConsistentWithSlantHeight() {
            double computedFromSlant = (pyramid.getBaseLength() * pyramid.getBaseLength())
                    + (2 * pyramid.getBaseLength() * pyramid.getSlantHeight());
            assertEquals(computedFromSlant, pyramid.getSurfaceArea(), DELTA);
        }
    }

    // =========================================================
    // 3. BOUNDARY TESTING
    // =========================================================

    @Nested
    @DisplayName("3. Boundary Testing")
    class BoundaryTesting {

        @Test
        @DisplayName("Zero base length throws IllegalArgumentException")
            // DECISION: A pyramid with zero base has no base face — reject.
        void zeroBaseLengthThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Pyramid(0.0, 8.0, "red"));
        }

        @Test
        @DisplayName("Zero height throws IllegalArgumentException")
            // DECISION: A pyramid with height 0 degrades to a flat square — reject.
        void zeroHeightThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Pyramid(5.0, 0.0, "red"));
        }

        @Test
        @DisplayName("Very small dimensions produce positive, finite results")
        void verySmallDimensionsPositive() {
            Pyramid p = new Pyramid(1e-9, 1e-9, "red");
            assertTrue(p.getVolume() > 0);
            assertTrue(p.getSurfaceArea() > 0);
            assertTrue(Double.isFinite(p.getSlantHeight()));
        }

        @Test
        @DisplayName("Very large dimensions remain finite")
        void veryLargeDimensionsFinite() {
            Pyramid p = new Pyramid(1e6, 1e6, "red");
            assertTrue(Double.isFinite(p.getVolume()));
            assertTrue(Double.isFinite(p.getSurfaceArea()));
        }

        @Test
        @DisplayName("Extremely tall pyramid (tiny base, huge height) — slant ≈ height")
        void extremelyTallPyramidSlantApproachesHeight() {
            // As baseLength → 0, slant height → vertical height
            Pyramid p = new Pyramid(0.0001, 1000.0, "red");
            assertEquals(p.getHeight(), p.getSlantHeight(), 0.01);
        }
    }

    // =========================================================
    // 4. INPUT VALIDATION
    // =========================================================

    @Nested
    @DisplayName("4. Input Validation")
    class InputValidation {

        @ParameterizedTest(name = "Negative base {0} throws")
        @ValueSource(doubles = {-1.0, -0.001, -999.0})
        @DisplayName("Negative base length throws IllegalArgumentException")
        void negativeBaseLengthThrows(double bad) {
            assertThrows(IllegalArgumentException.class, () -> new Pyramid(bad, 8.0, "red"));
        }

        @ParameterizedTest(name = "Negative height {0} throws")
        @ValueSource(doubles = {-1.0, -0.001, -999.0})
        @DisplayName("Negative height throws IllegalArgumentException")
        void negativeHeightThrows(double bad) {
            assertThrows(IllegalArgumentException.class, () -> new Pyramid(5.0, bad, "red"));
        }

        @Test
        @DisplayName("Null color throws IllegalArgumentException")
        void nullColorThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Pyramid(5.0, 8.0, null));
        }

        @Test
        @DisplayName("Blank color throws IllegalArgumentException")
        void blankColorThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Pyramid(5.0, 8.0, "  "));
        }

        @Test
        @DisplayName("NaN base length throws IllegalArgumentException")
        void nanBaseLengthThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Pyramid(Double.NaN, 8.0, "red"));
        }

        @Test
        @DisplayName("NaN height throws IllegalArgumentException")
        void nanHeightThrows() {
            assertThrows(IllegalArgumentException.class, () -> new Pyramid(5.0, Double.NaN, "red"));
        }

        @Test
        @DisplayName("setBaseLength to zero throws")
        void setBaseLengthZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> pyramid.setBaseLength(0.0));
        }

        @Test
        @DisplayName("setHeight to negative throws")
        void setHeightNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> pyramid.setHeight(-1.0));
        }
    }

    // =========================================================
    // 5. INHERITANCE / POLYMORPHISM TESTING
    // =========================================================

    @Nested
    @DisplayName("5. Inheritance & Polymorphism")
    class InheritanceTesting {

        @Test
        @DisplayName("Pyramid is instance of Shape3D")
        void pyramidIsShape3D() {
            assertInstanceOf(Shape3D.class, pyramid);
        }

        @Test
        @DisplayName("Pyramid is instance of ThreeDimensionalShape")
        void pyramidIsInterface() {
            assertInstanceOf(ThreeDimensionalShape.class, pyramid);
        }

        @Test
        @DisplayName("Via Shape3D reference — getVolume()")
        void asShape3DVolume() {
            Shape3D shape = new Pyramid(6.0, 9.0, "blue");
            assertEquals(108.0, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Via Shape3D reference — getSurfaceArea()")
        void asShape3DSurfaceArea() {
            Shape3D shape = new Pyramid(4.0, 3.0, "red");
            double slant = Math.sqrt(9 + 4);
            double expected = 16 + (2 * 4 * slant);
            assertEquals(expected, shape.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Via interface reference")
        void viaInterface() {
            ThreeDimensionalShape shape = new Pyramid(3.0, 4.0, "red");
            assertEquals(12.0, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("toString via Shape3D reference contains 'Pyramid'")
        void toStringPolymorphic() {
            Shape3D shape = new Pyramid(5.0, 8.0, "orange");
            assertTrue(shape.toString().contains("Pyramid"));
        }
    }
}
