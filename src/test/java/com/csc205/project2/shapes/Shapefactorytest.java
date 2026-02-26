package com.csc205.project2.shapes;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for {@link ShapeFactory}.
 *
 * <p>Test categories:
 * <ol>
 *   <li>Successful creation — correct type, fields, and calculations</li>
 *   <li>Wrong dimension count — too few and too many</li>
 *   <li>Invalid dimension values — zero, negative, NaN</li>
 *   <li>Invalid string inputs — null/blank color</li>
 *   <li>Null and unsupported class inputs</li>
 *   <li>Polymorphism — factory output works via Shape3D/interface references</li>
 * </ol>
 * </p>
 */
@DisplayName("ShapeFactory Tests")
class ShapeFactoryTest {

    private static final double DELTA = 1e-4;

    // =========================================================
    // 1. SUCCESSFUL CREATION
    // =========================================================

    @Nested
    @DisplayName("1. Successful Creation")
    class SuccessfulCreation {

        // ── Sphere ────────────────────────────────────────────

        @Test
        @DisplayName("create(Sphere.class, color, r) returns Sphere instance")
        void createSphereInstance() {
            Shape3D shape = ShapeFactory.create(Sphere.class, "red", 5.0);
            assertInstanceOf(Sphere.class, shape);
        }

        @Test
        @DisplayName("Sphere has correct color")
        void createSphereColor() {
            Shape3D shape = ShapeFactory.create(Sphere.class, "red", 5.0);
            assertEquals("red", shape.getColor());
        }

        @Test
        @DisplayName("Sphere has correct radius")
        void createSphereRadius() {
            Sphere sphere = (Sphere) ShapeFactory.create(Sphere.class, "red", 5.0);
            assertEquals(5.0, sphere.getRadius(), DELTA);
        }

        @Test
        @DisplayName("Sphere volume is correct")
        void createSphereVolume() {
            Shape3D shape = ShapeFactory.create(Sphere.class, "red", 5.0);
            assertEquals(523.5988, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Sphere surface area is correct")
        void createSphereSA() {
            Shape3D shape = ShapeFactory.create(Sphere.class, "red", 5.0);
            assertEquals(314.1593, shape.getSurfaceArea(), DELTA);
        }

        // ── Cube ──────────────────────────────────────────────

        @Test
        @DisplayName("create(Cube.class, color, s) returns Cube instance")
        void createCubeInstance() {
            Shape3D shape = ShapeFactory.create(Cube.class, "blue", 4.0);
            assertInstanceOf(Cube.class, shape);
        }

        @Test
        @DisplayName("Cube has correct sideLength")
        void createCubeSideLength() {
            Cube cube = (Cube) ShapeFactory.create(Cube.class, "blue", 4.0);
            assertEquals(4.0, cube.getSideLength(), DELTA);
        }

        @Test
        @DisplayName("Cube volume = 64.0")
        void createCubeVolume() {
            Shape3D shape = ShapeFactory.create(Cube.class, "blue", 4.0);
            assertEquals(64.0, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Cube surface area = 96.0")
        void createCubeSA() {
            Shape3D shape = ShapeFactory.create(Cube.class, "blue", 4.0);
            assertEquals(96.0, shape.getSurfaceArea(), DELTA);
        }

        // ── Cylinder ──────────────────────────────────────────

        @Test
        @DisplayName("create(Cylinder.class, color, r, h) returns Cylinder instance")
        void createCylinderInstance() {
            Shape3D shape = ShapeFactory.create(Cylinder.class, "green", 3.0, 7.0);
            assertInstanceOf(Cylinder.class, shape);
        }

        @Test
        @DisplayName("Cylinder has correct radius and height")
        void createCylinderDimensions() {
            Cylinder cylinder = (Cylinder) ShapeFactory.create(Cylinder.class, "green", 3.0, 7.0);
            assertEquals(3.0, cylinder.getRadius(), DELTA);
            assertEquals(7.0, cylinder.getHeight(), DELTA);
        }

        @Test
        @DisplayName("Cylinder volume ≈ 197.9203")
        void createCylinderVolume() {
            Shape3D shape = ShapeFactory.create(Cylinder.class, "green", 3.0, 7.0);
            assertEquals(197.9203, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Cylinder surface area ≈ 188.4956")
        void createCylinderSA() {
            Shape3D shape = ShapeFactory.create(Cylinder.class, "green", 3.0, 7.0);
            assertEquals(188.4956, shape.getSurfaceArea(), DELTA);
        }

        // ── RectangularPrism ───────────────────────────────────

        @Test
        @DisplayName("create(RectangularPrism.class, color, l, w, h) returns RectangularPrism instance")
        void createPrismInstance() {
            Shape3D shape = ShapeFactory.create(RectangularPrism.class, "yellow", 6.0, 4.0, 3.0);
            assertInstanceOf(RectangularPrism.class, shape);
        }

        @Test
        @DisplayName("RectangularPrism has correct dimensions")
        void createPrismDimensions() {
            RectangularPrism prism = (RectangularPrism) ShapeFactory.create(
                    RectangularPrism.class, "yellow", 6.0, 4.0, 3.0);
            assertEquals(6.0, prism.getLength(), DELTA);
            assertEquals(4.0, prism.getWidth(),  DELTA);
            assertEquals(3.0, prism.getHeight(), DELTA);
        }

        @Test
        @DisplayName("RectangularPrism volume = 72.0")
        void createPrismVolume() {
            Shape3D shape = ShapeFactory.create(RectangularPrism.class, "yellow", 6.0, 4.0, 3.0);
            assertEquals(72.0, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("RectangularPrism surface area = 108.0")
        void createPrismSA() {
            Shape3D shape = ShapeFactory.create(RectangularPrism.class, "yellow", 6.0, 4.0, 3.0);
            assertEquals(108.0, shape.getSurfaceArea(), DELTA);
        }

        // ── Pyramid ───────────────────────────────────────────

        @Test
        @DisplayName("create(Pyramid.class, color, base, h) returns Pyramid instance")
        void createPyramidInstance() {
            Shape3D shape = ShapeFactory.create(Pyramid.class, "orange", 5.0, 8.0);
            assertInstanceOf(Pyramid.class, shape);
        }

        @Test
        @DisplayName("Pyramid has correct baseLength and height")
        void createPyramidDimensions() {
            Pyramid pyramid = (Pyramid) ShapeFactory.create(Pyramid.class, "orange", 5.0, 8.0);
            assertEquals(5.0, pyramid.getBaseLength(), DELTA);
            assertEquals(8.0, pyramid.getHeight(),     DELTA);
        }

        @Test
        @DisplayName("Pyramid volume ≈ 66.6667")
        void createPyramidVolume() {
            Shape3D shape = ShapeFactory.create(Pyramid.class, "orange", 5.0, 8.0);
            assertEquals(66.6667, shape.getVolume(), DELTA);
        }

        // ── Color / name preservation ─────────────────────────

        @Test
        @DisplayName("getName() returns the shape's built-in name (not color)")
        void factoryShapeName() {
            assertEquals("Sphere",           ShapeFactory.create(Sphere.class,           "red",    5.0).getName());
            assertEquals("Cube",             ShapeFactory.create(Cube.class,             "blue",   4.0).getName());
            assertEquals("Cylinder",         ShapeFactory.create(Cylinder.class,         "green",  3.0, 7.0).getName());
            assertEquals("Rectangular Prism",ShapeFactory.create(RectangularPrism.class, "yellow", 6.0, 4.0, 3.0).getName());
            assertEquals("Pyramid",          ShapeFactory.create(Pyramid.class,          "orange", 5.0, 8.0).getName());
        }

        @Test
        @DisplayName("Color with leading/trailing spaces is trimmed")
        void colorWithSpacesIsTrimmed() {
            Shape3D shape = ShapeFactory.create(Sphere.class, "  red  ", 5.0);
            assertEquals("red", shape.getColor());
        }
    }

    // =========================================================
    // 2. WRONG DIMENSION COUNT
    // =========================================================

    @Nested
    @DisplayName("2. Wrong Dimension Count")
    class WrongDimensionCount {

        @Test
        @DisplayName("Sphere with 0 dimensions throws")
        void sphereNoDimensions() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Sphere.class, "red"));
        }

        @Test
        @DisplayName("Sphere with 2 dimensions throws")
        void sphereTooManyDimensions() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Sphere.class, "red", 5.0, 3.0));
        }

        @Test
        @DisplayName("Cube with 2 dimensions throws")
        void cubeTooManyDimensions() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Cube.class, "blue", 4.0, 4.0));
        }

        @Test
        @DisplayName("Cylinder with 1 dimension throws")
        void cylinderTooFewDimensions() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Cylinder.class, "green", 3.0));
        }

        @Test
        @DisplayName("Cylinder with 3 dimensions throws")
        void cylinderTooManyDimensions() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Cylinder.class, "green", 3.0, 7.0, 1.0));
        }

        @Test
        @DisplayName("RectangularPrism with 2 dimensions throws")
        void prismTooFewDimensions() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(RectangularPrism.class, "yellow", 6.0, 4.0));
        }

        @Test
        @DisplayName("RectangularPrism with 4 dimensions throws")
        void prismTooManyDimensions() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(RectangularPrism.class, "yellow", 6.0, 4.0, 3.0, 1.0));
        }

        @Test
        @DisplayName("Pyramid with 1 dimension throws")
        void pyramidTooFewDimensions() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Pyramid.class, "orange", 5.0));
        }

        @Test
        @DisplayName("Pyramid with 3 dimensions throws")
        void pyramidTooManyDimensions() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Pyramid.class, "orange", 5.0, 8.0, 2.0));
        }

        @Test
        @DisplayName("Error message contains shape name and expected count")
        void errorMessageContainsShapeName() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Sphere.class, "red", 1.0, 2.0));
            assertTrue(ex.getMessage().contains("Sphere"));
            assertTrue(ex.getMessage().contains("1"));
        }
    }

    // =========================================================
    // 3. INVALID DIMENSION VALUES
    // =========================================================

    @Nested
    @DisplayName("3. Invalid Dimension Values")
    class InvalidDimensionValues {

        @Test
        @DisplayName("Zero radius for Sphere throws")
        void zeroRadiusSphere() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Sphere.class, "red", 0.0));
        }

        @ParameterizedTest(name = "Negative dimension {0} throws for Sphere")
        @ValueSource(doubles = {-1.0, -0.001, -999.0})
        @DisplayName("Negative radius for Sphere throws")
        void negativeRadiusSphere(double bad) {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Sphere.class, "red", bad));
        }

        @Test
        @DisplayName("NaN radius for Sphere throws")
        void nanRadiusSphere() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Sphere.class, "red", Double.NaN));
        }

        @Test
        @DisplayName("NaN second dimension for Cylinder throws")
        void nanHeightCylinder() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Cylinder.class, "green", 3.0, Double.NaN));
        }

        @Test
        @DisplayName("Zero width in RectangularPrism throws")
        void zeroWidthPrism() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(RectangularPrism.class, "yellow", 6.0, 0.0, 3.0));
        }

        @Test
        @DisplayName("Negative height in Pyramid throws")
        void negativeHeightPyramid() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Pyramid.class, "orange", 5.0, -8.0));
        }

        @Test
        @DisplayName("Double.NEGATIVE_INFINITY throws")
        void negativeInfinity() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Cube.class, "blue", Double.NEGATIVE_INFINITY));
        }
    }

    // =========================================================
    // 4. INVALID STRING INPUTS
    // =========================================================

    @Nested
    @DisplayName("4. Invalid String Inputs")
    class InvalidStringInputs {

        @Test
        @DisplayName("Null color throws")
        void nullColor() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Sphere.class, null, 5.0));
        }

        @Test
        @DisplayName("Empty string color throws")
        void emptyColor() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Sphere.class, "", 5.0));
        }

        @Test
        @DisplayName("Blank/whitespace color throws")
        void blankColor() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Sphere.class, "   ", 5.0));
        }

        @Test
        @DisplayName("Error message mentions color when color is null")
        void errorMessageMentionsColor() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Sphere.class, null, 5.0));
            assertTrue(ex.getMessage().toLowerCase().contains("color"));
        }
    }

    // =========================================================
    // 5. NULL AND UNSUPPORTED CLASS INPUTS
    // =========================================================

    @Nested
    @DisplayName("5. Null and Unsupported Class Inputs")
    class NullAndUnsupportedClass {

        @Test
        @DisplayName("Null shapeClass throws")
        void nullShapeClass() {
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(null, "red", 5.0));
        }

        @Test
        @DisplayName("Unsupported class (Shape3D.class directly) throws")
        void unsupportedAbstractClass() {
            // Shape3D itself is abstract and cannot be instantiated
            assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Shape3D.class, "red", 5.0));
        }

        @Test
        @DisplayName("Error message mentions 'Unsupported' for unknown class")
        void errorMessageForUnsupported() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> ShapeFactory.create(Shape3D.class, "red", 5.0));
            assertTrue(ex.getMessage().toLowerCase().contains("unsupported"));
        }

        @Test
        @DisplayName("ShapeFactory cannot be instantiated")
        void factoryNotInstantiable() {
            // Verify private constructor blocks direct instantiation
            assertThrows(Exception.class, () -> {
                var ctor = ShapeFactory.class.getDeclaredConstructor();
                ctor.setAccessible(true);
                ctor.newInstance();
            });
        }
    }

    // =========================================================
    // 6. POLYMORPHISM
    // =========================================================

    @Nested
    @DisplayName("6. Polymorphism — Factory Output via Base References")
    class PolymorphismTests {

        @Test
        @DisplayName("All five shapes created and stored in Shape3D array")
        void allShapesInShape3DArray() {
            Shape3D[] shapes = {
                    ShapeFactory.create(Sphere.class,           "red",    5.0),
                    ShapeFactory.create(Cube.class,             "blue",   4.0),
                    ShapeFactory.create(Cylinder.class,         "green",  3.0, 7.0),
                    ShapeFactory.create(RectangularPrism.class, "yellow", 6.0, 4.0, 3.0),
                    ShapeFactory.create(Pyramid.class,          "orange", 5.0, 8.0)
            };
            for (Shape3D shape : shapes) {
                assertTrue(shape.getVolume() > 0,
                        shape.getName() + " must have positive volume");
                assertTrue(shape.getSurfaceArea() > 0,
                        shape.getName() + " must have positive surface area");
                assertTrue(Double.isFinite(shape.getVolume()),
                        shape.getName() + " volume must be finite");
            }
        }

        @Test
        @DisplayName("All five shapes via ThreeDimensionalShape interface reference")
        void allShapesViaInterface() {
            ThreeDimensionalShape[] shapes = {
                    ShapeFactory.create(Sphere.class,           "red",    5.0),
                    ShapeFactory.create(Cube.class,             "blue",   4.0),
                    ShapeFactory.create(Cylinder.class,         "green",  3.0, 7.0),
                    ShapeFactory.create(RectangularPrism.class, "yellow", 6.0, 4.0, 3.0),
                    ShapeFactory.create(Pyramid.class,          "orange", 5.0, 8.0)
            };
            for (ThreeDimensionalShape shape : shapes) {
                assertTrue(shape.getVolume() > 0);
                assertTrue(shape.getSurfaceArea() > 0);
            }
        }

        @Test
        @DisplayName("Sphere from factory is instance of Shape3D")
        void sphereIsShape3D() {
            assertInstanceOf(Shape3D.class, ShapeFactory.create(Sphere.class, "red", 5.0));
        }

        @Test
        @DisplayName("Sphere from factory is instance of ThreeDimensionalShape")
        void sphereIsInterface() {
            assertInstanceOf(ThreeDimensionalShape.class,
                    ShapeFactory.create(Sphere.class, "red", 5.0));
        }

        @Test
        @DisplayName("Factory-created Sphere volume matches direct construction")
        void factorySphereMatchesDirectConstruction() {
            Shape3D fromFactory = ShapeFactory.create(Sphere.class, "red", 5.0);
            Shape3D direct      = new Sphere(5.0, "red");
            assertEquals(direct.getVolume(),      fromFactory.getVolume(),      DELTA);
            assertEquals(direct.getSurfaceArea(), fromFactory.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Factory-created Cylinder matches direct construction")
        void factoryCylinderMatchesDirect() {
            Shape3D fromFactory = ShapeFactory.create(Cylinder.class, "green", 3.0, 7.0);
            Shape3D direct      = new Cylinder(3.0, 7.0, "green");
            assertEquals(direct.getVolume(),      fromFactory.getVolume(),      DELTA);
            assertEquals(direct.getSurfaceArea(), fromFactory.getSurfaceArea(), DELTA);
        }
    }
}