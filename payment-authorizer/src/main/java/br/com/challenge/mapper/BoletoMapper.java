package br.com.challenge.mapper;

import br.com.challenge.model.Boleto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BoletoMapper {

    void insert(@Param("boleto") Boleto boleto);

    Boleto findBy(@Param("authorizerNumber") Long authorizerNumber);
}
