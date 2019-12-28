package cn.yj.idiomsline.repository;

import cn.yj.idiomsline.entity.IdiomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdiomRepository extends JpaRepository<IdiomEntity,Integer> {
    IdiomEntity findByValue(String value);
    List<IdiomEntity> findByWordS(String wordS);
}
