package com.tch.dao.base;


import com.tch.common.utils.MyMapResultTransformer;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by shz on 2017/10/13.
 * Repository 基类实现类
 */
@NoRepositoryBean
public class BaseRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>
        implements BaseRepository<T, ID> {

    //实体管理类，对持久化实体做增删改查，自动义sq操作模板所需要的核心类
    @PersistenceContext
    EntityManager entityManager;
    //必须实现此构造函数，参考自官方文档
    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Map> tchQueryList(String sql, Object... args) {
        Query q=entityManager.createNativeQuery(sql);
        //查询结果转map
        q.unwrap(SQLQuery.class).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
                .setResultTransformer(MyMapResultTransformer.MY_INSTANCE);
        int i = 1;
        for (Object arg : args
                ) {
            q.setParameter(i++, arg);
        }
        return q.getResultList();
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<Map> tchQueryPage(String sql, Pageable pageable, Object... args) {
        Page<Map> pages = null;
        try{
            Query q=entityManager.createNativeQuery(sql);
            //查询结果转map
            q.unwrap(SQLQuery.class).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
                    .setResultTransformer(MyMapResultTransformer.MY_INSTANCE);
            int i = 1;
            for (Object arg : args
                    ) {
                q.setParameter(i++, arg);
            }
            List<Map> totalCount = q.getResultList();
            q.setFirstResult(pageable.getPageSize() * (pageable.getPageNumber()));
            q.setMaxResults(pageable.getPageSize());
            List<Map> pageCount = q.getResultList();
            pages = new PageImpl<Map>(pageCount, pageable, totalCount.size());
            
        }catch (Exception e){
            e.printStackTrace();
        }
        return pages;
    }

}