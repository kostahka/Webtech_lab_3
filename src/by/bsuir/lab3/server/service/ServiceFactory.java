package by.bsuir.lab3.server.service;

public class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public AuthService getAuthService() {
        return AuthService.getInstance();
    }
    public RecordService getRecordService() {
        return RecordService.getInstance();
    }
}
