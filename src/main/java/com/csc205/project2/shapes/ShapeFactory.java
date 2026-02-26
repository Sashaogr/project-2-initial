/*
* AI GENERATION DOCUMENTATION
* ===========================
* AI Tool Used: Claude 4.6
* Generation Date: 02/25/26
*
* Original Prompt:
* Shape Factory Pattern (+15 points). Consult your AI about what an OOP factory pattern
* is, then implement a factory pattern that creates shapes from Class and string/dimension
* inputs: Shape3D shape = ShapeFactory.create(Sphere.class, 'Red Ball', 5.0)
* Ensure the factory handles invalid inputs gracefully.
*
* Follow-up Prompts (if any):
* 1.Document the factory implementation and usage in your reflection
* 2.Include tests for the factory method
*
* Manual Modifications:
* - Fixed package declaration to com.csc205.project2.shapes
* - After reading the generated code I moved all the NaN/null/blank checks to run
*   before the dispatch block so errors are caught in one place rather than buried
*   inside individual shape constructors — makes the error messages much clearer
* - Added the requireDimensions() helper method to avoid repeating the same count
*   check five times inline
* - The private constructor throwing UnsupportedOperationException was my idea
*   I wanted to make sure nobody could accidentally do new ShapeFactory() since
*   everything is static
*
* Formula Verification:
* - N/A — ShapeFactory delegates all construction to the existing shape classes
*   which have already been individually verified
*/
package com.csc205.project2.shapes;

/**
 * Factory class for creating {@link Shape3D} instances.
 *
 * <h2>What is the Factory Pattern?</h2>
 * <p>The <strong>Factory Pattern</strong> is a creational design pattern that centralizes
 * object construction behind a single static method. Instead of calling {@code new Sphere(...)}
 * directly throughout your code, all callers go through one place —
 * {@code ShapeFactory.create(...)} — which decides what to build and how.
 * This decouples the calling code from the concrete classes, makes it easy to add
 * new shape types in one place, and provides a single choke-point for validation.</p>
 *
 * <h2>Usage Examples</h2>
 * <pre>{@code
 * // Sphere  — 1 dimension: radius
 * Shape3D sphere = ShapeFactory.create(Sphere.class, "red", 5.0);
 *
 * // Cube    — 1 dimension: sideLength
 * Shape3D cube = ShapeFactory.create(Cube.class, "blue", 4.0);
 *
 * // Cylinder — 2 dimensions: radius, height
 * Shape3D cylinder = ShapeFactory.create(Cylinder.class, "green", 3.0, 7.0);
 *
 * // RectangularPrism — 3 dimensions: length, width, height
 * Shape3D prism = ShapeFactory.create(RectangularPrism.class, "yellow", 6.0, 4.0, 3.0);
 *
 * // Pyramid — 2 dimensions: baseLength, height
 * Shape3D pyramid = ShapeFactory.create(Pyramid.class, "orange", 5.0, 8.0);
 * }</pre>
 *
 * <h2>Dimension Order Reference</h2>
 * <table border="1">
 *   <tr><th>Shape</th><th>dims[0]</th><th>dims[1]</th><th>dims[2]</th></tr>
 *   <tr><td>Sphere</td><td>radius</td><td>—</td><td>—</td></tr>
 *   <tr><td>Cube</td><td>sideLength</td><td>—</td><td>—</td></tr>
 *   <tr><td>Cylinder</td><td>radius</td><td>height</td><td>—</td></tr>
 *   <tr><td>RectangularPrism</td><td>length</td><td>width</td><td>height</td></tr>
 *   <tr><td>Pyramid</td><td>baseLength</td><td>height</td><td>—</td></tr>
 * </table>
 *
 * @author  CSC205
 * @version 1.0
 */
public class ShapeFactory {

    // ---------------------------------------------------------------
    // Prevent instantiation — this is a pure static utility class
    // ---------------------------------------------------------------

    private ShapeFactory() {
        throw new UnsupportedOperationException("ShapeFactory is a static utility class.");
    }

    // ---------------------------------------------------------------
    // Factory method
    // ---------------------------------------------------------------

    /**
     * Creates and returns a concrete {@link Shape3D} instance.
     *
     * <p>The correct number of {@code dimensions} varies by shape type:
     * <ul>
     *   <li>{@link Sphere}            — 1 value:  radius</li>
     *   <li>{@link Cube}              — 1 value:  sideLength</li>
     *   <li>{@link Cylinder}          — 2 values: radius, height</li>
     *   <li>{@link RectangularPrism}  — 3 values: length, width, height</li>
     *   <li>{@link Pyramid}           — 2 values: baseLength, height</li>
     * </ul>
     * </p>
     *
     * @param shapeClass the {@link Class} object of the desired concrete shape;
     *                   must not be {@code null} and must be a supported type
     * @param color      the color to assign to the shape;
     *                   must not be {@code null} or blank
     * @param dimensions one or more positive dimension values in the order
     *                   documented above; must match the expected count for
     *                   the requested shape type
     * @return a fully constructed {@link Shape3D} of the requested type
     * @throws IllegalArgumentException if {@code shapeClass} is {@code null},
     *                                  {@code color} is null/blank,
     *                                  the wrong number of dimensions is supplied,
     *                                  any dimension is &le; 0 or NaN, or
     *                                  {@code shapeClass} is an unsupported type
     */
    public static Shape3D create(Class<? extends Shape3D> shapeClass,
                                 String color,
                                 double... dimensions) {

        // ── Guard: null class ─────────────────────────────────────
        if (shapeClass == null) {
            throw new IllegalArgumentException(
                    "shapeClass must not be null.");
        }

        // ── Guard: null / blank color ─────────────────────────────
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException(
                    "color must not be null or blank.");
        }

        // ── Guard: null dimensions array ──────────────────────────
        if (dimensions == null) {
            throw new IllegalArgumentException(
                    "dimensions must not be null.");
        }

        // ── Guard: individual NaN / non-positive values ───────────
        for (int i = 0; i < dimensions.length; i++) {
            if (Double.isNaN(dimensions[i])) {
                throw new IllegalArgumentException(
                        "dimensions[" + i + "] must not be NaN.");
            }
            if (dimensions[i] <= 0) {
                throw new IllegalArgumentException(
                        "dimensions[" + i + "] must be > 0. Received: " + dimensions[i]);
            }
        }

        // ── Dispatch by class type ────────────────────────────────
        if (shapeClass == Sphere.class) {
            requireDimensions(shapeClass, dimensions, 1);
            return new Sphere(dimensions[0], color);

        } else if (shapeClass == Cube.class) {
            requireDimensions(shapeClass, dimensions, 1);
            return new Cube(dimensions[0], color);

        } else if (shapeClass == Cylinder.class) {
            requireDimensions(shapeClass, dimensions, 2);
            return new Cylinder(dimensions[0], dimensions[1], color);

        } else if (shapeClass == RectangularPrism.class) {
            requireDimensions(shapeClass, dimensions, 3);
            return new RectangularPrism(dimensions[0], dimensions[1], dimensions[2], color);

        } else if (shapeClass == Pyramid.class) {
            requireDimensions(shapeClass, dimensions, 2);
            return new Pyramid(dimensions[0], dimensions[1], color);

        } else {
            throw new IllegalArgumentException(
                    "Unsupported shape class: " + shapeClass.getSimpleName()
                            + ". Supported types: Sphere, Cube, Cylinder, RectangularPrism, Pyramid.");
        }
    }

    // ---------------------------------------------------------------
    // Helper: enforce exact dimension count
    // ---------------------------------------------------------------

    /**
     * Validates that the supplied {@code dimensions} array has exactly
     * {@code required} elements for the given {@code shapeClass}.
     *
     * @param shapeClass the shape type being constructed (used in the error message)
     * @param dimensions the dimension values supplied by the caller
     * @param required   the exact number of dimensions the shape needs
     * @throws IllegalArgumentException if {@code dimensions.length != required}
     */
    private static void requireDimensions(Class<? extends Shape3D> shapeClass,
                                          double[] dimensions,
                                          int required) {
        if (dimensions.length != required) {
            throw new IllegalArgumentException(
                    shapeClass.getSimpleName() + " requires exactly " + required
                            + " dimension(s), but " + dimensions.length + " were provided.");
        }
    }
}