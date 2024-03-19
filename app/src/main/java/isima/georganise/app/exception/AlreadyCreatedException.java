package isima.georganise.app.exception;

import lombok.Getter;

@Getter
public class AlreadyCreatedException extends RuntimeException {

    Long id;

    public AlreadyCreatedException() {
        super("Entity already created");
    }

    public AlreadyCreatedException(Long id, String message) {
        super(message);
        this.id = id;
    }
}
