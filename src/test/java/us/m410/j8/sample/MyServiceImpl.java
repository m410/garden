package us.m410.j8.sample;


/**
 */
public class MyServiceImpl implements MyService {

    private MyServiceDao myServiceDao;

    public MyServiceImpl(MyServiceDao myServiceDao) {
        this.myServiceDao = myServiceDao;
    }

    public String get(String id) {
        return "got id: " + id;
    }

    @Override
    public String toString() {
        return "MyServiceImpl";
    }
}
