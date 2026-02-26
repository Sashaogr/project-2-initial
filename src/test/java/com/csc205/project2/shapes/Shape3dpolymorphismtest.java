package com.csc205.project2.shapes;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Cross-shape polymorphism and inheritance tests.
 *
 * <p>Validates that ALL concrete shapes:</p>
 * <ul>
 *   <li>Behave correctly when stored in a {@link Shape3D} or {@link ThreeDimensionalShape} array</li>
 *   <li>Return positive, finite values for volume and surface area</li>
 *   <li>Expose getName() and getColor() through base-class references</li>
 *   <li>Produce non-null, non-empty toString() output</li>
 * </ul>
 */
@DisplayName("Cross-Shape Polymorphism Tests")
class Shape3DPolymorphismTest {

    private static final double DELTA = 1e-4;

    // ---------------------------------------------------------------
    // Shared shape factory (used by parameterized tests)
    // ---------------------------------------------------------------

    static Stream<Shape3D> allShapes() {
        return Stream.of(
                new Sphere(5.0, "red"),
                new Cube(4.0, "blue"),
                new Cylinder(3.0, 7.0, "green"),
                new RectangularPrism(6.0, 4.0, 3.0, "yellow"),
                new Pyramid(5.0, 8.0, "orange")
        );
    }

    // =========================================================
    // 1. BASIC POLYMORPHIC CONTRACTS
    // =========================================================

    @Nested
    @DisplayName("1. Polymorphic Contract — Every Shape")
    class PolymorphicContract {

        @ParameterizedTest(name = "{0} getVolume() > 0")
        @MethodSource("com.csc205.project2.shapes.Shape3DPolymorphismTest#allShapes")
        @DisplayName("Every shape returns a positive volume")
        void everyShapeHasPositiveVolume(Shape3D shape) {
            assertTrue(shape.getVolume() > 0,
                    shape.getName() + " should have a positive volume");
        }

        @ParameterizedTest(name = "{0} getSurfaceArea() > 0")
        @MethodSource("com.csc205.project2.shapes.Shape3DPolymorphismTest#allShapes")
        @DisplayName("Every shape returns a positive surface area")
        void everyShapeHasPositiveSurfaceArea(Shape3D shape) {
            assertTrue(shape.getSurfaceArea() > 0,
                    shape.getName() + " should have a positive surface area");
        }

        @ParameterizedTest(name = "{0} volume is finite")
        @MethodSource("com.csc205.project2.shapes.Shape3DPolymorphismTest#allShapes")
        @DisplayName("Every shape volume is finite (no overflow)")
        void everyShapeVolumeIsFinite(Shape3D shape) {
            assertTrue(Double.isFinite(shape.getVolume()));
        }

        @ParameterizedTest(name = "{0} surface area is finite")
        @MethodSource("com.csc205.project2.shapes.Shape3DPolymorphismTest#allShapes")
        @DisplayName("Every shape surface area is finite (no overflow)")
        void everyShapeSurfaceAreaIsFinite(Shape3D shape) {
            assertTrue(Double.isFinite(shape.getSurfaceArea()));
        }

        @ParameterizedTest(name = "{0} getName() non-empty")
        @MethodSource("com.csc205.project2.shapes.Shape3DPolymorphismTest#allShapes")
        @DisplayName("Every shape getName() returns non-null, non-blank string")
        void everyShapeHasNonBlankName(Shape3D shape) {
            assertNotNull(shape.getName());
            assertFalse(shape.getName().isBlank());
        }

        @ParameterizedTest(name = "{0} getColor() non-empty")
        @MethodSource("com.csc205.project2.shapes.Shape3DPolymorphismTest#allShapes")
        @DisplayName("Every shape getColor() returns non-null, non-blank string")
        void everyShapeHasNonBlankColor(Shape3D shape) {
            assertNotNull(shape.getColor());
            assertFalse(shape.getColor().isBlank());
        }

        @ParameterizedTest(name = "{0} toString() non-null")
        @MethodSource("com.csc205.project2.shapes.Shape3DPolymorphismTest#allShapes")
        @DisplayName("Every shape toString() returns non-null, non-empty string")
        void everyShapeToStringNonNull(Shape3D shape) {
            assertNotNull(shape.toString());
            assertFalse(shape.toString().isBlank());
        }

        @ParameterizedTest(name = "{0} toString contains name")
        @MethodSource("com.csc205.project2.shapes.Shape3DPolymorphismTest#allShapes")
        @DisplayName("Every shape toString() includes its own name")
        void everyShapeToStringContainsName(Shape3D shape) {
            assertTrue(shape.toString().contains(shape.getName()),
                    "toString() for " + shape.getName() + " should contain its own name");
        }
    }

    // =========================================================
    // 2. INTERFACE-LEVEL POLYMORPHISM
    // =========================================================

    @Nested
    @DisplayName("2. ThreeDimensionalShape Interface Polymorphism")
    class InterfacePolymorphism {

        @Test
        @DisplayName("All shapes stored in ThreeDimensionalShape array all return valid volume")
        void allShapesInInterfaceArray() {
            ThreeDimensionalShape[] shapes = {
                    new Sphere(5.0, "red"),
                    new Cube(4.0, "blue"),
                    new Cylinder(3.0, 7.0, "green"),
                    new RectangularPrism(6.0, 4.0, 3.0, "yellow"),
                    new Pyramid(5.0, 8.0, "orange")
            };
            for (ThreeDimensionalShape shape : shapes) {
                assertTrue(shape.getVolume() > 0);
                assertTrue(shape.getSurfaceArea() > 0);
            }
        }

        @Test
        @DisplayName("All shapes stored in Shape3D array all return valid volume")
        void allShapesInShape3DArray() {
            Shape3D[] shapes = {
                    new Sphere(5.0, "red"),
                    new Cube(4.0, "blue"),
                    new Cylinder(3.0, 7.0, "green"),
                    new RectangularPrism(6.0, 4.0, 3.0, "yellow"),
                    new Pyramid(5.0, 8.0, "orange")
            };
            for (Shape3D shape : shapes) {
                assertTrue(shape.getVolume() > 0);
                assertTrue(Double.isFinite(shape.getVolume()));
            }
        }
    }

    // =========================================================
    // 3. KNOWN VOLUME CROSS-CHECK
    // =========================================================

    @Nested
    @DisplayName("3. Spot-check Known Values via Base Reference")
    class KnownValueCrossCheck {

        @Test
        @DisplayName("Sphere (r=5) volume ≈ 523.5988 via Shape3D ref")
        void sphereVolumeViaShape3D() {
            Shape3D s = new Sphere(5.0, "red");
            assertEquals(523.5988, s.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Cube (s=4) volume = 64.0 via Shape3D ref")
        void cubeVolumeViaShape3D() {
            Shape3D s = new Cube(4.0, "blue");
            assertEquals(64.0, s.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Cylinder (r=3,h=7) surface area ≈ 188.4956 via Shape3D ref")
        void cylinderSAViaShape3D() {
            Shape3D s = new Cylinder(3.0, 7.0, "green");
            assertEquals(188.4956, s.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("RectangularPrism (6x4x3) volume = 72.0 via interface ref")
        void prismVolumeViaInterface() {
            ThreeDimensionalShape s = new RectangularPrism(6.0, 4.0, 3.0, "yellow");
            assertEquals(72.0, s.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Pyramid (b=3,h=4) volume = 12.0 via interface ref")
        void pyramidVolumeViaInterface() {
            ThreeDimensionalShape s = new Pyramid(3.0, 4.0, "orange");
            assertEquals(12.0, s.getVolume(), DELTA);
        }
    }
}