package com.csc205.project2.shapes;

/*
        * AI GENERATION DOCUMENTATION
 * ===========================
         * AI Tool Used: Claude 4.6
        * Generation Date: 02/25/26
        *
        * Original Prompt:
        * [create shape3d.java with these requirements Implements the ThreeDimensionalShape interface * Concrete implementations of getVolume() and getSurfaceArea() that call the abstract methods * Common properties: name (String), color (String) (Properties in Java are typically private fields with public getters and setters) * Constructor(s) for initialization * toString() method that formats output consistently * Getter/setter methods as appropriate AI Prompting Tips: * Be specific about method signatures and return types * Request proper JavaDoc documentation * Ask for input validation where appropriate]
        *
        *
        * Follow-up Prompts (if any):
        * 1. "[Refinement prompt 1]"
        * 2. "[Refinement prompt 2]"
        *
        * Manual Modifications:
        * - [List any changes you made to the AI output]
        * - [Explain why changes were necessary]
        *
        * Formula Verification:
        * - Volume formula verified against: [source]
        * - Surface area formula verified against: [source]
        */

public abstract class Shape3D implements ThreeDimensionalShape {

    // ---------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------

    /** The descriptive name of this shape (e.g., "Sphere", "Cylinder"). */
    private String name;

    /** The color of this shape (e.g., "red", "blue"). */
    private String color;

    // ---------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------

    /**
     * Default constructor. Initializes name and color to empty strings.
     */
    public Shape3D() {
        this.name  = "";
        this.color = "";
    }

    /**
     * Parameterized constructor.
     *
     * @param name  the name of the shape; must not be {@code null} or blank
     * @param color the color of the shape; must not be {@code null} or blank
     * @throws IllegalArgumentException if {@code name} or {@code color} is
     *                                  {@code null} or blank
     */
    public Shape3D(String name, String color) {
        setName(name);
        setColor(color);
    }

    // ---------------------------------------------------------------
    // Abstract methods (delegated to concrete subclasses)
    // ---------------------------------------------------------------

    /**
     * Calculates and returns the surface area of this shape.
     *
     * @return the surface area as a {@code double}, always &ge; 0
     */
    @Override
    public abstract double getSurfaceArea();

    /**
     * Calculates and returns the volume of this shape.
     *
     * @return the volume as a {@code double}, always &ge; 0
     */
    @Override
    public abstract double getVolume();

    // ---------------------------------------------------------------
    // Getters & Setters
    // ---------------------------------------------------------------

    /**
     * Returns the name of this shape.
     *
     * @return the shape name; never {@code null}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this shape.
     *
     * @param name the new name; must not be {@code null} or blank
     * @throws IllegalArgumentException if {@code name} is {@code null} or blank
     */
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Shape name must not be null or blank.");
        }
        this.name = name.trim();
    }

    /**
     * Returns the color of this shape.
     *
     * @return the shape color; never {@code null}
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of this shape.
     *
     * @param color the new color; must not be {@code null} or blank
     * @throws IllegalArgumentException if {@code color} is {@code null} or blank
     */
    public void setColor(String color) {
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Shape color must not be null or blank.");
        }
        this.color = color.trim();
    }

    // ---------------------------------------------------------------
    // toString
    // ---------------------------------------------------------------

    /**
     * Returns a consistently formatted string representation of this shape,
     * including its name, color, surface area, and volume.
     *
     * <p>Example output:
     * <pre>
     * Shape  : Sphere
     * Color  : red
     * Surface Area : 314.16
     * Volume       : 523.60
     * </pre>
     * </p>
     *
     * @return formatted multi-line string describing this shape
     */
    @Override
    public String toString() {
        return String.format(
                "Shape        : %s%n" +
                        "Color        : %s%n" +
                        "Surface Area : %.2f%n" +
                        "Volume       : %.2f",
                name,
                color,
                getSurfaceArea(),
                getVolume()
        );
    }
}

