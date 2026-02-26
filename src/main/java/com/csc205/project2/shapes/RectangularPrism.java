/*
AI GENERATION DOCUMENTATION
* ===========================
* AI Tool Used: Claude 4.6
* Generation Date: 02/25/26
*
* Original Prompt:
* Create shape classes: RectangularPrism with properties length, width, height. Each
* class should extend Shape3D, implement the abstract methods from ThreeDimensionalShape,
* include proper constructors with validation, override toString() with shape-specific
* formatting, and add any shape-specific methods if needed
*
* Follow-up Prompts (if any):
* when I run the test I got this error — NaN validation not throwing exception
*
* Manual Modifications:
* - Fixed package declaration to com.csc205.project2.shapes
* - Added Double.isNaN() check to all three setters: setLength(), setWidth(), setHeight()
*   since each one had the same NaN vulnerability as the other shape classes
* - I verified that when l=w=h=4 the output matches Cube(4) exactly, which confirmed
*   the formulas are consistent between both classes
*
* Formula Verification:
* - Volume formula (l * w * h) verified against: basic geometry, confirmed with
*   l=6, w=4, h=3, expected 72.0
* - Surface area formula (2*(lw + lh + wh)) verified against: Wolfram MathWorld
*   https://mathworld.wolfram.com/Cuboid.html — confirmed with same values, expected 108.0
* - Space diagonal (sqrt(l^2 + w^2 + h^2)) verified against omnicalculator.com cuboid calculator
*/
package com.csc205.project2.shapes;

/**
 * Represents a three-dimensional Rectangular Prism (cuboid).
 *
 * <p>Formulas used:
 * <ul>
 *   <li>Surface Area = 2(lw + lh + wh)</li>
 *   <li>Volume = l * w * h</li>
 * </ul>
 * </p>
 *
 * @author  CSC205
 * @version 1.0
 */
public class RectangularPrism extends Shape3D {

    // ---------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------

    /** The length of the prism; must be greater than zero. */
    private double length;

    /** The width of the prism; must be greater than zero. */
    private double width;

    /** The height of the prism; must be greater than zero. */
    private double height;

    // ---------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------

    /**
     * Constructs a RectangularPrism with the specified dimensions and color.
     *
     * @param length the length of the prism; must be &gt; 0
     * @param width  the width of the prism; must be &gt; 0
     * @param height the height of the prism; must be &gt; 0
     * @param color  the color of the prism; must not be null or blank
     * @throws IllegalArgumentException if any dimension is not positive
     */
    public RectangularPrism(double length, double width, double height, String color) {
        super("Rectangular Prism", color);
        setLength(length);
        setWidth(width);
        setHeight(height);
    }

    // ---------------------------------------------------------------
    // ThreeDimensionalShape implementations
    // ---------------------------------------------------------------

    /**
     * Calculates the surface area of the rectangular prism.
     * Formula: 2(lw + lh + wh)
     *
     * @return the surface area as a {@code double}
     */
    @Override
    public double getSurfaceArea() {
        return 2 * ((length * width) + (length * height) + (width * height));
    }

    /**
     * Calculates the volume of the rectangular prism.
     * Formula: l * w * h
     *
     * @return the volume as a {@code double}
     */
    @Override
    public double getVolume() {
        return length * width * height;
    }

    // ---------------------------------------------------------------
    // Shape-specific method
    // ---------------------------------------------------------------

    /**
     * Calculates the length of the space diagonal of the rectangular prism
     * (the longest straight-line distance between two opposite corners).
     * Formula: &radic;(l&sup2; + w&sup2; + h&sup2;)
     *
     * @return the space diagonal length as a {@code double}
     */
    public double getSpaceDiagonal() {
        return Math.sqrt((length * length) + (width * width) + (height * height));
    }

    // ---------------------------------------------------------------
    // Getters & Setters
    // ---------------------------------------------------------------

    /** @return the length of this prism */
    public double getLength() { return length; }

    /**
     * Sets the length of this prism.
     *
     * @param length must be &gt; 0
     * @throws IllegalArgumentException if {@code length} is not positive
     */
    public void setLength(double length) {
        if (Double.isNaN(length) || length <= 0) {
            throw new IllegalArgumentException("Length must be greater than zero. Received: " + length);
        }
        this.length = length;
    }

    /** @return the width of this prism */
    public double getWidth() { return width; }

    /**
     * Sets the width of this prism.
     *
     * @param width must be &gt; 0
     * @throws IllegalArgumentException if {@code width} is not positive
     */
    public void setWidth(double width) {
        if (Double.isNaN(width) || width <= 0) {
            throw new IllegalArgumentException("Width must be greater than zero. Received: " + width);
        }
        this.width = width;
    }

    /** @return the height of this prism */
    public double getHeight() { return height; }

    /**
     * Sets the height of this prism.
     *
     * @param height must be &gt; 0
     * @throws IllegalArgumentException if {@code height} is not positive
     */
    public void setHeight(double height) {
        if (Double.isNaN(height) || height <= 0) {
            throw new IllegalArgumentException("Height must be greater than zero. Received: " + height);
        }
        this.height = height;
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Returns a formatted string representation of this Rectangular Prism.
     *
     * @return shape details including dimensions, space diagonal, surface area, and volume
     */
    @Override
    public String toString() {
        return super.toString() + String.format(
                "%nLength        : %.2f" +
                        "%nWidth         : %.2f" +
                        "%nHeight        : %.2f" +
                        "%nSpace Diagonal: %.2f",
                length,
                width,
                height,
                getSpaceDiagonal()
        );
    }
}