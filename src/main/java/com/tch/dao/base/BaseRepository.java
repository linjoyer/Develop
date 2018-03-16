package com.tch.dao.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by shz on 2017/10/13.
 * repository 基类，封装自定义查询方法
 */

//该注解表示 spring 容器不会创建该对象
@NoRepositoryBean
public interface  BaseRepository<T,ID extends Serializable>  extends JpaRepository<T,ID>,PagingAndSortingRepository<T, ID>,JpaSpecificationExecutor<T> {
    /**
     * sql查询
     *
     * @param sql
     * @param args
     * @return
     */
    List<Map> tchQueryList(String sql, Object... args);

    /**
     * sql分页查询
     *
     * @param sql
     * @param args
     * @return
     */
    Page<Map> tchQueryPage(String sql, Pageable pageable, Object... args);

}
