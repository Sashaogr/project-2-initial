package com.csc205.project2.shapes;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for {@link RectangularPrism}.
 *
 * <p>Design decisions documented:
 * <ul>
 *   <li>Any zero dimension → {@link IllegalArgumentException}</li>
 *   <li>Any negative dimension → {@link IllegalArgumentException}</li>
 *   <li>Floating-point comparisons use a delta of 1e-4</li>
 * </ul>
 * </p>
 */
@DisplayName("RectangularPrism Tests")
class RectangularPrismTest {

    private static final double DELTA = 1e-4;
    private RectangularPrism prism;

    @BeforeEach
    void setUp() {
        prism = new RectangularPrism(6.0, 4.0, 3.0, "yellow");
    }

    // =========================================================
    // 1. BASIC FUNCTIONALITY
    // =========================================================

    @Nested
    @DisplayName("1. Basic Functionality")
    class BasicFunctionality {

        @Test
        @DisplayName("Constructor sets length correctly")
        void constructorSetsLength() {
            assertEquals(6.0, prism.getLength(), DELTA);
        }

        @Test
        @DisplayName("Constructor sets width correctly")
        void constructorSetsWidth() {
            assertEquals(4.0, prism.getWidth(), DELTA);
        }

        @Test
        @DisplayName("Constructor sets height correctly")
        void constructorSetsHeight() {
            assertEquals(3.0, prism.getHeight(), DELTA);
        }

        @Test
        @DisplayName("Constructor sets color correctly")
        void constructorSetsColor() {
            assertEquals("yellow", prism.getColor());
        }

        @Test
        @DisplayName("Constructor sets name to 'Rectangular Prism'")
        void constructorSetsName() {
            assertEquals("Rectangular Prism", prism.getName());
        }

        @Test
        @DisplayName("setLength updates value")
        void setLengthUpdates() {
            prism.setLength(10.0);
            assertEquals(10.0, prism.getLength(), DELTA);
        }

        @Test
        @DisplayName("setWidth updates value")
        void setWidthUpdates() {
            prism.setWidth(8.0);
            assertEquals(8.0, prism.getWidth(), DELTA);
        }

        @Test
        @DisplayName("setHeight updates value")
        void setHeightUpdates() {
            prism.setHeight(5.0);
            assertEquals(5.0, prism.getHeight(), DELTA);
        }

        @Test
        @DisplayName("toString contains 'Rectangular Prism'")
        void toStringContainsName() {
            assertTrue(prism.toString().contains("Rectangular Prism"));
        }

        @Test
        @DisplayName("toString contains color")
        void toStringContainsColor() {
            assertTrue(prism.toString().contains("yellow"));
        }
    }

    // =========================================================
    // 2. CALCULATION ACCURACY
    // =========================================================

    @Nested
    @DisplayName("2. Calculation Accuracy")
    class CalculationAccuracy {

        @Test
        @DisplayName("Volume of l=6, w=4, h=3 prism = 72.0")
        void volumeL6W4H3() {
            assertEquals(72.0, prism.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Volume of unit prism (1x1x1) = 1.0")
        void volumeUnitPrism() {
            RectangularPrism p = new RectangularPrism(1.0, 1.0, 1.0, "white");
            assertEquals(1.0, p.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Volume of l=2, w=3, h=4 = 24.0")
        void volumeL2W3H4() {
            RectangularPrism p = new RectangularPrism(2.0, 3.0, 4.0, "red");
            assertEquals(24.0, p.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Surface area of l=6, w=4, h=3 = 2*(24+18+12) = 108.0")
        void surfaceAreaL6W4H3() {
            assertEquals(108.0, prism.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Surface area of unit prism = 6.0")
        void surfaceAreaUnitPrism() {
            RectangularPrism p = new RectangularPrism(1.0, 1.0, 1.0, "white");
            assertEquals(6.0, p.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Surface area of l=2, w=3, h=4 = 2*(6+8+12) = 52.0")
        void surfaceAreaL2W3H4() {
            RectangularPrism p = new RectangularPrism(2.0, 3.0, 4.0, "red");
            assertEquals(52.0, p.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Space diagonal of l=3, w=4, h=5 = √(9+16+25) = √50 ≈ 7.0711")
        void spaceDiagonalL3W4H5() {
            RectangularPrism p = new RectangularPrism(3.0, 4.0, 5.0, "red");
            assertEquals(Math.sqrt(50), p.getSpaceDiagonal(), DELTA);
        }

        @Test
        @DisplayName("Space diagonal of unit prism = √3 ≈ 1.7321")
        void spaceDiagonalUnitPrism() {
            RectangularPrism p = new RectangularPrism(1.0, 1.0, 1.0, "white");
            assertEquals(Math.sqrt(3), p.getSpaceDiagonal(), DELTA);
        }

        @Test
        @DisplayName("A cube (l=w=h=4) has same volume as Cube(4)")
        void rectangularPrismMatchesCubeWhenSquare() {
            RectangularPrism square = new RectangularPrism(4.0, 4.0, 4.0, "red");
            assertEquals(64.0, square.getVolume(), DELTA);
            assertEquals(96.0, square.getSurfaceArea(), DELTA);
        }
    }

    // =========================================================
    // 3. BOUNDARY TESTING
    // =========================================================

    @Nested
    @DisplayName("3. Boundary Testing")
    class BoundaryTesting {

        @Test
        @DisplayName("Zero length throws IllegalArgumentException")
        void zeroLengthThrows() {
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism(0.0, 4.0, 3.0, "red"));
        }

        @Test
        @DisplayName("Zero width throws IllegalArgumentException")
        void zeroWidthThrows() {
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism(6.0, 0.0, 3.0, "red"));
        }

        @Test
        @DisplayName("Zero height throws IllegalArgumentException")
        void zeroHeightThrows() {
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism(6.0, 4.0, 0.0, "red"));
        }

        @Test
        @DisplayName("Very small dimensions produce positive, finite results")
        void verySmallDimensionsPositive() {
            RectangularPrism p = new RectangularPrism(1e-9, 1e-9, 1e-9, "red");
            assertTrue(p.getVolume() > 0);
            assertTrue(p.getSurfaceArea() > 0);
        }

        @Test
        @DisplayName("Very large dimensions remain finite")
        void veryLargeDimensionsFinite() {
            RectangularPrism p = new RectangularPrism(1e6, 1e6, 1e6, "red");
            assertTrue(Double.isFinite(p.getVolume()));
            assertTrue(Double.isFinite(p.getSurfaceArea()));
        }
    }

    // =========================================================
    // 4. INPUT VALIDATION
    // =========================================================

    @Nested
    @DisplayName("4. Input Validation")
    class InputValidation {

        @ParameterizedTest(name = "Negative length {0} throws")
        @ValueSource(doubles = {-1.0, -0.001, -500.0})
        @DisplayName("Negative length throws IllegalArgumentException")
        void negativeLengthThrows(double bad) {
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism(bad, 4.0, 3.0, "red"));
        }

        @ParameterizedTest(name = "Negative width {0} throws")
        @ValueSource(doubles = {-1.0, -0.001, -500.0})
        @DisplayName("Negative width throws IllegalArgumentException")
        void negativeWidthThrows(double bad) {
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism(6.0, bad, 3.0, "red"));
        }

        @ParameterizedTest(name = "Negative height {0} throws")
        @ValueSource(doubles = {-1.0, -0.001, -500.0})
        @DisplayName("Negative height throws IllegalArgumentException")
        void negativeHeightThrows(double bad) {
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism(6.0, 4.0, bad, "red"));
        }

        @Test
        @DisplayName("Null color throws IllegalArgumentException")
        void nullColorThrows() {
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism(6.0, 4.0, 3.0, null));
        }

        @Test
        @DisplayName("Blank color throws IllegalArgumentException")
        void blankColorThrows() {
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism(6.0, 4.0, 3.0, "  "));
        }

        @Test
        @DisplayName("NaN length throws IllegalArgumentException")
        void nanLengthThrows() {
            assertThrows(IllegalArgumentException.class, () -> new RectangularPrism(Double.NaN, 4.0, 3.0, "red"));
        }
    }

    // =========================================================
    // 5. INHERITANCE / POLYMORPHISM TESTING
    // =========================================================

    @Nested
    @DisplayName("5. Inheritance & Polymorphism")
    class InheritanceTesting {

        @Test
        @DisplayName("RectangularPrism is instance of Shape3D")
        void isShape3D() {
            assertInstanceOf(Shape3D.class, prism);
        }

        @Test
        @DisplayName("RectangularPrism is instance of ThreeDimensionalShape")
        void isInterface() {
            assertInstanceOf(ThreeDimensionalShape.class, prism);
        }

        @Test
        @DisplayName("Via Shape3D reference — getVolume()")
        void asShape3DVolume() {
            Shape3D shape = new RectangularPrism(6.0, 4.0, 3.0, "yellow");
            assertEquals(72.0, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Via Shape3D reference — getSurfaceArea()")
        void asShape3DSurfaceArea() {
            Shape3D shape = new RectangularPrism(6.0, 4.0, 3.0, "yellow");
            assertEquals(108.0, shape.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Via interface reference")
        void viaInterface() {
            ThreeDimensionalShape shape = new RectangularPrism(2.0, 3.0, 4.0, "red");
            assertEquals(24.0, shape.getVolume(), DELTA);
        }
    }
}