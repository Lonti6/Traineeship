package ru.work.trainsheep.data;

public class ServerRepositoryFactory {

    public static final String URL = "http://79.143.29.47:8080/";
    public static final boolean IS_ADMIN = true;


    private static ServerRepository serverRepository;

    public static ServerRepository getInstance(){
        if(serverRepository == null){
            //serverRepository = new FakeServerRepository();
            serverRepository = new RealServerRepository();
        }
        return serverRepository;
    }
}