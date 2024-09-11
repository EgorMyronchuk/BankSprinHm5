package app.customException;

public class UserEmailNotFoundException extends RuntimeException{

    public UserEmailNotFoundException (String message)
    {
        super(message);
    }


}
