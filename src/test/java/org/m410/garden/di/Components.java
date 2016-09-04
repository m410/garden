package org.m410.garden.di;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Fortin
 */
public class Components {

    List<Component> componentList;
    List<TransactionProxy> transactionAspectList;

    private Components(List<Component> componentList, List<TransactionProxy> transactionAspectList) {
        this.componentList = componentList;
        this.transactionAspectList = transactionAspectList;
    }

    public static Components init() {
        return new Components(new ArrayList<>(),new ArrayList<>());
    }

    public Components withTransaction(TransactionProxy o) {
        List<TransactionProxy> transactionAspectList2 = new ArrayList<>();
        transactionAspectList2.addAll(transactionAspectList);
        transactionAspectList2.add(o);
        List<Component> componentList2 = new ArrayList<>();
        componentList2.addAll(componentList);

        return new Components(componentList2,transactionAspectList2);
    }

    public Components withComponents(SampleComponent component) {
        List<TransactionProxy> transactionAspectList2 = new ArrayList<>();
        transactionAspectList2.addAll(transactionAspectList);
        List<Component> componentList2 = new ArrayList<>();
        componentList2.add(component);
        componentList2.addAll(componentList);

        return new Components(componentList2,transactionAspectList2);
    }

    public Components make() {
        // todo initialize components into service map
        return null;
    }

    public <T> T typeOf(Class<T> myServiceClass) {
        // todo lookup in service map
        return null;
    }
}
