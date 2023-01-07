package at.ac.csdc23vz_02.bankserver.entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DepotEntryEntityDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(DepotEntryEntity depotEntryEntity) {
        entityManager.merge(depotEntryEntity);
    }


}
