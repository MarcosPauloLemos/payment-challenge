package br.com.challenge.mapper;

import br.com.challenge.model.Buyer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BuyerMapper {
    Buyer findByCpf(@Param("cpf") Long cpf);

    void insert(@Param("buyer") Buyer buyer);
}
