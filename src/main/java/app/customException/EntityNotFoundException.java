package app.customException;

public class EntityNotFoundException extends RuntimeException {
   public EntityNotFoundException(String message) {
        super(message);
    }
}