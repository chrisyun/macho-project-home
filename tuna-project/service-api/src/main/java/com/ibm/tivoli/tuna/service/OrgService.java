/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.ibm.tivoli.tuna.entity.OrgDataResponse;

/**
 * @author ZhaoDongLu
 *
 */
@WebService(targetNamespace="http://service.tuna.tivoli.ibm.com/")
public interface OrgService {
  
  
  /**
   * 查询所有的组织机构, 具体结果设定受查询最大结果的限制, 超过允许返回的做大数量时, 返回相应失败信息.
   * @param startPage 以0开始的起始页
   * @param pageSize
   * @param sortAttributeName  需要排序的属性
   * @param ascend  true表示已升序排序, false表示以降序排列
   * @return
   */
  @WebMethod(operationName = "searchAll")
  @WebResult(name = "SearchAllResponse")
  public OrgDataResponse searchAll(@WebParam(name = "startPage")int startPage, @WebParam(name = "pageSize")int pageSize, @WebParam(name = "sortAttributeName")String sortAttributeName, @WebParam(name = "ascend")boolean ascend);

  /**
   * 查询所有的组织机构, 具体结果设定受查询最大结果的限制, 超过允许返回的做大数量时, 返回相应失败信息.
   * @param filter    LDAP查询过滤器
   * @param startPage 以0开始的起始页
   * @param pageSize
   * @param sortAttributeName  需要排序的属性
   * @param ascend  true表示已升序排序, false表示以降序排列
   * @return
   */
  @WebMethod(operationName = "searchAllByFilter")
  @WebResult(name = "SearchAllByFilterResponse")
  public OrgDataResponse searchAllByFilter(@WebParam(name = "filter")String filter, @WebParam(name = "startPage")int startPage, @WebParam(name = "pageSize")int pageSize, @WebParam(name = "sortAttributeName")String sortAttributeName, @WebParam(name = "ascend")boolean ascend);

  /**
   * 按照组织机构标识查询组织机构数据
   * @param orgid 组织机构
   * @return
   */
  @WebMethod(operationName = "getOrgByID")
  @WebResult(name = "GetOrgByIDResponse")
  public OrgDataResponse getOrgByID(@WebParam(name = "ou")String orgid);
  
  /**
   * 根据父组织标识查询子组织
   * @param parentOrgid 组织OU
   * @param sortAttributeName 需要排序的属性
   * @param ascend true表示已升序排序, false表示以降序排列
   * @return
   */
  @WebMethod(operationName = "searchOrgByParentOrgid")
  @WebResult(name = "SearchOrgByParentOrgid")
  public OrgDataResponse searchOrgByParentOrgid(@WebParam(name = "ou")String parentOrgid,@WebParam(name = "sortAttributeName")String sortAttributeName, @WebParam(name = "ascend")boolean ascend);
  
}
