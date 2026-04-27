package com.factorysuite.repository;

import com.factorysuite.entity.ProcessEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessRepository extends JpaRepository<ProcessEntity, Integer> {


    @Query(value =
            "select * from process where " +
                    " (:keyword = '' or " +
                    " (:key = 'processName' and process_name like %:keyword%) or " +
                    " (:key = 'processCode' and process_code like %:keyword%) ) " +

                    "and (:useYn = '' or use_yn = :useYn) " +
                    "and delete_state='N' " ,
            nativeQuery = true)
    Page<ProcessEntity> findByprocessserch(String key, String keyword, String useYn, Pageable pageable);


    @Query(value =
            "select * from process where " +
                    "process_name = :processName " +
                    "and delete_state='N' " ,
            nativeQuery = true)
    Optional<ProcessEntity> findByProcessName(String processName);

}
