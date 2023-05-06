package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
<#--<#if mapperAnnotationClass??>-->
import org.apache.ibatis.annotations.Mapper;
<#--</#if>-->

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#--<#if mapperAnnotationClass??>-->
@Mapper
<#--</#if>-->
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
