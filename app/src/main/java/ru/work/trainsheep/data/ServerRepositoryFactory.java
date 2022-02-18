package ru.work.trainsheep.data;

public class ServerRepositoryFactory {

    private static ServerRepository serverRepository;

    public static ServerRepository getInstance(){
        if(serverRepository == null){
            serverRepository = new FakeServerRepository();
        }
        return serverRepository;
    }
}
