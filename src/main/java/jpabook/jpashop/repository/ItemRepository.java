package jpabook.jpashop.repository;


import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if (item.getId()==null){

            //id가 없을 때는 새로 저장한다.
            em.persist(item);//save
        } else {

            //id가 있을 때는 Update한다.
            em.merge(item);//update
        }
    }
    public Item findOne(Long id){
        return em.find(Item.class,id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
