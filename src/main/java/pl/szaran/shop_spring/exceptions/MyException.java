package pl.szaran.shop_spring.exceptions;

public class MyException extends RuntimeException{
    private ExceptionInfo exceptionInfo;

    public MyException(ExceptionCode exceptionCode, String exceptionMessage) {
        this.exceptionInfo = new ExceptionInfo(exceptionCode, exceptionMessage);
    }

    public ExceptionInfo getExceptionInfo() {
        return exceptionInfo;
    }
}