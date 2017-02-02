package xyz.hedo.shop.dao.exception;

public class DaoException extends Exception {
    private static final long serialVersionUID = 1L;

    public DaoException(){
        super();
    }

    public DaoException(String message){
        super(message);
    }

    public DaoException(Exception e){
        super(e);
    }

    public DaoException(String message, Exception e){
        super(message, e);
    }
}
