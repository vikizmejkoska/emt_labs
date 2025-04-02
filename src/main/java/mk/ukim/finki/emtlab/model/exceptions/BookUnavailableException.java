package mk.ukim.finki.emtlab.model.exceptions;

public class BookUnavailableException extends RuntimeException{
    public BookUnavailableException(Long id){
        super(String.format("Book with id: %d does not have available copies.", id));
    }

}
