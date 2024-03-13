package isima.georganise.app.exception;

public class WrongPasswordException extends RuntimeException {

        public WrongPasswordException() {
            super();
        }

        public WrongPasswordException(String message) {
            super(message);
        }
}
