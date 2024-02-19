package org.example.message;

public final class ErrMessageForCar {
    public static final String MOVE_ERROR_OUT_OF_BOUNDS =
            "Car cannot move outside of the field. Current position ({0}:{1}), direction - {2}";
    public static final String INIT_ERROR_OUT_OF_BOUNDS =
            "Car init outside field. Car coordinates for init: ({0}, {1}); filed bounds ({2}, {3})";

    private ErrMessageForCar() {
        throw new UnsupportedOperationException("This is the util class and cannot be instantiated");
    }
}
