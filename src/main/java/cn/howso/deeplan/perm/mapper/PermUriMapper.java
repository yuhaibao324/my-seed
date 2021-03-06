package cn.howso.deeplan.perm.mapper;

import java.util.List;

import cn.howso.deeplan.perm.dto.PermUriWithPerm;
import cn.howso.deeplan.perm.model.PermUri;
import cn.howso.mybatis.anno.Table;
import cn.howso.mybatis.mapper.BaseMapper;
import cn.howso.mybatis.model.Example;
@Table(name="sys_perm_uri")
public interface PermUriMapper extends BaseMapper<PermUri,Example,Integer>{

    List<PermUriWithPerm> queryFetchPerm();
}