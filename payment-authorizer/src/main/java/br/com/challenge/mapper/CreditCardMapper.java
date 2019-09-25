package br.com.challenge.mapper;

import br.com.challenge.model.Card;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CreditCardMapper {

    void insert(@Param("card") Card card);
}
