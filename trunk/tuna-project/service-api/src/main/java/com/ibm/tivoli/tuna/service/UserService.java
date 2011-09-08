/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.ibm.tivoli.tuna.entity.UserDataResponse;


/**
 * @author ZhaoDongLu
 *
 */
@WebService(targetNamespace="http://service.tuna.tivoli.ibm.com/")
public interface UserService {
  
  
  /**
   * 查询所有的用户, 具体结果设定受查询最大结果的限制, 超过允许返回的做大数量时, 返回相应失败信息.
   * @param startPage 以0开始的起始页
   * @param pageSize
   * @param sortAttributeName  需要排序的属性
   * @param ascend  true表示已升序排序, false表示以降序排列
   * @return
   */
  @WebMethod(operationName = "searchAll")
  @WebResult(name = "SearchAllResponse")
  public UserDataResponse searchAll(@WebParam(name = "startPage")int startPage, @WebParam(name = "pageSize")int pageSize, @WebParam(name = "sortAttributeName")String sortAttributeName, @WebParam(name = "ascend")boolean ascend);

  /**
   * 查询所有的用户, 具体结果设定受查询最大结果的限制, 超过允许返回的做大数量时, 返回相应失败信息.
   * @param filter    LDAP查询过滤器
   * @param startPage 以0开始的起始页
   * @param pageSize
   * @param sortAttributeName  需要排序的属性
   * @param ascend  true表示已升序排序, false表示以降序排列
   * @return
   */
  @WebMethod(operationName = "searchAllByFilter")
  @WebResult(name = "SearchAllByFilterResponse")
  public UserDataResponse searchAllByFilter(@WebParam(name = "filter")String filter, @WebParam(name = "startPage")int startPage, @WebParam(name = "pageSize")int pageSize, @WebParam(name = "sortAttributeName")String sortAttributeName, @WebParam(name = "ascend")boolean ascend);

  /**
   * 按照用户标识查询用户数据
   * @param username 用户名
   * @return
   */
  @WebMethod(operationName = "getUserByUsername")
  @WebResult(name = "GetUserByUsername")
  public UserDataResponse getUserByUsername(@WebParam(name = "username")String username);
  
  /**
   * 根据组织标识查询该组织下的用户
   * @param orgid 组织标识
   * @param sortAttributeName 需要排序的属性
   * @param ascend true表示已升序排序, false表示以降序排列
   * @return
   */
  @WebMethod(operationName = "searchUserByOrgid")
  @WebResult(name = "SearchUserByOrgid")
  public UserDataResponse searchUserByOrgid(@WebParam(name = "departmentnumber")String orgid,@WebParam(name = "sortAttributeName")String sortAttributeName, @WebParam(name = "ascend")boolean ascend);
}
