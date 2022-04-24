package ru.work.trainsheep.data;

public class ServerRepositoryFactory {

    public static final String URL = "http://192.168.43.223:8080/";
    public static final boolean IS_ADMIN = false;


    private static ServerRepository serverRepository;

    public static ServerRepository getInstance(){
        if(serverRepository == null){
            //serverRepository = new FakeServerRepository();
            serverRepository = new RealServerRepository();
        }
        return serverRepository;
    }
}