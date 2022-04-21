package ru.work.trainsheep.data;

public class ServerRepositoryFactory {

    public static final String URL = "http://5.53.125.122:8080/";


    private static ServerRepository serverRepository;

    public static ServerRepository getInstance(){
        if(serverRepository == null){
            serverRepository = new FakeServerRepository();
            //serverRepository = new RealServerRepository();
        }
        return serverRepository;
    }
}
