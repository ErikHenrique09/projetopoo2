package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.HibernateException;

public class ConexaoHibernate {
    private static EntityManagerFactory factory;
    private static EntityManager manager;

    public static EntityManager getInstance() {

        if (manager == null)
        {
            synchronized (ConexaoHibernate.class) {
                if (manager == null)
                {
                    try {
                        factory = Persistence.createEntityManagerFactory("lango");
                        manager = factory.createEntityManager();
                    } catch (HibernateException he) {
                        System.err.println(he.getMessage());
                    }
                }
            }
        }

        return manager;

    }

    public static void close() {
        manager.close();
        factory.close();
    }

}