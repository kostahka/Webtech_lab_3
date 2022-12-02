package by.bsuir.lab3.server.dao;

public class DaoFactory {
    public static final DaoFactory INSTANCE = new DaoFactory();

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return INSTANCE;
    }
    public RecordDao getRecordDao(){
        return RecordDao.getInstance();
    }
}
